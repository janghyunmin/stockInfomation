package com.osj.stockinfomation.Http;

import com.osj.stockinfomation.DAO.BaseDAO;
import com.osj.stockinfomation.DAO.GetAlramDAO;
import com.osj.stockinfomation.DAO.GetCategoryLikeFavDAO;
import com.osj.stockinfomation.DAO.GetCheckFreeDAO;
import com.osj.stockinfomation.DAO.GetContentsViewDAO;
import com.osj.stockinfomation.DAO.GetContentsViewType2DAO;
import com.osj.stockinfomation.DAO.GetLikeDAO;
import com.osj.stockinfomation.DAO.GetLikeDetailDAO;
import com.osj.stockinfomation.DAO.GetNickNameDAO;
import com.osj.stockinfomation.DAO.GetPushDetailDAO;
import com.osj.stockinfomation.DAO.GetPushListDAO;
import com.osj.stockinfomation.DAO.GetSearchMainDAO;
import com.osj.stockinfomation.DAO.IndexDAO;
import com.osj.stockinfomation.DAO.SetCategoryLikeDAO;
import com.osj.stockinfomation.DAO.SetLikeDAO;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAO;
import com.osj.stockinfomation.DAO.SpotUpDAO;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory2;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory3;
import com.osj.stockinfomation.DAO.Response;
import com.osj.stockinfomation.DAO.VersionDAO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EndpointMain {


    /** jhm 2021-09-14 오후 4:09
     * 코스콤 API Direct
     ***/
    @POST("/{marketcode}/index") //코스피 코스닥 지수
    Call<IndexDAO> getIndex(@Query("marketcode") String marketcode);




    @FormUrlEncoded
    @POST("/api/get_version.php")
    Call<VersionDAO> getVersion(@Field("type") String page);

    @FormUrlEncoded
    @POST("/api/set_profile.php")
    Call<BaseDAO> setProfile(@Field("mb_id") String mb_id,
                             @Field("nick_name") String nick_name);

    @FormUrlEncoded
    @POST("/api/get_profile.php")
    Call<GetNickNameDAO> getProfile(@Field("mb_id") String mb_id);


    @FormUrlEncoded
    @POST("/api/set_alarm.php")
    Call<BaseDAO> setAlram(@Field("mb_id") String mb_id,
                           @Field("al_all") String al_all,
                           @Field("mb_1") String mb_1,
                           @Field("mb_2") String mb_2,
                           @Field("mb_3") String mb_3);


    @FormUrlEncoded
    @POST("/api/select_user.php")
    Call<BaseDAO> getUser(@Field("mb_id") String mb_id);



    @FormUrlEncoded
    @POST("/api/set_user.php")
    Call<BaseDAO> setUser(@Field("mb_id") String mb_id,
                          @Field("mb_4") String mb_4,
                            @Field("token") String token);

    @FormUrlEncoded
    @POST("/api/insert_user.php")
    Call<BaseDAO> setInsertUser(@Field("mb_id") String mb_id,
                                @Field("mb_name") String mb_name,
                                @Field("mb_hp") String mb_hp ,
                                @Field("token") String token);


    @FormUrlEncoded
    @POST("/api/set_free_update.php")
    Call<BaseDAO> setFreeUpdate(@Field("mb_id") String mb_id,
                                @Field("mb_name") String mb_name,
                                @Field("mb_hp") String mb_hp);

    @FormUrlEncoded
    @POST("/api/get_free_data.php")
    Call<Response> getFreeUpdate(@Field("mb_id") String mb_id);


    @FormUrlEncoded
    @POST("/api/set_inquiry.php")
    Call<BaseDAO> setInquiry(@Field("mb_id") String mb_id,
                             @Field("wr_name") String wr_name,
                             @Field("wr_email") String wr_email,
                             @Field("wr_content") String wr_content);

    @FormUrlEncoded
    @POST("/api/get_alarm.php")
    Call<GetAlramDAO> getAlram(@Field("mb_id") String mb_id);

    @FormUrlEncoded
    @POST("/api/get_search_main.php")
    Call<GetSearchMainDAO> getSearchMain(@Field("mb_id") String mb_id,
                                         @Field("stx") String stx);

//    @GET("/api/info/appdata")
//    Call<AppDataResponseModel> getApplicationData();
//
    @FormUrlEncoded
    @POST("/api/get_contents.php")
    Call<ResultMarketConditionsDAO> getMainContentList(@Field("page") int page,
                                                       @Field("mb_id") String mb_id,
                                                      @Field("board_type") String board_type);

    @FormUrlEncoded
    @POST("/api/get_contents_view.php")
    Call<GetContentsViewDAO> getContentsView(@Field("mb_id") String mb_id,
                                             @Field("board_type") String board_type,
                                             @Field("wr_id") String wri_id);

    @FormUrlEncoded
    @POST("/api/get_contents_view.php")
    Call<GetContentsViewType2DAO> getContentsViewType2(@Field("mb_id") String mb_id,
                                                           @Field("board_type") String board_type,
                                                           @Field("wr_id") String wri_id);

    @FormUrlEncoded
    @POST("/api/get_category01.php")
    Call<SpotUpDAO> getCategory01(@Field("mb_id") String mb_id);

    @FormUrlEncoded
    @POST("/api/get_category02.php")
    Call<SpotUpDAOCategory2> getCategory02(@Field("ca_id") String ca_id,
                                           @Field("code") String code);

    @FormUrlEncoded
    @POST("/api/get_category03_ori.php")
    Call<SpotUpDAOCategory3> getCategory03(@Field("mb_id") String mb_id,
                                           @Field("ca_id2") String ca_id,
                                           @Field("code") String code);

    @FormUrlEncoded
    @POST("/api/set_like.php")
    Call<SetLikeDAO> setLike(@Field("mb_id") String mb_id,
                             @Field("wr_id") String wr_id,
                             @Field("bo_table") String bo_table);

    @FormUrlEncoded
    @POST("/api/get_like.php")
    Call<GetLikeDAO> getLike(@Field("mb_id") String mb_id);

//    @FormUrlEncoded
//    @POST("/api/get_check_free.php")
//    Call<GetCheckFreeDAO> getCheckFree(@Field("mb_id") String mb_id);

    @FormUrlEncoded
    @POST("/api/get_push_member_loop_lidingcnt.php")
    Call<GetCheckFreeDAO> getCheckFree(@Field("mb_id") String mb_id);

    @FormUrlEncoded
    @POST("/api/set_category_like.php")
    Call<SetCategoryLikeDAO> setCategolyLike(@Field("mb_id") String mb_id,
                                             @Field("ca_id2") String ca_id2,
                                             @Field("ca_id3") String ca_id3);

    @FormUrlEncoded
    @POST("/api/get_lately_category.php")
    Call<SpotUpDAOCategory2> getLatelyCategory(@Field("mb_id") String mb_id);


    @FormUrlEncoded
    @POST("/api/get_like_detail.php")
    Call<GetLikeDetailDAO> getLikeDetail(@Field("wr_id") String wr_id,
                                         @Field("bo_table") String bo_table);

    @FormUrlEncoded
    @POST("/api/get_category_like.php")
    Call<GetCategoryLikeFavDAO> getCategoryLike(@Field("mb_id") String mb_id);


    @FormUrlEncoded
    @POST("/api/get_push_list.php")
    Call<GetPushListDAO> getPushList(@Field("mb_id") String mb_id,
                                     @Field("bo_table") String bo_table);

    @FormUrlEncoded
    @POST("/api/get_push_detail.php")
    Call<GetPushDetailDAO> getPushDetail(@Field("pu_id") String pu_id,
                                         @Field("bo_table") String bo_table);

    @FormUrlEncoded
    @POST("/api/get_push_delete.php")
    Call<BaseDAO> getPushDelete(@Field("mb_id") String mb_id,
                                @Field("pu_id") String pu_id,
                                @Field("bo_table") String bo_table);





    /*
    user_id : String - (유저 아이디)

order_type : Enum(1, 2) - (정렬 순서 : 1(오름차순), 2(내림차순), 기본값: 2)

notice_type: Enum(0, 1) - (게시판 유형 : 0(정보공유방), 1(공지사항))

page : Number - (현재 페이지, 기본값 : 1)

is_mobile: Enum(0, 1) - (모바일 여부: 0(PC), 1(모바일), 기본값: 0)
     */

//    @FormUrlEncoded
//    @POST("/api/admin/notice_list")
//    Call<NotiDataModel> getNotiList(@Field("user_id") String user_id,
//                                    @Field("order_type") int order_type,
//                                    @Field("page") int page,
//                                    @Field("is_mobile") int is_mobile,
//                                    @Field("notice_type") int notice_type,
//                                    @Field("com_seq") int com_seq,
//                                    @Field("itemCountPerPage") int itemCountPerPage);
//
//    @FormUrlEncoded
//    @POST("/api/admin/notice_list/view")
//    Call<BaseResponseModel> setNotiViewCount(@Field("notice_seq") int notice_seq);
//
//    /*
//    com_seq: Integer - (학원 번호)
//
//interval: Integer(Nullable) - (GPS 신호를 보내는 시간 간격(초))
//     */
//    @FormUrlEncoded
//    @POST("/api/admin/car_gps_loc")
//    Call<GPSCustomerReturnModel> getGPS(@Field("com_seq") int com_seq,
//                                        @Field("parent_phone") String parent_phone,
//                                        @Field("interval") int order_type);
//
//    @FormUrlEncoded
//    @POST("/api/user/check_att")
//    Call<BaseResponseModel> checkAttendance(@Field("com_seq") int com_seq,
//                                            @Field("att_number") String att_number,
//                                            @Field("att_img") String att_img);
//
//    /*
//    com_seq: Integer - (학원 번호)
//
//car_number: Integer - (차량 번호)
//
//latitude: Float(10, 6) - (위도)
//
//longitude: Float(10, 6) - (경도)
//
//accuracy: String(Nullable) - (정확도)
//
//type: String(Nullable) - (위치 타입)
//
//user_id: String - (유저 아이디)
//     */
//    @FormUrlEncoded
//    @POST("/api/admin/car_gps_loc/insert")
//    Call<GPSShopReturnModel> setGpsTracker(@Field("com_seq") int com_seq,
//                                           @Field("mobile_uuid") String mobile_uuid,
//                                           @Field("latitude") float latitude,
//                                           @Field("longitude") float longitude,
//                                           @Field("accuracy") String accuracy,
//                                           @Field("type") String type,
//                                           @Field("user_id") String user_id);
//
//    @Multipart
//    @POST("/file")
//    Call<FileUploadResponseModel> sendImageUploadNoAuth(
//            @Part MultipartBody.Part file,
//            @Part("filePath") RequestBody filePath,
//            @Part("useUniqueFileName") RequestBody useUniqueFileName,
//            @Part("useDateFolder") RequestBody useDateFolder,
//            @Part("thumbPath") RequestBody thumbPath,
//            @Part("isProfile") RequestBody isProfile);
//
//    @FormUrlEncoded
//    @POST("/api/user/parent_logout")
//    Call<BaseResponseModel> logout(@Field("parent_phone") String parent_phone,
//                                   @Field("parent_pass") String parent_pass);

}
