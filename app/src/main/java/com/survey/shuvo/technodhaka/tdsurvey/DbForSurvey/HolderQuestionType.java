package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/22/2017.
 */

public class HolderQuestionType {

    public int questionTypeId = -1;
    public String questionTypeName;

    public HolderQuestionType(int questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public HolderQuestionType(int questionTypeId, String questionTypeName) {
        this.questionTypeId = questionTypeId;
        this.questionTypeName = questionTypeName;
    }

    public HolderQuestionType() {
    }
}
