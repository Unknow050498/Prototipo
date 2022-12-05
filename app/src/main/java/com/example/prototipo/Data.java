package com.example.prototipo;

import java.io.Serializable;

public class Data implements Serializable {

    public String Degree;
    public String Name;
    public String LnameP;
    public String LnameM;
    public String Enrrollment;

    public  Data(){

    }

    public Data(String Name, String LnameP, String LnameM, String Enrrollment){
        this.Name = Name;
        this.LnameP = LnameP;
        this.LnameM = LnameM;
        this.Enrrollment = Enrrollment;
    }

    public String getDegree() {
        return Degree;
    }

    public void setDegree(String degree) {
        Degree = degree;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLnameP() {
        return LnameP;
    }

    public void setLnameP(String lnameP) {
        LnameP = lnameP;
    }

    public String getLnameM() {
        return LnameM;
    }

    public void setLnameM(String lnameM) {
        LnameM = lnameM;
    }

    public String getEnrrollment() {
        return Enrrollment;
    }

    public void setEnrrollment(String enrrollment) {
        Enrrollment = enrrollment;
    }
}
