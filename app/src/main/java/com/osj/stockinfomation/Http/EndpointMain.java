package com.osj.stockinfomation.Http;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface EndpointMain {

//    @GET("/api/info/appdata")
//    Call<AppDataResponseModel> getApplicationData();
//
//    @FormUrlEncoded
//    @POST("/api/admin/att_list")
//    Call<AttendanceListResponseModel> getAttendanceList(@Field("page") int page,
//                                                        @Field("parent_phone") String parent_phone,
//                                                        @Field("keyword") String keyword);
//
//    @FormUrlEncoded
//    @POST("/api/user/fee_list")
//    Call<DepositListResponseModel> getFeeList(@Field("page") int page,
//                                              @Field("parent_phone") String parent_phone,
//                                              @Field("keyword") String keyword);

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
