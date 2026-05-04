# MathQuest

A cozy, hand-drawn educational adventure game for elementary students (Grades 3 to 5), built with Kotlin and Jetpack Compose for Android.

Travel inside a magic book to the kingdom of **Wumbo** and defeat three monsters by solving math problems. The game covers multiplication, division, and fractions across three story chapters.


<img width="912" height="408" alt="Screenshot 2026-05-04 215533" src="https://github.com/user-attachments/assets/d1a1205d-bbad-4ec9-835c-ef2cd1113ca8" />


---

## Features

* Landscape, full-screen, immersive mode (no status or navigation bars)
* Original hand-drawn artwork: paper-note panels, watercolor backgrounds, crayon-style buttons
* 9 screens with smooth transitions: Main Menu, Login, Story Intro, Level, Settings, Pause, plus the chapter/lesson/challenge flow
* Persistent save: player name, current level, audio settings, and chapter progress are stored across launches
* Interactive volume sliders for Music and SFX in both Settings and Pause overlays
* Pause overlay blurs the level beneath it for a polished feel
* Animated entry sequences (book opening, story fade-in, monster spawn) with bobbing characters and gentle parallax

---

## Screens

| Screen | What happens |
| --- | --- |
| Main Menu | Entry point. Start Adventure, Continue (locked when no save exists), View Progress, Settings, Profile. |
| Login | Hand-drawn paper "HELLO!" panel. Tap inside the Enter Name box, type your hero name, then tap Start Adventure. |
| Intro Story | Cinematic fade to parchment. The story of Wumbo unfolds with your name highlighted in purple. |
| Level: Earth | First chapter. The Multiplication monster appears in a forest scene. |
| Settings Overlay | Music and SFX sliders on a paper note. Closes with the home icon. |
| Pause Overlay | Blurs the level. Music + SFX sliders, plus Home, Resume, and Restart buttons. |

---

## Built With

* **Kotlin 2.0.21**
* **Jetpack Compose** (BOM 2024.09.03)
* **Compose Navigation** for screen routing
* **AndroidViewModel + StateFlow** for game state
* **SharedPreferences** for persistent saves
* **Google Fonts** (Baloo 2 + Nunito) via Compose Text Google Fonts
* **Android Gradle Plugin 8.6.1**, target SDK 35, min SDK 24

---

## Getting Started

### Prerequisites

1. **Android Studio Ladybug** (or newer)
2. **JDK 11** or higher (Android Studio bundles its own JDK)
3. **Android SDK 35** (installed via Android Studio's SDK Manager)
4. An Android device or emulator running **API 24 (Android 7.0)** or higher

### Clone and run

```bash
git clone https://github.com/VelzaNava/MathQuest.git
cd MathQuest
```

Then:

1. Open Android Studio
2. Choose **File > Open** and select the `MathQuest` folder
3. Wait for Gradle sync to finish (downloads dependencies on first run)
4. Plug in a real device with USB debugging enabled, or start an emulator (recommend a Medium Phone configured to landscape)
5. Press **Run** (Shift+F10) or click the green play button

### Build a debug APK manually

```bash
./gradlew assembleDebug
```

The APK will land in `app/build/outputs/apk/debug/app-debug.apk`. Install it with `adb`:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## How to Play

1. Launch the app. It opens in **landscape**, so rotate your phone or emulator if needed.
2. From the Main Menu, tap **Start Adventure**.
3. On the Login panel, tap inside the Enter Name box, type your hero name, then tap **Start Adventure** again.
4. Read the story intro. When the Continue button appears, tap it to enter the first level.
5. The Multiplication monster (Multiplico) appears. Tap **Pause** in the top-right at any time to pause, adjust audio, restart, or return home.
6. Adjust music and SFX volumes from the Settings panel on the Main Menu, or from the Pause overlay.

Saved progress (your name, current level, completed chapters, and volume settings) is restored automatically the next time you open the app. Use **Continue** on the Main Menu to jump straight back in.

---

## Project Structure

```
MathQuest/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/mathquest/app/
│       │   ├── MainActivity.kt
│       │   ├── GameViewModel.kt
│       │   ├── SaveManager.kt
│       │   ├── data/GameData.kt
│       │   ├── navigation/AppNavigation.kt
│       │   ├── ui/
│       │   │   ├── theme/   (Color, Type, Theme)
│       │   │   ├── components/
│       │   │   └── screens/ (MainMenu, Login, IntroStory, LevelEarth, etc.)
│       └── res/
│           ├── drawable/ (PNG assets, vector icons)
│           └── values/   (strings, themes, font certs)
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/libs.versions.toml
```

---

```bash
adb exec-out screencap -p > docs/screenshots/01-main-menu.png
```

Once the files are in place, push them with `git add docs/ && git commit -m "Add README screenshots" && git push`.

---

## Roadmap

* Wire up the remaining chapters (Castle of Splitting for Division, Deep Ocean Rift for Fractions)
* Add the Lesson and Challenge screens to the asset-driven flow
* Background music and SFX wired through the volume sliders
* Sound effects on button taps, correct answers, and monster defeats
* Particle effects on chapter completion
* Achievements and a stats screen

---

## Credits

* Concept, design, art direction, and development: **Velza Nava**
* Hand-drawn assets: original artwork created for this project
* Fonts: Baloo 2 and Nunito by Google Fonts
* Built with Kotlin and Jetpack Compose

---

## License

This project is currently for educational and personal use. Contact the author before redistributing the code or assets.
