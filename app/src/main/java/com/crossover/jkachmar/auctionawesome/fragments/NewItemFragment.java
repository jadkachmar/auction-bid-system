package com.crossover.jkachmar.auctionawesome.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crossover.jkachmar.auctionawesome.R;
import com.crossover.jkachmar.auctionawesome.db.Database;
import com.crossover.jkachmar.auctionawesome.main.AuctionActivity;
import com.crossover.jkachmar.auctionawesome.models.AuctionItem;
import com.crossover.jkachmar.auctionawesome.session.UserSessionManager;
import com.crossover.jkachmar.auctionawesome.views.NumberPickerExtended;

import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewItemFragment extends DialogFragment {

    @Bind(R.id.CancelButton)
    Button cancelButton;

    @Bind(R.id.CreateButton)
    Button createButton;

    @Bind(R.id.ItemName)
    EditText itemNameText;

    @Bind(R.id.ItemDescription)
    EditText itemDescriptionText;

    @Bind(R.id.StartingBidPicker)
    NumberPickerExtended startingBidPicker;

    @Bind(R.id.HoursText)
    EditText hoursText;

    @Bind(R.id.MinText)
    EditText minText;

    @Bind(R.id.SecText)
    EditText secText;

    public NewItemFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null) {
            return;
        }

        getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_fade);
    }


    public static NewItemFragment newInstance() {
        NewItemFragment fragment = new NewItemFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.new_item_fragment, container, false);
        ButterKnife.bind(this, v);

        setCancelable(false);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemName = itemNameText.getText().toString();
                String itemDescription = itemDescriptionText.getText().toString();
                int startingBid = startingBidPicker.getValue();

                String hoursStr = hoursText.getText().toString();
                String minsStr = minText.getText().toString();
                String secsStr = secText.getText().toString();

                int hrs = 0, mins = 0, secs = 0;
                if(!hoursStr.isEmpty())
                    hrs = Integer.parseInt(hoursStr);
                if(!minsStr.isEmpty())
                    mins = Integer.parseInt(minsStr);
                if(!secsStr.isEmpty())
                    secs = Integer.parseInt(secsStr);

                Calendar c = Calendar.getInstance();
                c.add(Calendar.HOUR, hrs);
                c.add(Calendar.MINUTE, mins);
                c.add(Calendar.SECOND, secs);

                long expiredTime = c.getTime().getTime();

                if(itemName.isEmpty()){
                    Toast.makeText(getContext(), "Please enter an item name", Toast.LENGTH_LONG).show();
                    return;
                }

                if(hoursStr.isEmpty() && minsStr.isEmpty() && secsStr.isEmpty()){
                    Toast.makeText(getContext(), "Please enter an expiration date", Toast.LENGTH_LONG).show();
                    return;
                }


                AuctionItem newItem = new AuctionItem();
                newItem.setName(itemName);
                newItem.setDescription(itemDescription);
                newItem.setCreatedBy(UserSessionManager.getInstance().getLoggedInUser().getUsername());
                newItem.setCreated(Long.toString(new Date().getTime()));
                newItem.setStartingBid(startingBid);
                newItem.setCurrentBid(startingBid);
                newItem.setExpiresIn(expiredTime + "");
                
                Database.auctionDAO.addAuctionItem(newItem);

                Toast.makeText(getContext(), "Item " + itemName + " has been created", Toast.LENGTH_LONG).show();

                ((AuctionActivity)getContext()).refreshData();
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return v;
    }
}