package com.example.rombe;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {

    private List<Ranking> rankingList;
    private String currentUserName;

    public RankingAdapter(List<Ranking> rankingList, String currentUserName) {
        this.rankingList = rankingList;
        this.currentUserName = currentUserName;
    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ranking, parent, false);
        return new RankingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingViewHolder holder, int position) {
        Ranking ranking = rankingList.get(position);
        holder.tvPosition.setText((position + 1) + ".");
        holder.tvPlayerName.setText(ranking.getNombre());
        holder.tvScore.setText(String.valueOf(ranking.getPuntaje()));

        // Resaltar al usuario actual con color Naranja (el de tu login)
        if (currentUserName != null && ranking.getNombre().equalsIgnoreCase(currentUserName)) {
            holder.tvPlayerName.setTextColor(Color.parseColor("#FF9800"));
            holder.tvScore.setTextColor(Color.parseColor("#FF9800"));
            holder.tvPosition.setTextColor(Color.parseColor("#FF9800"));
        } else {
            // Blanco para los demás para que se vea en el fondo oscuro
            holder.tvPlayerName.setTextColor(Color.WHITE);
            holder.tvScore.setTextColor(Color.WHITE);
            holder.tvPosition.setTextColor(Color.parseColor("#FF9800")); // Mantenemos el número en naranja
        }
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public static class RankingViewHolder extends RecyclerView.ViewHolder {
        TextView tvPosition, tvPlayerName, tvScore;

        public RankingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPosition = itemView.findViewById(R.id.tvPosition);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            tvScore = itemView.findViewById(R.id.tvScore);
        }
    }
}
