package com.sinosoft.aod.feed.utils;

import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class PatternTools {

    // 验证手机号
    private static final Pattern p = Pattern.compile("^(1)\\d{10}$");
    private static final Map<String,String> phone = new HashMap<>();
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private PatternTools() {
        //加载号码归属地数据
        loadPhoneArea();
    }

    /**
     * 按照规则对手机号码进行处理
     */
    public static String isLocal(String phoneNumber) {

        if (checkPhone(phoneNumber)){
            return phoneNumber;
        }

        return process(phoneNumber);
    }

    /**
     *  号码前缀写死，需要优化
     * @param phoneNumber 手机号码
     * @return 号码
     */
    private static String process(String phoneNumber){
        //手机号码，只取前七位、六位
        String phoneNumber1 = phoneNumber.substring(0, 7);
        String phoneNumber2 = phoneNumber.substring(0, 6);

        if(contains(phoneNumber1) || contains(phoneNumber2)){
            return new StringBuilder("020").append(phoneNumber).toString();
        }else{
            return new StringBuilder("0").append(phoneNumber).toString();
        }
    }

    /**
     * 验证手机号码
     * @param phoneNumber 手机号码
     * @return 是 或 否
     */
    public static boolean checkPhone(String phoneNumber){
        //验证是否是手机号
        Matcher m = p.matcher(phoneNumber.trim());
        return !m.matches();
    }

    public static boolean contains(String phoneNumber){
        return phone.containsKey(phoneNumber);
    }

    /**
     * 加载号码归属地表
     */
    private void loadPhoneArea(){
        List<Map<String, Object>> maps = SqlRunner.db().selectList("select a.num from crm_phone_area a where a.code = {0}","020");
        if (CollectionUtils.isNotEmpty(maps)) {
//            maps.forEach(map -> {
//                String num = map.get("num").toString();
//                phone.put(num,num);
//            });
            for (Map<String, Object> map : maps) {
                for (String s : map.keySet()) {

                    String num = map.get("num").toString();
                    phone.put(num,num);
                }
            }

        }

        log.info("PatternTools 电话号码规则类完成初始化，总计加载电话号码 {} 条！", phone.size());
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date strToDate(String str) throws Exception{

        Date date = null;
        try {
            if (!Strings.isNullOrEmpty(str)){
                format.setLenient(false);
                date = format.parse(str);
            }

        } catch (ParseException e) {
            log.error("日期格式不正确，请检查数据！{}",str);
            throw e;
        }
        return date;
    }


}
