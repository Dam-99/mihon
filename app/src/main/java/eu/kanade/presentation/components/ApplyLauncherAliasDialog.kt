package eu.kanade.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import eu.kanade.tachiyomi.util.system.applyLauncherAliasAndRestart
import mihon.app.di.appGraph
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.stringResource

/**
 * Prompts to restart the app so the launcher alias saved in preferences can be applied.
 *
 * Callers supply their own copy, but the behavior is identical everywhere: confirming
 * applies whichever alias is currently stored in preferences, dismissing is a no-op.
 */
@Composable
fun ApplyLauncherAliasDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current
    val uiPreferences = remember { context.appGraph.uiPreferences }

    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = message) },
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    context.applyLauncherAliasAndRestart(uiPreferences.launcherAlias.get())
                },
            ) {
                Text(text = stringResource(MR.strings.action_restart_app))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(MR.strings.action_cancel))
            }
        },
    )
}
