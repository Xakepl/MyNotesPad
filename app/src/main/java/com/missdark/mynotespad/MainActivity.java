package com.missdark.mynotespad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements Serializable {
    FloatingActionButton create;
    ProjectsDB mDBConnector;
    myListAdapter myAdapter;
    File file;
    ListView mlistView;
    enum Status {OPENFILEANDEDIT, CREATNEW}

    Status def = Status.CREATNEW; //по дефолту

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        Log.d("Date", "DATE : " + strDate);
//        mContext = this;
        mDBConnector = new ProjectsDB(this);
        mlistView = findViewById(R.id.list);
        myAdapter = new myListAdapter(this, mDBConnector.selectAll());
// !!!!!!!!!!!!!!!!!=========== ВНИМАНИЕ, ИСПОЛЬЗОВАТЬ В СЛУЧАЕ ОЧИСТКИ =====================!!!!!!!!!!!!!!!!!
//        mDBConnector.deleteAll();
// !=========================================================================================================!
        mlistView.setAdapter(myAdapter);
        mlistView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(MainActivity.this, "Выбран: " + mDBConnector.select(id).getName(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, editor.class);
            file = new File(getFilesDir(), mDBConnector.select(id).getName() + ".txt");
            intent.putExtra("STATE", Status.OPENFILEANDEDIT);
            intent.putExtra("FILE", file);
            startActivity(intent);
        });
        mlistView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Подтвердите удаление: ")
                        .setPositiveButton("OK", (dialog, which) ->mDBConnector.delete(mlistView.getSelectedItemId()))
                        //TODO сделать удаление файла!!!!!!!
                        .setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });

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
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Пожалуйста, введите название заметки!!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    void deleteFile(){
        file.delete();
    }
// !!!========================!!! ЮЗАТЬ ЗАПРОС, ЕСЛИ НЕТУ ИДЕЙ ИЛИ НИЧЕГО НЕ ВЫХОДИТ !!!=================================!!!!
    /*Короче братан, задача, у меня в коде есть класс BaseAdapter,
    у меня есть метод onContextItemSelected() для него нужно реализовать открытие текстового файла по нажатию на айтем*/
// !!!===================================================================================================================!!!!
    //    private void updateList () {

    /// /        myAdapter.setArrayMyData(mDBConnector.selectAll());
    /// /        myAdapter.notifyDataSetChanged();
//    }

    // TODO Сделать разметки под разные разметки экранов!!!!!!!!!!!!!


    static class myListAdapter extends BaseAdapter {
        private final LayoutInflater mLayoutInflater;
        private ArrayList<Projects> arrayMyMatches;

        public myListAdapter(Context ctx, ArrayList<Projects> arr) {
            mLayoutInflater = LayoutInflater.from(ctx);
            setArrayMyData(arr);
        }

        //    public ArrayList<Projects> getArrayMyData() {
//        return arrayMyMatches;
//    }
        public void setArrayMyData(ArrayList<Projects> arrayMyData) {
            this.arrayMyMatches = arrayMyData;
        }

        public int getCount() {
            return arrayMyMatches.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            Projects md = arrayMyMatches.get(position);
            if (md != null)
                return md.getId();
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.item_file, null);
            TextView name = convertView.findViewById(R.id.name);
            TextView path = convertView.findViewById(R.id.path);
            TextView data = convertView.findViewById(R.id.data);
            Projects md = arrayMyMatches.get(position);
            name.setText(md.getName());
            path.setText(md.getPath());
            data.setText(md.getDate());
            return convertView;
        }

    }
}


/*
!==============

 */



