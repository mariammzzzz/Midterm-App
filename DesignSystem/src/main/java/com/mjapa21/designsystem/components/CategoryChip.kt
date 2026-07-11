package com.mjapa21.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mjapa21.designsystem.R

@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    category: String,
    imageUrl: String? = null,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .size(width = 100.dp, height = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageUrl?.let {
            AsyncImage(
                model = imageUrl,
                contentDescription = category,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onClick),
                contentScale = ContentScale.Crop
            )
        } ?: Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_food_placeholder),
                contentDescription = category,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Text(
            text = category,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign =
                TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipPreview() {
    CategoryChip(category = "Sample Category big name")
}