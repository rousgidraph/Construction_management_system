package com.example.third_year_project.logic;

public class Labourer {


    int ID;

    public Labourer() {
    }

    //static String TABLE_NAME= "Workers";
    String FIRST_NAME;
    String OTHER_NAME;
    String SPECIALTY;
    int WEEKLY_WAGE;
    String PHONE;

    public Labourer(int ID, String FIRST_NAME, String OTHER_NAME, String SPECIALTY, int WEEKLY_WAGE, String PHONE) {
        this.ID = ID;
        this.FIRST_NAME = FIRST_NAME;
        this.OTHER_NAME = OTHER_NAME;
        this.SPECIALTY = SPECIALTY;
        this.WEEKLY_WAGE = WEEKLY_WAGE;
        this.PHONE = PHONE;
    }


    @Override
    public String toString() {
        return "Labourer{" +
                "ID=" + ID +
                ", FIRST_NAME='" + FIRST_NAME + '\'' +
                ", OTHER_NAME='" + OTHER_NAME + '\'' +
                ", SPECIALTY='" + SPECIALTY + '\'' +
                ", WEEKLY_WAGE=" + WEEKLY_WAGE +
                ", PHONE='" + PHONE + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        this.FIRST_NAME = FIRST_NAME;
    }

    public String getOTHER_NAME() {
        return OTHER_NAME;
    }

    public void setOTHER_NAME(String OTHER_NAME) {
        this.OTHER_NAME = OTHER_NAME;
    }

    public String getSPECIALTY() {
        return SPECIALTY;
    }

    public void setSPECIALTY(String SPECIALTY) {
        this.SPECIALTY = SPECIALTY;
    }

    public int getWEEKLY_WAGE() {
        return WEEKLY_WAGE;
    }

    public void setWEEKLY_WAGE(int WEEKLY_WAGE) {
        this.WEEKLY_WAGE = WEEKLY_WAGE;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }
}
