package com.crossover.jkachmar.auctionawesome.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crossover.jkachmar.auctionawesome.R;
import com.crossover.jkachmar.auctionawesome.adapters.MyItemsListAdapter;
import com.crossover.jkachmar.auctionawesome.db.Database;
import com.crossover.jkachmar.auctionawesome.common.DividerItemDecoration;
import com.crossover.jkachmar.auctionawesome.models.AuctionItem;
import com.crossover.jkachmar.auctionawesome.session.UserSessionManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyItemsFragment extends Fragment {

    @Bind(R.id.MyItemsListViewLayout)
    SwipeRefreshLayout myItemsListViewLayout;
    @Bind(R.id.MyItemsListView)
    RecyclerView myItemsListView;
    private MyItemsListAdapter myItemsListAdapter;

    @Bind(R.id.NotificationText)
    TextView notificationText;

    public MyItemsFragment() {
    }

    public static MyItemsFragment newInstance() {
        MyItemsFragment fragment = new MyItemsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_items_fragment, container, false);
        ButterKnife.bind(this, rootView);

        myItemsListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        myItemsListView.setLayoutManager(layoutManager);
        myItemsListView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        myItemsListAdapter = new MyItemsListAdapter();
        myItemsListView.setAdapter(myItemsListAdapter);

        refreshData();

        myItemsListViewLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myItemsListViewLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        myItemsListViewLayout.setRefreshing(true);
                        refreshData();
                        myItemsListViewLayout.setRefreshing(false);
                    }
                });
            }
        });

        return rootView;
    }

    public void refreshData() {
        List<AuctionItem> auctionItemList = Database.auctionDAO.fetchAuctionItemsByUsername(UserSessionManager.getInstance().getLoggedInUser().getUsername());
        myItemsListAdapter.setItems(auctionItemList);
        myItemsListAdapter.notifyDataSetChanged();

        notificationText.setVisibility(auctionItemList.size() == 0 ? View.VISIBLE : View.GONE);
    }
}