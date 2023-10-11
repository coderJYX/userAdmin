package com.xjaxl.user.common.utils;

import java.math.BigDecimal;

/**
 * 后台参数替换，不允许使用魔法值
 */
public class ManaConstant {

    /**
     * 中文
     */
    public interface CN{

        /**
         * 正常
         */
        String YES = "正常";

        /**
         * 离线
         */
        String OFFLINE = "离线";

        /**
         * 报警
         */
        String NO = "报警";

    }


    /**
     * 符号
     */
    public interface Symbol {

        /**
         * 大于
         */
        String GT = ">";


        /**
         * 小于
         */
        String LT = "<";


        /**
         * 百分号
         */
        String PERCENT = "%";
        /**
         * 无穷大
         */
        String INFINITE = "∞";
        /**
         * 逗号
         */
        String COMMA = ",";

        /**
         * 顿号
         */
        String CAESURA = "、";
        /**
         * 问号
         */
        String QUESTION_MARK = "?";
        /**
         * 并且
         */
        String SINGLE_AND = "&";
        /**
         * 单斜杠
         */
        String SINGLE_SLASH = "/";
        /**
         * 下划线
         */
        String UNDERLINE = "_";
        /**
         * "@"
         */
        String AT = "@";
        /**
         * "."
         */
        String POINT = ".";
        /**
         * "{"
         */
        String LEFT_BRACE = "{";
        /**
         * "["
         */
        String LEFT_BRACKET = "[";
        /**
         * "("
         */
        String LEFT_PARENTHESES = "(";
        /**
         * "xls"
         */
        String XLS = "xls";
        /**
         * "xlsx"
         */
        String XLSX = "xlsx";
        /**
         * "docx"
         */
        String DOCX = "docx";
        /**
         * "wmf"
         */
        String WMF = "wmf";
        /**
         * "emf"
         */
        String EMF = "emf";
        /**
         * "svgz"
         */
        String SVGZ = "svgz";
        /**
         * 美元符号
         */
        String DOLLAR = "$";
        /**
         * 分号
         */
        String SEMICOLON = ";";
        /**
         * 时分秒
         */
        String TIME = " 23:59:59";
    }

    /**
     * 字符串数
     */
    public interface StringNumber{
        String ZERO = "0";
        String ONE = "1";
        String TWO = "2";
        String THREE = "3";
        String TEN = "10";

        String STRING_ZERO = "0";

        String STRING_ONE = "1";
        String STRING_TWO = "2";
        String STRING_THREE = "3";
        String STRING_FOUR = "4";

    }

    /**
     * 数字
     */
    public interface Number {
        int NEGATIVE_ONE = -1;
        int NEGATIVE_TWO = -2;
        int NEGATIVE_THREE = -3;
        int NEGATIVE_FOUR = -4;
        int NEGATIVE_FIVE = -5;
        int NEGATIVE_SIX = -6;
        int NEGATIVE_TEN = -10;
        int ZERO = 0;
        int ONE = 1;
        int TWO = 2;
        int THREE = 3;
        int FOUR = 4;
        int FIVE = 5;
        int SIX = 6;
        int SEVEN = 7;
        int EIGHT = 8;
        int NINE = 9;
        int TEN = 10;
        int ELEVEN = 11;
        int TWELVE = 12;
        int THIRTEEN = 13;
        int FOURTEEN = 14;
        int FIFTEEN = 15;
        int SIXTEEN = 16;
        int SEVENTEEN = 17;
        int EIGHTEEN = 18;
        int NINETEEN = 19;
        int TWENTY = 20;
        int TWENTY_ONE = 21;
        int TWENTY_TWO = 22;
        int TWENTY_THREE = 23;
        int TWENTY_FOUR = 24;
        int TWENTY_FIVE = 25;
        int TWENTY_SIX = 26;
        int TWENTY_SEVEN = 27;
        int TWENTY_EIGHT = 28;
        int TWENTY_NINE = 29;
        int THIRTY = 30;
        int THIRTY_ONE = 31;
        int THIRTY_TWO = 32;
        int THIRTY_THREE = 33;
        int THIRTY_FOUR = 34;
        int THIRTY_FIVE = 35;
        int THIRTY_SIX = 36;
        int THIRTY_SEVEN = 37;
        int THIRTY_EIGHT = 38;
        int THIRTY_NINE = 39;
        int FORTY = 40;
        int FORTY_ONE = 41;
        int FORTY_TWO = 42;
        int FORTY_THREE = 43;
        int FORTY_FOUR = 44;
        int FORTY_FIVE = 45;
        int FORTY_SIX = 46;
        int FORTY_SEVEN = 47;
        int FORTY_EIGHT = 48;
        int FORTY_NINE = 49;
        int FIFTY = 50;
        int FIFTY_ONE = 51;
        int FIFTY_TWO = 52;
        int FIFTY_THREE = 53;
        int FIFTY_FOUR = 54;
        int FIFTY_FIVE = 55;
        int FIFTY_SIX = 56;
        int FIFTY_SEVEN = 57;
        int FIFTY_EIGHT = 58;
        int FIFTY_NINE = 59;
        int SIXTY = 60;
        int SIXTY_ONE = 61;
        int SIXTY_TWO = 62;
        int SIXTY_THREE = 63;
        int SIXTY_FOUR = 64;
        int SIXTY_FIVE = 65;
        int SIXTY_SIX = 66;
        int SIXTY_SEVEN = 67;
        int SIXTY_EIGHT = 68;
        int SIXTY_NINE = 69;
        int SEVENTY = 70;
        int SEVENTY_ONE = 71;
        int SEVENTY_TWO = 72;
        int SEVENTY_THREE = 73;
        int SEVENTY_FOUR = 74;
        int SEVENTY_FIVE = 75;
        int SEVENTY_SIX = 76;
        int SEVENTY_SEVEN = 77;
        int SEVENTY_EIGHT = 78;
        int SEVENTY_NINE = 79;
        int EIGHTY = 80;
        int EIGHTY_ONE = 81;
        int EIGHTY_TWO = 82;
        int EIGHTY_THREE = 83;
        int EIGHTY_FOUR = 84;
        int EIGHTY_FIVE = 85;
        int EIGHTY_SIX = 86;
        int EIGHTY_SEVEN = 87;
        int EIGHTY_EIGHT = 88;
        int EIGHTY_NINE = 89;
        int NINETY = 90;
        int NINETY_ONE = 91;
        int NINETY_TWO = 92;
        int NINETY_THREE = 93;
        int NINETY_FOUR = 94;
        int NINETY_FIVE = 95;
        int NINETY_SIX = 96;
        int NINETY_SEVEN = 97;
        int NINETY_EIGHT = 98;
        int NINETY_NINE = 99;
        int ONE_HUNDRED = 100;
        int TWO_HUNDRED = 200;
        int FOUR_HUNDRED = 400;
        int FOUR_HANDERD_SEVENTY = 470;
        int FIVE_HUNDRED = 500;
        int SIX_HUNDRED = 600;
        int NINE_HUNDRED_NINETY_NINE = 999;
        int ONE_THOUSAND = 1000;
        int ONE_THOUSAND_AND_TWO = 1002;
        int THRITY_THOUSAND = 30000;
        int THREE_THOUSAND_AND_SIX_HUNDRED = 3600;
        double ZERO_POINT_ZERO = 0.0;
        double ZERO_POINT_TWO=0.2;
        double ZERO_POINT_THREE=0.3;
        double ZERO_POINT_FOUR=0.4;
        double ZERO_POINT_FIVE = 0.5;
        double ZERO_POINT_EIGHT = 0.8;
        BigDecimal ONE_HUNDRED_B = new BigDecimal(100);
        BigDecimal TEN_THOUSAND_B = new BigDecimal(10000);

        BigDecimal ZERO_POINT_ZERO_B = new BigDecimal("0.00");
    }


}
