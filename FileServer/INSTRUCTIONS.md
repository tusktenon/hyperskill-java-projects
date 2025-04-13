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
