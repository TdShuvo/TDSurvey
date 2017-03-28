package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.ApiUrlGenerator;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.DbHelper;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderCountry;
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

public class SyncCountry {

    private ApiUrlGenerator apiUrlGenerator = new ApiUrlGenerator();
    private String apiLink;
    private DbHelper dbHelper;
    private Context context;

    public SyncCountry(Context context) {
        this.context = context;
        apiLink = apiUrlGenerator.getCountryApiLink();
        dbHelper = new DbHelper(context);
    }

    public void startDownLoad() {
        if(Build.VERSION.SDK_INT >= 11)
            new SyncCountry.GetJson().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            new SyncCountry.GetJson().execute();
    }

    private class GetJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            new SyncSurvey(context).startDownLoad();
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
                int country_id = arrayElement.getInt("Country_id");
                String country_name = arrayElement.getString("Country_name");

                //Log.e("showData",question);
                HolderCountry holderCountry = new HolderCountry(country_id,country_name);
                dbHelper.insertCountry(holderCountry);
//                Log.e("PD DEBUG","Insert song success: "+title+" "+album+" "+artist+" "+photo+" >> "+ret);
               // Log.e("showData",holderQuestion.toString());
                Log.e("ShuvoQuestion", "get Question" + holderCountry.countryName);
            }

        }
    }
}
