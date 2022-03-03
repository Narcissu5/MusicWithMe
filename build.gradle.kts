plugins {
    id("org.openjfx.javafxplugin") version "0.0.12"
    id("org.beryx.jlink") version "2.24.4"
}

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/central") }
    mavenCentral()
}

application {
    mainModule.set("musicwithme")
    mainClass.set("xyz.narcissu5.music.Main")
}

javafx {
    version = "17"
    modules("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.jaudiotagger:jaudiotagger:2.0.1")
}

