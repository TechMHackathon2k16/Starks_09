package com.example.hemantsaini.myresult;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SemesterActivity extends AppCompatActivity implements View.OnClickListener {



    Cursor cursorSub;
    List<String> sem = new ArrayList<String>();
    List<String> sem1 = new ArrayList<String>();
    List<String> sem2 = new ArrayList<String>();
    List<String> sem3 = new ArrayList<String>();
    List<String> sem4 = new ArrayList<String>();
    List<String> sem5 = new ArrayList<String>();
    List<String> sem6 = new ArrayList<String>();
    List<String> sem7 = new ArrayList<String>();
    List<String> sem8 = new ArrayList<String>();





    private DatabaseAdapterSubject databaseAdapterSubject;
    private SQLiteDatabase sqLiteDatabase;
    ArrayAdapter<String> arrayAdapter;
    List<String> itemstemp = new ArrayList<String>();
    ListView listView;



    private Button buttonadd;
    private Button buttonSAve;

    private RelativeLayout relativeLayoutlist;
    private RelativeLayout relativeLayoutEdit;

    private EditText edittextAddSubject;
    private int semester;
    private String SubjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);

        Intent i=getIntent();
        semester=i.getIntExtra("Semester",1);
        Log.d("hemant", String.valueOf(semester));



        edittextAddSubject=(EditText)findViewById(R.id.id_EditTextAddSubject);
        buttonadd=(Button)findViewById(R.id.id_ButtonAdd1);
        buttonadd.setOnClickListener(this);
        buttonSAve=(Button)findViewById(R.id.id_ButtonSave);
        buttonSAve.setOnClickListener(this);

        relativeLayoutlist=(RelativeLayout)findViewById(R.id.id_RelativeListView);
        relativeLayoutEdit=(RelativeLayout)findViewById(R.id.id_relativeEdit);

        databaseAdapterSubject = new DatabaseAdapterSubject(this);

        sqLiteDatabase = databaseAdapterSubject.getWritableDatabase();

        listView = (ListView) findViewById(R.id.id_ListViewSemester);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sem);
        listView.setAdapter(arrayAdapter);

    }


    private void display() {
        arrayAdapter.clear();
        String[] columnssemester={"SEMESTER"};
        String[] columnsSubject={"SUBJECT"};
        cursorSub = sqLiteDatabase.query(databaseAdapterSubject.DATABASE_TABLE, columnsSubject, null, null, null, null, null);
        Cursor cursorSem = sqLiteDatabase.query(databaseAdapterSubject.DATABASE_TABLE, columnssemester, null, null, null, null, null);
        int semesterIndex = cursorSem.getColumnIndex("SEMESTER");
        int subjectIndex = cursorSub.getColumnIndex("SUBJECT");
        while (cursorSem.moveToNext()) {
            cursorSub.moveToNext();
            String i=cursorSem.getString(semesterIndex);
            if(i.equals(String.valueOf(semester))) {
                sem.add(cursorSub.getString(subjectIndex));
                itemstemp.clear();
                itemstemp.addAll(sem);
                arrayAdapter.notifyDataSetChanged();
            }

            Log.d("hemant", "Name:" + cursorSem.getString(semesterIndex));//+" " +"Roll no: "+cursor1.getString(rollIndex)+" "+"Semester :"+cursor2.getString(semesterIndex));

        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_ButtonAdd1) {
            relativeLayoutlist.setVisibility(View.GONE);
            relativeLayoutEdit.setVisibility(View.VISIBLE);
        }
        if(v.getId()==R.id.id_ButtonSave){
                SubjectName = String.valueOf(edittextAddSubject.getText());
                ContentValues values = new ContentValues();//***********************************______
                values.put("SUBJECT", SubjectName);//***********************************____Inserting Data
                values.put("SEMESTER", semester);//***********************************____Inserting Data
                long id = sqLiteDatabase.insert(databaseAdapterSubject.DATABASE_TABLE, null, values);//****__To check weather the data has been inserted or not
                Log.d("hemant", "ID is " + id);
                Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();
                edittextAddSubject.setText("");
            relativeLayoutEdit.setVisibility(View.GONE);
            relativeLayoutlist.setVisibility(View.VISIBLE);
            display();
        }
    }
}
