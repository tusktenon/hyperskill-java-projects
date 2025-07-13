package collections;

final class OpenClosedRange<C extends Comparable<C>> extends Range<C> {

    final C lower;
    final C upper;

    OpenClosedRange(C lower, C upper) {
        this.lower = lower;
        this.upper = upper;
    }


    /* ──── API instance methods ────────────────────────────────────────────────────────────── */

    @Override
    public boolean contains(C value) {
        return isLessThan(lower, value) && isAtMost(value, upper);
    }

    @Override
    public boolean encloses(Range<C> other) {
        return other.isEnclosedByOpenClosedRange(this);
    }

    @Override
    public Range<C> intersection(Range<C> other) {
        return other.intersectionWithOpenClosedRange(this);
    }

    @Override
    public Range<C> span(Range<C> other) {
        return other.spanWithOpenClosedRange(this);
    }

    @Override
    public String toString() {
        return "(%s, %s]".formatted(lower, upper);
    }


    /* ──── Double-dispatch methods for enclosures ──────────────────────────────────────────── */

    @Override
    boolean isEnclosedByOpenRange(OpenRange<C> other) {
        return isAtLeast(lower, other.lower) && isLessThan(upper, other.upper);
    }

    @Override
    boolean isEnclosedByClosedRange(ClosedRange<C> other) {
        return isAtLeast(lower, other.lower) && isAtMost(upper, other.upper);
    }

    @Override
    boolean isEnclosedByOpenClosedRange(OpenClosedRange<C> other) {
        return isAtLeast(lower, other.lower) && isAtMost(upper, other.upper);
    }

    @Override
    boolean isEnclosedByClosedOpenRange(ClosedOpenRange<C> other) {
        return isAtLeast(lower, other.lower) && isLessThan(upper, other.upper);
    }

    @Override
    boolean isEnclosedByGreaterThanRange(GreaterThanRange<C> other) {
        return isAtLeast(lower, other.lower);
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
        return intersectionOfOpenAndOpenClosedRanges(other, this);
    }

    @Override
    Range<C> intersectionWithClosedRange(ClosedRange<C> other) {
        return intersectionOfClosedAndOpenClosedRanges(other, this);
    }

    @Override
    Range<C> intersectionWithOpenClosedRange(OpenClosedRange<C> other) {
        return intersectionOfOpenClosedRanges(this, other);
    }

    @Override
    Range<C> intersectionWithClosedOpenRange(ClosedOpenRange<C> other) {
        return intersectionOfOpenClosedAndClosedOpenRanges(this, other);
    }

    @Override
    Range<C> intersectionWithGreaterThanRange(GreaterThanRange<C> other) {
        return intersectionOfOpenClosedAndGreaterThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtLeastRange(AtLeastRange<C> other) {
        return intersectionOfOpenClosedAndAtLeastRanges(this, other);
    }

    @Override
    Range<C> intersectionWithLessThanRange(LessThanRange<C> other) {
        return intersectionOfOpenClosedAndLessThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtMostRange(AtMostRange<C> other) {
        return intersectionOfOpenClosedAndAtMostRanges(this, other);
    }


    /* ──── Double-dispatch methods for spans ───────────────────────────────────────────────── */

    @Override
    Range<C> spanWithOpenRange(OpenRange<C> other) {
        return spanOfOpenAndOpenClosedRanges(other, this);
    }

    @Override
    Range<C> spanWithClosedRange(ClosedRange<C> other) {
        return spanOfClosedAndOpenClosedRanges(other, this);
    }

    @Override
    Range<C> spanWithOpenClosedRange(OpenClosedRange<C> other) {
        return spanOfOpenClosedRanges(this, other);
    }

    @Override
    Range<C> spanWithClosedOpenRange(ClosedOpenRange<C> other) {
        return spanOfOpenClosedAndClosedOpenRanges(this, other);
    }

    @Override
    Range<C> spanWithGreaterThanRange(GreaterThanRange<C> other) {
        return spanOfOpenClosedAndGreaterThanRanges(this, other);
    }

    @Override
    Range<C> spanWithAtLeastRange(AtLeastRange<C> other) {
        return spanOfOpenClosedAndAtLeastRanges(this, other);
    }

    @Override
    Range<C> spanWithLessThanRange(LessThanRange<C> other) {
        return spanOfOpenClosedAndLessThanRanges(this, other);
    }

    @Override
    Range<C> spanWithAtMostRange(AtMostRange<C> other) {
        return spanOfOpenClosedAndAtMostRanges(this, other);
    }
}
