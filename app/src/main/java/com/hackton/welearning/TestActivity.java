package com.hackton.welearning;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class TestActivity extends AppCompatActivity {

    TextView questionTextView;
    TextView subjectTV;
    CheckBox ans1, ans2, ans3, ans4;
    int correctAnswer;
    String subject, subjectName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subject=Singleton.getInstance().getValue();
        subjectName = Singleton.getInstance().getName();
        Log.d("test","subject"+subject);
        setContentView(R.layout.activity_test);
        subjectTV = findViewById(R.id.which_subject);
        subjectTV.setText(subjectName);
        questionTextView = findViewById(R.id.which_question);
        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        ans4 = findViewById(R.id.ans4);
        getSubjectsFromServer();
        getQuestionsFromServer(subject);


    }
    private void getSubjectsFromServer(){
        RequestQueue requestQueueForSubject = Volley.newRequestQueue(this);
        String urlSubjects = "http://45.55.61.171:5050/api/subjects";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlSubjects, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for(int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int resultCount = jsonObject.optInt("id");
                            if (resultCount > 0) {
                                String name = jsonObject.optString("name");
                                Log.d("Danielle", "GOT SOMETHING " + name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Danielle", error.toString());
            }
        });
        requestQueueForSubject.add(jsonArrayRequest);
    }

    private void getQuestionsFromServer(String subject){
        RequestQueue requestQueueForQeustions = Volley.newRequestQueue(this);
        String urlQuestions = "http://45.55.61.171:5050/api/subjects/"+subject + "/next-question";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
               (Request.Method.GET,urlQuestions,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        String question = response.getString("question") + "\n";
                        questionTextView.setText(question);
                        JSONArray answers = response.getJSONArray("answers");
                        ans1.setText((String)answers.get(0));
                        ans2.setText((String)answers.get(1));
                        ans3.setText((String)answers.get(2));
                        ans4.setText((String)answers.get(3));
                        correctAnswer = response.getInt("correctAnswer");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }}

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Danielle", error.toString());
            }
        });
        requestQueueForQeustions.add(jsonObjectRequest);

    }

    public void onSubmitClicked(View view){
        Log.i("Danielle", "correctAnswer is: " + correctAnswer + " " + ans1.isChecked());
        if (correctAnswer==0 && ans1.isChecked() == true || correctAnswer==1 && ans2.isChecked() == true ||
                correctAnswer==2 && ans3.isChecked() == true || correctAnswer==3 && ans4.isChecked() == true){
//            Log.i("Danielle", "correctAnswer is: " + correctAnswer);
//        }
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("תשובה נכונה!")
                    .setMessage("כל הכבוד :)")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            getQuestionsFromServer(subject);
                            ans1.setChecked(false);
                            ans2.setChecked(false);
                            ans3.setChecked(false);
                            ans4.setChecked(false);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("תשובה לא נכונה...")
                    .setMessage("לא נורא, נסה שוב :)")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void onCheckboxClicked(View view) {
        switch (view.getId()) {

            case R.id.ans1:
                ans2.setChecked(false);
                ans3.setChecked(false);
                ans4.setChecked(false);
                //if (correctAnswer == 1){

                //}

                break;

            case R.id.ans2:
                ans1.setChecked(false);
                ans3.setChecked(false);
                ans4.setChecked(false);
                break;

            case R.id.ans3:
                ans2.setChecked(false);
                ans1.setChecked(false);
                ans4.setChecked(false);
                break;

            case R.id.ans4:
                ans2.setChecked(false);
                ans3.setChecked(false);
                ans1.setChecked(false);
                break;

            default:
                break;
        }
    }

}
