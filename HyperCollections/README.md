# HyperCollections

## Project description

Java developers often face the need to store and process data conveniently. The Java Collections Framework can help with this issue, as developers usually don't need to create new collections from scratch – they only need to apply or slightly extend the existing ones to solve their needs. However, there are some design drawbacks inherited in the Java Collections Framework, which can even lead to bugs in your programs. Some approaches used in this library are considered obsolete now and are normally avoided in modern libraries. Besides, sometimes developers face rare issues, for which the standard collections aren't enough. In such cases, alternative libraries such as Google Guava Collections or Apache Common Collections may help. These libraries contain more collections and an abundance of auxiliary algorithms to work with. Another solution is to write your problem-specific collections. This is much more complicated and not always the best solution, but it isn't forbidden either.

[View more](https://hyperskill.org/projects/319)


## Stage 1/4: Immutable Collections

### Description

In the first stage of the project, you will create a simple immutable generic collection. The elements of the collection shouldn't change under any circumstances. To implement this properly is actually more difficult than it seems at the first glance.

After completing this stage, you will already have understood the general approaches used in this project. However, you might have a question: why do we need a collection that can't be changed at all? In fact, working with immutable data is very convenient and allows avoiding many possible mistakes. **Immutability** is a crucial concept in this project. Remember the `String` class, whose elements can't be modified either.

### Objectives

This stage will reveal to you all the key ideas, restrictions, and agreements that you should use throughout the project to successfully complete it.

1. You should create a `public final` class named `ImmutableCollection` in the `collections` package.

2. The class should contain a static factory method called `of` that takes an arbitrary number of elements as an argument and returns an implementation of it (i.e. an instance of the `ImmutableCollection` class).

3. The invocation `ImmutableCollection.of()` should create an empty collection, whereas the invocation `ImmutableCollection.of(1, 2, 3, 4, 5)` should create a five-element collection.

4. If any of the passed elements changes in the external code, it shouldn't change within the collection.

5. If any of the passed elements is null, the method `of` should throw NPE.

6. The class should contain the `boolean contains(E element)` method that checks whether the collection contains a specified element.

7. The class should provide `int size()` and `boolean isEmpty()` methods that check the size of the collection and whether it is empty, respectively.

> [!NOTE]
> To implement this, you should use a utility class called `Objects`. It has a set of convenient static methods to work with the `null` value, compare references, and so on. Using this class will make your code more error-prone and well-readable.

### Example

Here is an example of how to work with the collection:
```java
ImmutableCollection<?> emptyCollection = ImmutableCollection.of();
System.out.println(emptyCollection.size()); // 0
System.out.println(emptyCollection.isEmpty()); // true

Student student1 = new Student(1, "Student 1", "Email 1");

ImmutableCollection<Student> addresses = ImmutableCollection.of(
    student1,
    new Student(2, "Student 2", "Email 2"),
    new Student(3, "Student 3", "Email 3"),
    new Student(4, "Student 4", "Email 4"),
    new Student(5, "Student 5", "Email 5")
);

Student student = new Student(1, "Student 1", "Email 1");
System.out.println(addresses.contains(student)); // true

ImmutableCollection<Integer> collection = ImmutableCollection.of(1, 2, 3, 4, 5);
System.out.println(collection.isEmpty()); // false
System.out.println(collection.size()); // 5
```

Note: you don't need to implement the `main` method. 
