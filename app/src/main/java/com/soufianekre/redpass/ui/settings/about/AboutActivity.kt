package com.soufianekre.redpass.ui.settings.about



import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.google.android.material.card.MaterialCardView
import com.mikepenz.aboutlibraries.LibsBuilder
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.soufianekre.redpass.R


class AboutActivity : MaterialAboutActivity() {



    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        return MaterialAboutList.Builder()
            .addCard(setupAppInfoCard())
            .addCard(setupDevInfoCard())
            .build()
    }

    override fun getActivityTitle(): CharSequence? {
        return  getString(R.string.title_about)
    }

    fun setupAppInfoCard(): MaterialAboutCard {
        val devInfo: MaterialAboutCard.Builder = MaterialAboutCard.Builder()
            .title("App")
        devInfo.addItem(
            MaterialAboutTitleItem.Builder()
                .text("Clippy Android")
                .desc("© 2020 Soufiane Kremcht")
                .icon(R.mipmap.ic_launcher)
                .build()
        )
        devInfo.addItem(
            ConvenienceBuilder.createVersionActionItem(
                this,
                IconicsDrawable(this)
                    .icon(CommunityMaterial.Icon.cmd_information_outline)
                    .sizeDp(18),
                "Version",
                false
            )
        )
        devInfo.addItem(
            MaterialAboutActionItem.Builder()
                .text("Open Source Licenses")
                .icon(
                    IconicsDrawable(this)
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .sizeDp(18)
                )
                .setOnClickAction {
                    LibsBuilder()
                        .withAboutAppName(getString(R.string.app_name))
                        .withActivityTitle("Licenses")
                        .start(this@AboutActivity)
                }
                .build()
        )
        return devInfo.build()
    }

    private fun setupDevInfoCard(): MaterialAboutCard {
        val authorCardBuilder: MaterialAboutCard.Builder = MaterialAboutCard.Builder().title("Author")
        authorCardBuilder.addItem(
            MaterialAboutActionItem.Builder()
                .text("Soufiane Kre")
                .subText("Morocco")
                .icon(
                    IconicsDrawable(this)
                        .icon(CommunityMaterial.Icon.cmd_account)
                        .sizeDp(18)
                )
                .build()
        )
        authorCardBuilder.addItem(
            MaterialAboutActionItem.Builder()
                .text("visit my GitHub")
                .icon(
                    IconicsDrawable(this)
                        .icon(CommunityMaterial.Icon.cmd_github_circle)
                        .sizeDp(18)
                )
                .setOnClickAction(
                    ConvenienceBuilder.createWebsiteOnClickAction(
                        this,
                        Uri.parse("https://github.com/SoufianeKre")
                    )
                )
                .build()
        )
        return authorCardBuilder.build()
    }


}



