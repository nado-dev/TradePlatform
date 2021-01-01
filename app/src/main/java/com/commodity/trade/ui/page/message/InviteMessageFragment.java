package com.commodity.trade.ui.page.message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commodity.trade.R;
import com.commodity.trade.entity.message_bean.InviteMessageBox;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.ui.adapter.msg.InviteMessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by AaFaa
 * on 2020/12/27
 * in package com.commodity.trade.ui.page.message
 * with project trade
 */
public class InviteMessageFragment extends Fragment {
    @BindView(R.id.rv_msg_invite)
    RecyclerView rvMsgInvite;
    @BindView(R.id.chance_list_foot_text)
    TextView chanceListFootText;
    @BindView(R.id.message_list_loading)
    LinearLayout messageListLoading;
    private MessageActivity activity;
    private Context context;
    private List <InviteMessageBox> data;
    private InviteMessageAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = getContext();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MessageActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_invite_message, container, false);
        ButterKnife.bind(this, view);
        rvMsgInvite.setVisibility(View.GONE);
        messageListLoading.setVisibility(View.VISIBLE);
        data = new ArrayList <>();
        adapter = new InviteMessageAdapter(data);
        rvMsgInvite.setLayoutManager(new LinearLayoutManager(context));
        rvMsgInvite.setAdapter(adapter);
        BmobQuery <InviteMessageBox> boxBmobQuery = new BmobQuery <>();
        User currentUser = BmobUser.getCurrentUser(User.class);
        boxBmobQuery.addWhereEqualTo("invitor", currentUser.getObjectId());
        boxBmobQuery.findObjects(new FindListener <InviteMessageBox>() {
            @Override
            public void done(List <InviteMessageBox> list, BmobException e) {
                if (null == e) {
                    rvMsgInvite.setVisibility(View.VISIBLE);
                    messageListLoading.setVisibility(View.GONE);
                    data.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }
}
