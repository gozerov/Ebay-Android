plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    //Dagger2
    implementation("com.google.dagger:dagger:2.46")
    kapt("com.google.dagger:dagger-compiler:2.46")

}