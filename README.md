# WebSocket Chat Java

A simple chat application built with Spring Boot and WebSocket. This project demonstrates real-time chat functionality using Spring Boot's built-in WebSocket support. It includes a basic front-end interface built with HTML, CSS, and JavaScript.

## Features

- **Real-Time Communication:** Chat in real time using WebSockets.
- **Spring Boot:** Built on the Spring Boot framework (version 3.0.5).
- **Modern Java:** Uses Java 17 for modern language features.
- **Clean Front-End:** Static resources served from the `src/main/resources/static` directory.
- **Reduced Boilerplate:** Uses Lombok to simplify code.

## Prerequisites

- **Java 17** or later.
- **Maven 3.x** for building the project.

## Getting Started

Follow these steps to get a local copy of the project up and running.

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/Peczenko/websocket-chat-java.git
   cd websocket-chat-java
   ```

2. **Build the Project:**

   Use Maven to clean and build the project:

   ```bash
   ./mvnw clean install
   ```

3. **Run the Application:**

   Start the Spring Boot application with:

   ```bash
   ./mvnw spring-boot:run
   ```

4. **Access the Application:**

   Open your browser and navigate to [http://localhost:8080](http://localhost:8080) to view the chat interface.

## Project Structure

```
websocket-chat-java/
├── .mvn/                 # Maven wrapper files
├── src/
│   ├── main/
│   │   ├── java/         # Java source code (Spring Boot application)
│   │   └── resources/
│   │       ├── static/   # Static resources for front-end
│   │       │   ├── css/  # CSS styles
│   │       │   └── js/   # JavaScript files
│   │       └── templates/ 
│   └── test/             # Test 
├── pom.xml               # Maven configuration
└── README.md             # This file
```

## Configuration

- **Port Configuration:**  
  By default, the Spring Boot application runs on port `8080`. You can change this setting in the `src/main/resources/application.properties` file if needed.


