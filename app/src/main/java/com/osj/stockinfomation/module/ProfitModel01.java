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
//public class ProfitModel01 extends AndroidViewModel {
//    public final ProfitRepository01 profitRepository01;
//
//    @SuppressWarnings({"FieldCanBeLocal"})
//    public MutableLiveData<ProfitDAO> profitData = new MutableLiveData<>();
//    public ProfitModel01(@NonNull Application application) {
//        super(application);
//        profitRepository01 = new ProfitRepository01();
//
//    }
//
//    public MutableLiveData<ProfitDAO> getProfitData(String date){
//        profitData = loadSpotData(date);
//        return profitData;
//    }
//    public MutableLiveData<ProfitDAO> loadSpotData(String date){
//        return ProfitRepository01.getInstance().getProfitData(date);
//    }
//
//    public LiveData<ProfitDAO> getData(){ return profitData;}
//
//
//
//
//}
