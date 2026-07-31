import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            plugins.withId("com.android.application") {
                extensions.configure(ApplicationExtension::class.java) {
                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_21
                        targetCompatibility = JavaVersion.VERSION_21
                    }
                }
            }

            plugins.withId("com.android.library") {
                extensions.configure(LibraryExtension::class.java) {
                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_21
                        targetCompatibility = JavaVersion.VERSION_21
                    }
                }
            }

            plugins.withId("org.jetbrains.kotlin.android") {
                extensions.configure(KotlinAndroidProjectExtension::class.java) {
                    jvmToolchain(21)
                }
            }
        }
    }
}