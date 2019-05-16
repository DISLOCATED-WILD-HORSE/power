package common;

import java.util.ArrayList;
import java.util.List;

/*
*分页
 */
public class Page<T> {
    //当前页数
    private int currentPage;
    //每页显示数据条数
    private int pageSize;
    //总条数
    private int totalCount;
    //总页数
    private int totalPage;
    //每页的显示数据
    List<T> list = new ArrayList<>();

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }
}
