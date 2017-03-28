package com.survey.shuvo.technodhaka.tdsurvey.perser;

/**
 * Created by TD02 on 3/7/2017.
 */

public class Parse {

    /**
     * this method convert the string into long
     * @param string string value
     * @return converted  long
     */
    public static long StringToLongNullCheck(String string) {

        long lgMaxValue = -1;
        if (string != null) {
            if (string.equals("null") || string.length() == 0) {
                lgMaxValue = 0;
            } else {
                lgMaxValue = (long) Double.parseDouble(string);
            }
        }

        return lgMaxValue;
    }

    /**
     * this method convert the string into double
     * @param string string value
     * @return converted  double
     */

    public static double StringToDoubleNullCheck(String string) {

        double lgMaxValue = -1;
        if (string != null) {
            if (string.equals("null") || string.length() == 0) {
                lgMaxValue = 0;
            } else {
                lgMaxValue = Double.parseDouble(string);
            }
        }

        return lgMaxValue;
    }
}

