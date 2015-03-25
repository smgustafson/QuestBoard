package com.example.shayla.questboardv1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import entities.Kingdom;

/**
 * Created by Shayla on 3/16/2015.
 *
 * Manages the list of all {@Link Kingdom} displayed in {@Link RecyclerView}, creating a {@Link ViewHolder} for each
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    public List<Kingdom> mDataset;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //UI References
        public ImageView mImageView;
        public TextView mTextView;
        public int mKingdomId;

        private ClickListener clickListener;

        /**
         * Construct a {@Link ViewHolder}, passing in a {@Link View}, finds the UI elements
         *
         * @param v
         */
        public ViewHolder(View v) {
            super(v);
            mImageView = (ImageView) v.findViewById(R.id.imageView);
            mTextView = (TextView) v.findViewById(R.id.textView);

            v.setOnClickListener(this);
        }

        /* Interface for handling clicks */
        public interface ClickListener {

            /**
             * Called when the view is clicked.
             *
             * @param v view that is clicked
             * @param position of the clicked item
             */
            public void onClick(View v, int position, int kingdom_id);

        }

        /* Setter for listener. */
        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        /**
         * Calls this.clickListener when this {#Link ViewHolder} is clicked on
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition(), mKingdomId);
        }

    }

    /**
     * Default constructor
     */
    public RecyclerViewAdapter() {
    }

    /**
     * constructor
     *
     * @param context
     */
    public RecyclerViewAdapter(Context context) {
        mContext = context;
    }

    /**
     * Provide a suitable constructor (depends on the kind of dataset)
     *
     * @param myDataset
     */
    public RecyclerViewAdapter(List<Kingdom> myDataset) {
        mDataset = myDataset;
    }

    /**
     * Creates new views (invoked by the layout manager)
     *
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_kingdom_list_view, parent, false);
        // set the view's size, margins, paddings and layout parameters...
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * Binds the {@Link ViewHolder} to this adapter
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mTextView.setText(mDataset.get(i).getName());
        //Display image based on URL reference
        Picasso.with(mContext)
                .load(mDataset.get(i).getImage())
                .into(viewHolder.mImageView);
        viewHolder.mKingdomId = mDataset.get(i).getId();
        viewHolder.setClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onClick(View v, int pos, int kingdom_id) {
                Intent intent = new Intent(mContext, QuestSlideActivity.class);
                intent.putExtra("KINGDOM_ID", kingdom_id);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * Return the size of the dataset (invoked by the layout manager)
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}