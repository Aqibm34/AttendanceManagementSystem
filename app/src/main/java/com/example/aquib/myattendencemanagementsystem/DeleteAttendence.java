package com.example.aquib.myattendencemanagementsystem;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class DeleteAttendence extends AppCompatActivity {

    private DBManager dbManager;
    private EditText date;
    private DatePickerDialog datePickerDialog;
    private SimpleCursorAdapter simpleCursorAdapter;

    private String[] from = new String[]{DatabaseHelper.SUB_NAME};
    private int[] to = new int[]{android.R.id.text1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_attendence);

        date = findViewById(R.id.Delete_datepick);

        final Spinner course = findViewById(R.id.Delete_Cource_Spinner);
        final Spinner sem = findViewById(R.id.Delete_semester_spinner);
        final Spinner subject = findViewById(R.id.Delete_subject_spinner);

        ArrayAdapter <CharSequence> adapterS = ArrayAdapter.createFromResource(this,R.array.Semester_names,android.R.layout.simple_spinner_item);
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(adapterS);

        ArrayAdapter <CharSequence> adapterC = ArrayAdapter.createFromResource(this,R.array.cource_names,android.R.layout.simple_spinner_item);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(adapterC);

        //Subject Spinner Data
        try {
            dbManager = new DBManager(this);
             dbManager.open();
            Cursor cursor = dbManager.getSubjects();

            simpleCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_spinner_item,cursor,from,to,0);
            simpleCursorAdapter.notifyDataSetChanged();
            simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            subject.setAdapter(simpleCursorAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }

        //Date picker

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int Mday = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(DeleteAttendence.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int MonthofYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (MonthofYear + 1)+"/" + year);
                    }
                },mYear,mMonth,Mday);
                datePickerDialog.show();
            }
                });

        Button delete = findViewById(R.id.Delete_AttendenceButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Course = course.getSelectedItem().toString();
                final String Semeester = sem.getSelectedItem().toString();

                Cursor cur =(Cursor) subject.getSelectedItem();
                final String subject = cur.getString(cur.getColumnIndex(DatabaseHelper.SUB_NAME));

                final String Date = date.getText().toString();

                new AlertDialog.Builder(DeleteAttendence.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are You Sure, You Want to Delete")
                        .setMessage("Selected Attendence Session Will be deleted")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                try{
                                    int j = dbManager.delete_attendence_Class_record(Course,Semeester,subject,Date);

                                    if (j>0){
                                        new AlertDialog.Builder(DeleteAttendence.this)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Success")
                                                .setMessage("Attendence Session Successfully deleted")
                                                .setPositiveButton(android.R.string.ok,null)
                                                .show();
                                    }else{
                                        new AlertDialog.Builder(DeleteAttendence.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Error Something Went Wrong")
                                                .setMessage("Action Unseccuessful , Try again with different inputs")
                                                .setPositiveButton(android.R.string.ok,null)
                                                .show();
                                    }

                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                             dialogInterface.dismiss();
                            }
                        }).show();
            }
        });

    }


    }



