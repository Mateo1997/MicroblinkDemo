package com.example.microblinkdemo.mrz;

public class MrzConstants {

    private MrzConstants() {
        throw new IllegalStateException("MrzConstants class");
    }

    protected static final String EMPTY_STRING = "";
    protected static final String FILLER = "<";
    protected static final String DOUBLE_FILLER = "<<";
    protected static final String ZERO = "0";
    protected static final String SPACE = " ";

    protected static final int DOCUMENT_CODE_SIZE = 2;
    protected static final int ISSUER_SIZE = 3;
    protected static final int DOCUMENT_NUMBER_SIZE = 9;
    protected static final int DATE_OF_BIRTH_SIZE = 6;
    protected static final int DATE_OF_EXPIRY_SIZE = 6;
    protected static final int GENDER_SIZE = 1;
    protected static final int NATIONALITY_SIZE = 3;
    protected static final int CHECK_DIGIT_SIZE = 1;
    protected static final int OPTIONAL_1_SIZE = 15;
    protected static final int OPTIONAL_2_SIZE = 11;

}
