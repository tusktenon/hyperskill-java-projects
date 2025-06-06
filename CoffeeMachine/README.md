# Coffee Machine Simulator with Java

## Project description

 What can be better than a cup of coffee during a break? A coffee that you don’t have to make yourself. It’s enough to press a couple of buttons on the machine and you get a cup of energy; but first, we should teach the machine how to do it. In this project, you'll program a coffee machine simulator using Java. The machine works with typical products: coffee, milk, sugar, and plastic cups; if it runs out of something, it shows a notification. You can get three types of coffee: espresso, cappuccino, and latte. Since nothing’s for free, it also collects the money. This project allows you to better understand the basic OOP, its main concepts such as classes, class methods and attributes, and get a taste of Java. Practice working with methods, challenge yourself with loops and conditions, and get more confident with OOP.

[View more](https://hyperskill.org/projects/33)


## Stage 1/6: Make a first cup

### Description

Let's start with a program that makes you a coffee – virtual coffee, of course. In this project, you will implement functionality that simulates a real coffee machine. It can run out of ingredients, such as milk or coffee beans, it can offer you various types of coffee, and, finally, it will take money for the prepared drink. Throughout the project, you'll learn how to manage ingredients, take user input, and make decisions based on those inputs.

This stage introduces basic console output that represents the process of making a cup of coffee. In the next stages, you will improve the program by adding user interaction and resource management.

### Objective

The first version of the program just makes you a coffee. Your task is to simulate the coffee-making process by printing each step to the console, showing what the coffee machine does as it makes the drink.

### Example

Take a look at the sample output below and print all the following lines.

Output:
```text
Starting to make a coffee
Grinding coffee beans
Boiling water
Mixing boiled water with crushed coffee beans
Pouring coffee into the cup
Pouring some milk into the cup
Coffee is ready!
```


## Stage 2/6: Calculate the ingredients

### Description

Now let's consider a scenario where you need a lot of coffee—perhaps you're hosting a party with many guests! In such cases, it's better to make preparations in advance.

In this stage, you will ask the user to enter the desired number of coffee cups. Based on this input, you will calculate the necessary amounts of water, coffee, and milk needed to prepare the specified quantity of coffee.

Please note that the coffee machine won't actually make any coffee in this stage; instead, it will simply compute the required ingredients.

### Objectives

Let's break down the task into several steps:

1. Read the number of coffee cups from the input.
2. Calculate the amount of each ingredient needed. One cup of coffee requires:
    - 200 ml of water
    - 50 ml of milk
    - 15 g of coffee beans
3. Output the required ingredient amounts back to the user.

**Note:** As you work through each stage of the project, please ensure to remove any code from previous stages that is no longer needed. This includes printout messages from earlier steps, as they may not be relevant to the current implementation. Keeping your code clean and focused on the specific objectives of each stage will enhance clarity and improve the overall structure of your project.

### Examples

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:** a dialogue with a user might look like this

```text
Write how many cups of coffee you will need: 
> 25
For 25 cups of coffee you will need:
5000 ml of water
1250 ml of milk
375 g of coffee beans
```

**Example 2:** here is another dialogue

```text
Write how many cups of coffee you will need: 
> 125
For 125 cups of coffee you will need:
25000 ml of water
6250 ml of milk
1875 g of coffee beans
```


## Stage 3/6: Estimate the number of servings

### Description

A real coffee machine doesn't have an infinite supply of water, milk, or coffee beans. If you request too many cups of coffee, it's almost certain that a real coffee machine wouldn't have enough supplies to fulfill the order.

In this stage, you need to improve the previous stage program. Now you will check amounts of water, milk, and coffee beans available in the coffee machine at the moment.

### Objectives

Write a program that does the following:

1. Requests the amounts of water, milk, and coffee beans available at the moment, and then asks for the number of cups of coffee a user needs.
2. If the coffee machine has enough supplies to make the specified amount of coffee, the program should print `"Yes, I can make that amount of coffee".`
3. If the coffee machine can make more than the requested amount, the program should output `"Yes, I can make that amount of coffee (and even N more than that)"`, where *N* is the number of additional cups of coffee that the coffee machine can make.
4. If the available resources are insufficient to make the requested amount of coffee, the program should output `"No, I can make only N cup(s) of coffee"`.

Like in the previous stage, the coffee machine needs *200 ml* of water, *50 ml* of milk, and *15 g* of coffee beans to make one cup of coffee.

### Examples

The greater-than symbol followed by a space (`>`) represents the user input. Note that it's not part of the input.

**Example 1:**
```text
Write how many ml of water the coffee machine has:
> 300
Write how many ml of milk the coffee machine has:
> 65
Write how many grams of coffee beans the coffee machine has:
> 100
Write how many cups of coffee you will need:
> 1
Yes, I can make that amount of coffee
```

**Example 2:**
```text
Write how many ml of water the coffee machine has:
> 500
Write how many ml of milk the coffee machine has:
> 250
Write how many grams of coffee beans the coffee machine has:
> 200
Write how many cups of coffee you will need:
> 10
No, I can make only 2 cup(s) of coffee
```

**Example 3:**
```text
Write how many ml of water the coffee machine has:
> 1550
Write how many ml of milk the coffee machine has:
> 299
Write how many grams of coffee beans the coffee machine has:
> 300
Write how many cups of coffee you will need:
> 3
Yes, I can make that amount of coffee (and even 2 more than that)
```

**Example 4:**
```text
Write how many ml of water the coffee machine has:
> 0
Write how many ml of milk the coffee machine has:
> 0
Write how many grams of coffee beans the coffee machine has:
> 0
Write how many cups of coffee you will need:
> 1
No, I can make only 0 cup(s) of coffee
```

**Example 5:**
```text
Write how many ml of water the coffee machine has:
> 0
Write how many ml of milk the coffee machine has:
> 0
Write how many grams of coffee beans the coffee machine has:
> 0
Write how many cups of coffee you will need:
> 0
Yes, I can make that amount of coffee 
```

**Example 6:**
```text
Write how many ml of water the coffee machine has:
> 200
Write how many ml of milk the coffee machine has:
> 50
Write how many grams of coffee beans the coffee machine has:
> 15
Write how many cups of coffee you will need:
> 0
Yes, I can make that amount of coffee (and even 1 more than that)
```


## Stage 4/6: Add functions to your machine

### Description

Now, let's simulate an actual coffee machine! This coffee machine will have a limited supply of water, milk, coffee beans, and disposable cups. Additionally, it will track how much money it earns from selling coffee.

The coffee machine will have three main functions:

1. It can sell different types of coffee: espresso, latte, and cappuccino. Of course, each variety would require a different amount of supplies, however, in any case, would need only one disposable cup for a drink.
2. A special worker should be able to replenish the machine's supplies.
3. Another special worker should be able to collect the money earned by the machine.

### Objectives

Write a program that offers three actions: buying coffee, refilling supplies, or taking its money out. Note that the program is supposed to perform only one of the mentioned actions at a time for each input. It should update the coffee machine's state accordingly i.e. calculate the amounts of remaining ingredients and the total money collected; and display them before and after each action.

1. First, your program reads one option from the standard input, which can be `buy`, `fill`, `take`. If a user wants to buy some coffee, the input is `buy`. If a special worker thinks that it is time to fill out all the supplies for the coffee machine, the input line will be `fill`. If another special worker decides that it is time to take out the money from the coffee machine, you'll get the input `take`.
2. If the user writes `buy` then they must choose one of three types of coffee that the coffee machine can make: espresso, latte, or cappuccino.
    - For a cup of **espresso**, the coffee machine needs *250 ml* of water and *16 g* of coffee beans. It costs *$4*.
    - For a **latte**, the coffee machine needs *350 ml* of water, *75 ml* of milk, and *20 g* of coffee beans. It costs *$7*.
    - And for a **cappuccino**, the coffee machine needs *200 ml* of water, *100 ml* of milk, and *12 g* of coffee beans. It costs *$6*.
3. If the user writes `fill`, the program should ask them how much water, milk, coffee beans, and how many disposable cups they want to add into the coffee machine.
4. If the user writes `take` the program should give all the money that it earned from selling coffee.

In summary, your program should display the coffee machine's current state, process one user action, and then display the updated state. Aim to implement each action using separate functions.

**Note:**

- When the user writes `buy`, they will be prompted to choose a coffee type by entering a number: 1 for espresso, 2 for latte, 3 for cappuccino.
- Initially, the coffee machine has *$550*, *400 ml* of water, *540 ml* of milk, *120 g* of coffee beans, and *9* disposable cups.

### Examples

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:**
```text
The coffee machine has:
400 ml of water
540 ml of milk
120 g of coffee beans
9 disposable cups
$550 of money

Write action (buy, fill, take): 
> buy
What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: 
> 3

The coffee machine has:
200 ml of water
440 ml of milk
108 g of coffee beans
8 disposable cups
$556 of money
```

**Example 2:**
```text
The coffee machine has:
400 ml of water
540 ml of milk
120 g of coffee beans
9 disposable cups
$550 of money

Write action (buy, fill, take): 
> fill
Write how many ml of water you want to add: 
> 2000
Write how many ml of milk you want to add: 
> 500
Write how many grams of coffee beans you want to add: 
> 100
Write how many disposable cups you want to add: 
> 10

The coffee machine has:
2400 ml of water
1040 ml of milk
220 g of coffee beans
19 disposable cups
$550 of money
```

**Example 3:**
```text
The coffee machine has:
400 ml of water
540 ml of milk
120 g of coffee beans
9 disposable cups
$550 of money

Write action (buy, fill, take): 
> take
I gave you $550

The coffee machine has:
400 ml of water
540 ml of milk
120 g of coffee beans
9 disposable cups
$0 of money
```


## Stage 5/6: Keep track of the supplies

### Description

Handling only a single action at a time is quite limited, so let's improve the program to handle multiple actions, one after another. The program should repeatedly ask a user what they want to do. If the user types `buy`, `fill` or `take`, then the program should behave exactly as it did in the previous stage. But unlike the previous stage, where the state of the coffee machine was displayed before and after each action (`buy`, `fill` or `take`), the state of the coffee machine should now be shown only when the user types `remaining`. Additionally, if the user wants to switch off the coffee machine, they should type `exit` to stop the program. In total, the program should now five actions: `buy`, `fill`, `take`, `remaining`, and `exit`.

Remember, that:

- For a cup of **espresso**, the coffee machine needs *250 ml* of water and *16 g* of coffee beans. It costs *$4*.
- For a **latte**, the coffee machine needs *350 ml* of water, *75 ml* of milk, and *20 g* of coffee beans. It costs *$7*.
- And for a **cappuccino**, the coffee machine needs *200 ml* of water, *100 ml* of milk, and *12 g* of coffee beans. It costs *$6*.

### Objectives

Write a program that continuously processes user actions until the `exit` command is given. Additionally, introduce two new options:

- `remaining`: to display the current state of the coffee machine
- `exit`: to switch off the coffee machine

Remember, the coffee machine can run out of resources. If it doesn't have enough resources to make coffee, the program should output a message that says it can't make a cup of coffee and indicate which resource is missing.

And the last improvement to the program in this stage — if the user types `buy` to buy a cup of coffee but then changes their mind, they should be able to type `back` to return into the main menu.

**Note:**

1. Your coffee machine should start with the same initial resources: *400 ml* of water, *540 ml* of milk, *120 g* of coffee beans, *9* disposable cups, *$550* in cash.
2. The program should loop indefinitely, processing actions until the user types `exit` to switch off the coffee machine.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

```text
Write action (buy, fill, take, remaining, exit): 
> remaining

The coffee machine has:
400 ml of water
540 ml of milk
120 g of coffee beans
9 disposable cups
$550 of money

Write action (buy, fill, take, remaining, exit): 
> buy

What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: 
> 2
I have enough resources, making you a coffee!

Write action (buy, fill, take, remaining, exit): 
> remaining

The coffee machine has:
50 ml of water
465 ml of milk
100 g of coffee beans
8 disposable cups
$557 of money

Write action (buy, fill, take, remaining, exit): 
> buy

What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: 
> 2
Sorry, not enough water!

Write action (buy, fill, take, remaining, exit): 
> fill

Write how many ml of water you want to add: 
> 1000
Write how many ml of milk you want to add: 
> 0
Write how many grams of coffee beans you want to add: 
> 0
Write how many disposable cups you want to add: 
> 0

Write action (buy, fill, take, remaining, exit): 
> remaining

The coffee machine has:
1050 ml of water
465 ml of milk
100 g of coffee beans
8 disposable cups
$557 of money

Write action (buy, fill, take, remaining, exit): 
> buy

What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: 
> 2
I have enough resources, making you a coffee!

Write action (buy, fill, take, remaining, exit): 
> remaining

The coffee machine has:
700 ml of water
390 ml of milk
80 g of coffee beans
7 disposable cups
$564 of money

Write action (buy, fill, take, remaining, exit): 
> take

I gave you $564

Write action (buy, fill, take, remaining, exit): 
> remaining

The coffee machine has:
700 ml of water
390 ml of milk
80 g of coffee beans
7 disposable cups
$0 of money

Write action (buy, fill, take, remaining, exit): 
> exit
```


## Stage 6/6: Brush up your code

### Description

In this stage, let's improve the design of our program by organizing it into classes that represent different parts of the coffee machine. For instance, we can create one class for the coffee machine itself and another class to represent each type of coffee with its ingredients and cost. This approach helps structure the code better, allowing for easier reuse and extension. Each class should have methods that handle specific tasks, working together to process the user input and manage the coffee machine's operations.

Your program should handle the user's input through methods in these classes. Every time the user inputs something, it will be processed by these methods to update the state of the machine. This setup simulates how real-world machines operate, where each part has a defined role.

As the coffee machine operates, it will keep track of its resources, including water, milk, coffee beans, disposable cups, and the cash collected. Each action taken by the user should be processed in the context of the machine's current state, which reflects the available resources.

Additionally, we'll introduce a new action: **cleaning**. The coffee machine will monitor how many coffees have been made. After producing 10 cups, it will require cleaning. During this action, the machine will not be able to make any more coffee until it is cleaned by the user typing `clean`. After cleaning, the machine resumes its normal operations.

Remember, that:

- For a cup of **espresso**, the coffee machine needs *250 ml* of water and *16 g* of coffee beans. It costs *$4*.
- For a **latte**, the coffee machine needs *350 ml* of water, *75 ml* of milk, and *20 g* of coffee beans. It costs *$7*.
- And for a **cappuccino**, the coffee machine needs *200 ml* of water, *100 ml* of milk, and *12 g* of coffee beans. It costs *$6*.

### Objective

Your final task is to refactor the program to ensure you can interact with the coffee machine through methods in the classes you created. Implement the **cleaning** action, where the machine requires cleaning after 10 cups of coffee are made. Once cleaned, the machine can make coffee again.

**Note:** Your coffee machine should start with the same initial resources: *400 ml* of water, *540 ml* of milk, *120 g* of coffee beans, *9* disposable cups, *$550* in cash.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

```text
Write action (buy, fill, take, clean, remaining, exit):
> remaining

The coffee machine has:
400 ml of water
540 ml of milk
120 g of coffee beans
9 disposable cups
$550 of money

Write action (buy, fill, take, clean, remaining, exit):
> buy

What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:
> 2
I have enough resources, making you a coffee!

Write action (buy, fill, take, clean, remaining, exit):
> remaining

The coffee machine has:
50 ml of water
465 ml of milk
100 g of coffee beans
8 disposable cups
$557 of money

Write action (buy, fill, take, clean, remaining, exit):
> buy

What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:
> 2
Sorry, not enough water!

Write action (buy, fill, take, clean, remaining, exit):
> fill

Write how many ml of water you want to add:
> 1000
Write how many ml of milk you want to add:
> 0
Write how many grams of coffee beans you want to add:
> 0
Write how many disposable cups you want to add:
> 0

Write action (buy, fill, take, clean, remaining, exit):
> remaining

The coffee machine has:
1050 ml of water
465 ml of milk
100 g of coffee beans
8 disposable cups
$557 of money

Write action (buy, fill, take, clean, remaining, exit):
> buy

What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:
> 2
I have enough resources, making you a coffee!

Write action (buy, fill, take, clean, remaining, exit):
> remaining

The coffee machine has:
700 ml of water
390 ml of milk
80 g of coffee beans
7 disposable cups
$564 of money

Write action (buy, fill, take, clean, remaining, exit):
> take

I gave you $564

Write action (buy, fill, take, clean, remaining, exit):
> remaining

The coffee machine has:
700 ml of water
390 ml of milk
80 g of coffee beans
7 disposable cups
$0 of money
```

*(After 10 cups of coffee have been made)*

```text
Write action (buy, fill, take, clean, remaining, exit):
> buy
I need cleaning!

Write action (buy, fill, take, clean, remaining, exit):
> clean
I have been cleaned!

Write action (buy, fill, take, clean, remaining, exit):
> exit
```
