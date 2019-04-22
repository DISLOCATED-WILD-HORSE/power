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

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }
}
