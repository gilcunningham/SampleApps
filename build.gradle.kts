// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.0" apply false
    //id 'com.android.application' version '7.4.0' apply false
    //id("com.android.application")
    id("com.android.library") version "7.4.0" apply false
    //id("com.android.library")
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    //id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.jvm") version "1.7.0" apply false
    //id 'kotlin-android'
    //id("kotlin-android")
}

//val compose_version = "1.4.0"
/**
buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.5")
    }
}

apply plugin: 'com.jfrog.bintray'
**/