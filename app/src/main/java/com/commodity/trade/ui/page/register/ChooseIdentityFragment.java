package com.commodity.trade.ui.page.register;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.commodity.trade.R;
import com.commodity.trade.entity.message.Event;
import com.commodity.trade.entity.user.UserConfig;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AaFaa
 * on 2020/12/10
 * in package com.commodity.trade.ui.page.register
 * with project trade
 * @author Aaron
 */
public class ChooseIdentityFragment extends Fragment {
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            EventBus.getDefault().post(new Event(msg.what));
        }
    };

    @BindView(R.id.tv_register_seller)
    TextView tvRegisterSeller;
    @BindView(R.id.tv_register_middle)
    TextView tvRegisterMiddle;
    @BindView(R.id.tv_register_buyer)
    TextView tvRegisterBuyer;
    RegisterActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_identity, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        this.activity = (RegisterActivity) getActivity();
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick({R.id.tv_register_seller, R.id.tv_register_middle, R.id.tv_register_buyer})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_register_seller:
                activity.navigateToVerifyCodeFragment(RegisterActivity.FRAG_CHOOSE_INVITE_CODE);
                handler.sendEmptyMessageDelayed(UserConfig.USER_TYPE_SELLER, 100);
                break;
            case R.id.tv_register_middle:
                activity.navigateToVerifyCodeFragment(RegisterActivity.FRAG_CHOOSE_INVITE_CODE);
                handler.sendEmptyMessageDelayed(UserConfig.USER_TYPE_MIDDLE, 100);

                break;
            case R.id.tv_register_buyer:
                activity.navigateToVerifyCodeFragment(RegisterActivity.FRAG_CHOOSE_INVITE_CODE);
                handler.sendEmptyMessageDelayed(UserConfig.USER_TYPE_BUYER, 100);
                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler = null;
    }
}
