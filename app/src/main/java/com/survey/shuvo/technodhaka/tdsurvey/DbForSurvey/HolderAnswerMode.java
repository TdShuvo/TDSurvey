package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/22/2017.
 */
public class HolderAnswerMode {

    public int minValue;
    public int maxValue;
    public int surveyId;
    public int questionId;
    public int QT_Id;
    public String hint;
    public int show_hide;

    public HolderAnswerMode(int minValue, int maxValue, int surveyId, int questionId, int QT_Id, String hint, int show_hide) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.surveyId = surveyId;
        this.questionId = questionId;
        this.QT_Id = QT_Id;
        this.hint = hint;
        this.show_hide = show_hide;
    }

    public HolderAnswerMode() {
    }
}
