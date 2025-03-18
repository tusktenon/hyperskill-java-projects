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
