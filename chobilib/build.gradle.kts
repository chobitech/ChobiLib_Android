plugins {
    alias(libs.plugins.android.library)
    //alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.chobitech.lib.android"

    compileSdk = 37

    defaultConfig {
        minSdk = 29
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }


    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

}



afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.chobitech"
                artifactId = "chobilib"
                version = "0.1.2"
            }
        }
    }
}


dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.foundation)
}