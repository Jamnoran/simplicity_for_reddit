package ${PACKAGE_NAME}

import com.simplicity.simplicityaclientforreddit.main.base.compose.BaseLogic
import com.simplicity.simplicityaclientforreddit.main.base.compose.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ${NAME}Logic : BaseComposeLogic<${NAME}Input>() {
    private val _stateFlow = MutableStateFlow<UiState<Data>>(UiState.Loading())
    val stateFlow: StateFlow<UiState<Data>> = _stateFlow

    fun start(input: ${NAME}Input) {
        background {
            // Do something in the background
            foreground {
                _stateFlow.emit(UiState.Success(Data("Hidden!")))
            }
        }
    }
}
