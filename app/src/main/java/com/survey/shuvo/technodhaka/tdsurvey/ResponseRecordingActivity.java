package com.survey.shuvo.technodhaka.tdsurvey;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.DbHelper;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderAnswer;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestion;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderQuestionType;
import com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey.HolderUser;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_recording);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        init();
        showFirstQuestion();

        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++mQusIndex;
                if (mQusIndex <= totalQuestionOfASurvey)
                    loadQuestion(mQusIndex);
                else if (mQusIndex > totalQuestionOfASurvey)
                    mQusIndex = totalQuestionOfASurvey;
                getQuestionType();
            }
        });

        btnPreQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --mQusIndex;
                if (mQusIndex >= 1)
                    loadQuestion(mQusIndex);
                else if (mQusIndex < 1)
                    mQusIndex = 0;
            }
        });

    }

    private void showFirstQuestion() {
        mQusIndex = 1;
        loadQuestion(mQusIndex);
    }

    public void loadQuestion(int id) {
        Log.e("shuvogivenId", String.valueOf(id));
        List<HolderQuestion> question = dbHelper.getQuestion("1", mQusIndex - 1);
        //  Log.e("shuvoQuestion",question.question);
        if (question.size()>0){
            for(int i =0;i<question.size();i++){
                HolderQuestion holderQuestion = question.get(i);
                //holderQuestion.
                loadAnswer(holderQuestion.questionId,holderQuestion.questionTypeId,1,holderQuestion.surveyId);
               // Log.e("QuestionType", String.valueOf(question.get(i).questionTypeId));
                txtQuestion.setText(question.get(i).question);

            }
        }


       /* ArrayList<HolderQuestion> quesSet = dbHelper.getQuestion();
        quesSet.size();
        Log.e("ds",quesSet.toString());
*/
    }

    public void loadAnswer(int questionID,int qt_id, int sequenceId, int surveyId){
        String response = null;
        String user_name = null, password = null;
        SharedPreferences prefs = getSharedPreferences(SURVEY_USER, MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText != null) {

            user_name = prefs.getString("user_name", null);
            password = prefs.getString("password", null);
        }

        HolderUser holderUser = dbHelper.getUserInfo(user_name,password);

       // Log.e("SharedPrefer", String.valueOf(userId)+ String.valueOf(country_id));
      //  HolderUser holderUser = dbHelper.getUser();
        HolderAnswer holderAnswer ;
       switch (qt_id){

           case 1:
               showOneView(edtText);
               response = edtText.getText().toString().trim();
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               edtText.setText("");
               break;
           case 2: showOneView(edtText);
               response = edtText.getText().toString().trim();
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               edtText.setText("");
               break;
           case 3: showOneView(edtText);
               response = edtText.getText().toString().trim();
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               edtText.setText("");
               break;
           case 4: showOneView(edtNumber);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               break;
           case 5: showOneView(edtDecimal);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               break;
           case 6: showOneView(txtDate);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               break;
           case 7: showOneView(txtTime);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               break;
           case 8: showOneView(txtDataAndTime);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               break;
           case 9: showOneView(txtGps);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
               break;
           default: showOneView(edtText);
               dbHelper.insertAnswer( new HolderAnswer(holderUser.userId,questionID,holderUser.countryId,qt_id,sequenceId,surveyId,response));
       }


    }

    public void showOneView(View view){
        hideView();
        view.setVisibility(View.VISIBLE);
    }

    public void hideView(){
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

    public void getQuestionType(){
       ArrayList<HolderQuestionType> qtArray = dbHelper.getQuestionType();
        for(int i =0;i<qtArray.size();i++){
         //   qtArray.get(i).questionTypeName;
        }
       // Log.e("qtArray",qtArray.toString());
    }

    public String sendAnswer(HolderAnswer holderAnswer){
        String query = "INSERT INTO [dbo].[Answer] VALUES (" + holderAnswer.answerId +","+ holderAnswer.answer+","+ holderAnswer.userId+","+
                holderAnswer.countryId+","+ holderAnswer.questionId +","+ holderAnswer.questionTypeId +","+ holderAnswer.secquenceId +");";


      /*  INSERT INTO [dbo].[Answer]
        VALUES (answerid, "Answer", userId,countryId,questionId,qt_id,secquence_id);*/

        return  query;
    }

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
