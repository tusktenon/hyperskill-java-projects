package collections;

import java.util.Objects;

final class AllRange<C extends Comparable<C>> extends Range<C> {

    private static final AllRange INSTANCE = new AllRange();

    @SuppressWarnings("unchecked")
    static <C extends Comparable<C>> AllRange<C> instance() {
        return (AllRange<C>) INSTANCE;
    }


    /* ──── API instance methods ────────────────────────────────────────────────────────────── */

    @Override
    public boolean contains(C value) {
        Objects.requireNonNull(value);
        return true;
    }

    @Override
    public boolean encloses(Range<C> other) {
        Objects.requireNonNull(other);
        return true;
    }

    @Override
    public Range<C> intersection(Range<C> other) {
        return Objects.requireNonNull(other);
    }

    @Override
    public Range<C> span(Range<C> other) {
        Objects.requireNonNull(other);
        return this;
    }

    @Override
    public String toString() {
        return "(-INF, INF)";
    }


    /* ──── Double-dispatch methods for enclosures ──────────────────────────────────────────── */

    @Override
    boolean isEnclosedByOpenRange(OpenRange<C> other) {
        return false;
    }

    @Override
    boolean isEnclosedByClosedRange(ClosedRange<C> other) {
        return false;
    }

    @Override
    boolean isEnclosedByOpenClosedRange(OpenClosedRange<C> other) {
        return false;
    }

    @Override
    boolean isEnclosedByClosedOpenRange(ClosedOpenRange<C> other) {
        return false;
    }

    @Override
    boolean isEnclosedByGreaterThanRange(GreaterThanRange<C> other) {
        return false;
    }

    @Override
    boolean isEnclosedByAtLeastRange(AtLeastRange<C> other) {
        return false;
    }

    @Override
    boolean isEnclosedByLessThanRange(LessThanRange<C> other) {
        return false;
    }

    @Override
    boolean isEnclosedByAtMostRange(AtMostRange<C> other) {
        return false;
    }


    /* ──── Double-dispatch methods for intersections ───────────────────────────────────────── */

    @Override
    Range<C> intersectionWithOpenRange(OpenRange<C> other) {
        return other;
    }

    @Override
    Range<C> intersectionWithClosedRange(ClosedRange<C> other) {
        return other;
    }

    @Override
    Range<C> intersectionWithOpenClosedRange(OpenClosedRange<C> other) {
        return other;
    }

    @Override
    Range<C> intersectionWithClosedOpenRange(ClosedOpenRange<C> other) {
        return other;
    }

    @Override
    Range<C> intersectionWithGreaterThanRange(GreaterThanRange<C> other) {
        return other;
    }

    @Override
    Range<C> intersectionWithAtLeastRange(AtLeastRange<C> other) {
        return other;
    }

    @Override
    Range<C> intersectionWithLessThanRange(LessThanRange<C> other) {
        return other;
    }

    @Override
    Range<C> intersectionWithAtMostRange(AtMostRange<C> other) {
        return other;
    }


    /* ──── Double-dispatch methods for spans ───────────────────────────────────────────────── */

    @Override
    Range<C> spanWithOpenRange(OpenRange<C> other) {
        return this;
    }

    @Override
    Range<C> spanWithClosedRange(ClosedRange<C> other) {
        return this;
    }

    @Override
    Range<C> spanWithOpenClosedRange(OpenClosedRange<C> other) {
        return this;
    }

    @Override
    Range<C> spanWithClosedOpenRange(ClosedOpenRange<C> other) {
        return this;
    }

    @Override
    Range<C> spanWithGreaterThanRange(GreaterThanRange<C> other) {
        return this;
    }

    @Override
    Range<C> spanWithAtLeastRange(AtLeastRange<C> other) {
        return this;
    }

    @Override
    Range<C> spanWithLessThanRange(LessThanRange<C> other) {
        return this;
    }

    @Override
    Range<C> spanWithAtMostRange(AtMostRange<C> other) {
        return this;
    }
}
