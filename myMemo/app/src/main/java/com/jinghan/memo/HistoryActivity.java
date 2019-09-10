package com.jinghan.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    LinkedList<HashMap<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final ListView listView = findViewById(R.id.list_view);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        Map<String, ?> allEntries = pref.getAll();

        list = new LinkedList<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("itemDate", entry.getKey());
            map.put("itemContent", entry.getValue().toString());
            list.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.text,
                new String[]{"itemDate", "itemContent"},
                new int[]{R.id.itemDate, R.id.itemContent});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(i);
                String date = map.get("itemDate");
                String content = map.get("itemContent");
                System.out.println(date + ": " + content);
                Intent intent=new Intent(HistoryActivity.this, EditActivity.class);
                intent.putExtra("itemContent",content);
                intent.putExtra("itemDate",date);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.add_btn){
            Intent intent=new Intent(HistoryActivity.this,CreateActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
