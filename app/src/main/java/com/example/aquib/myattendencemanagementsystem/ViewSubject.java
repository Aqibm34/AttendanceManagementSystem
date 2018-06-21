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

public class ViewSubject extends AppCompatActivity {

    private ListView sub_list;
    private DBManager dbManager;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[]{DatabaseHelper.SUB_ID, DatabaseHelper.SUB_NAME, DatabaseHelper.SUB_T_ID
            , DatabaseHelper.SUB_CODE, DatabaseHelper.SUB_COURCE, DatabaseHelper.SUB_SEM};

    final int[] to = new int[]{R.id.sub_idTV, R.id.sub_nameTV, R.id.sub_TidTV, R.id.sub_codeTV, R.id.sub_courceTV,
            R.id.sub_semTV};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_subject);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch_from_Subject();

        sub_list = findViewById(R.id.Subject_Listview);
        sub_list.setEmptyView(findViewById(R.id.emptyS));

        adapter = new SimpleCursorAdapter(ViewSubject.this, R.layout.view_subject_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        sub_list.setAdapter(adapter);

        // TODO: onClick listener on item selected

        sub_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long viewId) {

                TextView idTv     = view.findViewById(R.id.sub_idTV);
                TextView nameTv   = view.findViewById(R.id.sub_nameTV);
                TextView TidTv    = view.findViewById(R.id.sub_TidTV);
                TextView codeTv   = view.findViewById(R.id.sub_codeTV);
                TextView courceTv = view.findViewById(R.id.sub_courceTV);
                TextView semTv    = view.findViewById(R.id.sub_semTV);

                String id = idTv.getText().toString();
                String name = nameTv.getText().toString();
                String Tid = TidTv.getText().toString();
                int code = Integer.parseInt(codeTv.getText().toString());
                String course = courceTv.getText().toString();
                String sem = semTv.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), Modify_Subject.class);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("name", name);
                modify_intent.putExtra("tid", Tid);
                modify_intent.putExtra("code", code);
                modify_intent.putExtra("cource", course);
                modify_intent.putExtra("sem", sem);

                startActivity(modify_intent);
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


