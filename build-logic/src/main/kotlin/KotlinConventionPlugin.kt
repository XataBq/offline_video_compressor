import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class KotlinConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            plugins.withId("org.jetbrains.kotlin.jvm") {
                extensions.configure(KotlinJvmProjectExtension::class.java) {
                    jvmToolchain(21)
                }
            }
        }
    }
}