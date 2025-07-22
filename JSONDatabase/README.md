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


## Stage 2/6: Connect it to a server

### Theory

Usually, remote databases are accessed through the internet. In this project, the database will be on your computer, but it will still be run as a separate program (we'll call it the server). The client who wants to `get`, `create`, or `delete` some information is a separate program too.

We will be using a **socket** to connect to the database (server). A socket is an interface to send and receive data between different processes. These processes can be on the same computer or different computers connected through the internet.

To connect to the server, the client must know its address, which consists of two parts: IP address and port. The local address of your computer is always "127.0.0.1". The port can be any number between 0 and 65535, but preferably greater than 1024 to avoid conflicts with well-known ports used by system processes.

Let's take a look at this client-side code:
```java
String address = "127.0.0.1";
int port = 23456;
Socket socket = new Socket(InetAddress.getByName(address), port);
DataInputStream input = new DataInputStream(socket.getInputStream());
DataOutputStream output = new DataOutputStream(socket.getOutputStream());
```

The client created a new socket, which means that the client tried to connect to the server. Successful creation of a socket means that the client found the server and managed to connect to it.

After that, you can see the creation of `DataInputStream` and `DataOutputStream` objects. These are the input and output streams to the server, respectively. If you expect data from the server, you need to write `input.readUTF()`. This returns the String object that the server sent to the client. If you want to send data to the server, you need to write `output.writeUTF(stringText)`, and this message will be sent to the server.

Now let's look at the server-side code:
```java
String address = "127.0.0.1";
int port = 23456;
ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
Socket socket = server.accept();
DataInputStream input = new DataInputStream(socket.getInputStream());
DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
```

The server created a `ServerSocket` object that waits for client connections. When a client connects, the method `server.accept()` returns the `Socket` connection to this client.

After that, you can see the creation of `DataInputStream` and `DataOutputStream` objects. These are the input stream from and output stream to this client, respectively, now from the server side. To receive data from the client, write `input.readUTF()`. To send data to the client, write `output.writeUTF(stringText)`. The server should stop after responding to the client.

### Description

In this stage, you will implement the simplest connection between one server and one client. The client should send the server a message: something along the lines of `Give me a record # N`, where `N` is an arbitrary integer number. The server should reply `A record # N was sent!` to the client. Both the client and the server should print the received messages to the console.

> [!NOTE]
> In this stage, we are focusing solely on establishing communication between the client and server using sockets, and printing the exchanged messages. We are not yet performing actual database operations (`get`, `set`, `delete`), like we did in the previous stage.

### Objectives

1. Implement a server that waits for a client connection and responds to a specific message.
2. Implement a client that connects to the server and sends a specific message.
3. Ensure both the client and the server print the received messages to the console.

**Important:** Before a client connects to the server, the server output should be: `Server started!`. Similarly, after the client connects to the server, the client should print `Client started!`.

> [!NOTE]
> The server and the client are different programs that run separately. Your server should run from the `main` method of the `Main` class in the `/server` package, and the client should run from the `main` method of the `Main` class in the `/client` package. To test your program, you should run the server first so a client can connect to the server.

### Example

The server should output something like this:
```text
Server started!
Received: Give me a record # 12
Sent: A record # 12 was sent!
```

The client should output something like this:
```text
Client started!
Sent: Give me a record # 12
Received: A record # 12 was sent!
```

> [!NOTE]
> Here, number 12 in the examples was chosen arbitrarily. You can use any integer number of your liking.
