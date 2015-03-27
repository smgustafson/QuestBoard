package com.example.shayla.questboardv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import entities.Kingdom;
import rest.RestClientKingdoms;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Displays a list of all Kingdoms available and allows user to select one
 */
public class KingdomsListActivity extends ActionBarActivity implements WelcomeFragment.WelcomeDialogListener {
    //UI References
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar toolbar;

    private String emailAddress = null;
    private String savedCallbackMessage;
    List<Kingdom> list = new ArrayList<Kingdom>();

    /**
     * Determine if user is logged in. Navigate to {@Link LoginActivity} if not logged in.
     * Set UI elements.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = getSharedPreferences(getString(R.string.saved_info), 0);
        String savedEmail = settings.getString(getString(R.string.email_address), null); //Get saved email address, default null
        String savedCallbackMessage = settings.getString(getString(R.string.callback_message), null);
        if(savedEmail==null) { //If no saved email address, navigate to {@Link LoginActivity}
            settings = getSharedPreferences(getString(R.string.saved_info), 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(getString(R.string.is_new_user), true);
            editor.commit();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent); //Navigate to LoginActivity
            finish(); //Close this activity so back button does not bring user to logged in screen
        } else {
            emailAddress = savedEmail;
            this.savedCallbackMessage = savedCallbackMessage;
        }

        setContentView(R.layout.activity_kingdoms_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_kingdoms_list);
        toolbar.setTitle(emailAddress);
        toolbar.setLogo(R.drawable.icon_no_background);
        toolbar.setBackgroundResource(R.color.toolbar_background);
        //Toolbar will now take on default Action Bar characteristics
        setSupportActionBar(toolbar);
        //You can now use and reference the ActionBar

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //Changes in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        //Uses a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(this);

        //Access the list of Kingdoms from the database
        getDataSet();

        //Display the WelcomeFragment dialog box if this is a new user
        if(settings.getBoolean(getString(R.string.is_new_user), false)) {
            WelcomeFragment welcomeFragment = new WelcomeFragment();
            Bundle args = new Bundle();
            //Sets values that would be displayed on UI
            args.putString(getString(R.string.welcome_message), this.savedCallbackMessage);
            welcomeFragment.setArguments(args);
            welcomeFragment.show(getSupportFragmentManager(), "WelcomeDialogFragment");
        }
    }

    /**
     * Inflate the menu; add items to action bar
     *
     * @param menu
     * @return Boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kingdoms_list, menu);
        return true;
    }

    /**
     *
     * Handle action bar item clicks here. The action bar will
     * automatically handle clicks on the Home/Up button.
     *
     * @param item
     * @return Boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            emailAddress = null;
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish(); //Close this activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Save the current login state
     */
    @Override
    protected void onStop(){
        super.onStop();
        //indicate that the user has logged in before (to prevent dialog box) and save email address
        SharedPreferences settings = getSharedPreferences(getString(R.string.saved_info), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(getString(R.string.email_address), emailAddress);//Save the email address so the user remains logged in
        editor.putBoolean(getString(R.string.is_new_user), false);//This is no longer a 'new' user
        editor.commit();
    }

    /**
     * Async method to get the list of {#Link Kingdom}
     */
    private void getDataSet() {
        RestClientKingdoms rest = new RestClientKingdoms();
        Callback callback = new Callback<List<Kingdom>>() {
            @Override
            public void success(List<Kingdom> kingdoms, Response response) {
                list = kingdoms;
                mAdapter.mDataset = kingdoms; //finish setting up the activity after the async call is complete
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("", "error in getting kingdoms from database: " + retrofitError.getMessage());
            }
        };
        //Call the REST Client to set the Kingdoms list
        rest.getKingdomsInterface().kingdoms(callback);
    }

    /**
     * Handle click of WelcomeFragment dialog box. Indicate that this is no longer a new user
     *
     * @param dialog
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        SharedPreferences settings = getSharedPreferences(getString(R.string.saved_info), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(getString(R.string.is_new_user), false);
        editor.commit(); //save changes
        dialog.dismiss(); //close dialog box
    }
}