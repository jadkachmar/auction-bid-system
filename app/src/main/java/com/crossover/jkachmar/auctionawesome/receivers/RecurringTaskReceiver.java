package com.crossover.jkachmar.auctionawesome.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.crossover.jkachmar.auctionawesome.common.AuctionConstants;
import com.crossover.jkachmar.auctionawesome.db.Database;
import com.crossover.jkachmar.auctionawesome.main.AuctionActivity;
import com.crossover.jkachmar.auctionawesome.models.AuctionItem;

import java.util.List;
import java.util.Random;

public class RecurringTaskReceiver extends BroadcastReceiver {

    private AuctionActivity auctionActivity;

    public void setListener(AuctionActivity auctionActivity) {
        this.auctionActivity = auctionActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(AuctionConstants.AUCTION_PRIVATE_RECURRING_INTENT_FILTER)) {

            //PERFORM BOT AUCTION OPERATION
            List<AuctionItem> availableAuctionItems = Database.auctionDAO.fetchAvailableAuctionItems(AuctionConstants.AUCTION_BOT_USERNAME);
            if(availableAuctionItems.size() == 0)
                return;

            int randomIndex;
            if(availableAuctionItems.size() == 1)
                randomIndex = 0;
            else {
                Random generator = new Random();
                randomIndex = generator.nextInt(availableAuctionItems.size() - 1);
            }

            AuctionItem item = availableAuctionItems.get(randomIndex);
            item.setCurrentBidder(AuctionConstants.AUCTION_BOT_USERNAME);
            item.setCurrentBid(item.getCurrentBid() + 1);

            Database.auctionDAO.updateAuctionItem(item);

            //REFRESH DATA
            auctionActivity.refreshData();
        }
    }
}