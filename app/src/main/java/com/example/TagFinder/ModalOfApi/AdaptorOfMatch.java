package com.example.TagFinder.ModalOfApi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TagFinder.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptorOfMatch extends RecyclerView.Adapter<AdaptorOfMatch.ViewMatch> {
    Context context;
    List<MatchResponse.TypeMatch.SeriesMatches.SeriesAdWrapper.Matches> filteredMatchesList;

    // Constructor that takes the match type as a filter
    public AdaptorOfMatch(Context context, List<MatchResponse.TypeMatch> matchResponseList, String matchType) {
        this.context = context;
        filteredMatchesList = new ArrayList<>();

        // Flatten the data by filtering matches based on the given match type
        for (MatchResponse.TypeMatch typeMatch : matchResponseList) {
            if (matchType.equals(typeMatch.getMatchType())) {
                for (MatchResponse.TypeMatch.SeriesMatches seriesMatch : typeMatch.getSeriesMatches()) {
                    MatchResponse.TypeMatch.SeriesMatches.SeriesAdWrapper seriesAdWrapper = seriesMatch.getSeriesAdWrapper();
                    if (seriesAdWrapper != null) {
                        // Add all matches from this series to the filteredMatchesList
                        filteredMatchesList.addAll(seriesAdWrapper.getMatches());
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewMatch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.match_viewcard, parent, false);
        return new ViewMatch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMatch holder, int position) {
        // Get the match at the current position
        MatchResponse.TypeMatch.SeriesMatches.SeriesAdWrapper.Matches match = filteredMatchesList.get(position);
        holder.typeMatch.setText(match.getMatchInfo().getSeriesName());
        holder.result.setText(match.getMatchInfo().getStatus());
        // Set team names
        holder.TeamName1.setText(match.getMatchInfo().getTeam1().getTeamSName());
        holder.TeamName2.setText(match.getMatchInfo().getTeam2().getTeamSName());

        // Set scores if available
        MatchResponse.TypeMatch.SeriesMatches.SeriesAdWrapper.Matches.MatchScore score = match.getMatchScore();
        if (score != null) {
            holder.run1.setText(score.getTeam1Score().getInngs1().getRuns() + "/" + score.getTeam1Score().getInngs1().getWickets());
            holder.run2.setText(score.getTeam2Score().getInngs1().getRuns() + "/" + score.getTeam2Score().getInngs1().getWickets());
            holder.over1.setText("(" + score.getTeam1Score().getInngs1().getOvers() + ")");
            holder.over2.setText("(" + score.getTeam2Score().getInngs1().getOvers() + ")");
        } else {
            // Default values if no score is available
            holder.run1.setText("N/A");
            holder.run2.setText("N/A");
            holder.over1.setText("-");
            holder.over2.setText("-");
        }
    }

    @Override
    public int getItemCount() {
        return filteredMatchesList.size(); // Return the size of the filtered match list
    }

    public class ViewMatch extends RecyclerView.ViewHolder {
        TextView typeMatch,TeamName1, run1, over1, TeamName2, run2, over2,result;

        public ViewMatch(@NonNull View itemView) {
            super(itemView);
            TeamName1 = itemView.findViewById(R.id.TeamName1);
            run1 = itemView.findViewById(R.id.run1);
            over1 = itemView.findViewById(R.id.over1);
            TeamName2 = itemView.findViewById(R.id.TeamName2);
            run2 = itemView.findViewById(R.id.run2);
            over2 = itemView.findViewById(R.id.over2);
            typeMatch = itemView.findViewById(R.id.typeMatch);
            result = itemView.findViewById(R.id.result);
        }
    }
}
