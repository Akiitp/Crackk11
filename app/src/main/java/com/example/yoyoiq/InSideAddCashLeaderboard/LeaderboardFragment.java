package com.example.yoyoiq.InSideAddCashLeaderboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.yoyoiq.Model.LeaderboardPOJO;
import com.example.yoyoiq.R;
import com.example.yoyoiq.common.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String match_id1, contest_id1, matchA, matchB;
    String match_id, contest_id;
    RecyclerView recyclerView;
    ArrayList<LeaderboardPOJO> listItems = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    TextView totalTeam;
    SessionManager sessionManager;
    String url = "http://adminapp.tech/crack11/ItsMe/all_apis.php?func=get_leaderboard_users";

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    public static LeaderboardFragment newInstance(String param1, String param2) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    LeaderBoardAdapter leaderBoardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        totalTeam = root.findViewById(R.id.totalTeam);
        swipeRefreshLayout = root.findViewById(R.id.swiper);
        sessionManager = new SessionManager(getContext());
        try {
            getLeaderBoardData(match_id, contest_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            try {
                swipeRefreshLayout.setRefreshing(false);
                getLeaderBoardData(match_id, contest_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            try {
                matchA = getArguments().getString("matchA");
                matchB = getArguments().getString("matchB");
                match_id1 = getArguments().getString("match_id");
                contest_id1 = getArguments().getString("contestId");
                match_id = String.valueOf(match_id1);
                contest_id = String.valueOf(contest_id1);
                getLeaderBoardData(match_id, contest_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getLeaderBoardData(String match_id, String contest_id) throws JSONException {
        listItems.clear();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            JSONArray jsonArray1 = new JSONArray();
            JSONArray jsonArray = new JSONArray();
            try {
                listItems.clear();
                JSONObject jsonObject = new JSONObject(response);
                jsonArray1 = jsonObject.getJSONArray("users");
                totalTeam.setText("All Teams " + "( " + jsonArray1.length() + " )");
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                    try {
                        jsonArray = jsonObject1.getJSONArray("players_response");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String id = jsonObject1.getString("id");
                    String user_id = jsonObject1.getString("user_id");
                    String team_id = jsonObject1.getString("team_id");
                    String match_id2 = jsonObject1.getString("match_id");
                    String contest_id2 = jsonObject1.getString("contest_id");
                    String date_time = jsonObject1.getString("date_time");
                    String name = jsonObject1.getString("name");
                    String mobile = jsonObject1.getString("mobile");
                    String rank = String.valueOf((i + 1));

                    String total_points = jsonObject1.getString("total_points");
                    if (user_id.equalsIgnoreCase(sessionManager.getUserData().getUser_id())) {
                        listItems.add(0, new LeaderboardPOJO(id, user_id, team_id, match_id2, contest_id2, date_time, name, mobile, rank, total_points, jsonArray));
                    } else {
                        LeaderboardPOJO leaderboardPOJO = new LeaderboardPOJO(id, user_id, team_id, match_id2, contest_id2, date_time, name, mobile, rank, total_points, jsonArray);
                        listItems.add(leaderboardPOJO);
                    }

                }

                leaderBoardAdapter = new LeaderBoardAdapter(getContext(), listItems);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(leaderBoardAdapter);
                leaderBoardAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                swipeRefreshLayout.setRefreshing(false);
                e.printStackTrace();
            }
        }, error -> swipeRefreshLayout.setRefreshing(false)) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("match_id", match_id);
                params.put("contest_id", contest_id);
                return params;
            }
        };
        queue.add(request);
    }


    public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {
        Context context;
        ArrayList<LeaderboardPOJO> list;

        public LeaderBoardAdapter(Context context, ArrayList<LeaderboardPOJO> list) {
            this.context = context;
            this.list = list;
            sessionManager = new SessionManager(context.getApplicationContext());
        }

        @NonNull
        @Override
        public LeaderBoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_user_ranking_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LeaderBoardAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            LeaderboardPOJO listData = list.get(position);
            holder.setIsRecyclable(false);
            holder.userName.setText(listData.getName());
            holder.userTotalPoints.setText(String.valueOf(listData.getTotal_points()));
            holder.userRank.setText("# " + String.valueOf(listData.getRank()));
            if (listData.getUser_id().equalsIgnoreCase(sessionManager.getUserData().getUser_id())) {
                holder.linerLayout.setBackgroundColor(Color.parseColor("#F8F3E2"));
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView userName, userTotalPoints, userRank;
            ImageView userProfile;
            RelativeLayout relativeLayout;
            LinearLayout linerLayout;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                userProfile = itemView.findViewById(R.id.userProfile);
                userName = itemView.findViewById(R.id.userName);
                userRank = itemView.findViewById(R.id.userRank);
                userTotalPoints = itemView.findViewById(R.id.userTotalPoints);
                relativeLayout = itemView.findViewById(R.id.relativeLayout);
                linerLayout = itemView.findViewById(R.id.linerLayout);
            }
        }
    }
}