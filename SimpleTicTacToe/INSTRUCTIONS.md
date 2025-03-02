# Simple Tic-Tac-Toe (Java)

See the [project page on Hyperskill](https://hyperskill.org/projects/48) for a thorough overview.


## Stage 1/5: Welcome to the battlefield!

### Description

**Tic-tac-toe** is a game known all over the world. Each country may have its own version of the name, sometimes the rules are different, but the essence of the game remains the same. Below are the main rules that may be familiar to you since childhood.

Tic-tac-toe is played by two players on a 3x3 grid. One of the players is 'X', and the other player is 'O'. X plays first, then O takes the next turn, and so on.

The players write 'X' and 'O' on a 3x3 field.

The first player that puts 3 X's or 3 O's in a straight line (including diagonals) wins the game.

### Objectives

Your first task in this project is to print the game grid in the console output. Use what you’ve learned about the `print()` function to print three lines, each containing three characters (X’s and O’s) to represent a game of tic-tac-toe where all fields of the grid have been filled in.

### Example

The example below shows how your output might look:
```text
X O X
O X O
X X O
```


## Stage 2/5: The user is the gamemaster

### Description

Our program should be able to display the grid at all stages of the game. Now we’re going to write a program that allows the user to enter a string representing the game state and correctly prints the 3x3 game grid based on this input. We’ll also add some boundaries around the game grid.

### Objectives

In this stage, you will write a program that:

1. Reads a string of 9 symbols from the input and displays them to the user in a 3x3 grid. The grid can contain only X, O and _ symbols.
2. Outputs a line of dashes `---------` above and below the grid, adds a pipe `|` symbol to the beginning and end of each line of the grid, and adds a space between all characters in the grid.

### Examples

The greater-than symbol followed by a space (`>`) represents the user input. Note that it's not part of the input.

**Example 1:**
```text
> O_OXXO_XX
---------
| O _ O |
| X X O |
| _ X X |
---------
```

**Example 2:**
```text
> OXO__X_OX
---------
| O X O |
| _ _ X |
| _ O X |
---------
```

**Example 3:**
```text
> _XO__X___
---------
| _ X O |
| _ _ X |
| _ _ _ |
---------
```


## Stage 3/5: What's up on the field?

### Description

In this stage, we’re going to analyze the game state to determine if either player has already won the game or it is still ongoing, if the game is a draw, or if the user has entered an impossible game state (two winners, or with one player having made too many moves).

### Objectives

In this stage, your program should:

1. Take a string entered by the user and print the game grid as in the previous stage.
2. Analyze the game state and print the result. Possible states:
    - `Game not finished` when neither side has three in a row but the grid still has empty cells.
    - `Draw` when no side has a three in a row and the grid has no empty cells.
    - `X wins` when the grid has three X’s in a row (including diagonals).
    - `O wins` when the grid has three O’s in a row (including diagonals).
    - `Impossible` when the grid has three X’s in a row as well as three O’s in a row, or there are a lot more X's than O's or vice versa (the difference should be 1 or 0; if the difference is 2 or more, then the game state is impossible). 

In this stage, we will assume that either X or O can start the game.

You can choose whether to use a space or underscore `_` to print empty cells.

### Examples

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:**
```text
> XXXOO__O_
---------
| X X X |
| O O _ |
| _ O _ |
---------
X wins
```

**Example 2:**
```text
> XOXOXOXXO
---------
| X O X |
| O X O |
| X X O |
---------
X wins
```

**Example 3:**
```text
> XOOOXOXXO
---------
| X O O |
| O X O |
| X X O |
---------
O wins
```

**Example 4:**
```text
> XOXOOXXXO
---------
| X O X |
| O O X |
| X X O |
---------
Draw
```

**Example 5:**
```text
> XO_OOX_X_
---------
| X O   |
| O O X |
|   X   |
---------
Game not finished
```

**Example 6:**
```text
> XO_XO_XOX
---------
| X O _ |
| X O _ |
| X O X |
---------
Impossible
```

**Example 7:**
```text
> _O_X__X_X
---------
|   O   |
| X     |
| X   X |
---------
Impossible
```

**Example 8:**
```text
> _OOOO_X_X
---------
|   O O |
| O O   |
| X   X |
---------
Impossible
```


## Stage 4/5: First move!

### Description

It's time to make our game interactive! Now we're going to add the ability for a user to make a move. To do this, we need to divide the grid into cells. Suppose the top left cell has the coordinates (1, 1) and the bottom right cell has the coordinates (3, 3):
```text
(1, 1) (1, 2) (1, 3)
(2, 1) (2, 2) (2, 3)
(3, 1) (3, 2) (3, 3)
```

The program should ask the user to enter the coordinates of the cell where they want to make a move.

In this stage, the user plays as X, not O. Keep in mind that the first coordinate goes from top to bottom and the second coordinate goes from left to right. The coordinates can include the numbers 1, 2, or 3.

What happens if the user enters incorrect coordinates? The user could enter symbols instead of numbers, or enter coordinates representing occupied cells or cells that aren't even on the grid. You need to check the user's input and catch possible exceptions.

### Objectives

The program should work as follows:

1. Get the initial 3x3 grid from the input as in the previous stages. Here the user should input 9 symbols representing the field, for example, `_XXOO_OX_`.
2. Output this 3x3 grid as in the previous stages.
3. Prompt the user to make a move. The user should input 2 coordinate numbers that represent the cell where they want to place their X, for example, `1 1`.
4. Analyze user input. If the input is incorrect, inform the user why their input is wrong:
    - Print `This cell is occupied! Choose another one!` if the cell is not empty.
    - Print `You should enter numbers!` if the user enters non-numeric symbols in the coordinates input.
    - Print `Coordinates should be from 1 to 3!` if the user enters coordinates outside the game grid.
    - Keep prompting the user to enter the coordinates until the user input is valid.
5. If the input is correct, update the grid to include the user's move and print the updated grid to the console.

To summarize, you need to output the field 2 times: once before the user's move (based on the first line of input) and once after the user has entered valid coordinates (then you need to update the grid to include that move).

**IMPORTANT:** Do not delete the code you wrote in the previous stage! You will need it in the final stage of this project, so now you can just comment out a part of it.

### Examples

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:**
```text
> X_X_O____
---------
| X   X |
|   O   |
|       |
---------
> 3 1
---------
| X   X |
|   O   |
| X     |
---------
```

**Example 2:**
```text
> _XXOO_OX_
---------
|   X X |
| O O   |
| O X   |
---------
> 1 1
---------
| X X X |
| O O   |
| O X   |
---------
```

**Example 3:**
```text
> _XXOO_OX_
---------
|   X X |
| O O   |
| O X   |
---------
> 3 3
---------
|   X X |
| O O   |
| O X X |
---------
```

**Example 4:**
```text
> _XXOO_OX_
---------
|   X X |
| O O   |
| O X   |
---------
> 2 3
---------
|   X X |
| O O X |
| O X   |
---------
```

**Example 5:**
```text
> _XXOO_OX_
---------
|   X X |
| O O   |
| O X   |
---------
> 3 1
This cell is occupied! Choose another one!
> 1 1
---------
| X X X |
| O O   |
| O X   |
---------
```

**Example 6:**
```text
> _XXOO_OX_
---------
|   X X |
| O O   |
| O X   |
---------
> one
You should enter numbers!
> one one
You should enter numbers!
> 1 1
---------
| X X X |
| O O   |
| O X   |
---------
```

**Example 7:**
```text
> _XXOO_OX_
---------
|   X X |
| O O   |
| O X   |
---------
> 4 1
Coordinates should be from 1 to 3!
> 1 4
Coordinates should be from 1 to 3!
> 1 1
---------
| X X X |
| O O   |
| O X   |
---------
```
