package com.survey.shuvo.technodhaka.tdsurvey.controller;

/**
 * Created by TD02 on 3/7/2017.
 */
public class AppConfig {


    /***********************************************************************
     * UAT
     * //Windows Server Azure VM Live Server
     */
    public static final String API_LINK = "http://pciapp.cloudapp.net/datacraft/api/";
    public static final String API_LINK_ENU = "http://pciapp.cloudapp.net/datacraft/api/index.php?enu";
    //LIVE LINK
    //  public static final String API_LINK = "http://pciapp.cloudapp.net/apilive/";
    /************************************************************************/


    /***********************************************************************
     * //  Localhost
     */
    //  public static final String API_LINK = "http://192.168.49.1/api/local/";
    /************************************************************************/
    /***********************************************************************
     * //  Localhost out side ngrok
     */
    //  public static final String API_LINK = "http://83cb7db6.ngrok.io/api/";
    /************************************************************************/
    // Application developments  Environment
    public static Boolean DEV_ENVIRONMENT = false; // false / true


}
