package com.crossover.jkachmar.auctionawesome.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import com.crossover.jkachmar.auctionawesome.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends Activity {

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

    }
}
