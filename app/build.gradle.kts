plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

apply(from = "types.gradle.kts")

val NULL: String by project.extra

android {
    namespace = "com.example.paymentapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.paymentapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            project.extra["TERMINALS_TYPE"] as String,
            project.extra["TERMINALS_FIELD"] as String,
            NULL
        )
    }

    flavorDimensions.add("gateway")

    // Apply flavor settings defined in src/<flavor_name>/flavor.gradle
    productFlavors {
        project.files("src").forEach {
            if (it.isDirectory) {
                val files = it.listFiles()?.filter { file ->
                    file.isDirectory
                }

                files?.forEach { flavorDirectory ->
                    flavorDirectory.listFiles().forEach { flavorFile ->
                        if (flavorFile.name == "flavor.gradle") {
                            apply(from = flavorFile)
                        }
                    }
                }
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.materialWindow)

    // Request runtime permissions in Compose
    implementation(libs.google.accompanist.permissions)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}