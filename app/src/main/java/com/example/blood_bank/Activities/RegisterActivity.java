package com.example.blood_bank.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blood_bank.R;
import com.example.blood_bank.Utils.Endpoints;
import com.example.blood_bank.Utils.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

public class RegisterActivity extends AppCompatActivity {
    EditText name,city,number,blood,password;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        city = findViewById(R.id.city);
        number = findViewById(R.id.number);
        blood = findViewById(R.id.blood);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namet, cityt, numbert, bloodt, passwordt;
                namet = name.getText().toString();
                cityt = city.getText().toString();
                numbert = number.getText().toString();
                bloodt = blood.getText().toString();
                passwordt = password.getText().toString();
            //    showMessage(namet+"\n"+cityt+"\n"+numbert+"\n"+bloodt+"\n");
                if(isValid(namet,cityt,numbert,bloodt,passwordt)){
                      register(namet,cityt,bloodt,passwordt,numbert);
                }
            }

        });
    }

    private void register(final String name, final String city, final String blood_group, final String password,
                          final String mobile) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("SUCCESS")){
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                            .putString("city", city).apply();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    RegisterActivity.this.finish();
                }else{
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("city", city);
                params.put("blood_group", blood_group);
                params.put("password", password);
                params.put("number", mobile);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private boolean isValid(String name,String city,String number,String blood,String password){
        ArrayList<String> valid_blood_groups=new ArrayList<>();
        valid_blood_groups.add("A+");
        valid_blood_groups.add("A-");
        valid_blood_groups.add("B+");
        valid_blood_groups.add("B-");
        valid_blood_groups.add("AB+");
        valid_blood_groups.add("AB-");
        valid_blood_groups.add("O+");
        valid_blood_groups.add("O-");
        if(name.isEmpty()){
            showMessage("Name is Empty");
            return false;
        }else if(city.isEmpty()){
            showMessage("City is Empty!");
            return false;
        }else if(!valid_blood_groups.contains(blood)){
            showMessage("Blood group invalid choose from "+valid_blood_groups);
            return false;
        }else if(number.length()!=10){
            showMessage("Invalid mobile number, number should be of 10 digits.");
            return false;
        }else if(password.isEmpty()){
            showMessage("Password is Empty!");
            return false;
        }
        return true;
    }
        public void showMessage(String msg){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }

}
