package com.example.aquib.myattendencemanagementsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class Teacher_Portal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_portal);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public void AddStudentButton(View view) {
        Intent intent = new Intent(this, Add_Student.class);
        startActivity(intent);
    }

    public void ViewStudentList(View view) {
        Intent intent = new Intent(Teacher_Portal.this, ViewStudent.class);
        startActivity(intent);
    }

    public void Add_Subject(View view) {
        Intent intent = new Intent(Teacher_Portal.this, Add_Subject.class);
        startActivity(intent);
    }

    public void EditSubjects(View view) {
        Intent i = new Intent(Teacher_Portal.this, ViewSubject.class);
        startActivity(i);
    }

    public void delete_class_attendence(View view){

        Intent in  = new Intent(this,DeleteAttendence.class);
        in.setFlags(in.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
    }






    //action bar back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id  = item.getItemId();

        if (id == android.R.id.home){

            Intent in = new Intent(Teacher_Portal.this,MainActivity.class);
            startActivity(in);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


