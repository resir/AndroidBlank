package com.itguai.biz.util;

import java.util.regex.Pattern;

/**
 * Created by jianyuncheng on 15/5/14.
 */
public class CodeUtils {
    public final static int BATCH_SN = 11;//
    public final static int HUO_WEI = 12;//A01
    public final static int UNKNOWN = 0;

    public static int checkType(String code) {
        if(Pattern.matches("[A-Za-z]+[\\d|-]+", code)){
            return HUO_WEI;
        }
        if(Pattern.matches("\\d+[a-zA-Z\\d]+", code)){
            return BATCH_SN;
        }
        return UNKNOWN;
    }
}
