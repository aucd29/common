# common
[![Build Status](https://travis-ci.org/aucd29/common.svg?branch=master)](https://travis-ci.org/aucd29/common)

Add it to your build.gradle with:
```gradle
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

Step 2. Add the dependency

```gradle
dependencies {
    compile 'com.github.aucd29:common:1.0.14'
}
```
```gradle
# kotlin
dependencies {
    implementation 'com.android.support:appcompat-v7:26.1.0'
    implementation 'com.android.support:design:26.1.0'

    implementation('com.github.tony19:logback-android-classic:1.1.1-3') {
        exclude group: 'com.google.android', module: 'android'
    }
    implementation ('com.github.tony19:logback-android-core:1.1.1-3')  {
        exclude group: 'com.google.android', module: 'android'
    }
    implementation 'org.slf4j:slf4j-api:1.7.5'
    implementation 'com.google.android.gms:play-services-ads:8.3.0'

    implementation 'com.github.aucd29:common:2.0.4'
}
