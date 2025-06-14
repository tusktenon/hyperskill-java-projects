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

### *My Comment*

The Hyperskill tests require a no-argument version of the `of` method, but note that it isn't actually needed: the varargs overload handles this case correctly.


## Stage 2/4: BiMaps and MultiSets

### Description

A **bimap**, or "bidirectional map", is a map that preserves the uniqueness of its values as well as that of its keys. It maintains two underlying maps: one for the forward mapping from keys to values, and another for the reverse mapping from values to keys. This constraint enables bimaps to support an "inverse view", which is a second bimap containing the same entries as the first bimap but with reversed keys and values.

A **multiset** collection supports order-independent equality, like `Set`, but may have duplicate elements. A multiset is also sometimes called a **bag**. Elements of a multiset that are equal to one another are referred to as **occurrences** of the same single element. The total number of occurrences of an element in a multiset is called the **count** of that element (the terms "frequency" and "multiplicity" are equivalent, but not used in this API). Since the count of an element is represented as an `int`, a multiset may never contain more than `Integer.MAX_VALUE` occurrences of any one element.

### Objectives

- Implement the following endpoints in the `BiMap<K, V>` class:

| Method (endpoints) | Description |
| --- | --- |
| `V put(K key, V value)` | Stores the key-value pair. If a value or a key is already in BiMap, it should throw an `IllegalArgumentException` |
| `putAll(Map<K, V> map)` | Stores all key-value pairs from a map. If any values or keys are already in BiMap, it should throw an `IllegalArgumentException` |
| `Set<V> values()` | Returns all the values of a map as a `Set`
| `V forcePut(K key, V value)` | Inserts the specified key-value pair into the map, replacing any existing mapping for the same key or value.
| `BiMap<V, K> inverse()` | Returns the inverse view of this bimap, which maps each of this bimap's values to its associated key

- Implement the following endpoints in the `Multiset<E>` class:

| Method | Description |
| --- | --- |
| `add(E element)` | Adds a single occurrence of the specified element to the current multiset |
| `add(E element, int occurrences)` | Adds a number of occurrences of an element to the current multiset |
| `boolean contains(E element)` | Determines whether the current multiset contains the specified element |
| `int count(E element)` | Returns the number of occurrences of an element in the current multiset (the count of the element) |
| `Set<E> elementSet()` | Returns the set of distinct elements contained in the current multiset |
| `remove(E element)` | Removes a single occurrence of the specified element from the current multiset, if present |
| `remove(E element, int occurrences)` | Removes a number of occurrences of the specified element from the current multiset |
| `setCount(E element, int count)` | Adds or removes the necessary occurrences of an element so that the element attains the desired count. If there are no occurences, multiset's elements should not change. |
| `setCount(E element, int oldCount, int newCount)` | Conditionally sets the count of an element to a new value, as described in `setCount(E element, int count)`, provided that the element has the expected current count. If there are no occurences, multiset's elements should not change. |

Note: the `BiMap` and `Multiset` classes must be generic.

### Examples

**Example 1:** `BiMap<K, V>`
```java
BiMap<Character, Integer> biMap = new BiMap<>();

biMap.put('a', 3);
biMap.putAll(Map.of('b', 4, 'c', 5));

System.out.println(biMap); // {a=3, b=4, c=5}
System.out.println(biMap.values()); // [3, 4, 5]

//biMap.put('a', 6); - an IllegalArgumentException should be thrown
//biMap.put('d', 3); - an IllegalArgumentException should be thrown
//biMap.putAll(Map.of('d', 6, 'e', 4)); - an IllegalArgumentException should be thrown
//biMap.putAll(Map.of('d', 6, 'c', 7)); - an IllegalArgumentException should be thrown

biMap.putAll(Map.of('d', 6, 'e', 7));

System.out.println(biMap); // {a=3, b=4, c=5, d=6, e=7}
System.out.println(biMap.inverse()); // {3=a, 4=b, 5=c, 6=d, 7=e}

biMap.forcePut('f', 8);

System.out.println(biMap); // {a=3, b=4, c=5, d=6, e=7, f=8}
System.out.println(biMap.inverse()); // {3=a, 4=b, 5=c, 6=d, 7=e, 8=f}

biMap.forcePut('a', 9);

System.out.println(biMap); // {a=9, b=4, c=5, d=6, e=7, f=8}
System.out.println(biMap.inverse()); // {4=b, 5=c, 6=d, 7=e, 8=f, 9=a}

biMap.forcePut('g', 4);

System.out.println(biMap); // {a=9, c=5, d=6, e=7, f=8, g=4}
System.out.println(biMap.inverse()); // {4=g, 5=c, 6=d, 7=e, 8=f, 9=a}

biMap.forcePut('c', 6);

System.out.println(biMap); // {a=9, c=6, e=7, f=8, g=4}
System.out.println(biMap.inverse()); // {4=g, 6=c, 7=e, 8=f, 9=a}
```

**Example 2:** `Multiset<E>`
```java
Multiset<Character> multiset = new Multiset<>();
multiset.add('a');
multiset.add('b', 6);

System.out.println(multiset); // [a, b, b, b, b, b, b]
System.out.println(multiset.contains('c')); // false
System.out.println(multiset.count('b')); // 6
System.out.println(multiset.elementSet()); // ['a', 'b']

multiset.remove('a');
multiset.remove('b', 3);

System.out.println(multiset); // [b, b, b]

multiset.add('c');
multiset.setCount('c', 2);
multiset.setCount('b', 3, 4);

System.out.println(multiset); // [b, b, b, b, c, c]
```

Note: you don't need to implement the `main` method.

### *My Comments*

Consider the expected outputs shown in the examples: in addition to implementing the methods listed above, you'll need to override `toString()` for both classes.

The method descriptions are a little vague, and the messages from failing tests don't provide much help. For `Multiset` in particular, I found myself examining the source code for the tests to figure out the expected behaviour of the methods:

- `add(element, occurrences)` and `remove(element, occurrences)` should simply do nothing if `occurrences` is zero or negative.
- `setCount(element, count)` should remove `element` from the multiset if `count` is zero and do nothing if `count` is negative.
- Similarly, `setCount(element, oldCount, newCount)` should do nothing if `newCount` is negative, and remove `element` if `newCount` is zero and `oldCount` matches the current count.
- The `toString()` method must list elements in insertion order (i.e., the backing `Map` should be a `LinkedHashMap`).



## Stage 3/4: Size Limited Queues

### Description

A **Size Limited Queue**, or "CircularFifoQueue", is a first-in-first-out queue with a fixed size that replaces its oldest element if full.

The removal order of a `SizeLimitedQueue` is based on the insertion order, so elements are removed in the same order in which they were added. The iteration order is the same as the removal order.

The `add` and `remove` operations all perform in constant time. All other operations perform in linear time or worse.

To prevent unexpected situations with the operations of the collection, let's respond to some exceptional conditions. This queue prevents null objects from being added by throwing `NullPointerException`. The provided limit should be positive, otherwise the queue should throw an `IllegalArgumentException`. The `peek` operation should return null, and the `remove` operation should throw `NoSuchElementException` for an empty queue.

You may use unit tests to be sure you've implemented all collections correctly so far.

### Objectives

You have to implement the `SizeLimitedQueue<E>` class with the following endpoints:

| Method | Description |
| --- | --- |
| `void add(E element)` | Adds the given element to the queue |
| `void clear()` | Clears the queue |
| `boolean isAtFullCapacity()` | Returns `true` if the capacity limit of the queue has been reached |
| `boolean isEmpty()` | Returns `true` if the queue is empty, and `false` otherwise |
| `int maxSize()` | Gets the maximum size of the collection (the bound) |
| `E peek()` | Returns the first element in the queue |
| `E remove()` | Removes the first element in the queue |
| `int size()` | Returns the number of elements stored in the queue |
| `E[] toArray(E[] e)` | Returns the queue elements as an `E` array from tail to head |
| `Object[] toArray()` | Returns the queue elements as an `Object` array from tail to head |

Note: the `SizeLimitedQueue` class must be generic.

### Example

Here is an example of how to work with the collection.
```java
SizeLimitedQueue<Integer> collection = new SizeLimitedQueue<>(3); // limit is 3

System.out.println(collection); // []
System.out.println(collection.maxSize()); // 3
System.out.println(collection.size()); // 0
System.out.println(collection.isEmpty()); // true

collection.add(1);
collection.add(2);

System.out.println(collection.isEmpty()); // false
System.out.println(collection); // [1, 2]
System.out.println(collection.isAtFullCapacity()); // false

collection.add(3);

System.out.println(collection); // [1, 2, 3]
System.out.println(collection.isAtFullCapacity()); // true

collection.add(4);
System.out.println(collection); // [2, 3, 4]

System.out.println(collection.peek()); // 2
System.out.println(collection); // [2, 3, 4]
System.out.println(collection.remove()); // 2
System.out.println(collection); // [3, 4]

System.out.println(collection.toArray().getClass()); // class [Ljava.lang.Object;
System.out.println(collection.toArray(new Integer[0]).getClass()); // class [Ljava.lang.Integer;
```

Note: you don't need to implement the `main` method.
