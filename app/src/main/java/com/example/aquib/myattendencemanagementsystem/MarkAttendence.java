package com.example.aquib.myattendencemanagementsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MarkAttendence extends AppCompatActivity {
    private DBManager dbManager;
    private SimpleCursorAdapter adapter;
    private EditText date;
    private DatePickerDialog datePickerDialog;

    String[] from = new String[]{DatabaseHelper.SUB_NAME};
    int[] to = new int[]{android.R.id.text1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_attendence);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Date Picker Code
        date = findViewById(R.id.datepick);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(MarkAttendence.this,
                                    new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int MonthOfYear, int DayOfMonth) {
                        date.setText(DayOfMonth + "/" + (MonthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
                });



        //Spinners
        final Spinner cource_Spinner = findViewById(R.id.attendence_cource);
        final Spinner semester_spinner = findViewById(R.id.attendence_semester);

        //ArrayAdapter for COURSE spinner
        ArrayAdapter<CharSequence> cources_adapter = ArrayAdapter.createFromResource(this, R.array.cource_names, R.layout.spinner_item);
        cources_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        cource_Spinner.setAdapter(cources_adapter);

        //Arrayadapter for Semester
        final ArrayAdapter<CharSequence> sem_adapter = ArrayAdapter.createFromResource(this, R.array.Semester_names, R.layout.spinner_item);
        sem_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        semester_spinner.setAdapter(sem_adapter);




        //Subject Spinner

        final Spinner subject_Spinner = findViewById(R.id.attendence_subject);

        //open connection to database and fetching data
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.teacher_attendence_subjects();

        // SimpleCursorAdapter for a SUBJECT spinner
        adapter = new SimpleCursorAdapter(MarkAttendence.this, android.R.layout.simple_spinner_item, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        subject_Spinner.setAdapter(adapter);




        Button test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor1 = (Cursor) subject_Spinner.getSelectedItem();
                String sub = cursor1.getString(cursor1.getColumnIndex(DatabaseHelper.SUB_NAME));


                //selected cource
                String course = cource_Spinner.getSelectedItem().toString();

                //selected semester
                String sem = semester_spinner.getSelectedItem().toString();

                final String Date = date.getText().toString();


                Intent intent = new Intent(MarkAttendence.this,Attendence_List.class);
                intent.putExtra("course",course);
                intent.putExtra("semester",sem);
                intent.putExtra("sub",sub);
                intent.putExtra("Date",Date);
                startActivity(intent);



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
