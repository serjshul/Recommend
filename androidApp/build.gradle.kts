plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.serj.recommend.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.serj.recommend.android"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.serj.recommend.android.CustomTestRunner"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

//noinspection UseTomlInstead
dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.appcompat)
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.compose.ui:ui-util:1.5.4")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.compose.material:material")
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
    implementation("io.sanghun:compose-video:1.2.0")
    implementation("androidx.media3:media3-exoplayer:1.2.0")
    implementation("androidx.media3:media3-session:1.2.0")
    implementation("androidx.media3:media3-ui:1.2.0")
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation(libs.androidx.media3.common)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.messaging.ktx)
    debugImplementation(libs.compose.ui.tooling)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-config")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-perf")
    implementation("com.google.firebase:firebase-appcheck-playintegrity")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")

    implementation("com.google.dagger:hilt-android:2.49")
    ksp("com.google.dagger:hilt-compiler:2.49")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0-alpha01")

    // For Robolectric (emulate simulator on JVM on your machine) tests
    testImplementation("com.google.dagger:hilt-android-testing:2.49")
    kspTest("com.google.dagger:hilt-android-compiler:2.49")

    // For Instrumented (UI) tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.49")
    kspAndroidTest("com.google.dagger:hilt-android-compiler:2.49")

    // Setup Preferences DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}
