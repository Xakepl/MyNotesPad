package com.missdark.mynotespad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Serializable {
    FloatingActionButton create;
    ProjectsDB mDBConnector;
    myListAdapter myAdapter;
    File file;
    ListView mlistView;
    enum Status {OPENFILEANDEDIT, CREATNEW}
    SharedPreferences t;
//    String[] ThemesName = {"Red", "Orange", "Yellow", "Green", "LtBlue", "Blue", "Purple", "Pink", "Gray", "Choco Loco"};
//    enum themes {RED, ORNAGE, YELLOW, GREEN, LTBLUE, BLUE, PURPLR, PINK, GRAY, CHOCOLOCO};

//    HashMap<String, Integer> themes = new HashMap<String, Integer>(){{
//        put("Red", Color.parseColor("#db6456"));
//        put("Orange", Color.parseColor("#ff964f"));
//        put("Yellow", Color.parseColor("#fffd74"));
//        put("Green", Color.parseColor("#b0ff9d"));
//        put("LtBlue", Color.parseColor("#a2bffe"));
//        put("Blue", Color.parseColor("#19789c"));
//        put("Purple", Color.parseColor("#b19cd9"));
//        put("Pink", Color.parseColor("#dea5a4"));
//        put("Gray", Color.parseColor("#a1a18c"));
//        put("Choco Loco", Color.parseColor("#7d5f53"));
//    }};
    Status def = Status.CREATNEW; //по дефолт
//    int thm;
    ImageButton theme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c = Calendar.getInstance();
        t = getPreferences(MODE_PRIVATE);
//        SharedPreferences.Editor st = t.edit();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        Log.d("Date", "DATE : " + strDate);
        mDBConnector = new ProjectsDB(this);
        mlistView = findViewById(R.id.list);
//        thm = mlistView.getBackground();
        myAdapter = new myListAdapter(this, mDBConnector.selectAll());
// !!!!!!!!!!!!!!!!!=========== ВНИМАНИЕ, ИСПОЛЬЗОВАТЬ В СЛУЧАЕ ОЧИСТКИ =====================!!!!!!!!!!!!!!!!!
//        mDBConnector.deleteAll();
// !=========================================================================================================!
        mlistView.setAdapter(myAdapter);
        // ===========================================| Лист |==============================================================================!!!!
        // ----------------------------------------------------| Удаление |-----------------------------------------------------
        mlistView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Подтвердите удаление содержимого файла: ") //удаления файла должно быть, но его нету
                    .setPositiveButton("OK", (dialog, which) -> {
                        file = new File(getFilesDir(), mDBConnector.select(id).getName() + ".txt");
                        deleteFile(file, id);
                    })
                    //TODO сделать удаление файла!!!!!!!
                    .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        });
        // ----------------------------------------------------| Выбор |-----------------------------------------------------
        //не угадал, я литий
        mlistView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(MainActivity.this, "Выбран: " + mDBConnector.select(id).getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, editor.class);
            file = new File(getFilesDir(), mDBConnector.select(id).getName() + ".txt");
            intent.putExtra("STATE", Status.OPENFILEANDEDIT);
            intent.putExtra("FILE", file);
//            intent.putExtra("Theme", thm);
            startActivity(intent);
        });

        // ===========================================| Создание заметки |==============================================================================!!!!

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
                    .setPositiveButton("OK", (dialog, which) -> {
                        if (mDBConnector.isNameExists(inputEditText.getText().toString())) {
                            // Такое название уже есть в БД
                            Toast.makeText(this, "Файл с таким названием уже существует!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Названия нет, можно сохранять
                            String inputText = inputEditText.getText().toString();
                            if (!inputText.isEmpty()) {
                                Intent intent = new Intent(MainActivity.this, editor.class);
                                file = new File(getFilesDir(), inputText + ".txt");
                                mDBConnector.insert(inputText, strDate, file.getPath());
                                intent.putExtra("FILE", file);
                                intent.putExtra("STATE", def);
//                                intent.putExtra("", thm);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Пожалуйста, введите название заметки!!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        // ===========================================| Темы |==============================================================================!!!!
        theme = findViewById(R.id.themeBTN);
        theme.setOnClickListener(v -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Выберите тему: ")
//                    .setItems(ThemesName, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            thm = themes.get(ThemesName[which]);
//                            Log.e("x", "" + thm);
//                            mlistView.setBackgroundColor(thm);
//                            st.putInt("Themes", thm);
//                        }
//                    }).setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
//            AlertDialog dialog = builder.create();
//            dialog.show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Выберите тему: ")
                    .setMessage("Coming soon").setPositiveButton("OK", (dialog, which) -> dialog.dismiss()).setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }

    void deleteFile(@NonNull File file, long id){
        Log.e("FILE", file.toString());
        Log.e("ID", "" + id);
        try {
            if (file.delete()) {
                mDBConnector.delete(mlistView.getSelectedItemId());
                myAdapter.remove(id);
               // myAdapter.getArrayMyData().remove((int)id);
            }
            else{Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();}
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
    }
    // TODO Сделать разметки под разные разметки экранов!!!!!!!!!!!!!

    static class myListAdapter extends BaseAdapter {
        private final LayoutInflater mLayoutInflater;
        private ArrayList<Projects> arrayMyProjects;

        public myListAdapter(Context ctx, ArrayList<Projects> arr) {
            mLayoutInflater = LayoutInflater.from(ctx);
            setArrayMyData(arr);
        }
        public void setArrayMyData(ArrayList<Projects> arrayMyData) {
            this.arrayMyProjects = arrayMyData;
        }

        public int getCount() {
            return arrayMyProjects.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            Projects md = arrayMyProjects.get(position);
            if (md != null)
                return md.getId();
            return 0;
        }

        void remove(long id){
            arrayMyProjects.remove(id);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.item_file, null);
            TextView name = convertView.findViewById(R.id.name);
            TextView path = convertView.findViewById(R.id.path);
            TextView data = convertView.findViewById(R.id.data);
            Projects md = arrayMyProjects.get(position);
            name.setText(md.getName());
            path.setText(md.getPath());
            data.setText(md.getDate());
            return convertView;
        }

    }
}


