package de.rwthaachen.cosa.frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.rwthaachen.cosa.frontend.database.DBDao;
import de.rwthaachen.cosa.frontend.database.DBImplementation;
import de.rwthaachen.cosa.frontend.dsl.DSLHandler;
import de.rwthaachen.cosa.frontend.excel.DetectedCodesmell;
import de.rwthaachen.cosa.frontend.excel.DetectedCodesmellAllVersions;
import de.rwthaachen.cosa.frontend.newdsl.NewDSLHandler;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private DBDao dao = new DBImplementation();
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/**
	 * controller for evolution page
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/evolution", method=RequestMethod.GET)
	public String evolution(Model model){
		model.addAttribute("idAndVcsroot", dao.getIdAndVcsroot());
		return "evolution";
	}
	
	/**
	 * handler for evolution form 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/doEvolve", method=RequestMethod.POST)
	public String evolutionForm(@RequestParam Map<String, String> req, Model model){
		String cvsrootId = req.get("cvsroot").toString();
		String codesmelltype = req.get("codesmelltype").toString();
		String activitytype = req.get("activitytype").toString();
		String activityRange = req.get("activityrangeresult").toString();
		String severityRange = req.get("severityrangeresult").toString();
//		String range = req.get("riskrangeresult").toString();
		
		if(codesmelltype.equals("2")){
			codesmelltype = "God Class";
		}else if(codesmelltype.equals("3")){
			codesmelltype = "Brain Class";
		}else if(codesmelltype.equals("4")){
			codesmelltype = "Data Class";
		}else{
			codesmelltype = "all";
		}
		
		if(activitytype.equals("2")){
			activitytype = "Strong Active";
		}else if(activitytype.equals("3")){
			activitytype = "Stable Active";
		}else if(activitytype.equals("4")){
			activitytype = "Ameliorate Active";
		}else if(activitytype.equals("5")){
			activitytype = "Dormant";
		}else{
			activitytype = "all";
		}
		int activityLow = Integer.parseInt(activityRange);
		int severityLow = Integer.parseInt(severityRange);
//		
//		int low = Integer.parseInt(range.split(",")[0]) * 10;
//		int med = (Integer.parseInt(range.split(",")[1]) * 10) - low;
//		int high = 100 - med - low;
//		
		//System.out.println(low + " "+ med + " " + high);
		
		
		Map<String, Object> result = dao.getVCSAnalysisDetail(cvsrootId, codesmelltype, activitytype, activityLow, severityLow);
		model.addAttribute("id", result.get("id"));
		model.addAttribute("cvsroot", result.get("cvsroot"));
		model.addAttribute("analyzedDate", result.get("analyzedDate"));
		model.addAttribute("commitDate", result.get("commitDate"));
		model.addAttribute("numOfProblem", result.get("numOfProblem"));
		model.addAttribute("codesmells", result.get("codesmells"));
		model.addAttribute("totalVersion", result.get("totalVersion"));
		model.addAttribute("numOfVeryHigh", result.get("numOfVeryHigh"));
		model.addAttribute("numOfLow", result.get("numOfLow"));
		model.addAttribute("numOfMedium", result.get("numOfMedium"));
		model.addAttribute("numOfHigh", result.get("numOfHigh"));
		
//		model.addAttribute("low", low);
//		model.addAttribute("med", med);
//		model.addAttribute("high", high);
		
		return "evolutiondetail";
	}
	
	/**
	 * controller for showing the result of evolution form
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/viewEvolve", method=RequestMethod.POST)
	public String evolutionGraph(@RequestParam Map<String, String> req, Model model){
		String cvsrootId = req.get("cvsrootid").toString();
		String className = req.get("classname").toString();
		String codesmellType = req.get("codesmelltype").toString();
		
		model.addAttribute("codesmell", className);
		model.addAttribute("evolutionresult", dao.getCodeSmellEvolution(cvsrootId, className, codesmellType));
		return "evolutiongraph";
	}
	
	/**
	 * ===========
	 * = Old DSL =
	 * ===========
	 * 
	 * This is the controller to view the old DSL page
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/dsl", method=RequestMethod.GET)
	public String dsl(@RequestParam Map<String, String> req, Model model){
		return "dsl";
	}
	
	/**
	 * ===========
	 * = Old DSL =
	 * ===========
	 * 
	 * This controller is used to handle the DSL
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/getDSLResult", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> getDslResult(@RequestParam Map<String, String> req){
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> errorMessage = new ArrayList<String>();
		
		DSLHandler dsl = null;
		
		/**
		 * normalize the xml
		 */
		String requestdsl = req.get("dsldata");
		BufferedReader br = new BufferedReader(new StringReader(requestdsl));
		String tempdsl;
		StringBuilder normalizeddsl = new StringBuilder();
		
		try {
			while((tempdsl = br.readLine()) != null){
				normalizeddsl.append(tempdsl.trim());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dsl = new DSLHandler(normalizeddsl.toString(), dao);
		errorMessage = dsl.analyzedDSL();
		
		if(errorMessage.size() > 0){
			result.put("errormessage", errorMessage);
			result.put("status", "fail");
		}else{
			result.put("status", "success");
			if(dsl.getParser().getXmlcontent().get("rootelement") != null){
				result.put("rootelement", dsl.getParser().getXmlcontent().get("rootelement"));
				
				if(dsl.getParser().getXmlcontent().get("rootelement").equals("linegraph")){
					
					result.put("axis", dsl.getParser().getXmlcontent().get("axis"));
				}
			}
			result.put("result", normalizeddsl);
			if(dsl.getAnalyzeResult().size() > 0)
				result.put("resultcontent", dsl.getAnalyzeResult());
		}
		return result;
	}
	
	/**
	 * This is the controller to view the new DSL page
	 * @return
	 */
	@RequestMapping(value="/newdsl", method=RequestMethod.GET)
	public String newdsl(){
		return "newdsl";
	}
	
	/**
	 * This is the controller to handle the new DSL
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/getNewDSLResult", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> getNewDslResult(@RequestParam Map<String, String> req){
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> errorMessage = new ArrayList<String>();
		
		NewDSLHandler dsl = null;
		
		/**
		 * normalize the DSL
		 */
		String requestdsl = req.get("dsldata");
		
		BufferedReader br = new BufferedReader(new StringReader(requestdsl));
		String tempdsl;
		ArrayList<String> normalizeddsl = new ArrayList<String>();
		
		try {
			while((tempdsl = br.readLine()) != null){
				normalizeddsl.add(tempdsl.trim());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] input = new String[normalizeddsl.size()];
		input = normalizeddsl.toArray(input);
		
		dsl = new NewDSLHandler(input, dao);
		errorMessage = dsl.analyzedDSL();
		
		if(errorMessage.size() > 0){
			result.put("errormessage", errorMessage);
			result.put("status", "fail");
		}else{
			result.put("status", "success");
			if(dsl.getParser().getContent().get("make") != null){
				result.put("rootelement", dsl.getParser().getContent().get("make"));
				
				if(dsl.getParser().getContent().get("make").equals("linegraph")){
					
					result.put("axis", dsl.getParser().getContent().get("axis"));
				}
			}
			result.put("result", normalizeddsl);
			if(dsl.getAnalyzeResult().size() > 0)
				result.put("resultcontent", dsl.getAnalyzeResult());
		}
		return result;
	}
	
	/**
	 * This is the controller to download EXCEL
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/downloadExcel", method=RequestMethod.POST)
	public ModelAndView downloadExcel(@RequestParam Map<String, String> req){
		Gson gson = new Gson();
		List<DetectedCodesmell> list = gson.fromJson(req.get("excelinfo"), new TypeToken<List<DetectedCodesmell>>(){}.getType());
		return new ModelAndView("excelView","listdata",list);
	}
	
	@RequestMapping(value="/downloadExcelAllVersion", method=RequestMethod.POST)
	public ModelAndView downloadExcelAllVersion(@RequestParam Map<String, String> req){
		List<DetectedCodesmellAllVersions> list = dao.getAllVersionCodeSmells(req.get("rootId"));
		System.out.println(list.size());
		return new ModelAndView("excelViewAllVersions","listdata",list);
	}
}
