# Learning Progress Tracker (Java)

See the [project page on Hyperskill](https://hyperskill.org/projects/197) for a thorough overview.


## Stage 1/5: No empty lines here

### Description

We are going to create a program that keeps track of the learning progress of multiple students. To accomplish this task, we should teach our program to read various data in string and numeric formats, do certain calculations, and output desired information. To begin with, we need to make our program interactive.

Any program designed to interact with users should have a user interface. In our case, we will implement the command-line interface so that users can enter different commands and receive the corresponding responses. In addition, for the benefit of our users, we will make commands case-insensitive and keep our responses reasonably informative.

These are our requirements, so let's get going!

### Objectives

In this stage, your program should:

1. Demonstrate that it is running by printing its title: `Learning Progress Tracker`.
2. Wait for the commands. In this stage, the only command the program should recognize is `exit`. Once a user enters it, the program should print `Bye!` and quit.
3. Detect if a user has entered a blank line and print `No input` in response.
4. Print `Unknown command!` if a user enters an unknown command.

### Examples

The greater-than symbol is followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:** *an empty line and the exit*
```text
Learning Progress Tracker
> 
No input.
> exit
Bye!
```

**Example 2:** *unknown commands and exit*
```text
Learning Progress Tracker
> quit
Error: unknown command!
> help
Error: unknown command!
> exit
Bye!
```
