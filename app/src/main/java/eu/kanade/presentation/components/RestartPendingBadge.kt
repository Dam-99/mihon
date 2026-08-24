package eu.kanade.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.Badge
import tachiyomi.presentation.core.i18n.stringResource

@Composable
fun RestartPendingBadge(modifier: Modifier = Modifier) {
    Badge(
        text = stringResource(MR.strings.label_restart_pending),
        modifier = modifier,
        color = MaterialTheme.colorScheme.tertiary,
        textColor = MaterialTheme.colorScheme.onTertiary,
        shape = RoundedCornerShape(percent = 50),
    )
}
