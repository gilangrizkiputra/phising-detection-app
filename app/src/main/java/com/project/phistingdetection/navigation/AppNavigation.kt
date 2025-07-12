import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.phistingdetection.MainScreen.MainScreen
import com.project.phistingdetection.NewsDetailScreen.NewsDetailScreen
import com.project.phistingdetection.data.model.NewsItem

@Composable
fun AppNavigation(
    navController: NavHostController,
    initialUrl: String
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MainScreen(
                initialUrl = initialUrl,
                onNewsClick = { selectedNews ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("news", selectedNews)
                    navController.navigate("detail")
                }
            )
        }
        composable("detail") {
            val newsItem = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<NewsItem>("news")

            newsItem?.let {
                NewsDetailScreen(
                    news = it,
                    onBackClick = { navController.popBackStack() }
                    )
            }
        }
    }
}