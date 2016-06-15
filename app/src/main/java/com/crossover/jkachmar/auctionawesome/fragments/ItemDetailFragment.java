package com.crossover.jkachmar.auctionawesome.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.crossover.jkachmar.auctionawesome.R;
import com.crossover.jkachmar.auctionawesome.db.Database;
import com.crossover.jkachmar.auctionawesome.main.AuctionActivity;
import com.crossover.jkachmar.auctionawesome.models.AuctionItem;
import com.crossover.jkachmar.auctionawesome.session.UserSessionManager;
import com.crossover.jkachmar.auctionawesome.views.NumberPickerExtended;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemDetailFragment extends DialogFragment {

    @Bind(R.id.BidPicker)
    NumberPickerExtended bidPicker;

    @Bind(R.id.BidButton)
    Button bidButton;

    @Bind(R.id.CancelButton)
    Button cancelButton;

    private AuctionItem currentAuctionItem;

    public ItemDetailFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null) {
            return;
        }

        getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_fade);
    }

    public static ItemDetailFragment newInstance(AuctionItem auctionItem) {
        ItemDetailFragment fragment = new ItemDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("AUCTION_ITEM", auctionItem);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_detail_fragment, container, false);
        ButterKnife.bind(this, v);

        setCancelable(false);

        currentAuctionItem = (AuctionItem) getArguments().getSerializable("AUCTION_ITEM");

        bidPicker.setMinValue(currentAuctionItem.getCurrentBid() + 1);
        bidPicker.setMaxValue(1000);

        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedValue = bidPicker.getValue();
                currentAuctionItem.setCurrentBid(selectedValue);
                currentAuctionItem.setCurrentBidder(UserSessionManager.getInstance().getLoggedInUser().getUsername());

                Database.auctionDAO.updateAuctionItem(currentAuctionItem);

                Toast.makeText(getContext(), "Bid on item " + currentAuctionItem.getName() + " has been submitted. Good luck !", Toast.LENGTH_LONG).show();

                ((AuctionActivity)getContext()).refreshData();
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((AuctionActivity)getContext()).refreshData();
                dismiss();
            }
        });
        return v;
    }
}