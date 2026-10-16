# GallowsAndroidAppliccation

Игра «Виселица» для Android на Java. Один игрок вводит слово, передаёт устройство другому, после чего второй игрок угадывает его. В приложении есть отдельный экран правил.

## Сборка и запуск

Откройте корень проекта в Android Studio, установите Android SDK 34 и выполните синхронизацию Gradle. Используйте JDK, совместимый с версией Android Gradle Plugin проекта; настройки Java указаны в `app/build.gradle.kts`. Минимальная версия устройства — Android API 24.

Для сборки отладочного APK:

```sh
sh gradlew :app:assembleDebug
```

В Windows: `gradlew.bat :app:assembleDebug`. APK появится в `app/build/outputs/apk/debug/`. Для запуска выберите модуль `app` и устройство или эмулятор в Android Studio.

## Устройство проекта

Логика угадывания находится в `app/src/main/java/com/example/artemapp/core/WordGuesser.java`, экраны — в `gui/`, XML-разметка — в `app/src/main/res/layout/`.
