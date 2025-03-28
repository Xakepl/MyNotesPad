package com.missdark.mynotespad;

import android.annotation.SuppressLint;
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
    String strText;

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        file = (File) getIntent().getSerializableExtra("FILE");
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
                strTitle = strTitle + "\n";
                if(strTitle != null && strText != null) {
                    fos.write(strTitle.getBytes());
                    fos.write(strText.getBytes());
                }
                Toast.makeText(editor.this, "Файл успешно сохранён", Toast.LENGTH_SHORT);
                } catch (IOException e) {
                    Toast.makeText(editor.this, "Ошибка сохранения", Toast.LENGTH_SHORT);
                    throw new RuntimeException(e);
                }
            }
        });

        //FileOnputStream fos = new FileInputStream();


    }
}