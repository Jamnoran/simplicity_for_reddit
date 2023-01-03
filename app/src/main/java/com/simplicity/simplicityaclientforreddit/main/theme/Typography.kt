package com.simplicity.simplicityaclientforreddit.main.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simplicity.simplicityaclientforreddit.main.theme.Typography.Companion.HEADER_4_TEXT_SIZE

class Typography {
    companion object {
        val DEFAULT_TEXT_SIZE = 16.sp
        val HEADER_4_TEXT_SIZE = 40.sp
    }
}

/**
 * These have not been aligned properly. It's an estimate based on the names of the Text Styles.
 */
fun makeTypography(): Typography = Typography(
    displayLarge = Header1,
    displayMedium = Header2,
    displaySmall = Header3,
    headlineLarge = Header4,
    headlineMedium = Header5,
    headlineSmall = Header6,
    titleLarge = SubheaderLarge,
    titleMedium = SubheaderNormal,
    titleSmall = SubheaderSmall,
    bodyLarge = BodyLarge,
    bodyMedium = BodyNormal,
    bodySmall = BodySmall,
    labelLarge = BodyLargeBold,
    labelMedium = BodyNormalBold,
    labelSmall = BodySmallBold
)

private val HeaderFontFamily = FontFamily.Default
private val SubheaderFontFamily = FontFamily.Default
private val BodyFontFamily = FontFamily.Default

// region Header
val Header1 = TextStyle(
    fontFamily = HeaderFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 88.sp,
    letterSpacing = -0.02.sp
)
val Header2 = TextStyle(
    fontFamily = HeaderFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 64.sp,
    letterSpacing = -0.01.sp
)
val Header3 = TextStyle(
    fontFamily = HeaderFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 52.sp,
    letterSpacing = -0.01.sp
)
val Header4 = TextStyle(
    fontFamily = HeaderFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = HEADER_4_TEXT_SIZE
)
val Header5 = TextStyle(
    fontFamily = HeaderFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 32.sp
)
val Header6 = TextStyle(
    fontFamily = HeaderFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp
)
// endregion Header

// region Subheader
val SubheaderLarge = TextStyle(
    fontFamily = SubheaderFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 24.sp
)
val SubheaderLargeLink = SubheaderLarge.copy(textDecoration = TextDecoration.Underline)
val SubheaderNormal = TextStyle(
    fontFamily = SubheaderFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp
)
val SubheaderNormalLink = SubheaderNormal.copy(textDecoration = TextDecoration.Underline)
val SubheaderSmall = TextStyle(
    fontFamily = SubheaderFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
)
val SubheaderSmallLink = SubheaderSmall.copy(textDecoration = TextDecoration.Underline)
// endregion Subheader

// region Body
val BodyLarge = TextStyle(
    fontFamily = BodyFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp
)
val BodyLargeBold = BodyLarge.copy(fontWeight = FontWeight.Bold)
val BodyLargeLink = BodyLarge.copy(textDecoration = TextDecoration.Underline)
val BodyNormal = TextStyle(
    fontFamily = BodyFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)
val BodyNormalBold = BodyNormal.copy(fontWeight = FontWeight.Bold)
val BodyNormalLink = BodyNormal.copy(textDecoration = TextDecoration.Underline)
val BodySmall = TextStyle(
    fontFamily = BodyFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = 0.01.sp
)
val BodySmallBold = BodySmall.copy(fontWeight = FontWeight.Bold)
val BodySmallLink = BodySmall.copy(textDecoration = TextDecoration.Underline)
// region Body

// region Button
val ButtonLarge = TextStyle(
    fontFamily = BodyFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 18.sp
)

val ButtonNormal = TextStyle(
    fontFamily = BodyFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
)
val ButtonSmall = TextStyle(
    fontFamily = BodyFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    letterSpacing = 0.01.sp
)
// endregion Button

// region Misc
val Metadata = TextStyle(
    fontFamily = BodyFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    letterSpacing = 0.01.sp
)
val Label = TextStyle(
    fontFamily = BodyFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    letterSpacing = 0.01.sp
)

// endregion Misc

@Composable
@Preview(widthDp = 500, showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun PreviewTypography() {
    Column(modifier = Modifier.padding(4.dp)) {
        Text(text = "Header 1", style = Header1)
        Text(text = "Header 2", style = Header2)
        Text(text = "Header 3", style = Header3)
        Text(text = "Header 4", style = Header4)
        Text(text = "Header 5", style = Header5)
        Text(text = "Header 6", style = Header6)

        Spacer(modifier = Modifier.height(18.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Subheader Large", style = SubheaderLarge)
            Text(text = "Subheader Large Link", style = SubheaderLargeLink)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Subheader Normal", style = SubheaderNormal)
            Text(text = "Subheader Normal Link", style = SubheaderNormalLink)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Subheader Small", style = SubheaderSmall)
            Text(text = "Subheader Small Link", style = SubheaderSmallLink)
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Body Large", style = BodyLarge)
            Text(text = "Body Large Bold", style = BodyLargeBold)
            Text(text = "Body Large Link", style = BodyLargeLink)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Body Normal", style = BodyNormal)
            Text(text = "Body Normal Bold", style = BodyNormalBold)
            Text(text = "Body Normal Link", style = BodyNormalLink)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Body Small", style = BodySmall)
            Text(text = "Body Small Bold", style = BodySmallBold)
            Text(text = "Body Small Link", style = BodySmallLink)
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(text = "Button Large", style = ButtonLarge)
        Text(text = "Button Normal", style = ButtonNormal)
        Text(text = "Button Small", style = ButtonSmall)

        Spacer(modifier = Modifier.height(18.dp))
        Text(text = "Metadata", style = Metadata)

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Label", style = Label)
    }
}
