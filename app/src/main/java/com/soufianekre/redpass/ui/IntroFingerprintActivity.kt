package com.soufianekre.redpass.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.github.ajalt.reprint.core.AuthenticationFailureReason
import com.github.ajalt.reprint.core.AuthenticationListener
import com.github.ajalt.reprint.core.Reprint
import com.soufianekre.redpass.RedPassApp
import com.soufianekre.redpass.R
import com.soufianekre.redpass.data.app_pref.PrefConst
import com.soufianekre.redpass.ui.base.BaseActivity
import com.soufianekre.redpass.ui.intro.IntroActivity
import com.soufianekre.redpass.ui.main.MainActivity
import timber.log.Timber

class IntroFingerprintActivity : BaseActivity() {

    private val SUCCESS = "success"
    private val FAILURE = "failure"

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finger_print)
        val activity = this
        if (!RedPassApp.getPref().getBoolean(PrefConst.PREF_SECURITY_FINGER_PRINT_LOCK,false)){
            val intent  = Intent (this,IntroActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            if (Reprint.hasFingerprintRegistered()){
                Reprint.authenticate(object : AuthenticationListener {
                    override fun onSuccess(moduleTag: Int) {
                        val intent = Intent(activity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    override fun onFailure(
                        failureReason: AuthenticationFailureReason?,
                        fatal: Boolean,
                        errorMessage: CharSequence?,
                        moduleTag: Int,
                        errorCode: Int) {
                        showError("Error : $errorCode")
                        Timber.e("Error $errorCode")
                    }
                })

            }else{
                MaterialDialog(this).show {
                    title(text = "Finger Print Lock")
                    message(text = "You did not register your finger print in your phone.")
                }
            }

        }




        // New Finger Print Authentication using BioMetric
        /*

        val executor = Executors.newSingleThreadExecutor()

        val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative/cancel button
                } else {

                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                runOnUiThread{
                    startActivity(Intent(activity, MainActivity::class.java))
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                runOnUiThread{
                    Toast.makeText(activity,"Authentication failed! Please try again.", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication prompt!")
            /*Subtitle and description are optional parameters, so, you can skip those parameters.
            .setSubtitle("Set the subtitle to display.")
            .setDescription("Verification required")*/
            .setNegativeButtonText("Cancel")
            .build()

        // show the authentication
        biometricPrompt.authenticate(promptInfo)

        // cancel authentication
        biometricPrompt.cancelAuthentication()


         */

    }


}