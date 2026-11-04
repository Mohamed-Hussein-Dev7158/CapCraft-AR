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
        "App language" -> R.string.language
        "Color" -> R.string.color
        "Image" -> R.string.image
        "Blur" -> R.string.blur
        "Rotate" -> R.string.rotate
        "Advanced" -> R.string.advanced
        "Normal" -> R.string.normal
        "Volume" -> R.string.volume
        "Speed" -> R.string.speed
        "Replace" -> R.string.replace
        "Retouch" -> R.string.retouch
        "Style" -> R.string.style
        "Recommended" -> R.string.recommended
        "Trending" -> R.string.trending
        "Tools" -> R.string.tools
        "Flip video horizontally" -> R.string.flip_video_horizontally
        "Add auto captions" -> R.string.add_auto_captions
        "Reduce video noise" -> R.string.reduce_video_noise
        "Noise reduction" -> R.string.noise_reduction
        "Add a clip first" -> R.string.add_a_clip_first
        "Loudness adjustment" -> R.string.loudness_adjustment
        "Transitions" -> R.string.transitions
        "Change audio pitch" -> R.string.change_audio_pitch
        "Press and hold one to keep it here" -> R.string.press_and_hold_to_save
        "Added to favorites" -> R.string.added_to_favorites
        "Removed from favorites" -> R.string.removed_from_favorites
        "Takes the hum and hiss out from behind the sound." -> R.string.noise_reduction_description
        "Cleared %1$s of temporary files" -> R.string.cache_cleared
        "Full screen" -> R.string.full_screen
        "Exit full screen" -> R.string.exit_full_screen

        "Clear cache" -> R.string.clear_cache
        "Move the playhead inside the clip to split it" -> R.string.move_playhead_to_split
        "The text now shows for the whole video" -> R.string.text_shows_for_whole_video

        "Applied to all" -> R.string.applied_to_all
        "Licences" -> R.string.licences
        "GNU Affero General Public License" -> R.string.gnu_agpl
        "Engine third-party notices" -> R.string.engine_third_party_notices
        "Lucide icons" -> R.string.lucide_icons
        "Material Symbols" -> R.string.material_symbols
        "Noto Sans Arabic UI font" -> R.string.noto_sans_arabic_ui_font
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
        "Export failed" -> R.string.export_failed
        "Exporting video" -> R.string.exporting_video
        "Exports" -> R.string.export_notification_channel
        "Try again" -> R.string.try_again
        "New project" -> R.string.new_project
        "You can leave CapCraft while the video saves" -> R.string.export_background_tip
        "Saved to Pictures" -> R.string.saved_to_pictures
        "Saved to your device" -> R.string.saved_to_device
        "Share" -> R.string.share
        "Open" -> R.string.open
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

        "Add text" -> R.string.add_text
        "Video effects" -> R.string.video_effects
        "Body effects" -> R.string.body_effects
        "Enter captions" -> R.string.enter_captions
        "Caption templates" -> R.string.caption_templates
        "Import captions" -> R.string.import_captions
        "Export captions" -> R.string.export_captions
        "Text template" -> R.string.text_template
        "Auto lyrics" -> R.string.auto_lyrics
        "Add overlay" -> R.string.add_overlay
        "Text to audio" -> R.string.text_to_audio
        "Auto Captions" -> R.string.auto_captions_tool
        "Draw" -> R.string.draw
        "Curve" -> R.string.curve
        "Mirror" -> R.string.mirror
        "Auto removal" -> R.string.auto_removal
        "Custom removal" -> R.string.custom_removal
        "Chroma key" -> R.string.chroma_key
        "Change BG" -> R.string.change_background
        "Split" -> R.string.split
        "Graphs" -> R.string.graphs
        "Layers" -> R.string.layers
        "Animations" -> R.string.animations
        "Multi-select" -> R.string.multi_select
        "Basic" -> R.string.basic
        "Tracking" -> R.string.tracking
        "Edit captions" -> R.string.edit_captions
        "Text to speech" -> R.string.text_to_speech
        "Splice" -> R.string.splice
        "Beats" -> R.string.beats
        "Crop" -> R.string.crop
        "Maintrack" -> R.string.main_track
        "Video quality" -> R.string.video_quality
        "Remove BG" -> R.string.remove_bg
        "Relight" -> R.string.relight
        "Motion blur" -> R.string.motion_blur
        "Transform" -> R.string.transform
        "Layer" -> R.string.layer
        "Auto reframe" -> R.string.auto_reframe
        "Stabilize" -> R.string.stabilize
        "Camera tracking" -> R.string.camera_tracking
        "Recover audio" -> R.string.recover_audio
        "Isolate voice" -> R.string.isolate_voice
        "Audio effects" -> R.string.audio_effects
        "Freeze" -> R.string.freeze
        "Reverse" -> R.string.reverse
        "Mask" -> R.string.mask
        "Lasting text" -> R.string.lasting_text
        "Auto split" -> R.string.auto_split
        "Object" -> R.string.object
        "Describe what you want to do" -> R.string.describe_edit


        "Opacity" -> R.string.opacity
        "Fade" -> R.string.fade
        "Fade in" -> R.string.fade_in
        "Fade out" -> R.string.fade_out
        "Ratio" -> R.string.ratio
        "None" -> R.string.none
        "Reset" -> R.string.reset
        "Text and stickers" -> R.string.text_and_stickers
        "To front" -> R.string.to_front
        "Nothing on screen here to arrange" -> R.string.nothing_to_arrange
        "To back" -> R.string.to_back
        "Search audio effects" -> R.string.search_audio_effects
        "Light" -> R.string.intensity_light
        "Intense" -> R.string.intensity_intense
        "Auto-generate" -> R.string.auto_generate
        "Listening…" -> R.string.listening
        "Super resolution" -> R.string.super_resolution
        "Enter text" -> R.string.enter_text
        "Apply to all captions" -> R.string.apply_to_all_captions
        "Import" -> R.string.import
        "Type the words first, then choose how they come and go." -> R.string.type_words_animations
        "Voiceover" -> R.string.voiceover
        "Spoken language" -> R.string.spoken_language
        "Voice" -> R.string.voice
        "Replace current captions" -> R.string.replace_current_captions
        "Generate from" -> R.string.generate_from
        "All sound" -> R.string.all_sound
        "Download and generate" -> R.string.download_and_generate
        "Generate" -> R.string.generate
        "Tap the cross to stop" -> R.string.tap_cross_to_stop
        "Made on this phone; nothing is uploaded." -> R.string.on_device_privacy
        "Default" -> R.string.default_style
        "No speech was found" -> R.string.no_speech_found
        "Search your sounds" -> R.string.search_your_sounds
        "Your audio" -> R.string.your_audio
        "Extracted" -> R.string.extracted_audio
        "Device" -> R.string.device_audio
        "Extract audio from video" -> R.string.extract_audio_from_video
        "Allow access to the music on this phone to list it here." -> R.string.allow_music_access
        "Choose a file instead" -> R.string.choose_file_instead
        "No music on this phone" -> R.string.no_music_on_device
        "Sounds you bookmark show here." -> R.string.bookmarked_sounds
        "Choose 2 to 9" -> R.string.choose_two_to_nine
        "Add" -> R.string.add
        "Search effects" -> R.string.search_effects
        "Search filters" -> R.string.search_filters
        "Search animations" -> R.string.search_animations
        "Type what you will say; it scrolls over the picture while you record." -> R.string.type_while_recording
        "Type the words to be read aloud" -> R.string.type_words_to_read
        "Color picker" -> R.string.color_picker
        "Invert" -> R.string.invert
        "Select tracking subject" -> R.string.select_tracking_subject
        "Custom" -> R.string.custom
        "Reading the picture…" -> R.string.reading_picture
        "Save" -> R.string.save
        "Search stickers" -> R.string.search_stickers
        "No stickers found" -> R.string.no_stickers_found
        "Search sound effects" -> R.string.search_sound_effects
        "Search sounds" -> R.string.search_sounds
        "Choose a file" -> R.string.choose_file
        "Duration" -> R.string.duration
        "Original" -> R.string.original
        "Search music" -> R.string.search_music
        "No results" -> R.string.no_results
        "Downloading" -> R.string.downloading
        "Unpacking" -> R.string.unpacking
        "Reading the sound" -> R.string.reading_sound
        "Loading" -> R.string.loading
        "Separating the voice" -> R.string.separating_voice
        "Recognizing" -> R.string.recognizing
        "Captions added" -> R.string.captions_added
        "Hi there" -> R.string.preview_sample_greeting
        "This effect has nothing to set" -> R.string.effect_no_settings
        "Blurring…" -> R.string.blurring
        "Steadying…" -> R.string.steadying
        "Smoothing the motion…" -> R.string.smoothing_motion
        "Removing flickers…" -> R.string.removing_flickers
        "Curves" -> R.string.curves
        "Size" -> R.string.size
        "Thickness" -> R.string.thickness
        "Zoom" -> R.string.zoom
        "Character" -> R.string.character_spacing
        "Line" -> R.string.line_spacing
        "Round" -> R.string.round
        "Width" -> R.string.width
        "Height" -> R.string.height
        "Intensity" -> R.string.intensity
        "Range" -> R.string.range
        "Distance" -> R.string.distance
        "Angle" -> R.string.angle
        "Press and hold one to keep it here." -> R.string.press_hold_save_period
        "Type the words first, then give them an effect." -> R.string.type_words_effect
        "Type the words first, then give them this." -> R.string.type_words_style
        "Read by: Text to speech" -> R.string.read_by_tts
        "Delete beat" -> R.string.delete_beat
        "Add beat" -> R.string.add_beat
        "Stop" -> R.string.stop
        "Try an example" -> R.string.try_example
        "Type or paste a script. Each line becomes a caption of its own." -> R.string.script_hint
        "A clearer, sharper picture, made on this phone. A photo takes a moment; a video goes frame by frame and takes a while." -> R.string.super_resolution_description
        "Blurring" -> R.string.blur_label
        else -> 0
    }
    return if (id == 0) source else context.getString(id)
}

@Composable
fun localizedText(source: String): String = localizedText(LocalContext.current, source)
