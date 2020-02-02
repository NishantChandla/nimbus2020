package com.nith.appteam.nimbus2020.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.nith.appteam.nimbus2020.Models.QuestionData;
import com.nith.appteam.nimbus2020.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class Quiz extends AppCompatActivity {
    TextView questionView, questionnumber, timeview;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    RequestQueue requestQueue;
    List<QuestionData> questions = new ArrayList<>();
    int counter = 0;
    CountDownTimer timer;
    JSONArray mJSONArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mJSONArray = new JSONArray();
        Objects.requireNonNull(getSupportActionBar()).hide();
        timer = new CountDownTimer(15000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                timeview.setText(millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                counter++;
                if (counter < questions.size()) {
                    updateQuestion();

                } else {

                    getscore();
                }
            }
        };

        questionnumber = findViewById(R.id.quizquestionnumber);
        timeview = findViewById(R.id.quizTimer);
        questionView = findViewById(R.id.quizquestion);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        requestQueue = Volley.newRequestQueue(this);
        getQuestions();


        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(counter).setOption_chosen(1);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("questionId", questions.get(counter).getQuestionid());
                    jsonObject.put("answer", questions.get(counter).getOption_chosen());
                    mJSONArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.onFinish();


            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(counter).setOption_chosen(4);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("questionId", questions.get(counter).getQuestionid());
                    jsonObject.put("answer", questions.get(counter).getOption_chosen());
                    mJSONArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.onFinish();
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(counter).setOption_chosen(2);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("questionId", questions.get(counter).getQuestionid());
                    jsonObject.put("answer", questions.get(counter).getOption_chosen());
                    mJSONArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.onFinish();
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(counter).setOption_chosen(3);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("questionId", questions.get(counter).getQuestionid());
                    jsonObject.put("answer", questions.get(counter).getOption_chosen());
                    mJSONArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                timer.onFinish();
            }
        });


    }

    private void updateQuestion() {
        questionView.setText(questions.get(counter).getQuestion());
        option1.setText(questions.get(counter).getOption_1());
        option2.setText(questions.get(counter).getOption_2());
        option3.setText(questions.get(counter).getOption_3());
        option4.setText(questions.get(counter).getOption_4());
        questionnumber.setText("Q" + (counter + 1));
        timer.start();

    }


    private void getQuestions() {
        String response = getIntent().getStringExtra("questions");
        questionView.setText(response);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    QuestionData question = new QuestionData(
                            jsonArray.getJSONObject(i).getString("_id"),
                            jsonArray.getJSONObject(i).getString("question"),
                            jsonArray.getJSONObject(i).getString("option1"),
                            jsonArray.getJSONObject(i).getString("option2"),
                            jsonArray.getJSONObject(i).getString("option3"),
                            jsonArray.getJSONObject(i).getString("option4"));
                    questions.add(question);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        updateQuestion();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
        finish();
    }


    private void getscore() {
        option4.setClickable(false);
        option1.setClickable(false);
        option2.setClickable(false);
        option3.setClickable(false);
        Log.e("hiiii", "onResponse: ");
        final String result = mJSONArray.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                getString(R.string.baseUrl) + "/quiz/submit", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("hiiii", "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(Quiz.this, " score is " + jsonObject.getString("score"),
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Quiz.this, QuizScoreActivity.class);
                    intent.putExtra("score", Integer.valueOf(jsonObject.getString("score")));
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return result == null ? null : result.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf(
                            "Unsupported Encoding while trying to get the bytes of %s using %s",
                            result, "utf-8");
                    return null;
                }
            }
        };

        requestQueue.add(stringRequest);


    }

}
