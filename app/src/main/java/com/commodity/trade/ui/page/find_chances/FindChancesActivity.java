package com.commodity.trade.ui.page.find_chances;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commodity.trade.R;
import com.commodity.trade.entity.share.ShareBeanMuti;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.network.request.share.ShareMutiRequest;
import com.commodity.trade.ui.adapter.share.DetailedChanceListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class FindChancesActivity extends AppCompatActivity {
    ShareMutiRequest request = new ShareMutiRequest();
    @BindView(R.id.chance_list_back)
    ImageView chanceListBack;
    @BindView(R.id.chance_list_foot_text)
    TextView chanceListFootText;
    @BindView(R.id.chance_list_loading)
    LinearLayout chanceListLoading;
    @BindView(R.id.rv_chance_list)
    RecyclerView rvChanceList;
    private MutableLiveData <ShareBeanMuti> shareData;
    private User currentUser;
    private List <ShareBeanMuti.DataBean> data;
    private DetailedChanceListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_chances);
        ButterKnife.bind(this);
        this.shareData = request.getShareData();

        initView();

        shareData.observe(this, shareBeanMuti -> {
            if (shareBeanMuti.getErrorCode() == 220) {
                data.addAll(shareBeanMuti.getData());
                adapter.notifyDataSetChanged();
                chanceListLoading.setVisibility(View.GONE);
                rvChanceList.setVisibility(View.VISIBLE);
            }
        });

        currentUser = BmobUser.getCurrentUser(User.class);
        request.getChances(currentUser.getObjectId());
    }

    private void initView() {
        rvChanceList.setVisibility(View.GONE);
        data = new ArrayList <>();
        adapter = new DetailedChanceListAdapter(this, data);
        rvChanceList.setLayoutManager(new LinearLayoutManager(this));
        rvChanceList.setAdapter(adapter);
    }

    @OnClick(R.id.chance_list_back)
    public void onViewClicked() {
        finish();
    }
}