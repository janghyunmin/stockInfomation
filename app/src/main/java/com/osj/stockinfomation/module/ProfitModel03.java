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
//public class ProfitModel03 extends AndroidViewModel {
//    public final ProfitRepository03 profitRepository03;
//
//    @SuppressWarnings({"FieldCanBeLocal"})
//    public MutableLiveData<ProfitDAO> profitData = new MutableLiveData<>();
//    public ProfitModel03(@NonNull Application application) {
//        super(application);
//        profitRepository03 = new ProfitRepository03();
//
//    }
//
//    public MutableLiveData<ProfitDAO> getProfitData(String date){
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
//
