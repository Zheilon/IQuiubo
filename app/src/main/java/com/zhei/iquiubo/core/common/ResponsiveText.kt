package com.zhei.iquiubo.core.common
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Esta aplicación estará destinada para moviles unicamente
 * nada de tablets. Por esto mismo tomo 2 tipos de medidas de
 * telefonos.
 * */
object ResponsiveText {

    /**
     * Indica si el dispositivo posee un ancho reducido.
     * Utilizado para ajustar tamaños responsivos.
     * */
    @Composable
    fun isSmallDevice(): Boolean =
        LocalConfiguration.current.screenWidthDp <= Constants.SMALL_DEVICE_WIDTH_DP


    /**
     * Tamaño destinado para títulos principales
     * o elementos visualmente dominantes.
     * */
    @Composable fun displayBig(): TextUnit =
        if (isSmallDevice()) 37.sp else 40.sp


    /**
     * Tamaño destinado para títulos principales
     * o elementos visualmente dominantes.
     * */
    @Composable fun displaymiddle(): TextUnit =
        if (isSmallDevice()) 29.sp else 32.sp


    /**
     * Tamaño utilizado en encabezados
     * o secciones importantes.
     * */
    @Composable fun headline(): TextUnit =
        if (isSmallDevice()) 23.sp else 27.sp


    /**
     * Tamaño pensado para títulos
     * secundarios dentro de la interfaz.
     * */
    @Composable fun title(): TextUnit =
        if (isSmallDevice()) 17.sp else 19.sp


    /**
     * Tamaño utilizado para texto
     * descriptivo o de contenido general.
     * */
    @Composable fun body(): TextUnit =
        if (isSmallDevice()) 14.sp else 16.sp


    /**
     * Tamaño destinado al texto
     * mostrado en botones.
     * */
    @Composable fun button(): TextUnit =
        if (isSmallDevice()) 12.sp else 15.sp


    /**
     * Tamaño utilizado para textos
     * pequeños o información auxiliar.
     * */
    @Composable fun caption(): TextUnit =
        if (isSmallDevice()) 10.sp else 12.sp


    @Composable fun small(): TextUnit =
        if (isSmallDevice()) 8.sp else 10.sp


    /**
     * Tamaño utilizado para textos cuyo tamaño es muy pequeño
     * */
    @Composable fun minimal(): TextUnit =
        if (isSmallDevice()) 3.sp else 4.sp
}