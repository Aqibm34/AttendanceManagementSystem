package com.example.aquib.myattendencemanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ViewAttendenceByStudentList extends AppCompatActivity {

    private DBManager dbManager;
    private SimpleCursorAdapter adapter;
    private ListView listView;

    private String[] from = new String[]{DatabaseHelper.AT_STUDENT_NAME,DatabaseHelper.AT_STUDENT_REGNO
    ,DatabaseHelper.AT_STUDENT_CLASS,DatabaseHelper.AT_STUDENT_SEM,DatabaseHelper.AT_SUBJECT,DatabaseHelper.AT_DATE,
    DatabaseHelper.AT_STATUS};

    private int[] to = new int[]{R.id.Student_attendence_details_name,R.id.Student_attendence_details_regno,R.id.Student_attendence_details_cource,
    R.id.Student_attendence_details_sem,R.id.Student_attendence_details_subject,R.id.Student_attendence_details_date,
    R.id.Student_attendence_details_status};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_attendence_by_student_list);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent in = getIntent();
        String regno=in.getStringExtra("regno");
        String course =in.getStringExtra("course");
        String sem =in.getStringExtra("sem");
        String subject =in.getStringExtra("subject");


        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.View_attendence_by_student(regno,course,sem,subject);

        listView = findViewById(R.id.students_attenence_details_listview);
        listView.setEmptyView(findViewById(R.id.empty_attendence_details));

        adapter = new SimpleCursorAdapter(ViewAttendenceByStudentList.this,R.layout.students_attendence_details_row,
                cursor,from,to,0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

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
