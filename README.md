# Journal App

A journal application backend built with Spring Boot and MongoDB.

## Overview

Journal App is a web-based journaling application that allows users to create and manage their personal journal entries.

## Features
- Secure and Role based authentication using Spring Security
- MongoDB Atlas cloud database integration
- RESTful API endpoints for journal operations

## Technologies

- Java 17
- Spring Boot 3.5.0
- Spring Security
- Spring Data MongoDB
- Project Lombok
- Maven
- MongoDB Atlas (Cloud Database)

## Prerequisites

- JDK 17 or later
- MongoDB Atlas account
- Maven 3.x

## Configuration

### MongoDB Atlas Setup

1. Create a MongoDB Atlas account at [https://www.mongodb.com/cloud/atlas](https://www.mongodb.com/cloud/atlas)
2. Create a new cluster
3. In the cluster, click on "Connect" and choose "Connect your application"
4. Copy the connection string
5. Add your connection string in `application.properties` file in `src/main/resources` 

**Important**: Replace `your_mongodb_atlas_uri_here` with your actual MongoDB Atlas URI. Never commit the actual URI to version control.

## Building the Project

To build the project, run:
```bash
./mvnw clean install
```

## Running the Application

To start the application, run:

```bash
./mvnw spring-boot:run
```
The application will start on the default port 8080.

## Development
The project uses the following tools and configurations:
- Maven for dependency management and build automation
- Lombok for reducing boilerplate code (requires IDE plugin)
- Spring Boot 3.x framework
- MongoDB Atlas for cloud database

## Authors

- **Pritam Purkait**
  - Email: pritampurkait5533@gmail.com


