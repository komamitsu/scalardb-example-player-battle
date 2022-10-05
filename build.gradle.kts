plugins {
    id("java")
    id("application")
}

group = "org.komamitsu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.scalar-labs:scalardb:3.7.0")
    implementation("info.picocli:picocli:4.6.3")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

application {
    mainClass.set("org.komamitsu.playerbattle.Main")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
