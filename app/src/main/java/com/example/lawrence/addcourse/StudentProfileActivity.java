package com.example.lawrence.addcourse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Lawrence on 6/28/2017.
 */
public class StudentProfileActivity extends ActionBarActivity {
    EditText fname,lname,email,faculty,dept,reg,mobile,user,pass, cpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);

        //Initialising variables
        // Students Profile stub
        fname = (EditText) findViewById(R.id.edFname);
        lname = (EditText) findViewById(R.id.etLname);
        email = (EditText) findViewById(R.id.etEmail);
        faculty = (EditText) findViewById(R.id.etFac);
        dept = (EditText) findViewById(R.id.etDept);
        reg = (EditText) findViewById(R.id.etRegNo);
        mobile= (EditText) findViewById(R.id.etMobile);
        user = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        cpass = (EditText) findViewById(R.id.confirm);
    }

    public boolean validator() {
        if (fname.getText().toString().trim().isEmpty()) {
            fname.setHint("Required");
            return false;
        }
        if (lname.getText().toString().trim().isEmpty()) {
            lname.setHint("Required");
            return false;
        }
        String mailcheck = email.getText().toString().trim();
        if (mailcheck.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mailcheck).matches()) {
            email.setHint("Required");
            return false;
        }
        if (faculty.getText().toString().trim().isEmpty()) {
            faculty.setHint("Required");
            return false;
        }
        if (dept.getText().toString().trim().isEmpty()) {
            dept.setHint("Required");
            return false;
        }
        if (reg.getText().toString().trim().isEmpty()) {
            reg.setHint("Required");
            return false;
        }
        if (mobile.getText().toString().trim().isEmpty()) {
            mobile.setHint("Required");
            return false;
        }
        if (user.getText().toString().trim().isEmpty()) {
            user.setHint("Required");
            return false;
        }
        if (pass.getText().toString().trim().isEmpty()) {
            pass.setHint("Required");
            return false;
        }
        return true;
    }

    public void profileHandler(View view) {
        String p = pass.getText().toString().trim();
        String q = cpass.getText().toString().trim();

        validator();
        if (validator() == true ){
            if(p.contentEquals(q)){
                String fn = fname.getText().toString().trim();
                String ln = lname.getText().toString().trim();
                String em = email.getText().toString().trim();
                String fa = faculty.getText().toString().trim();
                String dep = dept.getText().toString().trim();
                String re = reg.getText().toString().trim();
                String mob = mobile.getText().toString().trim();
                String us = user.getText().toString().trim();
                String pa = pass.getText().toString().trim();
                DBHelper dbHelper= new DBHelper(this);
                long theResult = dbHelper.createProfile(fn,ln,em,fa,dep,re,mob,us,pa);
                if (theResult > 0){
                    Toast.makeText(this,"Profile Created Successfully",Toast.LENGTH_LONG).show();
                    Intent welcomeIntent = new Intent(this,WelcomeActivity.class);
                    welcomeIntent.putExtra("Username",us);
                    startActivity(welcomeIntent);
                }
                else
                    Toast.makeText(this,"Setting Your Profile Failed!",Toast.LENGTH_LONG).show();
            }else
                Toast.makeText(this,"Password's don't match", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this,"Please, All Fields are required", Toast.LENGTH_LONG).show();
    }


}

//    public void start(){
//        // Start next Intent
//        Intent main = new Intent(this,WelcomeActivity.class);
//        main.putExtra("Username",us);
//        startActivity(main);
//    }
