package com.survey.shuvo.technodhaka.tdsurvey.DBHelper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TD02 on 3/6/2017.
 */
public class DTSurveyTableDataModel implements Parcelable{


    private String dtBasic;
    private String countryCode;
    private String donorCode;
    private String awardCode;
    private String programCode;
    private String dtEnuId;
    private String dtqCode;
    private String dtaCode;
    private int dtrSeq;
    private String dtaValue;
    private String progActivityCode;
    private String dttTimeString;
    private String opMode;
    private String opMonthCode;
    private String dataType;
    private String dtqText;
    private int dtSurveyNumber;

    public DTSurveyTableDataModel() {
    }

    protected DTSurveyTableDataModel(Parcel in) {
        dtBasic = in.readString();
        countryCode = in.readString();
        donorCode = in.readString();
        awardCode = in.readString();
        programCode = in.readString();
        dtEnuId = in.readString();
        dtqCode = in.readString();
        dtaCode = in.readString();
        dtrSeq = in.readInt();
        dtaValue = in.readString();
        progActivityCode = in.readString();
        dttTimeString = in.readString();
        opMode = in.readString();
        opMonthCode = in.readString();
        dataType = in.readString();
        dtqText = in.readString();
        dtSurveyNumber = in.readInt();
    }

    public static final Parcelable.Creator<DTSurveyTableDataModel> CREATOR = new Parcelable.Creator<DTSurveyTableDataModel>() {
        @Override
        public DTSurveyTableDataModel createFromParcel(Parcel in) {
            return new DTSurveyTableDataModel(in);
        }

        @Override
        public DTSurveyTableDataModel[] newArray(int size) {
            return new DTSurveyTableDataModel[size];
        }
    };

    public String getDtBasic() {
        return dtBasic;
    }

    public void setDtBasic(String dtBasic) {
        this.dtBasic = dtBasic;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getDonorCode() {
        return donorCode;
    }

    public void setDonorCode(String donorCode) {
        this.donorCode = donorCode;
    }

    public String getAwardCode() {
        return awardCode;
    }

    public void setAwardCode(String awardCode) {
        this.awardCode = awardCode;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getDtEnuId() {
        return dtEnuId;
    }

    public void setDtEnuId(String dtEnuId) {
        this.dtEnuId = dtEnuId;
    }

    public String getDtqCode() {
        return dtqCode;
    }

    public void setDtqCode(String dtqCode) {
        this.dtqCode = dtqCode;
    }

    public String getDtaCode() {
        return dtaCode;
    }

    public void setDtaCode(String dtaCode) {
        this.dtaCode = dtaCode;
    }

    public int getDtrSeq() {
        return dtrSeq;
    }

    public void setDtrSeq(int dtrSeq) {
        this.dtrSeq = dtrSeq;
    }

    public String getDtaValue() {
        return dtaValue;
    }

    public void setDtaValue(String dtaValue) {
        this.dtaValue = dtaValue;
    }

    public String getProgActivityCode() {
        return progActivityCode;
    }

    public void setProgActivityCode(String progActivityCode) {
        this.progActivityCode = progActivityCode;
    }

    public String getDttTimeString() {
        return dttTimeString;
    }

    public void setDttTimeString(String dttTimeString) {
        this.dttTimeString = dttTimeString;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getOpMonthCode() {
        return opMonthCode;
    }

    public void setOpMonthCode(String opMonthCode) {
        this.opMonthCode = opMonthCode;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDtqText() {
        return dtqText;
    }

    public void setDtqText(String dtqText) {
        this.dtqText = dtqText;
    }

    public int getDtSurveyNumber() {
        return dtSurveyNumber;
    }

    public void setDtSurveyNumber(int dtSurveyNumber) {
        this.dtSurveyNumber = dtSurveyNumber;
    }


    public int describeContents() {
        return 0;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dtBasic);
        dest.writeString(countryCode);
        dest.writeString(donorCode);
        dest.writeString(awardCode);
        dest.writeString(programCode);
        dest.writeString(dtEnuId);
        dest.writeString(dtqCode);
        dest.writeString(dtaCode);
        dest.writeInt(dtrSeq);
        dest.writeString(dtaValue);
        dest.writeString(progActivityCode);
        dest.writeString(dttTimeString);
        dest.writeString(opMode);
        dest.writeString(opMonthCode);
        dest.writeString(dataType);
        dest.writeString(dtqText);
        dest.writeInt(dtSurveyNumber);
    }

}
