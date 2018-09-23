package com.example.svgk.mnnitacademicportal;

public class AdminUser {

    public  String NAME;
    public  String REGD_NO;
    public  String PASSWORD;
    public  String EMAIL;
    public  String CONTACT_NO;
    public  String DATE_OF_BIRTH;
    public  String BRANCH;
    public  String SEMESTER;
    public  String GENDER;
    public  String ADDRESS;

    public AdminUser(String... params){
        this.REGD_NO = params[0];
        this.NAME = params[1];
        this.BRANCH = params[2];
        this.EMAIL = params[3];
    }

    public String getBRANCH() {
        return BRANCH;
    }

    public String getCONTACT_NO() {
        return CONTACT_NO;
    }

    public String getDATE_OF_BIRTH() {
        return DATE_OF_BIRTH;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getGENDER() {
        return GENDER;
    }

    public String getNAME() {
        return NAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getREGD_NO() {
        return REGD_NO;
    }

    public String getSEMESTER() {
        return SEMESTER;
    }
}
