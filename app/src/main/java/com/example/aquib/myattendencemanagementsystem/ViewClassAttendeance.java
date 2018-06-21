package com.example.aquib.myattendencemanagementsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import java.util.Calendar;

public class ViewClassAttendeance extends AppCompatActivity {

    private DBManager dbManager;
    private SimpleCursorAdapter simpleCursorAdapter;
    private EditText date;
    private DatePickerDialog datePickerDialog;

    private String[] from = new String[]{DatabaseHelper.SUB_NAME};
    private int[] to = new int[]{android.R.id.text1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_class_attendence);

        //TODO:Cource Spinner
        final Spinner course_spinner = findViewById(R.id.Class_Cource_Spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cource_names, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        course_spinner.setAdapter(adapter);


        //TODO:Semester spinner
        final Spinner sem_spinner = findViewById(R.id.Class_semester_spinner);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.Semester_names, R.layout.spinner_item);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sem_spinner.setAdapter(adapter1);


        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.getSubjects();


        //TODO: Subject Spinner
        final Spinner sub_spinner = findViewById(R.id.Class_subject_spinner);
        simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to, 0);
        simpleCursorAdapter.notifyDataSetChanged();
        simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sub_spinner.setAdapter(simpleCursorAdapter);

        //Attendence View Selector
        final Spinner aws = findViewById(R.id.SelectAttendenceViewSpinner2);

        ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(this, R.array.ViewAttendencefor, android.R.layout.simple_spinner_dropdown_item);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aws.setAdapter(adp);

        Button spin = findViewById(R.id.spinnerButton2);

        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String classwise = aws.getSelectedItem().toString();
                if (classwise.equals("Class Wise Attandance Details")) {
                    Intent in = new Intent(ViewClassAttendeance.this, ViewClassAttendeance.class);
                    startActivity(in);
                } else {
                    Intent in = new Intent(ViewClassAttendeance.this, StudentPortal.class);
                    startActivity(in);
                }

            }
        });


        // Date Picker

        date = findViewById(R.id.Classdatepick);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mday = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(ViewClassAttendeance.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int MonthofYear, int DayofMonth) {
                                date.setText(DayofMonth + "/" + (MonthofYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth,mday);
                datePickerDialog.show();
            }
        });


        Button getDetails = findViewById(R.id.ClassAttendenceButton);

        getDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String course = course_spinner.getSelectedItem().toString();
                String sem = sem_spinner.getSelectedItem().toString();

                Cursor cursor1 = (Cursor)sub_spinner.getSelectedItem();
                String subject = cursor1.getString(cursor1.getColumnIndex(DatabaseHelper.SUB_NAME));



                final String Date = date.getText().toString();

                Intent intent = new Intent(ViewClassAttendeance.this,ViewAttendenceByClassList.class);
                intent.putExtra("course",course);
                intent.putExtra("semester",sem);
                intent.putExtra("sub",subject);
                intent.putExtra("Date",Date);
                startActivity(intent);

            }
        });
    }
}



