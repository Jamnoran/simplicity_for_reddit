package com.simplicity.simplicityaclientforreddit.main.utils.extensions

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.simplicity.simplicityaclientforreddit.main.screen.NavRoute

fun NavGraphBuilder.route(route: NavRoute, content: @Composable (NavBackStackEntry) -> Unit) {
    return composable(route.path, content = content)
}

fun NavGraphBuilder.route(route: NavRoute, args: List<String>, content: @Composable (NavBackStackEntry) -> Unit) {
    return composable(
        route = formatOfParams(route.path, args),
        arguments = NavRoute.run { getArguments(args) }
    ) { stack ->
        content.invoke(stack)
    }
}

// build and setup route format (in navigation graph)
fun formatOfParams(path: String, args: List<String>): String {
    return buildString {
        append(path)
        args.forEach { arg ->
            append("/{$arg}")
        }
    }
}
