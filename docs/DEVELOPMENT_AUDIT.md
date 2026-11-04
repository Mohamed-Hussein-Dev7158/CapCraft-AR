# CapCraft AR — Development Audit

**Audit date:** 2026-11-04  
**Audited branch:** `dev`  
**Audited source snapshot:** `a0e9644d96dab895813340916439e93912ea6baa`  
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
- The Android resource catalogs contain 380 English string keys and 380 Arabic counterparts. Static checks found no duplicate resource keys or missing Arabic counterparts.
- Kotlin uses a transitional `localizedText` source-string catalog because much of the upstream interface was authored with literals. The catalog contains 354 distinct source-string mappings, and every referenced `R.string` ID resolves in the resource catalog.
- The expanded localization pass covers home/project management, editor dock and search, shared UI, media selection, clip tools, text/caption workflows, audio tools, adjustment/effects panels, and export controls/status messages.
- Literal `localizedText("...")` calls across the 60 Kotlin files were checked against the mapping catalog. User-created project names, filenames, imported media names, transcript contents, effect/model identifiers, and other user/media data are intentionally not translated.
- This remains a source-level localization pass. A final manual audit of all upstream text, accessibility output, package names, and engine-originated error messages is still required during runtime QA.

## 4. RTL baseline and constraints

- The selected Android locale is configured with its matching layout direction, and the Compose root provides RTL for Arabic.
- Navigation glyph mirroring is handled centrally by `Glyph` for directional chevrons and arrows; action icons remain unmirrored.
- `Timeline.kt` explicitly wraps its contents in `LayoutDirection.Ltr`. The timeline time axis and gesture coordinates therefore remain left-to-right while the surrounding interface follows the selected locale.
- Source inspection confirms the intended direction policy, but row order, padding, sheet headers, arrows, media pickers, dialogs, text alignment, mixed Arabic/Latin content, and touch targets still require visual and interaction QA on Android.

## 5. Typography baseline

- The app's Compose theme selects the generated `capcraft_arabic` font resource when Arabic is active. Gradle generates that resource from `src/crates/concat-text/fonts/NotoSansArabicUI-Concat.ttf`.
- The same font's SIL Open Font License notice is retained in third-party notices and copied into the app's licence material by the build configuration.
- The native title renderer has its own font discovery and script fallback path; the Compose UI font and native rendered-title font are separate paths and must be validated separately.
- Static source inspection cannot validate Arabic shaping, ligatures, diacritics, baseline metrics, clipping, line wrapping, or consistency between editor preview and exported media.

## 6. Licensing and distribution checks

- The project declares AGPL-3.0-or-later. The third-party notice file documents additional components and their licences, including GPL-3.0-only Slint usage and the licence terms of bundled or downloaded dependencies and font assets.
- Distribution preparation must preserve upstream and third-party notices and provide the corresponding source required by the applicable copyleft terms.
- This audit is not a complete legal review. Each release must regenerate and review the dependency/licence inventory for the actual build configuration and ABI.

## 7. Static verification performed

- English/Arabic resource parity: 380 keys in each catalog; no missing counterparts or duplicate keys.
- Resource integrity: no unescaped XML ampersands; all 354 distinct `R.string` references in `AppLocale` resolve.
- Localization call sites: literal `localizedText("...")` calls across all 60 Kotlin files were checked for a matching catalog case; no missing literal mappings were found.
- Direction policy: the app root follows the locale, directional icon mirroring is centralized, and `Timeline.kt` explicitly keeps its content LTR.
- Typography wiring: Gradle generates `res/font/capcraft_arabic.ttf` from `NotoSansArabicUI-Concat.ttf`; `Theme.kt` selects it for Arabic Compose UI. The font licence file is copied into app licence assets.
- Native renderer source includes rustybuzz shaping, unicode-bidi processing, Arabic font fallback, and a Rust glyph-coverage test containing Arabic sample text.

## 8. Required verification before calling stages 0–3 complete

1. Build the Android app from a clean checkout using the documented Rust, FFmpeg, sherpa-onnx, Android SDK and NDK prerequisites.
2. Verify both locales on an Android device or emulator and inspect every UI screen, dialog, toast, content description, and notification.
3. Verify RTL/LTR directionality, especially that the timeline remains LTR and all hit targets match their visuals.
4. Verify Arabic UI font rendering, mixed Arabic/Latin text, text input, and accessibility scaling.
5. Verify Arabic text clips and captions in preview and in exported video; inspect line wrapping, glyph shaping, punctuation, numbers, and clipping.
6. Test export notifications, activity recreation after language changes, app relaunch, and project persistence.

**Verification status:** Repository and source inspection plus static consistency checks only. No Gradle build, APK generation, emulator run, screenshot review, Arabic preview/export test, or on-device QA was performed. Stages 0–3 have had their source-level audit and implementation pass, but runtime/build verification remains open.
