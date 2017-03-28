package com.survey.shuvo.technodhaka.tdsurvey;

/**
 * Created by TD02 on 3/7/2017.
 */
public class SpinnerHelper {

    private int position;
    private String databaseId;
    private String databaseValue;

    public SpinnerHelper ( int position, String databaseId , String databaseValue ) {
        this.position = position;
        this.databaseId = databaseId;
        this.databaseValue = databaseValue;
    }

    public int getSpinnerPosition () {
        return position;
    }

    public String getId () {
        return databaseId;
    }

    public String getValue () {
        return databaseValue;
    }

    @Override
    public String toString () {
        return databaseValue;
    }

}
