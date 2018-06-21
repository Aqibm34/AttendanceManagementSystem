package com.example.aquib.myattendencemanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register_User extends AppCompatActivity {
   private EditText TeacherName , Email , Contact , UserId , Password;
   private Button AddTeacher;
   private  DBManager dbManager;

   private MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TeacherName = findViewById(R.id.TecherName);
        Email       = findViewById(R.id.Teacher_email);
        UserId      = findViewById(R.id.UniqueID);
        Password    = findViewById(R.id.password);

        AddTeacher = findViewById(R.id.Add_techer_button);

        dbManager = new DBManager(this);
        dbManager.open();

        AddTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.Add_techer_button:

                        try {
                            final String teacher_name = TeacherName.getText().toString();
                            final String teacher_email = Email.getText().toString();
                            final String teacher_userId = UserId.getText().toString();
                            final String teacher_password = Password.getText().toString();


                            dbManager.insert_into_teacher(teacher_name, teacher_email, 123, teacher_userId, teacher_password);

                            new AlertDialog.Builder(Register_User.this)
                                    .setTitle("Success")
                                    .setMessage(" Registered Successfully")
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {


                                            Intent intent = new Intent(Register_User.this, MainActivity.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);

                                        }
                                    }).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }




                        break;
                }
            }
        });
    }


    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: logic to check for a valid password
        return password.length() > 4;
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
