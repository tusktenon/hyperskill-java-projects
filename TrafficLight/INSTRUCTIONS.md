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


## Stage 3/6: Oops, wrong button

### Description

What if users didn't get enough sleep? All night they controlled the movement of imaginary roads and in the morning, struggling with sleep and misclicks, enter the incorrect parameters. The system should handle wrong input and print appropriate feedback.

In this stage, let's expand our program with error handling and some visual improvements.

The number of roads and intervals at which the roads should open/close should be positive integer values (note, that `0` is not a positive value), so if a user provided any other input, our system should print an error that contains the `Incorrect input` and `Try again` substrings.

The selected option in the menu should be either `0`, `1`, `2` or `3`, so if a user made a mistake, our system should print the `Incorrect option` feedback.

To make the output of our program more convenient, we can clear the previous output after each menu option is executed. Due to the cross-platform nature of Java, clearing the console output can be complicated. You can use this snippet to remove the console output.
```java
try {
  var clearCommand = System.getProperty("os.name").contains("Windows")
          ? new ProcessBuilder("cmd", "/c", "cls")
          : new ProcessBuilder("clear");
  clearCommand.inheritIO().start().waitFor();
}
catch (IOException | InterruptedException e) {}
```
However, it would be difficult for users to get familiar with the result of the execution before the information is cleared, so after each operation, the program must wait for a new line to be entered before the next iteration.

**Note:** Clearing won't work in IntelliJ IDEA console. For that to show up you'll need to run a solution from a terminal.

We won't test how you clear the console. If you choose to display the output as solid text, make sure that your program still waits for a new line after option execution.

### Objectives

To complete this stage, your program must comply with the following requirements:

1. If the provided input for the number of roads or interval is not a positive integer value, the program should print a line, containing `Incorrect input` and `Try again` substrings, followed by a new input.
2. If the chosen option is something other than `0`, `1`, `2`, or `3`, the program should output an `Incorrect option` feedback.
3. Modify the infinite loop so that when the result of option execution is shown, the program requires any input before the next iteration.

### Example

**Note:** From this stage and beyond, the example will contain the gif representation of program running and an attached transcription block that contains the most important information. Text that starts with `//` is a comment, the greater-than symbol followed by a space `> ` represents the user input.

![](resources/Stage3Example.gif)

<details>
<summary>Transcription</summary>
```text
Welcome to the traffic management system!
Input the number of roads: > -1
Error! Incorrect Input. Try again: > Hello
Error! Incorrect Input. Try again: > 0
Error! Incorrect Input. Try again: > 5
Input the interval: > -5
Error! Incorrect Input. Try again: > World
Error! Incorrect Input. Try again: > 0
Error! Incorrect Input. Try again: > 3
// Output cleared
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 4
Incorrect option
>
// Output cleared
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> -1
Incorrect option
>
// Output cleared
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> Hello world
Incorrect option
>
// Output cleared
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 2
Road deleted
>
// Output cleared
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 0
Bye!
```
</details>


## Stage 4/6: Like a clockwork

### Description

Creating a whole system at once is challenging, so we will start with a simple stopwatch in the new thread.

Let's assume that our program has three possible states:

- **Not Started** — the state until the initial settings have been provided;
- **Menu** — the state we worked with earlier. It shows possible options and processes the user's input;
- **System** — the state that shows the user information about our traffic light, such as time from startup and initial settings for now.

When the user provided input for initial settings (both the number of roads and the interval), create a new thread to implement the **System** state. Name it as `QueueThread` by calling its `setName()` method with the corresponding string argument. The actions this newly-created thread should perform for now each second are:

- Increasing the variable that corresponds to the amount of time since the "system startup" each second (1000 milliseconds);
- (if in System state) Printing the system information.

Let's add new functionality to the `Open system` menu option. By choosing `3` option in Menu, the program switches to the System state, and the **main thread waits for input from the user**. To return to the Menu state, the user should press Enter, in other words, an empty string should be provided as input.

Example of system information output:
```text
! 3s. have passed since system startup !
! Number of roads: 1 !
! Interval: 1 !
! Press "Enter" to open menu !
```

**Note:** From this stage and beyond, the testing process can take some time.

### Objectives

Implement the System state as described above and set its thread name as `QueueThread`. While your program is in the System state, the output should contain the following information, updated (printing out) each second:

1. The line that shows the time since the program's start (contains only one integer — the number of seconds);
2. The line that indicates the number of roads provided by the user (includes the `number` substring and only one integer — the same value that was provided in the settings);
3. The line that shows the interval provided by the user (contains the `interval` substring and only one integer — the same value that was provided in the settings);
4. The line that asks the user to press Enter to open the menu (should contain the `Enter` substring)

When the user provided an empty input, the program should switch back to the Menu state and show the previously implemented menu.

**Note:** The created `QueueThread` thread should be terminated when the program is finished.

### Example

![](resources/Stage4Example.gif)

<details>
<summary>Transcription</summary>
```text
Welcome to the traffic management system!
Input the number of roads: > 5
Input the interval: > 3
// Started counting seconds
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
// 1 second passed
> 3
! 1s. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !
! Press "Enter" to open menu !
// 1 second passed
! 2s. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !
! Press "Enter" to open menu !
// 1 second passed
! 3s. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !
! Press "Enter" to open menu !
// 1 second passed
! 4s. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !
! Press "Enter" to open menu !
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
// 3 seconds passed
> 3
! 7s. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !
! Press "Enter" to open menu !
// 1 second passed
! 8s. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !
! Press "Enter" to open menu !
// 1 second passed
! 9s. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !
! Press "Enter" to open menu !
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 0
Bye!
```
</details>


## Stage 5/6: Over and over again

### Description

Expand the created stopwatch into a road management system. Implement the circular queue that contains roads and add functionality to the remaining options in the menu.

### Objectives

In this stage, we will add functionality to the `Add road` and the `Delete road` options. Expand the `QueueThread` system by implementing a circular queue, where the maximum number of roads is the value provided in users' settings.

When the user selects `1` as an option, print a line containing the `input` substring, followed by an input for a new element.

- If the queue is full, the program should inform the user about that with the `queue is full` substring
- Otherwise, add this element to the end of a queue and inform users with the `add` substring and the element's name

When the user selects `2` as an option:

- If the queue is empty, the program should inform users with the `queue is empty` substring
- Otherwise, delete the element from the start of the queue and inform users with the `delete` substring and the element's name.

Also, expand the output of the system information. If the queue is not empty, print all the elements' names line by line in the queue from front to rear, just like in the example.

### Examples

**Example 1**

![](resources/Stage5Example1.gif)

<details>
<summary>Transcription</summary>
```text
# To shrink the transcription each thread's output sequence will be collapsed like this:
# ! Xs. have passed since system startup !
# ! Number of roads: 5 !
# ! Interval: 3 !
# *roads*
# ! Press "Enter" to open menu !

Welcome to the traffic management system!
Input the number of roads: > 5
Input the interval: > 3
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 1
Input road name: > RoadA
RoadA Added!
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 3
! Xs. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !

RoadA

! Press "Enter" to open menu !
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 1
Input road name: > RoadB
RoadB Added!
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 3
! Xs. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !

RoadA
RoadB

! Press "Enter" to open menu !
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 1
Input road name: > RoadC
RoadC Added!
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 3
! Xs. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !

RoadA
RoadB
RoadC

! Press "Enter" to open menu !
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 2
RoadA deleted!
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 3
! Xs. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !

RoadB
RoadC

! Press "Enter" to open menu !
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 1
Input road name: > RoadD
RoadD Added!
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 2
RoadB deleted!
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 2
RoadC deleted!
>
> 3
! Xs. have passed since system startup !
! Number of roads: 5 !
! Interval: 3 !

RoadD

! Press "Enter" to open menu !
>
Menu:
1. Add road
2. Delete road
3. Open system
0. Quit
> 0
Bye!
```
</details>

**Example 2**

![](resources/Stage5Example2.gif)

<details>
<summary>Transcription</summary>
```text
# To shrink the output each thread's message sequence will be collapsed like this:
# ! THREAD_INFO_HEADER !
# 
# ! THREAD_INFO_FOOTER !

# And each menu output will be collapsed like this:
# ! MENU_INFO !

Welcome to the traffic management system!
Input the number of roads: > 2
Input the interval: > 2
! MENU_INFO !
> 1
Input road name: > RoadA 
RoadA Added!
> 
! MENU_INFO !
> 1
Input road name: > RoadB 
RoadB Added!
> 
! MENU_INFO !
> 1
Input road name: > RoadC 
Queue is full
> 
! MENU_INFO !
> 3
! THREAD_INFO_HEADER !

RoadA
RoadB

! THREAD_INFO_FOOTER !
>
! MENU_INFO !
> 2
RoadA deleted!
>
! MENU_INFO !
> 2
RoadB deleted!
>
! MENU_INFO !
> 3
! THREAD_INFO_HEADER !


! THREAD_INFO_FOOTER !
>
! MENU_INFO !
> 2
Queue is empty
>
! MENU_INFO !
> 1
Input road name: > RoadD 
RoadD Added!
> 
! MENU_INFO !
> 3
! THREAD_INFO_HEADER !

RoadD

! THREAD_INFO_FOOTER !
>
! MENU_INFO !
> 0
Bye!
```
</details>


## Stage 6/6: Red, yellow, green

### Description

So far, the information on which roads are part of the road junction means nothing to a driver and does not help users to control traffic. In this stage, we will finish our traffic management system. As a result, the system will control a set of roads, opening and closing them at intervals specified by the user.

The program first welcomes the user and prompts them to input the number of roads and the interval between the opening and closing of each road. Then the control panel with menu is displayed.

Let's consider a scenario where we want to control 4 roads, with an interval of 8 seconds. We add all the roads sequentially via the `1. Add` option: `"First"`, `"Second"`, `"Third"`, `"Fourth"`. After all the roads are added and the interval has been specified, we can start our system with `3. System`:
```text
! Number of roads: 4 !
! Interval: 8 !

Road "First" will be open for 8s.
Road "Second" will be closed for 8s.
Road "Third" will be closed for 16s.
Road "Fourth" will be closed for 24s.

! Press "Enter" to open menu !
```

It might be difficult to understand how this system should work, so here is a brief explanation with examples:

The roads are managed using a circular queue, where the road at the front of the queue is open, and the rest are closed. In this case, `"First"` was added first, so it is also the first to be open. It will remain open for the specified interval of 8 seconds before switching to the next road in line. So, `"Second"` will open after `"First"` in 8 seconds, `"Third"` will open after `"Second"` in 16 seconds, and `"Fourth"` will open after `"Third"` in 24 seconds.

For example, if the remaining time for `"First"` is 1 second, the system will display:
```text
Road "First" will be open for 1s.
Road "Second" will be closed for 1s.
Road "Third" will be closed for 9s.
Road "Fourth" will be closed for 17s.
```
Once the timer for the open road expires, it moves to the back of the queue, and the next road becomes open:
```text
Road "First" will be closed for 24s.
Road "Second" will be open for 8s.
Road "Third" will be closed for 8s.
Road "Fourth" will be closed for 16s.
```
In this system, once the last added road, `"Fourth"`, is closed, `"First"` will open again, and the cycle will repeat.

Overall, the program displays the current state of each road (`open`/`close`) and calculates the time, until this road closes/opens.

Let's consider corner cases. The time for each road should be calculated before the information is displayed, so if a road is deleted, the time for the other roads will change. Let's say we've just deleted one road from system. The next output (after 1 second) will be:
```text
Road "First" will be closed for 15s
Road "Second" will be open for 7s.
Road "Third" will be closed for 7s.
```
Similarly, the time for other roads will change when adding roads.

Let's continue exploring our example. If the opened road is deleted, there should be no roads in state `open` until the next one opens. Let's say we've just deleted two roads from system (sequentially). The next output (after 1 second) will be:
```text
Road "Third" will be closed for 6s.
```
Output after 6 more seconds will be:
```text
Road "Third" will be open for 6s.
```
If the only road that exists is the opened one - it should never close (its state should be always `open`), but still should count down the time to close, so the output after 7 seconds will be:
```text
Road "Third" will be open for 5s.
```
When there are no roads in the system, the next added road must be open for an interval. Let's say we've deleted last road from system, leaving it empty and added one more road. The next output will be:
```text
Road "Fifth" will be open for 8s.
```
Also, you might want to make your program more user-friendly and beautiful. You can achieve it by adding color to the output — green, yellow, and red with these ANSI color codes:

ANSI_RED = `"\u001B[31m"`
ANSI_GREEN = `"\u001B[32m"`
ANSI_YELLOW = `"\u001B[33m"`
ANSI_RESET = `"\u001B[0m"`

```text
System.out.println("\u001B[32m" + "Hello World!" + "\u001B[0m");
```
The snippet above will give you `Hello World!`

### Objectives

Your task for the final stage will be to add the road's state and timing information to each of the elements in **System mode**. Add the `open`/`close` substring, according to it's state and calculate the time, until this road switches to another state.

### Example

![](resources/Stage6Example.gif)

<details>
<summary>Transcription</summary>
```text
# Since the structure of the output does not change in this stage, this transcription will contain only actions and road states each second.
# "(hidden) " implies that thread's output on this second was not displayed in gif
# Number of roads: 5
# Interval: 3
(hidden) 1:
(hidden) 2:

# RoadA added

(hidden) 3:
RoadA will be open for 3s.
(hidden) 4:
RoadA will be open for 2s.
(hidden) 5:
RoadA will be open for 1s.

# RoadB added

(hidden) 6:
RoadA will be closed for 3s.
RoadB will be open for 3s.
(hidden) 7:
RoadA will be closed for 2s.
RoadB will be open for 2s.
(hidden) 8:
RoadA will be closed for 1s.
RoadB will be open for 1s.
(hidden) 9:
RoadA will be open for 3s.
RoadB will be closed for 3s.

# RoadC added

(hidden) 10:
RoadA will be open for 2s.
RoadB will be closed for 2s.
RoadC will be closed for 5s.
11:
RoadA will be open for 1s.
RoadB will be closed for 1s.
RoadC will be closed for 4s.
12:
RoadA will be closed for 6s.
RoadB will be open for 3s.
RoadC will be closed for 3s.
13:
RoadA will be closed for 5s.
RoadB will be open for 2s.
RoadC will be closed for 2s.
14:
RoadA will be closed for 4s.
RoadB will be open for 1s.
RoadC will be closed for 1s.
15:
RoadA will be closed for 3s.
RoadB will be closed for 6s.
RoadC will be open for 3s.
16:
RoadA will be closed for 2s.
RoadB will be closed for 5s.
RoadC will be open for 2s.
17:
RoadA will be closed for 1s.
RoadB will be closed for 4s.
RoadC will be open for 1s.
18:
RoadA will be open for 3s.
RoadB will be closed for 3s.
RoadC will be closed for 6s.
19:
RoadA will be open for 2s.
RoadB will be closed for 2s.
RoadC will be closed for 5s.
(hidden) 20:
RoadA will be open for 1s.
RoadB will be closed for 1s.
RoadC will be closed for 4s.

# RoadA deleted

(hidden) 21:
RoadB will be open for 3s.
RoadC will be closed for 3s.
(hidden) 22:
RoadB will be open for 2s.
RoadC will be closed for 2s.
23:
RoadB will be open for 1s.
RoadC will be closed for 1s.
24:
RoadB will be closed for 3s.
RoadC will be open for 3s.
25:
RoadB will be closed for 2s.
RoadC will be open for 2s.
26:
RoadB will be closed for 1s.
RoadC will be open for 1s.
27:
RoadB will be open for 3s.
RoadC will be closed for 3s.
28:
RoadB will be open for 2s.
RoadC will be closed for 2s.
29:
RoadB will be open for 1s.
RoadC will be closed for 1s.
(hidden) 30:
RoadB will be closed for 3s.
RoadC will be open for 3s.

# RoadD added

(hidden) 31:
RoadB will be closed for 5s.
RoadC will be open for 2s.
RoadD will be closed for 2s.
(hidden) 32:
RoadB will be closed for 4s.
RoadC will be open for 1s.
RoadD will be closed for 1s.
(hidden) 33:
RoadB will be closed for 3s.
RoadC will be closed for 6s.
RoadD will be open for 3s.

# RoadB deleted

(hidden) 34:
RoadC will be closed for 2s.
RoadD will be open for 2s.
(hidden) 35:
RoadC will be closed for 1s.
RoadD will be open for 1s.
(hidden) 36:
RoadC will be open for 3s.
RoadD will be closed for 3s.
(hidden) 37:
RoadC will be open for 2s.
RoadD will be closed for 2s.

# RoadE added

(hidden) 38:
RoadC will be open for 1s.
RoadD will be closed for 1s.
RoadE will be closed for 4s.
39:
RoadC will be closed for 6s.
RoadD will be open for 3s.
RoadE will be closed for 3s.
40:
RoadC will be closed for 5s.
RoadD will be open for 2s.
RoadE will be closed for 2s.
41:
RoadC will be closed for 4s.
RoadD will be open for 1s.
RoadE will be closed for 1s.
42:
RoadC will be closed for 3s.
RoadD will be closed for 6s.
RoadE will be open for 3s.
43:
RoadC will be closed for 2s.
RoadD will be closed for 5s.
RoadE will be open for 2s.
(hidden) 44:
RoadC will be closed for 1s.
RoadD will be closed for 4s.
RoadE will be open for 1s.

# RoadC deleted

(hidden) 45:
RoadD will be open for 3s.
RoadE will be closed for 3s.
(hidden) 46:
RoadD will be open for 2s.
RoadE will be closed for 2s.
(hidden) 47:
RoadD will be open for 1s.
RoadE will be closed for 1s.

# RoadD deleted

(hidden) 48:
RoadE will be open for 3s.
49:
RoadE will be open for 2s.
51:
RoadE will be open for 1s.
52:
RoadE will be open for 3s.
53:
RoadE will be open for 2s.
```
</details>
