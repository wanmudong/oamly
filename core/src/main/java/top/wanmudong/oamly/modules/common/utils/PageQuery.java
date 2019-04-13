package top.wanmudong.oamly.modules.common.utils;

import com.github.pagehelper.PageHelper;
import lombok.Data;

/**
 * @author wanmudong
 * @date 15:25 2018/12/12
 *
 * 查询参数
 */
@Data
public class PageQuery {

    /**
     * 每页大小
     */
    private int size = 10;
    /**
     * 当前页
     */
    private int page = 1;
    /**
     * 0为倒序，1为正序
     */
    private int isAsc = 1;
    /**
     * 排序
     */
    private String orderBy  = "id";

    private String orderDesc  = orderBy + " desc";

    /**
     * 为PageHelper开启查询参数
     * @param pageQuery 查询参数
     */
    public static void startPage(PageQuery pageQuery){

        if (1 == pageQuery.isAsc){
            PageHelper.startPage(pageQuery.getPage(),pageQuery.getSize(),pageQuery.getOrderBy());
        }else {
            PageHelper.startPage(pageQuery.getPage(),pageQuery.getSize(),pageQuery.getOrderDesc());
        }

    }

}
