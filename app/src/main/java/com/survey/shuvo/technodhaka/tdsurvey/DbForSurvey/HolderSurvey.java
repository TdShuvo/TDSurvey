package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/19/2017.
 */

public class HolderSurvey {
    public int surveyId = -1;
    public int userId ;
    public int sectorId;
    public String surveyName;

    public HolderSurvey() {
    }

    public HolderSurvey(int surveyId) {
        this.surveyId = surveyId;
    }

    public HolderSurvey(int surveyId, int userId, int sectorId, String surveyName) {
        this.surveyId = surveyId;
        this.userId = userId;
        this.sectorId = sectorId;
        this.surveyName = surveyName;
    }
}
