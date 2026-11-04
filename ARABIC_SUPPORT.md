# CapCraft AR — Arabic localization and interface direction

This document records the terminology and behavior decisions for the Arabic-first interface. Translations are chosen for the job a control performs in a video editor, not by matching English words one by one.

## Language behavior

- Arabic is the first-run language.
- English is available from Settings → App language.
- The selection is saved locally and applied when the Activity is recreated.
- Project names, imported media names, filenames, and text written by the user are not translated.
- Arabic UI direction is RTL. English UI direction is LTR.

## Editing terminology

| Upstream label | Arabic UI wording | Why this wording |
|---|---|---|
| Edit | تحرير | The action of working on a project, not “edit” as a text correction. |
| Projects | المشاريع | Familiar project-library wording. |
| Audio tools | أدوات الصوت | Names the tool group, not audio files generally. |
| Photo tools | أدوات الصور | Describes the available image operations. |
| Cover maker | تصميم الغلاف | Explains that the tool creates a project/video cover. |
| Smart lighting | تحسين الإضاءة | Describes the actual result instead of translating “smart” literally. |
| Frame capture | استخراج لقطة | The operation saves a still frame from a video. |
| Auto captions | إنشاء ترجمة نصية تلقائيًا | A familiar Arabic term for on-screen subtitles generated from speech. |
| Transcript | تفريغ الكلام | Distinguishes a spoken-word transcript from translation between languages. |
| Overlay | طبقة إضافية | Describes the extra layer placed above the main footage. |
| Splice | مزج | In this editor the action opens layer blending, not cutting footage. |
| Text to speech | تحويل النص إلى كلام | Names the direction of the conversion explicitly. |
| Speech to text | تحويل الكلام إلى نص | Kept distinct from text-to-speech. |
| Chroma key | مفتاح اللون | The established video-editing term for selecting/removing a keyed colour. |
| Maintrack | المسار الرئيسي | The primary chronological footage lane. |
| Crop | اقتصاص | Removing the outer area of a frame. |
| Split | تقسيم | Separating one clip into two clips at the playhead. |
| Reverse | عكس التشغيل | Reverses playback, not the clip's position in the project. |
| Freeze | تجميد الإطار | Holds one video frame as a still. |
| Lasting text | إبقاء النص ظاهرًا | Keeps the title visible across the video rather than translating “lasting” literally. |
| Auto reframe | إعادة تأطير تلقائية | Describes adapting the frame to a new aspect ratio. |
| Stabilize | تثبيت الفيديو | Describes reducing camera shake. |
| Reduce noise | تقليل ضوضاء الخلفية | Communicates the audio cleanup result. |
| Enhance voice | تحسين وضوح الصوت | Describes clearer speech without implying a volume-only change. |
| Fade in / Fade out | تلاشي للظهور / تلاشي للاختفاء | Identifies which end of the transition the control affects. |
| Aspect ratio | نسبة الأبعاد | The standard term for frame width-to-height proportion. |

## RTL and direction rules

- The application-level layout direction follows the selected language.
- Navigation chevrons and directional arrows mirror in RTL.
- Non-directional action icons (undo, redo, scissors, play, audio, and effect symbols) do not mirror.
- The timeline remains LTR in both languages because time progresses from left to right in the existing editing model. Its drawing coordinates, scrubbing, clip order, and drag behavior must not be reversed when the surrounding editor is RTL.
- Text direction and Arabic glyph shaping in the native text renderer are separate from interface direction; both must be checked in preview and exported media.

## Visual identity

| Token | Value |
|---|---|
| Primary cyan | `#81CCEC` |
| Secondary cyan | `#7CC5E2` |
| White | `#FFFFFF` |
| Main background | `#0A0A0A` |
| Surface | `#151515` |

The app label and launcher tint use the CapCraft AR identity. The upstream Kotlin namespace is deliberately unchanged in this first pass; only the Android application ID is set to `com.alaaeldinlabs.capcraftar`. This avoids a broad source-package migration while the localization foundation is being established.

## Known scope limits in this pass

- This is the first localization pass, not a claim that every panel, dialog, accessibility label, or engine error is translated.
- Several specialized panels still contain upstream English copy and will be migrated to the central string catalog in follow-up work.
- The bundled Arabic UI font currently exists in the native text-rendering crate. It has not yet been wired as a dedicated Compose font family.
- Arabic shaping and bidi logic exist in the native renderer, but Arabic preview/export behavior has not been device-tested in this pass.
- No Android build or APK generation was run, as requested. Static repository checks do not replace a build or on-device QA.
