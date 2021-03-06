package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/19/2017.
 */
public class ApiUrlGenerator {
    private final String BASE_URL = "http://gaanbox.com/api/?";
    private final String API_KEY = "key=57ccf93abbd2f996b50f28a2d7db505d";



    public static String getQuestionApiLink(int Id) {
       // return "http://10.0.2.2:8080/api/values/GetQuestion?id="+ Id;
        return "http://tshuvo-001-site1.htempurl.com/api/values/GetQuestion?id="+ Id;


    }

    public static String getCountryApiLink() {
      //  return "http://10.0.2.2:8080/api/values/getCountry";
        return "http://tshuvo-001-site1.htempurl.com/api/values/getCountry";

    }

    public static String getSkipApiLink() {
       // return "http://10.0.2.2:8080/api/values/getSkipData";
        return "http://tshuvo-001-site1.htempurl.com/api/values/getSkipData";

    }

    public static String getAnswerModeApiLink() {
       // return "http://10.0.2.2:8080/api/values/getAnswerMode";
        return "http://tshuvo-001-site1.htempurl.com/api/values/getAnswerMode";

    }

    public static String getUserAccessApiLink() {
       // return "http://10.0.2.2:8080/api/values/getAnswerMode";
        return "http://tshuvo-001-site1.htempurl.com/api/values/getAnswerMode";

    }

    public static String getQuestionTypeApiLink() {
       // return "http://10.0.2.2:8080/api/values/getQuestionType";
        return "http://tshuvo-001-site1.htempurl.com/api/values/getQuestionType";

    }

    public static String getSectorTypeApiLink() {
       // return "http://10.0.2.2:8080/api/values/getSector";
        return "http://tshuvo-001-site1.htempurl.com/api/values/getSector";

    }

    public static String getUserApiLink(String userName, String Password) {
       // return "http://10.0.2.2:8080/api/values/getUser?user_name=" + userName+"&password="+ Password;
        return "http://tshuvo-001-site1.htempurl.com/api/values/getUser?user_name=" + userName+"&password="+ Password;



    }

    public static String getSurveyApiLink(int userID) {
      //  return "http://10.0.2.2:8080/api/values/getSurveyName?UserId=" + userID;
        return "http://tshuvo-001-site1.htempurl.com/api/values/getSurveyName?UserId=" + userID;

    }
}
