package com.survey.shuvo.technodhaka.tdsurvey.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.survey.shuvo.technodhaka.tdsurvey.DBHelper.SQLiteHandler;
import com.survey.shuvo.technodhaka.tdsurvey.DynamicTable;
import com.survey.shuvo.technodhaka.tdsurvey.R;
import com.survey.shuvo.technodhaka.tdsurvey.perser.Parser;

import org.json.JSONArray;
import org.json.JSONObject;

public class ParseDataActivity extends BaseActivity {

    private ProgressDialog progressDialog;
    private SQLiteHandler db;
    private int progressIncremental;
    public static final String ALL_DATA = "all_data";
    public static final String DYNAMIC_TABLE = "dynamic_table";
    public static final String ENU_TABLE = "enu_table";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_data);
       //   Inject_DynamicTableIntoSQLite object = new Inject_DynamicTableIntoSQLite();
        db= new SQLiteHandler(getApplicationContext());
        Inject_All_DataIntoSQLite object = new Inject_All_DataIntoSQLite();
        object.execute();

        ;
    }

    private class Inject_All_DataIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }


        public void showProgressDialog() {
            progressDialog = new ProgressDialog(ParseDataActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(88);
            progressDialog.setMessage("Retrieving...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            /**
             * Read JSON DATA  from the text file
             * */
            String retrieveData = readDataFromFile(ALL_DATA);

            try {
                int size;
                /**
                 * The total string Convert into JSON object
                 * */

                JSONObject jObj = new JSONObject(retrieveData);

                String user_id = jObj.getString(Parser.USR_ID);
                JSONObject user = jObj.getJSONObject(Parser.USER_JSON_A);
                String country_code = user.getString(Parser.ADM_COUNTRY_CODE);
                String login_name = user.getString(Parser.USR_LOG_IN_NAME);
                String login_pw = user.getString(Parser.USR_LOG_IN_PW);
                String first_name = user.getString(Parser.USR_FIRST_NAME);
                String last_name = user.getString(Parser.USR_LAST_NAME);
                String email = user.getString(Parser.USR_EMAIL);
                String email_verification = user.getString(Parser.USR_EMAIL_VERIFICATION);
                String user_status = user.getString(Parser.USR_STATUS);
                String entry_by = user.getString(Parser.ENTRY_BY);
                String entry_date = user.getString(Parser.ENTRY_DATE);

//                setUserName(first_name); // Setting User hhName into session
//                setStaffID(user_id); // Setting Staff ID to use when sync data


              //  setUserCountryCode(country_code); // Setting Country code


                db.addUser(user_id, country_code, login_name, login_pw, first_name, last_name, email, email_verification, user_status, entry_by, entry_date);
//                Log.d("MOR_12",user_id +  country_code +  login_name +  login_pw +  first_name +  last_name +  email +  email_verification +  user_status +  entry_by +  entry_date);


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.COUNTRIES_JSON_A)) {

                    Parser.AdmCountryParser(jObj.getJSONArray(Parser.COUNTRIES_JSON_A), db);
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.VALID_DATES_JSON_A)) {
                    JSONArray valid_dates = jObj.getJSONArray(Parser.VALID_DATES_JSON_A);
                    size = valid_dates.length();

                    for (int i = 0; i < size; i++) {
                        JSONObject valid_date = valid_dates.getJSONObject(i);
                        String AdmCountryCode = valid_date.getString(Parser.ADM_COUNTRY_CODE);
                        String StartDate = valid_date.getString(Parser.START_DATE);
                        String EndDate = valid_date.getString(Parser.END_DATE);

                        db.addValidDateRange(AdmCountryCode, StartDate, EndDate);


                    }
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_GROUP_JSON_A)) {
                    Parser.gpsGroupParser(jObj.getJSONArray(Parser.GPS_GROUP_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_SUBGROUP_JSON_A)) {
                    Parser.gpsSubGroupParser(jObj.getJSONArray(Parser.GPS_SUBGROUP_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_LOCATION_JSON_A)) {
                    Parser.gpsLocationParse(jObj.getJSONArray(Parser.GPS_LOCATION_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_COUNTRY_AWARD_JSON_A)) {
                    Parser.admCountryAwardParser(jObj.getJSONArray(Parser.ADM_COUNTRY_AWARD_JSON_A), db);
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_AWARD_JSON_A)) {
                    Parser.admAwardParser(jObj.getJSONArray(Parser.ADM_AWARD_JSON_A), db);
                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_DONOR_JSON_A)) {
                    Parser.admDonorParser(jObj.getJSONArray(Parser.ADM_DONOR_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_PROGRAM_MASTER_JSON_A)) {
                    Parser.admProgramMasterParser(jObj.getJSONArray(Parser.ADM_PROGRAM_MASTER_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_SERVICE_MASTER_JSON_A)) {
                    Parser.admServiceMasterParser(jObj.getJSONArray(Parser.ADM_SERVICE_MASTER_JSON_A), db);

                }

                publishProgress(++progressIncremental);
                //adm_op_month


                if (!jObj.isNull(Parser.ADM_OP_MONTH_JSON_A)) {
                    JSONArray adm_op_months = jObj.getJSONArray(Parser.ADM_OP_MONTH_JSON_A);
                    size = adm_op_months.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject adm_op_month = adm_op_months.getJSONObject(i);

                        String AdmCountryCode = adm_op_month.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = adm_op_month.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = adm_op_month.getString(Parser.ADM_AWARD_CODE);
                        String OpCode = adm_op_month.getString(Parser.OP_CODE);
                        String OpMonthCode = adm_op_month.getString(Parser.OP_MONTH_CODE);
                        String MonthLabel = adm_op_month.getString(Parser.MONTH_LABEL);
                        String StartDate = adm_op_month.getString(Parser.START_DATE);
                        String EndDate = adm_op_month.getString(Parser.END_DATE);

                        String UsaStartDate = adm_op_month.getString(Parser.USA_START_DATE);
                        String UsaEndDate = adm_op_month.getString(Parser.USA_END_DATE);
                        String Status = adm_op_month.getString("Status");
                        db.addOpMonthFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, OpCode, OpMonthCode, MonthLabel, StartDate, EndDate, UsaStartDate, UsaEndDate, Status);


                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.ADM_COUNTRY_PROGRAM_JSON_A)) {
                    JSONArray adm_country_programs = jObj.getJSONArray(Parser.ADM_COUNTRY_PROGRAM_JSON_A);
                    size = adm_country_programs.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject adm_country_program = adm_country_programs.getJSONObject(i);
                        String AdmCountryCode = adm_country_program.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = adm_country_program.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = adm_country_program.getString(Parser.ADM_AWARD_CODE);
                        String AdmProgCode = adm_country_program.getString(Parser.ADM_PROG_CODE);
                        String AdmSrvCode = adm_country_program.getString(Parser.ADM_SRV_CODE);
                        String ProgFlag = adm_country_program.getString("ProgFlag");
                        String FoodFlag = adm_country_program.getString(Parser.FOOD_FLAG);
                        String NFoodFlag = adm_country_program.getString(Parser.N_FOOD_FLAG);
                        String CashFlag = adm_country_program.getString(Parser.CASH_FLAG);
                        String VOFlag = adm_country_program.getString(Parser.VO_FLAG);
                        String DefaultFoodDays = adm_country_program.getString(Parser.DEFAULT_FOOD_DAYS);
                        String DefaultNFoodDays = adm_country_program.getString(Parser.DEFAULT_N_FOOD_DAYS);
                        String DefaultCashDays = adm_country_program.getString(Parser.DEFAULT_CASH_DAYS);
                        String DefaultVODays = adm_country_program.getString(Parser.DEFAULT_VO_DAYS);
                        String SrvSpecific = adm_country_program.getString(Parser.SRV_SPECIFIC);

                        db.insertAdmCountryProgram(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, AdmSrvCode, ProgFlag, FoodFlag, NFoodFlag, CashFlag, VOFlag, DefaultFoodDays, DefaultNFoodDays, DefaultCashDays, DefaultVODays, SrvSpecific);


                    }
                }

                publishProgress(++progressIncremental);


                // * Adding data into  dob_service_center  Table


                if (!jObj.isNull(Parser.DOB_SERVICE_CENTER_JSON_A)) {// this is not servie
                    JSONArray dob_service_centers = jObj.getJSONArray(Parser.DOB_SERVICE_CENTER_JSON_A);
                    size = dob_service_centers.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject dob_service_center = dob_service_centers.getJSONObject(i);

                        String AdmCountryCode = dob_service_center.getString(Parser.ADM_COUNTRY_CODE);
                        String SrvCenterCode = dob_service_center.getString(Parser.SRV_CENTER_CODE);
                        String SrvCenterName = dob_service_center.getString(Parser.SRV_CENTER_NAME);
                        // String SrvCenterAddress = dob_service_center.getString("SrvCenterAddress");
                        //   String SrvCenterCatCode = dob_service_center.getString("SrvCenterCatCode");

                        String FDPCode = dob_service_center.getString(Parser.FDP_CODE);


                        db.addServiceCenter(AdmCountryCode, SrvCenterCode, SrvCenterName, FDPCode);

//                        Log.d("NIR1", "In Service Center Table - AdmCountryCode :" + AdmCountryCode + " SrvCenterCode : " + SrvCenterCode + " SrvCenterName : " + SrvCenterName);
                        //+ " SrvCenterAddress : " + SrvCenterAddress + " SrvCenterCatCode  : " + SrvCenterCatCode + " FDPCode  : " + FDPCode );
                    }
                }
                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.STAFF_ACCESS_INFO_JSON_A)) {// this is not servie
                    JSONArray staff_access_info_accesses = jObj.getJSONArray(Parser.STAFF_ACCESS_INFO_JSON_A);
                    size = staff_access_info_accesses.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject staff_access_info_access = staff_access_info_accesses.getJSONObject(i);

                        String StfCode = staff_access_info_access.getString(Parser.STF_CODE);
                        String AdmCountryCode = staff_access_info_access.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = staff_access_info_access.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = staff_access_info_access.getString(Parser.ADM_AWARD_CODE);
                        String LayRListCode = staff_access_info_access.getString(Parser.LAY_R_LIST_CODE);
                        String btnNew = staff_access_info_access.getString(Parser.BTN_NEW1);
                        String btnSave = staff_access_info_access.getString(Parser.BTN_SAVE);
                        String btnDel = staff_access_info_access.getString(Parser.BTN_DEL);
                        String btnPepr = staff_access_info_access.getString(Parser.BTN_PEPR);
                        String btnAprv = staff_access_info_access.getString(Parser.BTN_APRV);
                        String btnRevw = staff_access_info_access.getString(Parser.BTN_REVW);
                        String btnVrfy = staff_access_info_access.getString(Parser.BTN_VRFY);
                        String btnDTran = staff_access_info_access.getString(Parser.BTN_D_TRAN);


                        //String FDPCode = dbo_staff_geo_info_access.getString("FDPCode");
                        String disCode = LayRListCode.substring(0, 2);
                        String upCode = LayRListCode.substring(2, 4);
                        String unCode = LayRListCode.substring(4, 6);
                        String vCode = LayRListCode.substring(6);
                        db.addStaffGeoAccessInfoFromOnline(StfCode, AdmCountryCode, AdmDonorCode, AdmAwardCode, LayRListCode, disCode, upCode, unCode, vCode, btnNew, btnSave, btnDel, btnPepr, btnAprv, btnRevw, btnVrfy, btnDTran);//, SrvCenterCatCode, FDPCode);


                        //  Log.d(TAG, "In addStaffGeoAccessInfoFromOnline Table- StfCode :" + StfCode + " AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode +               " AdmAwardCode : " + AdmAwardCode + " LayRListCode  : " + LayRListCode);// + " FDPCode  : " + FDPCode );
                    }
                }


                if (!jObj.isNull(Parser.LB_REG_HH_CATEGORY_JSON_A)) {
                    JSONArray lb_reg_hh_categorys = jObj.getJSONArray(Parser.LB_REG_HH_CATEGORY_JSON_A);
                    size = lb_reg_hh_categorys.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lb_reg_hh_category = lb_reg_hh_categorys.getJSONObject(i);
                        String AdmCountryCode = lb_reg_hh_category.getString(Parser.ADM_COUNTRY_CODE);
                        String HHHeadCatCode = lb_reg_hh_category.getString(Parser.HH_HEAD_CAT_CODE);
                        String CatName = lb_reg_hh_category.getString(Parser.CAT_NAME);
                        db.addHHCategory(AdmCountryCode, HHHeadCatCode, CatName);


                        // Log.d(TAG, "In House hold Category Table: AdmCountryCode : " + AdmCountryCode + " HHHeadCatCode : " + HHHeadCatCode +" SubGrpName : " + SubGrpName + " Description : " + Description  );
                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.REG_LUP_GRADUATION_JSON_A)) {
                    JSONArray reg_lup_graduations = jObj.getJSONArray(Parser.REG_LUP_GRADUATION_JSON_A);
                    size = reg_lup_graduations.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject reg_lup_graduation = reg_lup_graduations.getJSONObject(i);

                        String AdmProgCode = reg_lup_graduation.getString(Parser.ADM_PROG_CODE);
                        String AdmSrvCode = reg_lup_graduation.getString(Parser.ADM_SRV_CODE);
                        String GRDCode = reg_lup_graduation.getString(Parser.GRD_CODE);
                        String GRDTitle = reg_lup_graduation.getString(Parser.GRD_TITLE);
                        String DefaultCatActive = reg_lup_graduation.getString(Parser.DEFAULT_CAT_ACTIVE);
                        String DefaultCatExit = reg_lup_graduation.getString(Parser.DEFAULT_CAT_EXIT);


                        db.addGraduation(AdmProgCode, AdmSrvCode, GRDCode, GRDTitle, DefaultCatActive, DefaultCatExit);


                        // Log.d(TAG, "In reg_lup_graduation: AdmProgCode : " + AdmProgCode + " AdmSrvCode : " + AdmSrvCode + " GRDCode : " + GRDCode + " GRDTitle : " + GRDTitle + " DefaultCatActive : " + DefaultCatActive+ " DefaultCatExit : " + DefaultCatExit   );
                    }
                }
                publishProgress(++progressIncremental);
                // Adding data into Layer Label Table
                if (!jObj.isNull(Parser.LAYER_LABELS_JSON_A)) {
                    JSONArray layer_labels = jObj.getJSONArray(Parser.LAYER_LABELS_JSON_A);
                    size = layer_labels.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject layer_label = layer_labels.getJSONObject(i);

                        String AdmCountryCode = layer_label.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = layer_label.getString(Parser.GEO_LAY_R_CODE);
                        String GeoLayRName = layer_label.getString(Parser.GEO_LAY_R_NAME);
                        db.addLayerLabel(AdmCountryCode, GeoLayRCode, GeoLayRName);


                    }
                }
                publishProgress(++progressIncremental);

                // Adding data into District Table
                if (!jObj.isNull(Parser.DISTRICT)) {
                    JSONArray district = jObj.getJSONArray(Parser.DISTRICT);
                    size = district.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject dist = district.getJSONObject(i);

                        String AdmCountryCode = dist.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = dist.getString(Parser.GEO_LAY_R_CODE);
                        String LayRListCode = dist.getString(Parser.LAY_R_LIST_CODE);
                        String LayRListName = dist.getString(Parser.LAY_R_LIST_NAME);

                        db.addDistrict(AdmCountryCode, GeoLayRCode, LayRListCode, LayRListName);


                    }
                }
                publishProgress(++progressIncremental);
                // Adding data into Upazilla Table
                if (!jObj.isNull(Parser.UPAZILLA)) {

                    JSONArray upazilla = jObj.getJSONArray(Parser.UPAZILLA);

                    size = upazilla.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject up = upazilla.getJSONObject(i);

                        String AdmCountryCode = up.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = up.getString(Parser.GEO_LAY_R_CODE);
                        String LayR1ListCode = up.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = up.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR2ListName = up.getString(Parser.LAY_R_2_LIST_NAME);

                        db.addUpazilla(AdmCountryCode, GeoLayRCode, LayR1ListCode, LayR2ListCode, LayR2ListName);

                        //  Log.d(TAG, "AdmCountryCode : " + AdmCountryCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : " + LayR2ListCode + " LayR2ListName : " + LayR2ListName);
                    }
                }
                publishProgress(++progressIncremental);

                // Adding data into Unit Table
                if (!jObj.isNull(Parser.UNIT_JSON_A)) {

                    JSONArray unit = jObj.getJSONArray(Parser.UNIT_JSON_A);
                    size = unit.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject un = unit.getJSONObject(i);

                        String AdmCountryCode = un.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = un.getString(Parser.GEO_LAY_R_CODE);
                        String LayR1ListCode = un.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = un.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = un.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR3ListName = un.getString(Parser.LAY_R_3_LIST_NAME);

                        db.addUnit(AdmCountryCode, GeoLayRCode, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR3ListName);

                        // Log.d(TAG, "AdmCountryCode : " + AdmCountryCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : " + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR3ListName : " + LayR3ListName);
                    }
                }
                publishProgress(++progressIncremental);
                // Adding data into Village Table
                if (!jObj.isNull(Parser.VILLAGE_JSON_A)) {

                    JSONArray village = jObj.getJSONArray(Parser.VILLAGE_JSON_A);

                    size = village.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject vil = village.getJSONObject(i);

                        String AdmCountryCode = vil.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = vil.getString(Parser.GEO_LAY_R_CODE);
                        String LayR1ListCode = vil.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = vil.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = vil.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR4ListCode = vil.getString(Parser.LAY_R_4_LIST_CODE);
                        String LayR4ListName = vil.getString(Parser.LAY_R_4_LIST_NAME);
                        String HHCount = vil.getString(Parser.HH_COUNT);

                        db.addVillage(AdmCountryCode, GeoLayRCode, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode, LayR4ListName, HHCount);


                    }
                }
                publishProgress(++progressIncremental);

                // Adding data into Relation Table
                if (!jObj.isNull(Parser.RELATION_JSON_A)) {

                    JSONArray relation = jObj.getJSONArray(Parser.RELATION_JSON_A);

                    size = relation.length();

                    for (int i = 0; i < size; i++) {

                        JSONObject rel = relation.getJSONObject(i);

                        String Relation_Code = rel.getString(Parser.HH_RELATION_CODE);
                        String RelationName = rel.getString(Parser.RELATION_NAME);

                        db.addRelation(Relation_Code, RelationName);

//                        Log.d(TAG, "Relation_Code : " + Relation_Code + " RelationName : " + RelationName);
                    }
                }
                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.REPORT_TEMPLATE)) {
                    JSONArray report_templates = jObj.getJSONArray(Parser.REPORT_TEMPLATE);
                    size = report_templates.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject report_template = report_templates.getJSONObject(i);

                        String AdmCountryCode = report_template.getString(Parser.ADM_COUNTRY_CODE);
                        String RptLabel = report_template.getString(Parser.RPT_LABEL);
                        String Code = report_template.getString(Parser.RPT_G_N_CODE);

                        db.addCardType(AdmCountryCode, RptLabel, Code);

//                        Log.d(TAG, "In Report Template Table: RptLabel : " + RptLabel + " Code : " + Code);
                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.CARD_PRINT_REASON)) {
                    JSONArray card_print_reasons = jObj.getJSONArray(Parser.CARD_PRINT_REASON);
                    size = card_print_reasons.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject card_print_reason = card_print_reasons.getJSONObject(i);

                        String ReasonCode = card_print_reason.getString(Parser.REASON_CODE);
                        String ReasonTitle = card_print_reason.getString(Parser.REASON_TITLE);

                        db.addCardPrintReason(ReasonCode, ReasonTitle);

//                        Log.d(TAG, "In Card Reason Table: ReasonCode : " + ReasonCode + " ReasonTitle : " + ReasonTitle);
                    }
                }
//                publishProgress(33);
                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.REG_MEM_CARD_REQUEST_JSON_A)) {
                    JSONArray reg_mem_card_requests = jObj.getJSONArray(Parser.REG_MEM_CARD_REQUEST_JSON_A);
                    size = reg_mem_card_requests.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject reg_mem_card_request = reg_mem_card_requests.getJSONObject(i);

                        String AdmCountryCode = reg_mem_card_request.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = reg_mem_card_request.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = reg_mem_card_request.getString(Parser.ADM_AWARD_CODE);
                        String LayR1ListCode = reg_mem_card_request.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = reg_mem_card_request.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = reg_mem_card_request.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR4ListCode = reg_mem_card_request.getString(Parser.LAY_R_4_LIST_CODE);
                        String HHID = reg_mem_card_request.getString(Parser.HHID);
                        String MemID = reg_mem_card_request.getString(Parser.MEM_ID);
                        String RptGroup = reg_mem_card_request.getString(Parser.RPT_GROUP);
                        String RptCode = reg_mem_card_request.getString(Parser.RPT_CODE);
                        String RequestSL = reg_mem_card_request.getString(Parser.REQUEST_SL);
                        String ReasonCode = reg_mem_card_request.getString(Parser.REASON_CODE);
                        String RequestDate = reg_mem_card_request.getString(Parser.REQUEST_DATE);
                        String PrintDate = reg_mem_card_request.getString(Parser.PRINT_DATE);
                        String PrintBy = reg_mem_card_request.getString(Parser.PRINT_BY);
                        String DeliveryDate = reg_mem_card_request.getString(Parser.DELIVERY_DATE);
                        String DeliveredBy = reg_mem_card_request.getString(Parser.DELIVERED_BY);
                        String DelStatus = reg_mem_card_request.getString(Parser.DEL_STATUS);
                        String EntryBy = reg_mem_card_request.getString(Parser.ENTRY_BY);
                        String EntryDate = reg_mem_card_request.getString(Parser.ENTRY_DATE);

                        db.addCardRequestDataFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode,
                                HHID, MemID, RptGroup, RptCode, RequestSL, ReasonCode, RequestDate,
                                PrintDate, PrintBy, DeliveryDate, DeliveredBy, DelStatus, EntryBy, EntryDate);

                        //Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : " + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode + " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.STAFF_FDP_ACCESS_JSON_A)) {
                    JSONArray staff_fdp_accesses = jObj.getJSONArray(Parser.STAFF_FDP_ACCESS_JSON_A);
                    size = staff_fdp_accesses.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject staff_fdp_access = staff_fdp_accesses.getJSONObject(i);

                        String StfCode = staff_fdp_access.getString(Parser.STF_CODE);
                        String AdmCountryCode = staff_fdp_access.getString(Parser.ADM_COUNTRY_CODE);
                        String FDPCode = staff_fdp_access.getString(Parser.FDP_CODE);
                        String btnNew = staff_fdp_access.getString(Parser.BTN_NEW);
                        String btnSave = staff_fdp_access.getString(Parser.BTN_SAVE);
                        String btnDel = staff_fdp_access.getString(Parser.BTN_DEL);


                        db.addStaffFDPAccess(StfCode, AdmCountryCode, FDPCode, btnNew, btnSave, btnDel);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);

                if (!jObj.isNull(Parser.FDP_MASTER_JSON_A)) {
                    JSONArray fdp_masters = jObj.getJSONArray(Parser.FDP_MASTER_JSON_A);
                    size = fdp_masters.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject fdp_master = fdp_masters.getJSONObject(i);

                        String AdmCountryCode = fdp_master.getString(Parser.ADM_COUNTRY_CODE);
                        String FDPCode = fdp_master.getString(Parser.FDP_CODE);
                        String FDPName = fdp_master.getString(Parser.FDP_NAME);
                        String FDPCatCode = fdp_master.getString(Parser.FDP_CAT_CODE);
                        String WHCode = fdp_master.getString(Parser.WH_CODE);
                        String LayR1Code = fdp_master.getString(Parser.LAY_R_1_CODE);
                        String LayR2Code = fdp_master.getString(Parser.LAY_R_2_CODE);


                        db.addFDPMaster(AdmCountryCode, FDPCode, FDPName, FDPCatCode, WHCode, LayR1Code, LayR2Code);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);


             /*   if (!jObj.isNull(Parser.DISTRIBUTION_TABLE_JSON_A)) {
                    JSONArray distribution_tableDatas = jObj.getJSONArray(Parser.DISTRIBUTION_TABLE_JSON_A);
                    size = distribution_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject distribution_tableData = distribution_tableDatas.getJSONObject(i);
                        DistributionSaveDataModel data = new DistributionSaveDataModel();
                        data.setCountryCode(distribution_tableData.getString(Parser.ADM_COUNTRY_CODE));
                        data.setAdmDonorCode(distribution_tableData.getString(Parser.ADM_DONOR_CODE));
                        data.setAdmAwardCode(distribution_tableData.getString(Parser.ADM_AWARD_CODE));
                        data.setDistrictCode(distribution_tableData.getString(Parser.LAY_R_1_LIST_CODE));
                        data.setUpCode(distribution_tableData.getString(Parser.LAY_R_2_LIST_CODE));
                        data.setUniteCode(distribution_tableData.getString(Parser.LAY_R_3_LIST_CODE));
                        data.setVillageCode(distribution_tableData.getString(Parser.LAY_R_4_LIST_CODE));
                        data.setProgCode(distribution_tableData.getString(Parser.PROG_CODE));
                        data.setSrvCode(distribution_tableData.getString(Parser.SRV_CODE));
                        data.setOpMonthCode(distribution_tableData.getString(Parser.OP_MONTH_CODE));
                        data.setFDPCode(distribution_tableData.getString(Parser.FDP_CODE));
                        data.setID(distribution_tableData.getString(Parser.ID));
                        data.setDistStatus(distribution_tableData.getString(Parser.DIST_STATUS));
                        data.setSrvOpMonthCode(distribution_tableData.getString(Parser.SRV_OP_MONTH_CODE));
                        data.setDistFlag(distribution_tableData.getString(Parser.DIST_FLAG));
                        data.setWd(distribution_tableData.getString("WD"));

                        db.addInDistributionTableFormOnLine(data);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);*/


                if (!jObj.isNull(Parser.DISTRIBUTION_EXT_TABLE_JSON_A)) {
                    JSONArray distribution_ext_tableDatas = jObj.getJSONArray(Parser.DISTRIBUTION_EXT_TABLE_JSON_A);
                    size = distribution_ext_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject distribution_ext_tableData = distribution_ext_tableDatas.getJSONObject(i);
                        // DistributionSaveDataModel data = new DistributionSaveDataModel();

                        String AdmCountryCode = distribution_ext_tableData.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = distribution_ext_tableData.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = distribution_ext_tableData.getString(Parser.ADM_AWARD_CODE);
                        String LayR1ListCode = distribution_ext_tableData.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = distribution_ext_tableData.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = distribution_ext_tableData.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR4ListCode = distribution_ext_tableData.getString(Parser.LAY_R_4_LIST_CODE);
                        String ProgCode = distribution_ext_tableData.getString(Parser.PROG_CODE);
                        String SrvCode = distribution_ext_tableData.getString(Parser.SRV_CODE);
                        String OpMonthCode = distribution_ext_tableData.getString(Parser.OP_MONTH_CODE);
                        String FDPCode = distribution_ext_tableData.getString(Parser.FDP_CODE);
                        String ID = distribution_ext_tableData.getString(Parser.ID);
                        String VOItmSpec = distribution_ext_tableData.getString(Parser.VO_ITM_SPEC);
                        String VOItmUnit = distribution_ext_tableData.getString(Parser.VO_ITM_UNIT);
                        String VORefNumber = distribution_ext_tableData.getString(Parser.VO_REF_NUMBER);
                        String SrvOpMonthCode = distribution_ext_tableData.getString(Parser.SRV_OP_MONTH_CODE);
                        String DistFlag = distribution_ext_tableData.getString(Parser.DIST_FLAG);


                        //data.setDistStatus(distribution_ext_tableData.getString(DIST_STATUS);
                        // // TODO: 9/25/2016  create Method for download online

                        db.addInDistributionExtendedTable(AdmCountryCode, AdmDonorCode, AdmAwardCode,
                                LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode, ProgCode,
                                SrvCode, OpMonthCode, FDPCode, ID, VOItmSpec, VOItmUnit,
                                VORefNumber, SrvOpMonthCode, DistFlag,

                                "", "", "1");

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);

                if (!jObj.isNull("distbasic_table")) {
                    JSONArray distbasic_table = jObj.getJSONArray("distbasic_table");
                    size = distbasic_table.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject distbasic = distbasic_table.getJSONObject(i);
                        // DistributionSaveDataModel data = new DistributionSaveDataModel();

                        String AdmCountryCode = distbasic.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = distbasic.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = distbasic.getString(Parser.ADM_AWARD_CODE);
                        String ProgCode = distbasic.getString(Parser.PROG_CODE);
                        String OpCode = distbasic.getString("OpCode");
                        String SrvOpMonthCode = distbasic.getString("SrvOpMonthCode");
                        String DisOpMonthCode = distbasic.getString("DisOpMonthCode");
                        String FDPCode = distbasic.getString(Parser.FDP_CODE);
                        String DistFlag = distbasic.getString("DistFlag");
                        ///   String FoodFlag = distbasic.getString("FoodFlag");
                        String OrgCode = distbasic.getString("OrgCode");
                        String Distributor = distbasic.getString("Distributor");
                        String DistributionDate = distbasic.getString("DistributionDate");
                        String DeliveryDate = distbasic.getString("DeliveryDate");
                        String Status = distbasic.getString("Status");
                        String PreparedBy = distbasic.getString("PreparedBy");
                        String VerifiedBy = distbasic.getString("VerifiedBy");
                        String ApproveBy = distbasic.getString("ApproveBy");


                        //data.setDistStatus(distribution_ext_tableData.getString(DIST_STATUS);

                        db.addInDistributionNPlaneTable(AdmCountryCode, AdmDonorCode, AdmAwardCode, ProgCode,
                                OpCode, SrvOpMonthCode, DisOpMonthCode, FDPCode, DistFlag, OrgCode, Distributor,
                                DistributionDate, DeliveryDate, Status, PreparedBy, VerifiedBy, ApproveBy);


                      /*  Log.d(TAG, "AdmCountryCode: " + AdmCountryCode + AdmDonorCode + AdmAwardCode + ProgCode +
                                OpCode + SrvOpMonthCode + DisOpMonthCode + FDPCode + DistFlag + OrgCode + Distributor +
                                DistributionDate + DeliveryDate + Status + PreparedBy + VerifiedBy + ApproveBy);*/

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.LUP_SRV_OPTION_LIST)) {
                    JSONArray lup_srv_option_listDatas = jObj.getJSONArray(Parser.LUP_SRV_OPTION_LIST);
                    size = lup_srv_option_listDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_srv_option_listData = lup_srv_option_listDatas.getJSONObject(i);

                        String countryCode = lup_srv_option_listData.getString(Parser.ADM_COUNTRY_CODE);

                        String programCode = lup_srv_option_listData.getString(Parser.PROG_CODE);
                        String serviceCode = lup_srv_option_listData.getString(Parser.SRV_CODE);
                        String LUPOptionCode = lup_srv_option_listData.getString(Parser.LUP_OPTION_CODE);
                        String LUPOptionName = lup_srv_option_listData.getString(Parser.LUP_OPTION_NAME);


                        db.addInLupSrvOptionListFromOnline(countryCode, programCode, serviceCode, LUPOptionCode, LUPOptionName);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull("vo_itm_table")) {
                    JSONArray vo_itm_tableDatas = jObj.getJSONArray("vo_itm_table");
                    size = vo_itm_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject vo_itm_tableData = vo_itm_tableDatas.getJSONObject(i);
                        //AGR_DataModel data = new AGR_DataModel();
                        String CatCode = vo_itm_tableData.getString("CatCode");
                        String ItmCode = vo_itm_tableData.getString("ItmCode");
                        String ItmName = vo_itm_tableData.getString("ItmName");


                        db.addVoucherItemTableFromOnline(CatCode, ItmCode, ItmName);

//                        Log.d(TAG, "In Voucher item table : CatCode : " + CatCode + " ItmCode : " + ItmCode + " ItmName : " + ItmName);

                    }
                }

//                publishProgress(39);
                publishProgress(++progressIncremental);

                if (!jObj.isNull(Parser.VO_ITM_MEAS_TABLE_JSON_A)) {
                    JSONArray vo_itm_meas_tableDatas = jObj.getJSONArray(Parser.VO_ITM_MEAS_TABLE_JSON_A);
                    size = vo_itm_meas_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject vo_itm_meas_tableData = vo_itm_meas_tableDatas.getJSONObject(i);
                        //AGR_DataModel data = new AGR_DataModel();
                        String MeasRCode = vo_itm_meas_tableData.getString("MeasRCode");
                        String UnitMeas = vo_itm_meas_tableData.getString("UnitMeas");
                        String MeasTitle = vo_itm_meas_tableData.getString("MeasTitle");


                        db.addVoucherItemMeasFromOnline(MeasRCode, UnitMeas, MeasTitle);

//                        Log.d(TAG, "In Voucher item table : MeasRCode : " + MeasRCode + " UnitMeas : " + UnitMeas + " MeasTitle : " + MeasTitle);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("vo_country_prog_itm")) {
                    JSONArray vo_country_prog_itmDatas = jObj.getJSONArray("vo_country_prog_itm");
                    size = vo_country_prog_itmDatas.length();


                    String AdmCountryCode;
                    String AdmDonorCode;
                    String AdmAwardCode;
                    String AdmProgCode;
                    String AdmSrvCode;
                    String CatCode;
                    String ItmCode;
                    String MeasRCode;
                    String VOItmSpec;
                    String UnitCost;
                    String Active;
                    String Currency;
                    for (int i = 0; i < size; i++) {
                        JSONObject vo_country_prog_itmData = vo_country_prog_itmDatas.getJSONObject(i);
                        //AGR_DataModel data = new AGR_DataModel();
                        AdmCountryCode = vo_country_prog_itmData.getString("AdmCountryCode");
                        AdmDonorCode = vo_country_prog_itmData.getString("AdmDonorCode");
                        AdmAwardCode = vo_country_prog_itmData.getString("AdmAwardCode");
                        AdmProgCode = vo_country_prog_itmData.getString("AdmProgCode");
                        AdmSrvCode = vo_country_prog_itmData.getString("AdmSrvCode");
                        CatCode = vo_country_prog_itmData.getString("CatCode");
                        ItmCode = vo_country_prog_itmData.getString("ItmCode");
                        MeasRCode = vo_country_prog_itmData.getString("MeasRCode");
                        VOItmSpec = vo_country_prog_itmData.getString(Parser.VO_ITM_SPEC);
                        UnitCost = vo_country_prog_itmData.getString("UnitCost");
                        Active = vo_country_prog_itmData.getString("Active");
                        Currency = vo_country_prog_itmData.getString("Currency");


                        db.addVoucherCountryProgItemFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, AdmSrvCode, CatCode, ItmCode, MeasRCode, VOItmSpec, UnitCost, Active, Currency);

                        //                        Log.d(TAG, "In Voucher Country  Prog Item  table : AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " AdmAwardCode : " + AdmAwardCode);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.LUP_GPS_TABLE_JSON_A)) {
                    JSONArray lup_gps_table_Datas = jObj.getJSONArray(Parser.LUP_GPS_TABLE_JSON_A);
                    size = lup_gps_table_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_gps_tableData = lup_gps_table_Datas.getJSONObject(i);

                        String GrpCode = lup_gps_tableData.getString("GrpCode");
                        String SubGrpCode = lup_gps_tableData.getString("SubGrpCode");
                        String AttributeCode = lup_gps_tableData.getString("AttributeCode");
                        String LookUpCode = lup_gps_tableData.getString("LookUpCode");
                        String LookUpName = lup_gps_tableData.getString("LookUpName");


                        db.addLUP_GPS_TableFromOnline(GrpCode, SubGrpCode, AttributeCode, LookUpCode, LookUpName);
                       /* Log.d("NIR2", "addLUP_GPS_TableFromOnline : GrpCode : " + GrpCode + " SubGrpCode : "
                                + SubGrpCode + " AttributeCode : " + AttributeCode
                                + " LookUpCode : " + LookUpCode + " LookUpName : " + LookUpName
                        );*/

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_SUB_GROUP_ATTRIBUTES_JSON_A)) {
                    JSONArray gps_sub_group_attributes_Datas = jObj.getJSONArray(Parser.GPS_SUB_GROUP_ATTRIBUTES_JSON_A);
                    size = gps_sub_group_attributes_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject gps_sub_group_attributes_Data = gps_sub_group_attributes_Datas.getJSONObject(i);


                        String GrpCode = gps_sub_group_attributes_Data.getString("GrpCode");
                        String SubGrpCode = gps_sub_group_attributes_Data.getString("SubGrpCode");
                        String AttributeCode = gps_sub_group_attributes_Data.getString("AttributeCode");
                        String AttributeTitle = gps_sub_group_attributes_Data.getString("AttributeTitle");
                        String DataType = gps_sub_group_attributes_Data.getString("DataType");
                        String LookUpCode = gps_sub_group_attributes_Data.getString("LookUpCode");


                        db.addGPS_SubGroupAttributesFromOnline(GrpCode, SubGrpCode, AttributeCode, AttributeTitle, DataType, LookUpCode);
                        /*Log.d("NIR2", "addGPS_SubGroupAttributesFromOnline : GrpCode : " + GrpCode
                                + " SubGrpCode : " + SubGrpCode + " AttributeCode : " + AttributeCode
                                + " AttributeTitle : " + AttributeTitle + " DataType : " + DataType
                                + " LookUpCode : " + LookUpCode);*/

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_LOCATION_ATTRIBUTES_JSON_A)) {
                    JSONArray gps_location_attributes_Datas = jObj.getJSONArray(Parser.GPS_LOCATION_ATTRIBUTES_JSON_A);
                    size = gps_location_attributes_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject gps_location_attributes_Data = gps_location_attributes_Datas.getJSONObject(i);


                        String AdmCountryCode = gps_location_attributes_Data.getString("AdmCountryCode");
                        String GrpCode = gps_location_attributes_Data.getString("GrpCode");
                        String SubGrpCode = gps_location_attributes_Data.getString("SubGrpCode");
                        String LocationCode = gps_location_attributes_Data.getString("LocationCode");
                        String AttributeCode = gps_location_attributes_Data.getString("AttributeCode");
                        String AttributeValue = gps_location_attributes_Data.getString("AttributeValue");
                        String AttPhoto = gps_location_attributes_Data.getString("AttPhoto");

                        db.addGPSLocationAttributesFromOnline(AdmCountryCode, GrpCode, SubGrpCode, LocationCode, AttributeCode, AttributeValue, AttPhoto);
                     /*   Log.d("NIR2", "addGPS_SubGroupAttributesFromOnline : AdmCountryCode : " + AdmCountryCode
                                + " GrpCode : " + GrpCode + " SubGrpCode : " + SubGrpCode
                                + " LocationCode : " + LocationCode + " AttributeCode : " + AttributeCode

                                + " AttributeValue : " + AttributeValue
                        );*/

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("community_group")) {

                    Parser.CommunityGroupParser(jObj.getJSONArray("community_group"), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_community_animal")) {
                    JSONArray lup_community_animal_Datas = jObj.getJSONArray("lup_community_animal");
                    size = lup_community_animal_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_community_animal_Data = lup_community_animal_Datas.getJSONObject(i);


                        String AdmCountryCode = lup_community_animal_Data.getString("AdmCountryCode");
                        String AdmDonorCode = lup_community_animal_Data.getString("AdmDonorCode");
                        String AdmAwardCode = lup_community_animal_Data.getString("AdmAwardCode");
                        String AdmProgCode = lup_community_animal_Data.getString("AdmProgCode");
                        String AnimalCode = lup_community_animal_Data.getString("AnimalCode");
                        String AnimalType = lup_community_animal_Data.getString("AnimalType");


                        db.addLUP_AnimalTypeFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, AnimalCode, AnimalType);


                    }
                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_prog_group_crop")) {
                    Parser.lupProgGroupCropParser(jObj.getJSONArray("lup_prog_group_crop"), db);
                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_community_loan_source")) {
                    JSONArray lup_community_loan_source_Datas = jObj.getJSONArray("lup_community_loan_source");
                    size = lup_community_loan_source_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_community_loan_source_Data = lup_community_loan_source_Datas.getJSONObject(i);


                        String AdmCountryCode = lup_community_loan_source_Data.getString("AdmCountryCode");
                        String AdmDonorCode = lup_community_loan_source_Data.getString("AdmDonorCode");
                        String AdmAwardCode = lup_community_loan_source_Data.getString("AdmAwardCode");
                        String AdmProgCode = lup_community_loan_source_Data.getString("AdmProgCode");
                        String LoanCode = lup_community_loan_source_Data.getString("LoanCode");
                        String LoanSource = lup_community_loan_source_Data.getString("LoanSource");


                /*        Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "AdmProgCode:" + AdmProgCode
                                + " LoanCode: " + LoanCode + "LoanSource:" + LoanSource);
*/
                        db.addLUP_CommunityLoanSource(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode
                                , LoanCode, LoanSource);
                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_community__lead_position")) {
                    JSONArray lup_community_lead_position_Datas = jObj.getJSONArray("lup_community__lead_position");
                    size = lup_community_lead_position_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_community_lead_position_Data = lup_community_lead_position_Datas.getJSONObject(i);


                        String AdmCountryCode = lup_community_lead_position_Data.getString("AdmCountryCode");
                        String AdmDonorCode = lup_community_lead_position_Data.getString("AdmDonorCode");
                        String AdmAwardCode = lup_community_lead_position_Data.getString("AdmAwardCode");
                        String AdmProgCode = lup_community_lead_position_Data.getString("AdmProgCode");
                        String LeadCode = lup_community_lead_position_Data.getString("LeadCode");
                        String LeadPosition = lup_community_lead_position_Data.getString("LeadPosition");


              /*          Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "AdmProgCode:" + AdmProgCode
                                + " LeadCode: " + LeadCode + "LeadPosition:" + LeadPosition);*/
                        db.addLUP_CommunityLeadPostition(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, LeadCode, LeadPosition);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("community_group_category")) {
                    JSONArray community_group_category_Datas = jObj.getJSONArray("community_group_category");
                    size = community_group_category_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject community_group_category_Data = community_group_category_Datas.getJSONObject(i);


                        String AdmCountryCode = community_group_category_Data.getString("AdmCountryCode");
                        String AdmDonorCode = community_group_category_Data.getString("AdmDonorCode");
                        String AdmAwardCode = community_group_category_Data.getString("AdmAwardCode");
                        String AdmProgCode = community_group_category_Data.getString("AdmProgCode");
                        String GrpCatCode = community_group_category_Data.getString("GrpCatCode");
                        String GrpCatName = community_group_category_Data.getString("GrpCatName");
                        String GrpCatShortName = community_group_category_Data.getString("GrpCatShortName");


           /*             Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "AdmProgCode:" + AdmProgCode
                                + " GrpCatCode: " + GrpCatCode + " GrpCatName:" + GrpCatName
                                + " GrpCatShortName: " + GrpCatShortName
                        );*/
                        db.addCommunityGroupCategoryFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, GrpCatCode, GrpCatName, GrpCatShortName);

                    }
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_reg_n_add_lookup")) {
                    JSONArray lup_reg_n_add_lookup = jObj.getJSONArray("lup_reg_n_add_lookup");
                    size = lup_reg_n_add_lookup.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject Lup_reg_n_add_lookup = lup_reg_n_add_lookup.getJSONObject(i);


                        String AdmCountryCode = Lup_reg_n_add_lookup.getString("AdmCountryCode");
                        String RegNAddLookupCode = Lup_reg_n_add_lookup.getString("RegNAddLookupCode");
                        String RegNAddLookup = Lup_reg_n_add_lookup.getString("RegNAddLookup");
                        String LayR1ListCode = Lup_reg_n_add_lookup.getString("LayR1ListCode");
                        String LayR2ListCode = Lup_reg_n_add_lookup.getString("LayR2ListCode");
                        String LayR3ListCode = Lup_reg_n_add_lookup.getString("LayR3ListCode");
                        String LayR4ListCode = Lup_reg_n_add_lookup.getString("LayR4ListCode");

/*
                        Log.d("InTest", "AdmCountryCode:" + AdmCountryCode + "RegNAddLookupCode" + RegNAddLookupCode
                                + "RegNAddLookup: " + RegNAddLookup + "LayR1ListCode : " + LayR1ListCode + "LayR2ListCode:" + LayR2ListCode
                                + " LayR3ListCode: " + LayR3ListCode + "LayR4ListCode:" + LayR4ListCode);*/
                        db.addLUP_RegNAddLookup(AdmCountryCode, RegNAddLookupCode, RegNAddLookup, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("prog_org_n_role")) {
                    JSONArray prog_org_n_role_Datas = jObj.getJSONArray("prog_org_n_role");
                    size = prog_org_n_role_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject prog_org_n_role_Data = prog_org_n_role_Datas.getJSONObject(i);
                        String AdmCountryCode = prog_org_n_role_Data.getString("AdmCountryCode");
                        String AdmDonorCode = prog_org_n_role_Data.getString("AdmDonorCode");
                        String AdmAwardCode = prog_org_n_role_Data.getString("AdmAwardCode");
                        String OrgNCode = prog_org_n_role_Data.getString("OrgNCode");
                        String PrimeYN = prog_org_n_role_Data.getString("PrimeYN");
                        String SubYN = prog_org_n_role_Data.getString("SubYN");
                        String TechYN = prog_org_n_role_Data.getString("TechYN");
                        String LogYN = prog_org_n_role_Data.getString("LogYN");
                        String ImpYN = prog_org_n_role_Data.getString("ImpYN");
                        String OthYN = prog_org_n_role_Data.getString("OthYN");
       /*                 Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "OrgNCode:" + OrgNCode
                                + " PrimeYN: " + PrimeYN + " SubYN:" + SubYN
                                + " TechYN: " + TechYN
                                + " LogYN: " + LogYN + "ImpYN:" + ImpYN + " OthYN: " + OthYN
                        );*/
                        db.insertIntoProgOrgNRole(AdmCountryCode, AdmDonorCode, AdmAwardCode, OrgNCode, PrimeYN, SubYN, TechYN, ImpYN, LogYN, OthYN);
                    }
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull("org_n_code")) {
                    JSONArray org_n_code_Datas = jObj.getJSONArray("org_n_code");
                    size = org_n_code_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject org_n_code_Data = org_n_code_Datas.getJSONObject(i);
                        String OrgNCode = org_n_code_Data.getString("OrgNCode");
                        String OrgNName = org_n_code_Data.getString("OrgNName");
                        String OrgNShortName = org_n_code_Data.getString("OrgNShortName");
                        Log.d("ShuvoTag", "OrgNName:" + OrgNName + "OrgNShortName:" + OrgNShortName);
                        db.insertIntoProgOrgN(OrgNCode, OrgNName, OrgNShortName);
                    }
                }
//// TODO: 10/18/2016  this parsing of the group detail set to parse class

                publishProgress(++progressIncremental);
                if (!jObj.isNull("community_grp_detail")) {

                    Parser.CommunityGroupDetailsParser(jObj.getJSONArray("community_grp_detail"), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("staff_master")) {
                    JSONArray staff_master_Datas = jObj.getJSONArray("staff_master");
                    size = staff_master_Datas.length();

                    String StfCode;
                    String OrigAdmCountryCode;
                    String StfName;
                    String OrgNCode;
                    String OrgNDesgNCode;
                    String StfStatus;
                    String StfCategory;
                    String UsrLogInName;
                    String UsrLogInPW;
                    String StfAdminRole;
                    for (int i = 0; i < size; i++) {
                        JSONObject staff_master_Data = staff_master_Datas.getJSONObject(i);


                        StfCode = staff_master_Data.getString("StfCode");
                        OrigAdmCountryCode = staff_master_Data.getString("OrigAdmCountryCode");
                        StfName = staff_master_Data.getString("StfName");
                        OrgNCode = staff_master_Data.getString("OrgNCode");
                        OrgNDesgNCode = staff_master_Data.getString("OrgNDesgNCode");
                        StfStatus = staff_master_Data.getString("StfStatus");
                        StfCategory = staff_master_Data.getString("StfCategory");
                        UsrLogInName = staff_master_Data.getString("UsrLogInName");
                        UsrLogInPW = staff_master_Data.getString("UsrLogInPW");
                        StfAdminRole = staff_master_Data.getString("StfAdminRole");


                        db.insertIntoStaffMasterTable(StfCode, OrigAdmCountryCode, StfName, OrgNCode, OrgNDesgNCode, StfStatus, StfCategory, UsrLogInName, UsrLogInPW, StfAdminRole);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("gps_lup_list")) {
                    JSONArray gps_lup_list_data = jObj.getJSONArray("gps_lup_list");
                    size = gps_lup_list_data.length();

                    String GrpCode;
                    String SubGrpCode;
                    String AttributeCode;
                    String LupValueCode;
                    String LupValueText;

                    for (int i = 0; i < size; i++) {
                        JSONObject gps_lup_list = gps_lup_list_data.getJSONObject(i);


                        GrpCode = gps_lup_list.getString("GrpCode");
                        SubGrpCode = gps_lup_list.getString("SubGrpCode");
                        AttributeCode = gps_lup_list.getString("AttributeCode");
                        LupValueCode = gps_lup_list.getString("LupValueCode");
                        LupValueText = gps_lup_list.getString("LupValueText");


                        db.insertIntoLupGpsList(GrpCode, SubGrpCode, AttributeCode, LupValueCode, LupValueText);

                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog();
            progressIncremental = 0;
        }

        @Override
        protected void onPostExecute(Void string) {

            new Inject_DynamicTableIntoSQLite().execute();
        }
    }


   /* private class Inject_All_DataIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            *//**
     * Read JSON DATA  from the text file
     * *//*
            String retrieveData = readDataFromFile(ALL_DATA);

            try {
                int size;
                */

    /**
     * The total string Convert into JSON object
     *//*

                JSONObject jObj = new JSONObject(retrieveData);

                String user_id = jObj.getString(Parser.USR_ID);
                JSONObject user = jObj.getJSONObject(Parser.USER_JSON_A);
                String country_code = user.getString(Parser.ADM_COUNTRY_CODE);
                String login_name = user.getString(Parser.USR_LOG_IN_NAME);
                String login_pw = user.getString(Parser.USR_LOG_IN_PW);
                String first_name = user.getString(Parser.USR_FIRST_NAME);
                String last_name = user.getString(Parser.USR_LAST_NAME);
                String email = user.getString(Parser.USR_EMAIL);
                String email_verification = user.getString(Parser.USR_EMAIL_VERIFICATION);
                String user_status = user.getString(Parser.USR_STATUS);
                String entry_by = user.getString(Parser.ENTRY_BY);
                String entry_date = user.getString(Parser.ENTRY_DATE);

                setUserName(first_name); // Setting User hhName into session
                setStaffID(user_id); // Setting Staff ID to use when sync data


                setUserCountryCode(country_code); // Setting Country code


                db.addUser(user_id, country_code, login_name, login_pw, first_name, last_name, email, email_verification, user_status, entry_by, entry_date);
//                Log.d("MOR_12",user_id +  country_code +  login_name +  login_pw +  first_name +  last_name +  email +  email_verification +  user_status +  entry_by +  entry_date);


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.COUNTRIES_JSON_A)) {

                    Parser.AdmCountryParser(jObj.getJSONArray(Parser.COUNTRIES_JSON_A), db);
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.VALID_DATES_JSON_A)) {
                    JSONArray valid_dates = jObj.getJSONArray(Parser.VALID_DATES_JSON_A);
                    size = valid_dates.length();

                    for (int i = 0; i < size; i++) {
                        JSONObject valid_date = valid_dates.getJSONObject(i);
                        String AdmCountryCode = valid_date.getString(Parser.ADM_COUNTRY_CODE);
                        String StartDate = valid_date.getString(Parser.START_DATE);
                        String EndDate = valid_date.getString(Parser.END_DATE);

                        db.addValidDateRange(AdmCountryCode, StartDate, EndDate);


                    }
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_GROUP_JSON_A)) {
                    Parser.gpsGroupParser(jObj.getJSONArray(Parser.GPS_GROUP_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_SUBGROUP_JSON_A)) {
                    Parser.gpsSubGroupParser(jObj.getJSONArray(Parser.GPS_SUBGROUP_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_LOCATION_JSON_A)) {
                    Parser.gpsLocationParse(jObj.getJSONArray(Parser.GPS_LOCATION_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_COUNTRY_AWARD_JSON_A)) {
                    Parser.admCountryAwardParser(jObj.getJSONArray(Parser.ADM_COUNTRY_AWARD_JSON_A), db);
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_AWARD_JSON_A)) {
                    Parser.admAwardParser(jObj.getJSONArray(Parser.ADM_AWARD_JSON_A), db);
                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_DONOR_JSON_A)) {
                    Parser.admDonorParser(jObj.getJSONArray(Parser.ADM_DONOR_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_PROGRAM_MASTER_JSON_A)) {
                    Parser.admProgramMasterParser(jObj.getJSONArray(Parser.ADM_PROGRAM_MASTER_JSON_A), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.ADM_SERVICE_MASTER_JSON_A)) {
                    Parser.admServiceMasterParser(jObj.getJSONArray(Parser.ADM_SERVICE_MASTER_JSON_A), db);

                }

                publishProgress(++progressIncremental);
                //adm_op_month


                if (!jObj.isNull(Parser.ADM_OP_MONTH_JSON_A)) {
                    JSONArray adm_op_months = jObj.getJSONArray(Parser.ADM_OP_MONTH_JSON_A);
                    size = adm_op_months.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject adm_op_month = adm_op_months.getJSONObject(i);

                        String AdmCountryCode = adm_op_month.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = adm_op_month.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = adm_op_month.getString(Parser.ADM_AWARD_CODE);
                        String OpCode = adm_op_month.getString(Parser.OP_CODE);
                        String OpMonthCode = adm_op_month.getString(Parser.OP_MONTH_CODE);
                        String MonthLabel = adm_op_month.getString(Parser.MONTH_LABEL);
                        String StartDate = adm_op_month.getString(Parser.START_DATE);
                        String EndDate = adm_op_month.getString(Parser.END_DATE);

                        String UsaStartDate = adm_op_month.getString(Parser.USA_START_DATE);
                        String UsaEndDate = adm_op_month.getString(Parser.USA_END_DATE);
                        String Status = adm_op_month.getString("Status");
                        db.addOpMonthFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, OpCode, OpMonthCode, MonthLabel, StartDate, EndDate, UsaStartDate, UsaEndDate, Status);


                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.ADM_COUNTRY_PROGRAM_JSON_A)) {
                    JSONArray adm_country_programs = jObj.getJSONArray(Parser.ADM_COUNTRY_PROGRAM_JSON_A);
                    size = adm_country_programs.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject adm_country_program = adm_country_programs.getJSONObject(i);
                        String AdmCountryCode = adm_country_program.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = adm_country_program.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = adm_country_program.getString(Parser.ADM_AWARD_CODE);
                        String AdmProgCode = adm_country_program.getString(Parser.ADM_PROG_CODE);
                        String AdmSrvCode = adm_country_program.getString(Parser.ADM_SRV_CODE);
                        String ProgFlag = adm_country_program.getString("ProgFlag");
                        String FoodFlag = adm_country_program.getString(Parser.FOOD_FLAG);
                        String NFoodFlag = adm_country_program.getString(Parser.N_FOOD_FLAG);
                        String CashFlag = adm_country_program.getString(Parser.CASH_FLAG);
                        String VOFlag = adm_country_program.getString(Parser.VO_FLAG);
                        String DefaultFoodDays = adm_country_program.getString(Parser.DEFAULT_FOOD_DAYS);
                        String DefaultNFoodDays = adm_country_program.getString(Parser.DEFAULT_N_FOOD_DAYS);
                        String DefaultCashDays = adm_country_program.getString(Parser.DEFAULT_CASH_DAYS);
                        String DefaultVODays = adm_country_program.getString(Parser.DEFAULT_VO_DAYS);
                        String SrvSpecific = adm_country_program.getString(Parser.SRV_SPECIFIC);

                        db.insertAdmCountryProgram(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, AdmSrvCode, ProgFlag, FoodFlag, NFoodFlag, CashFlag, VOFlag, DefaultFoodDays, DefaultNFoodDays, DefaultCashDays, DefaultVODays, SrvSpecific);


                    }
                }

                publishProgress(++progressIncremental);


                // * Adding data into  dob_service_center  Table


                if (!jObj.isNull(Parser.DOB_SERVICE_CENTER_JSON_A)) {// this is not servie
                    JSONArray dob_service_centers = jObj.getJSONArray(Parser.DOB_SERVICE_CENTER_JSON_A);
                    size = dob_service_centers.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject dob_service_center = dob_service_centers.getJSONObject(i);

                        String AdmCountryCode = dob_service_center.getString(Parser.ADM_COUNTRY_CODE);
                        String SrvCenterCode = dob_service_center.getString(Parser.SRV_CENTER_CODE);
                        String SrvCenterName = dob_service_center.getString(Parser.SRV_CENTER_NAME);
                        // String SrvCenterAddress = dob_service_center.getString("SrvCenterAddress");
                        //   String SrvCenterCatCode = dob_service_center.getString("SrvCenterCatCode");

                        String FDPCode = dob_service_center.getString(Parser.FDP_CODE);


                        db.addServiceCenter(AdmCountryCode, SrvCenterCode, SrvCenterName, FDPCode);

//                        Log.d("NIR1", "In Service Center Table - AdmCountryCode :" + AdmCountryCode + " SrvCenterCode : " + SrvCenterCode + " SrvCenterName : " + SrvCenterName);
                        //+ " SrvCenterAddress : " + SrvCenterAddress + " SrvCenterCatCode  : " + SrvCenterCatCode + " FDPCode  : " + FDPCode );
                    }
                }
                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.STAFF_ACCESS_INFO_JSON_A)) {// this is not servie
                    JSONArray staff_access_info_accesses = jObj.getJSONArray(Parser.STAFF_ACCESS_INFO_JSON_A);
                    size = staff_access_info_accesses.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject staff_access_info_access = staff_access_info_accesses.getJSONObject(i);

                        String StfCode = staff_access_info_access.getString(Parser.STF_CODE);
                        String AdmCountryCode = staff_access_info_access.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = staff_access_info_access.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = staff_access_info_access.getString(Parser.ADM_AWARD_CODE);
                        String LayRListCode = staff_access_info_access.getString(Parser.LAY_R_LIST_CODE);
                        String btnNew = staff_access_info_access.getString(Parser.BTN_NEW1);
                        String btnSave = staff_access_info_access.getString(Parser.BTN_SAVE);
                        String btnDel = staff_access_info_access.getString(Parser.BTN_DEL);
                        String btnPepr = staff_access_info_access.getString(Parser.BTN_PEPR);
                        String btnAprv = staff_access_info_access.getString(Parser.BTN_APRV);
                        String btnRevw = staff_access_info_access.getString(Parser.BTN_REVW);
                        String btnVrfy = staff_access_info_access.getString(Parser.BTN_VRFY);
                        String btnDTran = staff_access_info_access.getString(Parser.BTN_D_TRAN);


                        //String FDPCode = dbo_staff_geo_info_access.getString("FDPCode");
                        String disCode = LayRListCode.substring(0, 2);
                        String upCode = LayRListCode.substring(2, 4);
                        String unCode = LayRListCode.substring(4, 6);
                        String vCode = LayRListCode.substring(6);
                        db.addStaffGeoAccessInfoFromOnline(StfCode, AdmCountryCode, AdmDonorCode, AdmAwardCode, LayRListCode, disCode, upCode, unCode, vCode, btnNew, btnSave, btnDel, btnPepr, btnAprv, btnRevw, btnVrfy, btnDTran);//, SrvCenterCatCode, FDPCode);


                        //  Log.d(TAG, "In addStaffGeoAccessInfoFromOnline Table- StfCode :" + StfCode + " AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode +               " AdmAwardCode : " + AdmAwardCode + " LayRListCode  : " + LayRListCode);// + " FDPCode  : " + FDPCode );
                    }
                }


                if (!jObj.isNull(Parser.LB_REG_HH_CATEGORY_JSON_A)) {
                    JSONArray lb_reg_hh_categorys = jObj.getJSONArray(Parser.LB_REG_HH_CATEGORY_JSON_A);
                    size = lb_reg_hh_categorys.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lb_reg_hh_category = lb_reg_hh_categorys.getJSONObject(i);
                        String AdmCountryCode = lb_reg_hh_category.getString(Parser.ADM_COUNTRY_CODE);
                        String HHHeadCatCode = lb_reg_hh_category.getString(Parser.HH_HEAD_CAT_CODE);
                        String CatName = lb_reg_hh_category.getString(Parser.CAT_NAME);
                        db.addHHCategory(AdmCountryCode, HHHeadCatCode, CatName);


                        // Log.d(TAG, "In House hold Category Table: AdmCountryCode : " + AdmCountryCode + " HHHeadCatCode : " + HHHeadCatCode +" SubGrpName : " + SubGrpName + " Description : " + Description  );
                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.REG_LUP_GRADUATION_JSON_A)) {
                    JSONArray reg_lup_graduations = jObj.getJSONArray(Parser.REG_LUP_GRADUATION_JSON_A);
                    size = reg_lup_graduations.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject reg_lup_graduation = reg_lup_graduations.getJSONObject(i);

                        String AdmProgCode = reg_lup_graduation.getString(Parser.ADM_PROG_CODE);
                        String AdmSrvCode = reg_lup_graduation.getString(Parser.ADM_SRV_CODE);
                        String GRDCode = reg_lup_graduation.getString(Parser.GRD_CODE);
                        String GRDTitle = reg_lup_graduation.getString(Parser.GRD_TITLE);
                        String DefaultCatActive = reg_lup_graduation.getString(Parser.DEFAULT_CAT_ACTIVE);
                        String DefaultCatExit = reg_lup_graduation.getString(Parser.DEFAULT_CAT_EXIT);


                        db.addGraduation(AdmProgCode, AdmSrvCode, GRDCode, GRDTitle, DefaultCatActive, DefaultCatExit);


                        // Log.d(TAG, "In reg_lup_graduation: AdmProgCode : " + AdmProgCode + " AdmSrvCode : " + AdmSrvCode + " GRDCode : " + GRDCode + " GRDTitle : " + GRDTitle + " DefaultCatActive : " + DefaultCatActive+ " DefaultCatExit : " + DefaultCatExit   );
                    }
                }
                publishProgress(++progressIncremental);
                // Adding data into Layer Label Table
                if (!jObj.isNull(Parser.LAYER_LABELS_JSON_A)) {
                    JSONArray layer_labels = jObj.getJSONArray(Parser.LAYER_LABELS_JSON_A);
                    size = layer_labels.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject layer_label = layer_labels.getJSONObject(i);

                        String AdmCountryCode = layer_label.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = layer_label.getString(Parser.GEO_LAY_R_CODE);
                        String GeoLayRName = layer_label.getString(Parser.GEO_LAY_R_NAME);
                        db.addLayerLabel(AdmCountryCode, GeoLayRCode, GeoLayRName);


                    }
                }
                publishProgress(++progressIncremental);

                // Adding data into District Table
                if (!jObj.isNull(Parser.DISTRICT)) {
                    JSONArray district = jObj.getJSONArray(Parser.DISTRICT);
                    size = district.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject dist = district.getJSONObject(i);

                        String AdmCountryCode = dist.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = dist.getString(Parser.GEO_LAY_R_CODE);
                        String LayRListCode = dist.getString(Parser.LAY_R_LIST_CODE);
                        String LayRListName = dist.getString(Parser.LAY_R_LIST_NAME);

                        db.addDistrict(AdmCountryCode, GeoLayRCode, LayRListCode, LayRListName);


                    }
                }
                publishProgress(++progressIncremental);
                // Adding data into Upazilla Table
                if (!jObj.isNull(Parser.UPAZILLA)) {

                    JSONArray upazilla = jObj.getJSONArray(Parser.UPAZILLA);

                    size = upazilla.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject up = upazilla.getJSONObject(i);

                        String AdmCountryCode = up.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = up.getString(Parser.GEO_LAY_R_CODE);
                        String LayR1ListCode = up.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = up.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR2ListName = up.getString(Parser.LAY_R_2_LIST_NAME);

                        db.addUpazilla(AdmCountryCode, GeoLayRCode, LayR1ListCode, LayR2ListCode, LayR2ListName);

                        //  Log.d(TAG, "AdmCountryCode : " + AdmCountryCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : " + LayR2ListCode + " LayR2ListName : " + LayR2ListName);
                    }
                }
                publishProgress(++progressIncremental);

                // Adding data into Unit Table
                if (!jObj.isNull(Parser.UNIT_JSON_A)) {

                    JSONArray unit = jObj.getJSONArray(Parser.UNIT_JSON_A);
                    size = unit.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject un = unit.getJSONObject(i);

                        String AdmCountryCode = un.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = un.getString(Parser.GEO_LAY_R_CODE);
                        String LayR1ListCode = un.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = un.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = un.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR3ListName = un.getString(Parser.LAY_R_3_LIST_NAME);

                        db.addUnit(AdmCountryCode, GeoLayRCode, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR3ListName);

                        // Log.d(TAG, "AdmCountryCode : " + AdmCountryCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : " + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR3ListName : " + LayR3ListName);
                    }
                }
                publishProgress(++progressIncremental);
                // Adding data into Village Table
                if (!jObj.isNull(Parser.VILLAGE_JSON_A)) {

                    JSONArray village = jObj.getJSONArray(Parser.VILLAGE_JSON_A);

                    size = village.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject vil = village.getJSONObject(i);

                        String AdmCountryCode = vil.getString(Parser.ADM_COUNTRY_CODE);
                        String GeoLayRCode = vil.getString(Parser.GEO_LAY_R_CODE);
                        String LayR1ListCode = vil.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = vil.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = vil.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR4ListCode = vil.getString(Parser.LAY_R_4_LIST_CODE);
                        String LayR4ListName = vil.getString(Parser.LAY_R_4_LIST_NAME);
                        String HHCount = vil.getString(Parser.HH_COUNT);

                        db.addVillage(AdmCountryCode, GeoLayRCode, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode, LayR4ListName, HHCount);


                    }
                }
                publishProgress(++progressIncremental);

                // Adding data into Relation Table
                if (!jObj.isNull(Parser.RELATION_JSON_A)) {

                    JSONArray relation = jObj.getJSONArray(Parser.RELATION_JSON_A);

                    size = relation.length();

                    for (int i = 0; i < size; i++) {

                        JSONObject rel = relation.getJSONObject(i);

                        String Relation_Code = rel.getString(Parser.HH_RELATION_CODE);
                        String RelationName = rel.getString(Parser.RELATION_NAME);

                        db.addRelation(Relation_Code, RelationName);

//                        Log.d(TAG, "Relation_Code : " + Relation_Code + " RelationName : " + RelationName);
                    }
                }
                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.REPORT_TEMPLATE)) {
                    JSONArray report_templates = jObj.getJSONArray(Parser.REPORT_TEMPLATE);
                    size = report_templates.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject report_template = report_templates.getJSONObject(i);

                        String AdmCountryCode = report_template.getString(Parser.ADM_COUNTRY_CODE);
                        String RptLabel = report_template.getString(Parser.RPT_LABEL);
                        String Code = report_template.getString(Parser.RPT_G_N_CODE);

                        db.addCardType(AdmCountryCode, RptLabel, Code);

//                        Log.d(TAG, "In Report Template Table: RptLabel : " + RptLabel + " Code : " + Code);
                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.CARD_PRINT_REASON)) {
                    JSONArray card_print_reasons = jObj.getJSONArray(Parser.CARD_PRINT_REASON);
                    size = card_print_reasons.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject card_print_reason = card_print_reasons.getJSONObject(i);

                        String ReasonCode = card_print_reason.getString(Parser.REASON_CODE);
                        String ReasonTitle = card_print_reason.getString(Parser.REASON_TITLE);

                        db.addCardPrintReason(ReasonCode, ReasonTitle);

//                        Log.d(TAG, "In Card Reason Table: ReasonCode : " + ReasonCode + " ReasonTitle : " + ReasonTitle);
                    }
                }
//                publishProgress(33);
                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.REG_MEM_CARD_REQUEST_JSON_A)) {
                    JSONArray reg_mem_card_requests = jObj.getJSONArray(Parser.REG_MEM_CARD_REQUEST_JSON_A);
                    size = reg_mem_card_requests.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject reg_mem_card_request = reg_mem_card_requests.getJSONObject(i);

                        String AdmCountryCode = reg_mem_card_request.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = reg_mem_card_request.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = reg_mem_card_request.getString(Parser.ADM_AWARD_CODE);
                        String LayR1ListCode = reg_mem_card_request.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = reg_mem_card_request.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = reg_mem_card_request.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR4ListCode = reg_mem_card_request.getString(Parser.LAY_R_4_LIST_CODE);
                        String HHID = reg_mem_card_request.getString(Parser.HHID);
                        String MemID = reg_mem_card_request.getString(Parser.MEM_ID);
                        String RptGroup = reg_mem_card_request.getString(Parser.RPT_GROUP);
                        String RptCode = reg_mem_card_request.getString(Parser.RPT_CODE);
                        String RequestSL = reg_mem_card_request.getString(Parser.REQUEST_SL);
                        String ReasonCode = reg_mem_card_request.getString(Parser.REASON_CODE);
                        String RequestDate = reg_mem_card_request.getString(Parser.REQUEST_DATE);
                        String PrintDate = reg_mem_card_request.getString(Parser.PRINT_DATE);
                        String PrintBy = reg_mem_card_request.getString(Parser.PRINT_BY);
                        String DeliveryDate = reg_mem_card_request.getString(Parser.DELIVERY_DATE);
                        String DeliveredBy = reg_mem_card_request.getString(Parser.DELIVERED_BY);
                        String DelStatus = reg_mem_card_request.getString(Parser.DEL_STATUS);
                        String EntryBy = reg_mem_card_request.getString(Parser.ENTRY_BY);
                        String EntryDate = reg_mem_card_request.getString(Parser.ENTRY_DATE);

                        db.addCardRequestDataFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode,
                                HHID, MemID, RptGroup, RptCode, RequestSL, ReasonCode, RequestDate,
                                PrintDate, PrintBy, DeliveryDate, DeliveredBy, DelStatus, EntryBy, EntryDate);

                        //Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : " + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode + " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.STAFF_FDP_ACCESS_JSON_A)) {
                    JSONArray staff_fdp_accesses = jObj.getJSONArray(Parser.STAFF_FDP_ACCESS_JSON_A);
                    size = staff_fdp_accesses.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject staff_fdp_access = staff_fdp_accesses.getJSONObject(i);

                        String StfCode = staff_fdp_access.getString(Parser.STF_CODE);
                        String AdmCountryCode = staff_fdp_access.getString(Parser.ADM_COUNTRY_CODE);
                        String FDPCode = staff_fdp_access.getString(Parser.FDP_CODE);
                        String btnNew = staff_fdp_access.getString(Parser.BTN_NEW);
                        String btnSave = staff_fdp_access.getString(Parser.BTN_SAVE);
                        String btnDel = staff_fdp_access.getString(Parser.BTN_DEL);


                        db.addStaffFDPAccess(StfCode, AdmCountryCode, FDPCode, btnNew, btnSave, btnDel);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);

                if (!jObj.isNull(Parser.FDP_MASTER_JSON_A)) {
                    JSONArray fdp_masters = jObj.getJSONArray(Parser.FDP_MASTER_JSON_A);
                    size = fdp_masters.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject fdp_master = fdp_masters.getJSONObject(i);

                        String AdmCountryCode = fdp_master.getString(Parser.ADM_COUNTRY_CODE);
                        String FDPCode = fdp_master.getString(Parser.FDP_CODE);
                        String FDPName = fdp_master.getString(Parser.FDP_NAME);
                        String FDPCatCode = fdp_master.getString(Parser.FDP_CAT_CODE);
                        String WHCode = fdp_master.getString(Parser.WH_CODE);
                        String LayR1Code = fdp_master.getString(Parser.LAY_R_1_CODE);
                        String LayR2Code = fdp_master.getString(Parser.LAY_R_2_CODE);


                        db.addFDPMaster(AdmCountryCode, FDPCode, FDPName, FDPCatCode, WHCode, LayR1Code, LayR2Code);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }

*//*

                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.DISTRIBUTION_TABLE_JSON_A)) {
                    JSONArray distribution_tableDatas = jObj.getJSONArray(Parser.DISTRIBUTION_TABLE_JSON_A);
                    size = distribution_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject distribution_tableData = distribution_tableDatas.getJSONObject(i);
                        DistributionSaveDataModel data = new DistributionSaveDataModel();
                        data.setCountryCode(distribution_tableData.getString(Parser.ADM_COUNTRY_CODE));
                        data.setAdmDonorCode(distribution_tableData.getString(Parser.ADM_DONOR_CODE));
                        data.setAdmAwardCode(distribution_tableData.getString(Parser.ADM_AWARD_CODE));
                        data.setDistrictCode(distribution_tableData.getString(Parser.LAY_R_1_LIST_CODE));
                        data.setUpCode(distribution_tableData.getString(Parser.LAY_R_2_LIST_CODE));
                        data.setUniteCode(distribution_tableData.getString(Parser.LAY_R_3_LIST_CODE));
                        data.setVillageCode(distribution_tableData.getString(Parser.LAY_R_4_LIST_CODE));
                        data.setProgCode(distribution_tableData.getString(Parser.PROG_CODE));
                        data.setSrvCode(distribution_tableData.getString(Parser.SRV_CODE));
                        data.setOpMonthCode(distribution_tableData.getString(Parser.OP_MONTH_CODE));
                        data.setFDPCode(distribution_tableData.getString(Parser.FDP_CODE));
                        data.setID(distribution_tableData.getString(Parser.ID));
                        data.setDistStatus(distribution_tableData.getString(Parser.DIST_STATUS));
                        data.setSrvOpMonthCode(distribution_tableData.getString(Parser.SRV_OP_MONTH_CODE));
                        data.setDistFlag(distribution_tableData.getString(Parser.DIST_FLAG));
                        data.setWd(distribution_tableData.getString("WD"));

                        db.addInDistributionTableFormOnLine(data);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }
*//*


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.DISTRIBUTION_EXT_TABLE_JSON_A)) {
                    JSONArray distribution_ext_tableDatas = jObj.getJSONArray(Parser.DISTRIBUTION_EXT_TABLE_JSON_A);
                    size = distribution_ext_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject distribution_ext_tableData = distribution_ext_tableDatas.getJSONObject(i);
                        // DistributionSaveDataModel data = new DistributionSaveDataModel();

                        String AdmCountryCode = distribution_ext_tableData.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = distribution_ext_tableData.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = distribution_ext_tableData.getString(Parser.ADM_AWARD_CODE);
                        String LayR1ListCode = distribution_ext_tableData.getString(Parser.LAY_R_1_LIST_CODE);
                        String LayR2ListCode = distribution_ext_tableData.getString(Parser.LAY_R_2_LIST_CODE);
                        String LayR3ListCode = distribution_ext_tableData.getString(Parser.LAY_R_3_LIST_CODE);
                        String LayR4ListCode = distribution_ext_tableData.getString(Parser.LAY_R_4_LIST_CODE);
                        String ProgCode = distribution_ext_tableData.getString(Parser.PROG_CODE);
                        String SrvCode = distribution_ext_tableData.getString(Parser.SRV_CODE);
                        String OpMonthCode = distribution_ext_tableData.getString(Parser.OP_MONTH_CODE);
                        String FDPCode = distribution_ext_tableData.getString(Parser.FDP_CODE);
                        String ID = distribution_ext_tableData.getString(Parser.ID);
                        String VOItmSpec = distribution_ext_tableData.getString(Parser.VO_ITM_SPEC);
                        String VOItmUnit = distribution_ext_tableData.getString(Parser.VO_ITM_UNIT);
                        String VORefNumber = distribution_ext_tableData.getString(Parser.VO_REF_NUMBER);
                        String SrvOpMonthCode = distribution_ext_tableData.getString(Parser.SRV_OP_MONTH_CODE);
                        String DistFlag = distribution_ext_tableData.getString(Parser.DIST_FLAG);


                        //data.setDistStatus(distribution_ext_tableData.getString(DIST_STATUS);
                        // // TODO: 9/25/2016  create Method for download online

                        db.addInDistributionExtendedTable(AdmCountryCode, AdmDonorCode, AdmAwardCode,
                                LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode, ProgCode,
                                SrvCode, OpMonthCode, FDPCode, ID, VOItmSpec, VOItmUnit,
                                VORefNumber, SrvOpMonthCode, DistFlag,

                                "", "", "1");

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);

                if (!jObj.isNull("distbasic_table")) {
                    JSONArray distbasic_table = jObj.getJSONArray("distbasic_table");
                    size = distbasic_table.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject distbasic = distbasic_table.getJSONObject(i);
                        // DistributionSaveDataModel data = new DistributionSaveDataModel();

                        String AdmCountryCode = distbasic.getString(Parser.ADM_COUNTRY_CODE);
                        String AdmDonorCode = distbasic.getString(Parser.ADM_DONOR_CODE);
                        String AdmAwardCode = distbasic.getString(Parser.ADM_AWARD_CODE);
                        String ProgCode = distbasic.getString(Parser.PROG_CODE);
                        String OpCode = distbasic.getString("OpCode");
                        String SrvOpMonthCode = distbasic.getString("SrvOpMonthCode");
                        String DisOpMonthCode = distbasic.getString("DisOpMonthCode");
                        String FDPCode = distbasic.getString(Parser.FDP_CODE);
                        String DistFlag = distbasic.getString("DistFlag");
                        ///   String FoodFlag = distbasic.getString("FoodFlag");
                        String OrgCode = distbasic.getString("OrgCode");
                        String Distributor = distbasic.getString("Distributor");
                        String DistributionDate = distbasic.getString("DistributionDate");
                        String DeliveryDate = distbasic.getString("DeliveryDate");
                        String Status = distbasic.getString("Status");
                        String PreparedBy = distbasic.getString("PreparedBy");
                        String VerifiedBy = distbasic.getString("VerifiedBy");
                        String ApproveBy = distbasic.getString("ApproveBy");


                        //data.setDistStatus(distribution_ext_tableData.getString(DIST_STATUS);

                        db.addInDistributionNPlaneTable(AdmCountryCode, AdmDonorCode, AdmAwardCode, ProgCode,
                                OpCode, SrvOpMonthCode, DisOpMonthCode, FDPCode, DistFlag, OrgCode, Distributor,
                                DistributionDate, DeliveryDate, Status, PreparedBy, VerifiedBy, ApproveBy);


                      *//*  Log.d(TAG, "AdmCountryCode: " + AdmCountryCode + AdmDonorCode + AdmAwardCode + ProgCode +
                                OpCode + SrvOpMonthCode + DisOpMonthCode + FDPCode + DistFlag + OrgCode + Distributor +
                                DistributionDate + DeliveryDate + Status + PreparedBy + VerifiedBy + ApproveBy);*//*

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }


                publishProgress(++progressIncremental);


                if (!jObj.isNull(Parser.LUP_SRV_OPTION_LIST)) {
                    JSONArray lup_srv_option_listDatas = jObj.getJSONArray(Parser.LUP_SRV_OPTION_LIST);
                    size = lup_srv_option_listDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_srv_option_listData = lup_srv_option_listDatas.getJSONObject(i);

                        String countryCode = lup_srv_option_listData.getString(Parser.ADM_COUNTRY_CODE);

                        String programCode = lup_srv_option_listData.getString(Parser.PROG_CODE);
                        String serviceCode = lup_srv_option_listData.getString(Parser.SRV_CODE);
                        String LUPOptionCode = lup_srv_option_listData.getString(Parser.LUP_OPTION_CODE);
                        String LUPOptionName = lup_srv_option_listData.getString(Parser.LUP_OPTION_NAME);


                        db.addInLupSrvOptionListFromOnline(countryCode, programCode, serviceCode, LUPOptionCode, LUPOptionName);

                        //  Log.d(TAG, "In Reg Mem Card Request Table: AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " LayR1ListCode : " + LayR1ListCode + " LayR2ListCode : "
                        //        + LayR2ListCode + " LayR3ListCode : " + LayR3ListCode + " LayR4ListCode : " + LayR4ListCode+ " HHID : " + HHID);
                    }
                }

                publishProgress(++progressIncremental);


                if (!jObj.isNull("vo_itm_table")) {
                    JSONArray vo_itm_tableDatas = jObj.getJSONArray("vo_itm_table");
                    size = vo_itm_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject vo_itm_tableData = vo_itm_tableDatas.getJSONObject(i);
                        //AGR_DataModel data = new AGR_DataModel();
                        String CatCode = vo_itm_tableData.getString("CatCode");
                        String ItmCode = vo_itm_tableData.getString("ItmCode");
                        String ItmName = vo_itm_tableData.getString("ItmName");


                        db.addVoucherItemTableFromOnline(CatCode, ItmCode, ItmName);

//                        Log.d(TAG, "In Voucher item table : CatCode : " + CatCode + " ItmCode : " + ItmCode + " ItmName : " + ItmName);

                    }
                }

//                publishProgress(39);
                publishProgress(++progressIncremental);

                if (!jObj.isNull(Parser.VO_ITM_MEAS_TABLE_JSON_A)) {
                    JSONArray vo_itm_meas_tableDatas = jObj.getJSONArray(Parser.VO_ITM_MEAS_TABLE_JSON_A);
                    size = vo_itm_meas_tableDatas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject vo_itm_meas_tableData = vo_itm_meas_tableDatas.getJSONObject(i);
                        //AGR_DataModel data = new AGR_DataModel();
                        String MeasRCode = vo_itm_meas_tableData.getString("MeasRCode");
                        String UnitMeas = vo_itm_meas_tableData.getString("UnitMeas");
                        String MeasTitle = vo_itm_meas_tableData.getString("MeasTitle");


                        db.addVoucherItemMeasFromOnline(MeasRCode, UnitMeas, MeasTitle);

//                        Log.d(TAG, "In Voucher item table : MeasRCode : " + MeasRCode + " UnitMeas : " + UnitMeas + " MeasTitle : " + MeasTitle);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("vo_country_prog_itm")) {
                    JSONArray vo_country_prog_itmDatas = jObj.getJSONArray("vo_country_prog_itm");
                    size = vo_country_prog_itmDatas.length();


                    String AdmCountryCode;
                    String AdmDonorCode;
                    String AdmAwardCode;
                    String AdmProgCode;
                    String AdmSrvCode;
                    String CatCode;
                    String ItmCode;
                    String MeasRCode;
                    String VOItmSpec;
                    String UnitCost;
                    String Active;
                    String Currency;
                    for (int i = 0; i < size; i++) {
                        JSONObject vo_country_prog_itmData = vo_country_prog_itmDatas.getJSONObject(i);
                        //AGR_DataModel data = new AGR_DataModel();
                        AdmCountryCode = vo_country_prog_itmData.getString("AdmCountryCode");
                        AdmDonorCode = vo_country_prog_itmData.getString("AdmDonorCode");
                        AdmAwardCode = vo_country_prog_itmData.getString("AdmAwardCode");
                        AdmProgCode = vo_country_prog_itmData.getString("AdmProgCode");
                        AdmSrvCode = vo_country_prog_itmData.getString("AdmSrvCode");
                        CatCode = vo_country_prog_itmData.getString("CatCode");
                        ItmCode = vo_country_prog_itmData.getString("ItmCode");
                        MeasRCode = vo_country_prog_itmData.getString("MeasRCode");
                        VOItmSpec = vo_country_prog_itmData.getString(Parser.VO_ITM_SPEC);
                        UnitCost = vo_country_prog_itmData.getString("UnitCost");
                        Active = vo_country_prog_itmData.getString("Active");
                        Currency = vo_country_prog_itmData.getString("Currency");


                        db.addVoucherCountryProgItemFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, AdmSrvCode, CatCode, ItmCode, MeasRCode, VOItmSpec, UnitCost, Active, Currency);

                        //                        Log.d(TAG, "In Voucher Country  Prog Item  table : AdmCountryCode : " + AdmCountryCode + " AdmDonorCode : " + AdmDonorCode + " AdmAwardCode : " + AdmAwardCode);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.LUP_GPS_TABLE_JSON_A)) {
                    JSONArray lup_gps_table_Datas = jObj.getJSONArray(Parser.LUP_GPS_TABLE_JSON_A);
                    size = lup_gps_table_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_gps_tableData = lup_gps_table_Datas.getJSONObject(i);

                        String GrpCode = lup_gps_tableData.getString("GrpCode");
                        String SubGrpCode = lup_gps_tableData.getString("SubGrpCode");
                        String AttributeCode = lup_gps_tableData.getString("AttributeCode");
                        String LookUpCode = lup_gps_tableData.getString("LookUpCode");
                        String LookUpName = lup_gps_tableData.getString("LookUpName");


                        db.addLUP_GPS_TableFromOnline(GrpCode, SubGrpCode, AttributeCode, LookUpCode, LookUpName);
                       *//* Log.d("NIR2", "addLUP_GPS_TableFromOnline : GrpCode : " + GrpCode + " SubGrpCode : "
                                + SubGrpCode + " AttributeCode : " + AttributeCode
                                + " LookUpCode : " + LookUpCode + " LookUpName : " + LookUpName
                        );*//*

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_SUB_GROUP_ATTRIBUTES_JSON_A)) {
                    JSONArray gps_sub_group_attributes_Datas = jObj.getJSONArray(Parser.GPS_SUB_GROUP_ATTRIBUTES_JSON_A);
                    size = gps_sub_group_attributes_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject gps_sub_group_attributes_Data = gps_sub_group_attributes_Datas.getJSONObject(i);


                        String GrpCode = gps_sub_group_attributes_Data.getString("GrpCode");
                        String SubGrpCode = gps_sub_group_attributes_Data.getString("SubGrpCode");
                        String AttributeCode = gps_sub_group_attributes_Data.getString("AttributeCode");
                        String AttributeTitle = gps_sub_group_attributes_Data.getString("AttributeTitle");
                        String DataType = gps_sub_group_attributes_Data.getString("DataType");
                        String LookUpCode = gps_sub_group_attributes_Data.getString("LookUpCode");


                        db.addGPS_SubGroupAttributesFromOnline(GrpCode, SubGrpCode, AttributeCode, AttributeTitle, DataType, LookUpCode);
                        *//*Log.d("NIR2", "addGPS_SubGroupAttributesFromOnline : GrpCode : " + GrpCode
                                + " SubGrpCode : " + SubGrpCode + " AttributeCode : " + AttributeCode
                                + " AttributeTitle : " + AttributeTitle + " DataType : " + DataType
                                + " LookUpCode : " + LookUpCode);*//*

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull(Parser.GPS_LOCATION_ATTRIBUTES_JSON_A)) {
                    JSONArray gps_location_attributes_Datas = jObj.getJSONArray(Parser.GPS_LOCATION_ATTRIBUTES_JSON_A);
                    size = gps_location_attributes_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject gps_location_attributes_Data = gps_location_attributes_Datas.getJSONObject(i);


                        String AdmCountryCode = gps_location_attributes_Data.getString("AdmCountryCode");
                        String GrpCode = gps_location_attributes_Data.getString("GrpCode");
                        String SubGrpCode = gps_location_attributes_Data.getString("SubGrpCode");
                        String LocationCode = gps_location_attributes_Data.getString("LocationCode");
                        String AttributeCode = gps_location_attributes_Data.getString("AttributeCode");
                        String AttributeValue = gps_location_attributes_Data.getString("AttributeValue");
                        String AttPhoto = gps_location_attributes_Data.getString("AttPhoto");

                        db.addGPSLocationAttributesFromOnline(AdmCountryCode, GrpCode, SubGrpCode, LocationCode, AttributeCode, AttributeValue, AttPhoto);
                     *//*   Log.d("NIR2", "addGPS_SubGroupAttributesFromOnline : AdmCountryCode : " + AdmCountryCode
                                + " GrpCode : " + GrpCode + " SubGrpCode : " + SubGrpCode
                                + " LocationCode : " + LocationCode + " AttributeCode : " + AttributeCode

                                + " AttributeValue : " + AttributeValue
                        );*//*

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("community_group")) {

                    Parser.CommunityGroupParser(jObj.getJSONArray("community_group"), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_community_animal")) {
                    JSONArray lup_community_animal_Datas = jObj.getJSONArray("lup_community_animal");
                    size = lup_community_animal_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_community_animal_Data = lup_community_animal_Datas.getJSONObject(i);


                        String AdmCountryCode = lup_community_animal_Data.getString("AdmCountryCode");
                        String AdmDonorCode = lup_community_animal_Data.getString("AdmDonorCode");
                        String AdmAwardCode = lup_community_animal_Data.getString("AdmAwardCode");
                        String AdmProgCode = lup_community_animal_Data.getString("AdmProgCode");
                        String AnimalCode = lup_community_animal_Data.getString("AnimalCode");
                        String AnimalType = lup_community_animal_Data.getString("AnimalType");


                        db.addLUP_AnimalTypeFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, AnimalCode, AnimalType);


                    }
                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_prog_group_crop")) {
                    Parser.lupProgGroupCropParser(jObj.getJSONArray("lup_prog_group_crop"), db);
                }


                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_community_loan_source")) {
                    JSONArray lup_community_loan_source_Datas = jObj.getJSONArray("lup_community_loan_source");
                    size = lup_community_loan_source_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_community_loan_source_Data = lup_community_loan_source_Datas.getJSONObject(i);


                        String AdmCountryCode = lup_community_loan_source_Data.getString("AdmCountryCode");
                        String AdmDonorCode = lup_community_loan_source_Data.getString("AdmDonorCode");
                        String AdmAwardCode = lup_community_loan_source_Data.getString("AdmAwardCode");
                        String AdmProgCode = lup_community_loan_source_Data.getString("AdmProgCode");
                        String LoanCode = lup_community_loan_source_Data.getString("LoanCode");
                        String LoanSource = lup_community_loan_source_Data.getString("LoanSource");


                *//*        Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "AdmProgCode:" + AdmProgCode
                                + " LoanCode: " + LoanCode + "LoanSource:" + LoanSource);
*//*
                        db.addLUP_CommunityLoanSource(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode
                                , LoanCode, LoanSource);
                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_community__lead_position")) {
                    JSONArray lup_community_lead_position_Datas = jObj.getJSONArray("lup_community__lead_position");
                    size = lup_community_lead_position_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject lup_community_lead_position_Data = lup_community_lead_position_Datas.getJSONObject(i);


                        String AdmCountryCode = lup_community_lead_position_Data.getString("AdmCountryCode");
                        String AdmDonorCode = lup_community_lead_position_Data.getString("AdmDonorCode");
                        String AdmAwardCode = lup_community_lead_position_Data.getString("AdmAwardCode");
                        String AdmProgCode = lup_community_lead_position_Data.getString("AdmProgCode");
                        String LeadCode = lup_community_lead_position_Data.getString("LeadCode");
                        String LeadPosition = lup_community_lead_position_Data.getString("LeadPosition");


              *//*          Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "AdmProgCode:" + AdmProgCode
                                + " LeadCode: " + LeadCode + "LeadPosition:" + LeadPosition);*//*
                        db.addLUP_CommunityLeadPostition(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, LeadCode, LeadPosition);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("community_group_category")) {
                    JSONArray community_group_category_Datas = jObj.getJSONArray("community_group_category");
                    size = community_group_category_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject community_group_category_Data = community_group_category_Datas.getJSONObject(i);


                        String AdmCountryCode = community_group_category_Data.getString("AdmCountryCode");
                        String AdmDonorCode = community_group_category_Data.getString("AdmDonorCode");
                        String AdmAwardCode = community_group_category_Data.getString("AdmAwardCode");
                        String AdmProgCode = community_group_category_Data.getString("AdmProgCode");
                        String GrpCatCode = community_group_category_Data.getString("GrpCatCode");
                        String GrpCatName = community_group_category_Data.getString("GrpCatName");
                        String GrpCatShortName = community_group_category_Data.getString("GrpCatShortName");


           *//*             Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "AdmProgCode:" + AdmProgCode
                                + " GrpCatCode: " + GrpCatCode + " GrpCatName:" + GrpCatName
                                + " GrpCatShortName: " + GrpCatShortName
                        );*//*
                        db.addCommunityGroupCategoryFromOnline(AdmCountryCode, AdmDonorCode, AdmAwardCode, AdmProgCode, GrpCatCode, GrpCatName, GrpCatShortName);

                    }
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull("lup_reg_n_add_lookup")) {
                    JSONArray lup_reg_n_add_lookup = jObj.getJSONArray("lup_reg_n_add_lookup");
                    size = lup_reg_n_add_lookup.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject Lup_reg_n_add_lookup = lup_reg_n_add_lookup.getJSONObject(i);


                        String AdmCountryCode = Lup_reg_n_add_lookup.getString("AdmCountryCode");
                        String RegNAddLookupCode = Lup_reg_n_add_lookup.getString("RegNAddLookupCode");
                        String RegNAddLookup = Lup_reg_n_add_lookup.getString("RegNAddLookup");
                        String LayR1ListCode = Lup_reg_n_add_lookup.getString("LayR1ListCode");
                        String LayR2ListCode = Lup_reg_n_add_lookup.getString("LayR2ListCode");
                        String LayR3ListCode = Lup_reg_n_add_lookup.getString("LayR3ListCode");
                        String LayR4ListCode = Lup_reg_n_add_lookup.getString("LayR4ListCode");

*//*
                        Log.d("InTest", "AdmCountryCode:" + AdmCountryCode + "RegNAddLookupCode" + RegNAddLookupCode
                                + "RegNAddLookup: " + RegNAddLookup + "LayR1ListCode : " + LayR1ListCode + "LayR2ListCode:" + LayR2ListCode
                                + " LayR3ListCode: " + LayR3ListCode + "LayR4ListCode:" + LayR4ListCode);*//*
                        db.addLUP_RegNAddLookup(AdmCountryCode, RegNAddLookupCode, RegNAddLookup, LayR1ListCode, LayR2ListCode, LayR3ListCode, LayR4ListCode);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("prog_org_n_role")) {
                    JSONArray prog_org_n_role_Datas = jObj.getJSONArray("prog_org_n_role");
                    size = prog_org_n_role_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject prog_org_n_role_Data = prog_org_n_role_Datas.getJSONObject(i);
                        String AdmCountryCode = prog_org_n_role_Data.getString("AdmCountryCode");
                        String AdmDonorCode = prog_org_n_role_Data.getString("AdmDonorCode");
                        String AdmAwardCode = prog_org_n_role_Data.getString("AdmAwardCode");
                        String OrgNCode = prog_org_n_role_Data.getString("OrgNCode");
                        String PrimeYN = prog_org_n_role_Data.getString("PrimeYN");
                        String SubYN = prog_org_n_role_Data.getString("SubYN");
                        String TechYN = prog_org_n_role_Data.getString("TechYN");
                        String LogYN = prog_org_n_role_Data.getString("LogYN");
                        String ImpYN = prog_org_n_role_Data.getString("ImpYN");
                        String OthYN = prog_org_n_role_Data.getString("OthYN");
       *//*                 Log.d("InTest", "AdmCountryCode:" + AdmCountryCode
                                + "AdmDonorCode: " + AdmDonorCode + "AdmAwardCode : " + AdmAwardCode + "OrgNCode:" + OrgNCode
                                + " PrimeYN: " + PrimeYN + " SubYN:" + SubYN
                                + " TechYN: " + TechYN
                                + " LogYN: " + LogYN + "ImpYN:" + ImpYN + " OthYN: " + OthYN
                        );*//*
                        db.insertIntoProgOrgNRole(AdmCountryCode, AdmDonorCode, AdmAwardCode, OrgNCode, PrimeYN, SubYN, TechYN, ImpYN, LogYN, OthYN);
                    }
                }
                publishProgress(++progressIncremental);
                if (!jObj.isNull("org_n_code")) {
                    JSONArray org_n_code_Datas = jObj.getJSONArray("org_n_code");
                    size = org_n_code_Datas.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject org_n_code_Data = org_n_code_Datas.getJSONObject(i);
                        String OrgNCode = org_n_code_Data.getString("OrgNCode");
                        String OrgNName = org_n_code_Data.getString("OrgNName");
                        String OrgNShortName = org_n_code_Data.getString("OrgNShortName");
                        Log.d("ShuvoTag", "OrgNName:" + OrgNName + "OrgNShortName:" + OrgNShortName);
                        db.insertIntoProgOrgN(OrgNCode, OrgNName, OrgNShortName);
                    }
                }
//// TODO: 10/18/2016  this parsing of the group detail set to parse class

                publishProgress(++progressIncremental);
                if (!jObj.isNull("community_grp_detail")) {

                    Parser.CommunityGroupDetailsParser(jObj.getJSONArray("community_grp_detail"), db);
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("staff_master")) {
                    JSONArray staff_master_Datas = jObj.getJSONArray("staff_master");
                    size = staff_master_Datas.length();

                    String StfCode;
                    String OrigAdmCountryCode;
                    String StfName;
                    String OrgNCode;
                    String OrgNDesgNCode;
                    String StfStatus;
                    String StfCategory;
                    String UsrLogInName;
                    String UsrLogInPW;
                    String StfAdminRole;
                    for (int i = 0; i < size; i++) {
                        JSONObject staff_master_Data = staff_master_Datas.getJSONObject(i);


                        StfCode = staff_master_Data.getString("StfCode");
                        OrigAdmCountryCode = staff_master_Data.getString("OrigAdmCountryCode");
                        StfName = staff_master_Data.getString("StfName");
                        OrgNCode = staff_master_Data.getString("OrgNCode");
                        OrgNDesgNCode = staff_master_Data.getString("OrgNDesgNCode");
                        StfStatus = staff_master_Data.getString("StfStatus");
                        StfCategory = staff_master_Data.getString("StfCategory");
                        UsrLogInName = staff_master_Data.getString("UsrLogInName");
                        UsrLogInPW = staff_master_Data.getString("UsrLogInPW");
                        StfAdminRole = staff_master_Data.getString("StfAdminRole");


                        db.insertIntoStaffMasterTable(StfCode, OrigAdmCountryCode, StfName, OrgNCode, OrgNDesgNCode, StfStatus, StfCategory, UsrLogInName, UsrLogInPW, StfAdminRole);

                    }
                }

                publishProgress(++progressIncremental);
                if (!jObj.isNull("gps_lup_list")) {
                    JSONArray gps_lup_list_data = jObj.getJSONArray("gps_lup_list");
                    size = gps_lup_list_data.length();

                    String GrpCode;
                    String SubGrpCode;
                    String AttributeCode;
                    String LupValueCode;
                    String LupValueText;

                    for (int i = 0; i < size; i++) {
                        JSONObject gps_lup_list = gps_lup_list_data.getJSONObject(i);


                        GrpCode = gps_lup_list.getString("GrpCode");
                        SubGrpCode = gps_lup_list.getString("SubGrpCode");
                        AttributeCode = gps_lup_list.getString("AttributeCode");
                        LupValueCode = gps_lup_list.getString("LupValueCode");
                        LupValueText = gps_lup_list.getString("LupValueText");


                        db.insertIntoLupGpsList(GrpCode, SubGrpCode, AttributeCode, LupValueCode, LupValueText);

                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog();
            progressIncremental = 0;
        }

        @Override
        protected void onPostExecute(Void string) {

            new Inject_Reg_HouseH_DataIntoSQLite().execute();
        }
    }
*/
    private class Inject_DynamicTableIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new Inject_EnuTableIntoSQLite().execute();

        }

        @Override
        protected Void doInBackground(Void... params) {


            /**
             * Read JSON DATA  from the text file
             * */
            String retrieveData = readDataFromFile(DYNAMIC_TABLE);

            try {

                /**                 * The total string Convert into JSON object                 * */

                JSONObject jObj = new JSONObject(retrieveData);

                publishProgress(++progressIncremental);

                if (!jObj.isNull("D_T_answer")) {
                    Parser.DTA_Parser(jObj.getJSONArray("D_T_answer"), db);
                }

                if (!jObj.isNull("D_T_basic")) {
                    Parser.DTBasicParser(jObj.getJSONArray("D_T_basic"), db);
                }

                if (!jObj.isNull("D_T_category")) {
                    Parser.DTCategoryParser(jObj.getJSONArray("D_T_category"), db);
                }

                if (!jObj.isNull("D_T_CountryProgram")) {
                    Parser.DTCountryProgramParser(jObj.getJSONArray("D_T_CountryProgram"), db);
                }


                if (!jObj.isNull("D_T_GeoListLevel")) {
                    Parser.DTGeoListLevelParser(jObj.getJSONArray("D_T_GeoListLevel"), db);
                }


                if (!jObj.isNull("D_T_QTable")) {
                    Parser.DTQTableParser(jObj.getJSONArray("D_T_QTable"), db);
                }

                if (!jObj.isNull("D_T_QResMode")) {
                    Parser.DTQResModeParser(jObj.getJSONArray("D_T_QResMode"), db);
                }

                if (!jObj.isNull("D_T_ResponseTable")) {
                    Parser.DTResponseTableParser(jObj.getJSONArray("D_T_ResponseTable"), db);
                }

                if (!jObj.isNull("D_T_TableDefinition")) {
                    Parser.DTTableDefinitionParser(jObj.getJSONArray("D_T_TableDefinition"), db);
                }

                if (!jObj.isNull("D_T_TableListCategory")) {
                    Parser.DTTableListCategoryParser(jObj.getJSONArray("D_T_TableListCategory"), db);
                }

                if (!jObj.isNull("D_T_LUP")) {
                    Parser.DTLUPParser(jObj.getJSONArray("D_T_LUP"), db);
                }


            } catch (Exception e) {
                Log.e("ShuvoTag", "Exception : " + e);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
    }

    private class Inject_EnuTableIntoSQLite extends AsyncTask<Void, Integer, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            hideDialog();
            //  loadCountry();
/**
 * need to set the TXT Name .....
 *
 *
 */
            // set the user name
            //  txtName.setText(getUserName());
            startActivity(new Intent(ParseDataActivity.this,DynamicTable.class));
        }

        @Override
        protected Void doInBackground(Void... params) {


            /**             * Read JSON DATA  from the text file             **/
            String retrieveData = readDataFromFile(ENU_TABLE);

            try {


                /**                 * The total string Convert into JSON object                 * */

                JSONObject jObj = new JSONObject(retrieveData);
                publishProgress(++progressIncremental);

                if (!jObj.isNull("enu_table")) {
                    Parser.DTEnu_Parser(jObj.getJSONArray("enu_table"), db);
                }

            } catch (Exception e) {
                Log.e("ShuvoTag", "Exception : " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
