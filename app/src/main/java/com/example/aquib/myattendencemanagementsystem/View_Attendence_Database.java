package com.example.aquib.myattendencemanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class View_Attendence_Database extends AppCompatActivity {

    private ListView listView;
    private DBManager dbManager;
    private SimpleCursorAdapter adapter;

    private String[] from = new String[]{DatabaseHelper.AT_ID,DatabaseHelper.AT_STUDENT_NAME,DatabaseHelper.AT_STUDENT_REGNO,DatabaseHelper.AT_STUDENT_CLASS,
    DatabaseHelper.AT_STUDENT_SEM,DatabaseHelper.AT_SUBJECT,DatabaseHelper.AT_DATE,DatabaseHelper.AT_STATUS};

   private int[] to  = new int[]{R.id.Attendence_Database_id,R.id.A_D_name,R.id.A_D_regno,R.id.A_D_cource,R.id.A_D_sem,R.id.A_D_subject,
    R.id.A_D_date,R.id.A_D_status};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_attendence_database);

        listView= findViewById(R.id.attendence_database_listview);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.get_Attendece_data();

        adapter = new SimpleCursorAdapter(View_Attendence_Database.this,R.layout.attendence_database_row,cursor,from,to,0);
        adapter.notifyDataSetChanged();

        listView.setEmptyView(findViewById(R.id.empty_attendence_database));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView id1  = view.findViewById(R.id.Attendence_Database_id);
                TextView n    = view.findViewById(R.id.A_D_name);
                TextView r    = view.findViewById(R.id.A_D_regno);
                TextView c    = view.findViewById(R.id.A_D_cource);
                TextView s    = view.findViewById(R.id.A_D_sem);
                TextView sub  = view.findViewById(R.id.A_D_subject);
                TextView d    = view.findViewById(R.id.A_D_date);
                TextView stat = view.findViewById(R.id.A_D_status);

                String id = id1.getText().toString();
                String name = n.getText().toString();
                String reg = r.getText().toString();
                String course = c.getText().toString();
                String sem = s.getText().toString();
                String subject = sub.getText().toString();
                String date = d.getText().toString();
                String status = stat.getText().toString();

                Intent mintent = new Intent(View_Attendence_Database.this, Modify_Attendence_Database.class);
                mintent.putExtra("id",id);
                mintent.putExtra("name",name);
                mintent.putExtra("reg", reg);
                mintent.putExtra("cource", course);
                mintent.putExtra("sem", sem);
                mintent.putExtra("subject", subject);
                mintent.putExtra("date", date);
                mintent.putExtra("status", status);

                startActivity(mintent);




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
