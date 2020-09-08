package com.soufianekre.redpass.helpers

import android.content.Context
import com.soufianekre.redpass.R
import com.tozny.crypto.android.AesCbcWithIntegrity
import com.tozny.crypto.android.AesCbcWithIntegrity.keyString

class AesUtils {
    companion object {
        // Define salt string from GenerateSaltString test case
        // Generated salt string must be 172 characters
        const val SALT_STRING = ""

        fun encryptPassword(context: Context, plainText: String): String = when (plainText) {
            "" -> ""
            else -> {
                val savedPattern = "pattern"
                val key: AesCbcWithIntegrity.SecretKeys = AesCbcWithIntegrity.generateKeyFromPassword(savedPattern, SALT_STRING)

                // The encryption / storage & display:
                val civ = AesCbcWithIntegrity.encrypt(plainText, AesCbcWithIntegrity.keys(keyString(key)))
                civ.toString()
            }
        }

        fun decryptPassword(context: Context, cipherText: String): String = when (cipherText) {
            "" -> ""
            else -> {
                val savedPattern = "pattern"
                decryptPassword(context, cipherText, savedPattern)
            }
        }

        fun decryptPassword(context: Context, cipherText: String, patternString: String): String = when (cipherText) {
            "" -> ""
            else -> {
                val key: AesCbcWithIntegrity.SecretKeys = AesCbcWithIntegrity.generateKeyFromPassword(patternString, SALT_STRING)
                var plainText:String = ""
                try {
                    val cipherTextIvMac = AesCbcWithIntegrity.CipherTextIvMac(cipherText)
                    plainText = AesCbcWithIntegrity.decryptString(cipherTextIvMac, AesCbcWithIntegrity.keys(keyString(key)))
                } catch (e: Exception) {
                    plainText = context.getString(R.string.aes_utils_decrypt_error)
                }
                plainText
            }
        }
    }
}
