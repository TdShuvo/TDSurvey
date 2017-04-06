package com.survey.shuvo.technodhaka.tdsurvey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.DbHelper;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderAnswer;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestion;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncAnswerMode;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncCountry;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncQuestion;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncQuestionType;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncSector;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncSkip;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncSurvey;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncUser;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncUserAccess;
import com.survey.shuvo.technodhaka.tdsurvey.activity.GetSurveyActivity;
import com.survey.shuvo.technodhaka.tdsurvey.activity.SurevyListAnswerActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainPageActivity extends AppCompatActivity {

    Button btnSyncOnline, btnSurveyData;
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        dbHelper = new DbHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        btnSyncOnline = (Button) findViewById(R.id.btn_sync_online);
        btnSurveyData = (Button) findViewById(R.id.get_survey_list);
        btnSurveyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainPageActivity.this,"downloading",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainPageActivity.this, GetSurveyActivity.class));

            }
        });
        btnSyncOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainPageActivity.this,SurevyListAnswerActivity.class));

            }
        });
    }





  /*  public  void getQuestion(){

        String JSON_URL = "http://10.0.2.2:8080/api/values/GetQuestion?id=2";
        //  String JSON_URL = "192.168.0.1:8080/api/values/GetUserId?user_name=" + user_name + "&password="+ password ;

        //  Log.d("shuvoUrl",JSON_URL);

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            parseJson(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("ShuvoResponse",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainPageActivity.this,"shuvo :"+ error.getMessage(),Toast.LENGTH_LONG).show();
                        // Log.e("Shuvo",error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void parseJson(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
       // String permission = root.getString("state");

            JSONArray questionArray = root.getJSONArray("Table");
            int len =  questionArray.length();
            for (int i = 0; i < len; i++) {
                JSONObject arrayElement =  questionArray.getJSONObject(i);
                int id = arrayElement.getInt("Question_id");
                String question = arrayElement.getString("Question");
                int questionTypeID = arrayElement.getInt("QT_id");

                QuestionTableDM holderQuestion = new QuestionTableDM(id,questionTypeID,question);
                dbHelper.insertQuestion(holderQuestion);
//                Log.e("PD DEBUG","Insert song success: "+title+" "+album+" "+artist+" "+photo+" >> "+ret);

                Log.e("ShuvoQuestion", "get Question" + question);
            }

    }*/
}
