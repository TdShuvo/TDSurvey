package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/21/2017.
 */

public class HolderAnswer {
    public int answerId = -1;
    public int userId;
    public int questionId;
    public int countryId;
    public int questionTypeId;
    public int secquenceId;
    public int surveyId;
    public String answer;

    public HolderAnswer(int answerId) {
        this.answerId = answerId;
    }

    public HolderAnswer() {
    }

    public HolderAnswer( int userId, int questionId, int countryId, int questionTypeId, int secquenceId, int surveyId, String answer) {

        this.userId = userId;
        this.questionId = questionId;
        this.countryId = countryId;
        this.questionTypeId = questionTypeId;
        this.secquenceId = secquenceId;
        this.surveyId = surveyId;
        this.answer = answer;
    }
}
