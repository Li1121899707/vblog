package com.hitwh.vblog.bean;

import java.util.Date;

public class Demo {
    private Integer testId;

    private String testName;

    private Date testRegistTime;

    private String testDescription;

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName == null ? null : testName.trim();
    }

    public Date getTestRegistTime() {
        return testRegistTime;
    }

    public void setTestRegistTime(Date testRegistTime) {
        this.testRegistTime = testRegistTime;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription == null ? null : testDescription.trim();
    }
}