# Maze Runner (Java)

## Project description

You will practice concepts frequently tested in technical interviews at top tech companies. Mazes are amazing: keep running, find the exit if you can! If you are not too happy about getting stuck in the actual maze, you can entertain yourself with a virtual one. In this project, you will write a program that generates mazes and looks for a way out.

[View more](https://hyperskill.org/projects/47)


## Stage 1/4: Display a maze

### Description

Implement the maze as a two-dimensional array of integers. If the value of an element is 1, it is a wall. If the value is 0, it is a passage.

Your maze must be 10x10. Other rules are as follows:

1. There should be walls around the maze, except for two cells: entrance and exit.
2. Any empty cell should be accessible from the entrance or exit of the maze. It is impossible to move diagonally, only vertically or horizontally.
3. There has to be a path from the entrance to the exit. It doesn't matter what is considered an entrance and what is an exit as they are interchangeable.
4. It is not allowed to create blocks in the maze consisting only of walls, such as 3x3 ones.

To print the array, follow these two rules:

- to display a pass, use two space characters;
- to display a wall, use two block characters in a row (the same): █ (U+2588).

You can print a wall like this: `System.out.print("\u2588\u2588")`.

Remember that the maze is only visible if you have a monospaced font! Otherwise, the space symbol will be quite small in width.

At this stage, it does not matter what maze you display. The program may always output the same prepared maze or one of a set of prepared mazes.

### Example

After starting, your program should output a maze and then stop.
```text
████████████████████
    ██  ██  ██    ██
██  ██      ██  ████
██      ██████
██  ██          ████
██  ██  ██████  ████
██  ██  ██      ████
██  ██  ██████  ████
██  ██      ██    ██
████████████████████
```


## Stage 2/4: Maze generator

### Description

In this stage, you will develop an algorithm for creating a maze.

Don't forget the rules of the correct maze:

1. There should be walls around the maze, except for two cells: entrance and exit.
2. Any empty cell must be accessible from the entrance or exit of the maze. It is not possible to walk along the maze diagonally, only vertically and horizontally.
3. There's got to be a path from the entrance to the exit. It doesn't matter what is considered an entrance and what is an exit as they are interchangeable.
4. The maze should not contain 3x3 blocks consisting of walls only. Try to fill the entire maze area with pathways.

There is a very good algorithm for building a maze, based on the construction of a minimal spanning tree. You can [watch the video](https://www.youtube.com/watch?v=cQVH4gcb3O4) on how this algorithm works.

It should be possible to specify the size of the maze. After a user enters the size, the program must print a maze and then stop.

Break your program down into a set of methods to make it easy to understand and add to or edit later.

> [!NOTE]
> Starting from this stage, your program must generate a random maze each time.

### Examples

After starting, your program should ask a user to enter the size of the maze, then output a generated maze, and then stop. Here is a simple example of a maze. Notice that the first number is height and the second number is width.

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:**
```text
Please, enter the size of a maze
>7 9
██████████████████
████      ██  ████
████████  ██  ████
      ██      ████
████  ██  ██
████      ██  ████
██████████████████
```

**Example 2:**
```text
Please, enter the size of a maze
>15 35
██████████████████████████████████████████████████████████████████████
        ██  ██                  ██                  ██              ██
██████  ██  ██████████████████  ██████████████████  ██████████  ██████
██      ██  ██                              ██      ██  ██  ██
██████  ██  ██████████  ██  ██  ██████████  ██  ██  ██  ██  ██  ██████
██  ██  ██      ██      ██  ██  ██  ██          ██          ██      ██
██  ██  ██  ██  ██████████████████  ██████  ██████████████  ██████  ██
██          ██  ██      ██  ██              ██                  ██  ██
██████  ██  ██████████  ██  ██████████  ██████████████████████  ██  ██
██  ██  ██  ██              ██      ██  ██              ██      ██  ██
██  ██  ██  ██████████  ██████████  ██████████████  ██  ██  ██████  ██
██      ██      ██  ██  ██          ██          ██  ██              ██
██  ██████  ██████  ██  ██  ██  ██  ██████████  ██  ██████  ██  ██  ██
██  ██                      ██  ██                  ██      ██  ██  ██
██████████████████████████████████████████████████████████████████████
```
