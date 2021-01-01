package com.commodity.trade.ui.page.share;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commodity.trade.R;
import com.commodity.trade.entity.share.ShareBeanMuti;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.network.request.share.ShareMutiRequest;
import com.commodity.trade.ui.adapter.share.DetailedOrderListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * @author Aaron
 */
public class DetailedOrderActivity extends AppCompatActivity {

    @BindView(R.id.order_list_back)
    ImageView orderListBack;
    @BindView(R.id.order_list_foot_text)
    TextView orderListFootText;
    @BindView(R.id.order_list_loading)
    LinearLayout orderListLoading;
    @BindView(R.id.rv_order_list)
    RecyclerView rvOrderList;
    List<ShareBeanMuti.DataBean> data;
    ShareMutiRequest request = new ShareMutiRequest();
    private DetailedOrderListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_order);
        ButterKnife.bind(this);
        MutableLiveData <ShareBeanMuti> shareData = request.getShareData();

        orderListLoading.setVisibility(View.VISIBLE);
        rvOrderList.setVisibility(View.GONE);
        initRView();
        shareData.observe(this, shareBeanMuti -> {
            if (shareBeanMuti.getErrorCode() == 203) {
                // 查询成功
                data.addAll(shareBeanMuti.getData());
                Log.e("FWY", data.toString());
                adapter.notifyDataSetChanged();

                orderListLoading.setVisibility(View.GONE);
                rvOrderList.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(DetailedOrderActivity.this, shareBeanMuti.getMsg(), Toast.LENGTH_SHORT).show();
                Log.e("FWY", shareBeanMuti.toString());
                orderListLoading.setVisibility(View.GONE);
            }
        });

    }

    private void initRView() {
        rvOrderList.setLayoutManager(new LinearLayoutManager(this));
        data = new ArrayList<>();
        adapter = new DetailedOrderListAdapter(this, data);
        rvOrderList.setAdapter(adapter);
        initData();
    }

    private void initData() {
        User currentUser = BmobUser.getCurrentUser(User.class);
        request.getListByUid(currentUser.getObjectId());
    }

    @OnClick(R.id.order_list_back)
    public void onViewClicked() {
        finish();
    }
}