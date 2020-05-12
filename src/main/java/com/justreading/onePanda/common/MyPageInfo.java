package com.justreading.onePanda.common;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author LYJ
 * @Description  自定义分页的统一返回规则
 * @date 2020 年 02 月 15 日 12:42
 */
@ApiModel("分页的规则的类")
public class MyPageInfo<T> {
    private static final  long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前页")
    private int pageNum;

    @ApiModelProperty(value = "每页记录的个数")
    private int pageSize;

    @ApiModelProperty(value = "总共的数量")
    private long total;


    @ApiModelProperty(value = "总页数")
    private  int pages;

    @ApiModelProperty("结果集")
    private List<T> list;

    /**
     * Page继承ArrayList 进行强转能够进行转换成Page
     * @param list
     */
    public MyPageInfo(List<T> list) {
            PageInfo pageInfo = new PageInfo(list);
            this.pageNum = pageInfo.getPageNum();
            this.pageSize = pageInfo.getPageSize();
            this.total = pageInfo.getTotal();
            this.list = pageInfo.getList();
            this.pages = pageInfo.getPages();
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
