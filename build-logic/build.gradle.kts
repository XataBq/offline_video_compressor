plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly("com.android.tools.build:gradle:9.0.1")
    compileOnly("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:2.2.21")
}

gradlePlugin {
    plugins {
        register("androidConvention") {
            id = "my.android.convention"
            implementationClass = "AndroidConventionPlugin"
        }

        register("kotlinConvention") {
            id = "my.kotlin.convention"
            implementationClass = "KotlinConventionPlugin"
        }
    }
}