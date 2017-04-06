package com.survey.shuvo.technodhaka.tdsurvey.DbForSurvey;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;

/**
 * Created by TD02 on 3/18/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    Context context;
    private String myPath= "";
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "survey_td";

    private static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
    private static final String PRIMARY_KEY = "PRIMARY KEY";

    // TABLE NAME
    private final static String TABLE_USER = "table_user";
    private final static String TABLE_SECTOR = "table_sector";
    private final static String TABLE_QUESTION = "table_question";
    private final static String TABLE_ANSWER = "table_answer";
    private final static String TABLE_SKIP = "table_skip";
    private final static String TABLE_QUESTION_TYPE = "table_question_type";
    private final static String TABLE_COUNTRY = "table_country";
    private static final String TABLE_ANSWER_MODE = "table_answer_mode";
    private static final String TABLE_SURVEY = "table_survey";
    private static final String TABLE_USER_ACCESS = "table_user_access";


    //FIELD NAME
    private final static String USER_ID = "user_id";
    private final static String USER_NAME = "name";
    private final static String USER_PASSWORD = "password";
    private final static String USER_USER_NAME = "user_name";
    private final static String USER_EMAIL = "email";
    private final static String USER_GENDER = "gender";
    private final static String USER_COUNTRY_ID = "country_id";

    private final static String SECTOR_ID = "sector_id";
    private final static String SECTOR_NAME = "sector_name";

    private final static String QUESTION_TYPE_ID = "question_type_id";
    private final static String QUESTION_TYPE_NAME = "question_type_name";

    private final static String COUNTRY_ID = "country_id";
    private final static String COUNTRY_NAME = "country_name";

    private final static String ANSWER_MODE_MIN_VALUE = "answer_mode_min_value";
    private final static String ANSWER_MODE_MAX_VALUE = "answer_mode_max_value";
    private final static String ANSWER_MODE_SURVEY_ID = "answer_mode_survey_id";
    private final static String ANSWER_MODE_QUESTION_ID = "answer_mode_question_id";
    private final static String ANSWER_MODE_QUESTION_TYPE_ID = "answer_mode_question_type_id";
    private final static String ANSWER_MODE_HINT = "answer_mode_hint";
    private final static String ANSWER_MODE_SHOW_HIDE = "answer_mode_show_hide";


    private final static String SURVEY_ID = "survey_id";
    private final static String SURVEY_NAME = "survey_name";
    private final static String SURVEY_USER_ID = "survey_user_id";
    private final static String SURVEY_SECTOR_ID = "survey_sector_id";

    private final static String SKIP_ID = "skip_id";
    private final static String SKIP_SURVEY_ID = "skip_survey_id";
    private final static String SKIP_QUESTION_ID = "skip_question_id";
    private final static String SKIP_ANSWER_ID = "skip_answer_id";
    private final static String SKIP_SKIP_QUESTION_ID = "skip_skip_question_id";


    private final static String ANSWER_USER_ID = "answer_user_id";
    private final static String ANSWER_COUNTRY_ID = "answer_country_id";
    private final static String ANSWER_QUESTION_ID = "answer_question_id";
    private final static String ANSWER_ANSWER_ID = "answer_answer_id";
    private final static String ANSWER_QUESTION_TYPE_ID = "answer_question_type_id";
    private final static String ANSWER_SEQUENCE_ID = "answer_sequence_id";
    private final static String ANSWER_SURVEY_ID = "answer_survey_id";
    private final static String ANSWER = "answer";


    private final static String QUESTION_SURVEY_ID = "question_survey_id";
    private final static String QUESTION_QUESTION_TYPE_ID = "question_question_type_id";
    private final static String QUESTION_ID = "question_id";
    private final static String QUESTION = "question";

    private final static String USER_ACCESS_USER_ID = "user_access_user_id";
    private final static String USER_ACCESS_COUNTRY_ID = "user_access_country_id";
    private final static String USER_ACCESS_SECTOR_ID = "user_access_sector_id";


    //QUERY CREATE
    private final static String QUERY_CREATE_USER = CREATE_TABLE_IF_NOT_EXISTS + TABLE_USER + "("
            + USER_ID + " INTEGER PRIMARY KEY,"
            + USER_NAME + " TEXT,"
            + USER_PASSWORD + " TEXT,"
            + USER_USER_NAME + " TEXT,"
            + USER_EMAIL + " TEXT,"
            + USER_GENDER + " TEXT,"
            + USER_COUNTRY_ID + " INTEGER)";


    private final static String QUERY_CREATE_SECTOR = CREATE_TABLE_IF_NOT_EXISTS + TABLE_SECTOR + "("
            + SECTOR_ID + " INTEGER PRIMARY KEY,"
            + SECTOR_NAME + " TEXT)";


    private final static String QUERY_CREATE_QUESTION = CREATE_TABLE_IF_NOT_EXISTS + TABLE_QUESTION + "("
            + QUESTION_ID + " INTEGER PRIMARY KEY,"
            + QUESTION_QUESTION_TYPE_ID + " INTEGER,"
            + QUESTION_SURVEY_ID + " INTEGER,"
            + QUESTION + " TEXT)";

    private final static String QUERY_CREATE_ANSWER = CREATE_TABLE_IF_NOT_EXISTS + TABLE_ANSWER + "("
            + ANSWER_ANSWER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ANSWER_USER_ID + " INTEGER,"
            + ANSWER_COUNTRY_ID + " INTEGER,"
            + ANSWER_QUESTION_ID + " INTEGER,"
            + ANSWER_QUESTION_TYPE_ID + " INTEGER,"
            + ANSWER_SEQUENCE_ID + " INTEGER,"
            + ANSWER_SURVEY_ID + " INTEGER,"
            + ANSWER + " TEXT)";

    private final static String QUERY_CREATE_SKIP = CREATE_TABLE_IF_NOT_EXISTS + TABLE_SKIP + "("
            + SKIP_ID + " INTEGER PRIMARY KEY,"
            + SKIP_SURVEY_ID + " INTEGER,"
            + SKIP_QUESTION_ID + " INTEGER,"
            + SKIP_ANSWER_ID + " INTEGER,"
            + SKIP_SKIP_QUESTION_ID + " INTEGER)";


    private final static String QUERY_CREATE_QUESTION_TYPE = CREATE_TABLE_IF_NOT_EXISTS + TABLE_QUESTION_TYPE + "("
            + QUESTION_TYPE_ID + " INTEGER PRIMARY KEY,"
            + QUESTION_TYPE_NAME + " TEXT)";

    private final static String QUERY_CREATE_COUNTRY = CREATE_TABLE_IF_NOT_EXISTS + TABLE_COUNTRY + "("
            + COUNTRY_ID + " INTEGER PRIMARY KEY,"
            + COUNTRY_NAME + " TEXT)";

    private final static String QUERY_CREATE_ANSWER_MODE = CREATE_TABLE_IF_NOT_EXISTS + TABLE_ANSWER_MODE + "("
            + ANSWER_MODE_MIN_VALUE + " INTEGER,"
            + ANSWER_MODE_MAX_VALUE + " INTEGER,"
            + ANSWER_MODE_SURVEY_ID + " INTEGER,"
            + ANSWER_MODE_QUESTION_ID + " INTEGER,"
            + ANSWER_MODE_QUESTION_TYPE_ID + " INTEGER,"
            + ANSWER_MODE_HINT + " TEXT,"
            + ANSWER_MODE_SHOW_HIDE + " INTEGER)";


    private final static String QUERY_CREATE_SURVEY = CREATE_TABLE_IF_NOT_EXISTS + TABLE_SURVEY + "("
            + SURVEY_ID + " INTEGER PRIMARY KEY,"
            + SURVEY_SECTOR_ID + " INTEGER,"
            + SURVEY_USER_ID + " INTEGER,"
            + SURVEY_NAME + " TEXT)";

    private final static String QUERY_CREATE_USER_ACCESS = CREATE_TABLE_IF_NOT_EXISTS + TABLE_USER_ACCESS + "("
            + USER_ACCESS_USER_ID + " INTEGER,"
            + USER_ACCESS_COUNTRY_ID + " INTEGER,"
            + USER_ACCESS_SECTOR_ID + " INTEGER)";


    // QUERY DROP
    private final static String QUERY_DROP_USER = DROP_TABLE_IF_EXISTS + TABLE_USER;
    private final static String QUERY_DROP_SECTOR = DROP_TABLE_IF_EXISTS + TABLE_SECTOR;
    private final static String QUERY_DROP_QUESTION = DROP_TABLE_IF_EXISTS + TABLE_QUESTION;
    private final static String QUERY_DROP_ANSWER = DROP_TABLE_IF_EXISTS + TABLE_ANSWER;
    private final static String QUERY_DROP_SKIP = DROP_TABLE_IF_EXISTS + TABLE_SKIP;
    private final static String QUERY_DROP_QUESTION_TYPE = DROP_TABLE_IF_EXISTS + TABLE_QUESTION_TYPE;
    private final static String QUERY_DROP_COUNTRY = DROP_TABLE_IF_EXISTS + TABLE_COUNTRY;
    private final static String QUERY_DROP_ANSWER_MODE = DROP_TABLE_IF_EXISTS + TABLE_ANSWER_MODE;
    private final static String QUERY_DROP_SURVEY = DROP_TABLE_IF_EXISTS + TABLE_SURVEY;
    private final static String QUERY_DROP_USER_ACCESS = DROP_TABLE_IF_EXISTS + TABLE_USER_ACCESS;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_USER);
        db.execSQL(QUERY_CREATE_SECTOR);
        db.execSQL(QUERY_CREATE_QUESTION);
        db.execSQL(QUERY_CREATE_ANSWER);
        db.execSQL(QUERY_CREATE_SKIP);
        db.execSQL(QUERY_CREATE_QUESTION_TYPE);
        db.execSQL(QUERY_CREATE_COUNTRY);
        db.execSQL(QUERY_CREATE_ANSWER_MODE);
        db.execSQL(QUERY_CREATE_SURVEY);
        db.execSQL(QUERY_CREATE_USER_ACCESS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(QUERY_DROP_USER);
        db.execSQL(QUERY_DROP_SECTOR);
        db.execSQL(QUERY_DROP_QUESTION);
        db.execSQL(QUERY_DROP_ANSWER);
        db.execSQL(QUERY_DROP_SKIP);
        db.execSQL(QUERY_DROP_QUESTION_TYPE);
        db.execSQL(QUERY_DROP_COUNTRY);
        db.execSQL(QUERY_DROP_ANSWER_MODE);
        db.execSQL(QUERY_DROP_SURVEY);
        db.execSQL(QUERY_DROP_USER_ACCESS);

        onCreate(db);
    }


    // Question ---- START //


    public long insertQuestion(HolderQuestion holderQuestion) {
        long row = -1;
       // db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put(QUESTION_ID, holderQuestion.questionId);
        cv.put(QUESTION_QUESTION_TYPE_ID, holderQuestion.questionTypeId);
        cv.put(QUESTION_SURVEY_ID, holderQuestion.surveyId);
        cv.put(QUESTION, holderQuestion.question);

       /* HolderQuestion data = getQuestion(holderQuestion.questionId);*/
        HolderQuestion data = getQuestion(holderQuestion.questionId);
        db = getWritableDatabase();
        if (data == null || data.questionId == -1)
            row = db.insert(TABLE_QUESTION, null, cv);

        db.close();
        return row;
    }


    public ArrayList<HolderQuestion> getQuestion() {
        return getQuestion(-1, null, -1);
    }

    public ArrayList<HolderQuestion> getQuestion(String limit) {
        return getQuestion(-1, limit, -1);
    }

    public ArrayList<HolderQuestion> getQuestion(String limit, int offset) {
        return getQuestion(-1, null, offset);
    }

    public HolderQuestion getQuestion(int id) {
        ArrayList<HolderQuestion> ret = getQuestion(id, null, -1);
        if (ret.size() > 0)
            return ret.get(0);
        return new HolderQuestion();
    }

    private ArrayList<HolderQuestion> getQuestion(int id, String limit, int offset) {
        ArrayList<HolderQuestion> questionData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        Cursor cursor;

        if (id != -1 && offset == -1) {
            cursor = db.query(TABLE_QUESTION, null, QUESTION_ID + "=" + id, null, null, null, null);
        } else if (id == -1 && limit != null && offset == -1) {
            cursor = db.query(TABLE_QUESTION, null, null, null, null, null, QUESTION_ID + " desc", limit);
        } else if (offset == -1) {
            cursor = db.query(TABLE_QUESTION, null, null, null, null, null, QUESTION_ID + " desc");
        } else {
            cursor = db.rawQuery("SELECT * From " + TABLE_QUESTION + " Limit 1 Offset " + offset, null);
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                questionData.add(new HolderQuestion(
                        cursor.getInt(cursor.getColumnIndex(QUESTION_ID)),
                        cursor.getInt(cursor.getColumnIndex(QUESTION_QUESTION_TYPE_ID)),
                        cursor.getInt(cursor.getColumnIndex(QUESTION_SURVEY_ID)),
                        cursor.getString(cursor.getColumnIndex(QUESTION))
                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return questionData;
    }


    public ArrayList<HolderQuestion> getQuestionsForSurveyId(int surveyID, int offset){
        ArrayList<HolderQuestion> questionData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

      //  Cursor cursor = db.rawQuery("SELECT * FROM table_question WHERE question_survey_id = "+surveyID,null);
        Cursor cursor = db.rawQuery("SELECT * From " + TABLE_QUESTION + " WHERE question_survey_id = "+ surveyID+" Limit 1 Offset " + offset, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                questionData.add(new HolderQuestion(
                        cursor.getInt(cursor.getColumnIndex(QUESTION_ID)),
                        cursor.getInt(cursor.getColumnIndex(QUESTION_QUESTION_TYPE_ID)),
                        cursor.getInt(cursor.getColumnIndex(QUESTION_SURVEY_ID)),
                        cursor.getString(cursor.getColumnIndex(QUESTION))
                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return questionData;
    }

  /*  public long deleteQuestion(int ques_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_QUESTION, QUESTION_ID + "=" + ques_id, null);
        return 0;
    }*/

     /*  public int minimumQuestionIdOfASet(int surveyId){
        SQLiteDatabase db = getReadableDatabase();
        Cursor  cursor = db.query(TABLE_QUESTION, null, QUESTION_SURVEY_ID + "=" + surveyId, null, null, null, null);

    }*/

//    public ArrayList<HolderQuestion> getQuestionDataSet(int surveyId){
//        ArrayList<HolderQuestion> ds = cur
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("select * from table_question where question_survey_id = "+surveyId , null);
//
//        if (cursor.moveToFirst()){
//            do {
//                HolderQuestion holderQuestion= new
//                        HolderQuestion(cursor.getInt(cursor.getColumnIndex(QUESTION_ID)
//                , cursor))
//
//            }while (cursor.moveToNext());
//        }
//
//        return ds;
//    }

    // Question ---- END //

    //Answer --- Start//

    public long insertAnswer(HolderAnswer holderAnswer) {
        long row = -1;
        ContentValues cv = new ContentValues();
        cv.put(ANSWER_USER_ID, holderAnswer.userId);
        cv.put(ANSWER_COUNTRY_ID, holderAnswer.countryId);
        cv.put(ANSWER_QUESTION_ID, holderAnswer.questionId);
        cv.put(ANSWER_QUESTION_TYPE_ID, holderAnswer.questionTypeId);
        cv.put(ANSWER_SEQUENCE_ID, holderAnswer.secquenceId);
        cv.put(ANSWER_SURVEY_ID, holderAnswer.surveyId);
        cv.put(ANSWER, holderAnswer.answer);

        HolderAnswer data = getAnswer(holderAnswer.answerId);
        if(db != null && db.isOpen()) db.close();
        db = getWritableDatabase();
        if (data == null || data.questionId == -1)
            row = db.insert(TABLE_ANSWER, null, cv);
        db.close();
        return row;
    }

    public ArrayList<HolderAnswer> getAnswer() {
        return getAnswer(-1, null);
    }

    public ArrayList<HolderAnswer> getAnswer(String limit) {
        return getAnswer(-1, limit);
    }

    public HolderAnswer getAnswer(int id) {
        ArrayList<HolderAnswer> ret = getAnswer(id, null);
        if (ret.size() > 0)
            return ret.get(0);
        return new HolderAnswer();
    }

    private ArrayList<HolderAnswer> getAnswer(int id, String limit) {
        ArrayList<HolderAnswer> answerData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        Cursor cursor;

        if (id != -1) {
            cursor = db.query(TABLE_ANSWER, null, ANSWER_ANSWER_ID + "=" + id, null, null, null, null);
        } else if (id == -1 && limit != null) {
            cursor = db.query(TABLE_ANSWER, null, null, null, null, null, ANSWER_ANSWER_ID + " desc", limit);
        } else {
            cursor = db.query(TABLE_ANSWER, null, null, null, null, null, ANSWER_ANSWER_ID + " desc");
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                answerData.add(new HolderAnswer(
                        cursor.getInt(cursor.getColumnIndex(ANSWER_USER_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_QUESTION_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_COUNTRY_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_QUESTION_TYPE_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_SEQUENCE_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_SURVEY_ID)),
                        cursor.getString(cursor.getColumnIndex(ANSWER))
                ));
                cursor.moveToNext();
            }

            cursor.close();
            db.close();
        }
        return answerData;
    }


    public ArrayList<HolderAnswer> getAnswerForSurveyId(int surveyId){
        ArrayList<HolderAnswer> answerData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        //  Cursor cursor = db.rawQuery("SELECT * FROM table_question WHERE question_survey_id = "+surveyID,null);

        Cursor cursor = db.rawQuery("SELECT * From " + TABLE_ANSWER + " WHERE answer_survey_id = "+ surveyId, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                answerData.add(new HolderAnswer(
                        cursor.getInt(cursor.getColumnIndex(ANSWER_USER_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_QUESTION_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_COUNTRY_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_QUESTION_TYPE_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_SEQUENCE_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_SURVEY_ID)),
                        cursor.getString(cursor.getColumnIndex(ANSWER))
                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return answerData;
    }

 /*   public long deleteAnswer(int ans_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_ANSWER, ANSWER_ANSWER_ID + "=" + ans_id, null);
        return 0;
    }*/


    //Answer --- END//


    //USER --- START//


    public long insertUser(HolderUser holderUser) {
        long row = -1;
      /*  db.execSQL("insert into table_user (user_id,name,password,user_name,email,gender,country_id)" + "values("ordid+","+doci+","
                + "\"" + docnam + "\"" + ") ;");*/
        ContentValues cv = new ContentValues();
        cv.put(USER_ID, holderUser.userId);
        cv.put(USER_NAME, holderUser.name);
        cv.put(USER_PASSWORD, holderUser.password);
        cv.put(USER_USER_NAME, holderUser.user_name);
        cv.put(USER_EMAIL, holderUser.email);
        cv.put(USER_GENDER, holderUser.gender);
        cv.put(USER_COUNTRY_ID, holderUser.countryId);

        HolderUser data = getUser(holderUser.userId);
        if(db != null && db.isOpen()) db.close();
        db = getWritableDatabase();

        if (data == null || data.userId == -1)
            row = db.insert(TABLE_USER, null, cv);

        db.close();
        return row;
    }

    public ArrayList<HolderUser> getUser() {
        return getUser(-1, null);
    }

    public ArrayList<HolderUser> getUser(String limit) {
        return getUser(-1, null);
    }

    public HolderUser getUser(int id) {
        ArrayList<HolderUser> ret = getUser(id, null);
        if (ret.size() > 0)
            return ret.get(0);
        return new HolderUser();
    }

    private ArrayList<HolderUser> getUser(int id, String limit) {
        ArrayList<HolderUser> userData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        Cursor cursor;

        if (id != -1) {
            cursor = db.query(TABLE_USER, null, USER_ID + "=" + id, null, null, null, null);
        } else if (id == -1 && limit != null) {
            cursor = db.query(TABLE_USER, null, null, null, null, null, USER_ID + " desc", limit);
        } else {
            cursor = db.query(TABLE_USER, null, null, null, null, null, USER_ID + " desc");
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                userData.add(new HolderUser(
                        cursor.getInt(cursor.getColumnIndex(USER_ID)),
                        cursor.getString(cursor.getColumnIndex(USER_NAME)),
                        cursor.getString(cursor.getColumnIndex(USER_PASSWORD)),
                        cursor.getString(cursor.getColumnIndex(USER_USER_NAME)),
                        cursor.getString(cursor.getColumnIndex(USER_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(USER_GENDER)),
                        cursor.getInt(cursor.getColumnIndex(USER_COUNTRY_ID))
                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return userData;
    }
// Where is the error? Is it working now?
    // na dada. dada i think problem is Sal_sal
    // can i try with another usr name
    // OK
    public void showAllUser() {
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();
        Cursor cursorCountryId = db.rawQuery("SELECT * FROM table_user ",null);
        if (cursorCountryId != null && cursorCountryId.getCount() > 0){
            cursorCountryId.moveToFirst();
            Log.e("PD_DEBUG","User Data: "+
                    cursorCountryId.getInt(cursorCountryId.getColumnIndex(USER_ID))+ "\n"+
                    cursorCountryId.getString(cursorCountryId.getColumnIndex(USER_NAME))+ "\n"+
                    cursorCountryId.getString(cursorCountryId.getColumnIndex(USER_PASSWORD))+ "\n"+
                    cursorCountryId.getString(cursorCountryId.getColumnIndex(USER_USER_NAME))+ "\n"+
                    cursorCountryId.getString(cursorCountryId.getColumnIndex(USER_EMAIL))+ "\n"+
                    cursorCountryId.getString(cursorCountryId.getColumnIndex(USER_GENDER))+ "\n"+
                    cursorCountryId.getInt(cursorCountryId.getColumnIndex(USER_COUNTRY_ID))
            );
        }
    }
    public HolderUser getUserInfo(String user_name, String password){

        showAllUser();

        HolderUser holderUser = null;
      //  String cursor = "select * From "+ TABLE_USER +" where user_name = '"+ user_name+"' and password = '"+password+"';";
        //  String cursor = "SELECT * FROM "+ TABLE_USER +" WHERE user_name = '"+ user_name+"' and password = '"+password+"';";

        //  rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});

        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();
        Cursor cursorCountryId = db.rawQuery("SELECT * FROM table_user WHERE name = ? AND password = ?",new String[]{user_name,password});
        if (cursorCountryId != null && cursorCountryId.getCount() > 0){
            cursorCountryId.moveToFirst();
            holderUser = new HolderUser(
                    cursorCountryId.getInt(cursorCountryId.getColumnIndex(USER_ID)),
                    cursorCountryId.getString(cursorCountryId.getColumnIndex(USER_NAME)),
                    cursorCountryId.getString(cursorCountryId.getColumnIndex(USER_PASSWORD)),
                    cursorCountryId.getString(cursorCountryId.getColumnIndex(USER_USER_NAME)),
                    cursorCountryId.getString(cursorCountryId.getColumnIndex(USER_EMAIL)),
                    cursorCountryId.getString(cursorCountryId.getColumnIndex(USER_GENDER)),
                    cursorCountryId.getInt(cursorCountryId.getColumnIndex(USER_COUNTRY_ID)));
        }
      //  cursorCountryId.close();
          //  db.close();

        return holderUser;
    }

  /*  public long deleteUser(int user_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, USER_ID + "=" + user_id, null);
        return 0;
    }*/

    //USER --- END//


    // Country --- START//

    public long insertCountry(HolderCountry holderCountry) {
        long row = -1;
        //db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put(COUNTRY_ID, holderCountry.countryId);
        cv.put(COUNTRY_NAME, holderCountry.countryName);

       /* HolderQuestion data = getQuestion(holderQuestion.questionId);*/
        HolderCountry data = getCountry(holderCountry.countryId);
        if(db != null && db.isOpen()) db.close();
        db = getWritableDatabase();
        if (data == null || data.countryId == -1)
            row = db.insert(TABLE_COUNTRY, null, cv);
        db.close();
        return row;
    }

    public ArrayList<HolderCountry> getCountry() {
        return getCountry(-1, null);
    }

    public ArrayList<HolderCountry> getCountry(String limit) {
        return getCountry(-1, null);
    }

    public HolderCountry getCountry(int id) {
        ArrayList<HolderCountry> ret = getCountry(id, null);
        if (ret.size() > 0)
            return ret.get(0);
        return new HolderCountry();
    }

    private ArrayList<HolderCountry> getCountry(int id, String limit) {
        ArrayList<HolderCountry> userData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        Cursor cursor;

        if (id != -1) {
            cursor = db.query(TABLE_COUNTRY, null, COUNTRY_ID + "=" + id, null, null, null, null);
        } else if (id == -1 && limit != null) {
            cursor = db.query(TABLE_COUNTRY, null, null, null, null, null, COUNTRY_ID + " desc", limit);
        } else {
            cursor = db.query(TABLE_COUNTRY, null, null, null, null, null, COUNTRY_ID + " desc");
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                userData.add(new HolderCountry(
                        cursor.getInt(cursor.getColumnIndex(COUNTRY_ID)),
                        cursor.getString(cursor.getColumnIndex(COUNTRY_NAME))
                ));
                cursor.moveToNext();
            }
            cursor.close();
        }

        return userData;
    }

   /* public long deleteCountry(int country_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_COUNTRY, COUNTRY_ID + "=" + country_id, null);
        return 0;
    }*/

    // Country --- END//


    // QuestionType ---  START//

    public long insertQuestionType(HolderQuestionType holderQuestionType) {
        long row = -1;
        ContentValues cv = new ContentValues();
        cv.put(QUESTION_TYPE_ID, holderQuestionType.questionTypeId);
        cv.put(QUESTION_TYPE_NAME, holderQuestionType.questionTypeName);

       /* HolderQuestion data = getQuestion(holderQuestion.questionId);*/
        HolderQuestionType data = getQuestionType(holderQuestionType.questionTypeId);
        if(db != null && db.isOpen()) db.close();
        db = getWritableDatabase();
        if (data == null || data.questionTypeId == -1)
            row = db.insert(TABLE_QUESTION_TYPE, null, cv);
        db.close();
        return row;
    }

    public ArrayList<HolderQuestionType> getQuestionType() {
        return getQuestionType(-1, null);
    }

    public ArrayList<HolderQuestionType> getQuestionType(String limit) {
        return getQuestionType(-1, null);
    }

    public HolderQuestionType getQuestionType(int id) {
        ArrayList<HolderQuestionType> ret = getQuestionType(id, null);
        if (ret.size() > 0)
            return ret.get(0);
        return new HolderQuestionType();
    }

    private ArrayList<HolderQuestionType> getQuestionType(int id, String limit) {
        ArrayList<HolderQuestionType> questionTypesData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        Cursor cursor;

        if (id != -1) {
            cursor = db.query(TABLE_QUESTION_TYPE, null, QUESTION_TYPE_ID + "=" + id, null, null, null, null);
        } else if (id == -1 && limit != null) {
            cursor = db.query(TABLE_QUESTION_TYPE, null, null, null, null, null, QUESTION_TYPE_ID + " desc", limit);
        } else {
            cursor = db.query(TABLE_QUESTION_TYPE, null, null, null, null, null, QUESTION_TYPE_ID + " desc");
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                questionTypesData.add(new HolderQuestionType(
                        cursor.getInt(cursor.getColumnIndex(QUESTION_TYPE_ID)),
                        cursor.getString(cursor.getColumnIndex(QUESTION_TYPE_NAME))
                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return questionTypesData;
    }

  /*  public long deleteQuestionType(int qt_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_QUESTION_TYPE, QUESTION_TYPE_ID + "=" + qt_id, null);
        return 0;
    }*/

    // QuestionType --- END//


    //Sector --- Start//

    public long insertSector(HolderSector holderSector) {
        long row = -1;
        ContentValues cv = new ContentValues();
        cv.put(SECTOR_ID, holderSector.sectorId);
        cv.put(SECTOR_NAME, holderSector.sectorName);

       /* HolderQuestion data = getQuestion(holderQuestion.questionId);*/
        HolderSector data = getSector(holderSector.sectorId);
        if(db != null && db.isOpen()) db.close();
        db = getWritableDatabase();
        if (data == null || data.sectorId == -1)
            row = db.insert(TABLE_SECTOR, null, cv);
        db.close();
        return row;
    }

    public ArrayList<HolderSector> getSector() {
        return getSector(-1, null);
    }

    public ArrayList<HolderSector> getSector(String limit) {
        return getSector(-1, null);
    }

    public HolderSector getSector(int id) {
        ArrayList<HolderSector> ret = getSector(id, null);
        if (ret.size() > 0)
            return ret.get(0);
        return new HolderSector();
    }

    private ArrayList<HolderSector> getSector(int id, String limit) {
        ArrayList<HolderSector> sectorData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        Cursor cursor;

        if (id != -1) {
            cursor = db.query(TABLE_SECTOR, null, SECTOR_ID + "=" + id, null, null, null, null);
        } else if (id == -1 && limit != null) {
            cursor = db.query(TABLE_SECTOR, null, null, null, null, null, SECTOR_ID + " desc", limit);
        } else {
            cursor = db.query(TABLE_SECTOR, null, null, null, null, null, SECTOR_ID + " desc");
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                sectorData.add(new HolderSector(
                        cursor.getInt(cursor.getColumnIndex(SECTOR_ID)),
                        cursor.getString(cursor.getColumnIndex(SECTOR_NAME))
                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return sectorData;
    }

  /*  public long deleteSector(int sector_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SECTOR, SECTOR_ID + "=" + sector_id, null);
        return 0;
    }*/

    // Sector --- END//


    // Skip --- START//


    public long insertSkip(HolderSkip holderSkip) {
        long row=-1;
        ContentValues cv = new ContentValues();
        cv.put(SKIP_ID, holderSkip.skipId);
        cv.put(SKIP_SURVEY_ID, holderSkip.surveyId);
        cv.put(SKIP_QUESTION_ID, holderSkip.questionId);
        cv.put(SKIP_ANSWER_ID, holderSkip.answerId);
        cv.put(SKIP_SKIP_QUESTION_ID, holderSkip.skipQuestionId);

       /* HolderQuestion data = getQuestion(holderQuestion.questionId);*/
        HolderSkip data = getSkip(holderSkip.skipId);
        if(db != null && db.isOpen()) db.close();
        db = getWritableDatabase();
        if (data == null || data.skipId == -1)
            row= db.insert(TABLE_SKIP, null, cv);

        db.close();

        return row;
    }

    public ArrayList<HolderSkip> getSkip() {
        return getSkip(-1, null);
    }

    public ArrayList<HolderSkip> getSkip(String limit) {
        return getSkip(-1, null);
    }

    public HolderSkip getSkip(int id) {
        ArrayList<HolderSkip> ret = getSkip(id, null);
        if (ret.size() > 0)
            return ret.get(0);
        return new HolderSkip();
    }

    private ArrayList<HolderSkip> getSkip(int id, String limit) {
        ArrayList<HolderSkip> skipData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        Cursor cursor;

        if (id != -1) {
            cursor = db.query(TABLE_SKIP, null, SKIP_ID + "=" + id, null, null, null, null);
        } else if (id == -1 && limit != null) {
            cursor = db.query(TABLE_SKIP, null, null, null, null, null, SKIP_ID + " desc", limit);
        } else {
            cursor = db.query(TABLE_SKIP, null, null, null, null, null, SKIP_ID + " desc");
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                skipData.add(new HolderSkip(
                        cursor.getInt(cursor.getColumnIndex(SKIP_ID)),
                        cursor.getInt(cursor.getColumnIndex(SKIP_SURVEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(SKIP_QUESTION_ID)),
                        cursor.getInt(cursor.getColumnIndex(SKIP_ANSWER_ID)),
                        cursor.getInt(cursor.getColumnIndex(SKIP_SKIP_QUESTION_ID))
                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return skipData;
    }

  /*  public long deleteSkip(int skip_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SKIP, SKIP_ID + "=" + skip_id, null);
        return 0;
    }*/

    // Skip --- END//


    // Survey --- Start//


    public long insertSurvey(HolderSurvey holderSurvey) {
        long row = -1;
        // db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put(SURVEY_ID, holderSurvey.surveyId);
        cv.put(SURVEY_SECTOR_ID, holderSurvey.sectorId);
        cv.put(SURVEY_USER_ID, holderSurvey.userId);
        cv.put(SURVEY_NAME, holderSurvey.surveyName);

       /* HolderQuestion data = getQuestion(holderQuestion.questionId);*/
        HolderSurvey data = getSurvey(holderSurvey.surveyId);
        if(db != null && db.isOpen()) db.close();
        db = getWritableDatabase();
        if (data == null || data.surveyId == -1)
            row = db.insert(TABLE_SURVEY, null, cv);
        db.close();
        return row;
    }

    public ArrayList<HolderSurvey> getSurvey() {
        return getSurvey(-1, null);
    }

    public ArrayList<HolderSurvey> getSurvey(String limit) {
        return getSurvey(-1, null);
    }

    public HolderSurvey getSurvey(int id) {
        ArrayList<HolderSurvey> ret = getSurvey(id, null);
        if (ret.size() > 0)
            return ret.get(0);
        return new HolderSurvey();
    }

    private ArrayList<HolderSurvey> getSurvey(int id, String limit) {
        ArrayList<HolderSurvey> surveyData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        Cursor cursor;

        if (id != -1) {
            cursor = db.query(TABLE_SURVEY, null, SURVEY_ID + "=" + id, null, null, null, null);
        } else if (id == -1 && limit != null) {
            cursor = db.query(TABLE_SURVEY, null, null, null, null, null, SURVEY_ID + " desc", limit);
        } else {
            cursor = db.query(TABLE_SURVEY, null, null, null, null, null, SURVEY_ID + " desc");
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                surveyData.add(new HolderSurvey(
                        cursor.getInt(cursor.getColumnIndex(SURVEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(SURVEY_SECTOR_ID)),
                        cursor.getInt(cursor.getColumnIndex(SURVEY_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(SURVEY_NAME))
                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return surveyData;
    }

    public ArrayList<HolderSurvey> getAllSurveyForAUser(int userId){
       // int[] args={userId};
        ArrayList<HolderSurvey> allSurveyData = new ArrayList<>();

        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

      //  Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_SURVEY+" WHERE "+SURVEY_USER_ID+ "= "+ userId, null);
        Cursor cursor = db.rawQuery("SELECT * FROM table_survey WHERE survey_user_id = "+ userId, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                allSurveyData.add(new HolderSurvey(
                        cursor.getInt(cursor.getColumnIndex(SURVEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(SURVEY_SECTOR_ID)),
                        cursor.getInt(cursor.getColumnIndex(SURVEY_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(SURVEY_NAME))
                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return allSurveyData;
    }

  /*  public long deleteSurvey(int survey_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SURVEY, SURVEY_ID + "=" + survey_id, null);
        return 0;
    }*/

    // Survey --- END//


    // AnswerMode --- START //


    public long insertAnswerMode(HolderAnswerMode holderAnswerMode) {
        long row = -1;

        ContentValues cv = new ContentValues();
        cv.put(ANSWER_MODE_MIN_VALUE, holderAnswerMode.minValue);
        cv.put(ANSWER_MODE_MAX_VALUE, holderAnswerMode.maxValue);
        cv.put(ANSWER_MODE_SURVEY_ID, holderAnswerMode.surveyId);
        cv.put(ANSWER_MODE_QUESTION_ID, holderAnswerMode.questionId);
        cv.put(ANSWER_MODE_QUESTION_TYPE_ID, holderAnswerMode.QT_Id);
        cv.put(ANSWER_MODE_HINT, holderAnswerMode.hint);
        cv.put(ANSWER_MODE_SHOW_HIDE, holderAnswerMode.show_hide);

        // HolderQuestion data = getQuestion(holderQuestion.questionId);
        HolderAnswerMode data = getAnswerMode(holderAnswerMode.questionId);
        if(db != null && db.isOpen()) db.close();
        db = getWritableDatabase();
        if (data == null || data.questionId == -1)
            row = db.insert(TABLE_ANSWER_MODE, null, cv);
        db.close();
        return row;


    }

    public ArrayList<HolderAnswerMode> getAnswerMode() {
        return getAnswerMode(-1, null);
    }

    public ArrayList<HolderAnswerMode> getAnswerMode(String limit) {
        return getAnswerMode(-1, null);
    }

    public HolderAnswerMode getAnswerMode(int id) {
        ArrayList<HolderAnswerMode> ret = getAnswerMode(id, null);
        if (ret.size() > 0)
            return ret.get(0);
        return new HolderAnswerMode();
    }

    private ArrayList<HolderAnswerMode> getAnswerMode(int id, String limit) {
        ArrayList<HolderAnswerMode> answerModeData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        Cursor cursor;

        if (id != -1 ) {
            cursor = db.query(TABLE_ANSWER_MODE, null, ANSWER_MODE_QUESTION_ID + "=" + id, null, null, null, null);
        } else if (id == -1 && limit != null ) {
            cursor = db.query(TABLE_ANSWER_MODE, null, null, null, null, null, ANSWER_MODE_QUESTION_ID + " desc", limit);
        } else  {
            cursor = db.query(TABLE_ANSWER_MODE, null, null, null, null, null, ANSWER_MODE_QUESTION_ID + " desc");
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                answerModeData.add(new HolderAnswerMode(
                        cursor.getInt(cursor.getColumnIndex(ANSWER_MODE_MIN_VALUE)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_MODE_MAX_VALUE)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_MODE_SURVEY_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_MODE_QUESTION_ID)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_MODE_QUESTION_TYPE_ID)),
                        cursor.getString(cursor.getColumnIndex(ANSWER_MODE_HINT)),
                        cursor.getInt(cursor.getColumnIndex(ANSWER_MODE_SHOW_HIDE))

                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return answerModeData;
    }

 /*   public long deleteAnswerMode(int answer_mode_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_ANSWER_MODE, ANSWER_MODE_QUESTION_ID + "=" + answer_mode_id, null);
        return 0;
    }*/


    // AnswerMode --- END //


    // UserAccess --- START//


    public long insertUserAccess(HolderUserAccess holderUserAccess) {
        long row = -1;
        ContentValues cv = new ContentValues();
        cv.put(USER_ACCESS_USER_ID, holderUserAccess.userID);
        cv.put(USER_ACCESS_COUNTRY_ID, holderUserAccess.countryId);
        cv.put(USER_ACCESS_SECTOR_ID, holderUserAccess.SectorId);


        HolderUserAccess data = getUserAccess(holderUserAccess.userID);
        if(db != null && db.isOpen()) db.close();
        db = getWritableDatabase();
        if (data == null || data.userID == -1) {
            row = db.insert(TABLE_USER_ACCESS, null, cv);
        }
        db.close();
         return row;
    }
    public ArrayList<HolderUserAccess> getUserAccess() {
        return getUserAccess(-1, null);
    }

    public ArrayList<HolderUserAccess> getUserAccess(String limit) {
        return getUserAccess(-1, null);
    }

    public HolderUserAccess getUserAccess(int id) {
        ArrayList<HolderUserAccess> ret = getUserAccess(id, null);
        if (ret.size() > 0)
            return ret.get(0);
        return new HolderUserAccess();
    }

    private ArrayList<HolderUserAccess> getUserAccess(int id, String limit) {
        ArrayList<HolderUserAccess> userAccessesData = new ArrayList<>();
        if(db != null && db.isOpen()) db.close();
        db = getReadableDatabase();

        Cursor cursor;

        if (id != -1 ) {
            cursor = db.query(TABLE_USER_ACCESS, null, USER_ACCESS_USER_ID + "=" + id, null, null, null, null);
        } else if (id == -1 && limit != null ) {
            cursor = db.query(TABLE_USER_ACCESS, null, null, null, null, null, USER_ACCESS_USER_ID + " desc", limit);
        } else  {
            cursor = db.query(TABLE_USER_ACCESS, null, null, null, null, null, USER_ACCESS_USER_ID + " desc");
        }

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                userAccessesData.add(new HolderUserAccess(
                        cursor.getInt(cursor.getColumnIndex(USER_ACCESS_USER_ID)),
                        cursor.getInt(cursor.getColumnIndex(USER_ACCESS_COUNTRY_ID)),
                        cursor.getInt(cursor.getColumnIndex(USER_ACCESS_SECTOR_ID))

                ));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }

        return userAccessesData;
    }

  /*  public long deleteUserAccess(int user_access_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER_ACCESS, USER_ACCESS_USER_ID + "=" + user_access_id, null);
        return 0;
    }*/

    // UserAccess --- END//
}





