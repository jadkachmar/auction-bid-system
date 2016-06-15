package com.crossover.jkachmar.auctionawesome.db.dao.base;

import com.crossover.jkachmar.auctionawesome.models.User;

import java.util.List;

public interface IUserDAO {

    public User fetchUserByName(String username);

    public List<User> fetchAllUsers();

    public boolean addUser(User user);

}