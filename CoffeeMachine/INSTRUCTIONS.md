# Coffee Machine Simulator with Java

See the [project page on Hyperskill](https://hyperskill.org/projects/33) for a thorough overview.


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
