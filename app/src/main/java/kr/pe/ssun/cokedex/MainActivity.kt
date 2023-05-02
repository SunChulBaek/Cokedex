package kr.pe.ssun.cokedex

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kr.pe.ssun.cokedex.navigation.MainNavHost
import kr.pe.ssun.cokedex.ui.theme.CokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            CokedexTheme {
                MainNavHost(
                    showToast = { text ->
                        Toast.makeText(this, text, Toast.LENGTH_SHORT).apply {
                            this.show()
                        }
                    }
                )
            }
        }
    }
}