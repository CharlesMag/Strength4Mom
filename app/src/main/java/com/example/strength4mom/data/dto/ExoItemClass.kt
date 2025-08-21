package com.example.strength4mom.data.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class ExoItemClass {
    data class Exo(
        @DrawableRes val imageResourceID: Int,
        @StringRes val name: Int,
        val numberSet: Int,
        val numberRep: Int,
        @StringRes val description: Int,
        val Id: Int,
    )
}