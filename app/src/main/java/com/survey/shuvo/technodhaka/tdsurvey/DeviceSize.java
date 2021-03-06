package com.survey.shuvo.technodhaka.tdsurvey;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by TD02 on 3/7/2017.
 */
public class DeviceSize {


    /**
     * display's width
     */

    private float dpWidth;

    /**
     * display's Height
     */

    private float dpHeight;

    /**
     * to obtain the device's Display Metrics from resources
     */


    private Context mContext;


    /**
     * @param mContext the context of the invoking class
     */


    public DeviceSize(Context mContext) {
        this.mContext = mContext;
        getDeviceSizeInDp();
    }

    /**
     * Determin the see {@link #setDpHeight(float)}
     */

    private void getDeviceSizeInDp() {
        DisplayMetrics disMetrics = mContext.getResources().getDisplayMetrics();

        setDpWidth(disMetrics.widthPixels / disMetrics.density);

        setDpHeight(disMetrics.heightPixels / disMetrics.density);
    }

    public float getDpWidth() {
        return dpWidth;
    }

    /**
     * To prevent invoking from other class.
     * This should not call from outer class .
     * setter is only use for This class
     *
     * @param dpWidth floating value of width
     */

    private void setDpWidth(float dpWidth) {
        this.dpWidth = dpWidth;
    }

    public float getDpHeight() {
        return dpHeight;
    }

    /**
     * To prevent invoking from other class.
     * This should not call from outer class .
     * Setter is only use for This class
     *
     * @param dpHeight floating value of
     */

    private void setDpHeight(float dpHeight) {
        this.dpHeight = dpHeight;
    }

}
