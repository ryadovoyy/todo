# todo

This is a client-server application for to-do list management written with Spring Boot for the server and Android for the client.

## Requirements

### Server

Create a Spring Boot application that provides a REST API for task management. Use H2 Database to store data.

Implement the following endpoints:

- `GET /tasks` - get a list of all tasks
- `POST /tasks` - create a new task
- `PUT /tasks/{id}` - update an existing task
- `DELETE /tasks/{id}` - delete a task

Each task must have the following fields:

- id (automatically generated)
- title
- description
- completed (task completion status)

### Client

Create an Android application that communicates with the server via REST API:

- Implement a screen for displaying the list of tasks
- Implement functionality for adding, editing and deleting tasks
- Use Retrofit to work with network requests
- Use `ViewModel` and `LiveData` to manage data on the client

### Build

Organize the project in a single repository. Add the functionality to build the entire project (server and client) via a single command in the Gradle file (e.g. `./gradlew build`).
