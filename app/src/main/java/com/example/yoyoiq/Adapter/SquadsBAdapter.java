package com.example.yoyoiq.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yoyoiq.CreateTeamActivity;
import com.example.yoyoiq.InSideContestActivityFragments.AllSelectedPlayerFromServer;
import com.example.yoyoiq.Model.AllSelectedPlayer;
import com.example.yoyoiq.Model.SquadsA;
import com.example.yoyoiq.R;
import com.example.yoyoiq.common.HelperData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SquadsBAdapter extends RecyclerView.Adapter<SquadsBAdapter.MyViewHolder> {
    Context context;
    ArrayList<SquadsA> list;
    private List<AllSelectedPlayerFromServer> allSelectedPlayer = new ArrayList<>();

    public SquadsBAdapter(Context context, ArrayList<SquadsA> list, List<AllSelectedPlayerFromServer> allSelectedPlayer) {
        this.context = context;
        this.list = list;
        this.allSelectedPlayer = allSelectedPlayer;
    }

    @NonNull
    @Override
    public SquadsBAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_players, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SquadsBAdapter.MyViewHolder holder, int position) {
        SquadsA listData = list.get(position);
        holder.setIsRecyclable(false);
        holder.playerName.setText(listData.getShort_namePlayers());
        holder.playerPoints.setText(listData.getAvg_points());
        holder.playerCredit.setText(listData.getFantasy_player_rating());
        holder.country.setText(listData.getAbbr());

        if (CreateTeamActivity.addedPlayerIds.contains("" + list.get(position).getPidPlayers() + "")) {
            holder.cardViewSelected.setBackgroundColor(Color.LTGRAY);
            holder.im_AddPlayer.setImageResource(R.drawable.minus_icon);
        } else {
            holder.cardViewSelected.setBackgroundColor(Color.WHITE);
            holder.im_AddPlayer.setImageResource(R.drawable.plus_icon);
        }

        if (listData.getPlaying11() == true) {
            holder.isPlaying.setText("Playing");
        }

        if (listData.getAbbr() == HelperData.team1NameShort) {
            Glide.with(context).load(HelperData.logoUrlTeamA).into(holder.playerImg);
        } else {
            Glide.with(context).load(HelperData.logoUrlTeamB).into(holder.playerImg);
        }

      /*  if (allSelectedPlayer.size() > 0) {
            if (listData.isSelected() == true) {
                if (HelperData.team1NameShort == listData.getAbbr()) {
                    CreateTeamActivity.addedPlayerIds = CreateTeamActivity.addedPlayerIds + "_" + listData.getPidPlayers() + "_\n";
                    HelperData.conty1.setValue(HelperData.conty1.getValue() + 1);
                    HelperData.bat.setValue(HelperData.bat.getValue() + 1);
                    HelperData.creditCounter.setValue(HelperData.creditCounter.getValue() - Double.valueOf(listData.getFantasy_player_rating()));
                    HelperData.playerCounter.setValue(HelperData.playerCounter.getValue() + 1);
                    holder.im_AddPlayer.setImageResource(R.drawable.minus_icon);
                    holder.cardViewSelected.setBackgroundColor(Color.LTGRAY);
                    AllSelectedPlayer allSelectedPlayer = new AllSelectedPlayer(Integer.valueOf(listData.getPidPlayers()), HelperData.matchId, listData.getShort_namePlayers(), listData.getAbbr(), "BAT", Double.valueOf(listData.getFantasy_player_rating()), false, false, false, "");
                    HelperData.allSelectedPlayer.setValue(Collections.singletonList(allSelectedPlayer));
                    HelperData.myTeamList.add(allSelectedPlayer);
                } else if (HelperData.team2NameShort == listData.getAbbr()) {
                    CreateTeamActivity.addedPlayerIds = CreateTeamActivity.addedPlayerIds + "_" + listData.getPidPlayers() + "_\n";
                    HelperData.conty2.setValue(HelperData.conty2.getValue() + 1);
                    HelperData.bat.setValue(HelperData.bat.getValue() + 1);
                    HelperData.creditCounter.setValue(HelperData.creditCounter.getValue() - Double.valueOf(listData.getFantasy_player_rating()));
                    HelperData.playerCounter.setValue(HelperData.playerCounter.getValue() + 1);
                    holder.im_AddPlayer.setImageResource(R.drawable.minus_icon);
                    holder.cardViewSelected.setBackgroundColor(Color.LTGRAY);
                    AllSelectedPlayer allSelectedPlayer = new AllSelectedPlayer(Integer.valueOf(listData.getPidPlayers()), HelperData.matchId, listData.getShort_namePlayers(), listData.getAbbr(), "BAT", Double.valueOf(listData.getFantasy_player_rating()), false, false, false, "");
                    HelperData.allSelectedPlayer.setValue(Collections.singletonList(allSelectedPlayer));
                    HelperData.myTeamList.add(allSelectedPlayer);
                }
            }
        }*/

        /*please not Remove this commented code code*/
//        if(listData.getPlaying11()==false){
//            holder.isPlaying.setText(" Not Playing");
//            holder.isPlaying.setTextColor(Color.RED);
//        }

        holder.cardViewSelected.setOnClickListener(view -> {
            if (CreateTeamActivity.addedPlayerIds.contains("_" + list.get(position).getPidPlayers() + "_")) {
                //removed minus sign
                String credit = listData.getFantasy_player_rating();
                double playerCredit = Double.parseDouble(credit);
                int pid = Integer.parseInt(listData.getPidPlayers());
                holder.im_AddPlayer.setImageResource(R.drawable.plus_icon);
                CreateTeamActivity.addedPlayerIds = CreateTeamActivity.addedPlayerIds.replace("_" + list.get(position).getPidPlayers() + "_\n", "");
                HelperData.bat.setValue(HelperData.bat.getValue() - 1);
                HelperData.creditCounter.setValue(Double.valueOf(HelperData.creditCounter.getValue() + playerCredit));
                HelperData.playerCounter.setValue(HelperData.playerCounter.getValue() - 1);
                if (HelperData.team1NameShort == listData.getAbbr()) {
                    HelperData.conty1.setValue(HelperData.conty1.getValue() - 1);
                } else if (HelperData.team2NameShort == listData.getAbbr()) {
                    HelperData.conty2.setValue(HelperData.conty2.getValue() - 1);
                }
                holder.cardViewSelected.setBackgroundColor(Color.WHITE);
                int index = HelperData.myTeamList.size() - 1;
                CreateTeamActivity.removedPlayerFromAddedList(Integer.parseInt(list.get(position).getPidPlayers()));
            } else {
                if (HelperData.playerCounter.getValue() < HelperData.limit) {
                    if (HelperData.creditCounter.getValue() >= Double.valueOf(listData.getFantasy_player_rating())) {
                        if (HelperData.bat.getValue() < 6) {
                            if (HelperData.team1NameShort == listData.getAbbr()) {
                                if (HelperData.conty1.getValue() < 7) {
                                    CreateTeamActivity.addedPlayerIds = CreateTeamActivity.addedPlayerIds + "_" + listData.getPidPlayers() + "_\n";
                                    HelperData.conty1.setValue(HelperData.conty1.getValue() + 1);
                                    HelperData.bat.setValue(HelperData.bat.getValue() + 1);
                                    HelperData.creditCounter.setValue(HelperData.creditCounter.getValue() - Double.valueOf(listData.getFantasy_player_rating()));
                                    HelperData.playerCounter.setValue(HelperData.playerCounter.getValue() + 1);
                                    holder.im_AddPlayer.setImageResource(R.drawable.minus_icon);
                                    holder.cardViewSelected.setBackgroundColor(Color.LTGRAY);
                                    AllSelectedPlayer allSelectedPlayer = new AllSelectedPlayer(Integer.valueOf(listData.getPidPlayers()), HelperData.matchId, listData.getShort_namePlayers(), listData.getAbbr(), "BAT", Double.valueOf(listData.getFantasy_player_rating()), false, false, false, "");
                                    HelperData.myTeamList.add(allSelectedPlayer);
                                } else {
                                    Toast.makeText(context, "Please select player from another Country", Toast.LENGTH_SHORT).show();
                                }
                            } else if (HelperData.team2NameShort == listData.getAbbr()) {
                                if (HelperData.conty2.getValue() < 7) {
                                    CreateTeamActivity.addedPlayerIds = CreateTeamActivity.addedPlayerIds + "_" + listData.getPidPlayers() + "_\n";
                                    HelperData.conty2.setValue(HelperData.conty2.getValue() + 1);
                                    HelperData.bat.setValue(HelperData.bat.getValue() + 1);
                                    HelperData.creditCounter.setValue(HelperData.creditCounter.getValue() - Double.valueOf(listData.getFantasy_player_rating()));
                                    HelperData.playerCounter.setValue(HelperData.playerCounter.getValue() + 1);
                                    holder.im_AddPlayer.setImageResource(R.drawable.minus_icon);
                                    holder.cardViewSelected.setBackgroundColor(Color.LTGRAY);
                                    AllSelectedPlayer allSelectedPlayer = new AllSelectedPlayer(Integer.valueOf(listData.getPidPlayers()), HelperData.matchId, listData.getShort_namePlayers(), listData.getAbbr(), "BAT", Double.valueOf(listData.getFantasy_player_rating()), false, false, false, "");
                                    HelperData.myTeamList.add(allSelectedPlayer);
                                } else {
                                    Toast.makeText(context, "Please select player from another Country", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(context, "Please add player from another tab", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Not enough credits left", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, HelperData.limit + "player Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView playerName, country, playerCredit, isPlaying, playerPoints;
        ImageView playerImg, alreadyAddedPlayer, im_AddPlayer;
        CardView cardViewSelected;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            playerPoints = itemView.findViewById(R.id.playerPoints);
            country = itemView.findViewById(R.id.country);
            playerCredit = itemView.findViewById(R.id.tv_PlayerCredit);
            playerImg = itemView.findViewById(R.id.playerImg);
            im_AddPlayer = itemView.findViewById(R.id.im_AddPlayer);
            cardViewSelected = itemView.findViewById(R.id.cardViewSelected);
            alreadyAddedPlayer = itemView.findViewById(R.id.alreadyAddedPlayer);
            isPlaying = itemView.findViewById(R.id.isPlaying);
        }
    }
}