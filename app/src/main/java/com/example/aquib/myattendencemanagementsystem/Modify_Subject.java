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

public class Modify_Subject extends AppCompatActivity implements View.OnClickListener {

    private EditText name_ET,Tid_ET,code_ET,cource_ET,sem_ET;
    private Button upd_sub,del_sub;

    private long _id;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify__subject);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager =new DBManager(this);
        dbManager.open();

        name_ET = findViewById(R.id._modify_sub_name);
        code_ET = findViewById(R.id.modify_sub_code);
        cource_ET = findViewById(R.id.modify_sub_cource);
        sem_ET = findViewById(R.id.modify_sub_semester);

        upd_sub = findViewById(R.id.modify_subject_button);
        del_sub = findViewById(R.id.Delete_Subject_Button);

        Intent in= getIntent();
        String id     = in.getStringExtra("id");
        String name   = in.getStringExtra("name");
        int code   =in.getIntExtra("code",0);
        String cource = in.getStringExtra("cource");
        String sem    = in.getStringExtra("sem");

        _id = Long.parseLong(id);

        name_ET.setText(name);
        code_ET.setText(String.valueOf(code));
        cource_ET.setText(cource);
        sem_ET.setText(sem);

        upd_sub.setOnClickListener(this);
        del_sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.modify_subject_button:

                String subName = name_ET.getText().toString();
                int Code    = Integer.parseInt(code_ET.getText().toString());
                String Cource  = cource_ET.getText().toString();
                String Sem     = sem_ET.getText().toString();


                dbManager.update_Subject(_id,subName,"",Code,Cource,Sem);
                this.returnhome();
                break;

            case R.id.Delete_Subject_Button:
                dbManager.delete_subject(_id);

                new AlertDialog.Builder(this)
                        .setTitle("Delete Sbject")
                        .setMessage("Are You Sure ?")
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                returnhome();
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


    public void returnhome(){
        Intent in = new Intent(Modify_Subject.this,ViewSubject.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
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
