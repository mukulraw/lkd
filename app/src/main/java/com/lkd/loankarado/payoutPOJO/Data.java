package com.lkd.loankarado.payoutPOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("home_loan")
    @Expose
    private String homeLoan;
    @SerializedName("loan_against_property")
    @Expose
    private String loanAgainstProperty;
    @SerializedName("business_loan")
    @Expose
    private String businessLoan;
    @SerializedName("personal_loan")
    @Expose
    private String personalLoan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeLoan() {
        return homeLoan;
    }

    public void setHomeLoan(String homeLoan) {
        this.homeLoan = homeLoan;
    }

    public String getLoanAgainstProperty() {
        return loanAgainstProperty;
    }

    public void setLoanAgainstProperty(String loanAgainstProperty) {
        this.loanAgainstProperty = loanAgainstProperty;
    }

    public String getBusinessLoan() {
        return businessLoan;
    }

    public void setBusinessLoan(String businessLoan) {
        this.businessLoan = businessLoan;
    }

    public String getPersonalLoan() {
        return personalLoan;
    }

    public void setPersonalLoan(String personalLoan) {
        this.personalLoan = personalLoan;
    }
}
