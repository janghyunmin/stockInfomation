package com.osj.stockinfomation.MVP;

import android.app.Activity;
import android.util.Log;

import com.osj.stockinfomation.Adapter.AdapterMainContentList;
import com.osj.stockinfomation.Adapter.AdapterMainpage4ContentList;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
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
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAO;
import com.osj.stockinfomation.DAO.ResultMarketConditionsDAOList;
import com.osj.stockinfomation.DAO.SetCategoryLikeDAO;
import com.osj.stockinfomation.DAO.SetLikeDAO;
import com.osj.stockinfomation.DAO.SpotUpDAO;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory2;
import com.osj.stockinfomation.DAO.SpotUpDAOCategory3;
import com.osj.stockinfomation.DAO.VersionDAO;
import com.osj.stockinfomation.Http.EndpointMain;
import com.osj.stockinfomation.Http.NSCallback;
import com.osj.stockinfomation.Http.RetrofitSender2;
import com.osj.stockinfomation.Http.Util_osj;
import com.osj.stockinfomation.util.ErrorController;

/**
 * Created by YJK on 2019-03-14
 **/
public class CustomerMainPresenter {

    private AdapterMainpage4ContentList adapterMainpage4ContentList;

    public interface RowClick{
        void rowClick(ResultMarketConditionsDAOList item, String contentType);

        void rowCLick(ResultMarketConditionsDAOList item);
    }

    public interface FavClick{
        void FavClick(ResultMarketConditionsDAOList item);
    }

    private int mainPage1 = 1;
    private int mainPage2 = 1;
    private int mainPage3 = 1;

    public void loadList(final boolean isFirst, final Activity context, String board_type, final CommonCallback.SingleObjectCallback<ResultMarketConditionsDAO> callback) {

        int tempPage = 1;

        try {
            if(board_type.equals("contents01")){
                if (isFirst) {
                    mainPage1 = 1;
                } else {
                    mainPage1++;
                }
                tempPage = mainPage1;
            } else if(board_type.equals("contents02")){
                if (isFirst) {
                    mainPage2 = 1;
                } else {
                    mainPage2++;
                }
                tempPage = mainPage2;
            }

            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getMainContentList(tempPage, Util_osj.getAndroidId(context), board_type).enqueue(new NSCallback<ResultMarketConditionsDAO>(new NSCallback.SingleObjectCallback<ResultMarketConditionsDAO>() {
                @Override
                public void onSuccess(ResultMarketConditionsDAO result) {
                    if (result.getResultCode().equals("00")) {
                        callback.onSuccess(result);
                    } else {
                        callback.onFailed(result.getMessage());
                    }
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

//            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getMainContentList(tempPage, Util_osj.getAndroidId(context), board_type).enqueue(new NSCallback<ResultMarketConditionsDAO>(new NSCallback.SingleObjectCallback<ResultMarketConditionsDAO>() {
//                @Override
//                public void onSuccess(ResultMarketConditionsDAO result) {
//
//                    if (result.getResultCode().equals("00")) {
//                        if (result.getList() != null && result.getList().size() > 0) {
//                            if (isFirst) {
//
//                                callback.onSuccess(result.getList());
//                            } else {
//                                adapterMainContentList.addAll(result.getList());
//                                callback.onSuccess(null);
//                            }
//                        } else {
//                            callback.onSuccess(null);
//                        }
//                    } else {
//                        callback.onFailed(result.getResultCode());
//                    }
//                }
//
//                @Override
//                public void onFailed(String fault) {
//                    callback.onFailed(fault);
//                }
//            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void loadList1(final boolean isFirst, final Activity context, String board_type, final CommonCallback.SingleObjectCallback<AdapterMainpage4ContentList> callback, RowClick rowClick) {

        int tempPage = 1;

        try {
            if (isFirst) {
                mainPage3 = 1;
            } else {
                mainPage3++;
            }
            tempPage = mainPage3;

            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getMainContentList(tempPage, Util_osj.getAndroidId(context), board_type).enqueue(new NSCallback<ResultMarketConditionsDAO>(new NSCallback.SingleObjectCallback<ResultMarketConditionsDAO>() {
                @Override
                public void onSuccess(ResultMarketConditionsDAO result) {

                    if (result.getResultCode().equals("00")) {
                        if (result.getList() != null && result.getList().size() > 0) {
                            if (isFirst) {
                                adapterMainpage4ContentList = new AdapterMainpage4ContentList(context, result.getList(), new AdapterMainpage4ContentList.onClickCallback() {
                                    @Override
                                    public void onClick(ResultMarketConditionsDAOList item) {
                                        rowClick.rowCLick(item);
                                    }
                                });
                                callback.onSuccess(adapterMainpage4ContentList);
                            } else {
                                adapterMainpage4ContentList.addAll(result.getList());
                                callback.onSuccess(null);
                            }
                        } else {
                            callback.onSuccess(null);
                        }
                    } else {
                        callback.onFailed(result.getResultCode());
                    }
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getVersion(final CommonCallback.SingleObjectCallback<VersionDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getVersion("android").enqueue(new NSCallback<VersionDAO>(new NSCallback.SingleObjectCallback<VersionDAO>() {
                @Override
                public void onSuccess(VersionDAO result) {

                    if (result.getResultCode().equals("00")) {
                        callback.onSuccess(result);
                    } else {
                        callback.onFailed(result.getMessage());
                    }
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setPushToken(final CommonCallback.SingleObjectCallback<VersionDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getVersion("android").enqueue(new NSCallback<VersionDAO>(new NSCallback.SingleObjectCallback<VersionDAO>() {
                @Override
                public void onSuccess(VersionDAO result) {

                    if (result.getResultCode().equals("00")) {
                        callback.onSuccess(result);
                    } else {
                        callback.onFailed(result.getMessage());
                    }
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getCategory01(String mb_id, final CommonCallback.SingleObjectCallback<SpotUpDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getCategory01(mb_id).enqueue(new NSCallback<SpotUpDAO>(new NSCallback.SingleObjectCallback<SpotUpDAO>() {
                @Override
                public void onSuccess(SpotUpDAO result) {

                    if (result.getResultCode().equals("00")){
                        callback.onSuccess(result);
                    }
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getCategory02(String ca_id, String code, final CommonCallback.SingleObjectCallback<SpotUpDAOCategory2> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getCategory02(ca_id, code).enqueue(new NSCallback<SpotUpDAOCategory2>(new NSCallback.SingleObjectCallback<SpotUpDAOCategory2>() {
                @Override
                public void onSuccess(SpotUpDAOCategory2 result) {

                    if (result.getResultCode().equals("00")){
                        callback.onSuccess(result);
                    }
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getCategory03(Activity activity, String ca_id, String code, final CommonCallback.SingleObjectCallback<SpotUpDAOCategory3> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getCategory03(Util_osj.getAndroidId(activity), ca_id, code).enqueue(new NSCallback<SpotUpDAOCategory3>(new NSCallback.SingleObjectCallback<SpotUpDAOCategory3>() {
                @Override
                public void onSuccess(SpotUpDAOCategory3 result) {

                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setLike(final Activity context, String wr_id, String bo_table, final CommonCallback.SingleObjectCallback<SetLikeDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).setLike(Util_osj.getAndroidId(context), wr_id, bo_table).enqueue(new NSCallback<SetLikeDAO>(new NSCallback.SingleObjectCallback<SetLikeDAO>() {
                @Override
                public void onSuccess(SetLikeDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getLike(final Activity context, final CommonCallback.SingleObjectCallback<GetLikeDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getLike(Util_osj.getAndroidId(context)).enqueue(new NSCallback<GetLikeDAO>(new NSCallback.SingleObjectCallback<GetLikeDAO>() {
                @Override
                public void onSuccess(GetLikeDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setCategoryLike(final Activity context, String ca_id2, String ca_id3, final CommonCallback.SingleObjectCallback<SetCategoryLikeDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).setCategolyLike(Util_osj.getAndroidId(context), ca_id2, ca_id3).enqueue(new NSCallback<SetCategoryLikeDAO>(new NSCallback.SingleObjectCallback<SetCategoryLikeDAO>() {
                @Override
                public void onSuccess(SetCategoryLikeDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getLatelyCategory(final Activity context, final CommonCallback.SingleObjectCallback<SpotUpDAOCategory2> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getLatelyCategory(Util_osj.getAndroidId(context)).enqueue(new NSCallback<SpotUpDAOCategory2>(new NSCallback.SingleObjectCallback<SpotUpDAOCategory2>() {
                @Override
                public void onSuccess(SpotUpDAOCategory2 result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getContentView(final Activity context, String board_type, String wr_id, final CommonCallback.SingleObjectCallback<GetContentsViewDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getContentsView(Util_osj.getAndroidId(context), board_type, wr_id).enqueue(new NSCallback<GetContentsViewDAO>(new NSCallback.SingleObjectCallback<GetContentsViewDAO>() {
                @Override
                public void onSuccess(GetContentsViewDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getContentViewType2(final Activity context, String board_type, String wr_id, final CommonCallback.SingleObjectCallback<GetContentsViewType2DAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getContentsViewType2(Util_osj.getAndroidId(context), board_type, wr_id).enqueue(new NSCallback<GetContentsViewType2DAO>(new NSCallback.SingleObjectCallback<GetContentsViewType2DAO>() {
                @Override
                public void onSuccess(GetContentsViewType2DAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setProfile(final Activity context, String nickName, final CommonCallback.SingleObjectCallback<BaseDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).setProfile(Util_osj.getAndroidId(context), nickName).enqueue(new NSCallback<BaseDAO>(new NSCallback.SingleObjectCallback<BaseDAO>() {
                @Override
                public void onSuccess(BaseDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getAlram(final Activity context, final CommonCallback.SingleObjectCallback<GetAlramDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getAlram(Util_osj.getAndroidId(context)).enqueue(new NSCallback<GetAlramDAO>(new NSCallback.SingleObjectCallback<GetAlramDAO>() {
                @Override
                public void onSuccess(GetAlramDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setAlram(final Activity context, String al_all, String mb_1, String mb_2, String mb_3, final CommonCallback.SingleObjectCallback<BaseDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).setAlram(Util_osj.getAndroidId(context), al_all, mb_1, mb_2, mb_3).enqueue(new NSCallback<BaseDAO>(new NSCallback.SingleObjectCallback<BaseDAO>() {
                @Override
                public void onSuccess(BaseDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getSearchMain(final Activity context, String stx, final CommonCallback.SingleObjectCallback<GetSearchMainDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getSearchMain(Util_osj.getAndroidId(context), stx).enqueue(new NSCallback<GetSearchMainDAO>(new NSCallback.SingleObjectCallback<GetSearchMainDAO>() {
                @Override
                public void onSuccess(GetSearchMainDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setUser(final Activity context, String deviceInfo, String token, final CommonCallback.SingleObjectCallback<BaseDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).setUser(Util_osj.getAndroidId(context), deviceInfo, token).enqueue(new NSCallback<BaseDAO>(new NSCallback.SingleObjectCallback<BaseDAO>() {
                @Override
                public void onSuccess(BaseDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setFreeUpdate(final Activity context, String mb_name, String mb_hp, final CommonCallback.SingleObjectCallback<BaseDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).setFreeUpdate(Util_osj.getAndroidId(context), mb_name, mb_hp).enqueue(new NSCallback<BaseDAO>(new NSCallback.SingleObjectCallback<BaseDAO>() {
                @Override
                public void onSuccess(BaseDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getFreeUpdate(final Activity context, final CommonCallback.SingleObjectCallback<BaseDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getFreeUpdate(Util_osj.getAndroidId(context)).enqueue(new NSCallback<BaseDAO>(new NSCallback.SingleObjectCallback<BaseDAO>() {
                @Override
                public void onSuccess(BaseDAO result) {
                    if (result.getResultCode().equals("99"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void setInquiry(final Activity context, String name, String email, String content, final CommonCallback.SingleObjectCallback<BaseDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).setInquiry(Util_osj.getAndroidId(context), name, email, content).enqueue(new NSCallback<BaseDAO>(new NSCallback.SingleObjectCallback<BaseDAO>() {
                @Override
                public void onSuccess(BaseDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getLikeDetail(final Activity context, String wr_id, String bo_table, final CommonCallback.SingleObjectCallback<GetLikeDetailDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getLikeDetail(wr_id, bo_table).enqueue(new NSCallback<GetLikeDetailDAO>(new NSCallback.SingleObjectCallback<GetLikeDetailDAO>() {
                @Override
                public void onSuccess(GetLikeDetailDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getCategoryLike(final Activity context, final CommonCallback.SingleObjectCallback<GetCategoryLikeFavDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getCategoryLike(Util_osj.getAndroidId(context)).enqueue(new NSCallback<GetCategoryLikeFavDAO>(new NSCallback.SingleObjectCallback<GetCategoryLikeFavDAO>() {
                @Override
                public void onSuccess(GetCategoryLikeFavDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getPushList(final Activity context, String bo_table, final CommonCallback.SingleObjectCallback<GetPushListDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getPushList(Util_osj.getAndroidId(context), bo_table).enqueue(new NSCallback<GetPushListDAO>(new NSCallback.SingleObjectCallback<GetPushListDAO>() {
                @Override
                public void onSuccess(GetPushListDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getPushDetail(String pu_id, String bo_table, final CommonCallback.SingleObjectCallback<GetPushDetailDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getPushDetail(pu_id, bo_table).enqueue(new NSCallback<GetPushDetailDAO>(new NSCallback.SingleObjectCallback<GetPushDetailDAO>() {
                @Override
                public void onSuccess(GetPushDetailDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getPushDelete(final Activity context, String pu_id, String bo_table, final CommonCallback.SingleObjectCallback<BaseDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getPushDelete(Util_osj.getAndroidId(context), pu_id, bo_table).enqueue(new NSCallback<BaseDAO>(new NSCallback.SingleObjectCallback<BaseDAO>() {
                @Override
                public void onSuccess(BaseDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getProfile(final Activity context, final CommonCallback.SingleObjectCallback<GetNickNameDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getProfile(Util_osj.getAndroidId(context)).enqueue(new NSCallback<GetNickNameDAO>(new NSCallback.SingleObjectCallback<GetNickNameDAO>() {
                @Override
                public void onSuccess(GetNickNameDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public void getCheckFree(final Activity context, final CommonCallback.SingleObjectCallback<GetCheckFreeDAO> callback) {
        try{
            RetrofitSender2.initAndGetBaseEndPoint(EndpointMain.class).getCheckFree(Util_osj.getAndroidId(context)).enqueue(new NSCallback<GetCheckFreeDAO>(new NSCallback.SingleObjectCallback<GetCheckFreeDAO>() {
                @Override
                public void onSuccess(GetCheckFreeDAO result) {
                    if (result.getResultCode().equals("00"))
                        callback.onSuccess(result);
                    else
                        callback.onFailed(result.getMessage());
                }

                @Override
                public void onFailed(String fault) {
                    callback.onFailed(fault);
                }
            }));

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }
}
