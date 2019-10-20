enableFeaturePreview("GRADLE_METADATA")

if (System.getenv("JITPACK") != "true") {
    include(":app")
    include(":shared")
}
include(":summer")
//include(":summer-kodein")
include(":summer-androidx")