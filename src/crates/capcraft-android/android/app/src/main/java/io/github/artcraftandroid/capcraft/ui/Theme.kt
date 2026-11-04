package io.github.artcraftandroid.capcraft.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import io.github.artcraftandroid.capcraft.R

/** CapCraft's palette: a black editor with one cyan accent. */
object Palette {
    val Black = Color(0xFF0A0A0A)
    /** The editor's chrome: top bar, timeline and dock. */
    val Chrome = Color(0xFF101010)
    /** Panels and sheets over the editor. */
    val Panel = Color(0xFF151515)
    /** Cards and raised rows. */
    val Card = Color(0xFF202020)
    val CardPressed = Color(0xFF292929)
    val Divider = Color(0xFF303030)
    val Accent = Color(0xFF81CCEC)
    val SecondaryAccent = Color(0xFF7CC5E2)
    /** Words on the accent: the Export button's dark ink. */
    val OnAccent = Color(0xFF0A0A0A)
    /** The editor's top part: top bar, preview surround and play bar. */
    val EditorTop = Color(0xFF0A0A0A)
    /** The timeline and the bottom bar under it. */
    val Timeline = Color(0xFF151515)
    /** The bottom bar while a clip is selected, and the panels. */
    val DockSelected = Color(0xFF1D1D1D)
    /** The back button of a bottom bar level. */
    val DockBack = Color(0xFF303030)
    /** Labels and icons in the bottom bar. */
    val DockText = Color(0xFFD4D4D4)
    /** The resolution chip in the top bar. */
    val Chip = Color(0xFF292A2C)
    /** The "add audio" / "add text" rows and their tiles. */
    val HintRow = Color(0xFF262626)
    val HintIcon = Color(0xFF6B6B70)
    val Text = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFA3A3A3)
    val TextTertiary = Color(0xFF6B6B6B)
    val Disabled = Color(0xFF4D4D4D)
    /** Timeline clip colours by row. */
    val TextClip = Color(0xFFE67300)
    /** Captions, a lighter amber than titles. */
    val CaptionClip = Color(0xFFDA9208)
    val EffectClip = Color(0xFF7E61EE)
    val FilterClip = Color(0xFFE36BBF)
    val StickerClip = Color(0xFFDCB108)
    val AudioClip = Color(0xFF2A5256)
    /** Beat marks on sound clips. */
    val Beat = Color(0xFFFFD60A)
    val AudioWave = Color(0xFF7CC5E2)
    val OverlayClip = Color(0xFF2B2B2B)
    val Danger = Color(0xFFFF4D4F)
}

object Type {
    val Title = TextStyle(color = Palette.Text, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    val Body = TextStyle(color = Palette.Text, fontSize = 14.sp)
    val BodyStrong = TextStyle(color = Palette.Text, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    val Small = TextStyle(color = Palette.TextSecondary, fontSize = 12.sp)
    val Tiny = TextStyle(color = Palette.TextSecondary, fontSize = 10.sp)
    val DockLabel = TextStyle(color = Palette.Text, fontSize = 10.sp)
}

@Composable
fun CapCraftTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val appTypeface = if (AppLocale.isArabic(context)) {
        FontFamily(Font(R.font.capcraft_arabic, FontWeight.Normal))
    } else {
        FontFamily.Default
    }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Palette.Accent,
            onPrimary = Palette.OnAccent,
            background = Palette.Black,
            surface = Palette.Panel,
            onSurface = Palette.Text,
            onBackground = Palette.Text,
        ),
    ) {
        ProvideTextStyle(value = TextStyle(fontFamily = appTypeface)) {
            content()
        }
    }
}
