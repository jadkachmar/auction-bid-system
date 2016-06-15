package com.crossover.jkachmar.auctionawesome.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crossover.jkachmar.auctionawesome.R;
import com.crossover.jkachmar.auctionawesome.adapters.BidItemsListAdapter;
import com.crossover.jkachmar.auctionawesome.db.Database;
import com.crossover.jkachmar.auctionawesome.common.DividerItemDecoration;
import com.crossover.jkachmar.auctionawesome.common.AuctionUtils;
import com.crossover.jkachmar.auctionawesome.models.AuctionItem;
import com.crossover.jkachmar.auctionawesome.session.UserSessionManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BidsListFragment extends Fragment {

    @Bind(R.id.BidsListViewLayout)
    SwipeRefreshLayout bidsListViewLayout;
    @Bind(R.id.BidsListView)
    RecyclerView bidsListView;
    private BidItemsListAdapter bidsListAdapter;

    @Bind(R.id.NotificationText)
    TextView notificationText;

    public BidsListFragment() {
    }

    public static BidsListFragment newInstance() {
        BidsListFragment fragment = new BidsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.bids_list_fragment, container, false);
        ButterKnife.bind(this, rootView);

        bidsListView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        bidsListView.setLayoutManager(layoutManager);
        bidsListView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        bidsListAdapter = new BidItemsListAdapter();
        bidsListView.setAdapter(bidsListAdapter);

        bidsListAdapter.setOnItemClickListener(new BidItemsListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                refreshData();

                AuctionItem selectedItem = bidsListAdapter.getItemAt(position);
                if(AuctionUtils.hasExpired(selectedItem.getExpiresIn())){
                    Toast.makeText(getContext(), "Item has expired. Swipe left to check the results", Toast.LENGTH_LONG).show();
                    return;
                }

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ItemDetailFragment newItemFragment = ItemDetailFragment.newInstance(selectedItem);

                newItemFragment.show(ft, "dialog");
            }
        });

        bidsListViewLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bidsListViewLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        bidsListViewLayout.setRefreshing(true);
                        refreshData();
                        bidsListViewLayout.setRefreshing(false);
                    }
                });
            }
        });

        refreshData();

        return rootView;
    }


    public void refreshData() {
        List<AuctionItem> auctionItemList = Database.auctionDAO.fetchAvailableAuctionItems(UserSessionManager.getInstance().getLoggedInUser().getUsername());
        bidsListAdapter.setItems(auctionItemList);
        bidsListAdapter.notifyDataSetChanged();

        notificationText.setVisibility(auctionItemList.size() == 0 ? View.VISIBLE : View.GONE);
    }
}