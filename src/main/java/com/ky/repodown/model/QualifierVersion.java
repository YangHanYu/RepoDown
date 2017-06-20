package com.ky.repodown.model;

public class QualifierVersion {
    private String versionHead;
    private int qualifierVersion;
    private Object data;

    public QualifierVersion(String versionHead, int qualifierVersion, Object data) {
        super();
        this.versionHead = versionHead;
        this.qualifierVersion = qualifierVersion;
        this.data = data;
    }

    public String getVersionHead() {
        return versionHead;
    }

    public void setVersionHead(String versionHead) {
        this.versionHead = versionHead;
    }

    public int getQualifierVersion() {
        return qualifierVersion;
    }

    public void setQualifierVersion(int qualifierVersion) {
        this.qualifierVersion = qualifierVersion;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
