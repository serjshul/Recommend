package com.serj.recommend.android.ui.screens.main.home.components.categoryItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.serj.recommend.android.common.ext.categoryItemsInterval
import com.serj.recommend.android.common.ext.extendedCategoryBackgroundShape
import com.serj.recommend.android.common.ext.itemsInterval
import com.serj.recommend.android.common.ext.screenPaddingsInner
import com.serj.recommend.android.common.ext.toColor
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.components.items.transparent.HorizontalItemTransparent
import com.serj.recommend.android.ui.components.items.transparent.SquareItemTransparent
import com.serj.recommend.android.ui.components.items.transparent.VerticalItemTransparent
import com.serj.recommend.android.ui.components.snackbar.SnackbarManager
import com.serj.recommend.android.ui.screens.main.home.components.ShowAllButton
import com.serj.recommend.android.ui.styles.ItemsShapes

@Composable
fun ExtendedCategory(
    modifier: Modifier = Modifier,
    category: Category,
    openScreen: (String) -> Unit,
    onCategoryClick: ((String) -> Unit, String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    if (category.content.isNotEmpty()) {
        var sizeImage by remember { mutableStateOf(IntSize.Zero) }

        Box(
            modifier = modifier
                .itemsInterval()
                .padding(top = 10.dp)
        ) {
            when {
                category.backgroundVideo != null -> {
                    // TODO: add video player
                }
                category.backgroundImage != null -> {
                    Image(
                        modifier = Modifier
                            .extendedCategoryBackgroundShape()
                            .onGloballyPositioned { sizeImage = it.size },
                        bitmap = category.backgroundImage!!.asImageBitmap(),
                        contentDescription = "background image",
                        contentScale = ContentScale.Crop
                    )
                }
                else -> {
                    Image(
                        modifier = Modifier.extendedCategoryBackgroundShape(),
                        painter = painterResource(id = R.drawable.gradient),
                        contentDescription = "background gradient",
                        contentScale = ContentScale.Crop
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
                    .padding(top = 10.dp, bottom = 10.dp)
                    .align(Alignment.CenterStart)
                    .clickable { onCategoryClick(openScreen, category.id) },
                text = category.title,
                color = if (category.backgroundImage != null)
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
                    when (it.coverType) {
                        ItemsShapes.horizontal.name -> {
                            HorizontalItemTransparent(
                                modifier = Modifier.categoryItemsInterval(),
                                title = it.title,
                                creator = it.creator,
                                cover = it.cover,
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
                                cover = it.cover,
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
                                cover = it.cover,
                                recommendationId = it.id,
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