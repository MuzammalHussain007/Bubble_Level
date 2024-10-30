package com.orbitalsonic.bubblelevel.widget

import android.content.Context
import android.graphics.*
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.orbitalsonic.bubblelevel.R

class AccelerometerDrawer(context: Context, private val isSimple: Boolean) : ViewDrawer<PointF?> {
    private var zPos: Float=0f
    private var horizentalLinePaint: Paint
    private val pathPaint: Paint
    private val ballPaint: Paint
    private val plusPaint: Paint

    private var path: Path? = null
    private var xPos = 0f
    private var yPos = 0f
    private val center: Point
    private var radius = 0
    private val plusRadius: Float

    override fun layout(width: Int, height: Int) {
        radius = width / 8
        center[width / 2] = height / 2

        if (path == null) {
            val radius = width / 2f - width * 0.03f
            path = Path()
            path!!.moveTo(center.x - plusRadius, center.y.toFloat())
            path!!.lineTo(center.x + plusRadius, center.y.toFloat())
            path!!.moveTo(center.x.toFloat(), center.y - plusRadius)
            path!!.lineTo(center.x.toFloat(), center.y + plusRadius)
            if (!isSimple) {
                path!!.addCircle(center.x.toFloat(), center.y.toFloat(), radius, Path.Direction.CCW)
            }
        }
    }

    override fun draw(canvas: Canvas?) {
        //Draw grid
        canvas!!.drawPath(path!!, pathPaint)
        //Draw ball
       // canvas.drawCircle(center.x - xPos, center.y + yPos, radius.toFloat(), ballPaint)

        canvas?.save()

        canvas?.rotate(zPos, center.x - xPos, center.y + yPos)


        canvas.drawLine(
            center.x - xPos - radius, center.y + yPos,
            center.x - xPos + radius, center.y + yPos,
            horizentalLinePaint
        )

        canvas?.restore()



        canvas.drawLine(
            center.x - xPos - plusRadius, center.y + yPos,
            center.x - xPos + plusRadius, center.y + yPos,
            plusPaint
        )

        canvas.drawLine(
            center.x - xPos, center.y + yPos - plusRadius,
            center.x - xPos, center.y + yPos + plusRadius,
            plusPaint
        )






    }

    override fun update(value: PointF?,azimuth: Float) {
        xPos = value!!.x
        yPos = value.y
        zPos = azimuth
    }

    init {
        val gridColor: Int = ContextCompat.getColor(context,R.color.dayNightColor)
        val ballColor: Int = ContextCompat.getColor(context,R.color.white)
        pathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        pathPaint.strokeWidth = 1f
        pathPaint.style = Paint.Style.STROKE
        pathPaint.color = gridColor
        ballPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        ballPaint.style = Paint.Style.FILL
        ballPaint.color = ballColor
        center = Point(0, 0)


        horizentalLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, context.resources.displayMetrics)
            style = Paint.Style.STROKE
            color = Color.RED
        }

        plusPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2f, context.resources.displayMetrics)
            style = Paint.Style.STROKE
            color = Color.WHITE
        }

        radius = 0
        plusRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, context.resources.displayMetrics)
    }
}