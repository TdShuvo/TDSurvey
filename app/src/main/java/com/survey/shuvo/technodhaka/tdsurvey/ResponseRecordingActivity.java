package com.survey.shuvo.technodhaka.tdsurvey;

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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.DbHelper;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderAnswer;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestion;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestionType;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderUser;
import com.survey.shuvo.technodhaka.tdsurvey.controller.AppController;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static com.survey.shuvo.technodhaka.tdsurvey.activity.LoginActivity.SURVEY_USER;

public class ResponseRecordingActivity extends AppCompatActivity {

    DbHelper dbHelper;

    public static final String TEXT = "Text";
    public static final String NUMBER = "Number";
    public static final String Date = "Date";
    public static final String Date_and_Time = "Datetime";
    public static final String SELECT_ONE = "selectOne";
    public static final String SELECT_MULTIPLE = "selectMultiple";
    public static final String DECIMAL = "decimal";
    public static final String TIME = "time";
    public static final String GPS = "gps";
    public static final String PHOTO = "photo";
    public static final String AUDIO = "audio";
    public static final String VIDEO = "video";
    public static final String NOTE = "note";
    public static final String BARCODE = "barcode";
    public static final String ACKNOWLEDGE = "acknowledge";
    public static final String CALCULATE = "calculate";
    public static final String MATRIX = "matrix";
    public static final String RANKING = "ranking";


    Button btnPreQuestion, btnNextQuestion;
    TextView txtQuestion, txtDate, txtTime, txtDataAndTime, txtGps;
    EditText edtText, edtNumber, edtDecimal;
    int totalQuestionOfASurvey;

    int mQusIndex = 14;
    int surveyID;
    HolderUser holderUser;
    HolderAnswer holderAnswer;

    String answerResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_recording);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();

        if (b != null) {
            surveyID = b.getInt("surveyID");
        }

        String user_name = null, password = null;
        SharedPreferences prefs = getSharedPreferences(SURVEY_USER, MODE_PRIVATE);


        user_name = prefs.getString("user_name", null);
        password = prefs.getString("password", null);


        holderUser = dbHelper.getUserInfo(user_name, password);
        // holderAnswer = new HolderAnswer(2,15,18,5,5,3,"Hello test from android.");

        init();
        showFirstQuestion();

        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // answerResponse=loadResponseView().get
                if (answerResponse != null) {
                    Toast.makeText(ResponseRecordingActivity.this, "Give Your response.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ++mQusIndex;
                if (mQusIndex <= totalQuestionOfASurvey)
                    loadQuestion(mQusIndex, surveyID);
                else if (mQusIndex > totalQuestionOfASurvey)
                    mQusIndex = totalQuestionOfASurvey;
                getQuestionType();

                //if ()

                //   new SendPostRequest().execute();

            }
        });

        btnPreQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --mQusIndex;
                if (mQusIndex >= 1)
                    loadQuestion(mQusIndex, surveyID);
                else if (mQusIndex < 1)
                    mQusIndex = 0;
            }
        });

    }

    private void showFirstQuestion() {
        mQusIndex = 1;
        loadQuestion(mQusIndex, surveyID);
    }

    public void loadQuestion(int id, int surveyID) {
        //  Log.e("shuvogivenId", String.valueOf(id));
        // List<HolderQuestion> question = dbHelper.getQuestion("1", mQusIndex - 1);
        List<HolderQuestion> question = dbHelper.getQuestionsForSurveyId(surveyID, mQusIndex - 1);
        //  Log.e("shuvoQuestion",question.question);
        if (question.size() > 0) {
            for (int i = 0; i < question.size(); i++) {
                HolderQuestion holderQuestion = question.get(i);
                answerResponse = loadResponseView(holderQuestion.questionTypeId);
                //holderQuestion.
                //   loadAnswer(holderQuestion.questionId,holderQuestion.questionTypeId,1,holderQuestion.surveyId);
                // Log.e("QuestionType", String.valueOf(question.get(i).questionTypeId));
                txtQuestion.setText(question.get(i).question);

            }
        }


       /* ArrayList<HolderQuestion> quesSet = dbHelper.getQuestion();
        quesSet.size();
        Log.e("ds",quesSet.toString());
*/
    }

    public String loadResponseView(int qt_id) {

        String userResponse = null;
        switch (qt_id) {
            case 1:
                showOneView(edtText);
                userResponse = String.valueOf(edtText.getText());
                break;

            case 2:
                showOneView(edtText);
                userResponse = String.valueOf(edtText.getText());
                break;
            case 3:
                showOneView(edtText);
                userResponse = String.valueOf(edtText.getText());
                break;
            case 4:
                showOneView(edtNumber);
                userResponse = String.valueOf(edtNumber.getText());
                break;
            case 5:
                showOneView(edtDecimal);
                userResponse = String.valueOf(edtDecimal.getText());
                break;
            case 6:
                showOneView(txtDate);
                userResponse = String.valueOf(txtDate.getText());
                break;
            case 7:
                showOneView(txtTime);
                userResponse = String.valueOf(txtTime.getText());
                break;
            case 8:
                showOneView(txtDataAndTime);
                userResponse = String.valueOf(txtDataAndTime.getText());
                break;
            case 9:
                showOneView(txtGps);
                userResponse = String.valueOf(txtGps.getText());
                break;
            default:
                return userResponse;

        }
        return userResponse;
    }


    public void loadAnswer(int questionID, int qt_id, int sequenceId, int surveyId) {
        String response = null;
        String user_name = null, password = null;
        SharedPreferences prefs = getSharedPreferences(SURVEY_USER, MODE_PRIVATE);

        user_name = prefs.getString("user_name", null);
        password = prefs.getString("password", null);

        HolderUser holderUser = dbHelper.getUserInfo(user_name, password);


        // Log.e("SharedPrefer", String.valueOf(userId)+ String.valueOf(country_id));
        //  HolderUser holderUser = dbHelper.getUser();

        switch (qt_id) {

            case 1:
                //showOneView(edtText);
                response = edtText.getText().toString().trim();
                if (response != null) {
                    dbHelper.insertAnswer(new HolderAnswer(holderUser.userId, questionID, holderUser.countryId, qt_id, sequenceId, surveyId, response));
                } else Toast.makeText(this, "Please Give Your response", Toast.LENGTH_SHORT).show();
                edtText.setText("");
                break;
            case 2: //showOneView(edtText);
                response = edtText.getText().toString().trim();
                if (response != null) {
                    dbHelper.insertAnswer(new HolderAnswer(holderUser.userId, questionID, holderUser.countryId, qt_id, sequenceId, surveyId, response));
                } else Toast.makeText(this, "Please Give Your response", Toast.LENGTH_SHORT).show();
                edtText.setText("");
                break;
            case 3: //showOneView(edtText);
                response = edtText.getText().toString().trim();
                if (response != null) {
                    dbHelper.insertAnswer(new HolderAnswer(holderUser.userId, questionID, holderUser.countryId, qt_id, sequenceId, surveyId, response));
                } else Toast.makeText(this, "Please Give Your response", Toast.LENGTH_SHORT).show();
                edtText.setText("");
                break;
            case 4:// showOneView(edtNumber);
                response = edtNumber.getText().toString().trim();
                if (response != null) {
                    dbHelper.insertAnswer(new HolderAnswer(holderUser.userId, questionID, holderUser.countryId, qt_id, sequenceId, surveyId, response));
                } else Toast.makeText(this, "Please Give Your response", Toast.LENGTH_SHORT).show();
                edtNumber.setText("");
                break;
            case 5: //showOneView(edtDecimal);
                response = edtDecimal.getText().toString().trim();
                if (response != null) {
                    dbHelper.insertAnswer(new HolderAnswer(holderUser.userId, questionID, holderUser.countryId, qt_id, sequenceId, surveyId, response));
                } else Toast.makeText(this, "Please Give Your response", Toast.LENGTH_SHORT).show();
                edtDecimal.setText("");
                break;
          /* case 6: //showOneView(txtDate);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               break;
           case 7: //showOneView(txtTime);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               break;
           case 8: //showOneView(txtDataAndTime);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               break;
           case 9: //showOneView(txtGps);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               break;*/
            default: //showOneView(edtText);
                response = edtText.getText().toString().trim();
                if (response != null)
                    dbHelper.insertAnswer(new HolderAnswer(holderUser.userId, questionID, holderUser.countryId, qt_id, sequenceId, surveyId, response));
                edtText.setText("");
        }


    }

    public void showOneView(View view) {
        hideView();
        view.setVisibility(View.VISIBLE);
    }

    public void hideView() {
        txtDataAndTime.setVisibility(View.GONE);
        txtDate.setVisibility(View.GONE);
        txtTime.setVisibility(View.GONE);
        txtGps.setVisibility(View.GONE);
        // txtQuestion.setVisibility(View.GONE);

        edtText.setVisibility(View.GONE);
        edtNumber.setVisibility(View.GONE);
        edtDecimal.setVisibility(View.GONE);
    }

    public void init() {
        btnNextQuestion = (Button) findViewById(R.id.btn_dt_next);
        btnPreQuestion = (Button) findViewById(R.id.btn_dt_preview);

        txtQuestion = (TextView) findViewById(R.id.tv_DtQuestion);
        txtDate = (TextView) findViewById(R.id.txt_date_response);
        txtGps = (TextView) findViewById(R.id.txt_gps_response);
        txtTime = (TextView) findViewById(R.id.txt_time_response);
        txtDataAndTime = (TextView) findViewById(R.id.txt_date_and_time_response);

        edtDecimal = (EditText) findViewById(R.id.edt_decimal_response);
        edtNumber = (EditText) findViewById(R.id.edt_number_response);
        edtText = (EditText) findViewById(R.id.edt_text_response);


        totalQuestionOfASurvey = dbHelper.getQuestion().size();

        // Log.e("totalCount", String.valueOf(totalQuestionOfASurvey));
    }

    public void getQuestionType() {
        ArrayList<HolderQuestionType> qtArray = dbHelper.getQuestionType();
        for (int i = 0; i < qtArray.size(); i++) {
            //   qtArray.get(i).questionTypeName;
        }
        // Log.e("qtArray",qtArray.toString());
    }

  /*  public String sendAnswer(HolderAnswer holderAnswer){
        String query = "INSERT INTO [dbo].[Answer] VALUES (" + holderAnswer.answerId +","+ holderAnswer.answer+","+ holderAnswer.userId+","+
                holderAnswer.countryId+","+ holderAnswer.questionId +","+ holderAnswer.questionTypeId +","+ holderAnswer.secquenceId +");";


      *//*  INSERT INTO [dbo].[Answer]
        VALUES (answerid, "Answer", userId,countryId,questionId,qt_id,secquence_id);*//*

        return  query;
    }*/

  /*  private void saveProcessValidation() {

        int i = 0;
        String responseControl = mDTQResMode.getDtResponseValueControl();


        if (mDTQ.getAllowNullFlag().equals("N")) {

            switch (responseControl) {
                case TEXT:

                    String edtInput = dt_edt.getText().toString();

                    if (edtInput.equals("Text") || edtInput.equals("Number") || edtInput.length() == 0) {
                        errorIndicator();
                        displayError("Insert  Text");
                    } else {


                        //  if DTA type is number then it gonna check the max & min Value
                        if (mDTATableList.get(0).getDataType().equals("Number")) {
//                            normalIndicator();
//                            saveData(edtInput, "", mDTATableList.get(0));
//
//                            // load next Question
//                            nextQuestion();
                            // comparing the highest input value an  lowest value
                            if ((Double.parseDouble(edtInput) <= Parse.StringToDoubleNullCheck(mDTATableList.get(0).getMaxValue())) && (Double.parseDouble(edtInput) >= Parse.StringToDoubleNullCheck(mDTATableList.get(0).getMinValue()))) {
                                normalIndicator();
                                saveData(edtInput, "", mDTATableList.get(0));

                                // load next Question
                                nextQuestion();
                            } else {
                                errorIndicator();
                                displayError("Out of range input! ");
                            }
                        } else {  // else the input method would  be the text
                            normalIndicator();
                            saveData(edtInput, "", mDTATableList.get(0));
                            // load next Question
                            nextQuestion();
                        }


                    }

                    break;
                case Date_OR_Time:

                    if (_dt_tv_DatePicker.getText().toString().equals("Click for Date")) {
                        errorIndicator();
                        displayError("Set Date First");

                    } else {
                        normalIndicator();

                        *                         * mDTATableList.get(0) wil be single
                        saveData(_dt_tv_DatePicker.getText().toString(), "", mDTATableList.get(0));
                        nextQuestion();
                    }
                    break;
                case COMBO_BOX:
                    * here it get null point reference if spinner get no values
                    if (idSpinner != null) {
                        if (idSpinner.equals("00")) {

                            errorIndicator();
                            displayError("Select Item");

                        } else {
                            normalIndicator();
                            *                             * {@link DTResponseRecordingActivity#saveData(String, DT_ATableDataModel)}
                            saveData(strSpinner, "", mDTATableList.get(0));

                            * load   NEXT QUESTION
                            nextQuestion();
                        }
                    }
                    break;
                case CHECK_BOX:

                    if (countChecked <= 0) {
                        errorIndicator();
                        displayError("Select a option.");

                    } else {
                        normalIndicator();

                        i = 0;
                        for (CheckBox cb : mCheckBox_List) {
                            if (cb.isChecked()) {

                                saveData("", "", mDTATableList.get(i));
                            }// end of if condition
                            i++;
                        }// end of for each loop
                    }// end of else

                    nextQuestion();
                    break;

                case RADIO_BUTTON:
                    i = 0;
                    for (RadioButton rb : mRadioButton_List) {
                        if (rb.isChecked()) {

                            saveData("", "", mDTATableList.get(i));
                        }
                        i++;
                    }
                    nextQuestion();
                    break;


                case RADIO_BUTTON_N_TEXTBOX:

                    boolean error = false;

                    i = 0;
                    for (RadioButton rb : mRadioButtonForRadioAndEdit_List) {
                        if (rb.isChecked()) {

                            if (mEditTextForRadioAndEdit_List.get(i).getText().length() == 0) {
                                errorIndicator();
                                displayError("Insert value for Selected Option");
                                error = true;
                                break;

                            } else {
                                normalIndicator();

                                saveData(mEditTextForRadioAndEdit_List.get(i).getText().toString(), "", mDTATableList.get(i));
                            }
                        }
                        i++;    //increment
                    }

                    if (!error)
                        nextQuestion();
                    break;

                case CHECKBOX_N_TEXTBOX:


                    normalIndicator();
                    int k = 0;
                    for (CheckBox cb : mCheckBoxForCheckBoxAndEdit_List) {
                        if (cb.isChecked()) {
                            Toast.makeText(mContext, "Radio Button no:" + (k + 1) + " is checked"
                                    + " the value of the : " + mEditTextForCheckBoxAndEdit_List.get(k).getText(), Toast.LENGTH_SHORT).show();
                            if (mEditTextForCheckBoxAndEdit_List.get(k).getText().length() == 0) {
                                errorIndicator();
                                displayError("Insert value for Selected Option");
                                break;
                            } else {
                                normalIndicator();
                                saveData(mEditTextForCheckBoxAndEdit_List.get(k).getText().toString(), "", mDTATableList.get(k));
                            }


                        }
                        k++;
                    }
                    nextQuestion();
                    break;
                case PHOTO:

                    if (getImageString() == null) {
                        errorIndicator();
                        displayError("Insert  Image");
                    } else {

                        saveData("", getImageString(), mDTATableList.get(i));
                        normalIndicator();
                        nextQuestion();

                    }
                    break;


            }// end of switch

        }// end of the AllowNullFlag
        else {


            *
             *  TODO: 9/29/2016  save method & update method

//                    saveData("");


            //NEXT QUESTION
            nextQuestion();


        }// end of else where ans is not magneto


    }*/
}
