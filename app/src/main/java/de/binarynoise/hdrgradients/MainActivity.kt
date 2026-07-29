package de.binarynoise.hdrgradients

import kotlin.math.roundToInt
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.graphics.*
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.Config.RGBA_1010102
import android.graphics.Bitmap.Config.RGBA_F16
import android.graphics.ColorSpace.Named.ACES
import android.graphics.ColorSpace.Named.BT2020
import android.graphics.ColorSpace.Named.BT2020_HLG
import android.graphics.ColorSpace.Named.BT2020_PQ
import android.graphics.ColorSpace.Named.BT709
import android.graphics.ColorSpace.Named.DISPLAY_P3
import android.graphics.ColorSpace.Named.EXTENDED_SRGB
import android.graphics.ColorSpace.Named.SRGB
import android.os.Bundle
import android.util.TypedValue
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.graphics.createBitmap

class MainActivity : ComponentActivity() {
    
    data class GradientConfig(
        val name: String,
        val width: Int,
        val height: Int,
        val startVal: Float,
        val endVal: Float,
        val colorSpace: ColorSpace,
        val config: Bitmap.Config,
        val drawStart: Float,
        val drawEnd: Float,
    )
    
    private fun createGradient(
        gradientConfig: GradientConfig,
    ): Bitmap {
        val width = gradientConfig.width
        val height = gradientConfig.height
        val startVal = gradientConfig.startVal
        val endVal = gradientConfig.endVal
        val colorSpace = gradientConfig.colorSpace
        val config = gradientConfig.config
        val drawStart = gradientConfig.drawStart
        val drawEnd = gradientConfig.drawEnd
        
        val bitmap = createBitmap(width, height, config, true, colorSpace)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.BLACK)
        canvas.drawRect(drawEnd - 1f, 0f, width.toFloat(), height.toFloat(), Paint().apply { color = Color.WHITE })
        
        val paint = Paint()
        val startColor = Color.pack(startVal, startVal, startVal, 1f, colorSpace)
        val endColor = Color.pack(endVal, endVal, endVal, 1f, colorSpace)
        paint.shader = LinearGradient(drawStart, 0f, drawEnd, 0f, startColor, endColor, Shader.TileMode.CLAMP)
        canvas.drawRect(drawStart, 0f, drawEnd, height.toFloat(), paint)
        
        return bitmap
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
        window.colorMode = ActivityInfo.COLOR_MODE_HDR
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        setContentView(R.layout.activity_main)
        
        val gradientContainer = findViewById<LinearLayout>(R.id.gradientContainer)
        
        gradientContainer.post {
            val w = gradientContainer.width
            val h = 64.dp
            
            
            val srgb = ColorSpace.get(SRGB)
            val srgb_extended = ColorSpace.get(EXTENDED_SRGB)
            val bt709 = ColorSpace.get(BT709)
            val bt2020 = ColorSpace.get(BT2020)
            val bt2020_pq = ColorSpace.get(BT2020_PQ)
            val bt2020_hlg = ColorSpace.get(BT2020_HLG)
//            val bt2020_display = ColorSpace.get(ColorSpace.Named.DISPLAY_BT2020) // requires API 37
            val p3 = ColorSpace.get(DISPLAY_P3)
            val aces = ColorSpace.get(ACES)
            
            val gradients = listOf(
                GradientConfig("ACES F16", w, h, 0f, 1f, aces, RGBA_F16, 0f, w.toFloat()),
                
                GradientConfig("BT.709 F16", w, h, 0f, 1f, bt709, RGBA_F16, 0f, w.toFloat()),
//                GradientConfig("BT.709 1010102", w, h, 0f, 1f, bt709, Bitmap.Config.RGBA_1010102, 0f, w.toFloat()),
//                GradientConfig("BT.709 8888", w, h, 0f, 1f, bt709, Bitmap.Config.ARGB_8888, 0f, w.toFloat()),
//                GradientConfig("BT.709 F16", w, h, 0f, 1f, bt709, Bitmap.Config.RGBA_F16, 0f, w.toFloat()),
                
                GradientConfig("BT.2020 F16    ", w, h, 0f, 1f, bt2020, RGBA_F16, 0f, w.toFloat()),
//                GradientConfig("BT.2020 1010102", w, h, 0f, 1f, bt2020, RGBA_1010102, 0f, w.toFloat()),
//                GradientConfig("BT.2020 8888   ", w, h, 0f, 1f, bt2020, ARGB_8888, 0f, w.toFloat()),
//                GradientConfig("BT.2020 F16    ", w, h, 0f, 1f, bt2020, RGBA_F16, 0f, w.toFloat()),
                
                GradientConfig("P3 F16", w, h, 0f, 1f, p3, RGBA_F16, 0f, w.toFloat()),
                GradientConfig("sRGB F16", w, h, 0f, 1f, srgb, RGBA_F16, 0f, w.toFloat()),
                
                GradientConfig("BT.2020 HLG F16    ", w, h, 0f, 1f, bt2020_hlg, RGBA_F16, 0f, w.toFloat()),
                GradientConfig("BT.2020 HLG 1010102", w, h, 0f, 1f, bt2020_hlg, RGBA_1010102, 0f, w.toFloat()),
                GradientConfig("BT.2020 HLG 8888   ", w, h, 0f, 1f, bt2020_hlg, ARGB_8888, 0f, w.toFloat()),
                GradientConfig("BT.2020 HLG F16    ", w, h, 0f, 1f, bt2020_hlg, RGBA_F16, 0f, w.toFloat()),
                
                GradientConfig("BT.2020 PQ F16    ", w, h, 64f / 1023f, 940f / 1023f, bt2020_pq, RGBA_F16, 0f, w.toFloat()),
                GradientConfig("BT.2020 PQ 1010102", w, h, 64f / 1023f, 940f / 1023f, bt2020_pq, RGBA_1010102, 0f, w.toFloat()),
                GradientConfig("BT.2020 PQ 8888   ", w, h, 64f / 1023f, 940f / 1023f, bt2020_pq, ARGB_8888, 0f, w.toFloat()),
                GradientConfig("BT.2020 PQ F16    ", w, h, 64f / 1023f, 940f / 1023f, bt2020_pq, RGBA_F16, 0f, w.toFloat()),
                
                GradientConfig("BT.2020 PQ Full F16", w, h, 0f, 1f, bt2020_pq, RGBA_F16, 0f, w.toFloat()),
                GradientConfig("BT.2020 PQ Full 1010102", w, h, 0f, 1f, bt2020_pq, RGBA_1010102, 0f, w.toFloat()),
                GradientConfig("BT.2020 PQ Full 8888", w, h, 0f, 1f, bt2020_pq, ARGB_8888, 0f, w.toFloat()),
                GradientConfig("BT.2020 PQ Full F16", w, h, 0f, 1f, bt2020_pq, RGBA_F16, 0f, w.toFloat()),
                
                
                GradientConfig("sRGB Extended F16", w, h, -0.8f, 2.4f, srgb_extended, RGBA_F16, 0f, w.toFloat()),
                GradientConfig("sRGB padded F16", w, h, 0f, 1f, srgb, RGBA_F16, w.toFloat() * (0.8f / 3.2f), w.toFloat() * (1.8f / 3.2f)),
            )
            
            for (gradient in gradients) {
                val stack = FrameLayout(this)
                
                val imageView = ImageView(this)
                imageView.setImageBitmap(createGradient(gradient))
                stack.addView(imageView)
                
                val textView = TextView(this)
                textView.text = gradient.name.trimEnd()
                textView.setTextColor(Color.RED)
                textView.gravity = android.view.Gravity.CENTER
                stack.addView(textView)
                
                gradientContainer.addView(stack)
            }
        }
    }
}

val Int.dp: Int get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).roundToInt()
