package com.jinghan.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.jinghan.memo.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {
    private int id;
    TextView time_view,memo_edit;
    String currentDateandTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        id=1;
        time_view=findViewById(R.id.time_view);
        currentDateandTime = DateUtil.getTime();
        time_view.setText(currentDateandTime);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void saveData(String content){
        SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString(currentDateandTime,content);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.finish_btn){
            memo_edit=findViewById(R.id.memo_edit);
            String concent=memo_edit.getText().toString();

            saveData(concent);

            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();
            Intent back=new Intent(CreateActivity.this,MainActivity.class);
            startActivity(back);
        }
        return super.onOptionsItemSelected(item);
    }

}
