package com.example.feature.video.compressor.ui.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_design_system.theme.Primary
import com.example.core_design_system.theme.PrimaryDark
import com.example.core_design_system.theme.PrimaryLight
import com.example.core_design_system.theme.SurfacePurple
import com.example.core_design_system.theme.TextSecondary
import com.example.core_design_system.theme.VideoCompressorTypography
import com.example.feature.video.compressor.ui.R

@Composable
internal fun EstimatedSizeCard(
    estimatedSizeMb: Long,
    originalSizeMb: Long,
    savingsPercent: Int,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = SurfacePurple.copy(alpha = 0.62f),
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(62.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                PrimaryLight,
                                PrimaryDark,
                            ),
                        ),
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.PieChart,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(34.dp),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = stringResource(R.string.estimated_size_title),
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                )

                Text(
                    text = stringResource(
                        R.string.estimated_size_value,
                        estimatedSizeMb,
                    ),
                    style = VideoCompressorTypography.headlineMedium,
                    color = Primary,
                )

                Text(
                    text = stringResource(
                        R.string.estimated_size_description,
                        savingsPercent,
                        originalSizeMb,
                    ),
                    style = VideoCompressorTypography.bodySmall,
                    color = TextSecondary,
                )
            }

            SavingsIndicator(
                savingsPercent = savingsPercent,
            )
        }
    }
}