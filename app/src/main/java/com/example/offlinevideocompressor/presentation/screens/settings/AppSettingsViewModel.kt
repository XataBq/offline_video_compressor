package com.example.offlinevideocompressor.presentation.screens.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class AppSettingsViewModel @Inject constructor() : ViewModel() {

    val selectedLanguage: AppLanguage
        get() {
            val applicationLocales =
                AppCompatDelegate
                    .getApplicationLocales()

            val language =
                if (!applicationLocales.isEmpty) {
                    applicationLocales[0]
                        ?.language
                } else {
                    Locale.getDefault()
                        .language
                }

            return when (language) {
                LANGUAGE_RUSSIAN ->
                    AppLanguage.Russian

                else ->
                    AppLanguage.English
            }
        }

    fun onLanguageSelected(
        language: AppLanguage,
    ) {
        if (language == selectedLanguage) {
            return
        }

        val languageTag =
            when (language) {
                AppLanguage.Russian ->
                    LANGUAGE_RUSSIAN

                AppLanguage.English ->
                    LANGUAGE_ENGLISH
            }

        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(
                languageTag,
            ),
        )
    }

    private companion object {
        const val LANGUAGE_RUSSIAN = "ru"
        const val LANGUAGE_ENGLISH = "en"
    }
}