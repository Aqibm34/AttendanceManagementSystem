package com.example.aquib.myattendencemanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class StudentPortal extends AppCompatActivity {

    private DBManager dbManager;
    private EditText getRegET;
    private Button getAttendenceButton;

    private SimpleCursorAdapter simpleCursorAdapter;

    private String[] from = new String[]{DatabaseHelper.SUB_NAME};
    private int [] to = new int[]{android.R.id.text1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_portal_activity);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getRegET = findViewById(R.id.getRegno);
        getAttendenceButton = findViewById(R.id.getAttendenceButton);


        //TODO:Cource Spinner
        final Spinner course_spinner =findViewById(R.id.Cource_Spinner);

        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cource_names,R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        course_spinner.setAdapter(adapter);


        //TODO:Semester spinner
        final Spinner sem_spinner =  findViewById(R.id.semester_spinner);

        ArrayAdapter <CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.Semester_names,R.layout.spinner_item);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sem_spinner.setAdapter(adapter1);

        //Attendence View Selector
        final Spinner aw = findViewById(R.id.SelectAttendenceViewSpinner);

        ArrayAdapter <CharSequence> adp = ArrayAdapter.createFromResource(this,R.array.ViewAttendencefor,android.R.layout.simple_spinner_dropdown_item);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aw.setAdapter(adp);

        Button spin = findViewById(R.id.spinnerButton);

        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classwise = aw.getSelectedItem().toString();
                if(classwise.equals("Class Wise Attandance Details")){
                    Intent in = new Intent(StudentPortal.this,ViewClassAttendeance.class);
                    startActivity(in);
                }else{
                    Intent in = new Intent(StudentPortal.this,StudentPortal.class);
                    startActivity(in);
                }

            }
        });


        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.getSubjects();


        //TODO: Subject Spinner
        final  Spinner sub_spinner = findViewById(R.id.subject_spinner);
        simpleCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,cursor,from,to,0);
        simpleCursorAdapter.notifyDataSetChanged();
        simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sub_spinner.setAdapter(simpleCursorAdapter);

        getAttendenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String regNo = getRegET.getText().toString();
                String cource = course_spinner.getSelectedItem().toString();
                String sem =  sem_spinner.getSelectedItem().toString();

                //get Subjects from database
                Cursor cursor1 = (Cursor)sub_spinner.getSelectedItem();
                String subject = cursor1.getString(cursor1.getColumnIndex(DatabaseHelper.SUB_NAME));

                Intent in = new Intent(StudentPortal.this,ViewAttendenceByStudentList.class);
                in.putExtra("regno",regNo);
                in.putExtra("course",cource);
                in.putExtra("sem",sem);
                in.putExtra("subject",subject);

                startActivity(in);
            }
        });
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
