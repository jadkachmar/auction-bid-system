package com.crossover.jkachmar.auctionawesome;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.crossover.jkachmar.auctionawesome.common.AuctionConstants;
import com.crossover.jkachmar.auctionawesome.db.Database;
import com.crossover.jkachmar.auctionawesome.main.AuctionActivity;
import com.crossover.jkachmar.auctionawesome.main.LoginActivity;
import com.crossover.jkachmar.auctionawesome.main.RegisterActivity;
import com.crossover.jkachmar.auctionawesome.models.User;
import com.robotium.solo.Solo;

public class RegistrationTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private Solo solo;

    public RegistrationTest() {
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

    public void testGoToRegistration() throws Exception {
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);

        solo.clickOnButton("Register");

        solo.waitForActivity(RegisterActivity.class);
        solo.assertCurrentActivity("Expected AuctionActivity to launch", RegisterActivity.class);
    }

    public void testUserRegistrationValidations() throws Exception {
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);

        solo.clickOnButton("Register");

        solo.waitForActivity(RegisterActivity.class);

        //empty username
        EditText usernameText = (EditText) solo.getView(R.id.UsernameText);
        EditText passwordText = (EditText) solo.getView(R.id.PasswordText);

        assertTrue(usernameText.getText().toString().isEmpty());
        assertTrue(passwordText.getText().toString().isEmpty());

        solo.clickOnButton("Register");
        assertTrue(solo.waitForText("Please enter a username"));

        //existing username
        solo.typeText(usernameText, AuctionConstants.AUCTION_USER1_USERNAME);

        solo.clickOnButton("Register");
        assertTrue(solo.waitForText("Username : " + AuctionConstants.AUCTION_USER1_USERNAME + " already exists"));

        //empty password
        solo.clearEditText(usernameText);
        solo.typeText(usernameText, "awesomeUser2");

        solo.clickOnButton("Register");
        assertTrue(solo.waitForText("Password cannot be empty"));
    }


    public void testValidUserRegistration() throws Exception{
        solo.assertCurrentActivity("Wrong activity", LoginActivity.class);

        solo.clickOnButton("Register");

        solo.waitForActivity(RegisterActivity.class);

        //empty username
        EditText usernameText = (EditText) solo.getView(R.id.UsernameText);
        EditText passwordText = (EditText) solo.getView(R.id.PasswordText);

        assertTrue(usernameText.getText().toString().isEmpty());
        assertTrue(passwordText.getText().toString().isEmpty());

        solo.typeText(usernameText, "awesomeUser2");
        solo.typeText(passwordText, "p2");

        solo.clickOnButton("Register");

        solo.waitForActivity(LoginActivity.class);
        solo.assertCurrentActivity("Expected AuctionActivity to launch", LoginActivity.class);

        assertTrue(solo.waitForText("User " + "awesomeUser2" + " has been created successfully"));

        User newUser = Database.userDAO.fetchUserByName("awesomeUser2");

        assertTrue(newUser!=null);
        assertTrue(newUser.getUsername().equals("awesomeUser2"));
        assertTrue(newUser.getPassword().equals("p2"));
    }
}