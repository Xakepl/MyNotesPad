package com.missdark.mynotespad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        create = findViewById(R.id.create);
        create.setOnClickListener(v -> {
            // Создаем EditText
            final EditText inputEditText = new EditText(this);
            inputEditText.setHint("Введите текст");
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
                               startActivity(intent);
                               new Thread(() -> {
                                   try{
                                       FileOutputStream fos = new FileOutputStream(file);
                                       intent.putExtra("FILE_PATH", file.getAbsolutePath());

                                   } catch (FileNotFoundException e) {
                                       Toast.makeText(MainActivity.this,"Файл не был создан" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                       throw new RuntimeException(e);
                                   }

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