package com.osj.stockinfomation.util;


import android.widget.GridView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;


/**
 * Created by 김창혁, NomadSoft.Inc on 2018-07-06.
 */

/**
 * 기존의 Refresh가 마음에 들지 않는다는 의견이 많아서 기존의 로직을 해치지 않는 선에서 페이징 처리
 * onPaging는 ex) loadList(false)
 */
public class PagingUtil {


    /**
     * nestedScrollView 의 페이징 처리
     *
     * @param nestedScrollView
     * @param recyclerView
     * @param swipyRefreshLayout
     * @param onRefreshCallback
     */
    public static void onRefreshForNested(NestedScrollView nestedScrollView, final RecyclerView recyclerView, final SwipyRefreshLayout swipyRefreshLayout, final onPaging onRefreshCallback) {
        try {

            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                        int pastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        if (!swipyRefreshLayout.isRefreshing()) {
                            if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                                swipyRefreshLayout.setRefreshing(true);
                                onRefreshCallback.onPaging();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * nestedScrollView 의 페이징 처리
     * 가끔 두개의 recyclerView가 하나에 묶여서 조건에 따라 움직이는 케이스가 존재해서
     * 그 경우 조건이 true면 1번
     * false면 2번을 refresh한다.
     *
     * @param nestedScrollView
     * @param recyclerView1
     * @param recyclerView2
     * @param swipyRefreshLayout
     * @param onRefreshCallback
     */
    public static void onRefreshForNestedTwoRecyclerView(NestedScrollView nestedScrollView, final RecyclerView recyclerView1, final RecyclerView recyclerView2, final SwipyRefreshLayout swipyRefreshLayout,
                                                         final onPaging onRefreshCallback, final boolean isCondition) {
        try {

            nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                        if (isCondition) {
                            int visibleItemCount = recyclerView1.getLayoutManager().getChildCount();
                            int totalItemCount = recyclerView1.getLayoutManager().getItemCount();
                            int pastVisibleItem = ((LinearLayoutManager) recyclerView1.getLayoutManager()).findFirstVisibleItemPosition();
                            if (!swipyRefreshLayout.isRefreshing()) {
                                if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                                    swipyRefreshLayout.setRefreshing(true);
                                    onRefreshCallback.onPaging();
                                }
                            }
                        } else {
                            int visibleItemCount = recyclerView2.getLayoutManager().getChildCount();
                            int totalItemCount = recyclerView2.getLayoutManager().getItemCount();
                            int pastVisibleItem = ((LinearLayoutManager) recyclerView2.getLayoutManager()).findFirstVisibleItemPosition();
                            if (!swipyRefreshLayout.isRefreshing()) {
                                if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                                    swipyRefreshLayout.setRefreshing(true);
                                    onRefreshCallback.onPaging();
                                }
                            }
                        }

                    }
                }
            });

        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    /**
     * recycler의 페이징 처리
     * 이때는 nestedScrollingEnabled 상태가 true여야 가능. (따로 설정하지 않았다면 true)
     */
    public static void onRefreshForRecyclerView(RecyclerView recyclerView, final SwipyRefreshLayout swipyRefreshLayout, final onPaging onRefreshCallback) {
        try {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) {
                        int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                        int pastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        if (!swipyRefreshLayout.isRefreshing()) {
                            if ((visibleItemCount + pastVisibleItem) >= totalItemCount) {
                                swipyRefreshLayout.setRefreshing(true);
                                onRefreshCallback.onPaging();
                            }
                        }
                    }
                }
            });


        } catch (Exception e) {
            ErrorController.showError(e);
        }
    }

    public interface onPaging {
        void onPaging();
    }
}
