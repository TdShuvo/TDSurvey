package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/22/2017.
 */
public class HolderUserAccess {

    public int userID;
    public int countryId;
    public int SectorId;

    public HolderUserAccess(int userID, int countryId, int sectorId) {
        this.userID = userID;
        this.countryId = countryId;
        SectorId = sectorId;
    }

    public HolderUserAccess() {
    }
}
