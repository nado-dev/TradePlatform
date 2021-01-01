package com.commodity.trade.network;


import com.commodity.trade.entity.demo.InviteBean;
import com.commodity.trade.entity.demo.TestBean;
import com.commodity.trade.entity.share.ShareBean;
import com.commodity.trade.entity.share.ShareBeanMuti;
import com.commodity.trade.entity.share.ShareBeforeAfterBean;
import com.commodity.trade.entity.share.ShareIntegerBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "http://62.234.57.125:3000/";
    String TEST_LOCALHOST_8080 = "http://localhost:8080/";
    String TEST_MY_PC = "http://192.168.1.111:8080/";

//    @GET("login/cellphone")
//    Observable <TestBean> login(@Query("phone") String phone, @Query("password") String password);


    @GET("invite/test")
    Observable<TestBean> testApi();


    /**
     * 用户填写邀请码进行注册
     *
     * @param inviteCode 邀请码 6位
     * @param id uid 10位
     * @param uName 用户名
     * @param userType 0 1 2
     * @return Observable<InviteBean>
     */
    @GET("invite/invite")
    Observable<InviteBean> getInvited(@Query("invite_code") String inviteCode,
                                      @Query("mid") String id,
                                      @Query("user_name") String uName,
                                      @Query("user_type") int userType);

    /**
     * 邀请
     *
     * @param inviteCode 邀请码 6位 (3+3)
     * @param shareId shareId
     * @param userName 用户名
     * @param userId  uid
     * @param userType 0 1 2
     * @return Observable<ShareBean>
     */
    @GET("share/invite")
    Observable<ShareBean> getShared(@Query("invite_code") String inviteCode,
                                    @Query("share_id") String shareId,
                                    @Query("user_name") String userName,
                                    @Query("user_id") String userId,
                                    @Query("user_type") int userType);

    /**
     * @param shareCode 邀请码
     * @return Observable<ShareBean>
     */
    @GET("share/code_info")
    Observable<ShareBean> findInviteCodeInfo(@Query("invite_code") String shareCode);

    /**
     * @param uid uid
     * @return Observable<List<ShareBeanMuti>>
     */
    @GET("share/uid")
    Observable<ShareBeanMuti> findUidInfo(@Query("uid") String uid);

    /**
     * @param shareId uid
     * @return Observable<List<ShareBeanMuti>>
     */
    @GET("share/share_id")
    Observable<ShareBeanMuti> findShareIdInfo(@Query("share_id") String shareId);

    @GET("share/finish_count")
    Observable<ShareIntegerBean> findFinishCount(@Query("uid") String uid);

    @GET("share/total_count")
    Observable<ShareIntegerBean> findTotalCount(@Query("uid") String uid);

    @GET("share/chances")
    Observable<ShareBeanMuti> findChances(@Query("uid") String uid);

    @GET("share/status")
    Observable<ShareBeforeAfterBean> findBeforeAfter(@Query("share_id") String shareId, @Query("uid") String uid);

}
