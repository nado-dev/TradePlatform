package com.commodity.trade.ui.page.message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commodity.trade.R;
import com.commodity.trade.entity.message_bean.InviteMessageBox;
import com.commodity.trade.entity.message_bean.ShareMessageBox;
import com.commodity.trade.entity.share.ShareBeanMuti;
import com.commodity.trade.entity.user.User;
import com.commodity.trade.network.request.share.ShareMutiRequest;
import com.commodity.trade.ui.adapter.msg.InviteMessageAdapter;
import com.commodity.trade.ui.adapter.msg.ShareMessageAdapter;

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
public class ShareMessageFragment extends Fragment {
    @BindView(R.id.rv_msg_share)
    RecyclerView rvMsgShare;
    @BindView(R.id.chance_list_foot_text)
    TextView chanceListFootText;
    @BindView(R.id.message_list_loading)
    LinearLayout messageListLoading;
    private Context context;
    private List <ShareMessageBox> data;
    private ShareMessageAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = getContext();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User currentUser = BmobUser.getCurrentUser(User.class);
        ShareMutiRequest request = new ShareMutiRequest();
        request.getShareData().observe(getActivity(), shareBeanMuti -> {
            if (shareBeanMuti.getErrorCode() == 203) {
                List<String> strings = new ArrayList <>();
                List <ShareBeanMuti.DataBean> data = shareBeanMuti.getData();
                for (ShareBeanMuti.DataBean bean : data) {
                    strings.add(bean.getInviteCode());
                }
                BmobQuery <ShareMessageBox> boxBmobQuery = new BmobQuery <>();
                boxBmobQuery.addWhereContainedIn("shareFrom", strings);
                boxBmobQuery.findObjects(new FindListener <ShareMessageBox>() {
                    @Override
                    public void done(List <ShareMessageBox> list, BmobException e) {
                        if (null == e) {
                            ShareMessageFragment.this.data.addAll(list);
                            adapter.notifyDataSetChanged();
                            messageListLoading.setVisibility(View.GONE);
                            rvMsgShare.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
            else {
                Toast.makeText(getActivity(),
                        shareBeanMuti.getErrorCode()+" "+shareBeanMuti.getMsg(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        if (null == currentUser.getObjectId()) {
            return;
        }
        request.getListByUid(currentUser.getObjectId());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_share_message, container, false);
        ButterKnife.bind(this, view);
        messageListLoading.setVisibility(View.VISIBLE);
        rvMsgShare.setVisibility(View.GONE);

        data = new ArrayList <>();
        adapter = new ShareMessageAdapter(data);

        rvMsgShare.setLayoutManager(new LinearLayoutManager(context));
        rvMsgShare.setAdapter(adapter);
        return view;
    }
}
