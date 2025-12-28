package dev.mazharhussain.smartsms.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//val googleFontProvider = GoogleFont.Provider(
//    providerAuthority = "com.google.android.gms.fonts",
//    providerPackage = "com.google.android.gms",
//    certificates = R.array.com_google_android_gms_fonts_certs,
//)
//
//val notoSansGoogleFont = GoogleFont(name = "Google Sans")
//val notoSansFont = Font(googleFont = notoSansGoogleFont, fontProvider = googleFontProvider)
//val notoSansFontFamily = FontFamily(notoSansFont)
//val notoSans = notoSansFontFamily
//
//val robotoGoogleFont = GoogleFont(name = "Roboto")
//val robotoFont = Font(googleFont = robotoGoogleFont, fontProvider = googleFontProvider)
//val robotoFontFamily = FontFamily(robotoFont)
//val roboto = robotoFontFamily

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