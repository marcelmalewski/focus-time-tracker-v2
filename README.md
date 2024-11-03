# Focus time tracker
Official name is in progress :)

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Features](#features)
* [Links](#links)
* [Launch Instructions](#launch-instructions)

## General Info
Desing made in figma is in main directory: FocusTimeTrackerCoreDesign.png

### Inspiration
Both Pomodoro and Flowtime techniques offer unique advantages. Sometimes, one is necessary, and other times, the other is preferred. Flowtime allows for long, uninterrupted work sessions, but too much sitting isn't good. On the other hand, strict adherence to Pomodoro intervals may feel limiting. Combining these methods can yield significant benefits. Additionally, presenting this hybrid approach with maximum simplicity and providing useful statistics is essential.

### Description
...

## Technologies
Angular and Spring
...

## Features
...

## Links
...

## Launch Instructions
### Local Development Environment
1. Create a `.env` file with the following example values:
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
1. Navigate to the ./frontend directory.
2. Install the required dependencies using: yarn install
3. Launch the frontend by executing: `yarn dev`

### Backend

#### Running the Local Backend with IntelliJ
To start the application, execute `application bootRun` from the Gradle panel, using the variables specified in the `.env` file.

#### Running the Backend with images
1. Build the project: `./gradlew build`
2. Build the Docker image: `docker build -t play-together-api .`
3. Tag the image: `docker tag play-together-api 6745345/play-together-api:1.0`
4. Push the image to Docker Hub: `docker push 6745345/play-together-api:1.0`
5. Use the provided docker-compose-dev.yml.
   In case push doesn't reflect changes, remove the local image and rerun the Docker Compose.

