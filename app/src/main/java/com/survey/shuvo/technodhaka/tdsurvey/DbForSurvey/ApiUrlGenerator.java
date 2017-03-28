package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/19/2017.
 */
public class ApiUrlGenerator {
    private final String BASE_URL = "http://gaanbox.com/api/?";
    private final String API_KEY = "key=57ccf93abbd2f996b50f28a2d7db505d";

    public String getAlbumApiLink(int maxId) {
        return BASE_URL + API_KEY + "&table=album&after=" + maxId;
    }

    public static String getQuestionApiLink() {
        return "http://10.0.2.2:8080/api/values/GetQuestion?id=1002";
    }

    public static String getCountryApiLink() {
        return "http://10.0.2.2:8080/api/values/getCountry";

    }

    public static String getSkipApiLink() {
        return "http://10.0.2.2:8080/api/values/getSkipData";

    }

    public static String getAnswerModeApiLink() {
        return "http://10.0.2.2:8080/api/values/getAnswerMode";

    }

    public static String getUserAccessApiLink() {
        return "http://10.0.2.2:8080/api/values/getAnswerMode";

    }

    public static String getQuestionTypeApiLink() {
        return "http://10.0.2.2:8080/api/values/getQuestionType";

    }

    public static String getSectorTypeApiLink() {
        return "http://10.0.2.2:8080/api/values/getSector";

    }

    public static String getUserApiLink(int userId) {
        return "http://10.0.2.2:8080/api/values/getUser?UserId=" + userId;

    }

    public static String getSurveyApiLink(int surveyId) {
        return "http://10.0.2.2:8080/api/values/getSurveyName?SurveyId=" + surveyId;


    }
}
