package com.me.comment.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL) //springmvc中的标注，是为了控制返回的json字符串显示哪些字段。这里的设置是为null的字段不显示
public class Ad extends BaseBean {
    private Long id;
    private String title;
    private String imgFileName;
    private String link;
    private Long weight;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImgFileName() {
        return imgFileName;
    }
    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getWeight() {
        return weight;
    }
    public void setWeight(Long weight) {
        this.weight = weight;
    }
    
}