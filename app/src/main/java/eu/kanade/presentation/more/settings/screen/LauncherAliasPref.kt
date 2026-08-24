package eu.kanade.presentation.more.settings.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import eu.kanade.domain.ui.UiPreferences
import eu.kanade.presentation.components.ApplyLauncherAliasDialog
import eu.kanade.presentation.components.RestartPendingBadge
import eu.kanade.presentation.more.settings.Preference
import eu.kanade.presentation.more.settings.widget.LauncherAliasPreferenceWidget
import eu.kanade.tachiyomi.util.system.getLauncherAlias
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.util.collectAsState

@Composable
fun getLauncherAliasPref(uiPreferences: UiPreferences): Preference.PreferenceItem<Unit, Unit> {
    val context = LocalContext.current
    val launcherAlias by uiPreferences.launcherAlias.collectAsState()

    var pendingAliasChange by remember { mutableStateOf(false) }

    if (pendingAliasChange) {
        ApplyLauncherAliasDialog(
            title = stringResource(MR.strings.pref_launcher_alias_apply_title),
            message = stringResource(MR.strings.pref_launcher_alias_apply_message),
            onDismiss = { pendingAliasChange = false },
        )
    }

    return Preference.PreferenceItem.CustomPreference(
        title = stringResource(MR.strings.pref_launcher_alias),
    ) {
        LauncherAliasPreferenceWidget(
            value = launcherAlias,
            appliedAlias = context.getLauncherAlias(),
            title = stringResource(MR.strings.pref_launcher_alias),
            subtitle = stringResource(MR.strings.pref_launcher_alias_summary),
            onValueChange = {
                uiPreferences.launcherAlias.set(it)
                pendingAliasChange = it != context.getLauncherAlias()
            },
            widget = {
                if (launcherAlias != context.getLauncherAlias()) {
                    RestartPendingBadge()
                }
            },
        )
    }
}
