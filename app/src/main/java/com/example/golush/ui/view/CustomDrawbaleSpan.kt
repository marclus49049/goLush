package com.example.golush.ui.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.DynamicDrawableSpan
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

class CustomDrawableSpan(val progressDrawable: CircularProgressDrawable, val marginStart: Int) :
    DynamicDrawableSpan() {
    override fun getDrawable(): Drawable = progressDrawable
    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fontMetricsInt: Paint.FontMetricsInt?
    ): Int {
        // get drawable dimensions
        val rect = drawable.bounds
        fontMetricsInt?.let {
            val fontMetrics = paint.fontMetricsInt

            // get a font height
            val lineHeight = fontMetrics.bottom - fontMetrics.top

            //make sure our drawable has height >= font height
            val drHeight = Math.max(lineHeight, rect.bottom - rect.top)

            val centerY = fontMetrics.top + lineHeight / 2

            //adjust font metrics to fit our drawable size
            fontMetricsInt.apply {
                ascent = centerY - drHeight / 2
                descent = centerY + drHeight / 2
                top = ascent
                bottom = descent
            }
        }

        //return drawable width which is in our case = drawable width + margin from text
        return rect.width() + marginStart
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {

        canvas.save()
        val fontMetrics = paint.fontMetricsInt
        // get font height. in our case now it's drawable height
        val lineHeight = fontMetrics.bottom - fontMetrics.top

        // adjust canvas vertically to draw drawable on text vertical center
        val centerY = y + fontMetrics.bottom - lineHeight / 2
        val transY = centerY - drawable.bounds.height() / 2

        // adjust canvas horizontally to draw drawable with defined margin from text
        canvas.translate(x + marginStart, transY.toFloat())

        drawable.draw(canvas)

        canvas.restore()
    }
}