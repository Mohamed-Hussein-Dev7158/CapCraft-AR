# CapCraft AR — Development Audit

**Audit date:** 2026-11-04  
**Audited branch:** `dev`  
**Audited revision:** `15b4d990eafbb0a7d7046055230fc8a54ddf2e24`  
**Scope:** Foundation work for stages 0–3 (repository audit, localization, RTL, typography).

## 1. Repository and build architecture

- The project is an Android application inside the Cargo workspace at `src/`.
- The Android UI is implemented in Kotlin with Jetpack Compose. The application namespace remains `io.github.artcraftandroid.capcraft`; the distributable application ID is `com.alaaeldinlabs.capcraftar`.
- The application delegates editing, media, timeline, rendering and export work to the Rust workspace. The Android bridge uses generated UniFFI Kotlin bindings.
- The Gradle build has dedicated tasks for Rust/NDK compilation, UniFFI binding generation, icon generation, and copying selected repository assets into generated Android resources.
- Audited Android configuration: min SDK 29, target SDK 36, compile SDK 37, Java 17, NDK 29.0.14206865. The default configured native ABI is arm64-v8a.
- The app starts the native engine once per process on an IO coroutine. Export work is delegated to the native session and an Android foreground service.

## 2. Native text and media architecture

- Timeline and project state are represented by the native editing engine and exposed to Kotlin through the Android bridge.
- The title renderer lives in `src/crates/concat-text`. Its dependencies include rustybuzz for shaping, unicode-bidi for bidirectional runs, unicode-linebreak for line breaks, unicode-script for script detection, fontdb for font discovery, and tiny-skia for rasterization.
- Arabic title rendering therefore has a native shaping/bidi foundation; this does not by itself prove that every title, caption, preview, or exported frame is visually correct on Android. Device verification remains required.
- The native text crate contains script fallback font assets, including the Noto Sans Arabic UI subset.

## 3. Localization baseline

- `AppLocale` stores an app-specific `ar`/`en` language preference, defaults to Arabic, wraps the Activity base context with the selected locale, and recreates the Activity after a language change.
- The Android resource catalogs currently contain 208 matching string keys in `values/strings.xml` and `values-ar/strings.xml`, with no duplicate keys or missing Arabic counterparts at the time of this audit.
- Kotlin also uses a transitional `localizedText` source-string catalog because much of the upstream interface was authored with literals. That catalog currently references 199 distinct resource IDs.
- The first localization pass covers the home screen, common editor navigation, core dock items, export and selected system messages. Several specialized panels still contain literal English text and require migration.
- Project names, file names, user-entered text, transcript content, media metadata, and model names must not be translated as though they were interface labels.

## 4. RTL baseline and constraints

- The selected Android locale is configured with its matching layout direction, and the Compose root provides RTL for Arabic.
- Navigation glyph mirroring is handled centrally by `Glyph` for a limited set of directional icons. Action icons should remain unmirrored.
- The timeline's coordinate and time model is intentionally left-to-right: the playhead advances from left to right in both interface languages. Its visual and gesture behavior must remain LTR while surrounding controls can follow RTL.
- A full RTL pass must check row order, paddings, sheet headers, arrows, media pickers, dialogs, text alignment, mixed Arabic/Latin text, and the timeline interaction model.

## 5. Typography baseline

- The app's Compose theme selects the generated `capcraft_arabic` font resource when Arabic is active. Gradle generates that resource from `src/crates/concat-text/fonts/NotoSansArabicUI-Concat.ttf`.
- The same font's SIL Open Font License notice is retained in third-party notices and copied into the app's licence material by the build configuration.
- The native title renderer has its own font discovery and script fallback path; the Compose UI font and native rendered-title font are separate paths and must be validated separately.
- Static source inspection cannot validate Arabic shaping, ligatures, diacritics, baseline metrics, clipping, line wrapping, or consistency between editor preview and exported media.

## 6. Licensing and distribution checks

- The project declares AGPL-3.0-or-later. The third-party notice file documents additional components and their licences, including GPL-3.0-only Slint usage and the licence terms of bundled or downloaded dependencies and font assets.
- Distribution preparation must preserve upstream and third-party notices and provide the corresponding source required by the applicable copyleft terms.
- This audit is not a complete legal review. Each release must regenerate and review the dependency/licence inventory for the actual build configuration and ABI.

## 7. Required verification before calling stages 0–3 complete

1. Build the Android app from a clean checkout using the documented Rust, FFmpeg, sherpa-onnx, Android SDK and NDK prerequisites.
2. Verify both locales on an Android device or emulator and inspect every UI screen, dialog, toast, content description, and notification.
3. Verify RTL/LTR directionality, especially that the timeline remains LTR and all hit targets match their visuals.
4. Verify Arabic UI font rendering, mixed Arabic/Latin text, text input, and accessibility scaling.
5. Verify Arabic text clips and captions in preview and in exported video; inspect line wrapping, glyph shaping, punctuation, numbers, and clipping.
6. Test export notifications, activity recreation after language changes, app relaunch, and project persistence.

**Verification status:** Repository/source inspection only. No Gradle build, APK generation, emulator run, or on-device QA was performed as part of this audit.
