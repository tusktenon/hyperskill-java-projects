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
