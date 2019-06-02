package com.hitwh.vblog.bean;

import java.util.Date;

public class ResourceDo {
    private Integer resourceId;

    private String path;

    private String encryptFile;

    private Date uploadTime;

    private String url;

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getEncryptFile() {
        return encryptFile;
    }

    public void setEncryptFile(String encryptFile) {
        this.encryptFile = encryptFile == null ? null : encryptFile.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}