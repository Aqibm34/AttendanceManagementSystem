package com.example.aquib.myattendencemanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.File;


public class Dash_Main extends AppCompatActivity {

    private MainActivity obj;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dash);

        //TODO: helps to file exposure error(Tmp)
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());




        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.signout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //action bar back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
           case  android.R.id.home:
            moveTaskToBack(true);
            break;
            case R.id.Logout_account:
            signout();
                break;
            case R.id.Edit_account:
                Intent in = new Intent(this,ViewTeacher.class);
                in.setFlags(in.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                break;
            case R.id.sendDb:
                send_file();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        moveTaskToBack(true);
    }


    public void signout(){
        new AlertDialog.Builder(this)
                .setTitle("Signout")
                .setTitle("Are you sure You Want to Signout")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SharedPreferences preferences = getSharedPreferences(obj.CHAT_PREFS,MODE_PRIVATE);
                        preferences.edit().remove(obj.DISPLAY_NAME_KEY).apply();

                        Intent intent = new Intent(Dash_Main.this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        Dash_Main.this.finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    public void start_teacher_dash(View view) {
        Intent intent = new Intent(this, Teacher_Portal.class);
        startActivity(intent);
        this.finish();
    }

    public void mark_attendence(View view) {
        Intent intent = new Intent(this, MarkAttendence.class);
        startActivity(intent);
    }

    public void Start_View_Attendence(View view) {
        Intent intent = new Intent(this,StudentPortal.class);
        startActivity(intent);
    }

    public void send_file(){
        File sourceFile = new File("//mnt/sdcard/backupname.db");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("application/x-sqlite3");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(sourceFile));
        startActivity(intent);
    }





}






