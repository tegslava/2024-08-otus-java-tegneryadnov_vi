plugins {
    id("java")
}

group = "ru.otus"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation ("org.assertj:assertj-core")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    //options.encoding = "windows-1251"
    options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing"))
}

tasks.test {
    useJUnitPlatform()
}