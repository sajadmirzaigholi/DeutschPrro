# Contributing to DeutschPro

Thanks for your interest in improving DeutschPro! This project is structured
so that adding course content and adding code are mostly separate tasks.

## Adding course content (no architecture changes needed)

The fastest way to contribute is filling in real lessons for A2/B1/B2, which
today only have unit *shells*. Use
`app/src/main/java/com/deutschpro/app/data/content/A1Unit1Content.kt` as the
template:

1. Create a new `XxUnitYContent.kt` object under `data/content/`.
2. Define a `UnitEntity`, a list of `LessonEntity`s, `VocabularyEntity`s,
   optional `GrammarTopicEntity`s, and `QuizQuestionEntity`s (5 per lesson is
   the existing convention).
3. Register your unit + lessons + vocab + grammar + quiz lists in
   `ContentSeeder.kt`.
4. Remove the corresponding placeholder unit from `UpperLevelsPlaceholder.kt`.

No ViewModel, DAO, or UI code needs to change — the whole app is
content-driven.

## Adding code / features

1. Fork the repo and create a feature branch: `git checkout -b feature/my-feature`.
2. Keep new dependencies to a minimum; this project intentionally avoids a DI
   framework (see `util/ViewModelFactory.kt`) and heavy third-party libraries.
3. Follow the existing package structure: `data/model` (enums/DTOs),
   `data/local` (Room), `data/repository` (business logic), `ui/screens/*`
   (one folder per screen, `XScreen.kt` + `XViewModel.kt`).
4. Run `./gradlew lint` and `./gradlew assembleDebug` locally before opening
   a PR — CI runs the same checks.
5. Open a pull request describing what changed and why.

## Reporting issues

Please include: Android version, device/emulator, steps to reproduce, and
(if relevant) which level/lesson you were on.

## Code of conduct

Be respectful and constructive. This is a learning tool used by real
language learners — clarity and correctness of German/Persian content matter
more than cleverness.
