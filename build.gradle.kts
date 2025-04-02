plugins {
    id("java")
}

group = "fr.btsciel"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("io.github.java-native:jssc:2.9.4")
    implementation ("org.slf4j:slf4j-api:2.0.5")
    implementation ("org.slf4j:slf4j-simple:2.0.5")
    implementation ("org.xerial:sqlite-jdbc:3.36.0.3")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}