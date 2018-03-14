package com.sinosoft.aod.feed.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * 工具类
 * Created by LONGLEI on 2018/1/23.
 */
public class CommonUtil {
    /**
     * 判断一个字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmptyStr(String str){
        if(str==null){
            return true;
        }
        str=str.trim();
        return str.isEmpty();
    }
    /**
     * 对集合去重
     * @param arlList
     * @return
     */
    public static List removeDuplicate(List arlList){
        if(CommonUtil.isEmpityCollection(arlList)){
            return new ArrayList();
        }
        HashSet h=new HashSet(arlList);
        arlList.clear();
        arlList.addAll(h);
        return arlList;
    }
    /**
     * 判断对象是否为空
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj){
        if(obj!=null){
            return true;
        }
        return false;
    }
    /**
     * 判断集合是否为空
     * @param c
     * @return 集合为空,返回true ,反之 false
     */
    public static boolean isEmpityCollection(Collection c){
        if(c==null||c.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 判断对象是否为空
     * @param s
     * @return
     */
    public static boolean isEmptyStr(Object s){
        return (s==null)||(s.toString().trim().length()==0);
    }
}
