package com.serj.recommend.android.ui.screens.main.home.components.categoryItems

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.COVER_HORIZONTAL
import com.serj.recommend.android.ui.COVER_SQUARE
import com.serj.recommend.android.ui.COVER_VERTICAL
import com.serj.recommend.android.ui.components.items.transparent.HorizontalItemTransparent
import com.serj.recommend.android.ui.components.items.transparent.SquareItemTransparent
import com.serj.recommend.android.ui.components.items.transparent.VerticalItemTransparent
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.common.recommendation.components.toColor
import com.serj.recommend.android.ui.screens.main.home.components.ShowAllButton

@Composable
fun ExtendedCategory(
    modifier: Modifier = Modifier,
    backgroundImage: Bitmap?,
    backgroundVideo: String? = null,
    items: List<CategoryItem?>?,
    covers: List<Bitmap?>?,
    category: Category,
    openScreen: (String) -> Unit,
    onCategoryClick: ((String) -> Unit, String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    if (!items.isNullOrEmpty()) {
        var sizeImage by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
        ) {
            when {
                backgroundVideo != null -> {
                    // TODO: add video player
                }

                backgroundImage != null -> {
                    Image(
                        modifier = Modifier
                            .height(270.dp)
                            .fillMaxWidth()
                            .onGloballyPositioned { sizeImage = it.size },
                        bitmap = backgroundImage.asImageBitmap(),
                        contentDescription = "background_image",
                        contentScale = ContentScale.Crop
                    )
                }

                else -> {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(270.dp),
                        painter = painterResource(id = R.drawable.gradient),
                        contentDescription = "background_gradient",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Box(
                modifier = modifier
                    .height(270.dp)
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.White),
                            startY = (sizeImage.height.toFloat() / 1.5).toFloat(),
                            endY = sizeImage.height.toFloat()
                        )
                    )
            )

            Text(
                modifier = modifier
                    .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 10.dp)
                    .align(Alignment.TopCenter)
                    .clickable { onCategoryClick(openScreen, category.id) },
                text = category.title,
                color = if (backgroundImage != null) category.color?.toColor() ?: Color.Black
                else Color.Black,
                fontSize = 24.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
            )

            LazyRow(
                modifier = modifier.padding(top = 190.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Spacer(modifier = Modifier.size(15.dp))
                }

                items(items.size) { i ->
                    when (category.coverType) {
                        COVER_SQUARE -> {
                            SquareItemTransparent(
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.getOrNull(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        COVER_HORIZONTAL -> {
                            HorizontalItemTransparent(
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.getOrNull(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        COVER_VERTICAL -> {
                            VerticalItemTransparent(
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.getOrNull(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        else -> {
                            SnackbarManager.showMessage(R.string.error_cover_type)
                        }
                    }
                }

                item {
                    ShowAllButton(
                        modifier = Modifier.padding(start = 35.dp, end = 50.dp, bottom = 30.dp),
                        categoryId = category.id,
                        openScreen = openScreen,
                        onCategoryClick = onCategoryClick
                    )
                }
            }
        }
    }
}