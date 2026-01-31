package com.example.animedekho.ui.anime.view.components


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.animedekho.domain.entity.AnimeDetails
import com.example.animedekho.ui.anime.viewmodel.AnimeScreenViewModel
import com.example.animedekho.ui.theme.AccentAmber
import com.example.animedekho.ui.theme.BodyText
import com.example.animedekho.ui.theme.MutedText
import com.example.animedekho.ui.theme.PrimaryBlue
import com.example.animedekho.ui.theme.TitleText
import com.example.animedekho.ui.theme.backGroundColors
import com.example.animedekho.ui.theme.trackColor
import com.example.animedekho.ui.utils.UiState
import com.example.animedekho.ui.utils.YouTubePlayerUI


@Composable
fun AnimeDetailsScreen(
    innerPadding: PaddingValues,
    animeId: Int,
    viewModel: AnimeScreenViewModel,
    onBack: () -> Unit,
    onRetry: (Int) -> Unit
) {
    BackHandler {
        onBack()
    }

    val animeDetailsState = viewModel.animeDetailState.collectAsStateWithLifecycle().value

    LaunchedEffect(animeId) {
        viewModel.getAnimeDetails(animeId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = innerPadding.calculateBottomPadding()
            )
            .background(
                Brush.verticalGradient(
                    colors = backGroundColors
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        when (animeDetailsState) {
            UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp),
                    strokeWidth = 4.dp,
                    color = PrimaryBlue,
                    trackColor = trackColor
                )
            }

            is UiState.Error -> {
                ErrorRetryCard(
                    message = animeDetailsState.message,
                    onRetry = {
                        onRetry(animeId)
                    }
                )
            }

            is UiState.Success -> {
                val animeDetails = animeDetailsState.data
                AnimeDetailsUI(
                    animeDetails = animeDetails,
                    onBack = {
                        onBack()
                    }
                )
            }

            else -> Unit
        }
    }
}

@Composable
fun AnimeDetailsUI(
    animeDetails: AnimeDetails,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = backGroundColors
                )
            )
            .verticalScroll(rememberScrollState())
    ) {

        if (animeDetails.videoUrl.isNotEmpty()) {
            YouTubePlayerUI(
                videoUrl = animeDetails.videoUrl,
                modifier = Modifier
                    .padding(top = 36.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = 0.6f)),
                onBack = {
                    onBack()
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f)
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
                        .data(animeDetails.thumbnailUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = animeDetails.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(24.dp)),
                    contentScale = ContentScale.FillBounds
                )

            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = animeDetails.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TitleText
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = animeDetails.episodes,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MutedText,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = AccentAmber,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = animeDetails.rating,
                    color = MutedText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = animeDetails.synopsis,
                    fontSize = 14.sp,
                    color = BodyText,
                    lineHeight = 20.sp,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 4,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = if (isExpanded) "Read Less" else "Read More",
                    color = PrimaryBlue,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.End,
                    modifier = Modifier.clickable {
                        isExpanded = !isExpanded
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Genres",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TitleText
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "💥", fontSize = 20.sp)
                Text(
                    text = animeDetails.genres.joinToString(", "),
                    fontSize = 16.sp,
                    color = Color(0xFFE2E8F0)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}