# Cinema Room Manager

See the [project page on Hyperskill](https://hyperskill.org/projects/133) for a thorough overview.


## Stage 1/5: Print seats

### Description

There are many enjoyable activities on this funny little planet Earth, and still, the happiest, simplest, and most fulfilling one is probably going to the movies. Going with friends, going with loved ones, experiencing a whole new adventure from the safety of a cinema seat, surrounded by like-minded fellow travelers.

As a beginner developer, you can contribute to creating this wonderful cinema experience. Your good friends asked you to help them create an application for a cinema theatre where people can get tickets, reserve seats, and enjoy their movie night.

### Objectives

There is not a lot of space in our new cinema theatre, so you need to take into account the restrictions on the number of seats. Your friends said that the room would fit 7 rows of 8 seats. Your task is to help them visualize the seating arrangement by printing the scheme to the console.

Your output should be like in the example below and should contain 9 lines, the title "Cinema:" - 1 line and room size - 8 lines.

### Example

```text
Cinema:
  1 2 3 4 5 6 7 8
1 S S S S S S S S
2 S S S S S S S S
3 S S S S S S S S
4 S S S S S S S S
5 S S S S S S S S
6 S S S S S S S S
7 S S S S S S S S
```


## Stage 2/5: Calculate the profit

### Description

Good job: our friends really liked your program! Now they want to expand their theater and add screen rooms with more seats. However, this is expensive, so they want to make sure this will pay off. Help them calculate the profit from all the sold tickets depending on the number of available seats.

### Objectives

In this stage, you need to read two positive integer numbers from the input: the number of rows and the number of seats in each row. The ticket price is determined by the following rules:

- If the total number of seats in the screen room is not more than 60, then the price of each ticket is 10 dollars.
- In a larger room, the tickets are 10 dollars for the front half of the rows and 8 dollars for the back half. Please note that the number of rows can be odd, for example, 9 rows. In this case, the first half is the first 4 rows, and the second half is the other 5 rows.

Calculate the profit from the sold tickets depending on the number of seats and print the result as shown in the examples below. After that, your program should stop. Note that in this project, the number of rows and seats won't be greater than 9.

### Examples

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1**

```text
Enter the number of rows:
> 4
Enter the number of seats in each row:
> 5
Total income:
$200
```

**Example 2**

```text
Enter the number of rows:
> 8
Enter the number of seats in each row:
> 9
Total income:
$648
```

**Example 3**

```text
Enter the number of rows:
> 9
Enter the number of seats in each row:
> 7
Total income:
$560
```


## Stage 3/5: Set the ticket price

### Set the ticket price
Description

When choosing a ticket you are guided not only by your space preference but also by your finances. Let's implement the opportunity to check the ticket price and see the reserved seat.
Objectives

Read two positive integer numbers that represent the number of rows and seats in each row and print the seating arrangement like in the first stage. Then, read two integer numbers from the input: a row number and a seat number in that row. These numbers represent the coordinates of the seat according to which the program should print the ticket price. The ticket price is determined by the same rules as the previous stage:

- If the total number of seats in the screen room is not more than 60, then the price of each ticket is 10 dollars.
- In a larger room, the tickets are 10 dollars for the front half of the rows and 8 dollars for the back half. Please note that the number of rows can be odd, for example, 9 rows. In this case, the first half is the first 4 rows, and the second half is the last 5 rows.

After that, the program should print out all the seats in the screen room as shown in the example and mark the chosen seat by the `B` symbol. Finally, it should print the ticket price and stop. Note that in this project, the number of rows and seats won't be greater than 9.
Examples

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1**
```text
Enter the number of rows:
> 7
Enter the number of seats in each row:
> 8

Cinema:
  1 2 3 4 5 6 7 8
1 S S S S S S S S
2 S S S S S S S S
3 S S S S S S S S
4 S S S S S S S S
5 S S S S S S S S
6 S S S S S S S S
7 S S S S S S S S

Enter a row number:
> 3
Enter a seat number in that row:
> 6

Ticket price: $10

Cinema:
  1 2 3 4 5 6 7 8
1 S S S S S S S S
2 S S S S S S S S
3 S S S S S B S S
4 S S S S S S S S
5 S S S S S S S S
6 S S S S S S S S
7 S S S S S S S S
```

**Example 2**
```text
Enter the number of rows:
> 8
Enter the number of seats in each row:
> 9

Cinema:
  1 2 3 4 5 6 7 8 9
1 S S S S S S S S S
2 S S S S S S S S S
3 S S S S S S S S S
4 S S S S S S S S S
5 S S S S S S S S S
6 S S S S S S S S S
7 S S S S S S S S S
8 S S S S S S S S S

Enter a row number:
> 6
Enter a seat number in that row:
> 5

Ticket price: $8

Cinema:
  1 2 3 4 5 6 7 8 9
1 S S S S S S S S S
2 S S S S S S S S S
3 S S S S S S S S S
4 S S S S S S S S S
5 S S S S S S S S S
6 S S S S B S S S S
7 S S S S S S S S S
8 S S S S S S S S S
```
