package com.example.svgk.mnnitacademicportal;

public class User {

    private static String NAME;
    private static String REGD_NO;
    private static String PASSWORD;
    private static String EMAIL;
    private static String CONTACT_NO;
    private static String DATE_OF_BIRTH;
    private static String BRANCH;
    private static String SEMESTER;
    private static String GENDER;
    private static String ADDRESS;

    public User(String NAME, String REGD_NO, String PASSWORD, String EMAIL, String CONTACT_NO,
                String DATE_OF_BIRTH, String BRANCH, String SEMESTER, String GENDER, String ADDRESS) {

        User.NAME = NAME;
        User.REGD_NO = REGD_NO;

        User.PASSWORD = PASSWORD;
        User.EMAIL = EMAIL;
        User.CONTACT_NO = CONTACT_NO;
        User.DATE_OF_BIRTH = DATE_OF_BIRTH;
        User.BRANCH = BRANCH;
        User.SEMESTER = SEMESTER;
        User.GENDER = GENDER;
        User.ADDRESS = ADDRESS;

    }

    public static String getNAME() {
        return NAME;
    }

    public static String getRegdNo() {
        return REGD_NO;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }

    public static String getEMAIL() {
        return EMAIL;
    }

    public static String getContactNo() {
        return CONTACT_NO;
    }

    public static String getDateOfBirth() {
        return DATE_OF_BIRTH;
    }

    public static String getBRANCH() {
        return BRANCH;
    }

    public static String getSEMESTER() {
        return SEMESTER;
    }

    public static String getGENDER() {
        return GENDER;
    }

    public static String getADDRESS() {
        return ADDRESS;
    }

}
