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
//public class ProfitRepository04 {
//    private EndpointMain retrofitService;
//    private MutableLiveData<ProfitDAO> profitData = new MutableLiveData<>();
//    private static ProfitRepository04 profitRepository04;
//
//    public static ProfitRepository04 getInstance(){
//        if(profitRepository04 == null){
//            profitRepository04 = new ProfitRepository04();
//        }
//        return profitRepository04;
//    }
//
//    public ProfitRepository04(){ retrofitService = RetrofitModule.getService();}
//
//
//    public MutableLiveData<ProfitDAO> getProfitData(String date){
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
//
//}
