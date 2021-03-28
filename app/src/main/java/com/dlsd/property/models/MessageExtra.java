package com.dlsd.property.models;

import java.io.Serializable;

public class MessageExtra implements Serializable {
    private String patientUserId;
    private String patientUserName;
    private String patientProfilePhoto;
    private String patientCardNumber;
    private String familyMemberId;
    private String contractStatus;
    private String doctorUserId;
    private String doctorUserName;
    private String doctorProfilePhoto;
    private String doctorCardNumber;
    private String doctorDeptName;
    private String doctorProfName;
    private String doctorHospitalName;
    private String diagnosisId;
    private String diagnosisTypeId;

    public String getPatientUserId() {
        return patientUserId;
    }

    public void setPatientUserId(String patientUserId) {
        this.patientUserId = patientUserId;
    }

    public String getPatientUserName() {
        return patientUserName;
    }

    public void setPatientUserName(String patientUserName) {
        this.patientUserName = patientUserName;
    }

    public String getPatientProfilePhoto() {
        return patientProfilePhoto;
    }

    public void setPatientProfilePhoto(String patientProfilePhoto) {
        this.patientProfilePhoto = patientProfilePhoto;
    }

    public String getPatientCardNumber() {
        return patientCardNumber;
    }

    public void setPatientCardNumber(String patientCardNumber) {
        this.patientCardNumber = patientCardNumber;
    }

    public String getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(String familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getDoctorUserId() {
        return doctorUserId;
    }

    public void setDoctorUserId(String doctorUserId) {
        this.doctorUserId = doctorUserId;
    }

    public String getDoctorUserName() {
        return doctorUserName;
    }

    public void setDoctorUserName(String doctorUserName) {
        this.doctorUserName = doctorUserName;
    }

    public String getDoctorProfilePhoto() {
        return doctorProfilePhoto;
    }

    public void setDoctorProfilePhoto(String doctorProfilePhoto) {
        this.doctorProfilePhoto = doctorProfilePhoto;
    }

    public String getDoctorCardNumber() {
        return doctorCardNumber;
    }

    public void setDoctorCardNumber(String doctorCardNumber) {
        this.doctorCardNumber = doctorCardNumber;
    }

    public String getDoctorDeptName() {
        return doctorDeptName;
    }

    public void setDoctorDeptName(String doctorDeptName) {
        this.doctorDeptName = doctorDeptName;
    }

    public String getDoctorProfName() {
        return doctorProfName;
    }

    public void setDoctorProfName(String doctorProfName) {
        this.doctorProfName = doctorProfName;
    }

    public String getDoctorHospitalName() {
        return doctorHospitalName;
    }

    public void setDoctorHospitalName(String doctorHospitalName) {
        this.doctorHospitalName = doctorHospitalName;
    }

    public String getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getDiagnosisTypeId() {
        return diagnosisTypeId;
    }

    public void setDiagnosisTypeId(String diagnosisTypeId) {
        this.diagnosisTypeId = diagnosisTypeId;
    }
}
