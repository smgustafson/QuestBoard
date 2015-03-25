package com.example.shayla.questboardv1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import entities.Kingdom;


/**
 * Fragment to display the details of a specific {@Link Kingdom}
 * Activities that contain this fragment must implement the
 * {@link KingdomDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KingdomDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KingdomDetailsFragment extends Fragment {
    //Key values of the arguments that are passed to this Fragment from the QuestSlideActivity
    private static final String KINGDOM_NAME = "KINGDOM_NAME";
    private static final String KINGDOM_IMAGE = "KINGDOM_IMAGE";
    private static final String KINGDOM_CLIMATE = "KINGDOM_CLIMATE";
    private static final String KINGDOM_POPULATION = "KINGDOM_POPULATION";

    //Variable for the text that will be displayed on the page
    private String mName = "default name";
    private String mImage = "default image";
    private String mClimate = "default climate";
    private int mPopulation = 100;

    //UI References
    TextView kingdom_name;
    ImageView kingdom_image;
    TextView kingdom_climate;
    TextView kingdom_population;
    Toolbar toolbar;

    private OnFragmentInteractionListener mListener;

    /**
     * Creates a new instance of {@Link KingdomDetailsFragment} based on the details from the param {@Link Kingdom}
     *
     * @param {@Link Kingdom} kingdom
     * @return A new instance of fragment KingdomDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KingdomDetailsFragment newInstance(Kingdom kingdom) {
        KingdomDetailsFragment fragment = new KingdomDetailsFragment();
        Bundle args = new Bundle();
        args.putString(KINGDOM_NAME, kingdom.getName());
        args.putString(KINGDOM_IMAGE, kingdom.getImage());
        args.putString(KINGDOM_CLIMATE, kingdom.getClimate());
        args.putInt(KINGDOM_POPULATION, kingdom.getPopulation());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Required empty public constructor
     */
    public KingdomDetailsFragment() {
    }

    /**
     * Sets the local variables based on the arguments passed to it
     *
     * @param {@Link Bundle} savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(getArguments().getString(KINGDOM_NAME)!=null)mName = getArguments().getString(KINGDOM_NAME);
            if(getArguments().getString(KINGDOM_IMAGE)!=null && getArguments().getString(KINGDOM_IMAGE).trim().length()!=0)
                mImage = getArguments().getString(KINGDOM_IMAGE);
            if(getArguments().getString(KINGDOM_CLIMATE)!=null)mClimate = getArguments().getString(KINGDOM_CLIMATE);
            mPopulation = getArguments().getInt(KINGDOM_POPULATION);
        }
    }

    /**
     * Inflate the layout for this fragment and updates the elements on this fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return {@Link View}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_kingdom_details, container, false);
        return updateKingdomDetailsFragment(mViewGroup);
    }

    /**
     * Attaches this Fragment to an Activity.
     * Ensures that the Activity implements OnFragmentInteractionListener
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Detaches this Fragment from its Activity
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * Receives a {@Link ViewGroup}. Updates all elements on the Fragment and returns ViewGroup
     *
     * @param mViewGroup
     * @return ViewGroup
     */
    public ViewGroup updateKingdomDetailsFragment(ViewGroup mViewGroup) {
        kingdom_name = (TextView) mViewGroup.findViewById(R.id.kingdom_name);
        kingdom_name.setText(mName);
        kingdom_image = (ImageView) mViewGroup.findViewById(R.id.kingdom_image);
        Picasso.with(mViewGroup.getContext())
                .load(mImage)
                .into(kingdom_image);
        kingdom_climate = (TextView) mViewGroup.findViewById(R.id.kingdom_climate);
        kingdom_climate.setText(mClimate);
        kingdom_population = (TextView) mViewGroup.findViewById(R.id.kingdom_population);
        kingdom_population.setText("" + mPopulation);
        toolbar = (Toolbar) mViewGroup.findViewById(R.id.detailsToolbar);
        toolbar.setTitle(mName);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setBackgroundResource(R.color.toolbar_background);
        //toolbar.setLogo(R.drawable.icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), KingdomsListActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return mViewGroup;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
