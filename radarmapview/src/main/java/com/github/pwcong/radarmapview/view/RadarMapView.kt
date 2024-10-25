package com.github.pwcong.radarmapview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.github.pwcong.radarmapview.R
import com.github.pwcong.radarmapview.entry.RadarMapEntry
import kotlin.math.cos
import kotlin.math.sin


class RadarMapView : View {
    private var entries: List<RadarMapEntry>? = null
    private var updated: Boolean = false

    private var paint: Paint? = null

    private var width: Int = 0
    private var height: Int = 0

    private var margin: Float = 0f
    private var radius: Float = 0f


    /**     以下为可修改参数     */ //雷达图分段数
    private var division: Int = 4

    //字体大小
    private var textSize: Float = 20.0f

    //字体颜色
    private var textColor: Int = Color.GRAY

    //线条颜色
    private var lineColor: Int = Color.GRAY

    //填充区颜色
    private var fillColor: Int = -0x33c0ae4b

    //线条粗度
    private var strokeWidth: Float = 1.0f

    //当前动作数值
    private var current: Long = 0

    //每次绘制变化增量
    private var increment: Long = 3

    //每次绘制延迟时间
    private var delayTimes: Long = 10

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        updated = false
        initAttrs(attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width = MeasureSpec.getSize(widthMeasureSpec)
        height = MeasureSpec.getSize(heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w
        height = h
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        initCanvas(canvas)

        if (entries == null || entries!!.isEmpty()) {
            return
        }

        if (!updated) {
            initVariable()
            initData()
            updated = true
        }

        if (current < 90) {
            current += increment
            postInvalidateDelayed(delayTimes)
        }

        drawRadarMap(canvas)
        drawRadarMapEntry(canvas)
        drawRadarMapName(canvas)
    }

    private fun drawRadarMap(canvas: Canvas) {
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.style = Paint.Style.STROKE
        paint!!.color = lineColor
        paint!!.strokeWidth = strokeWidth

        val path: Path = Path()

        for (i in entries!!.indices) {
            val entry = entries!![i]

            path.moveTo(0F, 0F)
            path.lineTo(
                (radius * cos(Math.toRadians(entry.angle.toDouble()))).toFloat(),
                (radius * sin(Math.toRadians(entry.angle.toDouble()))).toFloat()
            )
        }

        for (i in 0 until division) {
            path.moveTo(
                (radius * cos(Math.toRadians(entries!![0].angle.toDouble())) * (i + 1) / division).toFloat(),
                (radius * sin(Math.toRadians(entries!![0].angle.toDouble())) * (i + 1) / division).toFloat()
            )

            for (j in 1 until entries!!.size) {
                path.lineTo(
                    (radius * cos(Math.toRadians(entries!![j].angle.toDouble())) * (i + 1) / division).toFloat(),
                    (radius * sin(Math.toRadians(entries!![j].angle.toDouble())) * (i + 1) / division).toFloat()
                )
            }

            path.lineTo(
                (radius * cos(Math.toRadians(entries!![0].angle.toDouble())) * (i + 1) / division).toFloat(),
                (radius * sin(Math.toRadians(entries!![0].angle.toDouble())) * (i + 1) / division).toFloat()
            )
        }

        canvas.drawPath(path, paint!!)
    }

    private fun drawRadarMapEntry(canvas: Canvas) {
        val ratio = sin(Math.toRadians(current.toDouble())).toFloat()

        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.style = Paint.Style.FILL
        paint!!.color = fillColor

        val path: Path = Path()
        path.moveTo(entries!![0].x * ratio, entries!![0].y * ratio)

        for (i in 1 until entries!!.size) {
            val entry = entries!![i]
            path.lineTo(entry.x * ratio, entry.y * ratio)
        }

        path.close()

        canvas.drawPath(path, paint!!)
    }

    private fun drawRadarMapName(canvas: Canvas) {
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.color = textColor
        paint!!.textSize = textSize


        for (entry in entries!!) {
            val v = paint!!.measureText(entry.name)

            canvas.drawText(
                entry.name, (radius + 0.6f * v) * cos(Math.toRadians(entry.angle.toDouble()))
                    .toFloat() - 0.55f * v,
                (radius + textSize) * sin(Math.toRadians(entry.angle.toDouble()))
                    .toFloat() + 0.3f * textSize,
                paint!!
            )
        }
    }

    private fun initCanvas(canvas: Canvas) {
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
    }

    private fun initVariable() {
        val min = if (width < height) width else height

        margin = 3f * textSize
        radius = min / 2 - margin
    }

    private fun initData() {
        val eachAngle = 360.0f / entries!!.size

        var max = 0f

        for (entry in entries!!) {
            if (entry.value > max) max = entry.value
        }

        for (i in entries!!.indices) {
            val entry = entries!![i]

            entry.angle = eachAngle * i

            val ratio: Float = entry.value / max
            val radius = this.radius - 0.1f * margin

            val x = (radius * cos(Math.toRadians(entry.angle.toDouble())) * ratio).toFloat()
            entry.x = x

            val y = (radius * sin(Math.toRadians(entry.angle.toDouble())) * ratio).toFloat()
            entry.y = y
        }
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarMapView)

        division = typedArray.getInteger(R.styleable.RadarMapView_division, 4)
        textSize = typedArray.getDimension(R.styleable.RadarMapView_text_size, 20.0f)
        textColor = typedArray.getColor(R.styleable.RadarMapView_text_color, Color.GRAY)
        lineColor = typedArray.getColor(R.styleable.RadarMapView_line_color, Color.GRAY)
        fillColor = typedArray.getColor(R.styleable.RadarMapView_fill_color, -0x33c0ae4b)
        strokeWidth = typedArray.getFloat(R.styleable.RadarMapView_stroke_width, 1.0f)

        typedArray.recycle()
    }

    /**
     * 设置展示数据
     * @param entries List<RadarMapEntry>
    </RadarMapEntry> */
    fun setData(entries: List<RadarMapEntry>?) {
        this.entries = entries
        updated = false
        postInvalidate()
    }

    /**
     * 设置雷达图分段数
     * @param division int
     */
    fun setDivision(division: Int) {
        this.division = division
    }

    /**
     * 设置字体大小
     * @param textSize float
     */
    fun setTextSize(textSize: Float) {
        this.textSize = textSize
    }

    /**
     * 设置字体颜色
     * @param textColor int
     */
    fun setTextColor(textColor: Int) {
        this.textColor = textColor
    }

    /**
     * 设置雷达图线条颜色
     * @param lineColor int
     */
    fun setLineColor(lineColor: Int) {
        this.lineColor = lineColor
    }

    /**
     * 设置雷达图填充颜色
     * @param fillColor int
     */
    fun setFillColor(fillColor: Int) {
        this.fillColor = fillColor
    }

    /**
     * 设置雷达图线条粗度
     * @param strokeWidth float
     */
    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
    }
}
