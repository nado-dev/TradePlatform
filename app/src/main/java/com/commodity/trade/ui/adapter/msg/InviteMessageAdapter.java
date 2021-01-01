package com.commodity.trade.ui.adapter.msg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.commodity.trade.R;
import com.commodity.trade.entity.message_bean.InviteMessageBox;

import java.util.List;

/**
 * Created by AaFaa
 * on 2020/12/27
 * in package com.commodity.trade.ui.adapter.msg
 * with project trade
 * @author Aaron
 */
public class InviteMessageAdapter extends RecyclerView.Adapter{
    private static final int NO_MORE_TO_LOAD = -1;
    private static final int NOTICE_LAYOUT = 1;
    private List <InviteMessageBox> data;

    @Override
    public int getItemViewType(int position) {
        if (position == data.size()) {
            return NO_MORE_TO_LOAD;
        }
        return NOTICE_LAYOUT;
    }

    public InviteMessageAdapter(List <InviteMessageBox> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detailed_msg_invite,
                parent, false);

        View noMoreToLoad = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_more_to_load_l,
                parent,false);
        if (viewType == NO_MORE_TO_LOAD){
            return new RecyclerViewHolder(noMoreToLoad, NO_MORE_TO_LOAD);
        }
        else{
            return new RecyclerViewHolder(view, NOTICE_LAYOUT);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == NO_MORE_TO_LOAD){
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        }
        else{
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            InviteMessageBox inviteMessageBox = data.get(position);
            recyclerViewHolder.who.setText(String.format("ID %s", inviteMessageBox.getNewUser()));
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    private static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView who, loading;
        RecyclerViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if (viewType == NOTICE_LAYOUT){
                who = itemView.findViewById(R.id.invite_who);
            }

            else if (viewType == NO_MORE_TO_LOAD){
                loading = itemView.findViewById(R.id.foot_text_no_more_l);
            }
        }
    }
}
