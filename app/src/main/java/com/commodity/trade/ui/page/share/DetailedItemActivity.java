package com.commodity.trade.ui.page.share;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.commodity.mylibrary.util.ShareUtil;
import com.commodity.mylibrary.weight.LoadingDialog;
import com.commodity.trade.R;
import com.commodity.trade.entity.message_bean.ShareMessageBox;
import com.commodity.trade.entity.order.Order;
import com.commodity.trade.entity.share.ShareBean;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.entity.user.UserConfig;
import com.commodity.trade.network.request.share.ShareBeforeAfterRequest;
import com.commodity.trade.network.request.share.ShareRequest;
import com.commodity.trade.util.Config;

import org.commonmark.node.Node;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import io.noties.markwon.Markwon;
import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptDialog;

public class DetailedItemActivity extends AppCompatActivity {

    @BindView(R.id.order_detail_back)
    ImageView orderDetailBack;
    @BindView(R.id.order_detail_share)
    ImageView orderDetailShare;
    @BindView(R.id.order_invite_code)
    TextView orderInviteCode;
    @BindView(R.id.order_go_detail)
    Button orderGoDetail;
    @BindView(R.id.order_show_success)
    LinearLayout orderShowSuccess;
    @BindView(R.id.order_detail_curr_status)
    TextView orderDetailStatus;
    @BindView(R.id.order_detail_unit_price)
    TextView orderDetailUnitPrice;
    @BindView(R.id.order_detail_amount)
    TextView orderDetailAmount;
    @BindView(R.id.order_detail_commission)
    TextView orderDetailCommission;
    @BindView(R.id.order_detail_document_desc)
    TextView orderDetailDocumentDesc;
    @BindView(R.id.order_document)
    LinearLayout orderDocument;
    @BindView(R.id.order_detail_detail)
    TextView orderDetailDetail;
    @BindView(R.id.order_setter)
    ScrollView orderSetter;
    @BindView(R.id.order_detail_take_part)
    Button orderDetailTakePart;
    @BindView(R.id.order_detail_before)
    TextView orderDetailBefore;
    @BindView(R.id.order_detail_after)
    TextView orderDetailAfter;
    @BindView(R.id.order_share_detail_name)
    TextView orderShareDetailName;
    private Order order;
    private User user;
    private String shareId;
    private String shareInviteCode;
    private LoadingDialog dialog;
    ShareRequest request = new ShareRequest();
    ShareBeforeAfterRequest shareBeforeAfterRequest = new ShareBeforeAfterRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_item_code);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        shareId = intent.getStringExtra("shareId");
        // 查询页面
        shareInviteCode = intent.getStringExtra("share_code");


        user = BmobUser.getCurrentUser(User.class);
        dialog = new LoadingDialog(this, "加载中", R.mipmap.ic_dialog_loading);
        dialog.setCancelable(true);
        dialog.show();

        if (shareInviteCode != null) {
            orderDetailShare.setVisibility(View.INVISIBLE);
            orderDetailTakePart.setVisibility(View.VISIBLE);
            MutableLiveData <ShareBean> shareData = request.getShareData();
            shareData.observe(this, shareBean -> {
                if (shareBean.getErrorCode() == 201) {
                    shareId = shareBean.getData().getShareId();
                    updateBeforeAfter();
                    queryBmob();
                }
                if (shareBean.getErrorCode() == 200) {
                    if (user.getUserType() == UserConfig.USER_TYPE_BUYER) {
                        // 更新表单信息为完成
                        updateBmobStatus();
                    }
                    else {
                        Toast.makeText(DetailedItemActivity.this,
                                shareBean.getErrorCode() + " "+ shareBean.getMsg(),
                                Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
                if (shareBean.getErrorCode() == 50001) {
                    dialog.dismiss();
                    Toast.makeText(DetailedItemActivity.this,
                            shareBean.getErrorCode() + shareBean.getMsg(),
                            Toast.LENGTH_SHORT).show();
                }
            });
            request.findShareCodeInfo(shareInviteCode);
        } else {
            updateBeforeAfter();
            queryBmob();
        }
    }

    private void updateBmobStatus() {
        order.setStatus(true);
        order.update(shareId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    if (user.getUserType() == UserConfig.USER_TYPE_BUYER) {
                        updateShareInfo(shareId, true, shareInviteCode,user.getObjectId());
                    }
                    else {
                        updateShareInfo(shareId, false, shareInviteCode,user.getObjectId());
                    }
                }
                else {
                    e.printStackTrace();
                    Toast.makeText(DetailedItemActivity.this, "参与失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateShareInfo(String shareId, boolean isFinish, String shareInviteCode, String newComer) {
        ShareMessageBox shareMessageBox = new ShareMessageBox();
        shareMessageBox.setFinish(isFinish);
        shareMessageBox.setShareId(shareId);
        shareMessageBox.setShareFrom(shareInviteCode);
        shareMessageBox.setShareTo(newComer);
        shareMessageBox.save(new SaveListener <String>() {
            @Override
            public void done(String s, BmobException e) {
                if ( null == e) {
                    dialog.dismiss();
                    if (user.getUserType() == UserConfig.USER_TYPE_BUYER) {
                        Toast.makeText(DetailedItemActivity.this,
                                "参与成功，交易达成，请尽快联系买方，按照承诺完成交易", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(DetailedItemActivity.this,
                                "参与成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void updateBeforeAfter() {
        shareBeforeAfterRequest.getShareData().observe(this, shareBeforeAfterBean -> {
            if (shareBeforeAfterBean.getErrorCode() == 230) {
                updateBeforeAfter1(shareBeforeAfterBean.getData().getBefore(),
                        shareBeforeAfterBean.getData().getAfter());
            }
        });
        shareBeforeAfterRequest.findBeforeAfter(shareId, user.getObjectId());
    }

    private void updateBeforeAfter1(int before, int after) {
        orderDetailBefore.setText(String.valueOf(before));
        orderDetailAfter.setText(String.valueOf(after));
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
                    Log.e("FWY", "查询失败" + list.toString());
                    Toast.makeText(DetailedItemActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    private void updateMsg() {
        if (order.getStatus()) {
            orderDetailStatus.setText("已完成");
            orderDetailStatus.setTextColor(Color.GREEN);
        }
        else {
            orderDetailStatus.setText("进行中");
            orderDetailStatus.setTextColor(Color.RED);
        }
        orderShareDetailName.setText(order.getItemName());
        orderDetailUnitPrice.setText(String.valueOf(order.getUnitPrice()));
        orderDetailAmount.setText(String.valueOf(order.getItemAmount()));
        orderDetailCommission.setText(String.valueOf(order.getCommission()));
        if ("".equals(order.getDocumentUrl())) {
            orderDetailDocumentDesc.setText("未上传");
        } else {
            orderDetailDocumentDesc.setText("已上传");
        }
        orderDetailDetail.setText(order.getDetailDescription());
    }

    private void initShareBtn() {
        PromptDialog dialog = new PromptDialog(this);
        dialog.showAlertSheet("分享方式", true,
                new PromptButton("二维码分享", button -> navigateToShareQrCode()),
                new PromptButton("文字分享", button -> shareWithText()));
    }

    private void shareWithText() {
        String shareText = "邀请您参与商品分享：商品名称" + order.getShareId().substring(0, 3)
                + user.getObjectId().substring(2, 5);
        ShareUtil.shareTextToOuterApp(this, shareText);
    }

    private void navigateToShareQrCode() {
        String newInviteCode = shareId.substring(0, 3) + user.getObjectId().substring(2, 5);
        Intent intent = new Intent(this, ShareQrItemActivity.class);
        intent.putExtra("invite_code", newInviteCode);
        intent.putExtra("item_name", order.getItemName());
        startActivity(intent);
    }

    @OnClick({R.id.order_detail_back, R.id.order_detail_share, R.id.order_detail_take_part})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_detail_back:
                finish();
                break;
            case R.id.order_detail_share:
                initShareBtn();
                break;
            case R.id.order_detail_take_part:
                inviteNew();
            default:
        }
    }

    private void inviteNew() {
        commitRights();
    }

    private void commitRights() {
        final TextView textView = new TextView(this);
        final Markwon markwon = Markwon.create(this);
        final Node node = markwon.parse(Config.CONTENT);
        final Spanned markdown = markwon.render(node);

        markwon.setParsedMarkdown(textView, markdown);
        new AlertDialog.Builder(this).setTitle("重要：请阅读并同意以下条款")
                .setView(textView)
                .setPositiveButton("确定", (dialog, which) -> {
                    User currentUser = BmobUser.getCurrentUser(User.class);
                    DetailedItemActivity.this.dialog.show();
                    request.getShared(shareInviteCode,
                            shareId,
                            currentUser.getUsername(),
                            currentUser.getObjectId(),
                            currentUser.getUserType());
                }
                ).setNegativeButton("取消",null).show();
    }
}