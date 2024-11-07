package com.example.studnet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText etName, etMarks, etAttend, etID;
    Button btnAdd, btnUpdate, btnDelete,btnlogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        etName = findViewById(R.id.etName);
        etMarks = findViewById(R.id.etMarks);
        etAttend = findViewById(R.id.etAtted);
        etID = findViewById(R.id.etID);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnlogout=findViewById(R.id.btnlogout);

        loadStudents(); // Load students to display in CardViews

        AlertDialog.Builder builder= new AlertDialog.Builder(this);


        btnAdd.setOnClickListener(v -> addStudent());
        btnUpdate.setOnClickListener(v -> updateStudent());
        btnlogout.setOnClickListener(v->
        {
            finish();
        });
        btnDelete.setOnClickListener(v -> {

            builder.setMessage("do u wnat to del?")
                    .setCancelable(false)
                    .setPositiveButton("yes" ,new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteStudent();

                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this,"cancle",Toast.LENGTH_LONG).show();
                        }
                    });
            AlertDialog alertDialog =  builder.create();
            alertDialog.setTitle("delete");
            alertDialog.show();

        });


    }

    private void addStudent() {
        String name = etName.getText().toString();
        int marks = Integer.parseInt(etMarks.getText().toString());
        int attendance = Integer.parseInt(etAttend.getText().toString());

        boolean result = db.addstudent(name, marks, attendance);
        if (result) {
            Toast.makeText(this, "Student added", Toast.LENGTH_LONG).show();
            loadStudents();
        } else {
            Toast.makeText(this, "Failed to add student", Toast.LENGTH_LONG).show();
        }
    }

    private void updateStudent() {
        // You can implement update functionality similar to add, using etID and DatabaseHelper
        String name = etName.getText().toString();
        int marks = Integer.parseInt(etMarks.getText().toString());
        int attendance = Integer.parseInt(etAttend.getText().toString());
        String idText = etID.getText().toString();
        boolean result = db.updatestudent(name, marks, attendance, Integer.parseInt(idText));
        if (result) {
            Toast.makeText(this, "Student updateddded", Toast.LENGTH_LONG).show();
            loadStudents();
        } else {
            Toast.makeText(this, "Failed updateb student", Toast.LENGTH_LONG).show();
        }


    }

    private void deleteStudent() {
        String idText = etID.getText().toString();
        if (idText.isEmpty()) {
            Toast.makeText(this, "Please enter a student ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idText);
        boolean result = db.delstud(id);

        if (result) {
            Toast.makeText(this, "Student deleted", Toast.LENGTH_SHORT).show();
            loadStudents(); // Reload the list to reflect the deletion
            etID.setText(""); // Clear the ID input field
        } else {
            Toast.makeText(this, "Failed to delete student", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadStudents() {
        LinearLayout cardContainer = findViewById(R.id.cardContainer);
        cardContainer.removeAllViews(); // Clear existing views

        Cursor cursor = db.getall();
        while (cursor.moveToNext()) {
            View cardView = getLayoutInflater().inflate(R.layout.stud_item, cardContainer, false);

            TextView tvStudentID = cardView.findViewById(R.id.tvStudentID);
            TextView tvStudentName = cardView.findViewById(R.id.tvStudentName);
            TextView tvMarks = cardView.findViewById(R.id.tvMarks);
            TextView tvAttendance = cardView.findViewById(R.id.tvAttendance);

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int marks = cursor.getInt(2);
            int attendance = cursor.getInt(3);

            tvStudentID.setText("ID: " + id);
            tvStudentName.setText("Name: " + name);
            tvMarks.setText("Marks: " + marks);
            tvAttendance.setText("Attendance: " + attendance);

            cardContainer.addView(cardView);
        }
        cursor.close();
    }
}
