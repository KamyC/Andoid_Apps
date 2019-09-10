package com.jinghan.memo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jinghan.memo.utils.DateUtil;

public class EditActivity extends AppCompatActivity {
    EditText editText;
    Button saveBtn;
    String date;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editText = findViewById(R.id.edit_text);

        Bundle bundle = getIntent().getExtras();
        date = bundle.getString("itemDate");
        content = bundle.getString("itemContent");
        editText.setText(content);
        saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                Toast.makeText(EditActivity.this, "Saving", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void saveData() {
        content = editText.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.remove(date);
        String currentDateandTime = DateUtil.getTime();
        editor.putString(currentDateandTime, content);
        editor.commit();
    }

    public void deleteData() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditActivity.this);
        dialogBuilder.setTitle("Delete?");
        dialogBuilder.setMessage("Are you sure to delete");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.remove(date);
                editor.commit();
                Intent intent = new Intent(EditActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_btn) {
            deleteData();
        }
        return super.onOptionsItemSelected(item);
    }
}
