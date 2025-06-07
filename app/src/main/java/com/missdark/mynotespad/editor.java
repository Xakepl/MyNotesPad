package com.missdark.mynotespad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
    String strText;
    File file;
    Spinner FSspinner;
    LinearLayout ed;
    Spinner FStyleSpinner;
    boolean clickTitle;
    boolean clickText;
    boolean clickLayout;
    int sizeText;
    int styleText;

    @Override
    protected void onStart() {
        super.onStart();
    }
//TODO html разметка
    //TODO просмотреть какие тextView принимают такой формат
    @SuppressLint("ClickableViewAccessibility")
//    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
        FStyleSpinner = findViewById(R.id.style);
        clickText = false;
        clickLayout = false;

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

        ed = findViewById(R.id.editorL);

        ed.setOnTouchListener((v, event) -> {
            clickLayout = true;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

//                Log.e("CLICKCANCEL ", "" + ed.callOnClick());
                View focusedView = getCurrentFocus();
                if (focusedView != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                    focusedView.clearFocus();
                    clickLayout = false;
                }
            }
            return false;
        });
        title.setTextSize(48);
        title.setTypeface(null, Typeface.BOLD);

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Spannable spannable = new SpannableString(s);
                if (text.getSelectionEnd() != text.getSelectionStart()){
                    FSspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (FSspinner.getSelectedItem().toString()) {
                                case "8":
                                    sizeText = 8;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "9":
                                    sizeText = 9;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "10":
                                    sizeText = 10;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "11":
                                    sizeText = 11;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "12":
                                    sizeText = 12;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "14":
                                    sizeText = 14;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "16":
                                    sizeText = 16;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "18":
                                    sizeText = 18;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "20":
                                    sizeText = 20;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "22":
                                    sizeText = 22;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "24":
                                    sizeText = 24;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "26":
                                    sizeText = 26;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "28":
                                    sizeText = 28;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "36":
                                    sizeText = 36;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "48":
                                    sizeText = 48;
                                    spannable.setSpan(new AbsoluteSizeSpan(sizeText), text.getSelectionStart(),
                                            text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    FStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (FStyleSpinner.getSelectedItem().toString().equals("Обычный")) {
                                styleText = 0;
                            spannable.setSpan(new StyleSpan(styleText), text.getSelectionStart(),
                                    text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                            } else if (FStyleSpinner.getSelectedItem().toString().equals("Жирный")) {
                                styleText = 1;
                                spannable.setSpan(new StyleSpan(styleText), text.getSelectionStart(),
                                        text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                            } else if (FStyleSpinner.getSelectedItem().toString().equals("Курсив")) {
                                styleText = 2;
                                spannable.setSpan(new StyleSpan(styleText), text.getSelectionStart(),
                                        text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                            } else if (FStyleSpinner.getSelectedItem().toString().equals("Жирный курсив")) {
                                styleText = 3;
                                spannable.setSpan(new StyleSpan(styleText), text.getSelectionStart(),
                                        text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                text.setText(spannable);
                }
            }
        });
    }

    void openAndEdit() throws FileNotFoundException {
        file = (File) getIntent().getSerializableExtra("FILE");
        sharedPreferences = getPreferences(MODE_PRIVATE);
        text.setTextSize(sharedPreferences.getFloat("TextSize", pxToSp(text.getTextSize())));
        text.setTypeface(text.getTypeface(), sharedPreferences.getInt("TextStyle", styleText));

        Log.e("CREATETF", "" + title.getTypeface().getStyle());
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
        seditor.putFloat("TextSize", pxToSp(text.getTextSize()));
        seditor.putInt("TextStyle", styleText);
        seditor.apply();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//            Log.e("TitleSSave", "" + title.getTypeface().getSystemFontFamilyName());
//        }
//        else
//            Log.e("FFF0", "error");
////        Log.e("TextSSave",  "" + pxToSp(text.getTextSize()));

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


//TODO СТИЛИ ТЕКСТА, КУРСИВ ЖИРНЫЙ ПОДЧЁРКНУТЫЙ И ЗАЧЁРКНУТЫЙ
    //TODO ЧАТ БОТ ИИ
//TODO темы для блокнота
    void clear(){
        title.setText("");
        text.setText("");
    }

    // Метод для конвертации px в sp
    private float pxToSp(float px) {
        return px / getResources().getDisplayMetrics().scaledDensity;
    }


    @Override
    protected void onPause() {
        super.onPause();
        save();
    }
    //https://clck.ru/3LbU9c
    //смена шрифтов
}
