# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./gradlew build

# Run
./gradlew bootRun

# Test
./gradlew test

# Run a single test class
./gradlew test --tests "com.mytodo.MyTodoApplicationTests"
```

## GitHub

Repository: https://github.com/johannkohler/mytodo (branch: `main`)

After every code change, commit and push with a clean, descriptive message:

```bash
git add <files>
git commit -m "short descriptive message"
git push
```

Also update this `CLAUDE.md` file whenever a change affects architecture, commands, or project structure.

## Architecture

Spring Boot 3.4.4 web application using Java 21 and Gradle.

Dependency versions are centralised in `gradle.properties` — update them there, not in `build.gradle`.

- Entry point: `MyTodoApplication.java` — standard `@SpringBootApplication` bootstrap
- Controllers live in `src/main/java/com/mytodo/`, directly under the root package
- The app runs on port 8080 by default (`application.properties`)
- Database: MariaDB at `jdbc:mariadb://localhost:43306/mytodo` (schema pre-exists, JPA set to `validate`)
- MariaDB driver version is managed via `mariadbDriverVersion` in `gradle.properties`
