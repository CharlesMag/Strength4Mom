package com.example.strength4mom.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.example.strength4mom.R


//Initializing imported font here
//Need to import Font and FontFamily
val LaoSansLao = FontFamily(
    Font(R.font.notosanslao_regular),
    Font(R.font.notosanslao_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with with LaoSansLao being used
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = LaoSansLao,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
//        lineHeight = 24.sp,
//        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = LaoSansLao,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    displayMedium = TextStyle(
        fontFamily = LaoSansLao,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    displaySmall = TextStyle(
        fontFamily = LaoSansLao,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = LaoSansLao,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
)

