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


import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.DbHelper;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderAnswer;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestion;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestionType;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderUser;

import java.util.ArrayList;
import java.util.List;

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

    String visibleView;

    int mQusIndex;
    int sequenceId;
    int surveyID;
    HolderUser holderUser;
    HolderAnswer holderAnswer;
    HolderQuestion holderQuestion;


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
                //  answerResponse = loadResponseView(mQusIndex);
               String response = edtText.getText().toString().trim();
                sequenceId = dbHelper.getSequenceId(holderQuestion.questionId,surveyID);
                Log.e("ShuvoQuestionId", String.valueOf(holderQuestion.questionId));
                if (sequenceId == 0){
                    sequenceId = 1;
                }else{
                    sequenceId++;
                }
                answerResponse = response;
                if (answerResponse.isEmpty()) {
                    Log.e("MOR",answerResponse);
                    Toast.makeText(ResponseRecordingActivity.this, "Give Your response."+ answerResponse, Toast.LENGTH_SHORT).show();
                    return;
                }

                ++mQusIndex;
                if (mQusIndex <= totalQuestionOfASurvey) {
                    insertAnswer(holderQuestion.questionId, holderQuestion.questionTypeId, sequenceId, holderQuestion.surveyId);
                    loadQuestion(mQusIndex, surveyID);


                    Log.e("ShuvoQuestionIndex", String.valueOf(mQusIndex));

                }
                else if (mQusIndex > totalQuestionOfASurvey) {
                    mQusIndex = totalQuestionOfASurvey;
                    sequenceId++;
                }


            }
        });

        btnPreQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --mQusIndex;
                if (mQusIndex >= 1){
                    loadQuestion(mQusIndex, surveyID);
                    String answer = loadAnswer(holderQuestion.questionId,surveyID,sequenceId);
                    loadResponseView(holderQuestion.questionTypeId,answer);
                }
                else mQusIndex = 0;
            }
        });

    }

    private void showFirstQuestion() {
        mQusIndex = 1;
        loadQuestion(mQusIndex, surveyID);
    }

    public void loadQuestion(int id, int surveyID) {


        List<HolderQuestion> questionList = dbHelper.getQuestionsForSurveyId(surveyID, id - 1);
        if (questionList != null && questionList.size() > 0) {
                holderQuestion = questionList.get(0);
               visibleView =  loadResponseView(holderQuestion.questionTypeId,null);

              // insertAnswer(holderQuestion.questionId, holderQuestion.questionTypeId, 1, holderQuestion.surveyId);
                txtQuestion.setText(questionList.get(0).question);

        }

    }

    public String loadResponseView(int qt_id,String answer) {

        String userResponse = null;
        switch (qt_id) {
            case 1:
                showOneView(edtText);
                if (answer != null){
                    edtText.setText(answer);
                }
                userResponse = String.valueOf(edtText.getText());
                break;
            case 2:
                showOneView(edtText);
                if (answer != null){
                    edtText.setText(answer);
                }
                userResponse = String.valueOf(edtText.getText());
                break;
            case 3:
                showOneView(edtText);
                if (answer != null){
                    edtText.setText(answer);
                }
                userResponse = String.valueOf(edtText.getText());
                break;
            case 4:
                showOneView(edtNumber);
                if (answer != null){
                    edtNumber.setText(answer);
                }
                userResponse = String.valueOf(edtNumber.getText());
                break;
            case 5:
                showOneView(edtDecimal);
                if (answer != null){
                    edtDecimal.setText(answer);
                }
                userResponse = String.valueOf(edtDecimal.getText());
                break;
            case 6:
                showOneView(txtDate);
                if (answer != null){
                    txtDate.setText(answer);
                }
                userResponse = String.valueOf(txtDate.getText());
                break;
            case 7:
                showOneView(txtTime);
                if (answer != null){
                    txtTime.setText(answer);
                }
                userResponse = String.valueOf(txtTime.getText());
                break;
            case 8:
                showOneView(txtDataAndTime);
                if (answer != null){
                    txtDataAndTime.setText(answer);
                }
                userResponse = String.valueOf(txtDataAndTime.getText());
                break;
            case 9:
                showOneView(txtGps);
                if (answer != null){
                    txtGps.setText(answer);
                }
                userResponse = String.valueOf(txtGps.getText());
                break;
            default:
                return userResponse;

        }
        return userResponse;
    }


    public void insertAnswer(int questionID, int qt_id, int sequenceId, int surveyId) {
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
                answerResponse = response;
                if (!response.isEmpty()) {
                    dbHelper.insertAnswer(new HolderAnswer(holderUser.userId, questionID, holderUser.countryId, qt_id, sequenceId, surveyId, response));
                } else Toast.makeText(this, "Please Give Your response", Toast.LENGTH_SHORT).show();
                edtText.setText("");
                break;
            case 2: //showOneView(edtText);
                response = edtText.getText().toString().trim();
                answerResponse = response;
                if (response != null) {
                    dbHelper.insertAnswer(new HolderAnswer(holderUser.userId, questionID, holderUser.countryId, qt_id, sequenceId, surveyId, response));
                } else Toast.makeText(this, "Please Give Your response", Toast.LENGTH_SHORT).show();
                edtText.setText("");
                break;
            case 3: //showOneView(edtText);
                response = edtText.getText().toString().trim();
                answerResponse = response;
                if (!response.isEmpty()) {
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


    public String loadAnswer(int questionId, int surveyId,int sequenceId){
        String answer =  dbHelper.getAnswerForQuesId(questionId,surveyId,sequenceId);
        return answer;
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
        String qt;
        for (int i = 0; i < qtArray.size(); i++) {
            qt =   qtArray.get(i).questionTypeName;
            Log.e("ShuvoQT",qt);
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


              TODO: 9/29/2016  save method & update method

//                    saveData("");


            //NEXT QUESTION
            nextQuestion();


        }// end of else where ans is not magneto


    }*/
}
