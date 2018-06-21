package com.example.aquib.myattendencemanagementsystem;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

public class TeacherLoginActivity extends AppCompatActivity {
    private AutoCompleteTextView username;
    private EditText password;
    private DBManager dbManager;
    private final int REQUEST_CODE = 123;

    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherlogin);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager = new DBManager(this);
        dbManager.open();

        username = findViewById(R.id.Teacher_Username);
        password = findViewById(R.id.teacher_password);

        //Permission Request

    if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                    Intent intent = new Intent(this, Teacher_Portal.class);
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

    public void checkUserLogInorNot(){

        SharedPreferences preferences = getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
        String userLogin = preferences.getString(DISPLAY_NAME_KEY,null);

        if (userLogin!=null){
            Intent intent= new Intent(this,Teacher_Portal.class);
            startActivity(intent);}
        }



        public void registerUser(View view) {
            Intent intent = new Intent(TeacherLoginActivity.this,Register_User.class);
            startActivity(intent);
        }


    //action bar back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id  = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }




}

