package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.ApiUrlGenerator;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.DbHelper;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderUser;
import com.survey.shuvo.technodhaka.tdsurvey.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by TD02 on 3/22/2017.
 */

public class SyncUser {

    private ApiUrlGenerator apiUrlGenerator = new ApiUrlGenerator();
    private String apiLink;
    private DbHelper dbHelper;
    private Context context;

    public SyncUser(Context context) {
        this.context = context;
       // apiLink = apiUrlGenerator.getUserApiLink(3);
        dbHelper = new DbHelper(context);
    }


    public void startDownLoad() {
        if(Build.VERSION.SDK_INT >= 11)
            new SyncUser.GetJson().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            new SyncUser.GetJson().execute();
    }

    private class GetJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            new SyncCountry(context).startDownLoad();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(apiLink);
                URLConnection con = requestUrl.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                int cp;
                while ((cp = in.read()) != -1) {
                    sb.append((char) cp);
                }
                jsonResult = sb.toString();
                parseJson(jsonResult);
            } catch (Exception e) {

            }
            return null;
        }


        void parseJson(String json) throws JSONException {
            JSONObject root = new JSONObject(json);
            // String permission = root.getString("state");

            JSONArray questionArray = root.getJSONArray("Table");
            int len =  questionArray.length();
            for (int i = 0; i < len; i++) {
                JSONObject arrayElement =  questionArray.getJSONObject(i);
                int userId = arrayElement.getInt("ID");
                String name = arrayElement.getString("Name");
                String password = arrayElement.getString("Password");
                String userName = arrayElement.getString("User_name");
                String email = arrayElement.getString("Email");
                String gender = arrayElement.getString("Gender");
                int countryId = arrayElement.getInt("Country_id");


                HolderUser holderUser = new HolderUser(userId,name,password,userName,email,gender,countryId);
                dbHelper.insertUser(holderUser);
             Log.e("ShuvoQuestion", "get Question" + holderUser.email);
            }

        }
    }

}
