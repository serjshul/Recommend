package com.serj.recommend.android.ui.screens.home.components.categoryItems

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serj.recommend.android.R
import com.serj.recommend.android.model.Category
import com.serj.recommend.android.model.CategoryItem
import com.serj.recommend.android.model.Recommendation
import com.serj.recommend.android.ui.screens.home.components.categoryContentItems.HorizontalCategoryItem
import com.serj.recommend.android.ui.screens.home.components.categoryContentItems.SquareCategoryItem
import com.serj.recommend.android.ui.screens.home.components.categoryContentItems.VerticalCategoryItem
import com.serj.recommend.android.ui.screens.recommendation.components.toColor


@Composable
fun ExtendedCategory(
    modifier: Modifier = Modifier,
    backgroundImage: Bitmap?,
    backgroundVideo: String? = null,
    items: List<CategoryItem?>?,
    covers: List<Bitmap?>?,
    category: Category,
    openScreen: (String) -> Unit,
    onRecommendationClick: ((String) -> Unit, Recommendation) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 5.dp, bottom = 50.dp)
    ) {
        when {
            backgroundVideo != null -> {
                // TODO: add video player
            }
            backgroundImage != null -> {
                Image(
                    modifier = Modifier
                        .height(320.dp),
                    bitmap = backgroundImage.asImageBitmap(),
                    contentDescription = "background_image",
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                Image(
                    modifier = Modifier
                        .height(320.dp),
                    painter = painterResource(id = R.drawable.gradient),
                    contentDescription = "background_gradient",
                    contentScale = ContentScale.Crop
                )
            }
        }

        Text(
            modifier = modifier
                .padding(start = 15.dp, top = 10.dp, end = 15.dp, bottom = 10.dp)
                .align(Alignment.TopCenter),
            text = category.title,
            color = category.color?.toColor() ?: Color.Black,
            fontSize = 24.sp,
            maxLines = 2,
            fontWeight = FontWeight.Bold,
        )

        LazyRow(
            modifier = modifier.padding(top = 240.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items?.let {
                items(items.size) { i ->
                    Spacer(modifier = Modifier.size(15.dp))

                    when (category.coverType) {
                        "Square" -> {
                            SquareCategoryItem(
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.get(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        "Horizontal" -> {
                            HorizontalCategoryItem(
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.get(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        "Vertical" -> {
                            VerticalCategoryItem(
                                title = items[i]?.title,
                                creator = items[i]?.creator,
                                cover = covers?.get(i),
                                recommendationId = items[i]?.recommendationId,
                                openScreen = openScreen,
                                onRecommendationClick = onRecommendationClick
                            )
                        }

                        else -> {
                            // TODO: what else?
                        }
                    }
                }
            }

            item {
                OutlinedIconButton(
                    modifier = modifier.padding(start = 10.dp, bottom = 35.dp, end = 15.dp),
                    colors = IconButtonDefaults.outlinedIconButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.Gray),
                    onClick = {
                        // TODO: category screen
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowForward,
                        contentDescription = "forward"
                    )
                }
            }
        }
    }
}