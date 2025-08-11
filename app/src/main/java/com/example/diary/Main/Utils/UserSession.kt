package com.example.diary.Main.Utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class UserSession(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    private val pref = EncryptedSharedPreferences.create(
        context, PREF_NAME, masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val edit = pref.edit()
    fun saveUserPassword(password: String) {
        edit.apply {
            putString(PASS, password)
            apply()
        }
    }

    fun getUserPassword(): String {
        return pref.getString(PASS, "") ?: ""
    }

    fun saveDiaryName(name: String) {
        edit.apply {
            putString(DIARY_NAME, name)
            apply()
        }
    }

    fun getDiaryName(): String {
        return pref.getString(DIARY_NAME, "") ?: ""
    }

    fun usePassword(passwordRequired: Boolean) {
        edit.apply {
            putBoolean(USE_PASS, passwordRequired)
            apply()
        }
    }

    fun isPasswordRequired(): Boolean {
        return pref.getBoolean(USE_PASS, false)
    }


    companion object {
        private const val PREF_NAME = "USER_DATA"
        private const val PASS = "PASSWORD"
        private const val USE_PASS = "USER_PASSWORD"
        private const val DIARY_NAME = "DIARY_NAME"
    }
}