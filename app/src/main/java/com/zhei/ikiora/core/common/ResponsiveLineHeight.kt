package com.zhei.ikiora.core.common
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

object ResponsiveLineHeight {

    private const val RELATION = 1.1f

    /**
     * Tamaño destinado para títulos principales
     * o elementos visualmente dominantes.
     * */
    @Composable
    fun displayLarge(): TextUnit =
        (ResponsiveText.displayLarge().value * RELATION).sp


    /**
     * Tamaño utilizado en encabezados
     * o secciones importantes.
     * */
    @Composable
    fun headline(): TextUnit =
        (ResponsiveText.headline().value * RELATION).sp


    /**
     * Tamaño pensado para títulos
     * secundarios dentro de la interfaz.
     * */
    @Composable
    fun title(): TextUnit =
        (ResponsiveText.title().value * RELATION).sp


    /**
     * Tamaño utilizado para texto
     * descriptivo o de contenido general.
     * */
    @Composable
    fun body(): TextUnit =
        (ResponsiveText.body().value * RELATION).sp


    /**
     * Tamaño destinado al texto
     * mostrado en botones.
     * */
    @Composable
    fun button(): TextUnit =
        (ResponsiveText.button().value * RELATION).sp


    /**
     * Tamaño utilizado para textos
     * pequeños o información auxiliar.
     * */
    @Composable
    fun caption(): TextUnit =
        (ResponsiveText.caption().value * RELATION).sp


    /**
     * Tamaño utilizado para textos cuyo tamaño es muy pequeño
     * */
    @Composable fun minimal(): TextUnit =
        (ResponsiveText.minimal().value * RELATION).sp

}