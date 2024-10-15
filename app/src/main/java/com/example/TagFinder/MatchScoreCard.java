package com.example.TagFinder;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.TagFinder.ModalOfApi.AdaptorOfMatch;
import com.example.TagFinder.ModalOfApi.MatchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchScoreCard extends AppCompatActivity {

    TextView textView, textid1, textid2, textid3;
    RecyclerView recyclerView_hr, horizontalRecycler1, horizontalRecycler2, horizontalRecycler3;

    private List<MatchResponse.TypeMatch> matchResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_score_card);

        textView = findViewById(R.id.textid);
        textid1 = findViewById(R.id.textid1);
        textid2 = findViewById(R.id.textid2);
        textid3 = findViewById(R.id.textid3);
        recyclerView_hr = findViewById(R.id.horizontalRecycler);
        horizontalRecycler1 = findViewById(R.id.horizontalRecycler1);
        horizontalRecycler2 = findViewById(R.id.horizontalRecycler2);
        horizontalRecycler3 = findViewById(R.id.horizontalRecycler3);

        getMatchInfo();
    }

    public void getMatchInfo() {
        Apiresponse apiresponse = RetrofitDataClass.getRetrofit1().create(Apiresponse.class);
        Call<MatchResponse> response = apiresponse.getMatchDetails();

        Log.e("response", "" + response.request());

        response.enqueue(new Callback<MatchResponse>() {
            @Override
            public void onResponse(Call<MatchResponse> call, Response<MatchResponse> response) {
                MatchResponse matchResponse = response.body();

                if (response.isSuccessful() && response.body() != null) {
                    matchResponseList = matchResponse.getTypeMatches();
                    Log.e("The size of list", ":= " + matchResponseList.size());

                    // Set match type title for the first type match
                    String matchType = matchResponseList.get(0).getMatchType();
                    textView.setText(matchType);
                    String matchType1 = matchResponseList.get(1).getMatchType();
                    textid3.setText(matchType1);
                    String matchType2 = matchResponseList.get(2).getMatchType();
                    textid1.setText(matchType2);
                    String matchType3 = matchResponseList.get(3).getMatchType();
                    textid2.setText(matchType3);

                    // Display matches for different types
                    setRecyclerViewForMatchType("International", recyclerView_hr);
                    setRecyclerViewForMatchType("Domestic", horizontalRecycler1);
                    setRecyclerViewForMatchType("Women", horizontalRecycler2);
                    setRecyclerViewForMatchType("League", horizontalRecycler3);

                } else {
                    Toast.makeText(MatchScoreCard.this, "response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MatchResponse> call, Throwable t) {
                Toast.makeText(MatchScoreCard.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerViewForMatchType(String matchType, RecyclerView recyclerView) {
        List<MatchResponse.TypeMatch> filteredList = filterMatchesByType(matchType);
        AdaptorOfMatch adaptorOfMatch = new AdaptorOfMatch(MatchScoreCard.this, filteredList,matchType);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MatchScoreCard.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adaptorOfMatch);
//        List<MatchResponse.TypeMatch> filteredList1 = filterMatchesByType(matchType);
//        AdaptorOfMatch adaptorOfMatch1 = new AdaptorOfMatch(MatchScoreCard.this, filteredList,"League");
//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(MatchScoreCard.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(layoutManager1);
//        recyclerView.setAdapter(adaptorOfMatch1);
    }

    private List<MatchResponse.TypeMatch> filterMatchesByType(String matchType) {
        List<MatchResponse.TypeMatch> filteredList = new ArrayList<>();
        for (MatchResponse.TypeMatch match : matchResponseList) {
            if (match.getMatchType().equalsIgnoreCase(matchType)) {
                filteredList.add(match);
            }
        }
        return filteredList;
    }
}
