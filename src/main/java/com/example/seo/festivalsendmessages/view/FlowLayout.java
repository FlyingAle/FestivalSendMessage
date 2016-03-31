package com.example.seo.festivalsendmessages.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seo on 2016/3/2.
 */
public class FlowLayout extends ViewGroup {

    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    private List<Integer> mAllLineHeight = new ArrayList<Integer>();
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mAllLineHeight.clear();
        List<View> mLineView = new ArrayList<View>();
        int lineWight = 0;
        int lineHeight = 0;
        int wight = getWidth();
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++)
        {
            View child = getChildAt(i);
            MarginLayoutParams childParams = (MarginLayoutParams) child.getLayoutParams();
            int childWight = child.getMeasuredWidth() + childParams.leftMargin + childParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + childParams.topMargin + childParams.bottomMargin;
            lineHeight = Math.max(lineHeight,childHeight);
            if(lineWight + childWight > wight - getPaddingLeft() - getPaddingRight())
            {
                mAllLineHeight.add(lineHeight);
                lineWight = 0;
                mAllViews.add(mLineView);
                mLineView = new ArrayList<View>();
            }
            lineWight += childWight;
            mLineView.add(child);
        }
        mAllViews.add(mLineView);
        mAllLineHeight.add(lineHeight);
        //设置childView位置
        int top = getPaddingTop();
        int left = getPaddingLeft();
        for(int i = 0;i<mAllViews.size();i++)
        {
            mLineView = mAllViews.get(i);
            lineHeight = mAllLineHeight.get(i);
            for(int j =0;j<mLineView.size();j++)
            {
                View child = mLineView.get(j);
                if(child.getVisibility() == View.GONE)
                {
                    continue;
                }
                MarginLayoutParams childParam = (MarginLayoutParams) child.getLayoutParams();
                int childTop = top + childParam.topMargin;
                int childLeft = left + childParam.leftMargin;
                int childRight = childLeft + child.getMeasuredWidth();
                int childBottom = childTop + child.getMeasuredHeight();
                child.layout(childLeft,childTop,childRight,childBottom);
                left += child.getMeasuredWidth() + childParam.leftMargin + childParam.bottomMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    //为当前viewgroup设定一个LayoutParams
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    //测量当前viewgroup的width & height
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取测量模式
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //match_parent
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //wrap_content
        int width = 0;
        int height =0;
        //每行width & height
        int lineWidth = 0;
        int lineHeight = 0;
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++)
        {
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams childParams = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + childParams.leftMargin + childParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + childParams.topMargin + childParams.bottomMargin;
            if(lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight())
            {
                width = Math.max(lineWidth,width);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            }
            else
            {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }
            if(i == childCount-1)
            {
                width = Math.max(lineWidth,width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());
    }
}
