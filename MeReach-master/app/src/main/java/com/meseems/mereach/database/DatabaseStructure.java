package com.meseems.mereach.database;

/**
 * Created by Asus on 24/08/2016.
 */
public class DatabaseStructure {
    //database attriutes
    public static final String DB_NAME = "servers_db";
    public static final int DB_VERSION = 1;

    //tables on database
    public final static String TABLE_SERVER = "server";
    //general attribute
    public static final String ID = "_id";

    //Table server attributes
    public static final String SERVER_URL = "url";


    public static final int SERVER_URL_COLLUMN = 1;

    //string create table product
    public static final String CREATE_SERVER = "CREATE TABLE IF NOT EXISTS "+TABLE_SERVER+" ("
            +ID+" INTEGER primary key autoincrement, "
            +SERVER_URL+" text)";
}