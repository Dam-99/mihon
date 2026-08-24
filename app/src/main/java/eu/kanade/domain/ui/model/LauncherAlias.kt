package eu.kanade.domain.ui.model

/**
 * The available launcher activity aliases declared in the manifest.
 *
 * Each alias provides a different app name and icon in the launcher.
 * Note that the name shown in notification headers always comes from the
 * application label and is not affected by the enabled alias.
 */
enum class LauncherAlias(
    val componentClassName: String,
    val defaultEnabled: Boolean,
) {
    MIHON("Mihon", defaultEnabled = true),
    TACHIYOMI("Tachiyomi", defaultEnabled = false),
}
