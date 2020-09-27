package com.ecy.show.util;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUtils {
    /**
     * Method name: isEmail <BR>
     * Description: 判断是不是邮箱,是就返回true <BR>
     * Remark: <BR>
     * @param email
     * @return  boolean<BR>
     */
    public static boolean isEmail(String email) {
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if(email.matches(regex)) {
            return true;
        }else {
            return false;
        }
    }
    /**
     * Method name: isPhoneNum <BR>
     * Description: 判断手机号是不是正确,是就返回true <BR>
     * Remark: <BR>
     * @param phoneNume
     * @return  boolean<BR>
     */
    public static boolean isPhoneNum(String phoneNume) {
        String pattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
        if(phoneNume.matches(pattern)) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * Method name: getNowDateYMD <BR>
     * Description: 返回当前日期和时间 yyyy-MM-dd <BR>
     * Remark: <BR>
     * @return  String<BR>
     */
    public static String getNowDateYMD() {
        String dateTime = "";
        String pattern = "yyyy-MM-dd";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        dateTime = sdf.format(date);
        return dateTime;
    }

    /**
     * Method name: getNowDateYMD <BR>
     * Description: 返回当前日期和时间 yyyyMMdd <BR>
     * Remark: <BR>
     * @return  String<BR>
     */
    public static String getNowDateyyyyMMdd() {
        String pattern = "yyyyMMdd";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    /**
     *身份证验证
     */
    public static String identityCardVerification(String idStr) {
        String[] wf = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] checkCode = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        String iDCardNo = "";
        try {
            //判断号码的长度 15位或18位
            if (idStr.length() != 15 && idStr.length() != 18) {
                return "身份证号码长度应该为15位或18位";
            }
            if (idStr.length() == 18) {
                iDCardNo = idStr.substring(0, 17);
            } else {
                iDCardNo = idStr.substring(0, 6) + "19" + idStr.substring(6, 15);
            }
            if (!isStrNum(iDCardNo)) {
                return "身份证15位号码都应为数字;18位号码除最后一位外,都应为数字";
            }
            //判断出生年月
            String strYear = iDCardNo.substring(6, 10);
            String strMonth = iDCardNo.substring(10, 12);
            String strDay = iDCardNo.substring(12, 14);
            if (!isStrDate(strYear + "-" + strMonth + "-" + strDay)) {
                return "身份证生日无效";
            }
            GregorianCalendar gc = new GregorianCalendar();
            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                return "身份证生日不在有效范围";
            }
            if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
                return "身份证月份无效";
            }
            if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
                return "身份证日期无效";
            }
            //判断地区码
            Hashtable h = GetAreaCode();
            if (h.get(iDCardNo.substring(0, 2)) == null) {
                return "身份证地区编码错误";
            }
            //判断最后一位
            int theLastOne = 0;
            for (int i = 0; i < 17; i++) {
                theLastOne = theLastOne + Integer.parseInt(String.valueOf(iDCardNo.charAt(i))) * Integer.parseInt(checkCode[i]);
            }
            int modValue = theLastOne % 11;
            String strVerifyCode = wf[modValue];
            iDCardNo = iDCardNo + strVerifyCode;
            if (idStr.length() == 18 && !iDCardNo.equals(idStr)) {
                return "身份证无效，不是合法的身份证号码";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 地区代码
     * @return Hashtable
     */
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    private static boolean isStrNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断字符串是否为日期格式
     * @param strDate
     * @return
     */
    public static boolean isStrDate(String strDate) {
        Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static LocalDateTime getLocalDateYMD(LocalDateTime startTime) {
        startTime = startTime.minusHours(startTime.getHour());
        startTime = startTime.minusMinutes(startTime.getMinute());
        startTime = startTime.minusSeconds(startTime.getSecond());
        return startTime;
    }

    /**
     * 字符串转时间
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, df);
    }

    public static String getCarNumber(String car){
        return car.substring(1, car.length()).trim();
    }

    public static String getLocalDateTime2String(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }

    /**
     * 验证车牌号
     * 新能源车牌：
     * 字母“D”代表纯电动汽车，字母“ F”代表非纯电动汽车（包括插电式混合动力和燃料电池汽车等）。
     * 小型汽车号牌中“D”或“F”位于号牌序号的第一位，大型汽车号牌中“D”或“F”位于号牌序号的最后一位。
     */
    public static boolean isCarnumberNO(String carnumber) {
        String carnumRegex = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1})";
        if (StringUtils.isEmpty(carnumber)) {
            return false;
        } else {
            return carnumber.matches(carnumRegex);
        }
    }

    public static void main(String[] args) {
        String carNum = "32012319980302001X";
        System.out.println(identityCardVerification(carNum));
    }
}
