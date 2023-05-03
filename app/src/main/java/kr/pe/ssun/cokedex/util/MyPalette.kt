package kr.pe.ssun.cokedex.util

import android.graphics.Bitmap
import android.os.Build
import androidx.palette.graphics.Palette
import timber.log.Timber

class MyPalette {

    companion object {
        // https://nanamare.tistory.com/186
        fun getPalette(bitmap: Bitmap, listener: Palette.PaletteAsyncListener) =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                when (bitmap.config) {
                    Bitmap.Config.HARDWARE -> {
                        Timber.d("[sunchulbaek] OVER O (8.0) : hardware bitmap!!!")
                        val softwareBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                        Palette.from(softwareBitmap).generate(listener)
                    }
                    else -> {
                        Timber.d("[sunchulbaek] OVER O (8.0) : software bitmap!!!")
                        Palette.from(bitmap).generate(listener)
                    }
                }
            } else {
                Timber.d("[sunchulbaek] UNDER O (8.0) : software bitmap!!!")
                Palette.from(bitmap).generate(listener)
            }
        }
}