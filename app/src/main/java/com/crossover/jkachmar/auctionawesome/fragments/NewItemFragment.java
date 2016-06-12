package com.crossover.jkachmar.auctionawesome.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crossover.jkachmar.auctionawesome.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewItemFragment extends DialogFragment {

    @Bind(R.id.CancelButton)
    Button cancelButton;

    @Bind(R.id.CreateButton)
    Button createButton;

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