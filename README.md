## About The Project

LoadingView is an animated Android custom view built with Ashley ECS.

It displays two particle layers, background and foreground, over a gradient.
Nearby particles within each layer are connected by lines.

Sample Image:

![Image](https://github.com/siarheisinelnikau/LoadingView/blob/main/files/demo.png)

Sample Video:

![Gif](https://github.com/siarheisinelnikau/LoadingView/blob/main/files/demo.gif)

## Build and compatibility

The project targets Android 17 (API 37), using Android Gradle Plugin 9.4.1
with built-in Kotlin and Gradle 9.6.0. Use JDK 17 or newer (JDK 21 recommended).
Install Android SDK Platform 37.0 and Build Tools 36.0.0, and set `ANDROID_HOME`
or put your SDK path in an untracked `local.properties` file (`sdk.dir=...`).

The sample app requires Android 6.0 (API 23). The library supports Android 4.1
(API 16). Device tests run in the sample app on API 23 or newer.

```sh
./gradlew assembleDebug assembleRelease testDebugUnitTest lintDebug
./gradlew :app:connectedDebugAndroidTest # requires a device or emulator, API 23+
```

## Configuration and animation

Add `com.loading.view.LoadingView` to your layout. Tapping or activating it with
an accessibility click changes the particle directions. Animation pauses while
the view is disabled, hidden, detached, or its window is not visible, and resumes
without a time jump. Particle positions are regenerated after resizing.

Both layers have independent proximity, density, radius, speed and line alpha
attributes (see `modules/LoadingView/src/main/res/values/styleables.xml`). Dimensions
are specified in `dp`, speed values represent `dp` per second, density is the area
in square `dp` per particle, and alpha ranges from 0 to 255. Densities must be
positive; minimum sizes and speeds must not exceed their maximum values.

## Tests

Tests cover motion, layer connections, frame timing, XML dimensions,
clicks and view lifecycle. Android-dependent JVM tests use Robolectric 4.17 on
API 23 and API 37.
The GitHub Actions workflow runs builds, JVM tests and lint on pushes and pull requests.

## License
Distributed under the Apache License. See `LICENSE` file for more information.

## Contact Me

[GitHub](https://github.com/siarheisinelnikau/LoadingView)

[LinkedIn](https://www.linkedin.com/in/siarhei-sinelnikau-04a33570)
