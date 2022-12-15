package com.simplicity.simplicityaclientforreddit.main.components.texts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.simplicity.simplicityaclientforreddit.main.theme.BodyNormal
import com.simplicity.simplicityaclientforreddit.main.theme.OnBackground
import com.simplicity.simplicityaclientforreddit.main.theme.OnPrimary
import com.simplicity.simplicityaclientforreddit.main.theme.OnSurface

@Composable
fun CText(text: String, style: TextStyle = BodyNormal, color: Color = OnBackground, textAlign: TextAlign? = TextAlign.Start) {
    Text(text = text, color = color, style = style, textAlign = textAlign)
}

@Composable
fun CText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = BodyNormal,
    color: Color = OnBackground,
    textAlign: TextAlign? = TextAlign.Start
) {
    Text(modifier = modifier, text = text, color = color, style = style, textAlign = textAlign)
}

@Composable
fun OnSurfaceText(modifier: Modifier = Modifier, text: String, style: TextStyle = BodyNormal) {
    Text(modifier = modifier, text = text, color = OnSurface, style = style)
}

@Composable
fun OnPrimaryText(modifier: Modifier = Modifier, text: String, style: TextStyle = BodyNormal) {
    Text(modifier = modifier, text = text, color = OnPrimary, style = style)
}

@Composable
fun OnSurfaceText(modifier: Modifier = Modifier, text: String, textAlign: TextAlign?, style: TextStyle = BodyNormal) {
    Text(modifier = modifier, textAlign = textAlign, text = text, color = Color.White, style = style)
}


// Here's an example of some reddit formatting tricks:
// **Bold**, *italic*, `code`, [link](http://redditpreview.com), ~~strikethrough~~
//
// >Quote
// >>Nested quote
//
// | Column 1   | Column 2    | Column 3     |
// |:-----------|------------:|:------------:|
// | You        |          You|     You
// | can align  |    can align|  can align
// | left       |        right|   center
//
// ---
//
// # Header 1
// ## Header 2
// ### Header 3
// #### Header 4
// ##### Header 5
// ###### Header 6
//
// - Unordered list
//
// 1. Ordered list
//
// This is some text^(this is some superscript)
//
// \*Escape formatting\*
@Preview
@Composable
fun MarkDownPreview() {
    Column(Modifier.fillMaxWidth()) {
        CText("Some text")
    }
}
