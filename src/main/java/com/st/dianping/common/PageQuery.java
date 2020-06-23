package com.st.dianping.common;

/**
 * @author ShaoTian
 * @date 2020/6/23 15:57
 */
public class PageQuery {

    private Integer page = 1;

    private Integer size = 20;

    public PageQuery() {
    }

    public PageQuery(Integer page, Integer size) {
        this.page = page;
        this.size = size;
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
