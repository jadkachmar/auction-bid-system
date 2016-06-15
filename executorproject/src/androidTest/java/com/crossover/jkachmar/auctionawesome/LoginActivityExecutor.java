package com.crossover.jkachmar.auctionawesome;

import android.app.Activity;
import com.robotium.recorder.executor.Executor;

@SuppressWarnings("rawtypes")
public class LoginActivityExecutor extends Executor {

	@SuppressWarnings("unchecked")
	public LoginActivityExecutor() throws Exception {
		super((Class<? extends Activity>) Class.forName("com.crossover.jkachmar.auctionawesome.main.LoginActivity"),  "com.crossover.jkachmar.auctionawesome.R.id.", new android.R.id(), false, false, "1465951385250");
	}

	public void setUp() throws Exception { 
		super.setUp();
	}
}
