<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:url value="/resources/images/swc.png" var="swclogo"/>
<spring:url value="/resources/codemirror/lib/codemirror.css" var="codemirrorcss"/>
<spring:url value="/resources/codemirror/theme/midnight.css" var="midnightcss"/>
<spring:url value="/resources/codemirror/lib/codemirror.js" var="codemirrorjs"/>
<spring:url value="/resources/codemirror/mode/xml/xml.js" var="xmljs"/>
<spring:url value="/resources/codemirror/addon/activeline.js" var="activelinejs"/>
<spring:url value="/resources/images/cosa.ico" var="cosaico"/>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>CoSA</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
	<link rel="stylesheet" href="${codemirrorcss}">
	<link rel="stylesheet" href="${midnightcss}">
	<link rel="icon" href="${cosaico}">
	<script src="${codemirrorjs}"></script>
	<script src="${xmljs}"></script>
	<script src="${activelinejs}"></script>
	<style>
		.navbar{
			margin-bottom: 0;
		}
		
		/* Tooltip */
	  	.tooltip > .tooltip-inner {
	      	background-color: #047081; 
	      	color: #FFFFFF; 
	      	border: 1px solid white; 
	      	padding: 5px;
	      	font-size: 16px;
	  	}
	  	
	</style>
</head>

<!-- header -->
<nav id="header" class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/cosa/">CoSA</a>
		</div>
		<ul class="nav navbar-nav">
			<li><a href="/cosa">Home</a></li>
			<li><a href="/cosa/evolution">Evolution</a></li>
			<!-- <li><a href="/cosa/dsl">Custom DSL</a></li> -->
			<li class="active"><a href="/cosa/newdsl">DSL</a></li>
		</ul>
	</div>
</nav>

 
<body data-ng-app="main" data-ng-controller="mainCtl" data-ng-init="init()"> 
	<div id="content" class="container-fluid">
		<!-- content -->
		<div id="containerdsl" class="row containerdsl">
			<div class="col-sm-4 containerdsl" style="padding:5px;background-color:#10192A;" style="overflow-y:scroll;">
				<div class="container-fluid">	
					<h3 style="color:white;">DSL <span data-toggle="modal" data-target="#howtouse"><span data-toggle="tooltip" data-placement="right" title="How to use!" class="glyphicon glyphicon-question-sign"></span></span></h3>
					<form action="/cosa/getNewDSLResult" method="POST" id="dslform">
						<div class="form-group">
							<textarea id="codeTextArea" name="dsldata"></textarea>
						</div>
						<div class="form-group" style="text-align:center;">
							<button type="submit" class="btn btn-info">Execute</button>
						</div>
						<div class="form-group">
							<div id="errormessage" class="container-fluid"></div>
						</div>
					</form>
				</div>
			</div>
			<div class="col-sm-8 containerdsl" style="overflow-y:scroll;">
				<h3>Result</h3>
				<div id="dslresult" class="container-fluid">
					
				</div>
			</div>
		</div>
		<!-- end of content -->
	</div>
</body>

<!-- footer -->
<nav class="navbar navbar-inverse navbar-fixed-bottom">
	<div class="container-fluid" style="text-align:center; padding:10px;">
		<p style="color:white;display:inline;">Copyright <span class="glyphicon glyphicon-copyright-mark"></span> 2016 by <img src="${swclogo}" alt="SWC RWTH Aachen"></p>
	</div>
</nav>


<!-- Modal -->
<div class="modal fade" id="tabledetails" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
         	 	<h4 class="modal-title">Result Details</h4>
			</div>
			<div id="modaldetails" class="modal-body">
          		
        	</div>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	</div>
		</div>
	</div>
</div>

<!-- How to use Modal -->
<div class="modal fade" id="howtouse" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
         	 	<h3 class="modal-title">How to use</h3>
			</div>
			<div class="modal-body">
          		<ul class="nav nav-tabs">
          			<li class="active"><a data-toggle="tab" href="#intro">Introduction</a></li>
          			<li><a data-toggle="tab" href="#htutable">Table</a></li>
          			<li><a data-toggle="tab" href="#htulinegraph">Line Graph</a></li>
          		</ul>
          		
          		<div class="tab-content">
          			<div id="intro" class="tab-pane fade in active">
          				<h4>What is DSL?</h4>
          				<p>DSL (stands for Domain Specific Language) a language that purposes to complete a particular task in a specific application domain. </p>
          				<br/>
          				<h4>Why DSL?</h4>
          				<p>It can express clearly what should a particular problem be resolved instead of knowing all syntaxes that unnecessary known by end-user.</p>
          			</div>
          			<div id="htutable" class="tab-pane fade">
          				<h4>Purpose</h4>
          				<p>To create a table that shows a brief and detail of a problem in certain version</p>
          				
          				<h4>Structure</h4>
          				<div class="well">
          					<code>make= table</code><sup>1</sup><br>
          					<code>fromlocalrepository= </code> [vcs local directory]<sup>1</sup><br>
          					<code>for=</code><br>
          					&emsp;<code>-classname= </code>[classname]<sup>*</sup><br/>
          					&emsp;<code>-version= </code>[version]<sup>*</sup><br/>
          					&emsp;<code>-codesmell= </code>[All|God Class|Brain Class|Data Class]<sup>*</sup><br/>
          					&emsp;<code>-change= </code>[All|Strong Active|Stable Active|Ameliorate Active|Dormant]<sup>*</sup><br/>
          					<br/>
          					<br/>
          					<strong>Explanation:</strong><br/>
          					<sup>1</sup> : needs to be occurred exactly once<br/>
          					<sup>*</sup> : can be occurred once or more (in case of more, it should be separated by comma)<br/>
          				</div>
          				
          				<h4>Example</h4>
          				<div class="well">
	          				<code>make= table</code><br>
	          				<code>fromlocalrepository= </code>/Users/harriskristanto/Desktop/Github Folder/Test/<br>
	          				<code>for=</code><br>
	          				<code>-classname= </code>CPPParser, Parser<br/>
	          				<code>-version= </code>1,2<br/>
	          				<code>-codesmell= </code>God Class<br/>
	          				<code>-change= </code>Strong Active
          				</div>
          			</div>
          			<div id="htulinegraph" class="tab-pane fade">
          				<h4>Purpose</h4>
          				<p>To create a line graph that shows the evolution of codesmell(s) according to a given axis</p>
          				
          				<h4>Structure</h4>
          				<div class="well">
          					<code>make= linegraph</code><sup>1</sup><br>
          					<code>fromlocalrepository= </code> [vcs local directory]<sup>1</sup><br>
          					<code>for=</code><br>
          					&emsp;<code>-classname= </code>[classname]<sup>*</sup><br/>
          					&emsp;<code>-codesmell= </code>[All|God Class|Brain Class|Data Class]<sup>*</sup><br/>
          					&emsp;<code>-change= </code>[All|Strong Active|Stable Active|Ameliorate Active|Dormant]<sup>*</sup><br/>
          					<code>axis=</code>[Severity Impact|Harmfulness Level|Severity Level|Changes]<sup>1</sup><br>
          					<br/>
          					<br/>
          					<strong>Explanation:</strong><br/>
          					<sup>1</sup> : needs to be occurred exactly once<br/>
          					<sup>*</sup> : can be occurred once or more (in case of more, it should be separated by comma)<br/>
          				</div>
          				
          				<h4>Example</h4>
          				<div class="well">
          					<code>make= linegraph</code><br>
	          				<code>fromlocalrepository= </code>/Users/harriskristanto/Desktop/Github Folder/Test/<br>
	          				<code>for=</code><br>
	          				<code>-classname= </code>CPPParser, Parser<br>
	          				<code>-codesmell= </code>God Class<br>
	          				<code>-change= </code>Strong Active<br>
	          				<code>axis= </code>Harmfulness Level
          				</div>
          			</div>
          		</div>
        	</div>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        	</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		$('[data-toggle="tooltip"]').tooltip();
		
		var detailsdata;
		$("#containerdsl").css("margin-top",$("#header").height() + "px");
		$(".containerdsl").height($(window).height() - ($(".navbar").height() *2) );
	
		$(window).resize(function(){
			$("#containerdsl").css("margin-top",$(".navbar").height() + "px");
			$(".containerdsl").height($(window).height() - ($(".navbar").height() *2) );	
		});
		
		var codeTextArea = document.getElementById("codeTextArea");
		var editor = CodeMirror.fromTextArea(codeTextArea, {
			lineNumbers : true,
			theme : "midnight",
			mode : "xml",
			styleActiveLine : true
		});
		
		$(".CodeMirror").css("border","1px solid #eee");

		//process AJAX
		$("#dslform").submit(function(e){
			e.preventDefault();
			$.ajax({
				type: "POST",
				url:'/cosa/getNewDSLResult',
				type:'POST',
				data:$(this).serialize()
			}).done(function(data){
				detailsdata = data;
				ajaxSuccess(data);
			}).fail(function(data){
				var error = "<div class='list-group'>";
				var errorcontent = "<a href='#' class='list-group-item list-group-item-action list-group-item-danger'>Failed to process the AJAX</a>";
				error += errorcontent;
				error += "</div>";
				$("#errormessage").html(error);
			});
		});
		
		function ajaxSuccess(data){
			if(data.status == "success"){
				$("#errormessage").html("");
				
				if(data.rootelement == "table"){
					createTable(data);
				}else if(data.rootelement == "linegraph"){
					createLinegraph(data);
				}
			}else if(data.status == "fail"){
				ajaxFailed(data.errormessage);
			}
		}
		
		function ajaxFailed(errormessage){
			var error = "<div class='list-group'>";
			var errorcontent = "";
			
			for(var i = 0 ; i < errormessage.length ; i++){
				var errorId = "err" + (i+1);
				errorcontent += "<a href='#' class='list-group-item list-group-item-action list-group-item-danger' id='"+errorId+"'>";
				errorcontent += errormessage[i] + "</a>";
				/*make it closeable (later)*/
				/* errorcontent += " <button type='button' class='close'>&times;</button></a>"; */
			}
			
			error += errorcontent + "</div>";
			
			$("#errormessage").html(error);
		}
		
		function createTable(data){
			var tab = 	'<table class="table table-hover table-bordered">'+
							'<thead>'+
								'<tr>'+
									'<th>Version</th>'+
									'<th>Version Date</th>'+
									'<th>Number Of Problem</th>'+
									'<th>View Details</th>'
								'</tr>'+
							'</thead>'+
						'<tbody>';
			
			var tabContent = "";
			for(var i = 0 ; i < data.resultcontent.versions.length; i++){
				var date = new Date(data.resultcontent.versions[i].commitdate);
				tabContent += "<tr>";
				tabContent += "<td>"+(i+1)+"</td>";
				tabContent += "<td>"+date.toUTCString()+"</td>";
				tabContent += "<td>"+data.resultcontent.versions[i].codesmells.length+"</td>";
				tabContent += '<td><button type="button" class="btn btn-info checkdetail" data-id="'+i+'" data-toggle="modal" data-target="#tabledetails">View</button></td>';
				tabContent += "</tr>";
			}
			
			tab += tabContent;
			tab += "</tbody>";
			tab += "</table>";
			
			$("#dslresult").html(tab);
		}
		
		$(document).on("click", ".checkdetail", function () {
		     var id = $(this).data('id');
		     var tab = 	'<table class="table table-hover table-bordered">'+
							'<thead>'+
								'<tr>'+
									'<th>Class Name</th>'+
									'<th>Codesmell Type</th>'+
									'<th>Change Type</th>'+
									'<th>Priority Level</th>'
								'</tr>'+
							'</thead>'+
						'<tbody>';
			
			var tabContent = "";
			var codesmells = detailsdata.resultcontent.versions[id].codesmells;
			for(var i = 0 ; i < codesmells.length; i++){
				tabContent += "<tr>";
				tabContent += "<td>"+codesmells[i].className+"</td>";
				tabContent += "<td>"+codesmells[i].codesmellType+"</td>";
				tabContent += "<td>"+codesmells[i].changeType+"</td>";
				tabContent += "<td>"+(i+1)+"</td>";
				tabContent += "</tr>";
			}
			
			tab += tabContent;
			tab += "</tbody>";
			tab += "</table>";
			
			$("#modaldetails").html(tab);
		});
		
		function createLinegraph(data){
			$("#dslresult").html('<div id="graphresult"></div>');
			
			//console.log(JSON.stringify(data));
			
			//setting for x-axis
			var datax = new Array();
			datax[0] = 0;
			
			for(var i = 0 ; i < data.resultcontent.version[0].length; i++){
				datax[i+1] = (i+1);
			}
			
			//setting for y-axis
			var datay = new Array();
			
			for(var i = 0 ; i < data.resultcontent.version.length; i++){
				datay[i] = new Array();
				datay[i][0] = 0;
				for(var j = 0 ; j < data.resultcontent.version[i].length; j++){
					datay[i][j+1] = data.resultcontent.version[i][j];
				}
			}
			
			//setting for names
			var dataName = new Array();
			for(var i = 0 ; i < data.resultcontent.className.length; i++){
				dataName[i] = data.resultcontent.className[i];
			}
			
			var evolvedata = new Array();
			for(var i = 0 ; i < data.resultcontent.className.length; i++){
				evolvedata[i] = {
					x	: datax,
					y	: datay[i],
					mode: 'lines+markers',
					name: String(dataName[i])
				};
			}
			
			var evolvelayout = {
				title: 'Class Evolution',
				xaxis: {
					title: 'Version'
				},
				yaxis: {
					title: data.axis
				}
			};
			
			Plotly.newPlot('graphresult', evolvedata, evolvelayout);
		}
	});
</script>
</html>
