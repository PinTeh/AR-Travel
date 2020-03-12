package cn.imhtb.ar.travel.fregment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class NotScrollViewPager extends ViewPager {

    public NotScrollViewPager( Context context) {
        super(context);
    }

    public NotScrollViewPager( Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
