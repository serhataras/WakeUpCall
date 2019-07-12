package com.groupnamenotfoundexception.wakeupcall.app.activities;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import com.groupnamenotfoundexception.wakeupcall.app.R;
import com.groupnamenotfoundexception.wakeupcall.app.activities.games.*;
import com.groupnamenotfoundexception.wakeupcall.app.fragments.ArcadeFragment;
import com.groupnamenotfoundexception.wakeupcall.app.fragments.HomeFragment;
import com.groupnamenotfoundexception.wakeupcall.app.fragments.StatsFragment;
import com.groupnamenotfoundexception.wakeupcall.app.games.Game;

import java.util.Locale;

/**
 * The activity that holds every Tab we include
 */
public class HomeActivity extends Activity implements ActionBar.TabListener{
    /** SUBCLASSES */
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        /**
         * CONSTRUCTOR
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Sets the fragments.
         *
         * @param position ,Positions of the tabs
         * @return The respective Fragment
         */
        @Override
        public Fragment getItem(int position) {
            // Return respective fragments according to Position
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new StatsFragment();
                case 2:
                    return new ArcadeFragment();
            }
            // If something goes wrong return null
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        /**
         * Sets the Tab Titles
         *
         * @param position ,Position of the tab
         * @return name of the tab
         */
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /** PROPERTIES */

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    // Shared Preferneces
    SharedPreferences alarms;
    SharedPreferences.Editor alarmsEditor;

    // Arcade Mode
    int temp = -1;

    /** METHODS */

    /**
     * Creates the view when called. Does the job of Constructor. Presented by Android, Overridden by Project Group
     *
     * @param savedInstanceState ,If the view has a saved instance - in this case there is none - loads this.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        // Sets the SharedPreferences
        alarms = getSharedPreferences(getString(R.string.alarms_master), Context.MODE_PRIVATE);
        alarmsEditor = alarms.edit();

        // Set Last Fragment
        if(getIntent().getExtras() != null)
            mViewPager.setCurrentItem(getIntent().getExtras().getInt("LastFragment",0));
    }

    /**
     * When an tab is selected this will be called
     *
     * @param tab ,selected tab
     * @param fragmentTransaction ,the animation
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    /**
     * When an tab is unselected this will be called
     *
     * @param tab ,unselected tab
     * @param fragmentTransaction ,the animation
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * When an tab is reselected this will be called
     *
     * @param tab ,reselected tab
     * @param fragmentTransaction ,the animation
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * Called by Floating Action Button, adds new Alarm
     *
     * @param view ,View that called the Method - Required for in XML Method Binding
     */
    public void addAlarm(View view) {
        // Create the intent
        Intent intent = new Intent( this, AlarmEditorActivity.class);

        // Current Number of Alarms
        int currentAlarmCount = alarms.getInt(getString(R.string.number_of_alarms), 1);

        // Call a new alarm with index as the number of the alarms
        intent.putExtra(getString(R.string.alarm_index), currentAlarmCount);

        // Increment the number of Alarms and put it in SharedPreferences
        alarmsEditor.putInt(getString(R.string.number_of_alarms), ++currentAlarmCount).commit();

        // Start the Activity
        startActivity(intent);
    }

    /**
     * Called by Floating Action Button, adds new Alarm
     *
     * @param view ,View that called the Method - Required for in XML Method Binding
     */
    public void openGameList(View view) {
        // Create the intent
        Intent intent = new Intent( this, GameListActivity.class);

        // Start the Activity
        startActivity(intent);
    }

    /**
     * Called by Floating Action Button, adds new Alarm
     *
     * @param view ,View that called the Method - Required for in XML Method Binding
     */
    public void openArcade(View view) {
        Intent intent;

        int randomNum = ((int) (Math.random() * 5));


        if (randomNum == 0) {
            intent = new Intent(this, SimonActivity.class);
        } else if (randomNum == 1) {
            intent = new Intent(this, MatchTheObjectsActivity.class);
        } else if (randomNum == 2) {
            intent = new Intent(this, ShakeTheDeviceActivity.class);
        } else if (randomNum == 3){
            intent = new Intent(this, PressTheBlueActivity.class);
        } else {
            intent = new Intent(this, LazyKillerActivity.class);
        }

        intent.putExtra("DIFFICULTY", Game.DIFFICULTY_MEDIUM);
        intent.putExtra("REQUESTER", Game.REQUESTER_RANDOM_ARCADE);

        startActivity(intent);
    }

    /**
     * When a Hardware button is clicked this will be called
     *
     * @param keyCode ,Code of the button or key that is pressed
     * @param event   ,KeyEvent
     * @return Indicate success of the operation
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // if button is menu button show Credits Page
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            startActivity(new Intent(this, CreditsActivity.class));
            return true;
        }
        // if it is not do normal job of the key
        return super.onKeyDown(keyCode, event);
    }
}