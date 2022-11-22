package ${PACKAGE_NAME}

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

class ${NAME}Navigation(private val navController: NavHostController) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch(input: ${NAME}Input) {
        val logic: ${NAME}Logic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = ${NAME}Screen(navController, logic, state.value)
        logic.init(input)
        return screen
    }
}
