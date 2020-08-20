package com.insist.json;

/**
 * @author fanping.jwq@alibaba-inc.com
 * @Date 2020-06-15 20:39
 */
public class SearchDTO {

    String code;

    String label;

    boolean obligatory;

    boolean show;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isObligatory() {
        return obligatory;
    }

    public void setObligatory(boolean obligatory) {
        this.obligatory = obligatory;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
