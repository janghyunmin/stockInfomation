//package com.osj.stockinfomation.module;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.osj.stockinfomation.DAO.ProfitDAO;
//
//public class ProfitModelLive extends AndroidViewModel {
//    public final ProfitRepositoryLive profitRepositoryLive;
//
//    @SuppressWarnings({"FieldCanBeLocal"})
//    public MutableLiveData<ProfitDAO> profitData = new MutableLiveData<>();
//    public ProfitModelLive(@NonNull Application application) {
//        super(application);
//        profitRepositoryLive = new ProfitRepositoryLive();
//
//    }
//
//    public MutableLiveData<ProfitDAO> getProfitData(String date ){
//        profitData = loadSpotData(date);
//        return profitData;
//    }
//    public MutableLiveData<ProfitDAO> loadSpotData(String date){
//        return ProfitRepository03.getInstance().getProfitData(date);
//    }
//
//    public LiveData<ProfitDAO> getData(){ return profitData;}
//
//
//
//
//}
