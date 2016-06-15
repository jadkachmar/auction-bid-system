package com.crossover.jkachmar.auctionawesome.session;

import com.crossover.jkachmar.auctionawesome.db.Database;
import com.crossover.jkachmar.auctionawesome.models.User;

public class UserSessionManager {

    private static UserSessionManager manager_instance;

    private User loggedInUser;

    private UserSessionManager() {
    }

    public synchronized static UserSessionManager getInstance() {
        if (manager_instance == null) {
            manager_instance = new UserSessionManager();
        }
        return manager_instance;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }

    public void login(String username, String password) throws Exception {
        User user = Database.userDAO.fetchUserByName(username);
        if(user==null || !user.getPassword().equals(password)){
            throw new Exception("Invalid Credentials. Please try again");
        }
        loggedInUser = user;
    }

    public void logout(){
        loggedInUser = null;
    }
}