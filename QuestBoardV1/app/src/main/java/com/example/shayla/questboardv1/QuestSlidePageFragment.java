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
import entities.Quest;


/**
 * {@Link Fragment} that displaces information about each {@Link Quest}.
 * Activities that contain this fragment must implement the
 * {@link QuestSlidePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestSlidePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestSlidePageFragment extends Fragment {
    //Key values of the arguments that are passed to this Fragment from the QuestSlideActivity
    private static final String QUEST_NAME = "QUEST_NAME";
    private static final String QUEST_DESCRIPTION = "QUEST_DESCRIPTION";
    private static final String GIVER_NAME = "GIVER_NAME";
    private static final String GIVER_IMAGE = "GIVER_IMAGE";

    //Values to display on UI
    private String questName;
    private String questDescription;
    private String giverName;
    private String giverImage;

    //UI References
    TextView quest_name;
    TextView quest_description;
    TextView giver_name;
    ImageView giver_image;
    Toolbar toolbar;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param quest
     * @return A new instance of fragment QuestSlidePageFragment.
     */
    public static QuestSlidePageFragment newInstance(Quest quest) {
        QuestSlidePageFragment fragment = new QuestSlidePageFragment();
        Bundle args = new Bundle();
        //Sets values that would be displayed on UI
        args.putString(QUEST_NAME, quest.getName());
        args.putString(QUEST_DESCRIPTION, quest.getDescription());
        args.putString(GIVER_NAME, quest.getGiver().getName());
        args.putString(GIVER_IMAGE, quest.getGiver().getImage());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Required empty public constructor
     */
    public QuestSlidePageFragment() {
    }

    /**
     * Sets the local variables based on the arguments passed to it
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Set all the UI components. Load default input if null
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(getArguments().getString(QUEST_NAME)!=null) {
                questName = getArguments().getString(QUEST_NAME);
            } else { questName = getString(R.string.no_quest_name_available); }
            if(getArguments().getString(QUEST_DESCRIPTION)!=null && getArguments().getString(QUEST_DESCRIPTION).trim().length()!=0) {
                questDescription = getArguments().getString(QUEST_DESCRIPTION);
            } else { questDescription = getString(R.string.no_quest_description_available); }
            if(getArguments().getString(GIVER_NAME)!=null) {
                giverName = getArguments().getString(GIVER_NAME);
            } else { giverName = getString(R.string.no_giver_name_available); }
            if(getArguments().getString(GIVER_IMAGE)!=null && getArguments().getString(GIVER_IMAGE).trim().length()!=0) {
                giverImage = getArguments().getString(GIVER_IMAGE);
            } else { giverImage = getString(R.string.no_giver_image_available); }
        }
    }

    /**
     * Inflate fragment and update values on the fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_quest_slide_page, container, false);
        return updateQuestSlidePageFragment(rootView);
    }

    /**
     * Attach this {@Link Fragment} to an {@Link Activity} and ensure that the {@Link Activity}
     * implements OnFragmentInteractionListener()
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
     * Detaches this {@Link Fragment} from its Activity
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
    public ViewGroup updateQuestSlidePageFragment(ViewGroup mViewGroup) {
        quest_name = (TextView) mViewGroup.findViewById(R.id.quest_name);
        quest_name.setText(questName);
        quest_description = (TextView) mViewGroup.findViewById(R.id.quest_description);
        quest_description.setText(questDescription);
        giver_name = (TextView) mViewGroup.findViewById(R.id.giver_name);
        giver_name.setText(giverName);
        giver_image = (ImageView) mViewGroup.findViewById(R.id.giver_image);
        try {
            Picasso.with(mViewGroup.getContext())
                    .load(giverImage)
                    .into(giver_image);
        } catch(Exception e) {
            Picasso.with(mViewGroup.getContext())
                    .load(R.drawable.icon)  //Insert a default, no-image-available here
                    .into(giver_image);
        }
        toolbar = (Toolbar) mViewGroup.findViewById(R.id.questToolbar);
        toolbar.setTitle(questName);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setBackgroundResource(R.color.toolbar_background);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), KingdomsListActivity.class);
                startActivity(intent); //Navigate back to Kingdoms List
                getActivity().finish(); //Close this activity to prevent back button from navigating here
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
