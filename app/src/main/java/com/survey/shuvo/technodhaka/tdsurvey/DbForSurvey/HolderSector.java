package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/22/2017.
 */
public class HolderSector {

    public int sectorId = -1;
    public String sectorName;

    public HolderSector(int sectorId) {
        this.sectorId = sectorId;
    }

    public HolderSector(int sectorId, String sectorName) {
        this.sectorId = sectorId;
        this.sectorName = sectorName;
    }

    public HolderSector() {
    }
}


