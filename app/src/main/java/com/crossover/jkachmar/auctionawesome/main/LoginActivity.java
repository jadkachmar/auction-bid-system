package com.crossover.jkachmar.auctionawesome.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crossover.jkachmar.auctionawesome.R;
import com.crossover.jkachmar.auctionawesome.session.UserSessionManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends Activity {

    @Bind(R.id.RegisterButton)
    Button registerButton;

    @Bind(R.id.LoginButton)
    Button loginButton;

    @Bind(R.id.UserNameText)
    EditText userNameText;

    @Bind(R.id.PasswordText)
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(i, RegisterActivity.REQUEST_CODE);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameText.getText().toString();
                String password = passwordText.getText().toString();

                //TODO - validations
                if(userName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    UserSessionManager.getInstance().login(userName, password);

                    Intent i = new Intent(LoginActivity.this, AuctionActivity.class);
                    startActivity(i);

                    finish();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RegisterActivity.REQUEST_CODE && data != null) {
            String username = data.getStringExtra(RegisterActivity.USERNAME_FIELD);
            String password = data.getStringExtra(RegisterActivity.PASSWORD_FIELD);

            userNameText.setText(username);
            passwordText.setText(password);
        }
    }
}
