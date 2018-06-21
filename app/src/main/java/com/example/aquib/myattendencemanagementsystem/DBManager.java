package com.example.aquib.myattendencemanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    // Inserting Into Student Table
    public void insert(String name, String regno, String cource, String semester) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.S_NAME, name);
        contentValue.put(DatabaseHelper.S_REGNO, regno);
        contentValue.put(DatabaseHelper.S_COURCE, cource);
        contentValue.put(DatabaseHelper.S_SEMESTER, semester);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    //Inserting Into TeacherTable
    public void insert_into_teacher(String name, String email, Integer contact, String uid, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.T_NAME, name);
        contentValues.put(DatabaseHelper.T_EMAIL, email);
        contentValues.put(DatabaseHelper.T_CONTACT, contact);
        contentValues.put(DatabaseHelper.T_USERID, uid);
        contentValues.put(DatabaseHelper.T_PASSWORD, password);
        database.insert(DatabaseHelper.TABLE_NAME1, null, contentValues);
    }

    //Inserting into Admin
    public long insert_into_admin(String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USERNAME, username);
        contentValues.put(DatabaseHelper.PASSWORD, password);
        long i = database.insert(DatabaseHelper.TABLE_NAME2, null, contentValues);
        return i;

    }

    // Inserting into Subject

    public long insert_into_subject(String S_name, String T_id, int code, String course, String sem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUB_NAME, S_name);
        contentValues.put(DatabaseHelper.SUB_T_ID, T_id);
        contentValues.put(DatabaseHelper.SUB_CODE, code);
        contentValues.put(DatabaseHelper.SUB_COURCE, course);
        contentValues.put(DatabaseHelper.SUB_SEM, sem);
        long l = database.insert(DatabaseHelper.TABLE_NAME3, null, contentValues);
        return l;
    }

    //Insert Into Attendence
    public long insert_into_attendence(String s_name, String s_class, String subject, String semester
            , String regno, String status, String date) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.AT_STUDENT_NAME, s_name);
        contentValues.put(DatabaseHelper.AT_STUDENT_CLASS, s_class);
        contentValues.put(DatabaseHelper.AT_SUBJECT, subject);
        contentValues.put(DatabaseHelper.AT_STUDENT_SEM, semester);
        contentValues.put(DatabaseHelper.AT_STUDENT_REGNO, regno);
        contentValues.put(DatabaseHelper.AT_STATUS, status);
        contentValues.put(DatabaseHelper.AT_DATE, date);
        long i = database.insert(DatabaseHelper.TABLE_NAME4, null, contentValues);
        return i;
    }

    //Fetching From Students Table
    public Cursor fetch() {
        String[] columns = new String[]{DatabaseHelper.S_ID, DatabaseHelper.S_NAME, DatabaseHelper.S_REGNO,
                DatabaseHelper.S_COURCE, DatabaseHelper.S_SEMESTER};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Fetching From Teacher Table
    public Cursor fetch_from_teacher() {
        String[] columns = new String[]{DatabaseHelper.T_ID, DatabaseHelper.T_NAME, DatabaseHelper.T_EMAIL,
                DatabaseHelper.T_CONTACT, DatabaseHelper.T_USERID, DatabaseHelper.T_PASSWORD};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME1, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


//    //Fetching From Admin
//    public Cursor fetch_from_admin(){
//        String[] columns = new String[]{DatabaseHelper.A_ID,DatabaseHelper.USERNAME,DatabaseHelper.PASSWORD};
//        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME2,columns,null,null,null,null,null);
//        if(cursor!=null){
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }

    //Fetching from Subject
    public Cursor fetch_from_Subject() {
        String[] columns = new String[]{DatabaseHelper.SUB_ID, DatabaseHelper.SUB_NAME, DatabaseHelper.SUB_CODE, DatabaseHelper.SUB_T_ID
                , DatabaseHelper.SUB_COURCE, DatabaseHelper.SUB_SEM};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME3, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    //Login System
    public boolean login(String username, String password) {
        Cursor cursor = database.rawQuery("Select * FROM " + DatabaseHelper.TABLE_NAME2 +
                " WHERE username=? AND password=?", new String[]{username, password});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

    //Teacher Login
    public boolean Teacher_login(String teacherId, String password) {
        Cursor cursor = database.rawQuery("SELECT uid,password FROM " + DatabaseHelper.TABLE_NAME1 +
                " WHERE uid=? AND password=?", new String[]{teacherId, password});
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }

//    //Auto FIll TextView
//    public Cursor autofill_fetch(){
//        String[] columns = new String[]{DatabaseHelper.T_ID,DatabaseHelper.T_USERID};
//        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME1,columns,null,null,null,null,null);
//        if (cursor!=null){
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }

    //fetch teacher subject
    public Cursor teacher_attendence_subjects() {

        String[] col = new String[]{DatabaseHelper.SUB_ID, DatabaseHelper.SUB_NAME};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME3, col, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String sub = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SUB_NAME));
            }
        }
        return cursor;
    }

    //get subjects for studentPortal
    public Cursor getSubjects() {
        String[] col = new String[]{DatabaseHelper.SUB_ID, DatabaseHelper.SUB_NAME};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME3, col, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // get Attendence List
    public Cursor get_Attendence_List(String c, String s) {

        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME +
                " WHERE cource=? AND semester=?", new String[]{c, s});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    //View Attendence database

    public Cursor get_Attendece_data() {

        String[] column = new String[]{DatabaseHelper.AT_ID, DatabaseHelper.AT_STUDENT_NAME, DatabaseHelper.AT_STUDENT_REGNO
                , DatabaseHelper.AT_STUDENT_CLASS, DatabaseHelper.AT_STUDENT_SEM, DatabaseHelper.AT_SUBJECT,
                DatabaseHelper.AT_DATE, DatabaseHelper.AT_STATUS};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME4, column, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // View Attendence of Student

    public Cursor View_attendence_by_student(String regno, String course, String sem, String sub) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME4 +
                " WHERE sregno=? AND sclass=? AND subject=? AND ssem=?", new String[]{regno, course, sub, sem});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    // View Attendence of Class

    public  Cursor View_attendence_of_class(String course, String sem, String sub, String date) {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME4 +
                " WHERE sclass=? AND subject=? AND ssem=? AND date=?", new String[]{course, sub, sem, date});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Forgot Password

    public Cursor forgot_pass(String email){

        Cursor cursor = database.rawQuery("SELECT uid,password FROM "+DatabaseHelper.TABLE_NAME1+
        " WHERE  email=?",new String[]{email});
        if (cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean register_email_exixts(String email){

        Cursor cursor = database.rawQuery("SELECT email FROM "+DatabaseHelper.TABLE_NAME1+
                " WHERE  email=?",new String[]{email});
        if (cursor!=null){
                if (cursor.getCount() > 0) {
                    return true;
                }
            }return false ;
    }

    //Update Student Details
    public int update(long _id, String name, String regno, String cource, String semester) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.S_NAME, name);
        contentValues.put(DatabaseHelper.S_REGNO, regno);
        contentValues.put(DatabaseHelper.S_COURCE, cource);
        contentValues.put(DatabaseHelper.S_SEMESTER, semester);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.S_ID + " = " + _id, null);
        return i;
    }


    //Update Teacher Details
    public int update_teacher(long _id, String name, String email, Integer contact, String uid, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.T_NAME, name);
        contentValues.put(DatabaseHelper.T_EMAIL, email);
        contentValues.put(DatabaseHelper.T_CONTACT, contact);
        contentValues.put(DatabaseHelper.T_USERID, uid);
        contentValues.put(DatabaseHelper.T_PASSWORD, password);
        int T_i = database.update(DatabaseHelper.TABLE_NAME1, contentValues, DatabaseHelper.T_ID + " = " + _id, null);
        return T_i;
    }

    // update Admin
    public int update_admin(long _id, String Username, String Password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USERNAME, Username);
        contentValues.put(DatabaseHelper.PASSWORD, Password);
        int A_i = database.update(DatabaseHelper.TABLE_NAME2, contentValues, DatabaseHelper.A_ID + " = " + _id, null);
        return A_i;
    }

    //Update Subject
    public int update_Subject(long _id, String S_name, String T_id, int code, String cource, String sem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUB_NAME, S_name);
        contentValues.put(DatabaseHelper.SUB_T_ID, T_id);
        contentValues.put(DatabaseHelper.SUB_CODE, code);
        contentValues.put(DatabaseHelper.SUB_COURCE, cource);
        contentValues.put(DatabaseHelper.SUB_SEM, sem);
        int Sub = database.update(DatabaseHelper.TABLE_NAME3, contentValues, DatabaseHelper.SUB_ID + " = " + _id, null);
        return Sub;
    }

    public int update_attendence(long _id, String status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        int i = database.update(DatabaseHelper.TABLE_NAME4, contentValues, DatabaseHelper.AT_ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.S_ID + "=" + _id, null);
    }

    public void delete_teacher(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME1, DatabaseHelper.T_ID + "=" + _id, null);
    }

    public void delete_admin(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME2, DatabaseHelper.A_ID + "=" + _id, null);
    }

    public void delete_subject(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME3, DatabaseHelper.SUB_ID + "=" + _id, null);
    }

    public void delete_attendence_record(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME4, DatabaseHelper.AT_ID + " = " + _id, null);
    }

    public int delete_attendence_Class_record(String course, String sem, String subject, String date) {

    int i =database.delete(DatabaseHelper.TABLE_NAME4,DatabaseHelper.AT_STUDENT_CLASS +"=? AND "+
    DatabaseHelper.AT_STUDENT_SEM+"=? AND "+DatabaseHelper.AT_SUBJECT+"=? AND "+DatabaseHelper.AT_DATE+"=?",
     new String[]{course,sem,subject,date});

    return i;
    }





}
