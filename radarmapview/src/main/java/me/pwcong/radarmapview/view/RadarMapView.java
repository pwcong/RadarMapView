package me.pwcong.radarmapview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import me.pwcong.radarmapview.R;
import me.pwcong.radarmapview.model.RadarMapEntry;

/**
 * Created by pwcong on 2016/9/9.
 */
public class RadarMapView extends View {

    ArrayList<RadarMapEntry> entries;
    boolean updated     =   false;

    Paint paint;

    int width;
    int height;

    float margin;
    float radius;


    /**     以下为可修改参数    **/

    //雷达图分段数
    int division        =   4;
    //字体大小
    float textSize      =   20.0f;
    //字体颜色
    int textColor       =   Color.GRAY;
    //线条颜色
    int lineColor       =   Color.GRAY;
    //填充区颜色
    int fillColor       =   0xcc3F51B5;
    //线条粗度
    float strokeWidth   =   1.0f;

    //当前动作数值
    long current        =   0;
    //每次绘制变化增量
    long increment      =   3;
    //每次绘制延迟时间
    long delayTimes     =   10;



    public RadarMapView(Context context) {
        super(context);
    }

    public RadarMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        updated=false;
        initAttrs(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        height=h;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        initCanvas(canvas);

        if(entries==null||entries.isEmpty()){
            return;
        }

        if(!updated){

            initVariable();
            initData();
            updated=true;
        }

        if(current<90){
            current+=increment;
            postInvalidateDelayed(delayTimes);
        }

        drawRadarMap(canvas);
        drawRadarMapEntry(canvas);
        drawRadarMapName(canvas);


    }

    private void drawRadarMap(Canvas canvas){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(lineColor);
        paint.setStrokeWidth(strokeWidth);

        Path path=new Path();

        for(int i=0;i<entries.size();i++){

            RadarMapEntry entry = entries.get(i);

            path.moveTo(0,0);
            path.lineTo((float)(radius*Math.cos(Math.toRadians(entry.getAngle()))),
                    (float)(radius*Math.sin(Math.toRadians(entry.getAngle()))));


        }

        for(int i=0;i<division;i++){

            path.moveTo((float)(radius*Math.cos(Math.toRadians(entries.get(0).getAngle()))*(i+1)/division),
                    (float)(radius*Math.sin(Math.toRadians(entries.get(0).getAngle()))*(i+1)/division));

            for(int j=1;j<entries.size();j++){

                path.lineTo((float)(radius*Math.cos(Math.toRadians(entries.get(j).getAngle()))*(i+1)/division),
                        (float)(radius*Math.sin(Math.toRadians(entries.get(j).getAngle()))*(i+1)/division));

            }

            path.lineTo((float)(radius*Math.cos(Math.toRadians(entries.get(0).getAngle()))*(i+1)/division),
                    (float)(radius*Math.sin(Math.toRadians(entries.get(0).getAngle()))*(i+1)/division));
        }

        canvas.drawPath(path,paint);


    }

    private void drawRadarMapEntry(Canvas canvas){

        float ratio=(float)Math.sin(Math.toRadians(current));

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(fillColor);

        Path path=new Path();
        path.moveTo(entries.get(0).getX() * ratio,entries.get(0).getY() * ratio);

        for(int i=1;i<entries.size();i++){

            RadarMapEntry entry = entries.get(i);
            path.lineTo(entry.getX()*ratio,entry.getY()*ratio);

        }

        path.close();

        canvas.drawPath(path,paint);


    }

    private void drawRadarMapName(Canvas canvas){

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(textColor);
        paint.setTextSize(textSize);


        for(RadarMapEntry entry:entries){

            float v = paint.measureText(entry.getName());

            canvas.drawText(entry.getName(),(radius+0.6f*v)*(float)(Math.cos(Math.toRadians(entry.getAngle())))-0.55f*v,
                    (radius+textSize)*(float)(Math.sin(Math.toRadians(entry.getAngle())))+0.3f*textSize,paint);


        }

    }

    private void initCanvas(Canvas canvas){

        canvas.translate(width/2,height/2);

    }

    private void initVariable(){

        int min=width<height?width:height;

        margin=3f*textSize;
        radius=min/2-margin;

    }

    private void initData(){

        float eachAngle = 360.0f / entries.size();

        float max=0;

        for(RadarMapEntry entry:entries){
            if(entry.getValue()>max)
                max=entry.getValue();
        }

        for (int i=0;i<entries.size();i++){

            RadarMapEntry entry = entries.get(i);

            entry.setAngle(eachAngle*i);

            float ratio = entry.getValue()/max;
            float radius= this.radius - 0.1f * margin;

            float x = (float)(radius * Math.cos(Math.toRadians(entry.getAngle())) * ratio );
            entry.setX(x);

            float y = (float)(radius * Math.sin(Math.toRadians(entry.getAngle())) * ratio );
            entry.setY(y);

        }


    }

    private void initAttrs(AttributeSet attrs){

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RadarMapView);

        division = typedArray.getInteger(R.styleable.RadarMapView_division,4);
        textSize = typedArray.getDimension(R.styleable.RadarMapView_textSize,20.0f);
        textColor = typedArray.getColor(R.styleable.RadarMapView_textColor,Color.GRAY);
        lineColor = typedArray.getColor(R.styleable.RadarMapView_lineColor,Color.GRAY);
        fillColor  = typedArray.getColor(R.styleable.RadarMapView_fillColor, 0xcc3F51B5);
        strokeWidth   = typedArray.getFloat(R.styleable.RadarMapView_strokeWidth,1.0f);

        typedArray.recycle();

    }

    /**
     * 设置展示数据
     * @param entries ArrayList<RadarMapEntry>
     */
    public void setData(ArrayList<RadarMapEntry> entries){

        this.entries=entries;
        updated=false;
        postInvalidate();

    }

    /**
     * 设置雷达图分段数
     * @param division int
     */
    public void setDivision(int division) {
        this.division = division;
    }

    /**
     * 设置字体大小
     * @param textSize float
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    /**
     * 设置字体颜色
     * @param textColor int
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * 设置雷达图线条颜色
     * @param lineColor int
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * 设置雷达图填充颜色
     * @param fillColor int
     */
    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * 设置雷达图线条粗度
     * @param strokeWidth float
     */
    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }
}
