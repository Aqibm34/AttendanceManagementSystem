package com.example.aquib.myattendencemanagementsystem;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forgot_password extends AppCompatActivity {

    private EditText email;
    private Button getpass;
    private DBManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        email = findViewById(R.id.forgotEmail);
        getpass = findViewById(R.id.forgotbutton);



        getpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    String Email = email.getText().toString();

                    try{
                    dbManager = new DBManager(Forgot_password.this);
                    dbManager.open();

                    Cursor cursor = dbManager.forgot_pass(Email);

                    String userid = cursor.getString(cursor.getColumnIndex(DatabaseHelper.T_USERID));
                  String password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.T_PASSWORD));

                        new AlertDialog.Builder(Forgot_password.this)
                                .setTitle("Your UserId And Password is:")
                                .setMessage("UserI: "+userid+"\nPassword: "+password)
                                .setPositiveButton(android.R.string.ok,null).show();


//                    String[] to = {Email};
//
//                        Intent emailintent = new Intent(Intent.ACTION_SEND);
//                        emailintent.setData(Uri.parse("mailto:"));
//                        emailintent.setType("text/plain");
//
//                        emailintent.putExtra(Intent.EXTRA_EMAIL,to);
//                        emailintent.putExtra(Intent.EXTRA_SUBJECT,"Your Password");
//                        emailintent.putExtra(Intent.EXTRA_TEXT,password);
//
//                        startActivity(Intent.createChooser(emailintent,"Send Email..."));
//                        finish();
//                        Log.d("AMS","finishedSendingEmail");

                }catch (Exception e){
                    e.printStackTrace();
                }


                //User Exixts OrNot
                if ((dbManager.register_email_exixts(Email))){

                }else{
                    new AlertDialog.Builder(Forgot_password.this)
                            .setTitle("User doesn't Exists")
                            .setMessage("User is not registered")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.ok,null).show();
                }

            }
        });
    }


}
