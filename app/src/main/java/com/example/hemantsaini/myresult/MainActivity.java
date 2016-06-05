package com.example.hemantsaini.myresult;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText editTextname;
    EditText editTextRollnumber;
    EditText editTextSemester;
    String Name;
    private RelativeLayout relativeLayoutdetail;
    private RelativeLayout relativeLayoutlist;
    String Rollnumber;
    String Semester;
    Button saveButton;
    private ArrayList<String> ListOfSelected = new ArrayList<>();
    DatabaseAdapter databaseAdapter;
    SQLiteDatabase sqLiteDatabase;
    ArrayAdapter<String> arrayAdapter;
    List<String> items = new ArrayList<String>();
    List<String> itemstemp = new ArrayList<String>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseAdapter = new DatabaseAdapter(this);
        sqLiteDatabase = databaseAdapter.getWritableDatabase();
        editTextname = (EditText) findViewById(R.id.editTextname);
        editTextRollnumber = (EditText) findViewById(R.id.editTextRollNumber);
        editTextSemester = (EditText) findViewById(R.id.editTextSemester);
        saveButton = (Button) findViewById(R.id.buttonsave);
        saveButton.setOnClickListener(this);
        relativeLayoutdetail = (RelativeLayout) findViewById(R.id.id_LAyoutdetail);
        relativeLayoutlist = (RelativeLayout) findViewById(R.id.id_LAyoutlist);

        listView = (ListView) findViewById(R.id.id_ListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) ((TextView) view).getText();
            }
        });
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(arrayAdapter);
        display();
    }


    private void display() {
        arrayAdapter.clear();
        String[] columns = {"NAME"};
        String[] columnssemester={"SEMESTER"};
        String[] columnsroll={"ROLLNO"};
        Cursor cursor = sqLiteDatabase.query(databaseAdapter.DATABASE_TABLE, columns, null, null, null, null, null);
        Cursor cursor2 = sqLiteDatabase.query(databaseAdapter.DATABASE_TABLE, columnssemester, null, null, null, null, null);
        Cursor cursor1 = sqLiteDatabase.query(databaseAdapter.DATABASE_TABLE, columnsroll, null, null, null, null, null);
        int nameIndex = cursor.getColumnIndex("NAME");
        int semesterIndex = cursor2.getColumnIndex("SEMESTER");
        int rollIndex = cursor1.getColumnIndex("ROLLNO");
        //       int idIndex = cursor.getColumnIndex("_id");
        int e = 1;
        while (cursor.moveToNext()) {
            // items.add(e++ + ".) " + cursor.getString(nameIndex));
            items.add(cursor.getString(nameIndex));
            itemstemp.clear();
            itemstemp.addAll(items);
            arrayAdapter.notifyDataSetChanged();
            Log.d("hemant", "Name:" + cursor.getString(nameIndex));//+" " +"Roll no: "+cursor1.getString(rollIndex)+" "+"Semester :"+cursor2.getString(semesterIndex));

        }
        while (cursor1.moveToNext()) {
            Log.d("hemant","Roll no: "+cursor1.getInt(rollIndex));

        }
        while (cursor2.moveToNext()) {
            Log.d("hemant", "Semester :"+cursor2.getString(semesterIndex));

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase(Locale.getDefault());
                items.clear();
                if (newText.length() == 0) {
                    items.addAll(itemstemp);
                } else {
                    for (String wp : itemstemp) {
                        String str = wp + "C";
                        if (!str.equals("nullC"))
                        //           if(wp!=null)
                        {
                            if (wp.toLowerCase(Locale.getDefault())
                                    .contains(newText)) {
                                items.add(wp);
                            }
                        }
                    }
                }
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_addStudent) {
            Log.d("codekamp", "AddStudent Button Pressed");
//            Intent i=new Intent(MainActivity.this,StudentDetailActivity.class);
//            startActivity(i);


            relativeLayoutlist.setVisibility(View.GONE);
            relativeLayoutdetail.setVisibility(View.VISIBLE);


            return true;
        }

        if (id == R.id.id_semester) {

            ListOfSelected.clear();
            ListOfSelected.add("Semester 1");
            ListOfSelected.add("Semester 2");
            ListOfSelected.add("Semester 3");
            ListOfSelected.add("Semester 4");
            ListOfSelected.add("Semester 5");
            ListOfSelected.add("Semester 6");
            ListOfSelected.add("Semester 7");
            ListOfSelected.add("Semester 8");

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.listitem);
            dialog.setTitle("Selected Items:");
            listView = (ListView) dialog.findViewById(R.id.list);
            dialog.show();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, ListOfSelected);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    int itemPosition = position+1;


                    String itemValue = (String) listView
                            .getItemAtPosition(position);
//                     Show Alert
                    Toast.makeText(
                            getApplicationContext(),
                            "Position :" + itemPosition + "  ListItem : "
                                    + itemValue, Toast.LENGTH_LONG).show();
                    dialog.cancel();

                    Intent i=new Intent(MainActivity.this,SemesterActivity.class);
                    i.putExtra("Semester", itemPosition);
                    startActivity(i);
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        Name = String.valueOf(editTextname.getText());
        Rollnumber = String.valueOf(editTextRollnumber.getText());
        Semester = String.valueOf(editTextSemester.getText());



        ContentValues values = new ContentValues();//***********************************______
        values.put("NAME", Name);//***********************************____Inserting Data
        values.put("ROLLNO", Rollnumber);//***********************************____Inserting Data
        values.put("SEMESTER", Semester);//***********************************____Inserting Data
        long id = sqLiteDatabase.insert(databaseAdapter.DATABASE_TABLE, null, values);//****__To check weather the data has been inserted or not
        Log.d("hemant", "ID is " + id);
        Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT).show();

        editTextname.setText("");
        editTextRollnumber.setText("");
        editTextSemester.setText("");
        relativeLayoutdetail.setVisibility(View.GONE);
        relativeLayoutlist.setVisibility(View.VISIBLE);
        display();

    }
}
