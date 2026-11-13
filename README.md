TASK MANAGER API
A RESTful backend for managing projects and their tasks, built with Java 17 and Spring Boot, with HTML/CSS/JavaScript frontend.

FEATURES
- CRUD for projects and tasks (one project has many tasks)
- Task priority (LOW / MEDIUM / HIGH) and status (TODO / DOING / DONE)
- Filter tasks by status
- Advance a task to its next status (TODO -> DOING -> DONE -> TODO)
- Input validation and consistent JSON error responses (400 / 404)
- Plain HTML/CSS/JS page that calls the API with fetch: list and add projects and tasks, change task status, delete tasks

TECH STACK
Java 17, Spring Boot, Spring Data JPA, Bean Validation, H2 (in-memory), JUnit 5, Mockito, Maven, Git

ARCHITECTURE
Browser (HTML/JS) -> Controller -> Service -> Repository -> H2 database

GETTING STARTED
Requirements: JDK 17 or newer.
Run the app:
  .\mvnw.cmd spring-boot:run    
Then open http://localhost:8080

API ENDPOINTS
POST    /api/projects                                 Create a project
GET     /api/projects                                 List projects
GET     /api/projects/{id}                            Get one project
PUT     /api/projects/{id}                            Update a project
DELETE  /api/projects/{id}                            Delete a project
POST    /api/projects/{projectId}/tasks               Create a task
GET     /api/projects/{projectId}/tasks?status=TODO   List tasks (optional status filter)
GET     /api/tasks/{id}                               Get one task
PUT     /api/tasks/{id}                               Update a task
POST    /api/tasks/{id}/advance                       Move a task to its next status
DELETE  /api/tasks/{id}                               Delete a task

DESIGN DECISIONS
- Layered architecture (controller, service, repository) to separate concerns.
- Constructor injection for dependencies, which makes classes easy to test.
- Centralized exception handling with @RestControllerAdvice for consistent errors.
- Status transition rule lives in the Status enum (Status.next()), so there is a single source of truth.
- Service tests use Mockito mocks, so they run without a database.

LIMITATIONS AND FUTURE WORK
- H2 is in-memory, so data is lost on restart --> PostgreSQL is the next step.
- No authentication, pagination, or integration tests yet.
