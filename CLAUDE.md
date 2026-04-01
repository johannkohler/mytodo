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
- Controllers live in `src/main/java/com/mytodo/`, directly under the root package (use `@Controller` for views, `@RestController` for APIs)
- The app runs on port 8080 by default (`application.properties`)
- Views use Thymeleaf with the Layout Dialect; templates live in `src/main/resources/templates/`
- `layout.html` is the base template — all pages extend it via `layout:decorate="~{layout}"`  and fill the `layout:fragment="content"` block
- Bootstrap 5 is served as a WebJar via `/webjars/bootstrap/...` (version-agnostic paths via `webjars-locator-lite`)
- Database: MariaDB at `jdbc:mariadb://localhost:43306/mytodo`; JPA `ddl-auto=none`
- Schema migrations are managed by Flyway — add versioned scripts to `src/main/resources/db/migration/` following the `V{n}__{description}.sql` naming convention
- MariaDB driver and Flyway versions are managed via `gradle.properties`

## Internationalisation (i18n)

The app supports English, French, and German. Language is persisted in a cookie (`lang`) via `CookieLocaleResolver` (configured in `WebConfig.java`). Language switching uses the `?lang=xx` query parameter.

- Translation files live in `src/main/resources/` — `messages.properties` (English fallback), `messages_fr.properties`, `messages_de.properties`
- Use `#{key}` syntax in Thymeleaf templates to reference translations
- **When adding any user-visible text to a template, always add the corresponding key to all three messages files**
- The navbar date display uses `Intl.DateTimeFormat` with the server-side locale injected via Thymeleaf inline JS (`th:inline="javascript"`)
- The language selector dropdown in the navbar shows the current language and links to `?lang=xx` for each option
- `layout.html` defines a `layout:fragment="scripts"` block at the bottom of `<body>` — pages can extend it to inject page-specific scripts after Bootstrap JS

## Features

### Weekly view (`/week/{year}/{weekNumber}`)
- `GET /` redirects to the current ISO week URL
- On first visit to a week URL, the Week + 5 Day rows are auto-created in the DB
- Week navigation (prev/next) uses ISO week arithmetic via `java.time.IsoFields`
- A "Today" button appears when not on the current week

### Domain model (all in `com.mytodo`)
- Entities: `Week`, `Day`, `Todo`; enum: `TodoType` (INFORMATION, ACTIVITY, MEETING, PERSONAL)
- Repositories: `WeekRepository`, `DayRepository`, `TodoRepository`
- Services: `WeekService`, `TodoService`
- Controllers: `WeekController` (views), `TodoController` (form POST + AJAX reorder)
- DTO: `ReorderRequest`

### Drag-and-drop reordering
- SortableJS served via WebJar `org.webjars.npm:sortablejs` (version `sortablejsVersion` in `gradle.properties`)
- `POST /todo/reorder` accepts JSON `{ dayId, orderedIds[] }` and rewrites `position` values

### Add todo
- Each day column has an "+ Add" button that opens a shared Bootstrap modal
- Form POSTs to `POST /todo/add` and redirects back to the current week URL
