package com.crossover.jkachmar.auctionawesome.db.dao.base;

import com.crossover.jkachmar.auctionawesome.models.AuctionItem;
import com.crossover.jkachmar.auctionawesome.models.User;

import java.util.List;

public interface IAuctionItemDAO {

    public List<AuctionItem> fetchAuctionItemsByUsername(String username);

    public List<AuctionItem> fetchAvailableAuctionItems(String username);

    List<AuctionItem> fetchWonAuctionItems(String username);

    public List<AuctionItem> fetchAllAuctionItems();

    public boolean addAuctionItem(AuctionItem newItem);
}