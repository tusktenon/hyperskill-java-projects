# Recipe Management System: Notes

## Stage 3/5: Store a recipe

In this stage, we transform `Recipe` from a simple record to a JPA `@Entity` class. Notice that the `ingredients` and `directions` fields are still of type `String[]` and receive no JPA annotations. In the corresponding H2 database, both columns in the `recipe` table are of type `CHARACTER VARYING ARRAY`. This is exactly what we want: treating both ingredients and directions as atomic sequences of text.

In many of the posted solutions submitted by other learners, these two fields were annotated with `@ElementCollection`, which serves to create separate `ingredients` and `directions` tables that have the parent `recipe`'s ID as a foreign key. But that really isn't consistent with the domain model: we aren't interested in individual ingredients or direction steps as independent entities. Adding these extra, foreign-key-linked tables serves no useful purpose, and only slows down recipe queries.
