package com.survey.shuvo.technodhaka.tdsurvey.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.ApiUrlGenerator;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.DbHelper;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestion;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestionType;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderSurvey;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderUser;
import com.survey.shuvo.technodhaka.tdsurvey.R;
import com.survey.shuvo.technodhaka.tdsurvey.ResponseRecordingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static com.survey.shuvo.technodhaka.tdsurvey.activity.LoginActivity.SURVEY_USER;

public class GetSurveyActivity extends AppCompatActivity {

    Context context;
    DbHelper dbHelper;
    ListView listView;
    ArrayList<HolderSurvey> ListData;
    PopulateSurveyData surveyAdapter;
    ProgressBar pb;
    String user_name, password;
    HolderUser holderUser;
    HolderSurvey hs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_survey);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pb = new ProgressBar(GetSurveyActivity.this, null, android.R.attr.progressBarStyleHorizontal);
        pb.setVisibility(View.GONE);
        init();

        surveyAdapter = new PopulateSurveyData();


        SharedPreferences prefs = getSharedPreferences(SURVEY_USER, MODE_PRIVATE);
        user_name = prefs.getString("user_name", null);
        password = prefs.getString("password", null);


        holderUser = dbHelper.getUserInfo(user_name,password);

        ListData = dbHelper.getAllSurveyForAUser(holderUser.userId); // Change the method first
        listView.setAdapter(surveyAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                WebView webView = new WebView(context);
                hs = (HolderSurvey) surveyAdapter.getItem(position);
                if (LoginActivity.secondTimeLogIn == false){
                    GetQuestionJson g = new GetQuestionJson(hs.surveyId);
                    g.execute();
                }


                Intent intent = new Intent(GetSurveyActivity.this, ResponseRecordingActivity.class);
                intent.putExtra("surveyID",hs.surveyId);
                startActivity(intent);
            }
        });

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }

    private void init(){

        context = getApplicationContext();
        listView = (ListView) findViewById(R.id.lst_survey);
        dbHelper = new DbHelper(context);


    }


    class PopulateSurveyData extends BaseAdapter {

        @Override
        public int getCount() {
            return ListData.size();
        }

        @Override
        public Object getItem(int position) {
            return ListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return ListData.get(position).surveyId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View layout = convertView;

            if (layout == null) {
                layout = getLayoutInflater().inflate(R.layout.item_survey, parent, false);
            }
            TextView tvSurveyName = (TextView) layout.findViewById(R.id.tv_survey_name);


            HolderSurvey data = (HolderSurvey) getItem(position);
            tvSurveyName.setText(data.surveyName);

            return layout;
        }
    }
    private class GetQuestionJson extends AsyncTask {

        int surveyId;

        public GetQuestionJson(int surveyId) {
            this.surveyId = surveyId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            pb.setVisibility(View.GONE);

            GetQuestionTypeJson g = new GetQuestionTypeJson();
            g.execute();


           // Toast.makeText(context, "Question download complete", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getQuestionApiLink(surveyId));
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

            JSONArray questionArray = root.getJSONArray("Table");
            int len =  questionArray.length();
            for (int i = 0; i < len; i++) {
                JSONObject arrayElement =  questionArray.getJSONObject(i);
                int id = arrayElement.getInt("Question_id");
                String question = arrayElement.getString("Question");
                int questionTypeID = arrayElement.getInt("QT_id");
                int serveyId = arrayElement.getInt("Survey_id");


                HolderQuestion holderQuestion = new HolderQuestion(id,questionTypeID,serveyId,question);
                dbHelper.insertQuestion(holderQuestion);
//
            }

        }
    }


    private class GetQuestionTypeJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            //  new SyncSkip(context).startDownLoad();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getQuestionTypeApiLink());
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
                int qtId = arrayElement.getInt("QT_id");
                String qtName = arrayElement.getString("QT_name");

                // Log.e("showData",question);
                HolderQuestionType holderQuestionType = new HolderQuestionType(qtId,qtName);
                dbHelper.insertQuestionType(holderQuestionType);
//                Log.e("PD DEBUG","Insert song success: "+title+" "+album+" "+artist+" "+photo+" >> "+ret);
                // Log.e("showData",holderQuestion.toString());
                //  Log.e("ShuvoQuestion", "get Question" + holderUser.email);
            }

        }
    }

   /* private class GetSurveyJson extends AsyncTask {

        int userID;

        public GetSurveyJson(int userID) {
            this.userID = userID;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            pb.setVisibility(View.GONE);

            ListData = dbHelper.getAllSurveyForAUser(holderUser.userId); // Change the method first
            // new SyncQuestion(context).startDownLoad();

        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getSurveyApiLink(userID));
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
                int surveyId = arrayElement.getInt("Survey_id");
                String surveyName = arrayElement.getString("Survey_name");
                int userId = arrayElement.getInt("User_id");
                int sectorId = arrayElement.getInt("Sector_id");

                // Log.e("showData",question);
                HolderSurvey holderSurvey = new HolderSurvey(surveyId,userId,sectorId,surveyName);
                dbHelper.insertSurvey(holderSurvey);
//                Log.e("PD DEBUG","Insert song success: "+title+" "+album+" "+artist+" "+photo+" >> "+ret);
                // Log.e("showData",holderQuestion.toString());
                //  Log.e("ShuvoQuestion", "get Question" + holderUser.email);
            }

        }
    }*/

}
