# Vaadin Test App
This project can be used as a starting point to create your own Vaadin application with Spring Boot.

## Running the application
The project is a standard Maven project. To run it from the command line, type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open http://localhost:8080 in your browser.

## Deploying to production
To create a production build, call `mvnw clean package -Pproduction` (Windows), or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources, ready to be deployed. The file can be found in the `target` folder after the build completes.
Once the JAR file is built, you can run it using `java -jar target/vaadintest-1.0-SNAPSHOT.jar`

## Deploying to Artifact Registry
```gcloud auth configure-docker us-central1-docker.pkg.dev```
```mvn compile -Pproduction com.google.cloud.tools:jib-maven-plugin:build -Dimage=us-central1-docker.pkg.dev/wmix-desenvolvimento/apps/vaadin-test```

##Deploying to Cloud Run
```gcloud alpha run deploy vaadin-test --project=wmix-desenvolvimento --region=us-central1 --min-instances=0 --max-instances=1 --cpu=1 --memory=256Mi --timeout=30s --cpu-throttling --cpu-boost --port=8080 --allow-unauthenticated --session-affinity --image=us-central1-docker.pkg.dev/wmix-desenvolvimento/apps/vaadin-test:20221103135304 --tag=d20221103135304```