package com.example.android.reportcard;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks, editId;
    Button btnAddData;
    Button btnviewAll;
    String a, b,c;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_Surname);
        editMarks = (EditText) findViewById(R.id.editText_Marks);
        editId = (EditText) findViewById(R.id.editText_id);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.viewAll);
        btnDelete=(Button)findViewById(R.id.button_delete);
    }

    public void DeleteData(View v){
                        Integer deletedRows =myDb.deleteData(editId.getText().toString());
                        if (deletedRows >0)
                        {
                            showMessage("success","data is deleted from database");
                        }

                    }

    public void AddData(View view) {
       if(readEditText()){
           boolean i = myDb.insertData(a,b,c);
           if (i){
               showMessage("success","data is added for student");
           }
           editName.setText("");
           editSurname.setText("");
           editMarks.setText("");

       }
        else return;

    }

    public void viewAll(View view) {

        Cursor res = myDb.getAllData();
        if (res.getColumnCount() == 0) {
        //show message
        showMessage("Error", "Nothing found");
         return;
         }


                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Surname :" + res.getString(2) + "\n");
                            buffer.append("Marks :" + res.getString(3) + "\n\n");
                        }
                        //show all data
                        showMessage("Data", buffer.toString());

    }

    public void showMessage(String tittle, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle(tittle);
        builder.setMessage(Message);
        builder.show();
    }

    //to read from edittext
    public boolean readEditText(){

        a = editName.getText().toString();
        b = editSurname.getText().toString();
        c = editMarks.getText().toString();
        return a != null || b != null || c != null;
    }

    //to updater user information
    public  void updateData(View view){
        EditText editTextID = (EditText) findViewById(R.id.editText_id);
        String id = editTextID.getText().toString();
        readEditText();
        System.out.println("it reads edittext: a=" + a);
        System.out.println("it reads edittext: b=" + b);
        System.out.println("it reads edittext: c=" + c);

        DatabaseHelper myDb = new DatabaseHelper(this);
        boolean updateThings  = myDb.updateData(a,b,c,id);
        System.out.println("is it updating?: check boolean variable: " + updateThings);

        if(updateThings){
            showMessage("Success","data is updated");
        }
        else {showMessage("error","not updated");}

    }
}