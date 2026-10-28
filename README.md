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

The library uses Ashley 1.7.4. The obsolete `com.loading.view.core` implementation
has been removed; the active ECS implementation is in `com.loading.view.space`.
Regression tests cover motion, layer connections, frame timing, XML dimensions,
clicks and view lifecycle. Android-dependent JVM tests use Robolectric 4.17 on
API 23 and API 37; these do not replace testing on a physical device.
The GitHub Actions workflow runs builds, JVM tests and lint on pushes and pull requests.

## License
Distributed under the Apache License. See `LICENSE` file for more information.

## Contact Me

[GitHub](https://github.com/siarheisinelnikau/LoadingView)

[LinkedIn](https://www.linkedin.com/in/siarhei-sinelnikau-04a33570)
