val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val exposed_version: String by project
val h2_version: String by project
plugins {
  kotlin("jvm") version "1.9.21"
  id("io.ktor.plugin") version "2.3.6"
  id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
}

group = "com.example"
version = "0.0.1"

application {
  mainClass.set("io.ktor.server.netty.EngineMain")
  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
  mavenCentral()
}

dependencies {
  
  // Ktor
  implementation("io.ktor:ktor-server-core-jvm")
  implementation("io.ktor:ktor-server-auth-jvm")
  implementation("io.ktor:ktor-server-auth-jwt-jvm")
  implementation("io.ktor:ktor-server-host-common-jvm")
  implementation("io.ktor:ktor-server-netty-jvm")
  implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
  
  
  // Routes
  implementation("io.ktor:ktor-server-resources")
  implementation("io.ktor:ktor-server-status-pages-jvm")
  implementation("io.ktor:ktor-server-swagger-jvm")
  implementation("io.ktor:ktor-server-websockets-jvm")
  implementation("io.ktor:ktor-server-request-validation:$ktor_version")
  
  // Logging
  implementation("io.ktor:ktor-server-call-logging-jvm")
  implementation("ch.qos.logback:logback-classic:$logback_version")
  
  // Serialization
  implementation("io.ktor:ktor-server-content-negotiation-jvm")
  implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
  
  // Database
  implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
  implementation("org.mariadb.jdbc:mariadb-java-client:3.2.0")
  
  // Koin
  implementation("io.insert-koin:koin-ktor:3.5.0")
  implementation("io.insert-koin:koin-ksp-compiler:1.3.0")
  testImplementation("io.ktor:ktor-server-test-host-jvm:2.3.6")
  runtimeOnly("io.insert-koin:koin-annotations:1.3.0")
  
  // Security
  implementation("at.favre.lib:bcrypt:0.10.2")
  
  
  // Test
  testImplementation("io.ktor:ktor-server-tests-jvm")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
