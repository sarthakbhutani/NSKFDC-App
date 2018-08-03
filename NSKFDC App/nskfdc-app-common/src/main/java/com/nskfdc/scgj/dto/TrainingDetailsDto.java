package com.nskfdc.scgj.dto;

import com.nskfdc.scgj.common.BaseDto;

public class TrainingDetailsDto extends BaseDto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dateOfScreeningCommittee;
	private String startDateOfTraining;
	private String endDateOfTraining;
	private String trainingPartner;
	private String prinicipalTrainer;
	private String candidatesRegistered;
	private String candidatesAssessed;
	private String candidatesPassed;
	private String dateOfMedicalExamination;
	private String candidatesMedicallyExamined;
	private String payoutToCandidates;
	private String participantHandbook;
	public String getDateOfScreeningCommittee() {
		return dateOfScreeningCommittee;
	}
	public void setDateOfScreeningCommittee(String dateOfScreeningCommittee) {
		this.dateOfScreeningCommittee = dateOfScreeningCommittee;
	}
	public String getStartDateOfTraining() {
		return startDateOfTraining;
	}
	public void setStartDateOfTraining(String startDateOfTraining) {
		this.startDateOfTraining = startDateOfTraining;
	}
	public String getEndDateOfTraining() {
		return endDateOfTraining;
	}
	public void setEndDateOfTraining(String endDateOfTraining) {
		this.endDateOfTraining = endDateOfTraining;
	}
	public String getTrainingPartner() {
		return trainingPartner;
	}
	public void setTrainingPartner(String trainingPartner) {
		this.trainingPartner = trainingPartner;
	}
	public String getPrinicipalTrainer() {
		return prinicipalTrainer;
	}
	public void setPrinicipalTrainer(String prinicipalTrainer) {
		this.prinicipalTrainer = prinicipalTrainer;
	}
	public String getCandidatesRegistered() {
		return candidatesRegistered;
	}
	public void setCandidatesRegistered(String candidatesRegistered) {
		this.candidatesRegistered = candidatesRegistered;
	}
	public String getCandidatesAssessed() {
		return candidatesAssessed;
	}
	public void setCandidatesAssessed(String candidatesAssessed) {
		this.candidatesAssessed = candidatesAssessed;
	}
	public String getCandidatesPassed() {
		return candidatesPassed;
	}
	public void setCandidatesPassed(String candidatesPassed) {
		this.candidatesPassed = candidatesPassed;
	}
	public String getDateOfMedicalExamination() {
		return dateOfMedicalExamination;
	}
	public void setDateOfMedicalExamination(String dateOfMedicalExamination) {
		this.dateOfMedicalExamination = dateOfMedicalExamination;
	}
	public String getCandidatesMedicallyExamined() {
		return candidatesMedicallyExamined;
	}
	public void setCandidatesMedicallyExamined(String candidatesMedicallyExamined) {
		this.candidatesMedicallyExamined = candidatesMedicallyExamined;
	}
	public String getPayoutToCandidates() {
		return payoutToCandidates;
	}
	public void setPayoutToCandidates(String payoutToCandidates) {
		this.payoutToCandidates = payoutToCandidates;
	}
	public String getParticipantHandbook() {
		return participantHandbook;
	}
	public void setParticipantHandbook(String participantHandbook) {
		this.participantHandbook = participantHandbook;
	}
	public TrainingDetailsDto(String dateOfScreeningCommittee, String startDateOfTraining, String endDateOfTraining,
			String trainingPartner, String prinicipalTrainer, String candidatesRegistered, String candidatesAssessed,
			String candidatesPassed, String dateOfMedicalExamination, String candidatesMedicallyExamined,
			String payoutToCandidates, String participantHandbook) {
		super();
		this.dateOfScreeningCommittee = dateOfScreeningCommittee;
		this.startDateOfTraining = startDateOfTraining;
		this.endDateOfTraining = endDateOfTraining;
		this.trainingPartner = trainingPartner;
		this.prinicipalTrainer = prinicipalTrainer;
		this.candidatesRegistered = candidatesRegistered;
		this.candidatesAssessed = candidatesAssessed;
		this.candidatesPassed = candidatesPassed;
		this.dateOfMedicalExamination = dateOfMedicalExamination;
		this.candidatesMedicallyExamined = candidatesMedicallyExamined;
		this.payoutToCandidates = payoutToCandidates;
		this.participantHandbook = participantHandbook;
	}
	
	
}
