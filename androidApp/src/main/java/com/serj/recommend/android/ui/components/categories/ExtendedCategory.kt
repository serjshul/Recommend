package com.serj.recommend.android.ui.components.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.common.ext.categoryItemsInterval
import com.serj.recommend.android.common.ext.extendedCategoryBackgroundShape
import com.serj.recommend.android.common.ext.itemsInterval
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.common.ext.toColor
import com.serj.recommend.android.model.collections.Category
import com.serj.recommend.android.model.collections.Recommendation
import com.serj.recommend.android.ui.components.loadingIndicators.SmallLoadingIndicator
import com.serj.recommend.android.ui.components.media.CustomGlideImage
import com.serj.recommend.android.ui.components.recommendationPreviews.ItemsShapes
import com.serj.recommend.android.ui.components.recommendationPreviews.transparent.HorizontalItemTransparent
import com.serj.recommend.android.ui.components.recommendationPreviews.transparent.SquareItemTransparent
import com.serj.recommend.android.ui.components.recommendationPreviews.transparent.VerticalItemTransparent
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.main.home.HomeViewModel.Companion.AMOUNT_THRESHOLD
import com.serj.recommend.android.ui.screens.main.home.components.ShowAllButton

@Composable
fun ExtendedCategory(
    modifier: Modifier = Modifier,
    category: Category,
    openScreen: (String) -> Unit,
    onCategoryClick: ((String) -> Unit, String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    if (category.title != null) {
        var sizeImage by remember { mutableStateOf(IntSize.Zero) }
        var isLoading by rememberSaveable { mutableStateOf(true) }
        var currentItemsAmount = 0

        Box(
            modifier = modifier
                .itemsInterval()
                .padding(top = 10.dp)
        ) {
            when {
                category.backgroundVideoReference != null -> {
                    // TODO: add video player
                }
                else -> {
                    CustomGlideImage(
                        modifier = Modifier
                            .extendedCategoryBackgroundShape()
                            .onGloballyPositioned { sizeImage = it.size },
                        reference = category.backgroundImageReference
                    )
                }
            }
            Box(
                modifier = Modifier
                    .extendedCategoryBackgroundShape()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.White),
                            startY = (sizeImage.height.toFloat() / 1.5).toFloat(),
                            endY = sizeImage.height.toFloat()
                        )
                    )
            )

            Text(
                modifier = Modifier
                    .screenPaddingsInner()
                    .padding(start = 4.dp, top = 10.dp, bottom = 10.dp)
                    .align(Alignment.TopStart)
                    .clickable { onCategoryClick(openScreen, category.id) },
                text = category.title,
                color = if (category.backgroundImageReference != null)
                    category.color?.toColor() ?: Color.Black
                else Color.Black,
                fontSize = 22.sp,
                maxLines = 2,
                fontWeight = FontWeight.Bold,
            )

            LazyRow(
                modifier = Modifier.padding(top = 190.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Spacer(modifier = Modifier.size(15.dp))
                }

                items(category.content) {
                    when (category.coverType) {
                        ItemsShapes.horizontal.name -> {
                            HorizontalItemTransparent(
                                modifier = Modifier.categoryItemsInterval(),
                                title = it.title,
                                creator = it.creator,
                                type = it.type,
                                tags = it.tags,
                                coverReference = it.coverReference,
                                recommendationId = it.id,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        ItemsShapes.square.name -> {
                            SquareItemTransparent(
                                modifier = Modifier.categoryItemsInterval(),
                                title = it.title,
                                creator = it.creator,
                                type = it.type,
                                tags = it.tags,
                                coverReference = it.coverReference,
                                recommendationId = it.id,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        ItemsShapes.vertical.name -> {
                            VerticalItemTransparent(
                                modifier = Modifier.categoryItemsInterval(),
                                title = it.title,
                                creator = it.creator,
                                type = it.type,
                                tags = it.tags,
                                coverReference = it.coverReference,
                                recommendationId = it.id,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }
                        else -> {
                            SnackbarManager.showMessage(R.string.error_cover_type)
                        }
                    }
                    currentItemsAmount++
                    isLoading = currentItemsAmount < AMOUNT_THRESHOLD
                }

                if (isLoading) {
                    if (currentItemsAmount != 0) {
                        item {
                            SmallLoadingIndicator(
                                modifier = Modifier
                                    .width(130.dp)
                                    .fillParentMaxHeight(),
                                backgroundColor = Color.White
                            )
                        }
                    }
                } else {
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
}