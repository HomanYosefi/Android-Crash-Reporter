package com.example.leitner



import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.leitner.GrammarScreen.Grammar
import com.example.leitner.GrammarScreen.ListDetailGrammarScreen
import com.example.leitner.homeScreen.Home


sealed class BottomNavItem(@SuppressLint("SupportAnnotationUsage") @StringRes var title: Int, var icon: Int, var screen_route: String) {
    object home : BottomNavItem(R.string.home, R.drawable.home, "home")
    object grammar :
        BottomNavItem(R.string.grammar, R.drawable.grammar, "grammar")

    object setting : BottomNavItem(R.string.setting, R.drawable.setting_line, "setting")
}

sealed class NavigationItems(var screen_route: String) {
    object home : NavigationItems("home")
    object grammar : NavigationItems("grammar")
    object setting : NavigationItems("setting")
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.grammar.screen_route) {
        composable(BottomNavItem.home.screen_route) {
            Home(navController)
        }
        composable(BottomNavItem.grammar.screen_route) {
            Grammar(navController)
        }
        composable(BottomNavItem.setting.screen_route) {
            Setting(navController)
        }
        composable(
            route = "listDetailGrammar/{chapterTitle}",
            arguments = listOf(
                navArgument("chapterTitle") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chapterTitle = backStackEntry.arguments?.getString("chapterTitle") ?: ""
            ListDetailGrammarScreen(
                navController = navController,
                chapterTitle = chapterTitle
            )
        }
    }
}


fun NavHostController.navigateToGrammarDetail(chapterTitle: String) {
    this.navigate("listDetailGrammar/$chapterTitle")
}



