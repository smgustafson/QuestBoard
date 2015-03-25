package com.example.shayla.questboardv1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import entities.Kingdom;
import entities.Quest;
import rest.RestClientSpecific;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Displays details of Kingdoms and their Quests through {@Link KingdomDetailsFragment} and {@Link QuestSlidePageFragment}
 */
public class QuestSlideActivity extends FragmentActivity implements QuestSlidePageFragment.OnFragmentInteractionListener, KingdomDetailsFragment.OnFragmentInteractionListener {
    private Kingdom mKingdom;

    //Variables will be set upon creation
    private int NUM_PAGES = 1; //arbitrary default for the number of pages
    private int kingdom_id = 1; //arbitrary default for the id of the selected {@Link Kingdom}

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    /**
     * Gets the id of the selected {@Link Kingdom} and sets the content view.
     * Gets the specific {@Link Kingdom}
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get the id passed from previous activity
        Intent intent = getIntent();
        kingdom_id = intent.getIntExtra("KINGDOM_ID", 1);

        setContentView(R.layout.activity_quest_slide);

        //Get the specific Kingdom based on the kingdom_id
        getSpecificKingdom();
    }

    /**
     * Handle the back button action
     */
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the KingdomDetailsFragment, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous QuestSlidePageFragment or the KingdomDetailsFragment .
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    /**
     * A simple pager adapter that creates a {@Link KingdomDetailsFragment} followed by n number of
     * {@Link QuestSlidePageFragment}, where n equals the number of Quests in the selected kingdom
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                KingdomDetailsFragment frag = new KingdomDetailsFragment();
                Bundle bundle = new Bundle();
                //Add all of the information that will be needed on the new fragment
                bundle.putString("KINGDOM_NAME", mKingdom.getName());
                bundle.putString("KINGDOM_IMAGE", mKingdom.getImage());
                bundle.putString("KINGDOM_CLIMATE", mKingdom.getClimate());
                bundle.putInt("KINGDOM_POPULATION", mKingdom.getPopulation());
                frag.setArguments(bundle);
                return frag;
            } else {
                QuestSlidePageFragment frag = new QuestSlidePageFragment();
                Quest q = mKingdom.getQuests().get(position - 1); //Creates a fragment for each Quest
                Bundle bundle = new Bundle();
                //Add all of hte information that will be needed on the new fragment
                bundle.putString("QUEST_NAME", q.getName());
                bundle.putString("QUEST_DESCRIPTION", q.getDescription());
                bundle.putString("GIVER_NAME", q.getGiver().getName());
                bundle.putString("GIVER_IMAGE", q.getGiver().getImage());
                frag.setArguments(bundle);
                return frag;
            }
        }

        /**
         * Set number of pages to the number of Quests + 1 (one for the Kingdom details, one for each Quest)
         *
         * @return int
         */
        @Override
        public int getCount() {
            NUM_PAGES = mKingdom.getQuests().size() + 1;
            return NUM_PAGES;
        }
    }

    /**
     * Uses RestClient to get the specific {@Link Kingdom} based on the id
     */
    private void getSpecificKingdom() {
        RestClientSpecific rest = new RestClientSpecific();
        Callback callback = new Callback<Kingdom>() {
            @Override
            public void success(Kingdom kingdom, Response response) {
                mKingdom = kingdom;
                // Now that we have your kingdom, instantiate a ViewPager and a PagerAdapter.
                mPager = (ViewPager) findViewById(R.id.pager);
                mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
                mPager.setAdapter(mPagerAdapter);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("", "error in getting specific kingdom from database: " + retrofitError.getMessage());
            }
        };
        rest.getSpecificKingdomInterface().kingdoms(kingdom_id, callback);
    }
}