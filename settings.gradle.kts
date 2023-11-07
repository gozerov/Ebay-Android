pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Ebay Android"
include(":app")
include(":presentation")
include(":data")
include(":domain")
include(":feature-profile")
include(":feature-profile:presentation")
include(":feature-profile:data")
include(":feature-profile:domain")
