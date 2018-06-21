package com.example.aquib.myattendencemanagementsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Modify_Teacher extends AppCompatActivity implements View.OnClickListener {

    EditText Tname, Temail, Tcontact, Tuid, Tpass;
    Button upd_btn, del_btn;

    DBManager dbManager;
    long _id;
    private MainActivity obj;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify__teacher);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager = new DBManager(this);
        dbManager.open();

        Tname = findViewById(R.id.UpdateTeacherName);
        Temail = findViewById(R.id.UpdateTeacher_email);
        Tuid = findViewById(R.id.UPDATEUniqueID);
        Tpass = findViewById(R.id.Update_t_passwrd);

        upd_btn = findViewById(R.id.UpdateTeacherButton);
        del_btn = findViewById(R.id.DeleteTeacherButton);

        Intent intent = getIntent();
        String TId = intent.getStringExtra("id");
        String TName = intent.getStringExtra("name");
        String TEmail = intent.getStringExtra("email");
        String TUid = intent.getStringExtra("uid");
        String TPasswrd = intent.getStringExtra("password");

        _id = Long.parseLong(TId);

        Tname.setText(TName);
        Temail.setText(TEmail);
        Tuid.setText(TUid);
        Tpass.setText(TPasswrd);

        upd_btn.setOnClickListener(this);
        del_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UpdateTeacherButton:

                String modify_name = Tname.getText().toString();
                String modify_email = Temail.getText().toString();
                String modify_Uid = Tuid.getText().toString();
                String modify_password = Tpass.getText().toString();

                dbManager.update_teacher(_id, modify_name, modify_email, 123, modify_Uid, modify_password);
                this.ReturnHome();
                break;

            case R.id.DeleteTeacherButton:

                dbManager.delete_teacher(_id);
                new AlertDialog.Builder(this)
                        .setTitle("Delete User")
                        .setMessage("Are You Sure ?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ReturnHome();
                                Modify_Teacher.this.finish();
                                SharedPreferences preferences = getSharedPreferences(obj.CHAT_PREFS,MODE_PRIVATE);
                                preferences.edit().remove(obj.DISPLAY_NAME_KEY).apply();
                                Modify_Teacher.this.deleteDatabase("MyAttendenceManager.DB");


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                        break;

        }
    }

    public void ReturnHome() {
        Intent in = new Intent(Modify_Teacher.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);

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