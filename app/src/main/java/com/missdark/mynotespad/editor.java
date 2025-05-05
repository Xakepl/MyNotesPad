package com.missdark.mynotespad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class editor extends AppCompatActivity implements Serializable {

    private SharedPreferences sharedPreferences;
    EditText title;
    String strTitle;
    String ftext;
    EditText text;
    ImageView save;
    ImageView back;
    ImageView clear;
    ImageView sttext;
    String strText;
    File file;

    MaterialToolbar mtlbr;
    Spinner FSspinner;

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
            FSspinner = findViewById(R.id.font_size);
            sttext = findViewById(R.id.textStyle);
            mtlbr = findViewById(R.id.materialToolbar);
            back.setOnClickListener(v -> {
                save();
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

            sttext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            mtlbr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        hideKeyboardForTitle(v);
                        hideKeyboardForText(v);
                    }
                }
            });


            title.setOnClickListener(v -> {
                FSspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (FSspinner.getSelectedItem().toString()) {
                            case "8":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);break;
                            case "9":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);break;
                            case "10":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);break;
                            case "11":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);break;
                            case "12":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);break;
                            case "14":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);break;
                            case "16":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);break;
                            case "18":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);break;
                            case "20":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);break;
                            case "22":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);break;
                            case "24":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);break;
                            case "26":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);break;
                            case "28":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);break;
                            case "36":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);break;
                            case "48":title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 48);break;
                        }
                        //TODO Сделать пандинги для заголовка
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            });
            text.setOnClickListener(v -> {
                FSspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                            switch (FSspinner.getSelectedItem().toString()) {
                            case "8":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);break;
                            case "9":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);break;
                            case "10":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);break;
                            case "11":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);break;
                            case "12":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);break;
                            case "14":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);break;
                            case "16":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);break;
                            case "18":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);break;
                            case "20":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);break;
                            case "22":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);break;
                            case "24":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);break;
                            case "26":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);break;
                            case "28":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28);break;
                            case "36":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);break;
                            case "48":text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 48);break;
                            }
                        }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

            });

        }

    void openAndEdit() throws FileNotFoundException {
        file = (File) getIntent().getSerializableExtra("FILE");
        sharedPreferences = getPreferences(MODE_PRIVATE);
      Log.e("TitleSizeText", String.valueOf(sharedPreferences.getFloat("TitleSize", title.getTextSize())));
        Log.e("TextSizeText", String.valueOf(sharedPreferences.getFloat("TitleSize", title.getTextSize())));
        title.setTextSize(sharedPreferences.getFloat("TitleSize", title.getTextSize())/1.75f);
        text.setTextSize(sharedPreferences.getFloat("TextSize", text.getTextSize())/1.75f);
        //1.75
        Log.e("TitleSizeText1", String.valueOf(sharedPreferences.getFloat("TitleSize", title.getTextSize())));
        Log.e("TextSizeText1", String.valueOf(sharedPreferences.getFloat("TitleSize", title.getTextSize())));
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
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor seditor = sharedPreferences.edit();
        seditor.putFloat("TitleSize", title.getTextSize());
        seditor.putFloat("TextSize", text.getTextSize());
        seditor.apply();
        file = (File) getIntent().getSerializableExtra("FILE");
        strTitle = title.getText().toString();
        strText = text.getText().toString();
        strTitle = strTitle + "\n";
        ftext = strTitle + strText;

        if(ftext.isEmpty()) {
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

    public void hideKeyboardForTitle(View  v){
        InputMethodManager imn = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imn.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    public void hideKeyboardForText(View v){
        InputMethodManager imn = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imn.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


//TODO СТИЛИ ТЕКСТА, КУРСИВ ЖИРНЫЙ ПОДЧЁРКНУТЫЙ И ЗАЧЁРКНУТЫЙ
    //TODO ЧАТ БОТ ИИ
//TODO темы для блокнота
    void clear(){
        title.setText("");
        text.setText("");
    }


    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    private void showPopupMenu(View v){
        PopupMenu ppmn = new PopupMenu(this, v);
        ppmn.inflate(R.menu.popupmenu);
        ppmn.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu1) {
                //Передать Жирный
                return true;
            }

            else if (item.getItemId() == R.id.menu2)
                    //Передать Курсив
                    return true;

            else if (item.getItemId() == R.id.menu3)
                    //Передать подчеркнутый
                    return true;

                else  return false;
        });

    }



    //https://clck.ru/3LbU9c
    //смена шрифтов

}
