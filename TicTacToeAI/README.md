# Tic-Tac-Toe with AI

## Project description

Everybody remembers this paper-and-pencil game from childhood: Tic-Tac-Toe, also known as Noughts and crosses or Xs and Os. A single mistake usually costs you the game, but thankfully it is simple enough that most players discover the best strategy quickly. Let’s program Tic-Tac-Toe and get playing!

[View more](https://hyperskill.org/projects/81)


## Stage 1/5: Initial setup

### Description

In this project, you'll write a game called Tic-Tac-Toe that you can play against your computer. The computer will have three levels of difficulty — easy, medium, and hard.

To begin with, let's write a program that knows how to work with coordinates and determine the state of the game.

The top-left cell will have the coordinates (1, 1) and the bottom-right cell will have the coordinates (3, 3), as shown in this table:
```text
(1, 1) (1, 2) (1, 3)
(2, 1) (2, 2) (2, 3)
(3, 1) (3, 2) (3, 3)
```

The program should ask the user to enter the coordinates of the cell where they want to make a move.

Keep in mind that the first coordinate goes from top to bottom, and the second coordinate goes from left to right. Also, notice that coordinates start with 1 and can be 1, 2, or 3.

But what if the user attempts to enter invalid coordinates? This could happen if they try to:

- enter letters or symbols instead of numbers
- enter the coordinates of an already occupied cell
- enter coordinates that are outside the table's range (e.g. ..., -1, 0, 4, 5, 6, ...)

Your program needs to prevent these things from happening by checking the user's input and catching possible exceptions.

### Objectives

The program should work in the following way:

1. Ask the user to provide the initial state of the 3x3 table with the first input line. This must include nine symbols that can be `X`, `O` or `_` (the latter represents an empty cell).
2. Output the specified 3x3 table before the user makes a move.
3. Request that the user enters the coordinates of the move they wish to make.
4. The user then inputs two numbers representing the cell in which they wish to place their `X` or `O`. The game always starts with `X`, so the user's move should be made with this symbol if there are an equal number of `X`'s and `O`'s in the table. If the table contains an extra `X`, the move should be made with `O`.
5. Analyze the user input and show messages in the following situations:
    - `This cell is occupied!` Choose another one! — if the cell is already occupied (i.e. not empty);
    - `You should enter numbers!` — if the user tries to enter letters or symbols instead of numbers;
    - `Coordinates should be from 1 to 3!` — if the user attempts to enter coordinates outside of the table's range.
6. Display the table again, now updated with the user's most recent move.
7. After displaying the updated table, output the current state of the game based on the table.

The possible states are:

- `Game not finished` — when no side has three in a row, but the table still has empty cells;
- `Draw` — when no side has three in a row, and the table is complete;
- `X wins` — when there are three `X`'s in a row (up, down, across, or diagonally);
- `O wins` — when there are three `O`'s in a row (up, down, across, or diagonally).

If the user provides invalid coordinates, the program should repeat the request until valid numbers representing an empty cell on the table are supplied. Ensure that the program outputs the table only twice: once before the move and once after the user makes a valid move.

### Examples

The examples below show how your program should work.
The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:**
```text
Enter the cells: > _XXOO_OX_
---------
|   X X |
| O O   |
| O X   |
---------
Enter the coordinates: > 3 1
This cell is occupied! Choose another one!
Enter the coordinates: > one
You should enter numbers!
Enter the coordinates: > one three
You should enter numbers!
Enter the coordinates: > 4 1
Coordinates should be from 1 to 3!
Enter the coordinates: > 1 1
---------
| X X X |
| O O   |
| O X   |
---------
X wins
```

**Example 2:**
```text
Enter the cells: > XX_XOXOO_
---------
| X X   |
| X O X |
| O O   |
---------
Enter the coordinates: > 3 3
---------
| X X   |
| X O X |
| O O O |
---------
O wins
```

**Example 3:**
```text
Enter the cells: > XX_XOXOO_
---------
| X X   |
| X O X |
| O O   |
---------
Enter the coordinates: > 1 3
---------
| X X O |
| X O X |
| O O   |
---------
O wins
```

**Example 4:**
```text
Enter the cells: > OX_XOOOXX
---------
| O X   |
| X O O |
| O X X |
---------
Enter the coordinates: > 1 3
---------
| O X X |
| X O O |
| O X X |
---------
Draw
```

**Example 5:**
```text
Enter the cells: >  _XO_OX___
---------
|   X O |
|   O X |
|       |
---------
Enter the coordinates: > 3 1
---------
|   X O |
|   O X |
| X     |
---------
Game not finished
```


## Stage 2/5: Easy does it

### Description

Now it's time to make a working game, so let's create our first opponent! In this version of the program, the user will be playing as `X`, and the computer will be playing as `O` at `easy` level. This will be our first small step towards creating the AI!

Let's design it so that at this level the computer makes random moves. This should be perfect for people who have never played the game before!

If you want, you could make the game even simpler by including a difficulty level where the computer never wins. Feel free to create this along with the `easy` level if you like, but it won't be tested.

### Objectives

In this stage, you should implement the following:

1. Display an empty table when the program starts.
2. The user plays first as `X`, and the program should ask the user to enter cell coordinates.
3. Next, the computer makes its move as `O`, and the players then move in turn until someone wins or the game results in a draw.
4. Print the final outcome at the very end of the game.

The possible states are:

- `Draw` — when no side has three in a row, and the table is complete;
- `X wins` — when there are three `X`'s in a row (up, down, across, or diagonally);
- `O wins` — when there are three `O`'s in a row (up, down, across, or diagonally).

Like in the previous stage, if the user provides invalid coordinates, the program should repeat the request until valid numbers representing an empty cell on the table are supplied.

### Example

The example below shows how your program should work.
The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.
```text
---------
|       |
|       |
|       |
---------
Enter the coordinates: > 2 2
---------
|       |
|   X   |
|       |
---------
Making move level "easy"
---------
| O     |
|   X   |
|       |
---------
Enter the coordinates: > 3 3
---------
| O     |
|   X   |
|     X |
---------
Making move level "easy"
---------
| O     |
| O X   |
|     X |
---------
Enter the coordinates: > 3 1
---------
| O     |
| O X   |
| X   X |
---------
Making move level "easy"
---------
| O     |
| O X O |
| X   X |
---------
Enter the coordinates: > 3 2
---------
| O     |
| O X O |
| X X X |
---------
X wins
```


## Stage 3/5: Watch 'em fight

### Description

It's time to make things more interesting by adding some game variations. What if you want to play against a friend instead of the AI? How about watching a match between two AIs if you get tired of playing? Additionally, the user should have the option to choose whether they go first or second when playing against the AI.

At the end of each match (after the result is displayed), the user should also have the option to quit the game.

### Objectives

Your tasks for this stage are:

1. Write a menu loop, which can interpret two commands:
    - `start`: Begins a new game with the specified players for `X` and `O`.
    - `exit`: Ends the program.
2. Implement the `start` command to take two parameters specifying who will play as `X` and who will play as `O`. The possible values for each parameter are:
    - `user`: A human player.
    - `easy`: An AI player at the easy difficulty level. In later stages, you will add the `medium` and `hard` AI difficulty levels.
The first parameter determines who will play as `X`. The second parameter determines who will play as `O`. For example:
    - `start easy easy`: Both players are AIs at the "easy" difficulty level.
    - `start easy user`: The AI (easy) plays as `X`, and the user plays as `O`.
    - `start user easy`: The user plays as `X`, and the AI (easy) plays as `O`.
    - `start user user`: Both players are human.

3. **Handle incorrect input**: If the user enters invalid parameters, display the message `Bad parameters!` and return to the menu.
4. **Add the `exit` command**: This command should terminate the program immediately.

Like the previous stage, the possible states are:

- `Draw` — when no side has three in a row, and the table is complete;
- `X wins` — when there are three `X`'s in a row (up, down, across, or diagonally);
- `O wins` — when there are three `O`'s in a row (up, down, across, or diagonally).

### Example

The example below shows how your program should work.
The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.
```text
Input command: > start
Bad parameters!
Input command: > start easy
Bad parameters!
Input command: > start easy easy
---------
|       |
|       |
|       |
---------
Making move level "easy"
---------
|       |
|     X |
|       |
---------
Making move level "easy"
---------
|       |
| O   X |
|       |
---------
Making move level "easy"
---------
|       |
| O   X |
|     X |
---------
Making move level "easy"
---------
|       |
| O   X |
|   O X |
---------
Making move level "easy"
---------
|       |
| O X X |
|   O X |
---------
Making move level "easy"
---------
|     O |
| O X X |
|   O X |
---------
Making move level "easy"
---------
| X   O |
| O X X |
|   O X |
---------
X wins

Input command: > start easy user
---------
|       |
|       |
|       |
---------
Making move level "easy"
---------
|       |
|       |
|     X |
---------
Enter the coordinates: > 2 2
---------
|       |
|   O   |
|     X |
---------
Making move level "easy"
---------
|   X   |
|   O   |
|     X |
---------
Enter the coordinates: > 3 1
---------
|   X   |
|   O   |
| O   X |
---------
Making move level "easy"
---------
|   X X |
|   O   |
| O   X |
---------
Enter the coordinates: > 2 3
---------
|   X X |
|   O O |
| O   X |
---------
Making move level "easy"
---------
| X X X |
|   O O |
| O   X |
---------
X wins

Input command: > start user user
---------
|       |
|       |
|       |
---------
Enter the coordinates: > 3 1
---------
|       |
|       |
| X     |
---------
Enter the coordinates: > 2 2
---------
|       |
|   O   |
| X     |
---------
Enter the coordinates: > 2 1
---------
|       |
| X O   |
| X     |
---------
Enter the coordinates: > 3 2
---------
|       |
| X O   |
| X O   |
---------
Enter the coordinates: > 1 1
---------
| X     |
| X O   |
| X O   |
---------
X wins

Input command: > exit
```


## Stage 4/5: Signs of intelligence

### Description

Let's enhance our AI by adding a `medium` difficulty level. This AI will be more challenging to beat than the `easy` difficulty level, as it incorporates basic awareness of the game state. While its initial moves are still random, it follows a strategic set of rules to play more effectively.

When the AI plays at `medium` level, it focuses on winning whenever possible because of its first rule, or blocking the opponent's winning moves because of its second rule. This makes the game more engaging and competitive.

You can see these rules below.

### Objectives

When the AI is playing at `medium` difficulty level, it makes moves based on the following rules:

1. **Winning move:** If the AI has two in a row and can win with one more move, it takes that move.
2. **Blocking move:** If the opponent has two in a row and can win with one more move, the AI blocks it.
3. **Fallback move:** If neither of the above applies, the AI makes a random move.

You should add a `medium` parameter so that you can play against this level. It should also be possible for AIs at different levels (`easy` and `medium`) to play against each other!

Like the previous stage, the possible states are:

- `Draw` — when no side has three in a row, and the table is complete;
- `X wins` — when there are three `X`'s in a row (up, down, across, or diagonally);
- `O wins` — when there are three `O`'s in a row (up, down, across, or diagonally).

Also, ensure that the program handles invalid input gracefully. For example, if the `start` command is given without valid parameters, display the message `Bad parameters!`

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.
```text
Input command: > start user medium
---------
|       |
|       |
|       |
---------
Enter the coordinates: > 2 2
---------
|       |
|   X   |
|       |
---------
Making move level "medium"
---------
|       |
|   X   |
| O     |
---------
Enter the coordinates: > 1 1
---------
| X     |
|   X   |
| O     |
---------
Making move level "medium"
---------
| X     |
|   X   |
| O   O |
---------
Enter the coordinates: > 3 3
---------
| X     |
|   X   |
| O X O |
---------
Making move level "medium"
---------
| X O   |
|   X   |
| O X O |
---------
Enter the coordinates: > 2 1
---------
| X O   |
| X X   |
| O X O |
---------
Making move level "medium"
---------
| X O   |
| X X O |
| O X O |
---------
Enter the coordinates: > 1 3
---------
| X O X |
| X X O |
| O X O |
---------
Draw

Input command: > start medium user
---------
|       |
|       |
|       |
---------
Making move level "medium"
---------
|       |
|       |
|   X   |
---------
Enter the coordinates: > 2 2
---------
|       |
|   O   |
|   X   |
---------
Making move level "medium"
---------
|       |
|   O   |
| X X   |
---------
Enter the coordinates: > 3 3
---------
|       |
|   O   |
| X X O |
---------
Making move level "medium"
---------
| X     |
|   O   |
| X X O |
---------
Enter the coordinates: > 2 1
---------
| X     |
| O O   |
| X X O |
---------
Making move level "medium"
---------
| X     |
| O O X |
| X X O |
---------
Enter the coordinates: > 1 3
---------
| X   O |
| O O X |
| X X O |
---------
Making move level "medium"
---------
| X X O |
| O O X |
| X X O |
---------
Draw

Input command: > exit
```


## Stage 5/5: An undefeated champion

### Description

Congratulations, you've almost reached the finish line! In this stage, you'll transform the AI into a formidable opponent by implementing the `hard` difficulty level.

Unlike the `medium` difficultly level, where the AI just looks one move ahead to see an immediate win or prevent an immediate loss, the `hard` difficulty level uses a more advanced strategy. At `hard` difficulty level, AI can look two moves ahead, three moves ahead, and even further. That is, it evaluates the entire game tree by simulating all possible moves, looking multiple steps ahead. It can calculate all possible moves that might be played during the game, and choose the best move based on the assumption that its opponent will also play perfectly. So, it doesn't rely on the mistakes of its opponent and plays the game flawlessly from start to finish, regardless of the opponent's skill level!

This advanced decision-making is powered by the [minimax algorithm](https://en.wikipedia.org/wiki/Minimax). It's a recursive brute-force algorithm that maximizes the AI's advantage while minimizing the opponent's chances of winning. Minimax is not just for Tic-Tac-Toe. You can use it with any other game where two players make alternate moves, such as chess.

### Objectives

In this last stage, you need to:

- Implement the `hard` difficulty level using the minimax algorithm.
- Add the `hard` parameter to enable games against this level.
- Ensure that the AI plays optimally, making it a challenging opponent.

Like the previous stage, the possible states are:

- `Draw` — when no side has three in a row, and the table is complete;
- `X wins` — when there are three `X`'s in a row (up, down, across, or diagonally);
- `O wins` — when there are three `O`'s in a row (up, down, across, or diagonally).

Also, ensure that the program handles invalid input gracefully. For example, if the `start` command is given without valid parameters, display the message `Bad parameters!`

The minimax algorithm can be conceptually tricky, especially for beginners, as it involves recursion. To help you understand it, here are some recommended resources:

- Video-based explanations:
    - ["Tic Tac Toe AI with Minimax Algorithm"](https://www.youtube.com/watch?v=trKjYdBASyQ) by The Coding Train
    - ["Algorithms Explained – minimax and alpha-beta pruning"](https://www.youtube.com/watch?v=l-hh51ncgDI) by Sebastian Lague
    - ["Mega-R3. Games, Minimax, Alpha-Beta"](https://www.youtube.com/watch?v=hM2EAvMkhtk) from MIT OpenCourseWare
- Text-based explanations:
    - ["How to make your Tic Tac Toe game unbeatable by using the minimax algorithm"](https://www.freecodecamp.org/news/how-to-make-your-tic-tac-toe-game-unbeatable-by-using-the-minimax-algorithm-9d690bad4b37/) on freeCodeCamp.org
    - ["Case Study on Tic-Tac-Toe Part 2: With AI"](https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaGame_TicTacToe_AI.html) from Nanyang Technological University
    - ["Tic Tac Toe - Creating Unbeatable AI"](https://gsurma.medium.com/tic-tac-toe-creating-unbeatable-ai-with-minimax-algorithm-8af9e52c1e7d) on Medium

Feel free to explore these or other resources to find the explanation that resonates best with you. Developing this skill of seeking and understanding resources is invaluable as you progress in your software development career.

### Example

The example below shows how your program should work.
The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.
```text
Input command: > start hard user
---------
|       |
|       |
|       |
---------
Making move level "hard"
---------
|       |
| X     |
|       |
---------
Enter the coordinates: > 2 2
---------
|       |
| X O   |
|       |
---------
Making move level "hard"
---------
|   X   |
| X O   |
|       |
---------
Enter the coordinates: > 3 2
---------
|   X   |
| X O   |
|   O   |
---------
Making move level "hard"
---------
| X X   |
| X O   |
|   O   |
---------
Enter the coordinates: > 3 1
---------
| X X   |
| X O   |
| O O   |
---------
Making move level "hard"
---------
| X X X |
| X O   |
| O O   |
---------
X wins

Input command: > exit
```

