package com.missdark.mynotespad;

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
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class editor extends AppCompatActivity {
    EditText title;
    String strTitle;
    EditText text;
    ImageView save;
    String strText;

    String fileS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        fileS = (String) getIntent().getSerializableExtra("FILE");
        File file = new File(fileS);

        FileOutputStream fos;

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fos = new FileOutputStream(file, true);
                    fos.write(strTitle.getBytes(StandardCharsets.UTF_8));
                    fos.write(strText.getBytes(StandardCharsets.UTF_8)); // Указываем кодировку
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        //FileOnputStream fos = new FileInputStream();


    }
}