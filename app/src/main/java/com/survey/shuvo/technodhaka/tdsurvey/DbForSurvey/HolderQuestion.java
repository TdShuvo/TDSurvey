package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/21/2017.
 */

public class HolderQuestion {
    public int questionId = -1;
    public int questionTypeId;
    public int surveyId;
    public String question;

    public HolderQuestion(int questionId) {
        this.questionId = questionId;
    }

    public HolderQuestion(int questionId, int questionTypeId, int surveyId, String question) {
        this.questionId = questionId;
        this.questionTypeId = questionTypeId;
        this.surveyId = surveyId;
        this.question = question;
    }

    public HolderQuestion() {
    }
}
