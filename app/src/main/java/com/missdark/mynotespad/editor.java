package com.missdark.mynotespad;

import static android.widget.TextView.BufferType.SPANNABLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
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
    Spinner FStyleSpinner;
    int sizeText;
    int styleText;
    String htmlText;
    Object[] allSpans;

    @Override
    protected void onStart() {
        super.onStart();
    }
    //TODO Поработать с сохранением спанов
    //TODO Добавить возможность изменение цвета текста

    @SuppressLint("ClickableViewAccessibility")
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

        findViewById(R.id.editorL).setOnClickListener( v -> {
                View focusedView = getCurrentFocus();
                if (focusedView != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
                    focusedView.clearFocus();
                }
        });

        title.setTextSize(48);
        title.setTypeface(null, Typeface.BOLD);


        FSspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (FSspinner.getSelectedItem().toString()) {
                                case "8": sizeText = 8; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "9": sizeText = 9; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "10": sizeText = 10; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "11": sizeText = 11; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "12": sizeText = 12; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "14": sizeText = 14; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "16": sizeText = 16; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "18": sizeText = 18; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "20": sizeText = 20; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "22": sizeText = 22; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "24": sizeText = 24; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "26": sizeText = 26; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "28": sizeText = 28; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "36": sizeText = 36; applySize(new AbsoluteSizeSpan(sizeText)); break;
                                case "48": sizeText = 48; applySize(new AbsoluteSizeSpan(sizeText)); break;
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                    FStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (FStyleSpinner.getSelectedItem().toString().equals("Обычный")) {
                                styleText = 0; applyStyle(new StyleSpan(styleText));}
                            else if (FStyleSpinner.getSelectedItem().toString().equals("Жирный")) {
                                styleText = 1; applyStyle(new StyleSpan(styleText));}
                            else if (FStyleSpinner.getSelectedItem().toString().equals("Курсив")) {
                                styleText = 2; applyStyle(new StyleSpan(styleText));}
                            else if (FStyleSpinner.getSelectedItem().toString().equals("Жирный курсив")) {
                                styleText = 3; applyStyle(new StyleSpan(styleText)); }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
    }

    void openAndEdit() throws FileNotFoundException {
        file = (File) getIntent().getSerializableExtra("FILE");
        sharedPreferences = getPreferences(MODE_PRIVATE);
        text.setTextSize(sharedPreferences.getFloat("TextSize", pxToSp(text.getTextSize())));
        text.setTypeface(text.getTypeface(), sharedPreferences.getInt("TextStyle", styleText));
        String savedHtml = sharedPreferences.getString("FormatedText", htmlText);
        Spanned spannedText = Html.fromHtml(savedHtml, Html.FROM_HTML_MODE_LEGACY);
        SpannableStringBuilder restoredSpannable = new SpannableStringBuilder(spannedText);

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
        } text.setText(restoredSpannable);
    }

    void save(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor seditor = sharedPreferences.edit();
        seditor.putFloat("TextSize", pxToSp(text.getTextSize()));
        seditor.putInt("TextStyle", styleText);
        seditor.putString("FormatedText", htmlText);
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
            Toast.makeText(this, "Файл " + file.getName() + " сохранён: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();}
        catch (IOException e) {
            Toast.makeText(this, "Ошибка сохранения: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


//TODO ЧАТ БОТ ИИ?
//TODO темы для блокнота
    void clear(){
        //Удаление спанов

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

    void applyStyle(StyleSpan span){
        text.getSelectionEnd();
        if (text.getSelectionStart() == text.getSelectionEnd())
            return;

        SpannableStringBuilder spannable = new SpannableStringBuilder(text.getText());
        spannable.setSpan( span,text.getSelectionStart(),
                text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        text.setText(spannable);
        text.setSelection(text.getSelectionStart(), text.getSelectionEnd()); // Восстанавливаем выделение
        htmlText = Html.toHtml(spannable);
    }

    void applySize(AbsoluteSizeSpan span){
        text.getSelectionEnd();
        if (text.getSelectionStart() == text.getSelectionEnd())
            return;

        SpannableStringBuilder spannable = new SpannableStringBuilder(text.getText());
        spannable.setSpan( span, text.getSelectionStart(),
                text.getSelectionEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        text.setText(spannable);
        text.setSelection(text.getSelectionStart(), text.getSelectionEnd()); // Восстанавливаем выделение
    }

    //https://clck.ru/3LbU9c
    //смена шрифтов
}
