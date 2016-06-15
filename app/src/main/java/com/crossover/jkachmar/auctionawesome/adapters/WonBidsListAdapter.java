package com.crossover.jkachmar.auctionawesome.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crossover.jkachmar.auctionawesome.R;
import com.crossover.jkachmar.auctionawesome.models.AuctionItem;

import java.util.ArrayList;
import java.util.List;

public class WonBidsListAdapter extends RecyclerView.Adapter<WonBidsListAdapter.DataObjectHolder> {

    private List<AuctionItem> auctionItems = new ArrayList<AuctionItem>();
    private static ClickListener clickListener;

    public void setItems(List<AuctionItem> auctionItems) {
        this.auctionItems = auctionItems;
    }

    @Override
    public WonBidsListAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.won_item_row, parent, false);

        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(WonBidsListAdapter.DataObjectHolder holder, int position) {
        AuctionItem currentItem = auctionItems.get(position);
        if (currentItem != null) {

            holder.itemName.setText(currentItem.getName());
            holder.itemDescription.setText(currentItem.getDescription());

            holder.data = auctionItems.get(position);
        }
    }

    @Override
    public int getItemCount() {
        return auctionItems.size();
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AuctionItem data;

        TextView itemName;
        TextView itemDescription;

        public DataObjectHolder(View itemView) {
            super(itemView);

            itemName = (TextView) itemView.findViewById(R.id.ItemName);
            itemDescription = (TextView) itemView.findViewById(R.id.ItemDescription);

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