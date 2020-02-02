package com.nith.appteam.nimbus2020.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.nith.appteam.nimbus2020.Adapters.QuizRecyclerAdapter;
import com.nith.appteam.nimbus2020.Models.Id_Value;
import com.nith.appteam.nimbus2020.R;
import com.nith.appteam.nimbus2020.Utils.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DepartmentQuiz extends AppCompatActivity {
    RecyclerView departmentquiz;
    TextView textView;
    RequestQueue queue;
    ArrayList<Id_Value> quiztypes = new ArrayList<>();
    ProgressBar loadwall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_quiz);
        departmentquiz = findViewById(R.id.departmentquiz);
        textView = findViewById(R.id.departmentname);

        loadwall = findViewById(R.id.loadwall);

        queue = Volley.newRequestQueue(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        departmentquiz.setLayoutManager(layoutManager);
        departmentquiz.setAdapter(new QuizRecyclerAdapter(this, quiztypes));
        getdata();
        departmentquiz.addOnItemTouchListener(
                new RecyclerItemClickListener(this, departmentquiz,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                postdata(position);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        })
        );

    }


    private void getdata() {
        Intent j = getIntent();
        String response = j.getStringExtra("quiz");
        textView.setText(j.getStringExtra("departmentname"));
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                Id_Value idValue = new Id_Value(jsonArray.getJSONObject(i).getString("quizName"),
                        jsonArray.getJSONObject(i).getString("_id"),
                        jsonArray.getJSONObject(i).getString("image"));
                quiztypes.add(idValue);
                Objects.requireNonNull(departmentquiz.getAdapter()).notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void postdata(final int position) {
        loadwall.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getString(R.string.baseUrl) + "/quiz/questions", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadwall.setVisibility(View.GONE);
                Log.e("hi", "onResponse: " + response);
                Intent intent = new Intent(DepartmentQuiz.this, QuizInstructionsActivity.class);
                intent.putExtra("questions", response);
                intent.putExtra("quizId", quiztypes.get(position).getId());
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }

        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences=getSharedPreferences("app",MODE_PRIVATE);
                String token=sharedPreferences.getString("token",null);
                HashMap<String,String> map=new HashMap<>();
                map.put("token",token);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("quizId", quiztypes.get(position).getId());
                return params;
            }

        };

        queue.add(stringRequest);

    }


}
