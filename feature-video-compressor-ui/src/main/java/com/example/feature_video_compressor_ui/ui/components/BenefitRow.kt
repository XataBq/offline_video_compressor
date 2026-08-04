package com.example.feature_video_compressor_ui.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_desing_system.theme.IconBackground
import com.example.core_desing_system.theme.OfflineVideoCompressorTheme
import com.example.core_desing_system.theme.PrimaryLight
import com.example.core_desing_system.theme.TextPrimary
import com.example.core_desing_system.theme.TextSecondary
import com.example.core_desing_system.theme.VideoCompressorTypography
import com.example.feature_video_compressor_ui.R

@Composable
internal fun BenefitRow(
    titleResource: Int,
    subTitleResource: Int,
    modifier: Modifier = Modifier,
    iconContent: @Composable (Modifier) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(IconBackground),
            contentAlignment = Alignment.Center,
        ) {
            iconContent(Modifier.size(25.dp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier,
        ) {
            Text(
                text = stringResource(titleResource),
                style = VideoCompressorTypography.titleMedium,
                color = TextPrimary,
            )
            Text(
                text = stringResource(subTitleResource),
                style = VideoCompressorTypography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.widthIn(max = 300.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BenefitRowPreview() {
    OfflineVideoCompressorTheme {
        BenefitRow(
            R.string.benefit_no_ads_title,
            R.string.benefit_no_ads_description,
            Modifier
        ) {
            Icon(
                imageVector = Icons.Outlined.Shield,
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = PrimaryLight,
            )
        }
    }
}