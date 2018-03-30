package de.rwthaachen.cosa.frontend.excel;

public class DetectedCodesmell {
	private String className;
	private String codesmellType;
	private String programmingLanguage;
	private String activityType;
	private String classificationType;
	private float harmfulnessLevel;
	
	public DetectedCodesmell() {
		// TODO Auto-generated constructor stub
	}
	
	public DetectedCodesmell(String className, String codesmellType,
			String programmingLanguage, String activityType, 
			String classificationType, float harmfulnessLevel) {
		// TODO Auto-generated constructor stub
		this.className = className;
		this.codesmellType = codesmellType;
		this.programmingLanguage = programmingLanguage;
		this.activityType = activityType;
		this.classificationType = classificationType;
		this.harmfulnessLevel = harmfulnessLevel;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCodesmellType() {
		return codesmellType;
	}

	public void setCodesmellType(String codesmellType) {
		this.codesmellType = codesmellType;
	}

	public String getProgrammingLanguage() {
		return programmingLanguage;
	}

	public void setProgrammingLanguage(String programmingLanguage) {
		this.programmingLanguage = programmingLanguage;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getClassificationType() {
		return classificationType;
	}

	public void setClassificationType(String classificationType) {
		this.classificationType = classificationType;
	}

	public float getHarmfulnessLevel() {
		return harmfulnessLevel;
	}

	public void setHarmfulnessLevel(float harmfulnessLevel) {
		this.harmfulnessLevel = harmfulnessLevel;
	}
}
