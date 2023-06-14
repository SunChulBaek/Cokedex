package kr.pe.ssun.cokedex.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.tan

fun cosDeg(degree: Int) = cos(degToRadian(degree))

fun tanDeg(degree: Int) = tan(degToRadian(degree))

private fun degToRadian(degree: Int) = degree * PI / 180

private fun degToRadian(degree: Double) = degree * PI / 180

@Composable
fun Dp.asSp() = with(LocalDensity.current) { this@asSp.toSp() }

@Composable
fun Dp.asPx() = with(LocalDensity.current) { this@asPx.toPx() }