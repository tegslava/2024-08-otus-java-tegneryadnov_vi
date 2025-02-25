dependencies {
    implementation("ch.qos.logback:logback-classic")
    implementation ("org.projectlombok:lombok")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    annotationProcessor ("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework:spring-jms:6.1.11")
    implementation("org.springframework.boot:spring-boot-starter-activemq")
    implementation("com.h2database:h2")
}