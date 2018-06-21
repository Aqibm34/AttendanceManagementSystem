package com.example.aquib.myattendencemanagementsystem;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView username;
    private EditText password;
    private DBManager dbManager;
    private final int REQUEST_CODE = 123;

    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);
        dbManager.open();



        username = findViewById(R.id.Teacher_Username);
        password = findViewById(R.id.teacher_password);

        //Permission Request

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            //Requesting Permissions
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
            return;
        }

    }
    //Act on User Permissions

    @Override
    public void onRequestPermissionsResult(int requestcode,@NonNull String[] permissions ,
                                           @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestcode,permissions,grantResults);

        if(requestcode==REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("AMS","onRequestPermissionCalled");
            }
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        checkUserLogInorNot();
        checkUserExixtsInorNot();
    }

    // Adding Menu to Activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.import_menu:
                importDb();
                break;
            case R.id.Export:
                exportDb();
                break;
            case R.id.exit:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
    }



    public void TeacherLogin(View view) {

        String uid = username.getText().toString();
        String pass = password.getText().toString();

        //Saving uid for attendence
        SharedPreferences prefs = getSharedPreferences(CHAT_PREFS,0);
        prefs.edit().putString(DISPLAY_NAME_KEY,uid).apply();


        if (username.length() > 0 && password.length() > 0) {

            try {


                if (dbManager.Teacher_login(uid, pass)) {
                    Toast.makeText(this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Dash_Main.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);

                }else {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Oops")
                            .setMessage("Invalid Credentials Try Again!!")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }

            } catch (Exception e) {

            }

        }
    }

    public void  checkUserLogInorNot(){

        SharedPreferences preferences = getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
        String userLogin = preferences.getString(DISPLAY_NAME_KEY,null);

        if (userLogin!=null){
            Intent intent= new Intent(this,Dash_Main.class);
            startActivity(intent);}
    }

    public void  checkUserExixtsInorNot(){

        dbManager = new DBManager(this);
        dbManager.open();

        Cursor c  = dbManager.fetch_from_teacher();
        int result = c.getCount();

        Button register = findViewById(R.id.registration_button);

        if (result == 1){
        register.setVisibility(View.GONE);
        }
    }




    public void  registerUser(View view) {
        Intent intent = new Intent(this,Register_User.class);
        startActivity(intent);
        this.finish();
    }





    // Export Database

    public void exportDb(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();

        String currentDBPath = "/data/" + getPackageName() + "/databases/MyAttendenceManager.DB";
        //String currentDBPath = DatabaseHelper.currentDBPath;
        String backupDBPath = "backupname.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();


            Toast.makeText(this, "Exported!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
        }

    }



    //Import Database
    public void importDb(){

        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File sd = new File(dir);
        File data = Environment.getDataDirectory();

        FileChannel source = null;
        FileChannel destination = null;

        String backupDbPath = "/data/" + getPackageName() + "/databases/MyAttendenceManager.DB";
        String currentDbPath = "backupname.db";

        File currentdb = new File(sd,currentDbPath);
        File backupDb = new File(data,backupDbPath);

        try{
            source = new FileInputStream(currentdb).getChannel();
            destination = new FileOutputStream(backupDb).getChannel();
            destination.transferFrom(source,0,source.size());
            source.close();
            destination.close();
            Toast.makeText(this,"Importing",Toast.LENGTH_SHORT).show();

        }catch (IOException e){
            e.printStackTrace();}
    }


    public void forgotpassword(View view){

        Intent intent = new Intent(this,Forgot_password.class);
        startActivity(intent);

    }
    
}
