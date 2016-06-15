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
import com.crossover.jkachmar.auctionawesome.adapters.WonBidsListAdapter;
import com.crossover.jkachmar.auctionawesome.db.Database;
import com.crossover.jkachmar.auctionawesome.common.DividerItemDecoration;
import com.crossover.jkachmar.auctionawesome.models.AuctionItem;
import com.crossover.jkachmar.auctionawesome.session.UserSessionManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WonBidsListFragment extends Fragment {

    @Bind(R.id.WonBidsListViewLayout)
    SwipeRefreshLayout wonBidsListViewLayout;
    @Bind(R.id.WonBidsListView)
    RecyclerView wonBidsListView;
    private WonBidsListAdapter wonBidsListAdapter;

    @Bind(R.id.NotificationText)
    TextView notificationText;

    public WonBidsListFragment() {
    }

    public static WonBidsListFragment newInstance() {
        WonBidsListFragment fragment = new WonBidsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.won_bids_list_fragment, container, false);
        ButterKnife.bind(this, rootView);

        wonBidsListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        wonBidsListView.setLayoutManager(layoutManager);
        wonBidsListView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        wonBidsListAdapter = new WonBidsListAdapter();
        wonBidsListView.setAdapter(wonBidsListAdapter);

        refreshData();

        wonBidsListViewLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wonBidsListViewLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        wonBidsListViewLayout.setRefreshing(true);
                        refreshData();
                        wonBidsListViewLayout.setRefreshing(false);
                    }
                });
            }
        });

        return rootView;
    }

    public void refreshData() {
        List<AuctionItem> auctionItemList = Database.auctionDAO.fetchWonAuctionItems(UserSessionManager.getInstance().getLoggedInUser().getUsername());
        wonBidsListAdapter.setItems(auctionItemList);
        wonBidsListAdapter.notifyDataSetChanged();

        notificationText.setVisibility(auctionItemList.size() == 0 ? View.VISIBLE : View.GONE);
    }
}