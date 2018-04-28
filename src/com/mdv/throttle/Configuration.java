package com.mdv.throttle;

public class Configuration {

    public static String IO_FILE = "./test.io.txt";
    public static int PUBLISH_INTERVAL_MILLISEC = 1000;
    public static int SUBSCRIBE_INTERVAL_MILLISEC = 1000;
    public static int MEASURE_INTERVAL_MILLISEC = 500;
    public static float REFERENCE_TPS = 5; //MAX ALLOWED TPS
    public static int CONTROL_INTERVAL = 2000; //frequency of control check
    public static String MEASURE_FILE = "./meter.txt";
    public static String LOG_FILE = "./log.txt";
    public static int MAX_MEASURE_DEPTH = 30;
    public static String DEF_STRING_TOKEN = "|";
    public static String AVG_SPEED_LOG_FILE = "./avgspeed.txt";
    public static float DIFF_TOLERANCE = 2; //max allowed difference between max TPS and current TPS
    public static int MAX_QUEUE_SIZE = 100;






}
