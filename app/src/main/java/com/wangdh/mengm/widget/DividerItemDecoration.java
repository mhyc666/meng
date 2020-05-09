package com.wangdh.mengm.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * RecyclerView分割线
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    private int mDividerOrientation;
    private int mDividerSize;
    private Paint mPaint;

    public DividerItemDecoration(Context context, int orientation) {
        mDividerOrientation = orientation;

        mDividerSize = dp2px(context, 1);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xFFE3E3E3);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent,
                       RecyclerView.State state) {
        if (mDividerOrientation == VERTICAL) {
            drawVertical(canvas, parent);     // 垂直方向间隔高度为分割线高度
        } else {
            drawHorizontal(canvas, parent);    // 水平方向间隔宽度为分割线宽度
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mDividerOrientation == HORIZONTAL) {
            outRect.set(0, 0, 0, mDividerSize);
        } else {
            outRect.set(0, 0, mDividerSize, 0);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        // 对于水平方向的分割线，两端的位置是不变的，可以直接通过RecyclerView来获取
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        // 这里获取的是一屏的Item数量
        int childCount = parent.getChildCount();

        // 分割线从Item的底部开始绘制，且在最后一个Item底部不绘制
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            // 有的Item布局会设置layout_marginXXX
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDividerSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 绘制纵向分割线原理参考上面的方法
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + layoutParams.rightMargin;
            final int right = left + mDividerSize;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }
}