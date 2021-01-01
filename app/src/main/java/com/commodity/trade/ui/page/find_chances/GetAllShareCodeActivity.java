package com.commodity.trade.ui.page.find_chances;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commodity.mylibrary.weight.LoadingDialog;
import com.commodity.trade.R;
import com.commodity.trade.entity.order.Order;
import com.commodity.trade.entity.share.ShareBeanMuti;
import com.commodity.trade.network.request.share.ShareMutiRequest;
import com.commodity.trade.ui.adapter.share.InviteCodeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GetAllShareCodeActivity extends AppCompatActivity {

    @BindView(R.id.order_share_detail_back)
    ImageView orderShareDetailBack;
    @BindView(R.id.order_share_detail_share)
    ImageView orderShareDetailShare;
    @BindView(R.id.order_share_invite_code)
    TextView orderShareInviteCode;
    @BindView(R.id.order_share_go_detail)
    Button orderShareGoDetail;
    @BindView(R.id.order_share_show_success)
    LinearLayout orderShareShowSuccess;
    @BindView(R.id.order_share_detail_name)
    TextView orderShareDetailName;
    @BindView(R.id.order_share_detail_unit_price)
    TextView orderShareDetailUnitPrice;
    @BindView(R.id.order_share_detail_amount)
    TextView orderShareDetailAmount;
    @BindView(R.id.order_share_detail_commission)
    TextView orderShareDetailCommission;
    @BindView(R.id.order_share_detail_document_desc)
    TextView orderShareDetailDocumentDesc;
    @BindView(R.id.order_share_document)
    LinearLayout orderShareDocument;
    @BindView(R.id.order_share_detail_detail)
    TextView orderShareDetailDetail;
    @BindView(R.id.order_share_rv)
    RecyclerView orderShareRv;
    @BindView(R.id.order_share_setter)
    ScrollView orderShareSetter;
    ShareMutiRequest request = new ShareMutiRequest();
    @BindView(R.id.order_share_foot_text)
    TextView orderShareFootText;
    @BindView(R.id.order_share_loading)
    LinearLayout orderShareLoading;
    private String shareId;
    private Order order;
    private LoadingDialog dialog;
    private List <String> data = new ArrayList <>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_share_code);
        ButterKnife.bind(this);

        InviteCodeAdapter adapter = new InviteCodeAdapter(this, data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        orderShareRv.setLayoutManager(linearLayoutManager);
        orderShareRv.setAdapter(adapter);
        orderShareRv.setVisibility(View.GONE);
        orderShareLoading.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        shareId = intent.getStringExtra("shareId");
        dialog = new LoadingDialog(this, "加载中", R.mipmap.ic_dialog_loading);
        dialog.show();
        queryBmob();
        request.getShareData().observe(this, shareBeanMuti -> {
            if (shareBeanMuti.getErrorCode() == 202) {
                // 更新View
                List <ShareBeanMuti.DataBean> list = shareBeanMuti.getData();
                List <String> temp = new ArrayList <>();
                for (ShareBeanMuti.DataBean bean : list) {
                    temp.add(bean.getInviteCode());
                }
                data.addAll(temp);
                adapter.notifyDataSetChanged();
                orderShareRv.setVisibility(View.VISIBLE);
                orderShareLoading.setVisibility(View.GONE);
            }
        });
        request.getListByShareId(shareId);
    }

    @OnClick(R.id.order_share_detail_back)
    public void onViewClicked() {
        finish();
    }

    void queryBmob() {
        BmobQuery <Order> query = new BmobQuery <>();
        query.addWhereEqualTo("objectId", shareId);
        Log.e("FWY", "shareId" + shareId);
        query.findObjects(new FindListener <Order>() {
            @Override
            public void done(List <Order> list, BmobException e) {
                if (e == null && list.size() != 0) {
                    Log.e("FWY", "查询成功" + list.toString());
                    order = list.get(0);
                    updateMsg();
                    dialog.dismiss();
                } else {
                    Log.e("FWY", "查询失败");
                    Toast.makeText(GetAllShareCodeActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    private void updateMsg() {
        orderShareDetailName.setText(order.getItemName());
        orderShareDetailUnitPrice.setText(String.valueOf(order.getUnitPrice()));
        orderShareDetailAmount.setText(String.valueOf(order.getItemAmount()));
        orderShareDetailCommission.setText(String.valueOf(order.getCommission()));
        if ("".equals(order.getDocumentUrl())) {
            orderShareDetailDocumentDesc.setText("未上传");
        } else {
            orderShareDetailDocumentDesc.setText("已上传");
        }
        orderShareDetailDetail.setText(order.getDetailDescription());
    }
}