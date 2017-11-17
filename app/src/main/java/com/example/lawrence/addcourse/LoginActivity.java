package com.example.lawrence.addcourse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Lawrence on 6/27/2017.
 */
public class LoginActivity extends ActionBarActivity {
    DBHelper helpa = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        OnFirstRun();

    }

    public void OnLoginClickHandler(View view) {
        EditText eTuser = (EditText) findViewById(R.id.etuserlogin);
        EditText eTpass = (EditText) findViewById(R.id.etpasslogin);

        String tuser = eTuser.getText().toString();
        String tpass = eTpass.getText().toString();

        // search method to authenticate user
        // searchPass returns a password
        // checks if returned object equals the password user entered
        //Boolean rezult = helpa.checkUser(tuser,tpass);
        if (helpa.checkUser(tuser,tpass)){
            Intent wlcmIntent = new Intent(this, WelcomeActivity.class);
            wlcmIntent.putExtra("Username",tuser);
            startActivity(wlcmIntent);
            this.finish();
        }else {
            Toast.makeText(this, "Wrong Details Entered!!", Toast.LENGTH_LONG).show();

        }
    }

    public void OnFirstRun(){
        Boolean isFirstRun = getSharedPreferences("Preference", MODE_PRIVATE)
                .getBoolean("isfirstrun",true);

        if(isFirstRun){
            // do your thing
            Intent introIntent = new Intent(this,StudentProfileActivity.class);
            startActivity(introIntent);
            this.finish();

            getSharedPreferences("Preference", MODE_PRIVATE).edit()
                    .putBoolean("isfirstrun",false).commit();
        }
    }
}
