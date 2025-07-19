# Meal Planner (Java)

## *How to run this project*

Starting with Stage 3, the Meal Planner application connects to a PostgreSQL database named `meals_db`. The project instructions don't provide any guidance on setting up such a database; I opted to use Docker Compose (see the [compose.yaml](compose.yaml) file for configuration details). To create and start the Postgres container, run the following from the project's root directory:
```sh
$ docker compose up -d
```
If desired, you can then interact with the database using the [psql](https://www.postgresql.org/docs/current/app-psql.html) command-line tool:
```text
$ docker exec -it mealplanner-postgres psql -U postgres
postgres-# \c meals_db
```
(don't forget to add the semicolon at the end of any SQL commands!).


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


## Stage 2/6: Create a menu

### Description

One meal is not going to get you far! Let's create the main menu to add several meals and display their properties. For this, we need to add a few commands:

- `Add` starts the meal input process and prompts users for the meal properties;
- `Show` prints the list of saved meals with their names, categories, and ingredients;
- After executing `add` or `show`, the program should ask what users want to do next;
- The program must run until users input `exit` — the command that terminates the program.

In this stage, your program should also check the user input. What if users enter something wrong?

- If the input meal category is something other than `breakfast`, `lunch`, or `dinner`, print `Wrong meal category! Choose from: breakfast, lunch, dinner.` and prompt users for input;
- If the meal's name or ingredients have a wrong format (for example, there are numbers or non-alphabet characters; ingredients are blank, and so on), print `Wrong format. Use letters only!` and prompt users for input. Meal's name or ingredients containing several words like "peanut butter" should not be matched as wrong format.

### Objectives

To complete this stage, the program must comply with the following requirements:

1. Create an infinite loop of your program that can be terminated with the `exit` command only;
2. Prompt users to choose an operation with the message `What would you like to do (add, show, exit)?`
3. After the command has been processed, ask for another operation;
4. Make sure that the input and output formats are correct;
5. If users want to `add` a meal, follow the sequence from the previous stage. Don't forget to validate input as explained above. Output `The meal has been added!` before proceeding;
6. If users want to `show` the meals when no meals have been added, print `No meals saved. Add a meal first.` If there are meals that can be displayed, print them in the order they've been added, following the format from Stage 1;
7. Print `Bye!` and end the program once the `exit` command is entered;
8. If users fail to input a valid command, print the following message again: `What would you like to do (add, show, exit)?`

### Examples

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:** *standard execution*
```text
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> lunch
Input the meal's name:
> salad
Input the ingredients:
> lettuce, tomato, onion, cheese, olives
The meal has been added!
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> breakfast
Input the meal's name:
> oatmeal
Input the ingredients:
> oats, milk, banana, peanut butter
The meal has been added!
What would you like to do (add, show, exit)?
> show

Category: lunch
Name: salad
Ingredients:
lettuce
tomato
onion
cheese
olives

Category: breakfast
Name: oatmeal
Ingredients:
oats
milk
banana
peanut butter

What would you like to do (add, show, exit)?
> exit
Bye!
```

**Example 2:** *invalid input format*
```text
What would you like to do (add, show, exit)?
> show
No meals saved. Add a meal first.
What would you like to do (add, show, exit)?
> new meal
What would you like to do (add, show, exit)?
> meal
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> dessert
Wrong meal category! Choose from: breakfast, lunch, dinner.
> lunch
Input the meal's name:
> burger1
Wrong format. Use letters only!
>
Wrong format. Use letters only!
> soup
Input the ingredients:
> carrots, ginger, coconut milk, 123
Wrong format. Use letters only!
> carrots, ginger, coconut milk, curry
The meal has been added!
What would you like to do (add, show, exit)?
> exit
Bye!
```


## Stage 3/6: Database storage

### Description

At this point, when we close our program, we lose all our stored meals! Let's improve our planner and connect a database to retrieve all meals after a restart.

To connect a database to our project, we can use **Java DataBase Connectivity** (JDBC), an API for database-independent connectivity between programs and various databases. This standard ensures the same methods for connecting, updating, querying, and results handling, regardless of the database you employ. However, the choice of the database affects the SQL syntax, available data types, and supported features.

In this project, we will refer to **PostgreSQL**. It is a powerful, open source object-relational database system that contains great capabilities. PostgreSQL runs on all major operating systems and supports advanced data types such as arrays, hstore, and user-defined types. Although, there may be some differences between different SQL databases. You can find more information in the official PostgreSQL [documentation](https://www.postgresql.org/docs/).

In this stage, we're going to store meal data in database tables. When the program restarts, the saved data should be available in the program.

To use PostgreSQL in Java, you need to import the `postgresql-jdbc` library into your project. For the Meal Planner, this is already done. You can find an example of this driver on the [postgresql-jdbc](https://jdbc.postgresql.org/documentation/use/) website.

```java
import java.sql.*

public class Main {
  public static void main(String[] argv) throws SQLException {
    String DB_URL = "jdbc:postgresql:person_db";
    String USER = "postgres";
    String PASS = "1111";

    Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
    connection.setAutoCommit(true);

    Statement statement = connection.createStatement();
    statement.executeUpdate("drop table if exists person");
    statement.executeUpdate("create table person (" +
            "id integer," +
            "name varchar(1024) NOT NULL" +
            ")");
    statement.executeUpdate("insert into person (id, name) values (1, 'leo')");
    statement.executeUpdate("insert into person (id, name) values (2, 'yui')");

    ResultSet rs = statement.executeQuery("select * from person");
    // Read the result set
    while (rs.next()) {
      System.out.println("name = " + rs.getString("name"));
      System.out.println("id = " + rs.getInt("id"));
    }
    statement.close();
    connection.close();
  }
}
```

Mind that the nested `resultset` requires different `statement` instances.

The `jdbc:postgresql:person_db` string includes three strings divided by semicolons. The first one is the database interface, the second is the database, and the third is the name of your database.

It's a good idea to use [pgAdmin](https://www.pgadmin.org/) — a nice GUI tool for browsing and managing PostgreSQL databases. You can inspect the tables you've created and the data in your database.

> [!NOTE]
> If you are connected to the database file when you check your code, it may lead to issues.

> [!NOTE]
> Make sure to create `meals_db` database and add `postgres` user with `1111` password to it before initializing tests.

### Objectives

1. Your program should connect to a database named `meals_db`;
2. Create two tables in this database schema. Name the first one as `meals` with three columns: `category` (varchar), `meal` (varchar), and `meal_id` (integer). Name the second table `ingredients`; it must contain three columns: `ingredient` (varchar), `ingredient_id` (integer), and `meal_id` (integer). `meal_id` in both tables must match!
3. Read all data in the tables, so their contents are available before a `show` operation is requested;
1. When users `add` a new meal, store it in your database.

There are no changes in the input/output structure in this stage.

**Note:** The contents of the database tables are cleared at the beginning of the testing process.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:** *standard execution and a restart*
```text
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> lunch
Input the meal's name:
> salad
Input the ingredients:
> lettuce, tomato, onion, cheese, olives
The meal has been added!
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> breakfast
Input the meal's name:
> oatmeal
Input the ingredients:
> oats, milk, banana, peanut butter
The meal has been added!
What would you like to do (add, show, exit)?
> show

Category: lunch
Name: salad
Ingredients:
lettuce
tomato
onion
cheese
olives

Category: breakfast
Name: oatmeal
Ingredients:
oats
milk
banana
peanut butter

What would you like to do (add, show, exit)?
> exit
Bye!

What would you like to do (add, show, exit)?
> show

Category: lunch
Name: salad
Ingredients:
lettuce
tomato
onion
cheese
olives

Category: breakfast
Name: oatmeal
Ingredients:
oats
milk
banana
peanut butter

What would you like to do (add, show, exit)?
> exit
Bye!
```


## Stage 4/6: Show saved meals

### Description

Let's improve the navigation in the program and make it more user-friendly! In this stage, we will enhance the `show` command. The program will ask users to specify the meal category with the following message: `Which category do you want to print (breakfast, lunch, dinner)?`. After this, the program will search through the database and print only the chosen category. If the requested category is empty, the program should show an appropriate message.

### Objectives

When users input `show`, your program should:

- Ask users for the meal category;
- Search through the database for meals from the chosen category;
- Print `Category: <category>`. For each meal, print `Meal's name: <meal name>`, followed by the specific meal ingredients list, each on a new line. The meals and ingredients should be printed **in the same order** they've been added;
- If the input category doesn't exist, print `Wrong meal category! Choose from: breakfast, lunch, dinner.`;
- If there're no meals in the category, print `No meals found.`

### Examples

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

**Example 1:** *standard execution*
```text
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> lunch
Input the meal's name:
> salad
Input the ingredients:
> lettuce, tomato, onion, cheese, olives
The meal has been added!
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> lunch
Input the meal's name:
> omelette
Input the ingredients:
> eggs, milk, cheese
The meal has been added!
What would you like to do (add, show, exit)?
> add
Which meal do you want to add (breakfast, lunch, dinner)?
> breakfast
Input the meal's name:
> oatmeal
Input the ingredients:
> oats, milk, banana, peanut butter
The meal has been added!
What would you like to do (add, show, exit)?
> show
Which category do you want to print (breakfast, lunch, dinner)?
> breakfast
Category: breakfast
Name: oatmeal
Ingredients:
oats
milk
banana
peanut butter
What would you like to do (add, show, exit)?
> show
Which category do you want to print (breakfast, lunch, dinner)?
> lunch
Category: lunch

Name: salad
Ingredients:
lettuce
tomato
onion
cheese
olives

Name: omelette
Ingredients:
eggs
milk
cheese

What would you like to do (add, show, exit)?
> exit
Bye!
```

**Example 2:** *warnings during execution*
```text
What would you like to do (add, show, exit)?
> show
Which category do you want to print (breakfast, lunch, dinner)?
> dinner
No meals found.
What would you like to do (add, show, exit)?
> show
Which category do you want to print (breakfast, lunch, dinner)?
> brunch
Wrong meal category! Choose from: breakfast, lunch, dinner.
> dinner
No meals found.
What would you like to do (add, show, exit)?
> exit
Bye!
```


## Stage 5/6: Weekly plan

### Description

A solid person plans for the week ahead! Let's help our users plan their meals for the entire week. In this stage, we will add two new command — `plan` and `list plan`. We also need to change the main menu accordingly. From now on, it should read as `What would you like to do (add, show, plan, list plan, exit)?`

When users input `plan`, the program should print the first day of the week, `Monday`, and print the list of all breakfasts stored in the database **in alphabetical order**. After this, the program should ask users to pick a meal with the following message: `Choose the breakfast for Monday from the list above:`

After users input a meal option, the program should verify it. If it's not stored in the database, print `This meal doesn’t exist. Choose a meal from the list above.` If the input is correct, move on to the next category — `lunch` and then to `dinner`. Once the meals for three categories are picked, print `Yeah! We planned the meals for Monday.` Repeat these steps for other weekdays. In the end, print the whole plan for the week.

Save the plan to the database. For this purpose, create a new table named `plan` when the program starts. This table contains the `meal_option`, `meal_category`, and `meal_id`. The third column must match the `meal_id` columns of the other two tables. You are free to choose how to implement the fields in this table. If a new plan is created, delete the old plan.

The `list plan` command is simple and it only prints the plan to the console which is stored in the database.

### Objectives

1. Create a table in the database named `plan`;
2. Add the `plan` option to the menu;
3. When users choose the `plan` option:

    - Print `Monday`;
    - Print the meal names of the breakfast category in alphabetical order;
    - Prompt `Choose the breakfast for Monday from the list above:`
    - Once users input a meal, print the meal names of the lunch category in alphabetical order;
    - Prompt `Choose the lunch for Monday from the list above:`
    - Once users input a meal, print the meal names of the dinner category in alphabetical order;
    - Prompt `Choose the dinner for Monday from the list above:`;
    - Once users input a meal, print `Yeah! We planned the meals for Monday.`;
    - If a meal option isn't in the provided list, print `This meal doesn’t exist. Choose a meal from the list above.`;
    - Print a blank line and repeat for the rest of the week;
    - Once the plan for the week is drawn, print it. The plan print format is as follows:
        ```text
        Monday
        Breakfast: [meal's name]
        Lunch: [meal's name]
        Dinner: [meal's name]

        Tuesday etc.
        ```

    - Save the plan data in the `plan` table. Overwrite the old plan every time a new plan is created.
    - When users choose the `list plan` option, display the stored plan in the same format. If a plan does not exist on the database, you can display `Database does not contain any meal plans` error message to notify that the plan doesn't exist.

### Example

The greater-than symbol followed by a space (`> `) represents the user input. Note that it's not part of the input.

> [!NOTE]
> The example below assumes that the database stores a few meals.

**Example 1:** *planning for the week*
```text
What would you like to do (add, show, plan, list plan, exit)?
> plan
Monday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Monday from the list above:
> yogurt
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Monday from the list above:
> tomato salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Monday from the list above:
> spaghetti
This meal doesn’t exist. Choose a meal from the list above.
> ramen
Yeah! We planned the meals for Monday.

Tuesday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Tuesday from the list above:
> oatmeal
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Tuesday from the list above:
> wraps
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Tuesday from the list above:
> ramen
Yeah! We planned the meals for Tuesday.

Wednesday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Wednesday from the list above:
> sandwich
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Wednesday from the list above:
> avocado egg salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Wednesday from the list above:
> pesto chicken
Yeah! We planned the meals for Wednesday.

Thursday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Thursday from the list above:
> oatmeal
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Thursday from the list above:
> chicken salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Thursday from the list above:
> tomato soup
Yeah! We planned the meals for Thursday.

Friday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Friday from the list above:
> yogurt
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Friday from the list above:
> sushi
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Friday from the list above:
> pizza
Yeah! We planned the meals for Friday.

Saturday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Saturday from the list above:
> scrambled eggs
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Saturday from the list above:
> wraps
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Saturday from the list above:
> pesto chicken
Yeah! We planned the meals for Saturday.

Sunday
oatmeal
sandwich
scrambled eggs
yogurt
Choose the breakfast for Sunday from the list above:
> scrambled eggs
avocado egg salad
chicken salad
sushi
tomato salad
wraps
Choose the lunch for Sunday from the list above:
> tomato salad
beef with broccoli
pesto chicken
pizza
ramen
tomato soup
Choose the dinner for Sunday from the list above:
> beef with broccoli
Yeah! We planned the meals for Sunday.

Monday
Breakfast: yogurt
Lunch: tomato salad
Dinner: ramen

Tuesday
Breakfast: oatmeal
Lunch: wraps
Dinner: ramen

Wednesday
Breakfast: sandwich
Lunch: avocado egg salad
Dinner: pesto chicken

Thursday
Breakfast: oatmeal
Lunch: chicken salad
Dinner: tomato soup

Friday
Breakfast: yogurt
Lunch: sushi
Dinner: pizza

Saturday
Breakfast: scrambled eggs
Lunch: wraps
Dinner: pesto chicken

Sunday
Breakfast: scrambled eggs
Lunch: tomato salad
Dinner: beef with broccoli

What would you like to do (add, show, plan, list plan, exit)?
> exit
Bye!
```
