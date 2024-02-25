plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}


@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    kotlin.applyDefaultHierarchyTemplate()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    //noinspection UseTomlInstead
    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("androidx.datastore:datastore-preferences-core:1.0.0")
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidInstrumentedTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
//                implementation()
            }
        }
    }
}

android {
    namespace = "com.serj.recommend"
    compileSdk = 34
    defaultConfig {
        minSdk = 25
    }
}
