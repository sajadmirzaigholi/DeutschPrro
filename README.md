# DeutschPro 🇩🇪

[![Android CI](https://github.com/OWNER/deutschpro/actions/workflows/android-ci.yml/badge.svg)](https://github.com/OWNER/deutschpro/actions/workflows/android-ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.24-7F52FF?logo=kotlin&logoColor=white)
![Min SDK](https://img.shields.io/badge/minSdk-24-brightgreen)
![Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)

> Replace `OWNER` in the badge URL above with your GitHub username/org once
> you push this repo, so the CI badge links to your own Actions runs.

یک اپلیکیشن اندرویدی حرفه‌ای برای آموزش زبان آلمانی (A1 تا B2) ویژه فارسی‌زبانان.
A native Android app that teaches German (A1–B2) to Persian speakers, built with
Kotlin + Jetpack Compose + Room, fully offline-first.

---

## 1. What's in this project

This is a real, buildable Android Studio project — not a mockup. It implements
the full architecture requested (learning path, gamification, vocabulary,
grammar, four skills, quizzes, offline storage, dark/light theme, RTL) with
**two fully-authored A1 units** (9 lessons, ~40 vocabulary words, 2 grammar
topics, 40 quiz questions) as a complete, working reference example, plus the
unit/level *structure* for A2, B1 and B2 already wired into the learning path
so the whole A1→B2 journey renders end-to-end today.

Adding more content (the bulk of remaining work for a production launch) is a
data-entry task, not an architecture task — see §6.

## 2. Tech stack

| Concern | Choice |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigation | Navigation-Compose (single-Activity) |
| Offline storage | Room (SQLite) for course content & progress |
| Lightweight state | Jetpack DataStore (XP, streak, theme, daily goal) |
| Async | Kotlin Coroutines + Flow / StateFlow |
| DI | Manual constructor injection via a small `DeutschProApplication` container (no framework — trivial to swap for Hilt later) |
| Min/Target SDK | 24 / 34 |

## 3. Project structure

```
app/src/main/java/com/deutschpro/app/
├── data/
│   ├── model/        # Enums shared across layers: LevelCode, SkillType, LessonState...
│   ├── local/         # Room entities, DAOs, AppDatabase, TypeConverters
│   ├── repository/    # CourseRepository, VocabularyRepository, GrammarRepository,
│   │                   QuizRepository, AchievementRepository (all offline-first)
│   └── content/       # Seed data: A1Unit1Content, A1Unit2Content, AchievementCatalog,
│                        UpperLevelsPlaceholder, ContentSeeder
├── ui/
│   ├── theme/         # Color.kt, Type.kt, Shape.kt, Theme.kt (light/dark, RTL-forced)
│   ├── navigation/     # Screen.kt (routes), DeutschProNavGraph.kt
│   ├── components/     # Reusable widgets: LessonNode, XpBadge, StreakBadge,
│   │                    AchievementCard, DailyGoalRing, DeutschProBottomBar
│   └── screens/
│       ├── learningpath/  # Duolingo-style visual map, level selector, daily goal ring
│       ├── lesson/         # 6-tab lesson screen: Vocabulary/Grammar/Lesen/Hören/Schreiben/Sprechen
│       ├── quiz/           # Multiple-choice / fill-in-blank quiz engine + star rating
│       ├── vocabulary/     # Search, level+favorite filters, and Flashcard SRS mode
│       ├── grammar/        # Grammar topic list + full explanation detail screen
│       └── profile/        # XP/level, streak, daily goal slider, theme switch, achievements grid
└── util/               # PreferencesManager (DataStore), XpEngine, ViewModelFactory
```

## 4. Features implemented

- **Learning path** — Duolingo-style node map per level (A1/A2/B1/B2), lock/unlock
  logic (a lesson unlocks once the previous one is completed), stars per lesson,
  horizontal scroll per unit.
- **Gamification** — XP per lesson/quiz/flashcard, daily goal ring, streak
  counter with automatic break-detection, a badge/achievement catalog
  (streak, XP, vocabulary, lessons, quiz, level-completion categories) that
  unlocks automatically as stats change.
- **Vocabulary system** — level filter, live search (German + Persian), topic
  categories, favorites (star), and a Leitner-style spaced-repetition
  flashcard mode (box 0→5, review intervals from 10 minutes to 30 days).
- **Grammar system** — Persian-language explanations, German examples with
  Persian translations, an "exceptions/notes" section, organized per level.
- **Four skills per lesson** — Lesen (reading text + translation), Hören
  (listening script + prompt — see §6 for audio), Schreiben (writing prompt +
  example answer), Sprechen (speaking prompt + example sentence).
- **Quizzes** — multiple choice / fill-in-blank, instant feedback with
  explanation, mistake logging (`MistakeEntity`) for a future "review my
  mistakes" screen, 1–3 star scoring, XP bonus for a perfect score.
- **Offline-first** — Room seeds itself on first launch (`ContentSeeder`) and
  everything (content + all progress) lives on-device; no network calls
  anywhere in the app.
- **Theming** — Material 3 light/dark themes with an original slate-blue +
  amber palette, forced RTL layout direction, a settings toggle
  (system/light/dark) persisted via DataStore.

## 5. Getting started

```bash
git clone https://github.com/OWNER/deutschpro.git
cd deutschpro
```

1. Open the project root in **Android Studio (Koala or newer)**.
2. Let Gradle sync. **This repo does not commit the Gradle wrapper jar**
   (it's a binary file, deliberately kept out of version control /
   generated in a sandboxed offline environment) — Android Studio will
   offer to generate it automatically on first sync. If it doesn't prompt:
   go to **File → Sync Project with Gradle Files**, or run
   `gradle wrapper --gradle-version 8.7` once from a machine with Gradle
   installed, then commit the resulting `gradlew`, `gradlew.bat`, and
   `gradle/wrapper/gradle-wrapper.jar` so future clones (and CI) don't need
   to regenerate it.
3. Run on an emulator or device (minSdk 24 / Android 7.0+).

No API keys, backend, or network access are required — the app is fully
functional offline from first launch.

**CI**: `.github/workflows/android-ci.yml` builds and lints the app on every
push/PR to `main`. It provisions Gradle itself (via `gradle/actions/setup-gradle`)
and generates the wrapper as its first step, so CI works even before you've
committed a wrapper jar locally.

### Persian font (recommended, optional)
The typography system (`ui/theme/Type.kt`) is pre-wired for **Vazirmatn**, a
free OFL-licensed variable font with excellent Persian+Latin coverage (Latin
matters here since German words sit inline constantly). The app builds and
looks correct with the system font today; see the comment block at the top
of `Type.kt` for the 3-step swap once you download the `.ttf` files from
https://github.com/rastikerdar/vazirmatn.

## 6. Known gaps / next steps for a production launch

Being upfront about scope: a commercial-grade course with full A1–B2 content
represents months of curriculum-writing work that can't be fabricated
responsibly in one pass. What's stubbed vs. real:

- **Content coverage** — A1 Unit 1 & 2 are fully authored (lessons, vocab,
  grammar, quizzes). A2/B1/B2 currently ship as unit *shells* (title +
  description, one "coming soon" lesson) so the learning-path UI already
  shows the whole journey. Follow the pattern in `A1Unit1Content.kt` /
  `A1Unit2Content.kt` to add real content — it's pure data, no architecture
  changes needed.
- **Audio** — Hören lessons store a `listeningScript` string today (what
  *would* be read aloud) rather than a real audio file, since recording/TTS
  assets can't be generated here. Two ready-made options: (a) drop `.mp3`
  files into `res/raw/` and play them with `MediaPlayer`/`ExoPlayer` keyed by
  lesson id, or (b) use Android's built-in `TextToSpeech` engine with a
  German voice to read `listeningScript` aloud with zero extra assets.
- **Speaking practice** — currently shows a prompt + example sentence for the
  learner to read aloud themselves. `SpeechRecognizer`
  (`RecognizerIntent.ACTION_RECOGNIZE_SPEECH`) could be added to score
  pronunciation attempts against the expected sentence.
- **Level tests** — `QuizRepository.getLevelTestQuestions()` already exists
  (randomized pull across a level's lessons) but has no dedicated screen yet;
  wire it to a new `Screen.LevelTest` route reusing `QuizScreen`'s UI.
  "Mistake review" has the same story: `MistakeEntity` is logged on every
  wrong answer, just needs a list screen that replays them.
- **Accounts / cloud sync** — the repository layer is the seam for this: swap
  Room for a Room+remote-mediator (or add a sync worker) without touching any
  ViewModel or screen, since screens only ever talk to repositories.
- **Testing** — no unit/instrumented tests included yet; `XpEngine`,
  `CourseRepository`'s unlock logic, and the DAOs are the highest-value
  targets to start with since they're pure/deterministic.

## 7. Design notes

- The color palette is intentionally **not** a green Duolingo clone — a calm
  slate-blue paired with a warm amber accent, so the visual identity is
  original while the interaction pattern (path map, streak, XP) stays
  familiar to language-app users.
- Manual DI was chosen over Hilt to keep the codebase approachable without
  extra annotation-processing setup; every dependency is still constructor
  injected, so migrating to Hilt later is mechanical (swap `ViewModelFactory`
  lambdas for `@HiltViewModel`).
- All Room entities double as light "domain models" (no separate mapper
  layer) since the schema is still small; if it grows substantially, add a
  `data/model` mapping layer between `local` entities and UI state.

## 8. Contributing

Contributions are welcome — see [CONTRIBUTING.md](CONTRIBUTING.md).
Filling in real A2/B1/B2 lesson content (following the A1 template) is the
highest-impact, lowest-risk way to help.

## 9. License

Released under the [MIT License](LICENSE).

