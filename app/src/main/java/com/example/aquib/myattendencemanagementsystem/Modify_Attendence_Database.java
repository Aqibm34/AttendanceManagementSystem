package com.example.aquib.myattendencemanagementsystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Modify_Attendence_Database extends AppCompatActivity implements View.OnClickListener {

    private DBManager dbManager;
    private Button upd,del;
    private EditText Estat;
    private long _id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify__attendence__database);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager = new DBManager(this);
        dbManager.open();


        Estat  = findViewById(R.id.a_d_modify_status);

        upd = findViewById(R.id.Update_student_attendence);
        del = findViewById(R.id.Delete_Student_Attendence);

        Intent in = getIntent();
        String stat = in.getStringExtra("status");
        String id = in.getStringExtra("id");

        _id = Long.parseLong(id);

        Estat.setText(stat);

        upd.setOnClickListener(this);
        del.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Update_student_attendence:

                String new_stat = Estat.getText().toString();

                dbManager.update_attendence(_id,new_stat);
                this.returnHome();
                break;
            case R.id.Delete_Student_Attendence:
                dbManager.delete_attendence_record(_id);
                new AlertDialog.Builder(this)
                        .setTitle("Delete Attendence Record")
                        .setMessage("Are You Sure ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                returnHome();
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

    public void returnHome(){
        Intent in = new Intent(Modify_Attendence_Database.this, View_Attendence_Database.class)
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
