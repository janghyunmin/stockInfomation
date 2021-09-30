//package com.osj.stockinfomation.module;
//
//import androidx.lifecycle.MutableLiveData;
//
//import com.osj.stockinfomation.DAO.ProfitDAO;
//import com.osj.stockinfomation.Http.EndpointMain;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ProfitRepositoryLive {
//    private EndpointMain retrofitService;
//    private MutableLiveData<ProfitDAO> profitData = new MutableLiveData<>();
//    private static ProfitRepositoryLive profitRepositoryLive;
//
//
//    public static ProfitRepositoryLive getInstance(){
//        if(profitRepositoryLive == null){
//            profitRepositoryLive = new ProfitRepositoryLive();
//        }
//        return profitRepositoryLive;
//    }
//
//    public ProfitRepositoryLive(){ retrofitService = RetrofitModule.getService();}
//
//    public MutableLiveData<ProfitDAO> getProfitData(String date ){
//        Call<ProfitDAO> profitDAOCall = retrofitService.getProfitList(date);
//        profitDAOCall.enqueue(new Callback<ProfitDAO>() {
//            @Override
//            public void onResponse(Call<ProfitDAO> call, Response<ProfitDAO> response) {
//                profitData.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<ProfitDAO> call, Throwable t) {
//                profitData.postValue(null);
//            }
//        });
//        return profitData;
//    }
//}
