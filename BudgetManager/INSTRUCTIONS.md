# Budget Manager

See the [project page on Hyperskill](https://hyperskill.org/projects/76) for a thorough overview.


## Stage 1/5: Count my money

### Description

First, let’s implement the counting of your purchases. It’s much easier to analyze when your expenses are presented as a list. Read data from the console and at the end show the list of all purchases and its total amount.
It should be displayed as follows: `Total: $23.00`

Your program should process every line the user inputs. To end the input, the user should type [End-of-file](https://en.wikipedia.org/wiki/End-of-file) symbol that tells your operating system that no more input will be provided. It's `Ctrl+D` on Linux and Mac and `Ctrl+Z` on Windows. Don't be scared: you don’t have to implement it yourself, this is a special built-in hot-key that gives the command to stop input. See for yourself!

As a programmer, you can implement `.hasNext()` or `.hasNextLine()` methods of the `Scanner` class in your program to determine when there is no input available.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Notice that it's not part of the input.
```text
> Almond 250gm $35.43
> LEGO DUPLO Town Farm Animals $10.10
> Sensodyne Pronamel Toothpaste $19.74
> Hershey's milk chocolate bars $8.54
> Gildan LT $8.61
Almond 250gm $35.43
LEGO DUPLO Town Farm Animals $10.10
Sensodyne Pronamel Toothpaste $19.74
Hershey's milk chocolate bars $8.54
Gildan LT $8.61

Total: $82.42
```


## Stage 2/5: Make a menu

### Description

Let's make your application more convenient. Only counting the expenses is a little bit sad, right?

To make your application flexible and functional, add a menu that consists of 4 items.
1. **Add income** — We must track both our expenses and our income. When this item is selected, the program should ask to enter the amount of income.
2. **Add purchase** — This item should add a purchase to the list. You also need to subtract the price of the purchase from the income.
3. **Show the list of purchases** — This menu item should display a list of all expenses in the order they were made.
4. **Balance** — Show the balance which is equal to total income subtracted by total expenses during purchase.
4. **Exit** — Print `Bye!` and exit the program. Make this item under number **0**, not number 5.

Notice, that the amount of remaining money cannot be negative. In such cases, make the balance equal to $0.

When displaying the price or the total amount, print 2 numbers after the point. For example: `$14.20`. Upcoming stages will also follow this rule.

*Warning with regards to use of `.nextInt()`*:
Keep in mind that if you're using `.nextInt()` to scan integer input for your menu actions, then you must also handle the corresponding white space become receiving the next string input. For example, after selecting menu option 2, a user will press enter and then type the name of the purchase. If you had used `.nextInt()` to handle the menu selection, the enter button pressed by the user should also be read before the user can input the purchase name. This means you need to use an addition `.next()` after you use `.nextInt()`.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Notice that it's not part of the input.
```text
Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
0) Exit
> 1

Enter income:
> 1000
Income was added!

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
0) Exit
> 4

Balance: $1000.00

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
0) Exit
> 3

The purchase list is empty

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
0) Exit
> 2

Enter purchase name:
>Red Fuji Apple
Enter its price:
> 5.99
Purchase was added!

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
0) Exit
> 3

Red Fuji Apple $5.99
Total sum: $5.99

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
0) Exit
> 0

Bye!
```


## Stage 3/5: Oh the things you can buy

### Description

To better control the expenses, we need to categorize our purchases. It helps to see how exactly your budget is distributed: you may be actually quite surprised!

Implement a function that assigns a purchase to a specific category.

The program should have the following categories:

- **Food**
- **Clothes**
- **Entertainment**
- **Other**

The function allows you to output the shopping list by type. After selecting the action of showing the list of expenses, offer to show either a certain category or a general list. At the end of each list, print the total sum of purchases that are on the list.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Notice that it's not part of the input.
```text
Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
0) Exit
> 3

The purchase list is empty!

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
0) Exit
> 2

Choose the type of purchase
1) Food
2) Clothes
3) Entertainment
4) Other
5) Back
> 1

Enter purchase name:
> Milk
Enter its price:
> 3.5
Purchase was added!

Choose the type of purchase
1) Food
2) Clothes
3) Entertainment
4) Other
5) Back
> 5

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
0) Exit
> 3

Choose the type of purchases
1) Food
2) Clothes
3) Entertainment
4) Other
5) All
6) Back
> 4

Other:
The purchase list is empty!

Choose the type of purchases
1) Food
2) Clothes
3) Entertainment
4) Other
5) All
6) Back
> 1

Food:
Milk $3.50
Total sum: $3.50

Choose the type of purchases
1) Food
2) Clothes
3) Entertainment
4) Other
5) All
6) Back
> 5

All:
Milk $3.50
Total sum: $3.50

Choose the type of purchases
1) Food
2) Clothes
3) Entertainment
4) Other
5) All
6) Back
> 6

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
0) Exit
> 0

Bye!
```


## Stage 4/5: Memorable purchases

### Description

What's the point of counting the money if the results are lost and forgotten once you close the program? To allow for some long-term budget planning, we need to save purchases to file. Add items Save and Load to the menu.

**Save** should save all purchases to the file.
**Load** should load all purchases from the file.

Use the `purchases.txt` file to store purchases.

### Examples

The greater-than symbol followed by a space (`> `) represents the user input. Notice that it's not part of the input.

**Example 1:**
```text
Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
5) Save
6) Load
0) Exit
> 1

Enter income:
> 1000
Income was added!

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
5) Save
6) Load
0) Exit
> 2

Choose the type of purchase
1) Food
2) Clothes
3) Entertainment
4) Other
5) Back
> 1

Enter purchase name:
> Almond 250g
Enter its price:
> 35.43
Purchase was added!

Choose the type of purchase
1) Food
2) Clothes
3) Entertainment
4) Other
5) Back
> 5

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
5) Save
6) Load
0) Exit
> 5

Purchases were saved!

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
5) Save
6) Load
0) Exit
> 0

Bye!
```

**Example 2:**
```text
Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
5) Save
6) Load
0) Exit
> 6

Purchases were loaded!

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
5) Save
6) Load
0) Exit
> 3

Choose the type of purchases
1) Food
2) Clothes
3) Entertainment
4) Other
5) All
6) Back
> 1

Food:
Almond 250g $35.43
Total sum: $35.43

Choose the type of purchases
1) Food
2) Clothes
3) Entertainment
4) Other
5) All
6) Back
> 5

All:
Almond 250g $35.43
Total sum: $35.43

Choose the type of purchases
1) Food
2) Clothes
3) Entertainment
4) Other
5) All
6) Back
> 6

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
5) Save
6) Load
0) Exit
> 4

Balance: $964.57

Choose your action:
1) Add income
2) Add purchase
3) Show list of purchases
4) Balance
5) Save
6) Load
0) Exit
> 0

Bye!
```
