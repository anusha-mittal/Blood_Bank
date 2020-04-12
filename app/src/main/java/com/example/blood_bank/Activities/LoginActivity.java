package com.example.blood_bank.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blood_bank.R;
import com.example.blood_bank.Utils.Endpoints;
import com.example.blood_bank.Utils.VolleySingleton;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.xml.transform.ErrorListener;

public class LoginActivity extends AppCompatActivity {
    EditText numberEt,passwordEt;
    Button submit_button;
    TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        numberEt=findViewById(R.id.number);
        passwordEt=findViewById(R.id.password);
        submit_button=findViewById(R.id.submit_button);
        signUpText=findViewById(R.id.sign_up_text);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberEt.setError(null);
                passwordEt.setError(null);
                String numbert=numberEt.getText().toString();
                String passwordt=passwordEt.getText().toString();
                if(isValid(numbert,passwordt)){
                    login(numbert,passwordt);
                }
            }
        });
    }

    public void login(final String number, final String password){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Endpoints.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.equals("Invalid Credentials")) {
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                            .putString("number", number).apply();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                            .putString("city", response).apply();
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", Objects.requireNonNull(error.getMessage()));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("number", number);
                params.put("password", password);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private boolean isValid(String num,String pass){
        if (num.isEmpty()) {
            showMessage("Empty Mobile Number");
            numberEt.setError("Empty Mobile Number");
            return false;
        } else if (pass.isEmpty()) {
            showMessage("Empty Password");
            passwordEt.setError("Empty Password");
            return false;
        }
        return true;
    }
    private void showMessage(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
