/*
 * Copyright 2021 Lars Tennstedt
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
    kotlin("jvm") version "1.5.10" apply false
    `java-library`
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.17.1" apply false
    jacoco
    id("org.sonarqube") version "3.3" apply false
    id("org.jetbrains.dokka") version "1.4.32" apply false
    `maven-publish`
    `project-report`
    `build-dashboard`
    id("gradle.site") version "0.6" apply false
    id("com.github.ben-manes.versions") version "0.39.0" apply false
    idea
}
