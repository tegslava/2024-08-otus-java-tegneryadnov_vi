dependencies {
    implementation("ch.qos.logback:logback-classic")
    testImplementation ("org.assertj:assertj-core")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}