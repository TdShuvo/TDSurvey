package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/22/2017.
 */

public class HolderCountry {
    public int countryId = -1;
    public String countryName;

    public HolderCountry() {
    }

    public HolderCountry(int countryId) {
        this.countryId = countryId;
    }

    public HolderCountry(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }
}
