# Hyperskill Java Projects

These are the projects I completed as part of the [Java Backend Developer (Spring Boot)](https://hyperskill.org/courses/12) and [Spring Security for Java Backend Developers](https://hyperskill.org/courses/38) courses on [Hyperskill](https://hyperskill.org).


## Running the projects

When I started this course, I already had a fair amount of experience with Java, but none with Java build tools. To get as much practice as possible, I decided to use Gradle for even the simplest of programs.

Each project falls into one of three groups:

- **CLI applications that do not use Spring.** These can be started by executing `gradle run` from the project's root directory (I've added the `console=plain` option to each project's `gradle.properties` file, so the command-line output should be clear of any Gradle-related interference).
- **Spring Boot applications.** These can be started with `gradle bootRun`.
- **Special cases.** Some projects have exceptional requirements (at least in their later stages), which are described in detail in their READMEs:
    - The File Server and JSON Database projects have separate client and server programs, and the JSON Databases client takes mandatory command-line arguments;
    - Meal Planner requires a connection to a PostgreSQL database;
    - Simple Banking System has mandatory command-line arguments;
    - Transaction Aggregator only works if compatible data servers are running on the same machine, so I've created a [companion project](TransactionAggregatorDataServer) that provides a simple implementation for these servers.


## The Projects

*A note on code style.* I had a slight preference for Google Java Style over Oracle Java Conventions when I started this course, and used the former for my first four projects (Cinema Room Manager, Coffee Machine Simulator, Simple Tic-Tac-Toe, and Tic-Tac-Toe with AI). I switched to Oracle style for the remaining projects, to be consistent with Hyperskill.

### Easy

- [Cinema Room Manager](https://hyperskill.org/projects/133)
- [Coffee Machine Simulator](https://hyperskill.org/projects/33)
- [Simple Tic-Tac-Toe](https://hyperskill.org/projects/48)

### Medium

- [Budget Manager](https://hyperskill.org/projects/76)
- [File Server](https://hyperskill.org/projects/52)
- [HyperCollections](https://hyperskill.org/projects/319)
- [JSON Database](https://hyperskill.org/projects/65)
- [Maze Runner](https://hyperskill.org/projects/47)
- [Simple Banking System](https://hyperskill.org/projects/93) (JDBC with SQLite)
- [Tic-Tac-Toe with AI](https://hyperskill.org/projects/81)
- [Traffic Light Simulator](https://hyperskill.org/projects/288)

### Hard

- [Car Sharing](https://hyperskill.org/projects/140) (JDBC with H2)
- [Cinema Room REST Service](https://hyperskill.org/projects/189) (Spring Boot and Spring Web MVC)
- [Customer Feedback Service](https://hyperskill.org/projects/409) (Spring Boot, Spring Data MongoDB and Spring Web MVC)
- [Learning Progress Tracker](https://hyperskill.org/projects/197)
- [Meal Planner](https://hyperskill.org/projects/318) (JDBC with PostgreSQL)
- [QRCode Service](https://hyperskill.org/projects/385) (Spring Boot and Spring Web MVC)
- [Transaction Aggregator](https://hyperskill.org/projects/424) (Spring Boot and Spring Web MVC)
- [Web Calendar](https://hyperskill.org/projects/396) (Spring Boot, Spring Data JPA and Spring Web MVC)

### Challenging

- [Fitness Tracker API](https://hyperskill.org/projects/408) (Spring Boot, Spring Data JPA and Spring Web MVC)
- [Task Management System](https://hyperskill.org/projects/423) (Spring Boot, Spring Data JPA, Spring Security and Spring Web MVC)
- [Web Quiz Engine with Java](https://hyperskill.org/projects/91) (Spring Boot, Spring Data JPA, Spring Security and Spring Web MVC)
