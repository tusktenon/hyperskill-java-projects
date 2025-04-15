# File Server

See the [project page on Hyperskill](https://hyperskill.org/projects/52) for a thorough overview.


## Stage 1/4: Storage emulator

### Description

An HTTP File Server is like a remote file storage. If you have Internet access, you can manage your files from anywhere in the world. In this project, you will create your own file keeper that will let you save, get, and delete files.

Let's start by creating a simulator of a file storage that can save only 10 files named *file1*, *file2*, *file3*, ... *file10*. At first, when the file server just starts, there are no files on it yet: you add them using the `add` command followed by the name of the file, for example, `add NAME`, where `NAME` is the actual name of the file.

Your program should support three additional commands: `get`, `delete`, and `exit`. The `get` command will be used to retrieve a file: `get NAME`. The `delete` command will be used to delete a file: `delete NAME`. The `exit` command should terminate the program.

### Objectives

In this stage, your program should:

1. Accept a command and a file name from the user.
2. For the `add` action, print the message `The file NAME added successfully` if the file has been added; otherwise, print the message `Cannot add the file NAME`.
3. For the `get` action, print the message `The file NAME was sent` if the file exists; otherwise, print the message `The file NAME not found`.
4. For the `delete` action, print the message `The file NAME was deleted` if the file has been deleted successfully; otherwise, print the message `The file NAME not found`.

### Example

Below is an output example of the described program. Try to output all the messages from the example.

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.
```text
> add file1
The file file1 added successfully
> add file1
Cannot add the file file1
> add file11
Cannot add the file file11
> get file1
The file file1 was sent
> get file2
The file file2 not found
> get abcd1
The file abcd1 not found
> delete file2
The file file2 not found
> delete file1
The file file1 was deleted
> exit
```


## Stage 2/4: Networking

### Description

Our objective is to make the files stored on our file server accessible through the Internet. To achieve that, we need two separate programs: a file server, which stores our files and processes client requests, and a client, which will be used to make requests to create, get, and delete files on our server.

Let's consider an important term for this stage: **socket**. A socket is an interface used to send and receive data between different processes running either on the same computer or on different computers connected through the Internet.

In this stage, you will implement the simplest connection between one server and one client. The client should send the message `Give me everything you have!` to the server, and the server should reply `All files were sent!`. Both the client and the server should print the received messages to the console.

The very first message the server outputs before the client connects to it should be `Server started!`.

To connect to the server, the client must know the server's address. The address consists of two parts: the IP address and the port number. In your program, you will use the IP address 127.0.0.1, which is the localhost (your own computer). The port can be any number from 0 to 65535, but preferably higher than 1024. The server and the client should use the same IP address and port number; otherwise, they won't find each other.

To start, import `java.io.*` and `java.net.*` , which are necessary for input/output operations with the socket.

Let's take a look at the client-side code:

```java
String address = "127.0.0.1";
int port = 23456;
Socket socket = new Socket(InetAddress.getByName(address), port);
DataInputStream input = new DataInputStream(socket.getInputStream());
DataOutputStream output = new DataOutputStream(socket.getOutputStream());
```

The client creates a new socket, which means that it's trying to connect to the server. The successful creation of a socket means that the client has found the server and connected to it.

After that, you can see the creation of the `DataInputStream` and `DataOutputStream` objects. These are input and output connections to the server. If you expect data from the server, you need to write `input.readUTF()`. This returns the `String` object that the server sent to the client. If you want to send data to the server, you need to write `output.writeUTF(stringText)`, and the message will be sent to the server.

Now, let's look at the server-side code:
```java
String address = "127.0.0.1";
int port = 23456;
ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
Socket socket = server.accept();
DataInputStream input = new DataInputStream(socket.getInputStream());
DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
```

The server creates a `ServerSocket` object that listens for client connections. When the client connects, the `server.accept()` method returns the socket connection to the client. After that, two objects are created: `DataInputStream` and `DataOutputStream`. These are the input and output connections to the client, now from the server side. To receive data from the client, you need to write `input.readUTF()`. To send data to the client, `write output.writeUTF(stringText)`.

You should create two packages named `client` and `server` and then create `Main.java` in both of these packages. Both classes should contain the `public static void main(String[] args)` method. To start the server, just run `main` from the `server` package. To start the client, run `main` from the `client` package. The tests depend on the presence of these classes!

Finally, make sure to close all the sockets and streams!

### Objectives

In this stage, your client-side program should:

1. Print `Client started!` when the program starts.
2. Send the message `Give me everything you have!` to the server.
3. Receive a response message from the server.

Your server-side program should:

1. Print `Server started!` when the program starts.
2. Receive a message from the client.
3. Send the response message `All files were sent!` to the client.

Be sure to print the sent and received messages in both programs.

### Example

The server output should look like this:
```text
Server started!
Received: Give me everything you have!
Sent: All files were sent!
```

The client output should be as follows:
```text
Client started!
Sent: Give me everything you have!
Received: All files were sent!
```
