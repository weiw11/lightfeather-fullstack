# Frontend Angular Setup
FROM node:16-alpine AS frontend
WORKDIR /app/frontend
COPY frontend .
RUN npm install
RUN npm run build
FROM nginx:alpine
COPY --from=frontend /app/frontend/dist/frontend/ /usr/share/nginx/html

# Backend Java Setup
FROM maven:3.8.7-openjdk-18-slim AS backend
COPY backend/src /app/backend/src  
COPY backend/pom.xml /app/backend
RUN mvn -f /app/backend/pom.xml clean package

FROM eclipse-temurin:17.0.6_10-jre-ubi9-minimal
COPY --from=backend /app/backend/target/backend-0.0.1-SNAPSHOT.jar /usr/app/backend-0.0.1-SNAPSHOT.jar
EXPOSE 80 8080
ENTRYPOINT ["java","-jar","/usr/app/backend-0.0.1-SNAPSHOT.jar"]  
