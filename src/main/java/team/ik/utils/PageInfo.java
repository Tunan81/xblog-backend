package team.ik.utils;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long total;
    private List<T> list;

    public PageInfo() {
        super();
    }

    public PageInfo(List<T> list) {
        if (list instanceof Page) {
            Page page = (Page) list;
            this.total = page.getTotal();
            this.list = page;
        }
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "total=" + total +
                ", list=" + list +
                '}';
    }
}
