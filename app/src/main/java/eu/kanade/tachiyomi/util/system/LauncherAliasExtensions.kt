package eu.kanade.tachiyomi.util.system

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import eu.kanade.domain.ui.model.LauncherAlias
import eu.kanade.tachiyomi.R

/**
 * Returns the currently enabled launcher alias, or null if none is enabled.
 */
fun Context.getLauncherAlias(): LauncherAlias? {
    return LauncherAlias.entries.firstOrNull { it.isComponentEnabled(this) }
}

/**
 * Enables [alias] and disables all other launcher aliases, changing the app
 * name and icon shown in the launcher.
 *
 * Note that the system may kill and restart the process for the change to
 * fully apply.
 */
fun Context.setLauncherAlias(alias: LauncherAlias) {
    LauncherAlias.entries.forEach { entry ->
        val state = if (entry == alias) {
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        } else {
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED
        }
        packageManager.setComponentEnabledSetting(
            entry.componentName(this),
            state,
            PackageManager.DONT_KILL_APP,
        )
    }
}

/**
 * Small icon resource used by notifications and about page, matching the active launcher alias.
 */
val Context.logoIconRes: Int
    get() = if (getLauncherAlias() == LauncherAlias.TACHIYOMI) R.drawable.ic_tachi else R.drawable.ic_mihon

/**
 * App icon resource matching the active launcher alias.
 */
val Context.launcherIconRes: Int
    get() = if (getLauncherAlias() == LauncherAlias.TACHIYOMI) R.mipmap.ic_tachi_launcher else R.mipmap.ic_launcher

/**
 * Applies [alias] and restarts the app so the new branding takes effect immediately.
 *
 * Note that the system may still kill the process while the component states change,
 * in which case the relaunch is left to the user.
 */
fun Context.applyLauncherAliasAndRestart(alias: LauncherAlias) {
    setLauncherAlias(alias)
    packageManager.getLaunchIntentForPackage(packageName)?.let {
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(it)
    }
    Runtime.getRuntime().exit(0)
}

private fun LauncherAlias.isComponentEnabled(context: Context): Boolean {
    return when (context.packageManager.getComponentEnabledSetting(componentName(context))) {
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER,
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED,
        -> false
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED -> true
        else -> defaultEnabled
    }
}

private fun LauncherAlias.componentName(context: Context): ComponentName {
    val fullyQualifiedComponentName = "eu.kanade.tachiyomi.${componentClassName}Alias"
    return ComponentName(context.packageName, fullyQualifiedComponentName)
}
