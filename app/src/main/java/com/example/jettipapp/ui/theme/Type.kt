package com.example.jettipapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.jettipapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_bold,FontWeight.Bold),
    Font(R.font.poppins_bolditalic,FontWeight.Bold,FontStyle.Italic),
    Font(R.font.poppins_italic,FontWeight.Normal,FontStyle.Italic),
    Font(R.font.poppins_light,FontWeight.Light),
    Font(R.font.poppins_lightitalic,FontWeight.Light,FontStyle.Italic),
    Font(R.font.poppins_medium,FontWeight.Medium),
    Font(R.font.poppins_mediumitalic,FontWeight.Medium,FontStyle.Italic),
    Font(R.font.poppins_regular,FontWeight.Normal),
    Font(R.font.poppins_semibold,FontWeight.SemiBold),
)