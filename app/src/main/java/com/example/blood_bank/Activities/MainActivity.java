package com.example.blood_bank.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.blood_bank.Adapters.RequestAdapter;
import com.example.blood_bank.DataModels.RequestDataModel;
import com.example.blood_bank.R;
import com.example.blood_bank.Utils.Endpoints;
import com.example.blood_bank.Utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<RequestDataModel> requestDataModels;
    private RequestAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView make_request_button=findViewById(R.id.make_request_button);
        make_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MakeRequestActivity.class));
            }
        });

        requestDataModels=new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.action_search){
//                    //open search
//                    startActivity(new Intent(MainActivity.this,SearchActivity.class));
//                }
//                    return false;
//            }
//        });

        recyclerView=findViewById(R.id.recyclerView);
//        RecyclerView.LayoutManager layoutManager=new RecyclerView.LayoutManager() {
//            @Override
//            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
//                return null;
//            }
//        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        requestAdapter=new RequestAdapter(requestDataModels,this);
        recyclerView.setAdapter(requestAdapter);
        populateHomePage();
        TextView pick_location=findViewById(R.id.pick_location);
        String location=PreferenceManager.getDefaultSharedPreferences(this).getString("city","no_city_found");
        if (!location.equals("no_city_found")){
            pick_location.setText(location);
        }
    }

    private void populateHomePage(){
        final String city= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("city","no_city");
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Endpoints.get_requests, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<RequestDataModel>>(){}.getType();
                List<RequestDataModel> dataModels=gson.fromJson(response,type);
                requestDataModels.addAll(dataModels);
                requestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
                Log.d("VOLLEY", Objects.requireNonNull(error.getMessage()));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("city",city);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_search){
            startActivity(new Intent(MainActivity.this,SearchActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
