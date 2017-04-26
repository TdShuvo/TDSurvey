package com.survey.shuvo.technodhaka.tdsurvey.activity;

import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.DbHelper;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderAnswer;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderSurvey;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderUser;
import com.survey.shuvo.technodhaka.tdsurvey.R;
import com.survey.shuvo.technodhaka.tdsurvey.ResponseRecordingActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.survey.shuvo.technodhaka.tdsurvey.activity.LoginActivity.SURVEY_USER;

public class SurevyListAnswerActivity extends AppCompatActivity {

    Context context;
    DbHelper dbHelper;
    ListView listView;
    ArrayList<HolderSurvey> ListData;
    SurevyListAnswerActivity.PopulateSurveyData surveyAdapter;
    ProgressBar pb;
    String user_name, password;
    HolderUser holderUser;
    HolderSurvey hs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surevy_list_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pb = new ProgressBar(SurevyListAnswerActivity.this, null, android.R.attr.progressBarStyleHorizontal);
        pb.setVisibility(View.GONE);
        init();

        surveyAdapter = new SurevyListAnswerActivity.PopulateSurveyData();


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



                List<HolderAnswer> Answer = dbHelper.getAnswerForSurveyId(hs.surveyId);
                if (Answer.size() == 0){
                    Toast.makeText(context, "You do not Complete any survey.", Toast.LENGTH_SHORT).show();
                }
             //   ArrayList<HolderAnswer> holderA = new ArrayList<HolderAnswer>();
                for(int i =0;i<Answer.size();i++){
                    HolderAnswer holderAnswer = Answer.get(i);
                 //;  holderA.add(holderAnswer);

                    new SendPostRequest(holderAnswer).execute();
                   // new SendPostRequest(holderAnswer).execute(holderAnswer);
                }

              /*  Intent intent = new Intent(GetSurveyActivity.this, ResponseRecordingActivity.class);
                intent.putExtra("surveyID",hs.surveyId);
                startActivity(intent);*/
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
        listView = (ListView) findViewById(R.id.lst_answer_survey);
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

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        HolderAnswer holderAnswer;

        public SendPostRequest(HolderAnswer holderAnswer) {
            this.holderAnswer = holderAnswer;
        }

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {



            try {
                String request = "http://10.0.2.2:8080/api/values/putAnswer/";
                URL url = new URL(request); // here is your URL path

                JSONObject answer = new JSONObject();

                answer.put("answer",holderAnswer.answer);// Set the parameter and the
                answer.put("user_id", String.valueOf(holderAnswer.userId));
                answer.put("country_id", String.valueOf(holderAnswer.countryId));
                answer.put("question_id", String.valueOf(holderAnswer.questionId));
                answer.put("qt_id", String.valueOf(holderAnswer.questionTypeId));
                answer.put("scequence_id", String.valueOf(holderAnswer.secquenceId));
                //Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000   );
                conn.setConnectTimeout(15000  );
                conn.setDoInput(false);
                // conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                //  conn.setRequestMethod("putAnswer");
                // conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Type", "application/json");
                // conn.setRequestProperty("charset", "utf-8");
                conn.setUseCaches (false);



                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(String.valueOf(answer));
                // writer.write(getPostDataString(answer));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

    /*    protected HolderAnswer doInBackground(HolderAnswer... holderAnswer) {

            HolderAnswer holAnswer = holderAnswer[0];

            try {
                String request = "http://10.0.2.2:8080/api/values/putAnswer/";
                URL url = new URL(request); // here is your URL path

                JSONObject answer = new JSONObject();

                answer.put("answer",holAnswer.answer);// Set the parameter and the
                answer.put("user_id", String.valueOf(holAnswer.userId));
                answer.put("country_id", String.valueOf(holAnswer.countryId));
                answer.put("question_id", String.valueOf(holAnswer.questionId));
                answer.put("qt_id", String.valueOf(holAnswer.questionTypeId));
                answer.put("scequence_id", String.valueOf(holAnswer.secquenceId));
                //Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000   );
                conn.setConnectTimeout(15000  );
                conn.setDoInput(false);
                // conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                //  conn.setRequestMethod("putAnswer");
                // conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Type", "application/json");
                // conn.setRequestProperty("charset", "utf-8");
                conn.setUseCaches (false);



                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(String.valueOf(answer));
                // writer.write(getPostDataString(answer));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                   // return holAnswer.toString();
                    return holAnswer;

                }
                else {
                   // return new String("false : "+responseCode);
                    return holAnswer;
                }
            }
            catch(Exception e){
                //return new String("Exception: " + e.getMessage());
                return holAnswer;
            }

        }*/

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Shuvo"+result,
                    Toast.LENGTH_LONG).show();

            Log.e("ShuvoError",result);
        }
    }
}
