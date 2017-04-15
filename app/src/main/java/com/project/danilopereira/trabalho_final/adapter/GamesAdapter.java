package com.project.danilopereira.trabalho_final.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.danilopereira.trabalho_final.R;
import com.project.danilopereira.trabalho_final.model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danilopereira on 10/04/17.
 */

public class GamesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Game> games = new ArrayList<>();
    private GamesOnClickListener gamesOnClickListener;

    public GamesAdapter(Context context, List<Game> games, GamesOnClickListener gamesOnClickListener){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.games = games;
        this.gamesOnClickListener = gamesOnClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return new GameItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GameItemHolder itemHolder = (GameItemHolder) holder;

        itemHolder.tvGameName.setText(String.valueOf(games.get(position).getName()));
        itemHolder.tvCategory.setText(String.valueOf(games.get(position).getCategory().getName()));
        itemHolder.ivCover.setImageBitmap(BitmapFactory
                .decodeFile(games.get(position).getCover()));

        itemHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                gamesOnClickListener.onLongClickCell(itemHolder.itemView, position);

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    private class GameItemHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvGameName;
        TextView tvCategory;


        public GameItemHolder(View itemView) {
            super(itemView);

            ivCover = (ImageView) itemView.findViewById(R.id.ivCover);
            tvGameName = (TextView) itemView.findViewById(R.id.tvGameName);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
        }
    }

    public interface GamesOnClickListener{
        void onLongClickCell(View view, int posicao);
    }
}
