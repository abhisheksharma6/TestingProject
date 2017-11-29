package com.denimhouse.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denimhouse.Adapters.CheckOutAdapter;
import com.denimhouse.Models.AllProductsModel;
import com.denimhouse.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManager sessionManager;
    TextView name, email, phoneNumber;
    String firstNameS, lastNameS, emailS, phoneNumberS;
    private RecyclerView recyclerView;
    private CheckOutAdapter adapter;
    private ArrayList<AllProductsModel> checkOutList;
    private ArrayList<AllProductsModel> finalCheckOutList;
    ProgressBar progressBar;
    Button sendMail;
    Context context;
    ImageView backButton;
    String URL, sessionEmail, sessionPassword, responsemessage, message;
    StringBuilder sb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        backButton = (ImageView) findViewById(R.id.back_button1);
        sendMail = (Button) findViewById(R.id.sendMail);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        checkOutList = new ArrayList<>();
        // Bundle bundle = getIntent().getExtras();
        checkOutList = getIntent().getParcelableArrayListExtra("key");
        // checkOutList = ((MainDashboardActivity)getApplicationContext()).passCheckOutList();


        //Getting the required values from CheckOutList
        sb = new StringBuilder();
        for (AllProductsModel a : checkOutList) {
            sb.append("Product Name:- " + a.getProductName().toString());
            sb.append("\n");
            sb.append("SubTotal:- " + a.getSubTotal().toString());
            sb.append("\n");
            sb.append("Product Code:- " + a.getId().toString());
            sb.append("\n");
            sb.append("Size:- " + a.getSize().toString());
            sb.append("\n");
        }


        //Getting the user's details
        sessionManager = new SessionManager(CheckOutActivity.this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        sessionEmail = user.get(SessionManager.KEY_EMAIL);
        sessionPassword = user.get(SessionManager.KEY_PASSWORD);

        sendMail.setOnClickListener(this);
        backButton.setOnClickListener(this);

        setAdapter();
        hitApi();

    }

    public void setAdapter() {

        adapter = new CheckOutAdapter(this, checkOutList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void hitApi() {
        progressBar.setVisibility(View.VISIBLE);
        //mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        RequestQueue queue = Volley.newRequestQueue(CheckOutActivity.this);
        URL = "http://vertosys.com/denimhouse/webservices/myaccount.php" + "?email=" + sessionEmail + "&password=" + sessionPassword;

        /*JSONObject jsonBody = new JSONObject();
        try{
            jsonBody.put("email",email);
            jsonBody.put("password",email);
        }catch(JSONException e){
            e.printStackTrace();
        }*/
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        Log.e("Response", response.toString());
                        progressBar.setVisibility(View.GONE);

                        try {

                            responsemessage = response.getString("success");
                            message = response.getString("message");
                            JSONObject data = response.getJSONObject("data");

                            if (responsemessage.equals("true")) {

                                String fName = data.getString("firstname");
                                String lName = data.getString("lastname");
                                String objEmail = data.getString("email");
                                String phone = data.getString("phone");

                                name.setText(fName + " " + lName);
                                email.setText(objEmail);
                                phoneNumber.setText(phone);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        queue.add(jsonObjectRequest);
    }


   /* public void passList(ArrayList<AllProductsModel> checkOutList){
        this.checkOutList = checkOutList;

    }*/

    public void sendDetailsOfUser() {
        String userName, userEmail, userPhoneNumber;
        userName = name.getText().toString();
        userEmail = email.getText().toString();
        userPhoneNumber = phoneNumber.getText().toString();
        Log.i("Send email", "");
        String[] TO = {"denimhouse77@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "App Mobile Order");
        emailIntent.putExtra(Intent.EXTRA_TEXT, sb + "\n" + "UserName:- " + userName + "\n" + "UserEmail:- " + userEmail + "\n"
                + "UserPhone No:- " + userPhoneNumber);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CheckOutActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


   /* @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMail:
                sendDetailsOfUser();
                break;
            case R.id.back_button1:
                finish();
                break;
        }
    }

}
