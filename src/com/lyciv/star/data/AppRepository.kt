package com.lyciv.star.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

class AppRepository(private val context: Context) {

    fun getAllApps(): List<AppModel> {
        val pm = context.packageManager
        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val apps = pm.queryIntentActivities(mainIntent, 0).mapNotNull { ri ->
            try {
                val pkg = ri.activityInfo.packageName
                val label = ri.loadLabel(pm).toString()
                val icon = ri.loadIcon(pm)
                AppModel(pkg, label, icon)
            } catch (e: Exception) {
                null
            }
        }

        return apps.sortedBy { it.label.lowercase() }
    }

    fun getAppByPackage(packageName: String): AppModel? {
        val pm = context.packageManager
        return try {
            val appInfo = pm.getApplicationInfo(packageName, 0)
            val label = pm.getApplicationLabel(appInfo).toString()
            val icon = pm.getApplicationIcon(packageName)
            AppModel(packageName, label, icon)
        } catch (e: Exception) {
            null
        }
    }

    fun launchApp(packageName: String) {
        try {
            val intent = context.packageManager.getLaunchIntentForPackage(packageName)
            intent?.let { context.startActivity(it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
