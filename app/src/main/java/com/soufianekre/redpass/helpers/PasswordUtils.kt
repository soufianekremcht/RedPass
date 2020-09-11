package com.soufianekre.redpass.helpers

import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.*


object PasswordUtils{

    private const val CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz"
    private  val CHAR_UPPER = CHAR_LOWER.toUpperCase()
    private const val NUMBER = "0123456789"
    private const val OTHER_CHAR = "!@#$%&*()_+-=[]?"

    private val PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR
    // optional, make it more random
    private val PASSWORD_ALLOW_BASE_SHUFFLE = shuffleString(PASSWORD_ALLOW_BASE)
    private val PASSWORD_ALLOW = PASSWORD_ALLOW_BASE_SHUFFLE

    private val random = SecureRandom()

    @JvmStatic
    fun main(args: Array<String>) {

        System.out.format("String for password \t\t\t: %s%n", PASSWORD_ALLOW_BASE)
        System.out.format("String for password (shuffle) \t: %s%n%n", PASSWORD_ALLOW)

        // generate 5 random password
        for (i in 0..4) {
            println("password : " + generateRandomPassword(15))
            println("\n")
        }

    }

    fun generateRandomPassword(length: Int): String {
        require(length >= 1)

        val sb = StringBuilder(length)
        for (i in 0 until length) {

            val rndCharAt = random.nextInt(PASSWORD_ALLOW.length)
            val rndChar = PASSWORD_ALLOW[rndCharAt]

            // debug
            Log.w("Password generator",String.format("%d\t:\t%c%n", rndCharAt, rndChar))
            sb.append(rndChar)

        }

        return sb.toString()

    }

    // shuffle
    private fun shuffleString(string: String): String {
        val letters = listOf(string.split("".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        Collections.shuffle(letters)
        return letters.joinToString()
    }

    // encrypt Password;
    fun md5(s: String): String {
        val MD5 = "MD5"
        try {
            // Create MD5 Hash
            val digest = MessageDigest
                .getInstance(MD5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()

            // Create Hex String
            val hexString = java.lang.StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    // decrypt Password
}