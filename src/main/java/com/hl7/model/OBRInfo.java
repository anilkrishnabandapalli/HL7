package com.hl7.model;

public class OBRInfo {
	private String code;
	private String diagnosticName;
	private String fasting;
	private String priority;
	private String assessment;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDiagnosticName() {
		return diagnosticName;
	}

	public void setDiagnosticName(String diagnosticName) {
		this.diagnosticName = diagnosticName;
	}

	public String getFasting() {
		return fasting;
	}

	public void setFasting(String fasting) {
		this.fasting = fasting;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}
}
