package com.osj.stockinfomation.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.osj.stockinfomation.Adapter.MonthItemAdapter;
import com.osj.stockinfomation.CommonCallback.CommonCallback;
import com.osj.stockinfomation.DAO.ProfitDAO;
import com.osj.stockinfomation.DataModel.ProfitList;
import com.osj.stockinfomation.MVP.CustomerMainPresenter;
import com.osj.stockinfomation.R;
import com.osj.stockinfomation.activity.MainActivity;
import com.osj.stockinfomation.databinding.FragmentThreemonthBinding;
import com.osj.stockinfomation.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ThreeMonthFragment extends Fragment implements View.OnClickListener , SwipeRefreshLayout.OnRefreshListener{
    FragmentThreemonthBinding binding;

    private CustomerMainPresenter mPresenter;
    LinearLayoutManager linearLayoutManager;
    MonthItemAdapter monthItemAdapter;

    ArrayList<ProfitList> profitList = new ArrayList<>();
    Context context;
    MainActivity mainActivity;

    private void initializeViews(){ mainActivity = (MainActivity) getActivity(); }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            mainActivity = (MainActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_threemonth, container, false);
        View root = binding.getRoot();
        context = getActivity();
        initializeViews(); // 뷰 초기화

        mPresenter = new CustomerMainPresenter();
        profitList.clear();

        mPresenter.getProfitList(3, new CommonCallback.SingleObjectCallback<ProfitDAO>() {
            @Override
            public void onSuccess(ProfitDAO result) {
                if(result.getList()!=null){
                    for(int index = 0; index < result.getList().size(); index++){
                        profitList.add(new ProfitList(
                                index,
                                result.getList().get(index).getStockCode(),
                                result.getList().get(index).getStockName(),
                                result.getList().get(index).getmStatus(),
                                result.getList().get(index).getBuyPrice(),
                                result.getList().get(index).getPer(),
                                result.getList().get(index).getDate()
                        ));
                    }
                    initAdapter();
                }
            }

            @Override
            public void onFailed(String fault) {
                LogUtil.logE("fail.." + fault);
            }
        });

        return root;
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {

    }

    public void initAdapter(){
        // recycler item 구분선
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, new LinearLayoutManager(context).getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_item_divider));
        binding.rvMonth.addItemDecoration(dividerItemDecoration);

        monthItemAdapter = new MonthItemAdapter(profitList,getContext());
        binding.rvMonth.setHasFixedSize(true);
        binding.rvMonth.setItemAnimator(new DefaultItemAnimator());
        binding.rvMonth.setNestedScrollingEnabled(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvMonth.setLayoutManager(linearLayoutManager);
        binding.rvMonth.setAdapter(monthItemAdapter);
        monthItemAdapter.notifyDataSetChanged();
    }
    @Override
    public void onResume() {
        super.onResume();
        initAdapter();
    }
}
