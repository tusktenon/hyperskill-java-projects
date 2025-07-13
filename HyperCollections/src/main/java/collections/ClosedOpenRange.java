package collections;

final class ClosedOpenRange<C extends Comparable<C>> extends Range<C> {

    final C lower;
    final C upper;

    ClosedOpenRange(C lower, C upper) {
        this.lower = lower;
        this.upper = upper;
    }


    /* ──── API instance methods ────────────────────────────────────────────────────────────── */

    @Override
    public boolean contains(C value) {
        return isAtMost(lower, value) && isLessThan(value, upper);
    }

    @Override
    public boolean encloses(Range<C> other) {
        return other.isEnclosedByClosedOpenRange(this);
    }

    @Override
    public Range<C> intersection(Range<C> other) {
        return other.intersectionWithClosedOpenRange(this);
    }

    @Override
    public Range<C> span(Range<C> other) {
        return other.spanWithClosedOpenRange(this);
    }

    @Override
    public String toString() {
        return "[%s, %s)".formatted(lower, upper);
    }


    /* ──── Double-dispatch methods for enclosures ──────────────────────────────────────────── */

    @Override
    boolean isEnclosedByOpenRange(OpenRange<C> other) {
        return isGreaterThan(lower, other.lower) && isAtMost(upper, other.upper);
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
        return isAtLeast(lower, other.lower) && isAtMost(upper, other.upper);
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
        return isAtMost(upper, other.upper);
    }

    @Override
    boolean isEnclosedByAtMostRange(AtMostRange<C> other) {
        return isAtMost(upper, other.upper);
    }


    /* ──── Double-dispatch methods for intersections ───────────────────────────────────────── */

    @Override
    Range<C> intersectionWithOpenRange(OpenRange<C> other) {
        return intersectionOfOpenAndClosedOpenRanges(other, this);
    }

    @Override
    Range<C> intersectionWithClosedRange(ClosedRange<C> other) {
        return intersectionOfClosedAndClosedOpenRanges(other, this);
    }

    @Override
    Range<C> intersectionWithOpenClosedRange(OpenClosedRange<C> other) {
        return intersectionOfOpenClosedAndClosedOpenRanges(other, this);
    }

    @Override
    Range<C> intersectionWithClosedOpenRange(ClosedOpenRange<C> other) {
        return intersectionOfClosedOpenRanges(this, other);
    }

    @Override
    Range<C> intersectionWithGreaterThanRange(GreaterThanRange<C> other) {
        return intersectionOfClosedOpenAndGreaterThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtLeastRange(AtLeastRange<C> other) {
        return intersectionOfClosedOpenAndAtLeastRanges(this, other);
    }

    @Override
    Range<C> intersectionWithLessThanRange(LessThanRange<C> other) {
        return intersectionOfClosedOpenAndLessThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtMostRange(AtMostRange<C> other) {
        return intersectionOfClosedOpenAndAtMostRanges(this, other);
    }


    /* ──── Double-dispatch methods for spans ───────────────────────────────────────────────── */

    @Override
    Range<C> spanWithOpenRange(OpenRange<C> other) {
        return spanOfOpenAndClosedOpenRanges(other, this);
    }

    @Override
    Range<C> spanWithClosedRange(ClosedRange<C> other) {
        return spanOfClosedAndClosedOpenRanges(other, this);
    }

    @Override
    Range<C> spanWithOpenClosedRange(OpenClosedRange<C> other) {
        return spanOfOpenClosedAndClosedOpenRanges(other, this);
    }

    @Override
    Range<C> spanWithClosedOpenRange(ClosedOpenRange<C> other) {
        return spanOfClosedOpenRanges(this, other);
    }

    @Override
    Range<C> spanWithGreaterThanRange(GreaterThanRange<C> other) {
        return spanOfClosedOpenAndGreaterThanRanges(this, other);
    }

    @Override
    Range<C> spanWithAtLeastRange(AtLeastRange<C> other) {
        return spanOfClosedOpenAndAtLeastRanges(this, other);
    }

    @Override
    Range<C> spanWithLessThanRange(LessThanRange<C> other) {
        return spanOfClosedOpenAndLessThanRanges(this, other);
    }

    @Override
    Range<C> spanWithAtMostRange(AtMostRange<C> other) {
        return spanOfClosedOpenAndAtMostRanges(this, other);
    }
}
