package collections;

import java.util.Objects;

final class EmptyRange<C extends Comparable<C>> extends Range<C> {

    private static final EmptyRange INSTANCE = new EmptyRange();

    @SuppressWarnings("unchecked")
    static <C extends Comparable<C>> EmptyRange<C> instance() {
        return (EmptyRange<C>) INSTANCE;
    }


    /* ──── API instance methods ────────────────────────────────────────────────────────────── */

    @Override
    public boolean contains(C value) {
        Objects.requireNonNull(value);
        return false;
    }

    @Override
    public boolean encloses(Range<C> other) {
        Objects.requireNonNull(other);
        return false;
    }

    @Override
    public Range<C> intersection(Range<C> other) {
        Objects.requireNonNull(other);
        return this;
    }

    @Override
    public Range<C> span(Range<C> other) {
        return Objects.requireNonNull(other);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String toString() {
        return "EMPTY";
    }


    /* ──── Double-dispatch methods for enclosures ──────────────────────────────────────────── */

    @Override
    public boolean isEnclosedByOpenRange(OpenRange<C> other) {
        return true;
    }

    @Override
    boolean isEnclosedByClosedRange(ClosedRange<C> other) {
        return true;
    }

    @Override
    boolean isEnclosedByOpenClosedRange(OpenClosedRange<C> other) {
        return true;
    }

    @Override
    boolean isEnclosedByClosedOpenRange(ClosedOpenRange<C> other) {
        return true;
    }

    @Override
    boolean isEnclosedByGreaterThanRange(GreaterThanRange<C> other) {
        return true;
    }

    @Override
    boolean isEnclosedByAtLeastRange(AtLeastRange<C> other) {
        return true;
    }

    @Override
    boolean isEnclosedByLessThanRange(LessThanRange<C> other) {
        return true;
    }

    @Override
    boolean isEnclosedByAtMostRange(AtMostRange<C> other) {
        return true;
    }


    /* ──── Double-dispatch methods for intersections ───────────────────────────────────────── */

    @Override
    Range<C> intersectionWithOpenRange(OpenRange<C> other) {
        return this;
    }

    @Override
    Range<C> intersectionWithClosedRange(ClosedRange<C> other) {
        return this;
    }

    @Override
    Range<C> intersectionWithOpenClosedRange(OpenClosedRange<C> other) {
        return this;
    }

    @Override
    Range<C> intersectionWithClosedOpenRange(ClosedOpenRange<C> other) {
        return this;
    }

    @Override
    Range<C> intersectionWithGreaterThanRange(GreaterThanRange<C> other) {
        return this;
    }

    @Override
    Range<C> intersectionWithAtLeastRange(AtLeastRange<C> other) {
        return this;
    }

    @Override
    Range<C> intersectionWithLessThanRange(LessThanRange<C> other) {
        return this;
    }

    @Override
    Range<C> intersectionWithAtMostRange(AtMostRange<C> other) {
        return this;
    }


    /* ──── Double-dispatch methods for spans ───────────────────────────────────────────────── */

    @Override
    Range<C> spanWithOpenRange(OpenRange<C> other) {
        return other;
    }

    @Override
    Range<C> spanWithClosedRange(ClosedRange<C> other) {
        return other;
    }

    @Override
    Range<C> spanWithOpenClosedRange(OpenClosedRange<C> other) {
        return other;
    }

    @Override
    Range<C> spanWithClosedOpenRange(ClosedOpenRange<C> other) {
        return other;
    }

    @Override
    Range<C> spanWithGreaterThanRange(GreaterThanRange<C> other) {
        return other;
    }

    @Override
    Range<C> spanWithAtLeastRange(AtLeastRange<C> other) {
        return other;
    }

    @Override
    Range<C> spanWithLessThanRange(LessThanRange<C> other) {
        return other;
    }

    @Override
    Range<C> spanWithAtMostRange(AtMostRange<C> other) {
        return other;
    }
}
