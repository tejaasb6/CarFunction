plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.spotless)
    id("maven-publish")
}
publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.audi"
            artifactId = "common-core-ui"
            version = "1.0.1"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}
kotlin {
    jvmToolchain(17)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    buildUponDefaultConfig = true
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint("1.3.1")
    }
}

android {
    namespace  = "com.ui.core"
    compileSdk = 36

    defaultConfig { minSdk = 26 }

    buildFeatures { compose = true }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    publishing {
        singleVariant("release") {}
    }
}

dependencies {
    api(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.kotlinx.coroutines.android)
}
