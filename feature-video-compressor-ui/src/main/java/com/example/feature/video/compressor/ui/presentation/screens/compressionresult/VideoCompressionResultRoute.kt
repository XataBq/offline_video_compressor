package com.example.feature.video.compressor.ui.presentation.screens.compressionresult

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feature.video.compressor.ui.presentation.VideoCompressorNews
import com.example.feature.video.compressor.ui.presentation.VideoCompressorViewModel

@SuppressLint("UseKtx")
@Composable
internal fun VideoCompressionResultRoute(
    viewModel: VideoCompressorViewModel,
    navigateBack: () -> Unit,
    navigateHome: () -> Unit,
    navigateCompressionSettings: () -> Unit,
) {
    val state by
    viewModel.uiState
        .collectAsStateWithLifecycle()

    val context =
        LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.news.collect { news ->
            when (news) {
                VideoCompressorNews
                    .NavigateCompressionSettings -> {
                    navigateCompressionSettings()
                }

                else -> Unit
            }
        }
    }

    VideoCompressionResultScreen(
        state = state,
        onBackClick = navigateBack,
        onHomeClick = navigateHome,
        onEditClick =
            viewModel::onEditCompressedVideoClick,
        onShareClick = {
            val location = state.compressedVideoLocation ?: return@VideoCompressionResultScreen

            shareVideo(
                context = context,
                uri = Uri.parse(
                    location.value,
                ),
            )
        },
    )
}

private fun shareVideo(
    context: Context,
    uri: Uri,
) {
    val shareIntent =
        Intent(
            Intent.ACTION_SEND,
        ).apply {
            type = "video/mp4"

            putExtra(
                Intent.EXTRA_STREAM,
                uri,
            )

            addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION,
            )
        }

    context.startActivity(
        Intent.createChooser(
            shareIntent,
            null,
        ),
    )
}