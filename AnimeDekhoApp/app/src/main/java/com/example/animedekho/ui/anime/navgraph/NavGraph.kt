package com.example.animedekho.ui.anime.navgraph

sealed class NavRoute(val route: String) {

    data object TopAnimeList : NavRoute("top_anime_list")

    data object AnimeDetails : NavRoute("anime_details/{animeId}") {
        fun createRoute(animeId: Int): String {
            return "anime_details/$animeId"
        }
    }
}
