package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

import java.io.Serializable;

/**
 * Created by TD02 on 3/21/2017.
 */

public class HolderAnswer implements Serializable {
    public int answerId = -1;
    public int userId;
    public int questionId;
    public int countryId;
    public int questionTypeId;
    public int secquenceId;
    public int surveyId;
    public String answer;
    public int answer_flag;

    public HolderAnswer(int answerId) {
        this.answerId = answerId;
    }

    public HolderAnswer() {
    }

  /*  public HolderAnswer( int userId, int questionId, int countryId, int questionTypeId, int secquenceId, int surveyId, String answer) {

        this.userId = userId;
        this.questionId = questionId;
        this.countryId = countryId;
        this.questionTypeId = questionTypeId;
        this.secquenceId = secquenceId;
        this.surveyId = surveyId;
        this.answer = answer;
    }*/

    public HolderAnswer(int userId, int questionId, int countryId, int questionTypeId, int secquenceId, int surveyId,
                        String answer, int answer_flag) {
        this.answerId = answerId;
        this.userId = userId;
        this.questionId = questionId;
        this.countryId = countryId;
        this.questionTypeId = questionTypeId;
        this.secquenceId = secquenceId;
        this.surveyId = surveyId;
        this.answer = answer;
        this.answer_flag = answer_flag;
    }
}
