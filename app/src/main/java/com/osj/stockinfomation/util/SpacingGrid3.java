package com.osj.stockinfomation.util;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingGrid3 extends RecyclerView.ItemDecoration {
    private int spacing;
    private int topSpacing;

    public SpacingGrid3(int mSpacing, int topSpace) {
        this.spacing = mSpacing;
        this.topSpacing = topSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);// Column Index
        int index = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        // Item 포지션
        int position = parent.getChildLayoutPosition(view);
        if (index % 3 == 0) {
            outRect.right = spacing/ 2;
        } else if(index % 3 == 1) {
            outRect.left = spacing/ 2;
            outRect.right = spacing/ 2;
        } else {
            outRect.left = spacing/ 2;
        }
        // 상단 탑 Spacing 맨 위에 포지션 0, 1은 Spacing을 안 줍니다.
        if (position == 0 || position == 1 || position == 2) {
            outRect.top = 0;
        } else {
            outRect.top = topSpacing;
        }
    }
}
