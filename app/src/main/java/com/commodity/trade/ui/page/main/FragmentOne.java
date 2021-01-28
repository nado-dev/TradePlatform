package com.commodity.trade.ui.page.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.commodity.mylibrary.util.SizeUtil;
import com.commodity.mylibrary.weight.LoadingDialog;
import com.commodity.trade.MainActivity;
import com.commodity.trade.R;
import com.commodity.trade.entity.announce.Announce;
import com.commodity.trade.entity.message_bean.InviteMessageBox;
import com.commodity.trade.entity.order.Order;
import com.commodity.trade.entity.share.ShareBeanMuti;
import com.commodity.trade.entity.share.ShareIntegerBean;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.entity.user.UserConfig;
import com.commodity.trade.network.request.share.ShareIntegerRequest;
import com.commodity.trade.network.request.share.ShareMutiRequest;
import com.commodity.trade.ui.adapter.AnnounceAdapter;
import com.commodity.trade.ui.adapter.LooperPagerAdapter;
import com.commodity.trade.ui.page.feedback.FeedbackActivity;
import com.commodity.trade.ui.page.find_chances.FindChancesActivity;
import com.commodity.trade.ui.page.invite.QrCodePostActivity;
import com.commodity.trade.ui.page.message.MessageActivity;
import com.commodity.trade.ui.page.person_info.PersonInfoActivity;
import com.commodity.trade.ui.page.share.DetailedItemActivity;
import com.commodity.trade.ui.page.share.DetailedOrderActivity;
import com.commodity.trade.ui.page.share.GetInvitedActivity;
import com.commodity.trade.ui.page.share.ReleaseDemandActivity;
import com.commodity.trade.ui.page.share.ShareQrCodeActivity;
import com.commodity.trade.util.Config;
import com.taolibrary.interfaces.dialog.IDialog;
import com.taolibrary.widget.dialog.DialogLibrary;
import com.yzq.zxinglibrary.android.CaptureActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import q.rorbin.badgeview.QBadgeView;

import static com.taolibrary.BaseFunction.showToast;


/**
 * @author Aaron
 */
public class FragmentOne extends Fragment implements ToFragmentListener {
    private static BannerHandler handler;
    float count = 0;
    @BindView(R.id.f1_viewpager)
    ViewPager fragment1Viewpager;
    @BindView(R.id.banner_pointer)
    LinearLayout bannerPointer;
    @BindView(R.id.foot_text)
    TextView footText;
    @BindView(R.id.fragment_one_loading)
    LinearLayout fragmentOneLoading;
    @BindView(R.id.rv_notice)
    RecyclerView rvNotice;

    Context context;
    @BindView(R.id.f1_user_type)
    TextView f1UserType;
    @BindView(R.id.bt_scan)
    Button btScan;
    @BindView(R.id.bt_invoke_me)
    Button btInvokeMe;
    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.bt_about_me)
    Button btAboutMe;
    @BindView(R.id.bt_find_chances)
    Button btFindChances;
    @BindView(R.id.bt_feedback)
    Button btFeedback;
    @BindView(R.id.tv_num_invoking)
    TextView tvNumInvoking;
    @BindView(R.id.tv_num_invoked)
    TextView tvNumInvoked;
    @BindView(R.id.f1_add)
    LinearLayout f1Add;
    @BindView(R.id.f1_find)
    LinearLayout f1Find;
    @BindView(R.id.f1_find_text)
    TextView f1FindText;
    @BindView(R.id.layout_invoke)
    LinearLayout layoutInvoke;
    @BindView(R.id.layout_finish)
    LinearLayout layoutFinish;
    @BindView(R.id.tv_num_income)
    TextView tvNumIncome;
    @BindView(R.id.layout_income)
    LinearLayout layoutIncome;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.f1_message)
    ImageView f1Message;
    private User currentUser;
    List <Announce> announces;
    LoadingDialog loadingDialog;


    ShareIntegerRequest integerRequest = new ShareIntegerRequest();
    private MainActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @OnClick({R.id.bt_scan, R.id.bt_invoke_me, R.id.bt_add, R.id.bt_about_me, R.id.bt_find_chances, R.id.bt_feedback,
            R.id.layout_invoke, R.id.layout_finish, R.id.f1_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_scan:
                navigateToQrCode();
                break;
            case R.id.bt_invoke_me:
            case R.id.layout_finish:
            case R.id.layout_invoke:
                context.startActivity(new Intent(context, DetailedOrderActivity.class));
                break;
            case R.id.bt_add:
                if (currentUser.getUserType() != UserConfig.USER_TYPE_SELLER) {
                    context.startActivity(new Intent(context, GetInvitedActivity.class));
                    break;
                }
                context.startActivity(new Intent(context, ReleaseDemandActivity.class));
                break;
            case R.id.bt_about_me:
                context.startActivity(new Intent(context, PersonInfoActivity.class));
                break;
            case R.id.bt_find_chances:
                if (currentUser.getUserType() == UserConfig.USER_TYPE_BUYER) {
                    context.startActivity(new Intent(context, GetInvitedActivity.class));
                    break;
                }
                context.startActivity(new Intent(context, FindChancesActivity.class));
                break;
            case R.id.bt_feedback:
                context.startActivity(new Intent(context, FeedbackActivity.class));
                break;
            case R.id.f1_message:
                context.startActivity(new Intent(context, MessageActivity.class));
            default:
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.activity = (MainActivity) getActivity();
    }

    /**
     * QrCode 相关
     */
    private void navigateToQrCode() {
        int userType = currentUser.getUserType();
        if (userType == UserConfig.USER_TYPE_SELLER) {
            context.startActivity(new Intent(context, ShareQrCodeActivity.class));
        }
        else {
            DialogLibrary dialogLibrary = new DialogLibrary(context);
            dialogLibrary.bottomDialog("打开邀请二维码", "扫描商品二维码", new IDialog.OnBottomDialogClick() {
                @Override
                public void onTopButtonClick() {
                    startActivity(new Intent(context, QrCodePostActivity.class));
                }

                @Override
                public void onMidButonClick() {
                    activity.startActivityForResult(new Intent(context, CaptureActivity.class), 0x5379);
                    loadingDialog = new LoadingDialog(context, "处理中", R.mipmap.ic_dialog_loading);
                    loadingDialog.setCancelable(true);
                    loadingDialog.show();
                }
            });
        }
    }

    @Override
    public void onTypeClick(String content) {
        // TODO 回调消息 邀请码
        loadingDialog.dismiss();
        if (content != null && content.contains(Config.PREFIX_INVITE_R)) {
            Log.e("SCAN RESULT", "个人邀请码" + content.substring(48));
            showToast("无效邀请码");
        }
        if (content != null && content.contains(Config.PREFIX_SHARE_R)) {
            Log.e("SCAN RESULT", "商品邀请码" + content.substring(7));
            Intent intent = new Intent(activity, DetailedItemActivity.class);
            intent.putExtra("share_code", content.substring(Config.PREFIX_SHARE_R.length()));
            context.startActivity(intent);
        }
    }

    static class BannerHandler extends Handler {
        WeakReference <FragmentOne> mContext;

        public BannerHandler(FragmentOne fragmentOne) {
            mContext = new WeakReference <>(fragmentOne);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            FragmentOne fragmentOne = mContext.get();
            if (fragmentOne == null) {
                return;
            }

            if (msg.what == Config.CHANGE_PIC) {
                int index = (fragmentOne.fragment1Viewpager.getCurrentItem() + 1) % Config.BANNER_PICS.length;
                fragmentOne.fragment1Viewpager.setCurrentItem(index);
                handler.sendEmptyMessageDelayed(Config.CHANGE_PIC, 2000);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new BannerHandler(this);
        handler.sendEmptyMessageDelayed(Config.CHANGE_PIC, 2000);
        this.currentUser = BmobUser.getCurrentUser(User.class);

        MutableLiveData <ShareIntegerBean> shareData = integerRequest.getShareData();
        integerRequest.findFinishCount(currentUser.getObjectId());
        integerRequest.findTotalCount(currentUser.getObjectId());
        shareData.observe(this, shareBeanMuti -> {
            if (shareBeanMuti.getErrorCode() == 210) {
                int size = shareBeanMuti.getData();
                Log.e("FWY tvNumInvoked", String.valueOf(size));
                tvNumInvoked.setText(String.valueOf(size));
            }
            if (shareBeanMuti.getErrorCode() == 211) {
                int size = shareBeanMuti.getData();
                Log.e("FWY tvNumInvoking", String.valueOf(size));
                tvNumInvoking.setText(String.valueOf(size));
            }
        });

        BmobQuery<InviteMessageBox> query = new BmobQuery <>();
        query.addWhereEqualTo("invitor", currentUser.getObjectId());
        query.findObjects(new FindListener <InviteMessageBox>() {
            @Override
            public void done(List <InviteMessageBox> list, BmobException e) {
                new QBadgeView(context).bindTarget(f1Message).setBadgeNumber(list.size());
            }
        });
    }

    private void initAnnounce() {
        announces = new ArrayList <>();
//        rvNotice.set
        LinearLayoutManager layout = new LinearLayoutManager(context);
//        设置为横向排列
        rvNotice.setLayoutManager(layout);
        AnnounceAdapter adapter = new AnnounceAdapter(context, announces);
        rvNotice.setAdapter(adapter);
        // 加载数据
        BmobQuery <Announce> query = new BmobQuery <>();
        query.order("-sendDate");
        query.addWhereEqualTo("status", true);
        query.findObjects(new FindListener <Announce>() {
            @Override
            public void done(List <Announce> list, BmobException e) {
                if (e == null) {
                    Log.e("FWY", list.toString());
                    announces.addAll(list);
                    adapter.notifyDataSetChanged();
                    fragmentOneLoading.setVisibility(View.GONE);
                    rvNotice.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_one, container, false);
        ButterKnife.bind(this, view);
        if (currentUser.getUserType() == UserConfig.USER_TYPE_BUYER) {
            f1Find.setVisibility(View.GONE);
        }
        if (currentUser.getUserType() == UserConfig.USER_TYPE_MIDDLE) {
            layoutIncome.setVisibility(View.VISIBLE);
            f1Find.setVisibility(View.VISIBLE);
            // 加载收入
            loadMiddleIncome();
        }

        rvNotice.setVisibility(View.GONE);
        fragmentOneLoading.setVisibility(View.VISIBLE);
        f1UserType.setText(String.format("%s %s", currentUser.convertTypeToString(currentUser.getUserType()),
                currentUser.getUsername()));
        initAnnounce();
        initBanner();
        return view;
    }

    private void loadMiddleIncome() {
        ShareMutiRequest shareMutiRequest = new ShareMutiRequest();
        shareMutiRequest.getShareData().observe(this, shareBeanMuti -> {

            List <String> strings = new ArrayList <>();
            List <ShareBeanMuti.DataBean> data = shareBeanMuti.getData();
            if (data == null || data.size() == 0) {
                tvNumIncome.setText(String.valueOf(0));
                return;
            }
            for (ShareBeanMuti.DataBean bean : data) {
                strings.add(bean.getShareId());
            }
            BmobQuery <Order> query = new BmobQuery <>();
            query.addWhereContainedIn("objectId", strings)
                    .findObjects(new FindListener <Order>() {
                        @Override
                        public void done(List <Order> list, BmobException e) {
                            for (Order order : list) {
                                count += order.getCommission() * order.getItemAmount();
                                tvNumIncome.setText(String.valueOf(count));
                            }
                        }
                    });
        });
        shareMutiRequest.getListByUid(currentUser.getObjectId());
    }


    /**
     * Banner 的初始化
     */
    private void initBanner() {
        // 创建适配器
        LooperPagerAdapter adapter = new LooperPagerAdapter();
        // 设置适配器
        fragment1Viewpager.setAdapter(adapter);
        fragment1Viewpager.setOffscreenPageLimit(Config.BANNER_PICS.length);
        fragment1Viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int targetPosition = position % Config.BANNER_PICS.length;
                // 切换指示器
                updateIndicator(targetPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 设置中间点
        fragment1Viewpager.setCurrentItem(0);

        for (int i = 0; i < Config.BANNER_PICS.length; i++) {
            View singlePoint = new View(getContext());
            int px = SizeUtil.dp2px(context, 8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(px, px);

            params.leftMargin = SizeUtil.dp2px(context, 5);
            params.rightMargin = SizeUtil.dp2px(context, 5);

            singlePoint.setLayoutParams(params);
            fragment1Viewpager.addView(singlePoint);
        }
    }

    /**
     * 切换指示器 小圆点
     *
     * @param pos 位置
     */
    private void updateIndicator(int pos) {
        for (int i = 0; i < bannerPointer.getChildCount(); i++) {
            View pointerChild = bannerPointer.getChildAt(i);
            if (i == pos) {
                pointerChild.setBackgroundResource(R.drawable.shape_point_selected);
            } else {
                pointerChild.setBackgroundResource(R.drawable.shape_point_normal);
            }
        }
    }
}
