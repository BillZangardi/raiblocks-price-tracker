package me.billzangardi.raiblocks.util

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.inputmethod.InputMethodManager
import java.lang.Float.parseFloat
import java.util.regex.Pattern

object ViewUtil {

    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }

    fun dpToPx(dp: Int): Int {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
    }

    fun getConversion(bitcoinValue: Float, xrbValueInBtc: Float): Float {
        return bitcoinValue * xrbValueInBtc
    }

    fun isNumeric(string: String): Boolean {
        var numeric = true
        try {
            val num = parseFloat(string)
        } catch (e: NumberFormatException) {
            numeric = false
        }
        return numeric
    }

    fun isColorHex(string: String): Boolean {
        return try {
            Pattern.compile("#([0-9a-f]{6}|[0-9a-f]{8})").matcher(string).matches()
        } catch (e: NumberFormatException) {
            false
        }
    }

}
