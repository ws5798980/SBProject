package com.rs.mobile.wportal.activity.market.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;


public class ElasticTwoScrollView extends ScrollView
{
  private static final int size = 4;
  private View inner;
  private float y;
  private Rect normal = new Rect();
  private boolean DOWN = true;

  public ElasticTwoScrollView(Context context)
  {
    super(context);
    init(context);
  }

  private void init(Context context) {
  }

  public ElasticTwoScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  protected void onFinishInflate()
  {
    if (getChildCount() > 0)
      this.inner = getChildAt(0);
  }

  public boolean onTouchEvent(MotionEvent ev)
  {
    if (this.inner == null)
      return super.onTouchEvent(ev);

    commOnTouchEvent(ev);

    return super.onTouchEvent(ev);
  }

  public void commOnTouchEvent(MotionEvent ev) {
    int action = ev.getAction();
    switch (action)
    {
    case 1:
    case 3:
      if (!(this.normal.isEmpty()))
      {
        TranslateAnimation ta = new TranslateAnimation(0F, 0F, 
          this.inner.getTop(), this.normal.top);
        ta.setDuration(200L);
        this.inner.startAnimation(ta);

        this.inner.layout(this.normal.left, this.normal.top, this.normal.right, 
          this.normal.bottom);
        this.normal.setEmpty();
      }
      this.DOWN = true;
      break;
    case 2:
      if (this.DOWN) {
        this.y = ev.getY();
        this.DOWN = false;
      }
      float preY = this.y;
      float nowY = ev.getY();

      int deltaY = (int)(preY - nowY) / 4;

      this.y = nowY;

      if (!(isNeedMove())) return;
      if (this.normal.isEmpty())
      {
        this.normal.set(this.inner.getLeft(), this.inner.getTop(), 
          this.inner.getRight(), this.inner.getBottom());
        return;
      }
      int yy = this.inner.getTop() - deltaY;

      this.inner.layout(this.inner.getLeft(), yy, this.inner.getRight(), 
        this.inner.getBottom() - deltaY);
    }
  }

  public boolean isNeedMove()
  {
    int offset = this.inner.getMeasuredHeight() - getHeight();
    int scrollY = getScrollY();

    return ((scrollY == 0) || (scrollY == offset));
  }
}