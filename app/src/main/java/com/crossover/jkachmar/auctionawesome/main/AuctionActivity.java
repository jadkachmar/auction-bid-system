package com.crossover.jkachmar.auctionawesome.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.crossover.jkachmar.auctionawesome.R;
import com.crossover.jkachmar.auctionawesome.common.AuctionConstants;
import com.crossover.jkachmar.auctionawesome.fragments.BidsListFragment;
import com.crossover.jkachmar.auctionawesome.fragments.MyItemsFragment;
import com.crossover.jkachmar.auctionawesome.fragments.NewItemFragment;
import com.crossover.jkachmar.auctionawesome.fragments.WonBidsListFragment;
import com.crossover.jkachmar.auctionawesome.common.GlobalExceptionHandler;
import com.crossover.jkachmar.auctionawesome.receivers.RecurringTaskReceiver;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AuctionActivity extends AppCompatActivity {

    private static final long BOT_AUCTION_INTERVAL = 15 * 1000; //every minute

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.container)
    ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    private RecurringTaskReceiver recurringReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auction_activity);
        ButterKnife.bind(this);

        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler(this));

        initUI();
        registerReceivers();
        scheduleRecurringTasks();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);

        unregisterReceivers();
    }

    private void initUI() {
        setSupportActionBar(toolbar);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateFloatingButton(position);
            }

            @Override
            public void onPageSelected(int position) {
                updateFloatingButton(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(mViewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                NewItemFragment newItemFragment = new NewItemFragment();
                newItemFragment.show(ft, "dialog");
            }
        });
    }

    private void registerReceivers() {
        recurringReceiver = new RecurringTaskReceiver();
        recurringReceiver.setListener(this);

        IntentFilter recurringIntentFilter = new IntentFilter();
        recurringIntentFilter.addAction(AuctionConstants.AUCTION_PRIVATE_RECURRING_INTENT_FILTER);

        registerReceiver(recurringReceiver, recurringIntentFilter);
    }

    private void scheduleRecurringTasks() {
        Intent recurringIntent = new Intent();
        recurringIntent.setAction(AuctionConstants.AUCTION_PRIVATE_RECURRING_INTENT_FILTER);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar c = Calendar.getInstance();

        PendingIntent auctionBotUpdate = PendingIntent.getBroadcast(this, 1, recurringIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), BOT_AUCTION_INTERVAL, auctionBotUpdate);
    }

    private void unregisterReceivers() {
        unregisterReceiver(recurringReceiver);
    }

    public void refreshData() {
        mSectionsPagerAdapter.refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.auction_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent i = new Intent(AuctionActivity.this, LoginActivity.class);
            startActivity(i);

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateFloatingButton(int position) {
        switch (position) {
            case 0: {
                fab.setVisibility(View.VISIBLE);
                break;
            }
            case 1: {
                fab.setVisibility(View.GONE);
                break;
            }
            case 2: {
                fab.setVisibility(View.GONE);
                break;
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private MyItemsFragment myItemsFragment;
        private BidsListFragment bidsListFragment;
        private WonBidsListFragment wonBidsListFragment;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    myItemsFragment = MyItemsFragment.newInstance();
                    return myItemsFragment;
                }
                case 1: {
                    bidsListFragment = BidsListFragment.newInstance();
                    return bidsListFragment;
                }
                case 2: {
                    wonBidsListFragment = WonBidsListFragment.newInstance();
                    return wonBidsListFragment;
                }
                default:
                    return null; //or empty fragment
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "My Items";
                case 1:
                    return "Bid Now!";
                case 2:
                    return "Won Bids";
            }
            return null;
        }

        public void refreshData() {
            myItemsFragment.refreshData();
            bidsListFragment.refreshData();
        }
    }
}
