package com.moe.bootsetting.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.moe.bootsetting.R;

public class ShadowView extends View{
    public static final int LTR=1;
    public static final int RTL=2;
    public static final int TTB=0;
    public static final int BTT=4;
    private float fraction=0;
    private int orientation=TTB;
    private Paint paint;
    private boolean ReadyCheck;
    public ShadowView(Context context){
        this(context,null);
    }
    public ShadowView(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }
    public ShadowView(Context context,AttributeSet attrs,int defStyle){
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.ShadowView);
        orientation=ta.getInt(R.styleable.ShadowView_orientation,TTB);
        ta.recycle();
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        ReadyCheck=true;
    }
    public void setOrientation(int ori){
        this.orientation=ori;
        ReadyCheck=true;
        checkShader();
        invalidate();
    }
    private void checkShader(){
       if(ReadyCheck){
           ReadyCheck=false;
           LinearGradient lg=null;
           int[] colors=new int[]{0x50000000,0x10000000,0x00000000};
           switch(orientation){
               case TTB:
                   lg=new LinearGradient(0,0,0,getMeasuredHeight(),colors,null,LinearGradient.TileMode.REPEAT);
                   break;
               case BTT:
                   lg=new LinearGradient(0,getMeasuredHeight(),0,0,colors,null,LinearGradient.TileMode.REPEAT);
                   break;
               case LTR:
                   lg=new LinearGradient(0,0,getMeasuredWidth(),0,colors,null,LinearGradient.TileMode.REPEAT);
                   break;
               case RTL:
                   lg=new LinearGradient(getMeasuredWidth(),0,0,0,colors,null,LinearGradient.TileMode.REPEAT);
                   break;
           }
          paint.setShader(lg);
       }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int width=0,height=0;
        switch(widthMode){
            case MeasureSpec.AT_MOST:
                width=0;
                break;
            case MeasureSpec.EXACTLY:
                width=widthMeasureSpec;
                break;
            case MeasureSpec.UNSPECIFIED:
                width=widthMeasureSpec;
                break;
        }
        switch (heightMode){
            case MeasureSpec.AT_MOST:
                height=0;
                break;
            case MeasureSpec.EXACTLY:
                height=heightMeasureSpec;
                break;
            case MeasureSpec.UNSPECIFIED:
                height=heightMeasureSpec;
                break;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        checkShader();
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);
    }
}
