plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.dicoding.malanginsider"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.dicoding.malanginsider"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        mlModelBinding = true
    }
}

dependencies {
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.compiler) // Glide annotation processor
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    implementation(libs.firebase.auth)
    implementation(libs.androidx.cardview)

    implementation (libs.tensorflow.tensorflow.lite)
    implementation (libs.tensorflow.lite.task.vision)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.play.services.location)
    implementation(libs.androidx.tools.core)
    implementation(libs.tensorflow.lite.support)
    implementation(libs.tensorflow.lite.metadata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation ("androidx.viewpager2:viewpager2:1.0.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.activity:activity-ktx:1.6.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("com.squareup.picasso:picasso:2.71828")
    kapt("androidx.room:room-compiler:2.5.1")
    implementation ("androidx.room:room-runtime:2.5.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation ("androidx.room:room-runtime:2.5.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    implementation ("com.opencsv:opencsv:5.7.1")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("com.google.code.gson:gson:2.8.8")

    // Room dependencies
    implementation ("androidx.room:room-runtime:2.5.2")

    // RxJava2 support for Room
    implementation ("androidx.room:room-rxjava2:2.5.2")

    // RxJava dependencies (jika belum ada)
    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")

    annotationProcessor ("com.github.bumptech.glide:compiler:4.13.0")
    implementation ("com.google.android.material:material:1.9.0")

}