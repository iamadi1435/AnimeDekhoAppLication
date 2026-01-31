package com.example.animedekho.ui.anime.view.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.animedekho.domain.entity.AnimeBasicDetails
import com.example.animedekho.ui.utils.UiState


@Composable
fun AnimeListScreen(
    innerPadding: PaddingValues,
    topAnimeListState: UiState<List<AnimeBasicDetails>>,
    openAnimeDetailPage: (Int) -> Unit,
    onRetry : () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 0.dp,
                bottom = innerPadding.calculateBottomPadding()
            )
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F172A),
                        Color(0xFF1E293B),
                        Color(0xFF0F172A)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        when (topAnimeListState) {
            UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp),
                    strokeWidth = 4.dp,
                    color = Color(0xFF38BDF8),
                    trackColor = Color(0xFF334155)
                )
            }

            is UiState.Error -> {
                ErrorRetryCard(
                    message = topAnimeListState.message,
                    onRetry = onRetry
                )
            }

            is UiState.Success -> {
                val topAnimeList = topAnimeListState.data

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    contentPadding = PaddingValues(top = 42.dp, bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(topAnimeList) { anime ->
                        AnimeCard(
                            anime = anime,
                            openAnimeDetailPage = { animeId ->
                                openAnimeDetailPage(animeId)
                            }
                        )
                    }
                }

            }

            else -> Unit
        }
    }
}

@Composable
fun AnimeCard(
    anime: AnimeBasicDetails,
    openAnimeDetailPage: (Int) -> Unit
) {
    val context = LocalContext.current
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .scale(scale)
            .clickable {
                openAnimeDetailPage(anime.id)
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF1E293B).copy(alpha = 0.7f),
                            Color(0xFF334155).copy(alpha = 0.5f),
                            Color(0xFF1E293B).copy(alpha = 0.7f)
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF334155).copy(alpha = 0.3f),
                                Color.Transparent,
                                Color(0xFF334155).copy(alpha = 0.2f)
                            )
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(20.dp)
                            .background(
                                Color(0xFF1E293B).copy(alpha = 0.4f),
                                shape = RoundedCornerShape(16.dp)
                            )
                    )

                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(anime.posterUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = anime.title,
                        modifier = Modifier
                            .width(160.dp)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.FillBounds
                    )

                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (anime.title.isNotEmpty()) {
                            Text(
                                text = anime.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 20.sp,
                                color = Color.White,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 3,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    shadow = Shadow(
                                        color = Color.White.copy(alpha = 0.5f),
                                        offset = Offset(0f, 2f),
                                        blurRadius = 8f
                                    )
                                )
                            )
                        }

                        if (anime.numberOfEpisodes.isNotEmpty()) {
                            Text(
                                text = anime.numberOfEpisodes,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                color = Color(0xFFCBD5E1)
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFBBF24),
                            modifier = Modifier
                                .size(20.dp)
                                .blur(8.dp)
                        )

                        Text(
                            text = anime.rating,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                shadow = Shadow(
                                    color = Color(0xFFFBBF24).copy(alpha = 0.5f),
                                    offset = Offset(0f, 1f),
                                    blurRadius = 4f
                                )
                            )
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .width(100.dp)
                    .height(100.dp)
                    .blur(60.dp)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF1E293B).copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}