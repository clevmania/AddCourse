package com.example.lawrence.addcourse;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    EditText title, code, unit, lname, desc, ca, xam;
    Spinner se,yr;
    int semesta, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing variables
        //course details
        title = (EditText) findViewById(R.id.eTCosTitle);
        code = (EditText) findViewById(R.id.eTCosCode);
        unit = (EditText) findViewById(R.id.eTCosUnit);
        lname = (EditText) findViewById(R.id.eTLectName);
        desc = (EditText) findViewById(R.id.eTCosDesc);
        // grades for ca and exam
        ca = (EditText) findViewById(R.id.eTCA);
        xam = (EditText) findViewById(R.id.eTExam);

        // Spinner tins
        se = (Spinner) findViewById(R.id.spinner);
        yr = (Spinner) findViewById(R.id.spinner2);

        se.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    //displayError("select a semester");
                }else{
                    semesta = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                displayError("Please select a semester");
            }
        });

        yr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    //displayError("Please select a year");
                }else{
                    year = position;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                displayError("Please select a Year");
            }
        });

    }

    public boolean validateForm(){
        if(title.getText().toString().trim().isEmpty()){
            title.setHint("Title Required");
            title.setHintTextColor(Color.RED);
            return false;
        }
        if(code.getText().toString().trim().isEmpty()){
            code.setHint("code Required");
            code.setHintTextColor(Color.RED);
            return false;
        }
        if(unit.getText().toString().trim().isEmpty()){
            unit.setHint("Unit Required");
            unit.setHintTextColor(Color.RED);
            return false;
        }
        if(ca.getText().toString().trim().isEmpty()){
            ca.setHint("CA Required");
            ca.setHintTextColor(Color.RED);
            return false;
        }
        if(xam.getText().toString().trim().isEmpty()){
            xam.setHint("exam score Required");
            xam.setHintTextColor(Color.RED);
            return false;
        }
        if(semesta == 0){
            displayError("You have not chosen a semester");
            return false;
        }
        if(year == 0){
            displayError("You have not chosen a Year");
            return false;
        }

        // basic form validation ends here...
       return true;
    }


    public void addCourse(View view) {
        validateForm();
        if(validateForm()== true ){
            //Toast.makeText(this,"confirmed",Toast.LENGTH_LONG).show();
            String costit = title.getText().toString();
            String cosCode = code.getText().toString();
            int cosUnit = Integer.parseInt(unit.getText().toString());
            String cosLect = lname.getText().toString();
            int cosCA = Integer.parseInt(ca.getText().toString());
            String cosdes = desc.getText().toString();
            int cosExam = Integer.parseInt(xam.getText().toString());
            int cosSemester = semesta;
            int cosYear = year;
            DBHelper dbHelper= new DBHelper(this);
            long theResult = dbHelper.addCourse(costit,cosCode,cosUnit,cosLect,
                    cosdes,cosCA,cosExam,cosSemester,cosYear);
            if (theResult > 0){
                Toast dtos = Toast.makeText(this, "Your course is saved", Toast.LENGTH_LONG);
                dtos.setGravity(Gravity.TOP, 0, 0);
                dtos.show();
                clear();
                finish();
            }
            else
                Toast.makeText(this,"Could not add course",Toast.LENGTH_LONG).show();
        }
    }

    public void clear(){
        title.setText("");
        code.setText("");
        unit.setText("");
        lname.setText("");
        desc.setText("");
        ca.setText("");
        xam.setText("");
    }

    public void displayError(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}


