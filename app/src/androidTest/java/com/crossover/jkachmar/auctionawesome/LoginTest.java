package com.crossover.jkachmar.auctionawesome;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.widget.Button;
import android.widget.EditText;

import com.crossover.jkachmar.auctionawesome.common.AuctionConstants;
import com.crossover.jkachmar.auctionawesome.main.AuctionActivity;
import com.crossover.jkachmar.auctionawesome.main.LoginActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;

public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private Solo solo;

    public LoginTest() {
        super(LoginActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    public void testUserLoginValidations() throws Exception {
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);

        EditText usernameText = (EditText) solo.getView(R.id.UserNameText);
        EditText passwordText = (EditText) solo.getView(R.id.PasswordText);

        assertTrue(usernameText.getText().toString().equals(""));
        assertTrue(passwordText.getText().toString().equals(""));

        //test login with no username
        solo.clickOnButton("Login");
        assertTrue(solo.waitForText("Please enter a username"));

        //test login with no password
        solo.typeText(usernameText, "testuser");

        solo.clickOnButton("Login");
        assertTrue(solo.waitForText("Please enter a password"));
    }

    public void testInvalidUserLogin() throws Exception {
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);

        EditText usernameText = (EditText) solo.getView(R.id.UserNameText);
        EditText passwordText = (EditText) solo.getView(R.id.PasswordText);

        assertTrue(usernameText.getText().toString().equals(""));
        assertTrue(passwordText.getText().toString().equals(""));

        //test invalid user
        solo.typeText(usernameText, "invalidUser");
        solo.typeText(passwordText, "invalidPassword");

        solo.clickOnButton("Login");
        assertTrue(solo.waitForText("Invalid Credentials. Please try again"));
    }

    public void testValidUserInvalidPasswordLogin() throws Exception {
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);

        EditText usernameText = (EditText) solo.getView(R.id.UserNameText);
        EditText passwordText = (EditText) solo.getView(R.id.PasswordText);

        assertTrue(usernameText.getText().toString().equals(""));
        assertTrue(passwordText.getText().toString().equals(""));

        //test invalid user
        solo.typeText(usernameText, AuctionConstants.AUCTION_USER1_USERNAME);
        solo.typeText(passwordText, "invalidPassword");

        solo.clickOnButton("Login");
        assertTrue(solo.waitForText("Invalid Credentials. Please try again"));
    }

    public void testValidUserValidPasswordLogin() throws Exception {
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);

        EditText usernameText = (EditText) solo.getView(R.id.UserNameText);
        EditText passwordText = (EditText) solo.getView(R.id.PasswordText);

        assertTrue(usernameText.getText().toString().equals(""));
        assertTrue(passwordText.getText().toString().equals(""));

        //test invalid user
        solo.typeText(usernameText, AuctionConstants.AUCTION_USER1_USERNAME);
        solo.typeText(passwordText, AuctionConstants.AUCTION_USER1_PASSWORD);

        solo.clickOnButton("Login");

        solo.waitForActivity(AuctionActivity.class);
        solo.assertCurrentActivity("Expected AuctionActivity to launch", AuctionActivity.class);
    }
}