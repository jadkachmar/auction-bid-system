package com.crossover.jkachmar.auctionawesome.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crossover.jkachmar.auctionawesome.R;
import com.crossover.jkachmar.auctionawesome.common.AuctionUtils;
import com.crossover.jkachmar.auctionawesome.models.AuctionItem;

import java.util.ArrayList;
import java.util.List;

public class BidItemsListAdapter extends RecyclerView.Adapter<BidItemsListAdapter.DataObjectHolder> {

    private List<AuctionItem> auctionItems = new ArrayList<AuctionItem>();
    private static ClickListener clickListener;

    public void setItems(List<AuctionItem> auctionItems) {
        this.auctionItems = auctionItems;
    }

    @Override
    public BidItemsListAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bid_item_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(BidItemsListAdapter.DataObjectHolder holder, int position) {
        AuctionItem currentItem = auctionItems.get(position);
        if (currentItem != null) {
            holder.itemName.setText(currentItem.getName());
            holder.itemDescription.setText(currentItem.getDescription());

            holder.currentBid.setText(currentItem.getCurrentBid() + "");

            if (!AuctionUtils.hasExpired(currentItem.getExpiresIn())) {
                holder.expiresIn.setText("Expires on " + AuctionUtils.getFormattedDate(currentItem.getExpiresIn()));
            }
            else {
                holder.expiresIn.setText("Expired !");
            }

            String currentBidder = currentItem.getCurrentBidder();

            holder.lastBid.setText("by " + (currentBidder!=null?currentBidder:""));
        }
    }

    @Override
    public int getItemCount() {
        return auctionItems.size();
    }

    public AuctionItem getItemAt(int position) {
        return auctionItems.get(position);
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }



    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemName;
        TextView itemDescription;
        TextView currentBid;
        TextView lastBid;
        TextView expiresIn;

        public DataObjectHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.ItemName);
            itemDescription = (TextView) itemView.findViewById(R.id.ItemDescription);
            lastBid = (TextView) itemView.findViewById(R.id.LastBid);
            currentBid = (TextView) itemView.findViewById(R.id.CurrentBid);
            expiresIn = (TextView) itemView.findViewById(R.id.ExpiresOn);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(getPosition(), v);
        }
    }

    public interface ClickListener {
        public void onItemClick(int position, View v);
    }
}