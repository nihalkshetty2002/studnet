package com.example.studnet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {


    private static final String username = "admin";
    private static final String pass = "pass";


    Button b1;
    EditText e1,e2;
    SQLiteDatabase sqb;
    DatabaseHelper db = new DatabaseHelper(this);


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b1=findViewById(R.id.button);
        e1=findViewById(R.id.editTextText);
        e2=findViewById(R.id.editTextText2);

        SharedPreferences sp = getSharedPreferences("userPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor  ed= sp.edit();

        if (sp.getBoolean("isLoggedIn", false)) {
            // If user is already logged in, redirect to LoggedDetails
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // close MainActivity
            return;
        }

        b1.setOnClickListener(v->{

            if(checkUser(e1.getText().toString(),e2.getText().toString())){
                ed.putString("name",e1.getText().toString());
                ed.putBoolean("isLoggedIn",true);
                ed.apply();
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
            }
           else{
                Toast.makeText(this,"cancle",Toast.LENGTH_LONG).show();
            }
//            if (e1.getText().toString().equals(usernam) && e2.getText().toString().equals(pass)) {
//                Intent intent=new Intent(this,MainActivity.class);
//                startActivity(intent);
//            }
        });
    }

    public boolean checkUser(String user, String pass) {
        SQLiteDatabase sqb = this.db.getWritableDatabase();
        String qry = "SELECT * FROM stud WHERE name = ? AND id = ?";
        Cursor cur = sqb.rawQuery(qry, new String[]{user, pass});
        boolean exists = cur.getCount() > 0;
        cur.close();
        return exists;
    }


}