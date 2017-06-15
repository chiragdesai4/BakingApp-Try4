package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Bake;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Chirag Desai on 12-06-2017.
 */

public class BakesAdapter extends RecyclerView.Adapter<BakesAdapter.ArticleViewHolder> {

    final private ListItemClickListener mOnClickListener;
    final private ArrayList<Bake> bakes;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public BakesAdapter(ListItemClickListener listener, ArrayList<Bake> bakes) {
        mOnClickListener = listener;
        this.bakes = bakes;
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
        return bakes.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder
            implements OnClickListener {

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
            if (!bakes.isEmpty()) {
                Picasso.with(itemView.getContext()).load(bakes.get(position).getImage()).into(icon);
                name.setText(bakes.get(position).getName());
                servings.setText(itemView.getContext().getString(R.string.servings) + " " + bakes.get(position).getServings());
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}