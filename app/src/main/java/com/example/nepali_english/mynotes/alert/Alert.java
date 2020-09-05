package com.example.nepali_english.mynotes.alert;

public class Alert {

    private String alertTitle;
    private String alertMessage;

    public Alert() {}

    public Alert(String alertTitle, String alertMessage) {
        this.alertTitle = alertTitle;
        this.alertMessage = alertMessage;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    @Override
    public String toString() {
        return "AlertDialog{" +
                "alertTitle='" + alertTitle + '\'' +
                ", alertMessage='" + alertMessage + '\'' +
                '}';
    }
}
