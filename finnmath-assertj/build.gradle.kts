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

plugins {
    `java-library`
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    jacoco
    `maven-publish`
    `project-report`
    `build-dashboard`
    id("gradle.site") version "0.6"
    id("com.github.ben-manes.versions") version "0.39.0"
    idea
}
group = "io.github.ltennstedt.finnmath"
version = "0.0.1-SNAPSHOT"
description = "AssertJ assertions for finnmath"
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    withJavadocJar()
    withSourcesJar()
}
tasks {
    test {
        useJUnitPlatform()
    }
    jacocoTestReport {
        reports {
            xml.isEnabled = true
            xml.destination = file("$buildDir/reports/jacoco/test/report.xml")
        }
    }
    dependencyUpdates {
        revision = "release"
        gradleReleaseChannel = "current"
        outputFormatter = "html"
        rejectVersionIf {
            candidate.version.endsWith("-RC") || candidate.version.endsWith("-M2") || candidate.version.endsWith("-M1")
        }
    }
    register("default") {
        dependsOn(
            build,
            check,
            jacocoTestReport,
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
jacoco {
    toolVersion = "0.8.7"
}
publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
            pom {
                name.set("finnmath-assertj")
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
    api("org.assertj:assertj-core:3.19.0")
    val junitJupiterVersion = "5.7.2"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    val junitPlatformVersion = "1.7.2"
    testRuntimeOnly("org.junit.platform:junit-platform-engine:$junitPlatformVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-runner:$junitPlatformVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitPlatformVersion")
}
