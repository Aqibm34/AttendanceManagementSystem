package com.example.aquib.myattendencemanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

public class Add_Subject extends AppCompatActivity {

    private EditText Sub_nameET,Sub_codeET,Sub_courceET,Sub_sem_ET;
    private Button Add_sub;
    private AutoCompleteTextView Sub_TidET;
    private  DBManager dbManager;
    private SimpleCursorAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Action bar back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Add Record");
        setContentView(R.layout.activity_add__subject);

        dbManager = new DBManager(this);
        dbManager.open();

        Sub_nameET = findViewById(R.id.sub_name);
        Sub_codeET = findViewById(R.id.sub_code);

        final Spinner CourceSpinner = findViewById(R.id.addSubjectCourceSpinner);
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cource_names,
                                            R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        CourceSpinner.setAdapter(adapter);

        final Spinner SemSpinner = findViewById(R.id.addSubjectSemesterSpinner);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.Semester_names,
                                            R.layout.spinner_item);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_item);
        SemSpinner.setAdapter(adapter1);

        Add_sub = findViewById(R.id.Add_subject_button);

//       Sub_TidET.setAdapter(Adapter);

        Add_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.Add_subject_button:

                        final String Sname = Sub_nameET.getText().toString();
                        final int Scode = Integer.parseInt(Sub_codeET.getText().toString());
                        final String Scource = CourceSpinner.getSelectedItem().toString();
                        final String Ssem = SemSpinner.getSelectedItem().toString();

    try {
        dbManager.insert_into_subject(Sname, "", Scode, Scource, Ssem);
        Intent in = new Intent(Add_Subject.this, ViewSubject.class)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(in);
        break;
        }    catch (Exception e){
                e.printStackTrace();
                    }
                }

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
