package eu.kanade.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import eu.kanade.tachiyomi.util.system.getLauncherAlias
import mihon.app.di.appGraph
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.stringResource

@Composable
fun LauncherAliasPendingPrompt(isLaunch: Boolean) {
    val context = LocalContext.current
    val uiPreferences = remember { context.appGraph.uiPreferences }

    var pendingAliasChange by rememberSaveable { mutableStateOf(false) }
    if (isLaunch) {
        LaunchedEffect(Unit) {
            pendingAliasChange = uiPreferences.launcherAlias.get() != context.getLauncherAlias()
        }
    }
    if (pendingAliasChange) {
        ApplyLauncherAliasDialog(
            title = stringResource(MR.strings.pref_launcher_alias_pending_title),
            message = stringResource(MR.strings.pref_launcher_alias_pending_message),
            onDismiss = { pendingAliasChange = false },
        )
    }
}
