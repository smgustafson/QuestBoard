package com.example.shayla.questboardv1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Dialog box to display when a new user registers. Will be displayed only upon navigation from LoginActivity
 * to KingdomsListActivity
 */
public class WelcomeFragment extends DialogFragment {

    //UI References
    TextView text_message;

    //Content of the dialog fragment
    String mMessage;

    /**
     * The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     */
    public interface WelcomeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    WelcomeDialogListener mListener;

    /**
     * Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (WelcomeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement WelcomeDialogListener");
        }
    }

    /**
     * Set the UI attributes
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mMessage = getArguments().getString(getString(R.string.welcome_message));
        if(mMessage==null) mMessage = "Default welcome message...";
        //builder.setTitle(getArguments().getString(getString(R.string.welcome_message)));
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        ViewGroup mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_welcome, null);
        text_message = (TextView) mViewGroup.findViewById(R.id.welcome_text);
        text_message.setText(mMessage);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(mViewGroup)
                // Add action buttons
                .setPositiveButton(getString(R.string.welcome_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.setTitle(getString(R.string.sign_up_successful));
        return builder.create();
    }
}