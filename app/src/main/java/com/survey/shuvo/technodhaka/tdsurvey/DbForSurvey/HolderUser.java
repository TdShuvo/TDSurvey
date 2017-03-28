package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;

/**
 * Created by TD02 on 3/22/2017.
 */
public class HolderUser {
    public int userId = -1;
    public String name;
    public String password;
    public String user_name;
    public String email;
    public String gender;
    public int countryId;

    public HolderUser(int userId) {
        this.userId = userId;
    }

    public HolderUser() {
    }

    public HolderUser(int userId, String name, String password, String user_name, String email, String gender, int countryId) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.user_name = user_name;
        this.email = email;
        this.gender = gender;
        this.countryId = countryId;
    }
}
