package com.example.animedekho.ui.anime.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.animedekho.application.AnimeDekhoApp
import com.example.animedekho.di.ActivityModule
import com.example.animedekho.di.ActivityScope
import com.example.animedekho.di.DaggerActivityComponent
import com.example.animedekho.ui.anime.navgraph.NavRoute
import com.example.animedekho.ui.anime.view.components.AnimeDetailsScreen
import com.example.animedekho.ui.anime.view.components.AnimeListScreen
import com.example.animedekho.ui.anime.viewmodel.AnimeScreenViewModel
import com.example.animedekho.ui.anime.viewmodel.AnimeViewModelFactory
import com.example.animedekho.ui.theme.AnimeDekhoTheme
import com.example.animedekho.ui.utils.ANIME_ID
import javax.inject.Inject

@ActivityScope
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: AnimeViewModelFactory

    private val viewModel: AnimeScreenViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDependencies()

        enableSystemBars()

        setContent {
            AnimeDekhoTheme {
                val navController = rememberNavController()

                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavRoute.TopAnimeList.route
                    ) {

                        composable(NavRoute.TopAnimeList.route) {
                            val topAnimeListState by
                            viewModel.topAnimeListState.collectAsStateWithLifecycle()

                            AnimeListScreen(
                                innerPadding = innerPadding,
                                topAnimeListState = topAnimeListState,
                                openAnimeDetailPage = { animeId ->
                                    navController.navigate(
                                        NavRoute.AnimeDetails.createRoute(animeId)
                                    )
                                },
                                onRetry = {
                                    viewModel.getTopAnimeList()
                                }
                            )
                        }

                        composable(
                            route = NavRoute.AnimeDetails.route,
                            arguments = listOf(
                                navArgument(ANIME_ID) {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->

                            val animeId =
                                backStackEntry.arguments?.getInt(ANIME_ID)
                                    ?: return@composable

                            AnimeDetailsScreen(
                                innerPadding = innerPadding,
                                animeId = animeId,
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() },
                                onRetry = { animeId ->
                                    viewModel.getAnimeDetails(animeId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as AnimeDekhoApp).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .injectToActivity(this)
    }

    private fun enableSystemBars() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.dark(
                scrim = Color.Transparent.toArgb()
            )
        )
    }
}
