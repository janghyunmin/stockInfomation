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
//public class ProfitModel04 extends AndroidViewModel {
//    public final ProfitRepository04 profitRepository04;
//
//    @SuppressWarnings({"FieldCanBeLocal"})
//    public MutableLiveData<ProfitDAO> profitData = new MutableLiveData<>();
//    public ProfitModel04(@NonNull Application application) {
//        super(application);
//        profitRepository04 = new ProfitRepository04();
//
//    }
//
//    public MutableLiveData<ProfitDAO> getProfitData(String date){
//        profitData = loadSpotData(date);
//        return profitData;
//    }
//    public MutableLiveData<ProfitDAO> loadSpotData(String date){
//        return ProfitRepository04.getInstance().getProfitData(date);
//    }
//
//    public LiveData<ProfitDAO> getData(){ return profitData;}
//
//
//
//
//}
