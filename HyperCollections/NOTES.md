# HyperCollections: Notes

## Stage 1/4: Immutable Collections

The Hyperskill tests require a no-argument version of the `of` method, but note that it isn't actually needed: the varargs overload handles this case correctly.


## Stage 2/4: BiMaps and MultiSets

Consider the expected outputs shown in the examples: in addition to implementing the methods listed above, you'll need to override `toString()` for both classes.

The method descriptions are a little vague, and the messages from failing tests don't provide much help. For `Multiset` in particular, I found myself examining the source code for the tests to figure out the expected behaviour of the methods:

- `add(element, occurrences)` and `remove(element, occurrences)` should simply do nothing if `occurrences` is zero or negative.
- `setCount(element, count)` should remove `element` from the multiset if `count` is zero and do nothing if `count` is negative.
- Similarly, `setCount(element, oldCount, newCount)` should do nothing if `newCount` is negative, and remove `element` if `newCount` is zero and `oldCount` matches the current count.
- The `toString()` method must list elements in insertion order (i.e., the backing `Map` should be a `LinkedHashMap`).


## Stage 3/4: Size Limited Queues

The descriptions of both `toArray` methods seem to suggest that the tail of the queue should be element `0` of the returned array, while the head should be element `size - 1`; in fact, the tests require the opposite ordering.

I used an array as the backing collection, but most learners used a `Queue<E>` implementation – namely, `ArrayDeque<E>` – from the Java Collections Framework. Using a `Queue<E>` is obviously much simpler, while using an array feels more "in the spirit" of the exercise.


## Stage 4/4: Ranges

I chose to implement the `encloses`, `intersection` and `span` methods using *double dispatch*, a classic OOP technique. The Hyperskill tests demand an implementation that uses Java language level 17, which introduced sealed classes; had they allowed Java 21, which added pattern matching for `switch` statements, I might have taken that approach instead.

Note that the advantages of pattern matching for `switch` (over `if`-`else` chains using `instanceof`) go beyond more legible code. The compiler can verify that the `switch` cases are exhaustive, and there's also a potential performance improvement: the compiler can emit code that will jump to the correct branch in constant time, instead of having to go through the `if` clauses sequentially until reaching a match. See the [JEP](https://openjdk.org/jeps/441) for a good explanation.

Since Ranges are immutable, I went to some length to avoid creating new ones whenever possible: the all and empty ranges are singletons, and whenever the span or intersection of two ranges happens to be one of the input ranges (a common occurrence), those methods return that range, not a new one with identical bounds. If minimizing the creating of new objects isn't a concern, it's possible to write a much shorter solution.
