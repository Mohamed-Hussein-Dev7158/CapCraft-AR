package io.github.artcraftandroid.capcraft.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import io.github.artcraftandroid.capcraft.R
import java.util.Locale

/**
 * App-specific language preference. Arabic is the first-run language; English remains available
 * from Settings. The preference is applied to Android resources before the Activity is created.
 */
object AppLocale {
    private const val PREFS = "capcraft_ar_preferences"
    private const val LANGUAGE_KEY = "language"

    fun language(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(LANGUAGE_KEY, "ar")?.takeIf { it == "ar" || it == "en" } ?: "ar"

    fun isArabic(context: Context): Boolean = language(context) == "ar"

    fun wrap(context: Context): Context {
        val locale = Locale.forLanguageTag(language(context))
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    fun change(context: Context, language: String) {
        require(language == "ar" || language == "en") { "Unsupported language: $language" }
        if (this.language(context) == language) return
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(LANGUAGE_KEY, language).apply()

        var current: Context? = context
        while (current != null && current !is Activity) {
            current = (current as? ContextWrapper)?.baseContext
        }
        (current as? Activity)?.recreate()
    }
}

/**
 * Transitional source-string catalog for the upstream UI, whose labels are currently authored
 * directly in Kotlin. Keeping the mapping in one place makes the first localization pass auditable
 * and avoids translating user-authored project names or media content.
 */
fun localizedText(context: Context, source: String): String {
    val id = when (source) {
        "Ready when you are" -> R.string.ready_when_you_are
        "Make your next video" -> R.string.make_your_next_video
        "Search projects" -> R.string.search_projects
        "All projects" -> R.string.all_projects
        "Close" -> R.string.close
        "More" -> R.string.more
        "Local" -> R.string.local
        "Trash" -> R.string.trash
        "Projects here are deleted for good after 30 days." -> R.string.deleted_after_30_days
        "The Trash is empty" -> R.string.trash_empty
        "All" -> R.string.all
        "Video" -> R.string.video
        "Photo" -> R.string.photo
        "Select" -> R.string.select
        "Done" -> R.string.done
        "No projects found" -> R.string.no_projects_found
        "No projects yet" -> R.string.no_projects_yet
        "Create" -> R.string.create
        "Edit" -> R.string.edit
        "Projects" -> R.string.projects
        "Me" -> R.string.account
        "Settings" -> R.string.settings
        "Clear cache" -> R.string.clear_cache
        "Licences" -> R.string.licences
        "About CapCraft" -> R.string.about_capcraft
        "Version" -> R.string.version
        "Clear the cache? Previews and waveforms are made again when needed; projects are kept." -> R.string.clear_cache_question
        "Clear" -> R.string.clear
        "Source code" -> R.string.source_code
        "A free video editor that keeps your work on your phone. Built on Concat's engine; free software under the GNU Affero General Public License, version 3 or later." -> R.string.about_description
        "Not affiliated with or endorsed by ByteDance or CapCut." -> R.string.not_affiliated
        "Select all" -> R.string.select_all
        "Deselect all" -> R.string.deselect_all
        "Delete" -> R.string.delete
        "Rename" -> R.string.rename
        "Duplicate" -> R.string.duplicate
        "Restore" -> R.string.restore
        "Delete for good" -> R.string.delete_for_good
        "Confirm" -> R.string.confirm
        "Cancel" -> R.string.cancel
        "Back" -> R.string.back
        "Untitled" -> R.string.untitled
        "Updated" -> R.string.updated
        "Audio tools" -> R.string.audio_tools
        "Photo tools" -> R.string.photo_tools
        "AutoCut" -> R.string.autocut
        "Retouch" -> R.string.retouch
        "Shoot and record" -> R.string.shoot_and_record
        "Auto enhance" -> R.string.auto_enhance
        "Cover maker" -> R.string.cover_maker
        "Auto captions" -> R.string.auto_captions
        "Remove background" -> R.string.remove_background
        "Adjust speed" -> R.string.adjust_speed
        "Collage" -> R.string.collage
        "Smart lighting" -> R.string.smart_lighting
        "Frame capture" -> R.string.frame_capture
        "Record" -> R.string.record
        "Reduce noise" -> R.string.reduce_noise
        "Enhance voice" -> R.string.enhance_voice
        "Extract audio" -> R.string.extract_audio
        "Lay two to nine photos or videos out together." -> R.string.collage_description
        "Brighten a dark photo in one tap." -> R.string.lighting_description
        "Keep a still from a video as a photo." -> R.string.frame_capture_description
        "Speak over a video as it plays." -> R.string.record_description
        "Quiet the hiss and hum behind a voice." -> R.string.noise_description
        "Make a voice clearer and closer." -> R.string.voice_description
        "Take a video's sound out as a clip of its own." -> R.string.extract_description
        "Export" -> R.string.export
        "Add media to start editing" -> R.string.add_media_to_start_editing
        "Add media first" -> R.string.add_media_first
        "Find a tool" -> R.string.find_a_tool
        "Undo" -> R.string.undo
        "Redo" -> R.string.redo
        "Layout" -> R.string.layout
        "Audio" -> R.string.audio
        "Text" -> R.string.text
        "Effects" -> R.string.effects
        "Overlay" -> R.string.overlay
        "Captions" -> R.string.captions
        "Filters" -> R.string.filters
        "Adjust" -> R.string.adjust
        "Stickers" -> R.string.stickers
        "Transcript" -> R.string.transcript
        "Aspect ratio" -> R.string.aspect_ratio
        "Background" -> R.string.background
        "Extract" -> R.string.extract
        "Sounds" -> R.string.sounds
        "Sound FX" -> R.string.sound_effects
        "Favorites" -> R.string.favorites
        "Search transitions" -> R.string.search_transitions
        "Apply to all" -> R.string.apply_to_all
        "Nothing by that name" -> R.string.nothing_by_that_name
        "Cover" -> R.string.cover
        "Mute\nclip" -> R.string.mute_clip
        "Unmute\nclip" -> R.string.unmute_clip
        "Opacity" -> R.string.opacity
        "Fade" -> R.string.fade
        "Fade in" -> R.string.fade_in
        "Fade out" -> R.string.fade_out
        "Ratio" -> R.string.ratio
        "None" -> R.string.none
        else -> 0
    }
    return if (id == 0) source else context.getString(id)
}

@Composable
fun localizedText(source: String): String = localizedText(LocalContext.current, source)
