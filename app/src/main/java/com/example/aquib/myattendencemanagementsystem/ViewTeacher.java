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

public class ViewTeacher extends AppCompatActivity {
    private ListView Tlist;
    private DBManager dbManager;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[]{DatabaseHelper.T_ID, DatabaseHelper.T_NAME, DatabaseHelper.T_EMAIL, DatabaseHelper.T_CONTACT,
            DatabaseHelper.T_USERID, DatabaseHelper.T_PASSWORD};
    final int[] to = new int[]{R.id.t_idTV, R.id.t_nameTV, R.id.t_emailTV, R.id.t_contactTV, R.id.t_useridTV, R.id.t_passwordTV};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_teacher);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager = new DBManager(this);
        dbManager.open();

        Cursor cursor = dbManager.fetch_from_teacher();

        Tlist = findViewById(R.id.Teacher_ListView);
        Tlist.setEmptyView(findViewById(R.id.T_empty));

        adapter = new SimpleCursorAdapter(ViewTeacher.this, R.layout.view_teacher_list_record,
                cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        Tlist.setAdapter(adapter);

        //TODO: On click listener for item selected.
        Tlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long viewId) {

                TextView IdTextV = findViewById(R.id.t_idTV);
                TextView NameTextV = findViewById(R.id.t_nameTV);
                TextView EmailTextV = findViewById(R.id.t_emailTV);
                TextView ContactTextV = findViewById(R.id.t_contactTV);
                TextView UidTextV = findViewById(R.id.t_useridTV);
                TextView PasswordTextV = findViewById(R.id.t_passwordTV);

                String techer_id = IdTextV.getText().toString();
                String teacher_name = NameTextV.getText().toString();
                String teacher_email = EmailTextV.getText().toString();
                int teacher_contact = Integer.parseInt(ContactTextV.getText().toString());
                String teacher_uid = UidTextV.getText().toString();
                String teacher_password = PasswordTextV.getText().toString();

                Intent intent = new Intent(ViewTeacher.this, Modify_Teacher.class);
                intent.putExtra("id", techer_id);
                intent.putExtra("name", teacher_name);
                intent.putExtra("email", teacher_email);
                intent.putExtra("contact", teacher_contact);
                intent.putExtra("Uid", teacher_uid);
                intent.putExtra("password", teacher_password);

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