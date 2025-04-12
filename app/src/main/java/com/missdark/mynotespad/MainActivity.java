package com.missdark.mynotespad;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements Serializable {
    FloatingActionButton create;
    int[] viewsAd;
    ProjectsDB mDBConnector;
        myListAdapter myAdapter;
//    CursorAdapter adapter;
    Projects md;
    ListView mListView;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        Log.d("Date", "DATE : " + strDate);
//        mContext = this;
        mDBConnector = new ProjectsDB(this);
        mListView = findViewById(R.id.list);
//        myAdapter = new myListAdapter(mDBConnector.selectAll());
        viewsAd = new int[]{R.id.name, R.id.data};
        myAdapter = new myListAdapter(this, mDBConnector.selectAll());
// !!!!!!!!!!!!!!!!!=========== ВНИМАНИЕ, ИСПОЛЬЗОВАТЬ В СЛУЧАЕ ОЧИСТКИ =====================!!!!!!!!!!!!!!!!!
//        mDBConnector.deleteAll();
// !=========================================================================================================!
//       Log.d("БАЗА", mDBConnector.selectAll());
//        mListView.setAdapter(myAdapter);
        mListView.setAdapter(myAdapter);
//        registerForContextMenu(mListView);
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
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String inputText = inputEditText.getText().toString();
                            if (!inputText.isEmpty()) {
                                Intent intent = new Intent(MainActivity.this, editor.class);
                                File file = new File(getFilesDir(), (String) inputText + ".txt");
                                mDBConnector.insert(inputText, strDate, (String) file.getPath());
//                                mDBConnector.update(md);
                                intent.putExtra("FILE", file);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Пожалуйста, введите название заметки!!!!", Toast.LENGTH_SHORT);
                            }
                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }
//    private void updateList () {
////        myAdapter.setArrayMyData(mDBConnector.selectAll());
////        myAdapter.notifyDataSetChanged();
//    }

}

class myListAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private ArrayList<Projects> arrayMyMatches;

    public myListAdapter (Context ctx, ArrayList<Projects> arr) {
        mLayoutInflater = LayoutInflater.from(ctx);
        setArrayMyData(arr);
    }

    public ArrayList<Projects> getArrayMyData() {
        return arrayMyMatches;
    }

    public void setArrayMyData(ArrayList<Projects> arrayMyData) {
        this.arrayMyMatches = arrayMyData;
    }

    public int getCount () {
        return arrayMyMatches.size();
    }

    public Object getItem (int position) {

        return position;
    }

    public long getItemId (int position) {
        Projects md = arrayMyMatches.get(position);
        if (md != null) {
            return md.getId();
        }
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = mLayoutInflater.inflate(R.layout.item_file, null);

        TextView name= (TextView)convertView.findViewById(R.id.name);
        TextView path = (TextView)convertView.findViewById(R.id.path);
        TextView data=(TextView)convertView.findViewById(R.id.data);


        Projects md = arrayMyMatches.get(position);
        name.setText(md.getName());
        path.setText(md.getPath());
        data.setText(md.getDate());

        return convertView;
    }
} // end myAdapter



