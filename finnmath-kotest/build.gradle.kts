/*
 * Copyright 2020 Lars Tennstedt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `java-library`
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    jacoco
    id("org.jetbrains.dokka")
    `maven-publish`
    `project-report`
    `build-dashboard`
    id("gradle.site")
    id("com.github.ben-manes.versions")
    idea
}
group = "io.github.ltennstedt.finnmath"
version = "0.0.1-SNAPSHOT"
description = "Kotest matchers for finnmath"
kotlin {
    explicitApi()
}
tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            val kotlinVersion = "1.5"
            apiVersion = kotlinVersion
            languageVersion = kotlinVersion
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjvm-default=all", "-Xemit-jvm-type-annotations")
        }
    }
    test {
        useJUnitPlatform()
    }
    jacocoTestReport {
        reports {
            xml.required.set(true)
            xml.outputLocation.set(file("$buildDir/reports/jacoco/test/report.xml"))
        }
    }
    dokkaHtml {
        dokkaSourceSets {
            configureEach {
                jdkVersion.set(8)
                noJdkLink.set(true)
                noStdlibLink.set(true)
            }
        }
    }
    dokkaJavadoc {
        dokkaSourceSets {
            configureEach {
                jdkVersion.set(8)
                noJdkLink.set(true)
                noStdlibLink.set(true)
            }
        }
    }
    register("javadocJar", Jar::class) {
        from(dokkaJavadoc)
        archiveClassifier.set("javadoc")
    }
    dependencyUpdates {
        revision = "release"
        gradleReleaseChannel = "current"
        outputFormatter = "html"
        rejectVersionIf {
            candidate.version.endsWith("-RC") ||
                candidate.version.endsWith("-M2") ||
                candidate.version.endsWith("-M1")
        }
    }
    register("default") {
        dependsOn(
            build,
            check,
            jacocoTestReport,
            dokkaHtml,
            dokkaJavadoc,
            publishToMavenLocal,
            projectReport,
            buildDashboard,
            generateSiteHtml,
            dependencyUpdates
        )
    }
}
ktlint {
    version.set("0.41.0")
}
detekt {
    config = files("../config/detekt/config.yml")
    buildUponDefaultConfig = true
    reports {
        txt.enabled = false
        xml.enabled = false
    }
}
jacoco {
    toolVersion = "0.8.7"
}
publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
            artifact(tasks["javadocJar"])
            artifact(tasks["kotlinSourcesJar"])
            pom {
                name.set("finnmath-kotest")
                description.set(project.description)
                url.set("https://github.com/ltennstedt/finnmath")
                inceptionYear.set("2017")
                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("ltennstedt")
                        name.set("Lars Tennstedt")
                        roles.addAll("owner", "architect", "developer")
                        timezone.set("Europe/Berlin")
                    }
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/ltennstedt/finnmath/issues")
                }
                ciManagement {
                    system.set("GitHub")
                    url.set("https://github.com/ltennstedt/finnmath")
                }
                scm {
                    connection.set("scm:git:https://github.com/ltennstedt/finnmath.git")
                    tag.set("master")
                    url.set("https://github.com/ltennstedt/finnmath")
                }
            }
        }
    }
}
site {
    websiteUrl.set("https://github.com/ltennstedt/finnmath")
    vcsUrl.set("https://github.com/ltennstedt/finnmath.git")
}
idea.module {
    isDownloadJavadoc = true
    isDownloadSources = true
}
repositories {
    mavenCentral()
}
dependencies {
    api(project(":finnmath"))
    val kotestVersion = "4.6.0"
    api("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-property-jvm:$kotestVersion")
    val junitJupiterVersion = "5.7.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly(kotlin("reflect"))
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    val junitPlatformVersion = "1.7.2"
    testRuntimeOnly("org.junit.platform:junit-platform-engine:$junitPlatformVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-runner:$junitPlatformVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformVersion")
}
