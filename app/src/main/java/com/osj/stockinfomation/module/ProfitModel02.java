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
//public class ProfitModel02 extends AndroidViewModel {
//    public final ProfitRepository02 profitRepository02;
//
//    @SuppressWarnings({"FieldCanBeLocal"})
//    public MutableLiveData<ProfitDAO> profitData = new MutableLiveData<>();
//    public ProfitModel02(@NonNull Application application) {
//        super(application);
//        profitRepository02 = new ProfitRepository02();
//
//    }
//
//    public MutableLiveData<ProfitDAO> getProfitData(String date){
//        profitData = loadSpotData(date);
//        return profitData;
//    }
//    public MutableLiveData<ProfitDAO> loadSpotData(String date){
//        return ProfitRepository02.getInstance().getProfitData(date);
//    }
//
//    public LiveData<ProfitDAO> getData(){ return profitData;}
//
//
//
//
//}
