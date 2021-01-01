package com.commodity.trade.ui.page.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.commodity.trade.MainActivity;
import com.commodity.trade.R;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.ui.page.about.AboutActivity;
import com.commodity.trade.ui.page.feedback.FeedbackActivity;
import com.commodity.trade.ui.page.login.LoginActivity;
import com.commodity.trade.ui.page.message.MessageActivity;
import com.commodity.trade.ui.page.person_info.PersonInfoActivity;
import com.commodity.trade.ui.page.share.DetailedOrderActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;


/**
 * @author Aaron
 */
public class FragmentTwo extends Fragment {


    @BindView(R.id.fragment3_logout)
    Button fragment3Logout;

    MainActivity activity;
    @BindView(R.id.fragment3_username)
    TextView fragment3Username;
    @BindView(R.id.fragment3_user_info)
    LinearLayout fragment3UserInfo;
    @BindView(R.id.fragment3_invoke)
    LinearLayout fragment3Invoke;
    @BindView(R.id.fragment3_feed_back)
    LinearLayout fragment3FeedBack;
    @BindView(R.id.fragment3_setting)
    LinearLayout fragment3Setting;
    @BindView(R.id.fragment3_message)
    LinearLayout fragment3Message;
    private User currentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_two, container, false);
        ButterKnife.bind(this, view);
        currentUser = BmobUser.getCurrentUser(User.class);
        fragment3Username.setText(currentUser.getUsername());
        return view;
    }

    @OnClick({R.id.fragment3_user_info, R.id.fragment3_invoke, R.id.fragment3_feed_back, R.id.fragment3_setting,
            R.id.fragment3_logout, R.id.fragment3_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment3_user_info:
                startActivity(new Intent(activity, PersonInfoActivity.class));
                break;
            case R.id.fragment3_invoke:
                startActivity(new Intent(activity, DetailedOrderActivity.class));
                break;
            case R.id.fragment3_feed_back:
                startActivity(new Intent(activity, FeedbackActivity.class));
                break;
            case R.id.fragment3_setting:
                startActivity(new Intent(activity, AboutActivity.class));
                break;
            case R.id.fragment3_logout:
                BmobUser.logOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                activity.finish();
            case R.id.fragment3_message:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            default:
        }
    }
}
