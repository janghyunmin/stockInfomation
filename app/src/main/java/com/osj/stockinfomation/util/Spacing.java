package com.osj.stockinfomation.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Spacing extends RecyclerView.ItemDecoration {
    private int spacing;
    private int topSpacing;

    public Spacing(int mSpacing, int topSpace) {
        this.spacing = mSpacing;
        this.topSpacing = topSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);// Column Index
        int index = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        // Item 포지션
        int position = parent.getChildLayoutPosition(view);
        if (index == 0) {
            //좌측 Spacing 절반
            outRect.right = spacing/ 2;
        } else {
            //우측 Spacing 절반
            outRect.left = spacing/ 2;
        }
        // 상단 탑 Spacing 맨 위에 포지션 0, 1은 Spacing을 안 줍니다.
        if (position < 2) {
            outRect.top = 0;
        } else {
            outRect.top = topSpacing;
        }
    }
}
