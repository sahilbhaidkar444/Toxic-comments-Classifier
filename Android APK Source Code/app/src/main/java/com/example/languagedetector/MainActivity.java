package com.example.languagedetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private ProgressBar progress;
    private Button predict_btn;
    private TextView prediction_text,text_input_user,text_display,text_prediction;
    private TextInputEditText text_input;
    private String url = "https://androiddeploy.herokuapp.com/prediction";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = findViewById(R.id.ProgressBar);
        predict_btn = findViewById(R.id.submit);
        prediction_text = findViewById(R.id.prediction);
        text_input = findViewById(R.id.text);
        text_display = findViewById(R.id.text_display);
        text_prediction = findViewById(R.id.text_prediction);
        text_input_user = findViewById(R.id.text_input_user);

        predict_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                text_display.setVisibility(View.VISIBLE);
                text_prediction.setVisibility(View.VISIBLE);
                text_input_user.setText(text_input.getText());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String data = jsonObject.getString("prediction");
                                    if(data.equals("1")){
                                        prediction_text.setText("Toxic Comment 👿...");
                                        Toast.makeText(getBaseContext(),"This is a Toxic Comment 😡...",Toast.LENGTH_SHORT).show();
                                        progress.setVisibility(View.GONE);
                                    }
                                    else {
                                        prediction_text.setText("Normal Comment 😇...");
                                        Toast.makeText(getBaseContext(),"This is a Normal Comment 😇...",Toast.LENGTH_SHORT).show();
                                        progress.setVisibility(View.GONE);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                text_input.setText("");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getBaseContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String,String>();
                        params.put("text",text_input.getText().toString());

                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(stringRequest);


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}