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

public class Modify_Student extends AppCompatActivity implements View.OnClickListener {

    private EditText nameText, regnoText, courceText, semText;
    private Button del_btn ,Update_btn;

    private long _id;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify__student);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager= new DBManager(this);
        dbManager.open();

        nameText   = findViewById(R.id.UpdateStudenrName);
        regnoText  = findViewById(R.id.UpdateStudentRegno);
        courceText = findViewById(R.id.UpdateStudent_cource);
        semText    = findViewById(R.id.UpdateStudent_semester);

        del_btn = findViewById(R.id.Delete_Student_Button);
        Update_btn = findViewById(R.id.Update_student_button);

        Intent intent = getIntent();
        String Id = intent.getStringExtra("id");
        String Name =intent.getStringExtra("name");
        String Regno = intent.getStringExtra("regno");
        String Cource =intent.getStringExtra("cource");
        String Sem = intent.getStringExtra("sem");

        _id = Long.parseLong(Id);

        nameText.setText(Name);
        regnoText.setText(Regno);
        courceText.setText(Cource);
        semText.setText(Sem);

        Update_btn.setOnClickListener(this);
        del_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Update_student_button:

                String U_name = nameText.getText().toString();
                String U_regno =regnoText.getText().toString();
                String U_cource =courceText.getText().toString();
                String U_sem = semText.getText().toString();

                dbManager.update(_id, U_name, U_regno ,U_cource, U_sem );
                this.returnHome();
                break;

            case R.id.Delete_Student_Button:
                dbManager.delete(_id);
                new AlertDialog.Builder(this)
                        .setTitle("Delete Student")
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
        Intent in = new Intent(Modify_Student.this, ViewStudent.class)
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
