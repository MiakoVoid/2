plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.greenfinance"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.greenfinance"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true

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
}

dependencies {
    // Android基础库
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Lifecycle组件
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    // 网络请求相关
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

    // 图片加载
    implementation(libs.glide)
    implementation(libs.navigation.runtime.android)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // 测试依赖
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
