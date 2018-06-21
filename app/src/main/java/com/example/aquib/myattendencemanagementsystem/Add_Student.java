package com.example.aquib.myattendencemanagementsystem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Add_Student extends AppCompatActivity {
    private EditText name, regno; //cource, semester;
    private DBManager dbManager;
    private Button add_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__student);

        //Action bar back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add_student = findViewById(R.id.Add_student_button);

        final Spinner courceSpinner = findViewById(R.id.addStudentCourceSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cource_names
                , R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        courceSpinner.setAdapter(adapter);

        final Spinner semSpinner = findViewById(R.id.addStudentSemesterSpinner);
        ArrayAdapter <CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                                                R.array.Semester_names,R.layout.spinner_item);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
        semSpinner.setAdapter(adapter1);


        name = findViewById(R.id.StudentName);
        regno = findViewById(R.id.StudentREGNO);

        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.Add_student_button:

                        final String Insert_name   = name.getText().toString();
                        final String Insert_regno  = regno.getText().toString();
                        final String Insert_cource = courceSpinner.getSelectedItem().toString();
                        final String Insert_sem    = semSpinner.getSelectedItem().toString();

                        try {
                            dbManager = new DBManager(Add_Student.this);
                            dbManager.open();

                            dbManager.insert(Insert_name, Insert_regno, Insert_cource, Insert_sem);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(Add_Student.this, ViewStudent.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        break;


                }
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
        int id = menu.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(menu);
    }




}

