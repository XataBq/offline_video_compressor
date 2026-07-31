package com.example.feature_video_compressor_impl.mappers

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.transformer.ExportException
import com.example.feature_video_compressor_api.model.CompressionError

@OptIn(UnstableApi::class)
internal fun Throwable.toCompressionError(): CompressionError {
    return when (this) {
        is SecurityException -> {
            CompressionError.InputFileUnavailable
        }

        is ExportException -> {
            when (errorCode) {
                ExportException.ERROR_CODE_IO_FILE_NOT_FOUND,
                ExportException.ERROR_CODE_IO_NO_PERMISSION -> {
                    CompressionError.InputFileUnavailable
                }

                ExportException.ERROR_CODE_DECODING_FORMAT_UNSUPPORTED -> {
                    CompressionError.UnsupportedFormat
                }

                ExportException.ERROR_CODE_ENCODER_INIT_FAILED,
                ExportException.ERROR_CODE_ENCODING_FORMAT_UNSUPPORTED -> {
                    CompressionError.EncoderUnavailable
                }

                ExportException.ERROR_CODE_MUXING_FAILED,
                ExportException.ERROR_CODE_MUXING_TIMEOUT -> {
                    CompressionError.OutputFileError
                }

                else -> {
                    CompressionError.Unknown
                }
            }
        }

        else -> {
            CompressionError.Unknown
        }
    }
}