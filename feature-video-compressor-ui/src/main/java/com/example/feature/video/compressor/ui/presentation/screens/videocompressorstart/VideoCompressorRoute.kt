package com.example.feature.video.compressor.ui.presentation.screens.videocompressorstart

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.example.feature.video.compressor.ui.presentation.VideoCompressorNews
import com.example.feature.video.compressor.ui.presentation.VideoCompressorViewModel
import timber.log.Timber

@Composable
internal fun VideoCompressorRoute(
    viewModel: VideoCompressorViewModel,
    navigateSettings: () -> Unit,
    navigateCompressionSettings: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri -> uri?.let { viewModel.onVideoChosen(it) } }

    LaunchedEffect(viewModel, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(
            Lifecycle.State.STARTED
        ) {
            viewModel.news.collect { news ->
                when(news) {
                    is VideoCompressorNews.NavigateCompressionSettings -> {
                        Timber.d("navigation to compression settings")
                        navigateCompressionSettings()
                    }
                    is VideoCompressorNews.OpenVideoPicker -> {
                        launcher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.VideoOnly
                            )
                        )
                    }
                    else -> Unit
                }
            }
        }
    }

    VideoCompressorScreen(
        onSettingsClick = navigateSettings,
        onChooseVideoButtonClick = viewModel::onChooseVideoClick
    )
}