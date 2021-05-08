import org.jetbrains.intellij.tasks.*

plugins {
    kotlin("jvm")
    id("org.jetbrains.intellij")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":common"))
}

intellij {
    version = "2020.3.4"
    setPlugins("java", "org.jetbrains.kotlin:203-1.4.32-release-IJ7148.5")

    downloadSources = true
}

tasks {
    getByName<PatchPluginXmlTask>("patchPluginXml") {
        changeNotes(
            """
            Initial project, migrating
        """.trimIndent()
        )

        sinceBuild("203")
        untilBuild("")
    }

    withType<PublishTask> {
        val publishUsername: String by rootProject.extra
        val publishPassword: String by rootProject.extra

        username(publishUsername)
        password(publishPassword)
    }

    compileJava {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
