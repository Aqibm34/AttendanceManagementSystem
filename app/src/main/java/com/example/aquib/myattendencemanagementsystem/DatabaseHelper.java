package com.example.aquib.myattendencemanagementsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME  = "STUDENTS";
    public static final String TABLE_NAME1 = "TEACHERS";
    public static final String TABLE_NAME2 = "ADMIN";
    public static final String TABLE_NAME3 = "SUBJECT";
    public static final String TABLE_NAME4 = "ATTENDENCE";

    // Student Table columns

    public static final String S_ID       = "_id";
    public static final String S_NAME     = "name";
    public static final String S_REGNO    = "regNo";
    public static final String S_COURCE   = "cource";
    public static final String S_SEMESTER = "semester";

    // Teacher Table columns
    public static final String T_ID       = "_id";
    public static final String T_NAME     = "name";
    public static final String T_EMAIL    = "email";
    public static final String T_CONTACT  = "contactNo";
    public static final String T_USERID   = "uid";
    public static final String T_PASSWORD = "password";

    // Admin Table Columns
    public static final String A_ID     = "_id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    //Subject Table Columns
    public static final String SUB_ID     = "_id";
    public static final String SUB_NAME   = "sub_name";
    public static final String SUB_T_ID   = "t_id";
    public static final String SUB_CODE   = "code";
    public static final String SUB_COURCE = "course";
    public static final String SUB_SEM    = "semester";

    //Attendence Table Columns

    public static final String AT_ID            = "_id";
    public static final String AT_STUDENT_NAME  = "sname";
    public static final String AT_STUDENT_CLASS = "sclass";
    public static final String AT_SUBJECT       = "subject";
    public static final String AT_STUDENT_SEM   = "ssem";
    public static final String AT_STUDENT_REGNO = "sregno";
    public static final String AT_STATUS        = "status";
    public static final String AT_DATE          = "date";

    // Database Information
    static final String DB_NAME = "MyAttendenceManager.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query

    //STUDENT
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + S_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + S_NAME + " TEXT, "
            + S_REGNO +" TEXT NOT NULL, " + S_COURCE + " TEXT, "+ S_SEMESTER +" TEXT);";
    //TEACHER
    private static final String CREATE_TABLE1 = "create table " + TABLE_NAME1 + "(" + T_ID
            +" INTEGER PRIMARY KEY AUTOINCREMENT, " + T_NAME + " TEXT NOT NULL, " + T_EMAIL + " TEXT, "
            + T_CONTACT + " INTEGER, " + T_USERID + " TEXT NOT NULL, " + T_PASSWORD + " TEXT NOT NULL);";

    //ADMIN
    private static final  String CREATE_TABLE2 = "create table " + TABLE_NAME2 + "(" + A_ID
            +" INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT, " + PASSWORD + " TEXT );";

    //SUBJECT
    private static final String CREATE_TABLE3 = "create table " + TABLE_NAME3 + "(" + SUB_ID
            +" INTEGER PRIMARY KEY AUTOINCREMENT, " + SUB_NAME + " TEXT NOT NULL, " + SUB_CODE + " INTEGER, "
            + SUB_T_ID + " TEXT, " + SUB_COURCE + " TEXT NOT NULL, " + SUB_SEM + " TEXT );";


    //ATTENDENCE
    private static final String CREATE_TABLE4 ="create table "+ TABLE_NAME4 + "(" + AT_ID
            +" INTEGER PRIMARY KEY AUTOINCREMENT, " + AT_STUDENT_NAME + " TEXT NOT NULL, " + AT_STUDENT_CLASS  + " TEXT, "+ AT_SUBJECT
            +" TEXT, " +AT_STUDENT_SEM + " TEXT, " + AT_STUDENT_REGNO + " TEXT, " + AT_STATUS + " TEXT NOT NULL, " + AT_DATE+
            " TEXT );";

    public static String currentDBPath = "";
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        currentDBPath = context.getDatabasePath(DB_NAME).getPath();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE1);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
        db.execSQL(CREATE_TABLE4);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME4);
        onCreate(db);
    }
}