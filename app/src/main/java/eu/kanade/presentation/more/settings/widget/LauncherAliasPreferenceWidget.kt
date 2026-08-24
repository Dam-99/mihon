package eu.kanade.presentation.more.settings.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import eu.kanade.domain.ui.model.LauncherAlias
import eu.kanade.tachiyomi.R
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.i18n.stringResource

@Composable
fun LauncherAliasPreferenceWidget(
    value: LauncherAlias,
    appliedAlias: LauncherAlias?,
    title: String,
    subtitle: String?,
    onValueChange: (LauncherAlias) -> Unit,
    widget: (@Composable () -> Unit)? = null,
) {
    var isDialogShown by remember { mutableStateOf(false) }

    TextPreferenceWidget(
        title = title,
        subtitle = subtitle,
        widget = widget,
        onPreferenceClick = { isDialogShown = true },
    )

    if (isDialogShown) {
        AlertDialog(
            onDismissRequest = { isDialogShown = false },
            title = { Text(text = title) },
            text = {
                Column {
                    LauncherAlias.entries.forEach { alias ->
                        val isSelected = value == alias
                        val isApplied = appliedAlias == alias
                        DialogRow(
                            label = alias.displayLabel,
                            iconRes = alias.iconRes,
                            isSelected = isSelected,
                            onSelected = {
                                onValueChange(alias)
                                isDialogShown = false
                            },
                            // Only a true no-op (already saved AND already active) is
                            // untappable; a pending selection can be reconfirmed.
                            isApplied = isApplied,
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { isDialogShown = false }) {
                    Text(text = stringResource(MR.strings.action_cancel))
                }
            },
        )
    }
}

@Composable
private fun DialogRow(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    isApplied: Boolean,
    onSelected: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .selectable(
                selected = isSelected,
                onClick = { if (!isSelected || !isApplied) onSelected() },
            )
            .fillMaxWidth()
            .minimumInteractiveComponentSize(),
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
        )
        AsyncImage(
            model = iconRes,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 24.dp)
                .size(40.dp),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.merge(),
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}

private val LauncherAlias.iconRes: Int
    get() = when (this) {
        LauncherAlias.MIHON -> R.mipmap.ic_launcher
        LauncherAlias.TACHIYOMI -> R.mipmap.ic_tachi_launcher
    }

private val LauncherAlias.displayLabel: String
    get() {
        return this.componentClassName
    }
