package com.aldoborrero.tinderjs.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aldoborrero.tinderjs.R;
import com.aldoborrero.tinderjs.inject.CommunicationComponent;
import com.aldoborrero.tinderjs.ui.TinderJsApplication;
import com.aldoborrero.tinderjs.utils.Dimens;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreHolder> {

    private Context context;

    //private List<Items> items;

    private int itemWidth;
    private int itemHeight;
    private int margin;
    private int columns;

    @Inject
    Picasso picasso;

    public ExploreAdapter(Context context) {
        CommunicationComponent.Initializer.init((TinderJsApplication) context.getApplicationContext()).inject(this);
        this.context = context;
        int screenWidth = Dimens.getScreenWidth(this.context);
        columns = context.getResources().getInteger(R.integer.explore_grid_columns);
        itemWidth = itemHeight = (screenWidth / columns);
        margin = Dimens.dpToPixels(this.context, 2);
    }

    @Override
    public ExploreHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_explore_item, viewGroup, false);
        return new ExploreHolder(v, itemHeight);
    }

    @Override
    public void onBindViewHolder(ExploreHolder viewHolder, int position) {
        int doubleMargin = margin * 2;
        int topMargin = (position < columns) ? doubleMargin : margin;

        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) viewHolder.itemView.getLayoutParams();
        layoutParams.height = itemHeight;
        layoutParams.width = itemWidth;
        if (position % columns == 0) {
            layoutParams.setMargins(doubleMargin, topMargin, margin, margin);
        } else if (position % columns == columns - 1) {
            layoutParams.setMargins(margin, topMargin, doubleMargin, margin);
        } else {
            layoutParams.setMargins(margin, topMargin, margin, margin);
        }
        viewHolder.itemView.setLayoutParams(layoutParams);

        picasso.load("http://lorempixel.com/640/480/people")
                .resize(itemWidth, itemHeight)
                .centerCrop()
                .into(viewHolder.coverImageView);
    }

    @Override
    public int getItemCount() {
        return 101;
    }

    public void clear() {
    }

    static class ExploreHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.cover_image)
        ImageView coverImageView;

        @InjectView(R.id.name)
        TextView nameTextView;

        public ExploreHolder(View view, int minHeight) {
            super(view);
            ButterKnife.inject(this, view);
            coverImageView.setMinimumHeight(minHeight);
        }

    }

    public enum Type {
        NORMAL,
        LOADING
    }

}