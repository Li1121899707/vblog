package com.hitwh.vblog.model;

import javax.validation.constraints.NotBlank;

public class LabelModel {

    private Integer labelId;

    @NotBlank(message = "标签名称不能为空")
    private String labelName;

    @NotBlank(message = "标签描述不能为空")
    private String description;

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
