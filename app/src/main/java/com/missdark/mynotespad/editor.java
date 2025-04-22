package com.missdark.mynotespad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class editor extends AppCompatActivity implements Serializable {
    EditText title;
    String strTitle;
    String ftext;
    EditText text;
    ImageView save;
    ImageView back;

    ImageView clear;
    String strText;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        title = findViewById(R.id.titleText);
        text = findViewById(R.id.textC);
        save = findViewById(R.id.save);
        save.setOnClickListener(v -> save());
        back = findViewById(R.id.back);
        clear = findViewById(R.id.clear);
        back.setOnClickListener(v -> {
            Intent i = new Intent(editor.this, MainActivity.class);
            startActivity(i);
        });
        clear.setOnClickListener(v -> clear());

        if (getIntent().getSerializableExtra("STATE") == MainActivity.Status.OPENFILEANDEDIT) {
            try {
                openAndEdit();
            } catch (FileNotFoundException e) {
                Log.e("unsucces openFile", String.valueOf(e));
                throw new RuntimeException(e);
            }
        }

    }

    void openAndEdit() throws FileNotFoundException {
        file = (File) getIntent().getSerializableExtra("FILE");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            boolean isFirstLine = true;
            // Читаем файл построчно
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    title.setText(line); // Первая строка → Title
                    isFirstLine = false;
                } else {
                    content.append(line).append("\n"); // Остальное → Content
                }
            }
            reader.close();
            text.setText(content.toString()); // Заполняем поле контентом
        } catch (IOException e) {
        Toast.makeText(this, "Ошибка чтения файла", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
    }

    void save(){
        file = (File) getIntent().getSerializableExtra("FILE");
        strTitle = title.getText().toString();
        strText = text.getText().toString();
        strTitle = strTitle + "\n";
        ftext = strTitle + strText;

        if (ftext.isEmpty()) {
            Toast.makeText(this, "Введите текст!", Toast.LENGTH_SHORT).show();
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(ftext.getBytes());
            Toast.makeText(this, "Файл " + file.getName() + " сохранён: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Ошибка сохранения: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    void clear(){
        title.setText("");
        text.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    //https://clck.ru/3LbU9c
    //смена шрифтов

}