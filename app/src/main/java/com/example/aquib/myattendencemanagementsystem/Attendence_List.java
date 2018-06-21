package com.example.aquib.myattendencemanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Attendence_List extends AppCompatActivity {

    private ListView listView;
    private SimpleCursorAdapter adapter;
    private DBManager dbManager;


    final String[] from = new String[]{DatabaseHelper.S_NAME,DatabaseHelper.S_REGNO,DatabaseHelper.S_COURCE};

    final int[] to = new int[]{R.id.student_attendence_name,R.id.student_attendence_regNo,R.id.student_attendence_Cource};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendence__list);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager = new DBManager(this);
        dbManager.open();


        Intent in = getIntent();
        String course   = in.getStringExtra("course");
        String semester = in.getStringExtra("semester");


        Cursor cursor  = dbManager.get_Attendence_List(course,semester);

        listView = findViewById(R.id.attendenceList);
        listView.setEmptyView(findViewById(R.id.emptyAttendenceSheet));

        adapter = new SimpleCursorAdapter(Attendence_List.this,R.layout.attendence__row,cursor,from,to,0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
    }
    public void v(View view) {

        try {
            Button submit = findViewById(R.id.Submit_attendence);

            Intent intent = getIntent();
            String subject = intent.getStringExtra("sub");
            String date = intent.getStringExtra("Date");
            String semester = intent.getStringExtra("semester");


            for (int i = 0; i < listView.getCount(); i++) {

                View v = getViewByPosition(i, listView);

                RadioGroup radioGroup = v.findViewById(R.id.radio_group);

                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radio_stat = (RadioButton) findViewById(selectedId);

                String stat = radio_stat.getText().toString();

                TextView t = v.findViewById(R.id.student_attendence_name);
                String name = t.getText().toString();

                TextView t1 = v.findViewById(R.id.student_attendence_regNo);
                String reg = t1.getText().toString();

                TextView t2 = v.findViewById(R.id.student_attendence_Cource);
                String cour = t2.getText().toString();

                long success = dbManager.insert_into_attendence(name, cour, subject, semester, reg, stat, date);
                if (success > 0) {
                    submit.setVisibility(View.GONE);

                    new AlertDialog.Builder(Attendence_List.this)
                            .setTitle("Success")
                            .setMessage("Attendence Successfully Taken")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent in = new Intent(Attendence_List.this, MarkAttendence.class);
                                    in.setFlags(in.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(in);
                                    Attendence_List.this.finish();
                                }
                            }).show();

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void reset(View view){

        for(int i=0; i<listView.getCount(); i++){

            View v = getViewByPosition(i,listView);

            RadioGroup radioGroup = v.findViewById(R.id.radio_group);
            radioGroup.clearCheck();
        }
    }


    public View getViewByPosition(int position, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (position < firstListItemPosition || position > lastListItemPosition ) {
            return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
        } else {
            final int childIndex = position - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
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
