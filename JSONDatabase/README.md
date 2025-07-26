# JSON Database with Java 

## *How to run this project*

The JSON Database project actually consists of two programs: a client and a server (that are meant to be run simultaneously in separate terminals). I've written custom Gradle tasks for each of these, so they can be built and run with `gradle runClient`, `gradle runServer`. Alternatively, you can just use `gradle build` to compile all the source files, then navigate to the `build/classes/java/main` directory and run `java client.Main` or `java server.Main`.

Starting with Stage 3, the client program has mandatory command-line arguments. These can be provided with Gradle's `--args` option; e.g.,
```sh
$ gradle runClient --args='-t set -i 1 -m "Hello world!"'
```


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


## Stage 3/6: Add new functionalities

### Description

In this stage, you will build upon the functionality of the program that you wrote in the first stage. The server should be able to receive messages with the operations `get`, `set`, and `delete`, each with an index of the cell.

For now, there is no need to save the database to a file on the hard drive, so if the server reboots, all the data in the database will be lost. The server should serve one client at a time in a loop, and the client should only send one request to the server, get one reply, and exit. After that, the server should wait for another connection from a client.

To send a request to the server, the client should get all the information through command-line arguments. There is a useful library called `JCommander` to parse all the arguments. It is included in our project setup, so you can use it without the need for any installation yourself. Before you get started with it, we recommend you check out a [JCommander tutorial](http://jcommander.org/).

The arguments will be passed to the client in the following format:
```sh
java Main -t <type> -i <index> [-m <message>]
```
- `-t` is the type of the request.
- `-i` is the index of the cell.
- `-m` is the value/message to save in the database (only needed for set requests).

For example: `java Main -t set -i 148 -m "Here is some text to store on the server"`

### Objectives

1. Implement the ability for the server to handle `get`, `set`, and `delete` operations with an index.
2. Ensure the server can serve one client at a time in a loop.
3. Since the server cannot shut down on its own and testing requires the program to stop at some point, you should implement a way to stop the server (after closing the socket), when the client sends an `exit` request. Note that in a normal situation, this behavior should not be allowed.
4. The client should send requests to the server using command-line arguments. These arguments include the type of the request (`set`, `get`, or `delete`), the index of the cell, and, in the case of the `set` request, a text value/message.

> [!NOTE]
> The server and the client are different programs that run separately. Your server should run from the `main` method of the `/server/Main` class, and the client should run from the `main` method of the `/client/Main` class.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

Starting the server:
```text
> java Main
Server started!
```

Starting the clients:
```text
> java Main -t get -i 1
Client started!
Sent: get 1
Received: ERROR
```

```text
> java Main -t set -i 1 -m "Hello world!"
Client started!
Sent: set 1 Hello world!
Received: OK
```

```text
> java Main -t set -i 1 -m HelloWorld!
Client started!
Sent: set 1 HelloWorld!
Received: OK
```

```text
> java Main -t get -i 1
Client started!
Sent: get 1
Received: HelloWorld!
```

```text
> java Main -t delete -i 1
Client started!
Sent: delete 1
Received: OK
```

```text
> java Main -t delete -i 1
Client started!
Sent: delete 1
Received: OK
```

```text
> java Main -t get -i 1
Client started!
Sent: get 1
Received: ERROR
```

```text
> java Main -t exit
Client started!
Sent: exit
Received: OK
```


## Stage 4/6: Start work with JSON

### Description

In this stage, you will store the database in JSON format, but keep in mind that this database will still be in memory and not saved as a file on the hard drive. To work with JSON, we recommend using the GSON library by Google. It is included in our project setup, so you can use it without the need for any installation yourself. It is also a good idea to get familiar with this library beforehand. You'd already have learned about it in before getting here. For more examples and explanations, visit [here](http://zetcode.com/java/gson/)!

You should store the database as a Java JSON object. The keys should be strings (now, no more limited to integer indexes like it was in the previous stages), and the values should be strings as well.

Example of a JSON database:
```json
{
    "key1": "some string value",
    "key2": "another string value",
    "key3": "yet another string value"
}
```

Note that we still use command-line arguments, but the client has to send JSON to the server and receive JSON from the server. Similarly, the server should process the received JSON and respond with JSON.

The format of the command-line arguments for the client should be as follows:
```text
java Main -t <type> -k <key> [-v <value>]
```
- `-t` specifies the type of request (`get`, `set`, or `delete`).
- `-k` specifies the key.
- `-v` specifies the value (only needed for `set` requests).

Note that based on the command-line arguments, the client should send to the server, a valid JSON which includes all the parameters needed to execute the request. Similarly, the server should respond with a valid JSON, for a valid request from a client.

Below are a few examples for the `set`, `get`, and `delete` requests, and the respective response from the server.

Here is what the `set` request format should look like:
```json
{ "type": "set", "key": "some key", "value": "some value" }
```

The respective response from the server, for that `set` request:
```json
{ "response": "OK" }
```

The `get` request format:
```json
{ "type": "get", "key": "some key" }
```

The `delete` request format:
```json
{ "type": "delete", "key": "a key that doesn't exist" }
```

In the case of a `get` request with a key stored in the database:
```json
{ "response": "OK", "value": "some value" }
```

In the case of a `get` or `delete` request with a key that doesn't exist:
```json
{ "response": "ERROR", "reason": "No such key" }
```

### Objectives

1. Implement a Java JSON object to store the database records.
2. Implement the `set`, `get`, and `delete` requests and the `OK` and `ERROR` responses.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

Starting the server:
```text
> java Main
Server started!
```

Starting the clients:
```text
> java Main -t get -k 1
Client started!
Sent: {"type":"get","key":"1"}
Received: {"response":"ERROR","reason":"No such key"}
```

```text
> java Main -t set -k 1 -v "Hello World!"
Client started!
Sent: {"type":"set","key":"1","value":"Hello World!"}
Received: {"response":"OK"}
```

```text
> java Main -t set -k 1 -v HelloWorld!
Client started!
Sent: {"type":"set","key":"1","value":"HelloWorld!"}
Received: {"response":"OK"}
```

```text
> java Main -t get -k 1
Client started!
Sent: {"type":"get","key":"1"}
Received: {"response":"OK","value":"HelloWorld!"}
```

```text
> java Main -t delete -k 1
Client started!
Sent: {"type":"delete","key":"1"}
Received: {"response":"OK"}
```

```text
> java Main -t delete -k 1
Client started!
Sent: {"type":"delete","key":"1"}
Received: {"response":"ERROR","reason":"No such key"}
```

```text
> java Main -t get -k 1
Client started!
Sent: {"type":"get","key":"1"}
Received: {"response":"ERROR","reason":"No such key"}
```

```text
> java Main -t set -k name -v "Sorabh Tomar"
Client started!
Sent: {"type":"set","key":"name","value":"Sorabh Tomar"}
Received: {"response":"OK"}
```

```text
> java Main -t get -k name
Client started!
Sent: {"type":"get","key":"name"}
Received: {"response":"OK","value":"Sorabh Tomar"}
```

```text
> java Main -t exit
Client started!
Sent: {"type":"exit"}
Received: {"response":"OK"}
```


## Stage 5/6: Manage multiple requests

### Theory

In the previous stages, you worked with an in-memory database, where the data was stored as a JSON object using Java Collections to allow for String keys and values. While this approach is suitable for small-scale applications, it has limitations when it comes to data persistence and scalability.

To overcome these limitations, you can work with a file-based database. By storing the database as a file on the hard drive, you can ensure that the data persists even if the server is restarted. This approach also prepares you for handling larger datasets that may not fit entirely in memory.

When your database server becomes very popular, it won’t be able to process a large number of requests because it can only process one request at a time. To avoid that scenario, you can parallelize the server's work using executors, so that each request is parsed and handled in a separate executor's task. The main thread should just wait for incoming requests.

For this kind of functionality, you will additionally need **synchronization** because all your threads will work with the same (database) file. Even after parallelizing, you need to maintain the integrity of the database. Of course, you can't write the file in more than one separate threads simultaneously, but if no one is currently writing to the file, a lot of threads can read it, and no one can interrupt the other since no one is modifying it. This behavior is implemented in `java.util.concurrent.locks.ReentrantReadWriteLock` class. It allows multiple readers of the resource but only a single writer. Once a writer locks the resource, it waits until all the readers finish reading and only then starts to write. The readers can freely read the file even though other readers locked it, but if the writer locks the file, no readers can read it.

To use this class, you need two locks: read lock and write lock. See the snippet below:
```java
ReadWriteLock lock = new ReentrantReadWriteLock();
Lock readLock = lock.readLock();
Lock writeLock = lock.writeLock();
```

Every time you want to read the file, invoke `readLock.lock()`. After reading, invoke `readLock.unlock()`. And do the same with `writeLock`, but only when you want to change the data.

### Description

In this stage, you will need to improve your client and server by adding the ability to work with files. The server should store (persist) the database as a file on the hard drive, updating it only when setting a new value or deleting one. This functionality is crucial for maintaining data persistence and ensuring that the database state is saved even if the server is restarted.

To handle multiple requests efficiently, you will parallelize the server's work using executors. Each request will be parsed and handled in a separate executor's task, allowing the server to process multiple requests simultaneously. This improvement will significantly enhance the server's performance and scalability, making it capable of handling a higher load.

Implementing synchronization is essential to maintain the integrity of the database when multiple threads access the same file. By using the `ReentrantReadWriteLock` class, you can allow multiple threads to read the file concurrently while ensuring that only one thread can write to the file at a time. This will prevent data corruption and ensure consistent access to the database.

Additionally, you will implement the ability for the client to read a request from a file. If the `-in` argument is followed by a file name, the client should read the request from that file. The file will be stored in the `/client/data` directory. This feature allows the client to directly send pre-formatted JSON requests to the server, bypassing the need to first convert command-line arguments into JSON format and then send that JSON to the server.

One significant advantage of this feature is that it will allow us to store not just strings but also complex JSON objects as values in the future. Writing complex JSON objects directly on the command line can be tedious and error-prone. By using pre-formatted JSON files, we can easily manage and send complex data structures to the server.

Here are the examples of the input file contents:
```json
{"type":"set","key":"name","value":"Sorabh"}
```
```json
{"type":"get","key":"name"}
```
```json
{"type":"delete","key":"name"}
```

For reading client requests from a file, you can get the path using:
```java
path = System.getProperty("user.dir") + "/src/client/data/" + fileName;
```

Note that like in the previous stage, you should store the database as a JSON object. The keys and values should both be strings.

Example of the contents of a JSON database file:
```json
{
    "key1": "some string value",
    "key2": "another string value",
    "key3": "yet another string value"
}
```

For working with database file, you can get the path using:
```java
path = System.getProperty("user.dir") + "/src/server/data/db.json";
```

### Objectives

1. **Persist the Database**: The server should store the database on the hard drive in a `db.json` file, located in the `/server/data` folder. The database should be updated only after setting a new value or deleting an existing one.

2. **Parallelize Request Handling**: The server should handle multiple requests simultaneously by using executors. Each request should be processed in a separate task, while the main thread waits for incoming requests.

3. **Implement Synchronization**: Ensure that multiple threads can read the database file concurrently, but only one thread can write to the file at a time. This will prevent data corruption and ensure consistent access to the database.

4. **Read Requests from a File**: The client should be able to read a request from a file if the `-in` argument is followed by a file name. The file will be stored in the `/client/data` directory.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

Starting the server:
```text
> java Main
Server started!
```

Starting the clients:

```text
> java Main -t get -k name
Client started!
Sent: {"type":"get","key":"name"}
Received: {"response":"ERROR","reason":"No such key"}
```

```text
> java Main -t set -k name -v "Sorabh Tomar" 
Client started!
Sent: {"type":"set","key":"name","value":"Sorabh Tomar"}
Received: {"response":"OK"}
```

```text
> java Main -t set -k name -v Sorabh
Client started!
Sent: {"type":"set","key":"name","value":"Sorabh"}
Received: {"response":"OK"}
```

```text
> java Main -t get -k name 
Client started!
Sent: {"type":"get","key":"name"}
Received: {"response":"OK","value":"Sorabh"}
```

```text
> java Main -in testSet.json 
Client started!
Sent: {"type":"set","key":"name","value":"Sorabh"}
Received: {"response":"OK"}
```

```text
> java Main -in testGet.json 
Client started!
Sent: {"type":"get","key":"name"}
Received: {"response":"OK","value":"Sorabh"}
```

```text
> java Main -in testDelete.json 
Client started!
Sent: {"type":"delete","key":"name"}
Received: {"response":"OK"}
```

```text
> java Main -t exit 
Client started!
Sent: {"type":"exit"}
Received: {"response":"OK"}
```


## Stage 6/6: Store JSON objects in your database

### Description

In this stage, you will enhance your database to store not just strings but any JSON types, including objects, arrays, numbers as values. This improvement will allow for more complex and nested data structures within the database.

Similarly, the key should not just be a single string as it was in the previous stage. The key should instead be in the form of an array, because now the user needs to be able to retrieve specific parts of the JSON value. For example, consider the following JSON structure, where the user wants to get only the `surname` of `person`:
```text
{
    ... ,

    "person": {
        "name": "Adam",
        "surname": "Smith"
    }
    ...
}
```

To retrieve only the surname of the person, the user should provide the full path to this field in the form of a JSON array: `["person", "surname"]`. If the user wants to get the full person object, they should provide `["person"]`.

The user should also be able to set value against different keys inside JSON objects. For example, it should be possible to set only the `surname` using the key `["person", "surname"]` and any value against a key, including another JSON object.

Moreover, the user should be able to add new values inside other JSON objects. For example, using the key `["person", "age"]` and the value `25`, the person object should look like this:
```text
{
    ... ,

    "person": {
        "name": "Adam",
        "surname": "Smith",
        "age": 25

    }
    ...
}
```

If there are no root objects, the server should create them. For example, if the database does not have a `"person1"` key but the user sets the value `{"id1": 12, "id2": 14}` for the key `["person1", "inside1", "inside2"]`, then the database will have the following structure:
```text
{
    ... ,
    "person1": {
        "inside1": {
            "inside2" : {
                "id1": 12,
                "id2": 14
            }
        }
    },
    ...
}
```
The deletion of objects should follow the same rules. If a user deletes the object above by the key `["person1", "inside1", "inside2"]`, then only `"inside2"` should be deleted, not `"inside1"` or `"person1"`. See the example below:
```text
{
    ... ,
    "person1": {
        "inside1": { }
    }

    ...
}
```

### Objectives

- **Enhance JSON Storage**: Modify your database to store any JSON values, not just strings.
- **Nested Key Access**: Implement the ability to access and modify nested JSON values using keys in the form of JSON arrays.
- **Dynamic Object Creation**: Ensure the server can dynamically create root objects if they do not exist when setting new values.
- **Selective Deletion**: Implement the ability to delete nested JSON objects without affecting their parent objects.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

Starting the server:
```text
> java Main
Server started!
```

There is no need to format JSON in the output.

Starting the clients:
```text
> java Main -t set -k text -v "Hello World!"
Client started!
Sent: {"type":"set","key":"text","value":"Hello World!"}
Received: {"response":"OK"}
```
```text
> java Main -in setFile.json 
Client started!
Sent:
{
   "type":"set",
   "key":"person",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster",
         "year":"2018"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"87"
      }
   }
}
Received: {"response":"OK"}
```
```text
> java Main -in getFile.json 
Client started!
Sent: {"type":"get","key":["person","name"]}
Received: {"response":"OK","value":"Elon Musk"}
```
```text
> java Main -in updateFile.json 
Client started!
Sent: {"type":"set","key":["person","rocket","launches"],"value":"88"}
Received: {"response":"OK"}
```
```text
> java Main -in secondGetFile.json 
Client started!
Sent: {"type":"get","key":["person"]}
Received:
{
   "response":"OK",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster",
         "year":"2018"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"88"
      }
   }
}
```
```text
> java Main -in deleteFile.json 
Client started!
Sent: {"type":"delete","key":["person","car","year"]}
Received: {"response":"OK"}
```
```text
> java Main -in secondGetFile.json 
Client started!
Sent: {"type":"get","key":["person"]}
Received:
{
   "response":"OK",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"88"
      }
   }
}
```
```text
> java Main -t exit 
Client started!
Sent: {"type":"exit"}
Received: {"response":"OK"}
```
