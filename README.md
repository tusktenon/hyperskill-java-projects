# Hyperskill Java Projects

These are the projects I completed as part of the [Java Backend Developer (Spring Boot)](https://hyperskill.org/courses/12-java-backend-developer-spring-boot) course on [Hyperskill](https://hyperskill.org).

When I started this course, I already had a fair amount of experience with Java, but none with Java build tools. To get as much practice as possible, I decided to use Gradle for even the simplest of projects.

Most of the projects are CLI applications that can be started by executing `gradle run` from the project's root directory (I've added the `console=plain` option to each project's `gradle.properties` file, so the command-line output should be clear of any Gradle-related interference). There are a few special cases:

- The later stages of the File Server project have separate client and server programs; see the project's [README](FileServer/README.md) for details.
- The later stages of the Simple Banking System project have (mandatory) command-line arguments;  see the project's [README](SimpleBankingSystem/README.md) for details.
- Cinema Room REST Service and QRCode Service are Spring Boot applications that can be started with `gradle bootRun`.

*A note on code style.* I have a slight preference for Google Java Style over Oracle Java Conventions, and used the former for my first four projects (Cinema Room Manager, Coffee Machine Simulator, Simple Tic-Tac-Toe, and Tic-Tac-Toe with AI). I switched to Oracle style for the remaining projects, to be consistent with Hyperskill.

## Easy Projects

- [Cinema Room Manager](https://hyperskill.org/projects/133)
- [Coffee Machine Simulator](https://hyperskill.org/projects/33)
- [Simple Tic-Tac-Toe](https://hyperskill.org/projects/48)

## Medium Projects

- [Budget Manager](https://hyperskill.org/projects/76)
- [File Server](https://hyperskill.org/projects/52)
- [Maze Runner](https://hyperskill.org/projects/47)
- [Simple Banking System](https://hyperskill.org/projects/93) (JDBC with SQLite)
- [Tic-Tac-Toe with AI](https://hyperskill.org/projects/81)
- [Traffic Light Simulator](https://hyperskill.org/projects/288)

## Hard Projects

- [Cinema Room REST Service](https://hyperskill.org/projects/189) (Spring Boot and Spring Web MVC)
- [Learning Progress Tracker](https://hyperskill.org/projects/197)
- [QRCode Service](https://hyperskill.org/projects/385) (Spring Boot and Spring Web MVC)
