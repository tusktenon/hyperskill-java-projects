package collections;

final class ClosedRange<C extends Comparable<C>> extends Range<C> {

    final C lower;
    final C upper;

    ClosedRange(C lower, C upper) {
        this.lower = lower;
        this.upper = upper;
    }


    /* ──── API instance methods ────────────────────────────────────────────────────────────── */

    @Override
    public boolean contains(C value) {
        return isAtMost(lower, value) && isAtMost(value, upper);
    }

    @Override
    public boolean encloses(Range<C> other) {
        return other.isEnclosedByClosedRange(this);
    }

    @Override
    public Range<C> intersection(Range<C> other) {
        return other.intersectionWithClosedRange(this);
    }

    @Override
    public Range<C> span(Range<C> other) {
        return other.spanWithClosedRange(this);
    }

    @Override
    public String toString() {
        return "[%s, %s]".formatted(lower, upper);
    }


    /* ──── Double-dispatch methods for enclosures ──────────────────────────────────────────── */

    @Override
    boolean isEnclosedByOpenRange(OpenRange<C> other) {
        return isGreaterThan(lower, other.lower) && isLessThan(upper, other.upper);
    }

    @Override
    boolean isEnclosedByClosedRange(ClosedRange<C> other) {
        return isAtLeast(lower, other.lower) && isAtMost(upper, other.upper);
    }

    @Override
    boolean isEnclosedByOpenClosedRange(OpenClosedRange<C> other) {
        return isGreaterThan(lower, other.lower) && isAtMost(upper, other.upper);
    }

    @Override
    boolean isEnclosedByClosedOpenRange(ClosedOpenRange<C> other) {
        return isAtLeast(lower, other.lower) && isLessThan(upper, other.upper);
    }

    @Override
    boolean isEnclosedByGreaterThanRange(GreaterThanRange<C> other) {
        return isGreaterThan(lower, other.lower);
    }

    @Override
    boolean isEnclosedByAtLeastRange(AtLeastRange<C> other) {
        return isAtLeast(lower, other.lower);
    }

    @Override
    boolean isEnclosedByLessThanRange(LessThanRange<C> other) {
        return isLessThan(upper, other.upper);
    }

    @Override
    boolean isEnclosedByAtMostRange(AtMostRange<C> other) {
        return isAtMost(upper, other.upper);
    }


    /* ──── Double-dispatch methods for intersections ───────────────────────────────────────── */

    @Override
    Range<C> intersectionWithOpenRange(OpenRange<C> other) {
        return intersectionOfOpenAndClosedRanges(other, this);
    }

    @Override
    Range<C> intersectionWithClosedRange(ClosedRange<C> other) {
        return intersectionOfClosedRanges(this, other);
    }

    @Override
    Range<C> intersectionWithOpenClosedRange(OpenClosedRange<C> other) {
        return intersectionOfClosedAndOpenClosedRanges(this, other);
    }

    @Override
    Range<C> intersectionWithClosedOpenRange(ClosedOpenRange<C> other) {
        return intersectionOfClosedAndClosedOpenRanges(this, other);
    }

    @Override
    Range<C> intersectionWithGreaterThanRange(GreaterThanRange<C> other) {
        return intersectionOfClosedAndGreaterThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtLeastRange(AtLeastRange<C> other) {
        return intersectionOfClosedAndAtLeastRanges(this, other);
    }

    @Override
    Range<C> intersectionWithLessThanRange(LessThanRange<C> other) {
        return intersectionOfClosedAndLessThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtMostRange(AtMostRange<C> other) {
        return intersectionOfClosedAndAtMostRanges(this, other);
    }


    /* ──── Double-dispatch methods for spans ───────────────────────────────────────────────── */

    @Override
    Range<C> spanWithOpenRange(OpenRange<C> other) {
        return spanOfOpenAndClosedRanges(other, this);
    }

    @Override
    Range<C> spanWithClosedRange(ClosedRange<C> other) {
        return spanOfClosedRanges(this, other);
    }

    @Override
    Range<C> spanWithOpenClosedRange(OpenClosedRange<C> other) {
        return spanOfClosedAndOpenClosedRanges(this, other);
    }

    @Override
    Range<C> spanWithClosedOpenRange(ClosedOpenRange<C> other) {
        return spanOfClosedAndClosedOpenRanges(this, other);
    }

    @Override
    Range<C> spanWithGreaterThanRange(GreaterThanRange<C> other) {
        return spanOfClosedAndGreaterThanRanges(this, other);
    }

    @Override
    Range<C> spanWithAtLeastRange(AtLeastRange<C> other) {
        return spanOfClosedAndAtLeastRanges(this, other);
    }

    @Override
    Range<C> spanWithLessThanRange(LessThanRange<C> other) {
        return spanOfClosedAndLessThanRanges(this, other);
    }

    @Override
    Range<C> spanWithAtMostRange(AtMostRange<C> other) {
        return spanOfClosedAndAtMostRanges(this, other);
    }
}
