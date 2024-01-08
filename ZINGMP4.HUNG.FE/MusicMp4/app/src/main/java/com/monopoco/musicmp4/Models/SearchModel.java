package com.monopoco.musicmp4.Models;

import com.google.gson.annotations.SerializedName;

public class SearchModel {

    @SerializedName("filter")
    private String filter;

    @SerializedName("skip")
    private Integer page;

    @SerializedName("take")
    private Integer size;

    public SearchModel(String filter, Integer page, Integer size) {
        this.filter = filter;
        this.page = page;
        this.size = size;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
