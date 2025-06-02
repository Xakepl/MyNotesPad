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
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.GenericArrayType;

public class editor extends AppCompatActivity implements Serializable {
    private SharedPreferences sharedPreferences;
    EditText title;
    String strTitle;
    String ftext;
    EditText text;
    ImageView save;
    ImageView back;
    ImageView clear;
    Typeface tf;
    String strText;
    File file;
//    MaterialToolbar mtlbr;
    Spinner FSspinner;
    LinearLayout ed;
    Spinner FStyleSpinner;
    boolean clickTitle;
    boolean clickText;
    boolean clickLayout;
    int styleTitle;
    int styleText;
    int sizeTitle;
    int sizeText;

    @Override
    protected void onStart() {
        super.onStart();
        tf = Typeface.createFromAsset(getAssets(),"samsungsans_regular.ttf");

        title.setTypeface(tf);
        text.setTypeface(tf);
    }
//TODO html разметка
    //TODO просмотреть какие тextView принимают такой формат
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
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
        clickTitle = false;
        clickText = false;
        clickLayout = false;
        if (tf != null)
            Log.e("CREATETF", "TRUE0 " + title.getTypeface().getSystemFontFamilyName());

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
//            ed.setOnClickListener(v -> {
//            //to do: проверить клики
//            Log.e("CLICKCANCEL ", "" + ed.callOnClick());
//            View focusedView = getCurrentFocus();
//            if (focusedView != null) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
//                focusedView.clearFocus();
//            }
//        });

        ed.setOnTouchListener((v, event) -> {
            clickLayout = true;
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.e("CLICKCANCEL ", "" + clickLayout);
                Log.e("CLICKTITLE ", "" + clickTitle);

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


        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int selS = text.getSelectionStart();
                int selE = text.getSelectionEnd();
                clickTitle = true;
//            clickText = false;
                Log.e("CLICKTITLE ", "" + clickTitle);
                Log.e("CLICKCANCEL ", "" + clickLayout);
//            Log.e("CLICKTITLE ", "" + title.callOnClick());

                FSspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (FSspinner.getSelectedItem().toString()) {
                            case "8":title.getText().setSpan(new AbsoluteSizeSpan(8), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "9":title.getText().setSpan(new AbsoluteSizeSpan(9), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "10":title.getText().setSpan(new AbsoluteSizeSpan(10), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "11":title.getText().setSpan(new AbsoluteSizeSpan(11), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "12":title.getText().setSpan(new AbsoluteSizeSpan(12), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "14":title.getText().setSpan(new AbsoluteSizeSpan(14), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "16":title.getText().setSpan(new AbsoluteSizeSpan(16), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "18":title.getText().setSpan(new AbsoluteSizeSpan(18), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "20":title.getText().setSpan(new AbsoluteSizeSpan(20), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "22":title.getText().setSpan(new AbsoluteSizeSpan(22), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "24":title.getText().setSpan(new AbsoluteSizeSpan(24), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "26":title.getText().setSpan(new AbsoluteSizeSpan(26), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "28":title.getText().setSpan(new AbsoluteSizeSpan(28), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "36":title.getText().setSpan(new AbsoluteSizeSpan(36), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                            case "48":title.getText().setSpan(new AbsoluteSizeSpan(48), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                        }}
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
                FStyleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (FStyleSpinner.getSelectedItem().toString().equals("Обычный")) {
                            styleTitle = 0;
                            title.setTypeface(Typeface.create(tf, styleTitle));
                            title.getText().setSpan(new StyleSpan(styleTitle), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (FStyleSpinner.getSelectedItem().toString().equals("Жирный")) {
                            styleTitle = 1;
                            title.getText().setSpan(new StyleSpan(styleTitle), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (FStyleSpinner.getSelectedItem().toString().equals("Курсив")) {
                            styleTitle = 2;
                            title.getText().setSpan(new StyleSpan(styleTitle), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        } else if (FStyleSpinner.getSelectedItem().toString().equals("Жирный курсив")) {
                            styleTitle = 3;
                            title.getText().setSpan(new StyleSpan(styleTitle), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
//
        });
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                //            clickText = true;
//            clickTitle = false;
                int selS = text.getSelectionStart();
                int selE = text.getSelectionEnd();
                if (selS != selE) {
                    FSspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            switch (FSspinner.getSelectedItem().toString()) {
                                case "8":text.getText().setSpan(new AbsoluteSizeSpan(8), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "9":text.getText().setSpan(new AbsoluteSizeSpan(9), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "10":text.getText().setSpan(new AbsoluteSizeSpan(10), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "11":text.getText().setSpan(new AbsoluteSizeSpan(11), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "12":text.getText().setSpan(new AbsoluteSizeSpan(12), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "14":text.getText().setSpan(new AbsoluteSizeSpan(14), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "16":text.getText().setSpan(new AbsoluteSizeSpan(16), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "18":text.getText().setSpan(new AbsoluteSizeSpan(18), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "20":text.getText().setSpan(new AbsoluteSizeSpan(20), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "22":text.getText().setSpan(new AbsoluteSizeSpan(22), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "24":text.getText().setSpan(new AbsoluteSizeSpan(24), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "26":text.getText().setSpan(new AbsoluteSizeSpan(26), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "28":text.getText().setSpan(new AbsoluteSizeSpan(28), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "36":text.getText().setSpan(new AbsoluteSizeSpan(36), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
                                case "48":text.getText().setSpan(new AbsoluteSizeSpan(48), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);break;
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
                                text.getText().setSpan(new StyleSpan(styleText), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            } else if (FStyleSpinner.getSelectedItem().toString().equals("Жирный")) {
                                styleText = 1;
                                text.getText().setSpan(new StyleSpan(styleText), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            } else if (FStyleSpinner.getSelectedItem().toString().equals("Курсив")) {
                                styleText = 2;
                                text.getText().setSpan(new StyleSpan(styleText), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            } else if (FStyleSpinner.getSelectedItem().toString().equals("Жирный курсив")) {
                                styleText = 3;
                                text.getText().setSpan(new StyleSpan(styleText), selS, selE, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }
        });

    }

    void openAndEdit() throws FileNotFoundException {
        file = (File) getIntent().getSerializableExtra("FILE");
        sharedPreferences = getPreferences(MODE_PRIVATE);
//        title.setTextSize(sharedPreferences.getFloat("TitleSize", pxToSp(title.getTextSize())));
//        text.setTextSize(sharedPreferences.getFloat("TextSize", pxToSp(text.getTextSize())));
//        title.setTypeface(title.getTypeface(), sharedPreferences.getInt("TitleStyle", styleTitle));
//        text.setTypeface(text.getTypeface(), sharedPreferences.getInt("TextStyle", styleText));

//        Log.e("CREATETF", "" + title.getTypeface().getStyle());
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
//        seditor.putFloat("TitleSize", pxToSp(title.getTextSize()));
//        seditor.putFloat("TextSize", pxToSp(text.getTextSize()));
//        seditor.putInt("TitleStyle", styleTitle);
//        seditor.putInt("TextStyle", styleText);
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
