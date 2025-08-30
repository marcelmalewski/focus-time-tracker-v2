# Focus time tracker
Official name is in progress :)

## Table of contents
* [General info](#general-info)
* [Local Development](#local-development)

## General Info
Design made in figma is in main directory: FocusTimeTrackerCoreDesign.png

### Inspiration
Both Pomodoro and Flowtime techniques offer unique advantages. Sometimes, one is necessary, and other times, the other is preferred. Flowtime allows for long, uninterrupted work sessions, but too much sitting isn't good. On the other hand, strict adherence to Pomodoro intervals may feel limiting. Combining these methods can yield significant benefits. Additionally, presenting this hybrid approach with maximum simplicity and providing useful statistics is essential.

## Local Development
### Environment
1. Create a `./local-dev/.env` file with the following example values:
    ```
    POSTGRES_USER=postgres
    POSTGRES_PASSWORD=postgres
    POSTGRES_DB=focus-time-tracker
    POSTGRES_PORT=5432
    PORT=5000
    SPRING_PROFILES_ACTIVE=dev
    ```
2. Initiate the services using `./local-dev/docker-compose-dev.yml` along with the `.env` file.

You can either set up a run configuration in IntelliJ or start it manually.

Example of manual start command: `docker-compose -f ./local-dev/docker-compose-dev.yml --env-file .env up`

The Docker setup will launch:
1. PostgreSQL database
2. Development Proxy

### Frontend
#### Before coding frontend
1. Set prettier. In Intellij you can just set "Automatic Prettier configuration".

#### Running the Local Frontend
1. npm install
2. Use intellij config or command: `ng start`

### Backend

#### Running the Local Backend with IntelliJ
To start the application, execute `application bootRun` from the Gradle panel, using the variables specified in the `.env` file.
