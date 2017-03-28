package com.survey.shuvo.technodhaka.tdsurvey.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by TD02 on 3/6/2017.
 */

public class UtilClass {

    /**
     * Registration Mode
     */
    public static final int REGISTRATION_OPERATION_MODE = 1;
    /**
     * Distribution Mode
     */
    public static final int DISTRIBUTION_OPERATION_MODE = 2;
    /**
     * Service Mode
     */
    public static final int SERVICE_OPERATION_MODE = 3;


    /**
     * OTHER Mode
     */
    public static final int OTHER_OPERATION_MODE = 4;

    /**
     * Class Tag for Debug
     */
    private static final String TAG = "UtilClass";

    public static final String OPERATION_MODE = "OPERATION_MODE";


    public static int getMaxNumberFromList(Integer[] surveyNumbers) {
        Arrays.sort(surveyNumbers);
        int surveyNumber = surveyNumbers[surveyNumbers.length - 1] + 1;
        return surveyNumber;
    }


}
