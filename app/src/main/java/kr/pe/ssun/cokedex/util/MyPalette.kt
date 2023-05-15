package kr.pe.ssun.cokedex.util

import android.graphics.Bitmap
import android.os.Build
import androidx.palette.graphics.Palette

class MyPalette {

    companion object {
        // https://nanamare.tistory.com/186
        fun getPalette(bitmap: Bitmap, listener: Palette.PaletteAsyncListener) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                when (bitmap.config) {
                    Bitmap.Config.HARDWARE -> {
                        val softwareBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                        Palette.from(softwareBitmap).generate(listener)
                    }
                    else -> {
                        Palette.from(bitmap).generate(listener)
                    }
                }
            } else {
                Palette.from(bitmap).generate(listener)
            }
        }
}