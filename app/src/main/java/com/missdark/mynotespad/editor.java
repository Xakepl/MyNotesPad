package com.missdark.mynotespad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class editor extends AppCompatActivity implements Serializable {
    EditText title;
    String strTitle;
    EditText text;
    ImageView save;
    ImageView back;
    String strText;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        file = (File) getIntent().getSerializableExtra("FILE");
        //DO перенести в трай ФОС
        FileOutputStream fos;
        try {
             fos = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                if(title.getText().toString() != null && text.getText().toString() != null) {
                    strTitle = (String)title.getText().toString();
                    strText = (String)text.getText().toString();
                    strTitle = strTitle + "\n";
                    fos.write((strTitle + "\n" + strText).getBytes());
                    Toast.makeText(editor.this, "Файл успешно сохранён", Toast.LENGTH_SHORT);
                }
                else{
                    Toast.makeText(editor.this, "Ошибка сохранения", Toast.LENGTH_SHORT);
                }
                } catch (IOException e) {
                    Toast.makeText(editor.this, "Ошибка сохранения", Toast.LENGTH_SHORT);
                    throw new RuntimeException(e);
                }
            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(editor.this, MainActivity.class);
                startActivity(i);
            }
        });
        //FileOnputStream fos = new FileInputStream();


    }
}