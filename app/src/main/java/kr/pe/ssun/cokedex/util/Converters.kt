package kr.pe.ssun.cokedex.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.asSp() = with(LocalDensity.current) { this@asSp.toSp() }

@Composable
fun Dp.asPx() = with(LocalDensity.current) { this@asPx.toPx() }