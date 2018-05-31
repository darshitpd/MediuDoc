package com.example.asus.mediudoc;

/**
 * Created by asus on 31-May-18.
 */

public class AptSingle {
    public String patient_name;

    public AptSingle(){

    }
    public AptSingle(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getName() {
        return patient_name;
    }

    public void setName(String patient_name) {
        this.patient_name = patient_name;
    }
}
