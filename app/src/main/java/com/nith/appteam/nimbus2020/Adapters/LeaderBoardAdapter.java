package com.nith.appteam.nimbus2020.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nith.appteam.nimbus2020.Models.LeaderboardModel;
import com.nith.appteam.nimbus2020.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LeaderBoardAdapter extends
        RecyclerView.Adapter<LeaderBoardAdapter.LeaderboardViewHolder> {

    List<LeaderboardModel> mLeaderboardModelList;
    Activity mActivity;

    public LeaderBoardAdapter(
            List<LeaderboardModel> leaderboardModelList, Activity activity) {
        mLeaderboardModelList = leaderboardModelList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.adapter_leaderboard, null);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {

        LeaderboardModel user = mLeaderboardModelList.get(position);
        holder.nameTextView.setText(user.getName());
        if (!user.getImageUrl().isEmpty()) {
            Picasso.with(mActivity).load(user.getImageUrl()).resize(80, 80).centerCrop().into(
                    holder.userImageView);
        }
        holder.scoreTextView.setText(user.getScore());

    }

    @Override
    public int getItemCount() {
        return mLeaderboardModelList.size();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {

        ImageView userImageView;
        TextView nameTextView;
        TextView scoreTextView;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);

            userImageView = itemView.findViewById(R.id.userImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }
}
