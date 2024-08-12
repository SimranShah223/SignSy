package com.signsy.signsymain;

public class DItem {
    String FirstName,Description,StartDate,EndDate;

    public DItem(){}

    public DItem(String firstName, String description, String startDate, String endDate) {
        FirstName = firstName;
        Description = description;
        StartDate = startDate;
        EndDate = endDate;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
}
