package com.commodity.trade.ui.adapter.share;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.commodity.trade.R;
import com.commodity.trade.entity.share.ShareBeanMuti;
import com.commodity.trade.entity.user.UserConfig;
import com.commodity.trade.ui.page.find_chances.GetAllShareCodeActivity;
import com.commodity.trade.ui.page.invite.QrCodePostActivity;
import com.commodity.trade.ui.page.share.DetailedItemActivity;
import com.commodity.trade.ui.page.share.ShareQrCodeActivity;
import com.commodity.trade.ui.page.share.ShareQrItemActivity;

import java.util.List;

/**
 * Created by AaFaa
 * on 2020/12/26
 * in package com.commodity.trade.ui.adapter.share
 * with project trade
 */
public class InviteCodeAdapter extends RecyclerView.Adapter{
    private static final int NO_MORE_TO_LOAD = -1;
    private static final int NOTICE_LAYOUT = 1;
    private final List <String> data;
    private Context context;

    public InviteCodeAdapter(Context context, List <String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_share_code, parent, false);
        View noMoreToLoad = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_more_to_load, parent, false);
        if (viewType == NO_MORE_TO_LOAD) {
            return new NoMoreViewHolder(noMoreToLoad);
        } else {
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == NO_MORE_TO_LOAD) {
            final InviteCodeAdapter.NoMoreViewHolder recyclerViewHolder = (InviteCodeAdapter.NoMoreViewHolder) holder;
        }
        else{
            final ItemViewHolder recyclerViewHolder = (ItemViewHolder) holder;
            String s = data.get(position);
            recyclerViewHolder.tvShareCode.setText(s);
            recyclerViewHolder.ivCopy.setOnClickListener(v->{
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard != null) {
                    //参数一：标签，可为空，参数二：要复制到剪贴板的文本
                    clipboard.setPrimaryClip(ClipData.newPlainText(null, s));
                }
                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
            });
            recyclerViewHolder.ivShare.setOnClickListener(v -> {
                Intent intent = new Intent(context, ShareQrItemActivity.class);
                intent.putExtra("invite_code", s);
                intent.putExtra("item_name", "");
                Log.e(getClass().getSimpleName(), s);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == data.size()) {
            return NO_MORE_TO_LOAD;
        }
        return NOTICE_LAYOUT;
    }

    private static class NoMoreViewHolder extends RecyclerView.ViewHolder {
        ImageView footTextNoMore;

        public NoMoreViewHolder(View noMoreToLoad) {
            super(noMoreToLoad);
            footTextNoMore = itemView.findViewById(R.id.foot_text_no_more);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvShareCode;
        ImageView ivShare, ivCopy;

        ItemViewHolder(View view) {
            super(view);
            tvShareCode = itemView.findViewById(R.id.tv_share_code);
            ivShare = itemView.findViewById(R.id.iv_share);
            ivCopy = itemView.findViewById(R.id.iv_copy);
        }
    }
}
