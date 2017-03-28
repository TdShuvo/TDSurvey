package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/22/2017.
 */
public class HolderSkip {
    public int skipId = -1;
    public int surveyId;
    public int questionId;
    public int answerId;
    public int skipQuestionId;

    public HolderSkip(int skipId) {
        this.skipId = skipId;
    }

    public HolderSkip(int skipId, int surveyId, int questionId, int answerId, int skipQuestionId) {
        this.skipId = skipId;
        this.surveyId = surveyId;
        this.questionId = questionId;
        this.answerId = answerId;
        this.skipQuestionId = skipQuestionId;
    }

    public HolderSkip() {
    }
}
