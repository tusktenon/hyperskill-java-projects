# Car Sharing

## Project description

During the project implementation, you will learn the basics of SQL and work with the H2 database. You will also learn about advanced Java features such as Collections.

[View more](https://hyperskill.org/projects/140)


## Stage 1/4: Initialization

### Description

In this project, you will work with the H2 database. It is an open-source lightweight Java database that can be embedded in Java applications or run in the client-server mode. Mainly, the H2 database can be configured to run as an in-memory database, which means that data will not persist on the disk. Because of the embedded database, it is not used for product development but mostly for development and testing purposes.

Before you start with the project, you need to [connect to the database from Java](https://www.tutorialspoint.com/h2_database/h2_database_jdbc_connection.htm). For your implementation, do not use login credentials. Note that there is no default username and password required.

Let's start by creating a `COMPANY` table that will store information about the created car companies.

### Objectives

Create a single table named `COMPANY` with the following columns:

- `ID` with the type `INT`;
- `NAME` with the type `VARCHAR`.

After running the program, it should create the database file in the `carsharing/db/` folder, initialize the table described above, and stop.

The database file name is obtained from the command-line arguments. Here is an example of args: `-databaseFileName carsharing`. If the `-databaseFileName` argument is not given, then the database file name can be anything. Your database URL should work out to be like the following: `jdbc:h2:./src/carsharing/db/{DATABASE_NAME}`.


> [!NOTE]
> To pass the tests, you have to enable the auto-commit mode so that all changes are automatically saved in the database file. To do that, call the method `connection.setAutoCommit(true)` of the `Connection` object.

### *My Comment*

I made a small modification to the `Main.java` file submitted to Hyperskill: The database storage directory has been changed from `./src/carsharing/db/` (which is required by Hyperskill's tests) to `./db/`.


## Stage 2/4: Companies

### Description

Great, we have a database! Now, let's implement the ability to actually work with it from your program. In this stage, you will create a user-friendly menu that allows you to log in as a manager, create companies, and save them in the database.

> [!TIP]
> Try to implement the Data Access Object Pattern as it lets you get the data from the database as a Java object. This can make working with the database easier.

### Objectives

Update your database and add constraints to the `COMPANY` table columns:

- `ID` column should be `PRIMARY KEY` and `AUTO_INCREMENT`.
- `NAME` column should be `UNIQUE` and `NOT NULL`.
- Column types should be the same as in the previous stage.

Implement the menu with the following options:
```text
1. Log in as a manager
0. Exit
```

If the option `Exit` was chosen, the program should stop. In case the user chose `Log in as a manager`, the following menu should be printed:
```text
1. Company list
2. Create a company
0. Back
```

Now, if the user chose to go `Back`, the program should print the main menu. `Company list` should print the list of companies sorted by their IDs. Their indexes start from 1, for example:
```text
Company list:
1. First company name
2. Second company name
3. Third company name
```

If there are no companies, print `The company list is empty!`.

If the option `Create a company` was chosen, the program should prompt the user to enter a company name with the message `Enter the company name:`, read the company name, and save it to the database.

Note that a list numeration should always start with 1, with 0 being reserved for the `Exit` and `Back` options shown at the end of options lists.

> [!NOTE]
> To pass the tests, you have to enable the auto-commit mode so that all changes are automatically saved in the database file. To do that, call the method `connection.setAutoCommit(true)` of the `Connection` object.

### Examples

The greater-than symbol followed by a space `> ` represents the user input. Note that it's not part of the input.
```text
1. Log in as a manager
0. Exit
> 1

1. Company list
2. Create a company
0. Back
> 1

The company list is empty!

1. Company list
2. Create a company
0. Back
> 2

Enter the company name:
> Super company
The company was created!

1. Company list
2. Create a company
0. Back
> 1

Company list:
1. Super company

1. Company list
2. Create a company
0. Back
> 2

Enter the company name:
> Another company
The company was created!

1. Company list
2. Create a company
0. Back
> 2

Enter the company name:
> One more company
The company was created!

1. Company list
2. Create a company
0. Back
> 1

Company list:
1. Super company
2. Another company
3. One more company

1. Company list
2. Create a company
0. Back
> 0

1. Log in as a manager
0. Exit
> 0
```
