package com.commodity.trade.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.commodity.trade.R;
import com.commodity.trade.entity.announce.Announce;
import com.commodity.trade.ui.page.announce.DetailedAnnounceActivity;

import java.util.List;


/**
 * @author Aaron
 */
public class AnnounceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NOTICE_LAYOUT = 1;
    private static final int NO_MORE_TO_LOAD = -1;
    private Context context;
    private List <Announce> data;
    @Override
    public int getItemViewType(int position) {
        if (position == data.size()){
            return NO_MORE_TO_LOAD;
        }
        return NOTICE_LAYOUT;
    }

    public AnnounceAdapter(Context context, List<Announce> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detailed_announce, parent, false);
        View noMoreToLoad = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_more_to_load_l, parent,false);
        if (viewType == NO_MORE_TO_LOAD){
            return new AnnounceAdapter.RecyclerViewHolder(noMoreToLoad, NO_MORE_TO_LOAD);
        }
        else{
            return new AnnounceAdapter.RecyclerViewHolder(view, NOTICE_LAYOUT);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == NO_MORE_TO_LOAD){
            final AnnounceAdapter.RecyclerViewHolder recyclerViewHolder = (AnnounceAdapter.RecyclerViewHolder) holder;
        }
        else{
            final AnnounceAdapter.RecyclerViewHolder recyclerViewHolder = (AnnounceAdapter.RecyclerViewHolder) holder;
            Announce post  = data.get(position);
            recyclerViewHolder.tvNoticeTitle.setText(String.format("标题 %s", post.getTitle()));

            String desc = post.getContent().length() >= 9 ? post.getContent().substring(0,8): post.getContent();
            recyclerViewHolder.tvDesc.setText(desc);
            Log.e("FWY","data set+ "+ recyclerViewHolder.tvNoticeTitle.getText());
            recyclerViewHolder.tvTitleCreateTime.setText(String.format("发布时间 %s", post.translateTimeStampToString()));
            recyclerViewHolder.itemView.setOnClickListener(v->{
                Intent intent = new Intent(context, DetailedAnnounceActivity.class);
                intent.putExtra("info",post);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tvNoticeTitle, tvTitleCreateTime, tvDesc;
        TextView loading;
        RecyclerViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if (viewType == NOTICE_LAYOUT){
                tvNoticeTitle = itemView.findViewById(R.id.notice_title);
                tvDesc = itemView.findViewById(R.id.notice_desc);
                tvTitleCreateTime = itemView.findViewById(R.id.notice_create_time);
            }

            else if (viewType == NO_MORE_TO_LOAD){
                loading = itemView.findViewById(R.id.foot_text_no_more_l);
            }
        }
    }
}
