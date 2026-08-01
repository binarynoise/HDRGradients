package de.binarynoise.hdrgradients

import java.lang.reflect.Method
import android.annotation.SuppressLint
import android.view.Display
import org.lsposed.hiddenapibypass.HiddenApiBypass

@SuppressLint("SoonBlockedPrivateApi")
object HiddenDisplay {
    init {
        HiddenApiBypass.setHiddenApiExemptions("");
    }
    
    val colorModeMethod: Method = Display::class.java.getDeclaredMethod("getColorMode")
    
    fun Display.getColorMode(): Int = colorModeMethod.invoke(this) as Int
    
    val supportedColorModesMethod: Method = Display::class.java.getDeclaredMethod("getSupportedColorModes")
    
    fun Display.getSupportedColorModes(): IntArray {
        return supportedColorModesMethod.invoke(this) as IntArray
    }
    
    val colorModeFields = Display::class.java.declaredFields.filter { it.name.startsWith("COLOR_MODE_") }.associate { it.get(null) as Int to it.name }
    
    fun colorModeToString(colorMode: Int): String {
        return colorModeFields[colorMode] ?: "Unknown"
    }
}
