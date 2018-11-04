package com.android.mlpj.southerninvestments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private ProgressDialog mProgressDialog;
    private SQLLiteHelper sqlLiteHelper;
    private UserLocalStore userLocalStore;

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _loginButton = findViewById(R.id.btn_login);

        userLocalStore = new UserLocalStore(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        sqlLiteHelper = new SQLLiteHelper(this);
        //Customer customer = new Customer(1,12,"Lahiru", "abc@abc.com","98745615V","0714587894","active","no 78","suh","dddd",4,null,null);
        //long result = sqlLiteHelper.insertCustomer(customer);
        //Toast.makeText(this, Long.toString(result), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (userLocalStore.getUserLoggedIn() == true) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void login() {
        Log.d(TAG, "Login");

        /*if (!validate()) {
            onLoginFailed();
            return;
        }*/

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.equals("")) {
            _emailText.setError("Can't be empty");
        } else if (password.equals("")) {
            _passwordText.setError("Can't be empty");
        } else {
            _loginButton.setEnabled(false);

            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("Logging In...");
            mProgressDialog.setMessage("Please wait for the Authentication!");
            mProgressDialog.show();





            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://www.southernpropertydevelopers.com/")
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiInterface client = retrofit.create(ApiInterface.class);
            Call<LoginResultPOJO> call = client.login(email, password);

            call.enqueue(new Callback<LoginResultPOJO>() {
                @Override
                public void onResponse(Call<LoginResultPOJO> call, Response<LoginResultPOJO> response) {
                    if(response.code() == 200){
                        try{
                            LoginResultPOJO loginResultPOJO = response.body();
                            Toast.makeText(LoginActivity.this, "Error : " + Boolean.toString(loginResultPOJO.isError()), Toast.LENGTH_LONG).show();
                            if(!loginResultPOJO.isError()){
                                //inserting customer tuples
                                for(int i = 0; i < loginResultPOJO.getCustomers().size();i++){
                                    long result = sqlLiteHelper.insertCustomer(loginResultPOJO.getCustomers().get(i));
                                }

                                //inserting loan tuples
                                for(int i = 0; i < loginResultPOJO.getLoans().size();i++){
                                    long result = sqlLiteHelper.insertLoans(loginResultPOJO.getLoans().get(i));
                                }

                                //insert repayments tuples
                                for(int i = 0; i < loginResultPOJO.getRepayments().size();i++){
                                    long result = sqlLiteHelper.insertRepayments(loginResultPOJO.getRepayments().get(i));
                                }


                                //saving salesrep details in shared preferences
                                userLocalStore.setUserLoggedIn(true);
                                userLocalStore.setUserDetails(loginResultPOJO.getUser());

                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }catch (NullPointerException e){
                            Toast.makeText(LoginActivity.this, "Null pointer Exception "+ e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }else{
                        Toast.makeText(LoginActivity.this, "HTTP Error code " + response.code(), Toast.LENGTH_LONG).show();
                    }


                    mProgressDialog.dismiss();
                    _loginButton.setEnabled(true);

                }

                @Override
                public void onFailure(Call<LoginResultPOJO> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(LoginActivity.this, "Connection Failure " + t.getMessage(), Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                    _loginButton.setEnabled(true);
                }
            });
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
