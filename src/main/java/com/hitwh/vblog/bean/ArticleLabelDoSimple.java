package com.hitwh.vblog.bean;

import java.util.Date;

public class ArticleLabelDoSimple {
    //private Integer article_id;

    private Integer label_id;

    //private Date label_add_time;

    private String label_name;

    //private String label_description;


    public Integer getLabel_id() {
        return label_id;
    }

    public void setLabel_id(Integer label_id) {
        this.label_id = label_id;
    }

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

}