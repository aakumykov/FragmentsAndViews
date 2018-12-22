
package ru.aakumykov.dvachreader.models.Thread;

import com.google.gson.annotations.Expose;

public class NewsAbu {

    @Expose
    private String date;
    @Expose
    private Long num;
    @Expose
    private String subject;
    @Expose
    private Long views;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

}
