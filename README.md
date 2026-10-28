## About The Project

Just a custom view called "LoadingView" with using ECS approach. Made for fun.

Two dot-connectable layers for view: background and foreground.
Sample Image:

![Image](https://github.com/siarheisinelnikau/LoadingView/blob/main/files/demo.png)

Sample Video:

![Gif](https://github.com/siarheisinelnikau/LoadingView/blob/main/files/demo.gif)

## Build and compatibility

The project targets Android 17 (API 37), using Android Gradle Plugin 9.4.1
with built-in Kotlin and Gradle 9.6.0. Use JDK 17 or newer (JDK 21 recommended).
Install Android SDK Platform 37.0 and Build Tools 36.0.0, and set `ANDROID_HOME`
or put your SDK path in an untracked `local.properties` file (`sdk.dir=...`).

The sample app requires Android 6.0 (API 23). The library keeps Android 4.1
(API 16) support. Device tests run in the sample app on API 23 or newer.

```sh
./gradlew assembleDebug assembleRelease testDebugUnitTest lintDebug
./gradlew :app:connectedDebugAndroidTest # requires a device or emulator, API 23+
```

`main` is the development base: it already contains the Ashley ECS implementation
from `ecs/ashley`, together with the README and demos.

## License
Distributed under the Apache License. See `LICENSE` file for more information.

## Contact Me

[GitHub](https://github.com/siarheisinelnikau/LoadingView)

[LinkedIn](https://www.linkedin.com/in/siarhei-sinelnikau-04a33570)
