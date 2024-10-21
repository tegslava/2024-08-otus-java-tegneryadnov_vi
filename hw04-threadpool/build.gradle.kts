dependencies {
    implementation("ch.qos.logback:logback-classic")
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