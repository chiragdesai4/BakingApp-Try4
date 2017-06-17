package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import static com.example.android.bakingapp.ui.BakesFragment.bakes;

/**
 * Created by Chirag Desai on 14-06-2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ArticleViewHolder> {

    final private ListItemClickListener mOnClickListener;
    final private ArrayList<Step> steps;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public StepsAdapter(ListItemClickListener listener, ArrayList<Step> steps) {
        mOnClickListener = listener;
        this.steps = steps;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ArticleViewHolder viewHolder = new ArticleViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView icon;
        TextView name;
        TextView servings;


        public ArticleViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            servings = (TextView) itemView.findViewById(R.id.servings);
            itemView.setOnClickListener(this);
        }

        void onBind(int position) {
            if (!steps.isEmpty()) {
                if(!steps.get(position).getThumbnailURL().isEmpty()) {
                    Picasso.with(itemView.getContext()).load(steps.get(position).getThumbnailURL()).error(R.drawable.ic_insert_image).into(icon);
                    name.setText(bakes.get(position).getName());
                    servings.setText(itemView.getContext().getString(R.string.servings) + " " + bakes.get(position).getServings());
                }else{
                    icon.setImageResource(R.drawable.ic_insert_image);
                }
                name.setText(steps.get(position).getShortDescription());
                servings.setText(itemView.getContext().getString(R.string.step)+" "+steps.get(position).getId());
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}