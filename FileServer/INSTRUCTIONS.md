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


## Stage 3/4: Save a text file

### Description

In this stage, you will write a program that can save files on a hard drive and send or delete them upon request.

To manage our files, we will use simplified HTTP requests. An HTTP request in our program should start with `GET`, `PUT`, or `DELETE`. We encourage you to [read more](https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods) about the HTTP methods. An HTTP response in our program should start with the status code of the operation. For example, the code `200` means that the operation was successful, and `404` means that the resource was not found. Check out the [full list of status codes on Wikipedia](https://en.wikipedia.org/wiki/List_of_HTTP_status_codes).

To create a new file on the server, the client should send a request `PUT NAME DATA`, where `NAME` is the name of the file without spaces or tabs, and `DATA` indicates text data. If a file with this name already exists, the server should respond with the code `403`. If the file is created successfully, the server responds with the code `200`. In this case, the client should print `The response says that the file was created!`. Otherwise, it should print `The response says that creating the file was forbidden!`

The server should store the client's files in the *.../server/data/* folder.

To get a file from the server, the client should send a request `GET NAME`, where `NAME` is the name of the file. If the file with this name does not exist, the server should respond with the code `404`. If a file with this name exists, the server responds with the code `200`, a single space, and the content of the file. In this case, the client should print `The content of the file is: FILE_CONTENT`, where `FILE_CONTENT` is the content of the requested file. Otherwise, it should print `The response says that the file was not found!`

To delete a file from the server, the client should send a request `DELETE NAME`, where `NAME` is the name of the file. If a file with this name does not exist, the server should respond with the code `404`. If the file is deleted successfully, the server responds with the code `200`. In this case, the client should print `The response says that the file was successfully deleted!`. Otherwise, it should print `The response says that the file was not found!`

In this stage, you should write a client program that prompts the user for an action, connects to the server, gets a response, prints it in a readable format, and quits. The client program should perform only one work cycle. Also, you should write a server program that processes the requests one after another in one cycle. As these requests are fast to process, there is no need for parallel execution.

Since the server cannot shut down by itself and the tests require that the program stops at a certain point, you should implement a simple way to stop the server. The client should be able to handle the `exit` action and send the respective message to the server. When the client sends `exit`, you should stop the server. Note: you shouldn't allow this behavior in a normal situation when no testing needs to be done.

Don't forget to shut down your server before starting tests!

### Objectives

In this stage, your client-side program should:

1. Prompt the user to enter an action.
2. Prompt the user for the name of the file to be created, sent, or deleted.
3. Prompt the user to enter the content of the file (when applicable).
4. Send the request to the server and receive a response from the server.
5. Print the respective message after receiving the response.
6. Disconnect from the server and terminate.

Your server-side program should:

1. Print `Server started!` when the program starts.
2. Receive a request from the client and respond accordingly.
    - For a `PUT` request, send a status code `200` if the file is created successfully; otherwise, send a status code `403`.
    - For a `GET` request, send a `200` status code and the `FILE_CONTENT` separated by a single space if the file exists; otherwise, send a `404` status code.
    For a `DELETE` request, send a `200` status code if the file is deleted successfully; otherwise, send a `404` status code.
3. Server program should not terminate until it receives the `exit` command.

### Examples

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

The first execution of the client program should produce the following output:
```text
Enter action (1 - get a file, 2 - create a file, 3 - delete a file): > 2
Enter filename: > 123.txt
Enter file content: > This is the first file on the server!
The request was sent.
The response says that file was created!
```

Then, the server should create a file on the disk drive with the text `This is the first file on the server!`. You should be able to access this file even after restarting the server.

After the second execution, the output of the client program should be the following:
```text
Enter action (1 - get a file, 2 - create a file, 3 - delete a file): > 1
Enter filename: > 123.txt
The request was sent.
The content of the file is: This is the first file on the server!
```

Here is an example of the output after deleting the file:
```text
Enter action (1 - get a file, 2 - create a file, 3 - delete a file): > 3
Enter filename: > 123.txt
The request was sent.
The response says that the file was successfully deleted!
```

After attempting to delete the same file again, the output should be the following:
```text
Enter action (1 - get a file, 2 - create a file, 3 - delete a file): > 3
Enter filename: > 123.txt
The request was sent.
The response says that the file was not found!
```

After attempting to get a file that doesn't exist, the output should be the following:
```text
Enter action (1 - get a file, 2 - create a file, 3 - delete a file): > 1
Enter filename: > file_that_doesnt_exist.txt
The request was sent.
The response says that the file was not found!
```

Here is an example of the output after stopping the server:
```text
Enter action (1 - get a file, 2 - create a file, 3 - delete a file): > exit
The request was sent.
```
