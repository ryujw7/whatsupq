package com.example.whatsupq.Data

import android.graphics.Bitmap

data class GetRegularListItem(
    val livingImg: Bitmap,
    val livingBrand: String,
    val livingName: String,
    val livingCharge: String,
    val livingBeforeCharge: String
)