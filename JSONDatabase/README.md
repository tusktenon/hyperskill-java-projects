# JSON Database with Java 

## *How to run this project*

The JSON Database project actually consists of two programs: a client and a server (that are meant to be run simultaneously in separate terminals). I've written custom Gradle tasks for each of these, so they can be built and run with `gradle runClient`, `gradle runServer`. Alternatively, you can just use `gradle build` to compile all the source files, then navigate to the `build/classes/java/main` directory and run `java client.Main` or `java server.Main`.


## Project Description

Acquire skills of working with JSON to use this tool as part of your work as a software developer. You will create a functional server that exchanges data with a web browser and handles multiple requests.

[View more](https://hyperskill.org/projects/65)


## Stage 1/6: Create a database

### Description

A JSON database is a single-file database that stores information in JSON format. It is typically accessed remotely over the Internet.

In this stage, you will simulate a database that can store text information in a string array of fixed size 1000. Initially, each cell (or element) in the database contains an empty string. Users will be able to perform three primary actions: saving strings to cells, reading information from cells, and deleting information from cells. When a string is deleted, the corresponding cell should revert to containing an empty string.

Users can interact with the database using the following commands: `set`, `get`, and `delete`.

### Objectives

1. `set` command:
    - Syntax: `set <index> <text>`
    - Function: Saves the specified text to the cell at the given index (1-1000).
    - Output: If the index is out of bounds, the program should output `ERROR`. Otherwise, it should output `OK`. If the specified cell already contains information, it should be overwritten.
2. `get` command:
    - Syntax: `get <index>`
    - Function: Retrieves the text from the cell at the given index.
    - Output: If the cell is empty or the index is out of bounds, the program should output `ERROR`. Otherwise, it should output the content of the cell.
3. `delete` command:
    - Syntax: `delete <index>`
    - Function: Deletes the text from the cell at the given index.
    - Output: If the index is out of bounds, the program should output `ERROR`. Otherwise, it should output `OK`. If the cell is already empty, no other action is needed. It should just output `OK`.
4. `exit` command:
    - Syntax: `exit`
    - Function: Terminates the program.

> [!NOTE]
> The server and the client are different programs that run separately. Your server should run from the `main` method of the `Main` class in the `/server` package, and the client should run from the `main` method of the `Main` class in the `/client` package. To test your program, you should run the server first so a client can connect to the server.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.
```text
> get 1
ERROR
> set 1 Hello world!
OK
> set 1 HelloWorld!
OK
> get 1
HelloWorld!
> delete 1
OK
> delete 1
OK
> get 1
ERROR
> set 55 Some text here
OK
> get 55
Some text here
> get 56
ERROR
> delete 55
OK
> delete 56
OK
> delete 100
OK
> delete 1001
ERROR
> exit
```
