package com.signsy.signsymain;

public class UInfoCard {
    String FirstName, UType, PhoneNumber, Email;

    public UInfoCard() {

    }

    public UInfoCard(String firstName, String UType, String phoneNumber, String email) {
        FirstName = firstName;
        this.UType = UType;
        PhoneNumber = phoneNumber;
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getUType() {
        return UType;
    }

    public void setUType(String UType) {
        this.UType = UType;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
