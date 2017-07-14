package com.xjk.android.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.xjk.android.ui.evaluator.SpiderEvaluator;

/**
 * Created by xxx on 2016/7/6.
 * 1.绘制六边形
 * 2.绘制直线
 * 3.绘制文本(坐标系,绘制文本坐标为基线位置)
 * 4.绘制能力值(设置画笔的填充模式去绘制边和形状内部)
 */
public class SpiderView extends View{

    private int count = 6;                //数据个数
    private float angle = (float) (Math.PI * 2 / count);
    private float radius;                 //网格最大半径
    private int centerX;                  //中心X
    private int centerY;                  //中心Y
    private String[] titles = {"a","b","c","d","e","f"};
    private double[] data = {100,60,60,60,100,50}; //各维度分值
    private float maxValue = 100;           //数据最大值
    private Paint mainPaint;                //雷达区画笔
    private Paint valuePaint;               //数据区画笔
    private Paint textPaint;                //文本画笔
    private boolean isStartAnimator;        //动画是否已经开始

    public SpiderView(Context context) {
        this(context,null);
    }

    public SpiderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpiderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mainPaint = new Paint();
        mainPaint.setColor(Color.GRAY);
        mainPaint.setStrokeWidth(2);
        mainPaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(20);

        valuePaint = new Paint();
        valuePaint.setColor(Color.BLUE);
    }

//    public double[] getData() {
//        return data;
//    }

    public void setData(double[] data) {
        this.data = data;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        radius = Math.min(w,h) / 2 * 0.9f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawText(canvas);
        drawRegion(canvas);
        if(!isStartAnimator){
            startAnimator();
            isStartAnimator = true;
        }
    }

    private void startAnimator(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofObject(this, "data",
                new SpiderEvaluator(), new double[data.length], data);
        objectAnimator.setDuration(1500);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    private void drawRegion(Canvas canvas){
        Path path = new Path();
        valuePaint.setAlpha(255);
        for (int i = 0; i < data.length; i++) {
            float curR = (float) (data[i] / maxValue * radius);
            float x = (float) (centerX + curR * Math.cos(angle * i));
            float y = (float) (centerY + curR * Math.sin(angle * i));
            if(i == 0){
                path.moveTo(x,y);
            }else {
                path.lineTo(x,y);
            }
            canvas.drawCircle(x,y,10,valuePaint);
        }
        path.close();

        valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);
        valuePaint.setAlpha(127);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path,valuePaint);
    }

    private void drawText(Canvas canvas){
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
            if(angle * i >= 0 && angle * i <= Math.PI / 2){
                canvas.drawText(titles[i],x,y,textPaint);
            }else if(angle * i > Math.PI / 2 && angle * i <= Math.PI){
                float dis = textPaint.measureText(titles[i]);
                canvas.drawText(titles[i],x-dis,y,textPaint);
            }else if(angle * i >= Math.PI && angle * i < Math.PI * 3 / 2){
                float dis = textPaint.measureText(titles[i]);
                canvas.drawText(titles[i],x-dis,y,textPaint);
            }else {
                canvas.drawText(titles[i],x,y,textPaint);
            }
        }
    }

    private void drawLines(Canvas canvas){
        for (int i = 0; i < 6; i++) {
            float x = (float) (centerX + radius * Math.cos(angle * i));
            float y = (float) (centerY + radius * Math.sin(angle * i));
            canvas.drawLine(centerX,centerY,x,y,mainPaint);
        }
    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / (count - 1);//r是蜘蛛丝之间的间距
        for (int i = 1; i < count; i++) {//中心点不用绘制
            float curR = r * i;//当前半径
            path.reset();
            for (int j = 0; j < count; j++) {
                if(j == 0){
                    path.moveTo(centerX + curR, centerY);
                }else {
                    //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    path.lineTo(x,y);
                }
            }
            path.close();//闭合路径
            canvas.drawPath(path,mainPaint);
        }
    }
}
