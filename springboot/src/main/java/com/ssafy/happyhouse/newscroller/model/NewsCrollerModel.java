package com.ssafy.happyhouse.newscroller.model;

public class NewsCrollerModel {
    String title;
    String url;
    String description;

    @Override
    public String toString() {
        return "NewsCrollerModel[{]" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ']';
    }

    public NewsCrollerModel() {

    }
    public NewsCrollerModel(String title, String url, String description) {
        this.title = title;
        this.url = url;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
