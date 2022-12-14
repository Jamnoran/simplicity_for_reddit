package com.simplicity.simplicityaclientforreddit.main.screen.test

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseComposeLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TestLogic : BaseComposeLogic<Unit>() {
    private val _stateFlow = MutableStateFlow<UiState<String>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<String>> = _stateFlow

    override fun ready() {
        foreground {
            val text =
                "#Heading\n\n**This is bold text**\n\n_Italic text_\n\n**Mixed bold** and *italic* text\n\n[Link with desc](https://www.google.se/)\n\n[https://www.svt.se/](https://www.svt.se/)\n\nText before link [https://www.svt.se/](https://www.svt.se/) and also after\n\n~~Strikethrough~~ text \n\n`This is some inlined` code\n\n^(Superscript is this)\n\n&gt;!This is a spoiler!&lt;\n\n#Header in the middle of the text\n\n* Bullted list\n* with a couple of points\n\n1. Numbered list in a bulleted list\n\n&amp;#x200B;\n\n1. Simple numbered list\n\n&gt; This is a quote from the great emperor\n\n&amp;#x200B;\n\n https://www.google.com \n\nCode blocks looks like this\n\nImage:\n\n[With caption](https://preview.redd.it/ndrt8axj4x1a1.jpg?width=658&amp;format=pjpg&amp;auto=webp&amp;s=9a888f4b95dadcf4931a54ca6d3edef85764b93f)\n\n&amp;#x200B;\n\n&amp;#x200B;\n\n|Column 1|Column 2|Column 3|\n|:-|:-|:-|\n|Row 2|Row 2|Row 2|"
            _stateFlow.emit(UiState.Success(text))
        }
    }

    fun onPause() {
        loggI("OnPause is called")
    }

    fun onStart() {
        loggI("OnStart called")
        foreground {
            _stateFlow.emit(UiState.Success("OnStart called"))
        }
    }

    fun onStop() {
        loggI("OnStop called")
        foreground {
            _stateFlow.emit(UiState.Success("OnStop called"))
        }
    }
}
