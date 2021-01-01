package com.commodity.trade.ui.adapter.share;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.commodity.trade.R;
import com.commodity.trade.entity.share.ShareBeanMuti;
import com.commodity.trade.entity.user.UserConfig;
import com.commodity.trade.ui.page.share.DetailedItemActivity;
import com.commodity.trade.ui.page.share.DetailedOrderActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AaFaa
 * on 2020/12/23
 * in package com.commodity.trade.ui.adapter.share
 * with project trade
 *
 * @author Aaron
 */
public class DetailedOrderListAdapter extends RecyclerView.Adapter {
    private static final int NO_MORE_TO_LOAD = -1;
    private static final int NOTICE_LAYOUT = 1;
    private Context context;
    private List <ShareBeanMuti.DataBean> data;

    public DetailedOrderListAdapter(Context context, List <ShareBeanMuti.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size()) {
            return NO_MORE_TO_LOAD;
        }
        return NOTICE_LAYOUT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detailed_order, parent, false);
        View noMoreToLoad = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_more_to_load_l, parent, false);
        if (viewType == NO_MORE_TO_LOAD) {
            return new DetailedOrderListAdapter.NoMoreViewHolder(noMoreToLoad);
        } else {
            return new DetailedOrderListAdapter.ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == NO_MORE_TO_LOAD) {
            final DetailedOrderListAdapter.NoMoreViewHolder recyclerViewHolder = (DetailedOrderListAdapter.NoMoreViewHolder) holder;
        }
        else{
            final DetailedOrderListAdapter.ItemViewHolder recyclerViewHolder = (DetailedOrderListAdapter.ItemViewHolder) holder;
            ShareBeanMuti.DataBean dataBean = data.get(position);
            String name = dataBean.getName();
            String shareId = dataBean.getShareId();
            int status = 0;
            if (data.get(data.size() - 1).getUserType() == UserConfig.USER_TYPE_BUYER) {
                status = 1;
            }
            recyclerViewHolder.orderItemShareId.setText(shareId);
            recyclerViewHolder.orderItemName.setText(name);
            recyclerViewHolder.orderItemStatus.setText(status == 0 ? "进行中": "已结束");
            recyclerViewHolder.itemView.setOnClickListener(v->{
                Intent intent = new Intent(context, DetailedItemActivity.class);
                intent.putExtra("shareId", shareId);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView orderItemShareId;
        TextView orderItemName;
        TextView orderItemStatus;

        ItemViewHolder(View view) {
            super(view);
            orderItemShareId = itemView.findViewById(R.id.order_item_share_id);
            orderItemName = itemView.findViewById(R.id.order_item_name);
            orderItemStatus = itemView.findViewById(R.id.order_item_status);
        }
    }

    private static class NoMoreViewHolder extends RecyclerView.ViewHolder {
        TextView footTextNoMore;

        public NoMoreViewHolder(View noMoreToLoad) {
            super(noMoreToLoad);
            footTextNoMore = itemView.findViewById(R.id.foot_text_no_more);
        }
    }
}
