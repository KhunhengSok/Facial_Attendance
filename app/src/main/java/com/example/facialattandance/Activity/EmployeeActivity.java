package com.example.facialattandance.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.facialattandance.Model.Employee;
import com.example.facialattandance.R;
import com.example.facialattandance.adaptor.EmployeeAdaptor;
import com.google.gson.Gson;
import org.json.JSONArray;

public class EmployeeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private String endpoint;
    private String ENDPOINT = "1000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        recyclerView = findViewById(R.id.recycler_view);

        progressBar = findViewById(R.id.progress_bar);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        Intent intent = getIntent();
        endpoint = intent.getStringExtra("1000");
        loadEmployee(endpoint);

        recyclerView.setLayoutManager(layoutManager);
    }

    private void loadEmployee(String endpoint) {
        // Show progress bar and hide recycler view
        showLoading(true);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "http://10.0.2.2:3000/bestEmployee/";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                showLoading(false);
                Gson gson = new Gson();
                Employee[] employees = gson.fromJson(response.toString(), Employee[].class);
                EmployeeAdaptor adapter = new EmployeeAdaptor(employees);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showLoading(false);

                Toast.makeText(EmployeeActivity.this, "Load data error.", Toast.LENGTH_LONG).show();
                Log.d("log data", "Load data error: " + error.getMessage());
            }
        });

        // Add request to Queue
        requestQueue.add(request);
    }

    private void showLoading(boolean state){
        if(state) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
