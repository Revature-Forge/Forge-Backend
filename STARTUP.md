# Getting Started
## Cloning the repo
- Create a new folder
- Within the new folder
    - Clone Forge-Backend
    - Clone Porfolio-Frontend

<b>Have these projects within the same directory for the docker-compose to work.</b>

## Development
- `mvn clean install` to install the necessary dependencies for the project.
- `mvn spring-boot:run` to run the Spring Boot application.
    - Access the REST API via `http://localhost:8081/api`
    - In development an h2 database is used.
- To run the frontend see `Portfolio-Frontend/STARTUP.md#Development`

## Deployment
- Setup your `docker-compose.yml` located in `Forge-Backend/devops/bundle/docker-compose.yml`
    - Change each of the fields contained within {curly braces} with your PostgreSQL credentials.
        - `{IP OF DB}`
        - `{DB USERNAME}`
        - `{DB PASSWORD}`
- Run `docker-compose up -d`
    - This will build the image using the `Dockerfile` in the same location then begin running the container connecting to the specified database.
    - The docker image will have the compiled Spring Boot application and host the static React site.
        - <b>If both projects are not in the same directory the image will not compile</b>
    - The REST API will connect to the database specified within the `docker-compose.yml`
- Access the Front End via `http://{IP OF HOST}:8081` 
