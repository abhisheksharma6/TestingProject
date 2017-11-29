package com.denimhouse.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denimhouse.Network.AppUrl;
import com.denimhouse.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    private Pattern pattern;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    EditText etEmail, etPassword;
    TextView forgetPassword;
    String email, password, response_message, fname, lname, phonenNumber;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        pattern = Pattern.compile(EMAIL_PATTERN);

        etEmail = (EditText) findViewById(R.id.etEmailLogin);
        etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        login = (Button) findViewById(R.id.loginbutton);

        login.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        SessionManager sessionManager = new SessionManager(LoginActivity.this);
        sessionManager.checkLogin();
    }


    public void getUserDetails() {
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (email.equals("") && password.equals("")) {
            Toast.makeText(getApplicationContext(), "fill all the fields", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            etPassword.setError("Enter your password");
            etPassword.requestFocus();
        } else if (!validate(email)) {
            etEmail.setError("This email doesn't exist");
            etEmail.requestFocus();
        } else if (validate(email) && !password.equals("")) {
            progressBar.setVisibility(View.VISIBLE);
            String url = AppUrl.BASE_URL + AppUrl.SIGN_IN + "email=" + email + "&password=" + password;
            validLogin(url);

        }

    }

    public void validLogin(String loginUrl) {
        RequestQueue queue = Volley.newRequestQueue(this);
        // JSONObject jsonBody = new JSONObject();
       /* try {
            jsonBody.put("email",email);
            jsonBody.put("password",password);
          // jsonBody.put("role_id","4");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, loginUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());

                        try {
                            response_message = response.getString("message");


                            if (response_message.contains("Success")) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Log.e("Data23", response.getString("message"));
                                SessionManager sessionManager = new SessionManager(LoginActivity.this);
                                sessionManager.createLoginSession(email, fname, lname, password, phonenNumber);
                                Intent intent = new Intent(LoginActivity.this, MainDashboardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();

                            } else {
                                JSONArray errors = response.getJSONArray("errors");
                                String errorMessage = errors.getString(0);
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(jsObjRequest);
    }


    public boolean validate(final String hex) {
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
       *//* Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
        startActivity(intent);*//*
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginbutton:
                getUserDetails();
                break;
            case R.id.forgetPassword:
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
