# Meal Planner (Java)

## Project description

Every day, people face a lot of difficult choices: for example, what to prepare for breakfast, lunch, and dinner? Are the necessary ingredients in stock? With the Meal Planner, this can be quick and painless! You can make a database of categorized meals and set the menu for the week. This app will also help create and store shopping lists based on the meals so that no ingredient is missing.

[View more](https://hyperskill.org/projects/318)


## Stage 1/6: Add meals

### Description

Let's start with something simple. Write a program that can store meals and their properties. Prompt users about the category of a meal (`breakfast`, `lunch`, or `dinner`), name of a meal, and necessary ingredients. The program should print that information with the meal properties in the correct format. In this stage, you don't need to validate user input.

### Objectives

To complete this stage, your program should:

- Ask about the meal category with the following message: `Which meal do you want to add (breakfast, lunch, dinner)?`.
- Ask about the name of the meal with the message `Input the meal's name:`.
- Inquire about the necessary ingredients with the message `Input the ingredients:`. The input contains ingredients in one line separated by commas. The output displays each ingredient on a new line (see Examples).
- Print all the information about the meal in the following format:
    ```text
    Category: category
    Name: meal's name
    Ingredients:
    ingredient 1
    ingredient 2
    ingredient 3
    ```
- Print the message that the meal is saved successfully: `The meal has been added!`.

### Examples

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:** *standard execution — lunch*
```text
Which meal do you want to add (breakfast, lunch, dinner)?
> lunch
Input the meal's name:
> salad
Input the ingredients:
> lettuce,tomato,onion,cheese,olives

Category: lunch
Name: salad
Ingredients:
lettuce
tomato
onion
cheese
olives
The meal has been added!
```

**Example 2:** *standard execution — breakfast*
```text
Which meal do you want to add (breakfast, lunch, dinner)?
> breakfast
Input the meal's name:
> oatmeal
Input the ingredients:
> oats,milk,banana,peanut butter

Category: breakfast
Name: oatmeal
Ingredients:
oats
milk
banana
peanut butter
The meal has been added!
```
