# Traffic Light Simulator with Java 

See the [project page on Hyperskill](https://hyperskill.org/projects/288) for a thorough overview.


## Stage 1/6: Open the control panel

### Description

Let's output the starting menu that greets users and shows them a list of four possible options. We will use this menu in later stages.

### Objectives

As a start, develop a simple program that prints six non-empty lines to the output.

1. Being a very polite program, it greets users on the first line with the `Welcome` substring and tells them that they've just started `traffic management system`.
2. The following line is the list's title, with `Menu` substring.
3. After that, finally, display the list line-by-line in exact order, indexing and substrings:
```text
1. Add
2. Delete
3. System
0. Quit
```

In further stages we will control the traffic light system with these actions.

### Example

```text
Welcome to the traffic management system!
Menu:
1. Add
2. Delete
3. System
0. Quit
```


## Stage 2/6: Set up the traffic light

### Description

What is a system without parameters? The traffic light should work the way users want it. Everything is simple — provide input and get the corresponding program's output.

### Objectives

In this stage, after the welcoming line, ask the users to `input` the desired `number` of roads and `input` the `interval` at which the roads should open/close. After each request, read the value that a user provides.

Next, implement a looped selection menu. The loop (as well as the program execution) ends when a user selects `0` as the desired option. Any other option (`1`, `2`, `3`) prints an informational text on the action performed (`add`, `delete`, `system`) for each option.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

```text
Welcome to the traffic management system!
Input the number of roads: > 5
Input the interval: > 3
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 1
Road added
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 2
Road deleted
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 3
System opened
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 0
Bye!
```
