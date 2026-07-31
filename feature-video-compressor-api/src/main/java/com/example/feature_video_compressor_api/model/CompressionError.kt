package com.example.feature_video_compressor_api.model

sealed interface CompressionError {

    data object InputFileUnavailable : CompressionError

    data object UnsupportedFormat : CompressionError

    data object EncoderUnavailable : CompressionError

    data object OutputFileError : CompressionError

    data object Unknown : CompressionError
}