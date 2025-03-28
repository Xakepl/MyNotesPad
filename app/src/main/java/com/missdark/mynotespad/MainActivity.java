package com.missdark.mynotespad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.*;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements Serializable {

    FloatingActionButton create;
    ProjectsDB mDBConnector;
    Projects md;
//    Context mContext;
//    ListView mListView;
//    SimpleCursorAdapter scAdapter;
//    Cursor cursor;
//    myListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        Log.d("Date","DATE : " + strDate);
//        mContext = this;
//        mDBConnector = new ProjectsDB(this);
//        mListView=(ListView)findViewById(R.id.list);
//        myAdapter=new myListAdapter(mContext,mDBConnector.selectAll());
//        mListView.setAdapter(myAdapter);
//        registerForContextMenu(mListView);

        create = findViewById(R.id.create);
        create.setOnClickListener(v -> {
            // Создаем EditText
            final EditText inputEditText = new EditText(this);
            inputEditText.setHint("Название");
            inputEditText.setPadding(32, 32, 32, 32);

// Создаем AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ввод данных")
                    .setMessage("Пожалуйста, введите название заметки:")
                    .setView(inputEditText)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String inputText = inputEditText.getText().toString();
                            if (!inputText.isEmpty()) {
                               Intent intent = new Intent(MainActivity.this, editor.class);
                                File file = new File(getFilesDir(), (String)inputText +".txt");
                                intent.putExtra("FILE", file);
                                startActivity(intent);
                                new Thread(() -> {
                                    mDBConnector.insert(inputText, Long.parseLong(strDate), (String)file.getPath());

                                }).start();
                            }
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }



}