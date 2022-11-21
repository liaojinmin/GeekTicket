package me.geekTicket.Configuration;



/**
 * @author : 老廖
 * @date : 2022-05-02 13:06
 **/
public final class ConfigManage {

    public static boolean DEMO_BUG;

    // SQL set
    public static String DATA_TYPE;
    // MYSQL
    public static String MYSQL_HOST;
    public static int MYSQL_PORT;
    public static String MYSQL_DATABASE;
    public static String MYSQL_USERNAME;
    public static String MYSQL_PASSWORD;
    public static String MYSQL_PARAMS;
    public static String MYSQL_DATA_NAME;
    // hikari
    public static int MAXIMUM_POOL_SIZE;
    public static int MINIMUM_IDLE;
    public static int MAXIMUM_LIFETIME;
    public static int KEEPALIVE_TIME;
    public static int CONNECTION_TIMEOUT;

    // RedisServer
    public static boolean START_BUNGEE;
    public static String REDIS_HOST;
    public static int REDIS_PORT;
    public static String REDIS_PASSWORD;
    public static boolean REDIS_SSL;

    // AutoTask
    public static boolean AUTO_CLEAR;
    public static int AUTO_CLEAR_DAY;
    public static boolean GLOBAL_TOP;
    public static boolean AUTO_SAVE;

}
