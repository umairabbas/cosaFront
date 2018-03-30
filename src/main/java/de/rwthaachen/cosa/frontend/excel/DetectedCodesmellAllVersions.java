package de.rwthaachen.cosa.frontend.excel;

public class DetectedCodesmellAllVersions {
	private String className;
	private String codesmellType;
	private String programmingLanguage;
	private String activityType;
	private float impact;
	private float activeness;
	private float ACT; //refers to the accumulation of activeness
	private float IMP; //refers to the accumulation of impact
	private String harmfulnessLevel;
	private float harmfulness;
	private int version; //refer to the version number
	
	public DetectedCodesmellAllVersions() {
		// TODO Auto-generated constructor stub
	}
	
	public DetectedCodesmellAllVersions(String className, String codesmellType,
			String programmingLanguage, String activityType, float impact, float activeness,
			float ACT, float IMP, String harmfulnessLevel, float harmfulness, int version) {
		// TODO Auto-generated constructor stub
		this.className = className;
		this.codesmellType = codesmellType;
		this.programmingLanguage = programmingLanguage;
		this.activityType = activityType;
		this.impact = impact;
		this.activeness = activeness;
		this.ACT = ACT;
		this.IMP = IMP;
		this.harmfulnessLevel = harmfulnessLevel;
		this.harmfulness = harmfulness;
		this.version = version;
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
		return harmfulnessLevel;
	}

	public void setClassificationType(String classificationType) {
		this.harmfulnessLevel = classificationType;
	}

	public float getHarmfulnessLevel() {
		return harmfulness;
	}

	public void setHarmfulnessLevel(float harmfulnessLevel) {
		this.harmfulness = harmfulnessLevel;
	}

	public float getImpact() {
		return impact;
	}

	public void setImpact(float impact) {
		this.impact = impact;
	}

	public float getActiveness() {
		return activeness;
	}

	public void setActiveness(float activeness) {
		this.activeness = activeness;
	}

	public float getACT() {
		return ACT;
	}

	public void setACT(float aCT) {
		ACT = aCT;
	}

	public float getIMP() {
		return IMP;
	}

	public void setIMP(float iMP) {
		IMP = iMP;
	}

	public float getHarmfulness() {
		return harmfulness;
	}

	public void setHarmfulness(float harmfulness) {
		this.harmfulness = harmfulness;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setHarmfulnessLevel(String harmfulnessLevel) {
		this.harmfulnessLevel = harmfulnessLevel;
	}
}
