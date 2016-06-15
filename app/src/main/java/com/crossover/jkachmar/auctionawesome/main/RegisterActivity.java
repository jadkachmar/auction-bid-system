package com.crossover.jkachmar.auctionawesome.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.crossover.jkachmar.auctionawesome.R;
import com.crossover.jkachmar.auctionawesome.db.Database;
import com.crossover.jkachmar.auctionawesome.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends Activity {

    public static final String USERNAME_FIELD = "USERNAME_FIELD";
    public static final String PASSWORD_FIELD = "PASSWORD_FIELD";

    public static final int REQUEST_CODE = 111;

    @Bind(R.id.UsernameText)
    EditText usernameText;

    @Bind(R.id.MradioButton)
    RadioButton mRadioButton;

    @Bind(R.id.EmailText)
    EditText emailText;

    @Bind(R.id.PasswordText)
    EditText passwordText;

    @Bind(R.id.RegisterButton)
    Button registerButton;

    @Bind(R.id.CancelButton)
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_activity);
        ButterKnife.bind(this);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = usernameText.getText().toString().trim();

                if(username.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_LONG).show();
                    return;
                }

                User existingUser = Database.userDAO.fetchUserByName(username);
                if(existingUser!=null){
                    Toast.makeText(getApplicationContext(), "Username : " + username + " already exists", Toast.LENGTH_LONG).show();
                    return;
                }

                String password = passwordText.getText().toString();

                if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                String email = emailText.getText().toString().trim();

                User newUser = new User();
                newUser.setUsername(username);
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setGender(mRadioButton.isChecked()?"M":"F");

                Database.userDAO.addUser(newUser);

                Intent intent=new Intent();
                intent.putExtra(USERNAME_FIELD, username);
                intent.putExtra(PASSWORD_FIELD, password);
                setResult(REQUEST_CODE,intent);

                Toast.makeText(getApplicationContext(), "User " + username + " has been created successfully", Toast.LENGTH_LONG).show();

                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
