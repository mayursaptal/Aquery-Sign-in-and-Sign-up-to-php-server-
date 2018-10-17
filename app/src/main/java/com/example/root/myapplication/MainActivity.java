package com.example.root.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final   AQuery aq = new AQuery(this);
        final   String url = "http://themysteryboy.cf/user/login";


        final EditText email, password;
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        final Button signup_button = findViewById(R.id.signup);
        signup_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);


            }
        });

        final Button button = findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Map<String, Object> params = new HashMap<String, Object>();

                params.put("email", email.getText().toString().trim());
                params.put("password", password.getText().toString().trim());

                aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

                    @Override
                    public void callback(String url, JSONObject json, AjaxStatus status) {
                        try {
                            TextView newtext = (TextView) findViewById(R.id.test);
                            if(json != null){
                                //successful ajax call
                                String data = json.optString("data").toString();
                                String mgs = json.optString("msg").toString();
                                newtext.setText( data +"\n"+ mgs +"\n email"+ email.getText().toString().trim() + "\n password"+ password.getText().toString());

                                if(data == "true"){

//                                    setContentView(R.layout.home);

                                    Intent intent = new Intent(MainActivity.this,   home.class);
                                    startActivity(intent);
                                }

                            }else{
                                //ajax error
                                newtext.setText("fail");
                            }
                        } catch (Exception e) {
                            TextView newtext = (TextView) findViewById(R.id.test);
                            newtext.setText("error catch");

                        }
                    }
                });
            }
        });


    }


}
