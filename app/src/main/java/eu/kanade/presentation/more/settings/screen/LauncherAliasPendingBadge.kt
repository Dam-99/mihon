package eu.kanade.presentation.more.settings.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import eu.kanade.presentation.components.RestartPendingBadge
import eu.kanade.tachiyomi.util.system.getLauncherAlias
import mihon.app.di.appGraph
import tachiyomi.presentation.core.util.collectAsState

@Composable
fun LauncherAliasPendingBadge() {
    val context = LocalContext.current
    val uiPreferences = remember { context.appGraph.uiPreferences }
    val launcherAlias by uiPreferences.launcherAlias.collectAsState()

    if (launcherAlias != context.getLauncherAlias()) {
        RestartPendingBadge()
    }
}
