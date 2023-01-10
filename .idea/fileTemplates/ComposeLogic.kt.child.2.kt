package ${PACKAGE_NAME}

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.simplicity.simplicityaclientforreddit.main.listeners.NavigationListener

class ${NAME}Navigation(
    private val navController: NavHostController,
    private val navigationListener: NavigationListener) {

    @OptIn(ExperimentalLifecycleComposeApi::class)
    @Composable
    fun Launch(input: ${NAME}Input? = ${NAME}Input()) {
        val logic: ${NAME}Logic = viewModel()
        val state = logic.stateFlow.collectAsStateWithLifecycle()
        val screen = ${NAME}Screen(navController, logic, state.value)
        logic.init(input = input, navController = navController, navigationListener = navigationListener)
        return screen
    }
}
