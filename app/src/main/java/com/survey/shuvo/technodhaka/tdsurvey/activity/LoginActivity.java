package com.survey.shuvo.technodhaka.tdsurvey.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.ApiUrlGenerator;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.DbHelper;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderAnswerMode;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderCountry;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestion;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestionType;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderSector;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderSkip;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderSurvey;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderUser;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderUserAccess;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncAnswerMode;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncCountry;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncQuestion;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncQuestionType;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncSector;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncSkip;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncSurvey;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncUser;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.SycnWithOnline.SyncUserAccess;
import com.survey.shuvo.technodhaka.tdsurvey.MainPageActivity;
import com.survey.shuvo.technodhaka.tdsurvey.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class LoginActivity extends AppCompatActivity {
    public static final String SURVEY_USER = "Survey_user";
    EditText edtUserName, edtPassword;
    Button btnLogin;
    String user_name, password;
    DbHelper dbHelper;

    public ProgressBar spinner;
    SharedPreferences.Editor editor;
 //   public static final String JSON_URL = "http://127.0.0.1:8080/api/values/GetUserId?user_name=tdShuvo&password=shuvo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper  = new DbHelper(LoginActivity.this);

        editor = getSharedPreferences(SURVEY_USER, MODE_PRIVATE).edit();
       // dbHelper = new DbHelper(this);
       // new SyncUser(LoginActivity.this).startDownLoad();



        edtUserName = (EditText) findViewById(R.id.edt_user_name);
        edtPassword = (EditText) findViewById(R.id.edt_password);



       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        btnLogin = (Button) findViewById(R.id.button);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        GetUserJson g=new GetUserJson();
        g.execute();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_name = edtUserName.getText().toString().trim();
                password = edtPassword.getText().toString().trim();

                if (user_name.length() > 0 && password.length() >0){
                    isValidUser(user_name,password);
                }else {
                    Toast.makeText(LoginActivity.this, "Please Enter Your User Name and Password", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

   /* private void loadJson() {

        String API_BASE_URL = "http://localhost:6054/api/values/GetUserId?user_name=sal_sal&password=123/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(*//*"http://localhost:6054/api/values/GetUserId?user_name=sal_sal&password=123/"*//* API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JsonResponse> call = request.getJSON();
        call.enqueue(new Callback<JsonResponse>() {
            @Override
            public void onResponse(Call<JsonResponse> call, Response<JsonResponse> response) {

                String hy = String.valueOf(response.body());
                Toast.makeText(LoginActivity.this, ""+ hy, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonResponse> call, Throwable t) {

            }
        });
    }*/

    public  void isValidUser(final String user_name, final String password){

        String JSON_URL = "http://10.0.2.2:8080/api/values/GetUserId?user_name=" + user_name + "&password="+ password ;
      //  String JSON_URL = "192.168.0.1:8080/api/values/GetUserId?user_name=" + user_name + "&password="+ password ;

      //  Log.d("shuvoUrl",JSON_URL);

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // showJSON(response);
                        if (response.equals("true")){

                            editor.putString("user_name", user_name);
                            editor.putString("password", password);
                           /* editor.putInt("country_id", holderUser.countryId);
                            editor.putInt("user_id",holderUser.userId);*/
                            editor.commit();


                            startActivity(new Intent(LoginActivity.this, MainPageActivity.class));


                        }else {
                            Toast.makeText(LoginActivity.this, "Please Provide a valid user name and password", Toast.LENGTH_SHORT).show();
                        }

                      //  Log.e("ShuvoResponse",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,"shuvo :"+ error.getMessage(),Toast.LENGTH_LONG).show();
                       // Log.e("Shuvo",error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private class GetUserJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
         //   spinner.setVisibility(View.GONE);
           // new SyncCountry(LoginActivity.this).startDownLoad();
            GetCountryJson g=new GetCountryJson();
            g.execute();

        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getUserApiLink(3));
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
               // Log.e("ShuvoQuestion", "get Question" + holderUser.email);
            }

        }
    }

    private class GetCountryJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
           // new SyncSurvey(context).startDownLoad();

            GetSurveyJson g = new GetSurveyJson();
            g.execute();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getCountryApiLink());
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

    private class GetSurveyJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
           // new SyncQuestion(context).startDownLoad();
            GetQuestionJson g = new GetQuestionJson();
            g.execute();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getSurveyApiLink(1002));
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
    }

    private class GetQuestionJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            GetQuestionTypeJson g = new GetQuestionTypeJson();
            g.execute();

            spinner.setVisibility(View.GONE);
          //  new SyncQuestionType(context).startDownLoad();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getQuestionApiLink());
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
                int id = arrayElement.getInt("Question_id");
                String question = arrayElement.getString("Question");
                int questionTypeID = arrayElement.getInt("QT_id");
                int serveyId = arrayElement.getInt("Survey_id");

              //  Log.e("showData",question);
                HolderQuestion holderQuestion = new HolderQuestion(id,questionTypeID,serveyId,question);
                dbHelper.insertQuestion(holderQuestion);
//                Log.e("PD DEBUG","Insert song success: "+title+" "+album+" "+artist+" "+photo+" >> "+ret);
              //  Log.e("showData",holderQuestion.toString());
               //// Log.e("ShuvoQuestion", "get Question" + holderQuestion.question);
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
            GetSkipJson g = new GetSkipJson();
            g.execute();
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

    private class GetSkipJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            GetAnswerModeJson g = new GetAnswerModeJson();
            g.execute();

           // new SyncAnswerMode(context).startDownLoad();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getSkipApiLink());
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
                int skipId = arrayElement.getInt("Skip_id");
                int surveyId = arrayElement.getInt("Skip_ques_id");
                int questionId = arrayElement.getInt("Survey_id");
                int answerId = arrayElement.getInt("Question_id");
                int skipQuestionId = arrayElement.getInt("Answer_id");


                // Log.e("showData",question);
                HolderSkip holderSkip = new HolderSkip(skipId,surveyId,questionId,answerId,skipQuestionId);
                dbHelper.insertSkip(holderSkip);
//                Log.e("PD DEBUG","Insert song success: "+title+" "+album+" "+artist+" "+photo+" >> "+ret);
                // Log.e("showData",holderQuestion.toString());
                //  Log.e("ShuvoQuestion", "get Question" + holderUser.email);
            }

        }
    }

    private class GetAnswerModeJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            GetSectorJson g = new GetSectorJson();
            g.execute();

           // new SyncSector(context).startDownLoad();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getAnswerModeApiLink());
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
                int surveyId = arrayElement.getInt("Survey_Id");
                int questionId = arrayElement.getInt("Question_Id");
                int qtId = arrayElement.getInt("QT_Id");
                String hint = arrayElement.getString("Hint");
                int minValue = arrayElement.getInt("Min_Value");
                int maxValue = arrayElement.getInt("Max_Value");
                int showOrHide = arrayElement.getInt("ShowOrHide");


                // Log.e("showData",question);
                HolderAnswerMode holderAnswerMode = new HolderAnswerMode(minValue,maxValue,surveyId,questionId,qtId,hint,showOrHide);
                dbHelper.insertAnswerMode(holderAnswerMode);
//                Log.e("PD DEBUG","Insert song success: "+title+" "+album+" "+artist+" "+photo+" >> "+ret);
                // Log.e("showData",holderQuestion.toString());
                //  Log.e("ShuvoQuestion", "get Question" + holderUser.email);
            }

        }
    }

    private class GetSectorJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            GetUserAccessJson g = new GetUserAccessJson();
            g.execute();
           // new SyncUserAccess(context).startDownLoad();
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getSectorTypeApiLink());
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
                int sectorId = arrayElement.getInt("Sector_id");
                String sectorName = arrayElement.getString("Sector_name");

                // Log.e("showData",question);
                HolderSector holderSector = new HolderSector(sectorId,sectorName);
                dbHelper.insertSector(holderSector);
//                Log.e("PD DEBUG","Insert song success: "+title+" "+album+" "+artist+" "+photo+" >> "+ret);
                // Log.e("showData",holderQuestion.toString());
                //  Log.e("ShuvoQuestion", "get Question" + holderUser.email);
            }

        }
    }

    private class GetUserAccessJson extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

          //  spinner.setVisibility(View.GONE);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            String jsonResult = "";
            try {
                URL requestUrl = new URL(ApiUrlGenerator.getUserAccessApiLink());
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
                int userId = arrayElement.getInt("User_id");
                int countryId = arrayElement.getInt("Country_id");
                int sectorId = arrayElement.getInt("Sector_id");


                // Log.e("showData",question);
                HolderUserAccess holderUserAccess = new HolderUserAccess(userId,countryId,sectorId);
                dbHelper.insertUserAccess(holderUserAccess);
//                Log.e("PD DEBUG","Insert song success: "+title+" "+album+" "+artist+" "+photo+" >> "+ret);
                // Log.e("showData",holderQuestion.toString());
                //  Log.e("ShuvoQuestion", "get Question" + holderUser.email);
            }

        }
    }

}
