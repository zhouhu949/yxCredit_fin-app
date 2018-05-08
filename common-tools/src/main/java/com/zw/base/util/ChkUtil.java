package com.zw.base.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChkUtil {
    public static final String EXP_a_z = "[a-z]*";
    public static final String EXP_A_Z = "[A-Z]*";
    public static final String EXP_a_z_A_Z = "[a-zA-Z]*";
    public static final String EXP_0_9 = "[0-9]*";
    public static final String EXP_5_13 = "^\\d{5,13}$";
    public static final String EXP_0_9_a_z = "[0-9a-z]*";
    public static final String EXP_0_9_a_z_A_Z = "[0-9a-zA-Z]*";
    public static final String EXP_0_9_a_z__ = "[0-9a-z_]*";
    public static final String EXP_EMAIL = "^([a-z0-9A-Z_]+[_|\\-|\\.]?)+[a-z0-9A-Z_]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static final String EXP_PRICE = "^([1-9]\\d+|[1-9])(\\.\\d\\d?)*$";
    public static final String EXP_MOBILE = "^(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$";
    public static final String EXP_TEL = "^(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)+\\d{7,8}$";
    public static final String EXP_POSTALCODE = "^[0-9]{6}$";
    public static final String EXP_IP = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
    public static final String EXP_URL = "^[a-zA-z]+://[^><\"\' ]+";
    public static final String EXP_DATE = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}";
    public static final String EXP_DATETIME = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}";
    public static final String EXP_DATETIMESECOND = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}";
    public static final String DATESTRING_TAIL = "000000000";
    public static final String EXP_DATETIMESECOND2 = "[0-9]{4}[-]{1}[0-9]{1,2}[-]{1}[0-9]{1,2}[ ]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}[:]{1}[0-9]{1,2}[.]{1}[0-9]{1,3}";
    public static final String EXP_JAVA_SCRIPT_ONE = "[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']";
    public static final String EXP_JAVA_SCRIPT_TWO = "script";
    public static final String EXP_USERNAME = "^[a-zA-Z0-9_-]{4,30}$";
    public static final String EXP_PASSWORD = "^(?![^a-zA-Z]+$)(?!\\D+$).{6,16}$";
    public static final String EXP_NAME = "^[一-龥]{2,5}(?:·[一-龥]{2,5})*$";
    public static final String EXP_IC = "^[0-9Xx]{18}$";
    public static final String EXP_CAPTCHA = "^[0-9A-Za-z]{4}$";
    public static final String EXP_CAPTCHA_PHONEMSG = "^[0-9]{6}$";
    public static final String EXP_REALNAME = "^[一-龥豈-鶴]{2,4}$";
    public static final String EXP_CAR_NO = "^[A-Z_0-9]{5}$";
    public static final String EXP_NUMBER = "^[0-9]{1,}$";
    public static final String EXP_SQL_INJECT_ONE = "(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
    public static final String[] disableUsernames = new String[]{"51bel", "bel", "beier", "zhongzilian", "zzl", "admin", "test"};
    public static final String EXP_WEIXIN = "^[a-zA-Z\\d_-]{6,20}$";
    public static final String PASSPORT = "^[EGPSDegpsd]{1}[\\d]{7,8}$";

    public ChkUtil() {
    }

    public static String replaceHtmlJs(String s) {
        if(isEmpty(s)) {
            return s;
        } else {
            s = s.replace("&", "&amp");
            s = s.replace("\'", "\'\'");
            s = s.replace("<", "&lt");
            s = s.replace(">", "&gt");
            s = s.replace("chr(60)", "&lt");
            s = s.replace("chr(37)", "&gt");
            s = s.replace("\"", "&quot");
            s = s.replace(";", ";");
            s = s.replace("\n", "<br/>");
            s = s.replace(" ", "&nbsp");
            return s;
        }
    }

    public static boolean chkRight(Object obj, String regExp) {
        if(obj == null) {
            return false;
        } else {
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(obj.toString().trim());
            return matcher.matches();
        }
    }

    public static boolean chkStrLength(int length, String... regExps) {
        if(isEmpty(regExps)) {
            return true;
        } else {
            String[] var2 = regExps;
            int var3 = regExps.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String str = var2[var4];
                if(!chkStrLength(length, str)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String valueOf(Object obj) {
        return obj == null?"":obj.toString();
    }

    public static String valueOf(Object obj, String defaultValue) {
        return obj == null?defaultValue:obj.toString();
    }

    public static boolean chkStrLength(int length, String str) {
        return str == null || str.length() <= length;
    }

    public static boolean chkParamSqlInject(String... regExps) {
        if(isEmpty(regExps)) {
            return true;
        } else {
            String[] var1 = regExps;
            int var2 = regExps.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String string = var1[var3];
                if(!chkParamSqlInject(string)) {
                    return false;
                }
            }

            return true;
        }
    }

    private static boolean chkParamSqlInject(String regExp) {
        if(isEmpty(regExp)) {
            return true;
        } else {
            regExp = regExp.replaceAll("%", "");
            Pattern sqlPattern = Pattern.compile("(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)", 2);
            String param = "";

            try {
                param = URLDecoder.decode(regExp, "UTF-8");
            } catch (UnsupportedEncodingException var4) {
                var4.printStackTrace();
            }

            return !sqlPattern.matcher(param).find();
        }
    }

    public static boolean chkRight(Object[] objs, String regExp) {
        Object[] var2 = objs;
        int var3 = objs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object obj = var2[var4];
            if(!chkRight(obj, regExp)) {
                return false;
            }
        }

        return true;
    }

    public static boolean chkRigth(Object obj, String... regExps) {
        String[] var2 = regExps;
        int var3 = regExps.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String reg = var2[var4];
            if(!chkRight(obj, reg)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isEmpty(Object data) {
        return data == null || "".equals(data.toString().trim());
    }

    public static boolean isNotEmpty(Object data) {
        return !isEmpty(data);
    }

    public static boolean isStr(String str) {
        return !isEmpty(str);
    }

    public static boolean isMoney(String str) {
        if(isEmpty(str)) {
            return false;
        } else {
            try {
                Double.valueOf(str);
                return true;
            } catch (RuntimeException var2) {
                return false;
            }
        }
    }

    public static boolean isInteger(String str) {
        if(isEmpty(str)) {
            return false;
        } else {
            try {
                Integer.valueOf(str);
                return true;
            } catch (RuntimeException var2) {
                return false;
            }
        }
    }

    public static boolean isLong(String str) {
        if(isEmpty(str)) {
            return false;
        } else {
            try {
                Long.valueOf(str);
                return true;
            } catch (RuntimeException var2) {
                return false;
            }
        }
    }

    public static boolean isDouble(String str) {
        if(isEmpty(str)) {
            return false;
        } else {
            try {
                Double.valueOf(str);
                return true;
            } catch (RuntimeException var2) {
                return false;
            }
        }
    }

    public static boolean isDisableUsername(String username) {
        if(isEmpty(username)) {
            return true;
        } else {
            String[] var1 = disableUsernames;
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String disableUsername = var1[var3];
                if(username.toLowerCase().indexOf(disableUsername) != -1) {
                    return true;
                }
            }

            return false;
        }
    }

    public static JSONArray distinct(JSONArray array, String key) {
        JSONArray tempArray = new JSONArray();
        tempArray.add(array.get(0));

        for(int i = 0; i < array.size(); ++i) {
            boolean repeat = false;
            JSONObject baseJson = array.getJSONObject(i);
            String value = baseJson.getString(key);

            for(int k = 0; k < tempArray.size(); ++k) {
                JSONObject temp = tempArray.getJSONObject(k);
                String tempvalue = temp.getString(key);
                if(tempvalue.equals(value)) {
                    repeat = true;
                }
            }

            if(!repeat) {
                tempArray.add(array.get(i));
            }
        }

        return tempArray;
    }

    public static List<Map> distinct(List<Map> list, String key) {
        ArrayList tempResultList1 = new ArrayList();
        tempResultList1.add(list.get(0));

        for(int k = 0; k < list.size(); ++k) {
            boolean repeat = false;
            Map baseJson = (Map)list.get(k);
            String value = baseJson.get(key).toString();

            for(int j = 0; j < tempResultList1.size(); ++j) {
                Map temp = (Map)tempResultList1.get(j);
                String tempvalue = temp.get(key).toString();
                if(tempvalue.equals(value)) {
                    repeat = true;
                }
            }

            if(!repeat) {
                tempResultList1.add(list.get(k));
            }
        }

        return tempResultList1;
    }

    public static String filterHtml(String inputString) {
        String htmlStr = inputString;
        String textStr = "";

        try {
            String e = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            String regEx_html = "<[^>]+>";
            String regEx_nbsp = "&nbsp;";
            Pattern p_script = Pattern.compile(e, 2);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            Pattern p_style = Pattern.compile(regEx_style, 2);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            Pattern p_html = Pattern.compile(regEx_html, 2);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");
            Pattern p_nbsp = Pattern.compile(regEx_nbsp, 2);
            Matcher m_nbsp = p_nbsp.matcher(htmlStr);
            htmlStr = m_nbsp.replaceAll("");
            textStr = htmlStr;
        } catch (RuntimeException var15) {
            System.err.println("Html2Text: " + var15.getMessage());
        }

        return textStr;
    }
}
