package com.example.microblinkdemo.mrz;

public class MrzConstants {

    private MrzConstants() {
        throw new IllegalStateException("MrzConstants class");
    }

    public static final String EMPTY_STRING = "";
    public static final String FILLER = "<";
    public static final String DOUBLE_FILLER = "<<";
    public static final String ZERO = "0";
    public static final String SPACE = " ";

    public static final int DOCUMENT_CODE_SIZE = 2;
    public static final int ISSUER_SIZE = 3;
    public static final int DOCUMENT_NUMBER_SIZE = 9;
    public static final int DATE_OF_BIRTH_SIZE = 6;
    public static final int DATE_OF_EXPIRY_SIZE = 6;
    public static final int GENDER_SIZE = 1;
    public static final int NATIONALITY_SIZE = 3;
    public static final int CHECK_DIGIT_SIZE = 1;
    public static final int OPTIONAL_1_SIZE = 15;
    public static final int OPTIONAL_2_SIZE = 11;

}
