package com.hackton.welearning;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.*;


public class Uploadquestion extends AppCompatActivity {

    private static final String TAG = "Uploadquestion";
    EditText question, correct, wornganswer1, wornganswer2, wornganswer3;
    Button submit_button;
    JSONObject json;
    JSONObject json_answer;
    String subject, subjectName;
    TextView subjectTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        subject=Singleton.getInstance().getValue();
        subjectName = Singleton.getInstance().getName();
        Log.d("test","subject"+subject);
        setContentView(R.layout.activity_uploadquestion);
        subjectTV = findViewById(R.id.upload_title_subject);
        subjectTV.setText(subjectName);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        question = (EditText) findViewById(R.id.question);
        correct = (EditText) findViewById(R.id.correctanswer);
        wornganswer1 = (EditText) findViewById(R.id.wornganswer1);
        wornganswer2 = (EditText) findViewById(R.id.wornganswer2);
        wornganswer3 = (EditText) findViewById(R.id.wornganswewr3);
        submit_button = (Button) findViewById(R.id.submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String str_question = question.getText().toString();

                json = new JSONObject();
                json_answer = new JSONObject();
                try {
                    json.put("question", question.getText().toString());
                    json_answer.put("1", correct.getText().toString());
                    json_answer.put("2", wornganswer1.getText().toString());
                    json_answer.put("3", wornganswer2.getText().toString());
                    json_answer.put("4", wornganswer3.getText().toString());
                    json.put("answers", json_answer);
                    json.put("correctAnswer", "1");
                    send_question(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void send_question(JSONObject json) {


        RequestQueue queue = Volley.newRequestQueue(this);// this = context\
        final String url = "http://45.55.61.171:5050/api/subjects/1/questions";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());





                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };
        queue.add(jsonObjReq);
    }

}