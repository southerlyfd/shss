package com.jianghongbo.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-31 18:59
 * @description：
 */
public class StringUtil {

    public static boolean isBlank(String str) {
        str = StringUtils.isBlank(str) ? "" : str.trim();
        return str.length() > 0 ? false : true;
    }

    public static String getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmssSSS");
        return sdf.format(date);
    }

}
