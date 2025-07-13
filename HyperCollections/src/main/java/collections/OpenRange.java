package collections;

final class OpenRange<C extends Comparable<C>> extends Range<C> {

    final C lower;
    final C upper;

    OpenRange(C lower, C upper) {
        this.lower = lower;
        this.upper = upper;
    }


    /* ──── API instance methods ────────────────────────────────────────────────────────────── */

    @Override
    public boolean contains(C value) {
        return isLessThan(lower, value) && isLessThan(value, upper);
    }

    @Override
    public boolean encloses(Range<C> other) {
        return other.isEnclosedByOpenRange(this);
    }

    @Override
    public Range<C> intersection(Range<C> other) {
        return other.intersectionWithOpenRange(this);
    }

    @Override
    public Range<C> span(Range<C> other) {
        return other.spanWithOpenRange(this);
    }

    @Override
    public String toString() {
        return "(%s, %s)".formatted(lower, upper);
    }


    /* ──── Double-dispatch methods for enclosures ──────────────────────────────────────────── */

    @Override
    boolean isEnclosedByOpenRange(OpenRange<C> other) {
        return isAtLeast(lower, other.lower) && isAtMost(upper, other.upper);
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
        return isAtLeast(lower, other.lower) && isAtMost(upper, other.upper);
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
        return isAtMost(upper, other.upper);
    }

    @Override
    boolean isEnclosedByAtMostRange(AtMostRange<C> other) {
        return isAtMost(upper, other.upper);
    }


    /* ──── Double-dispatch methods for intersections ───────────────────────────────────────── */

    @Override
    Range<C> intersectionWithOpenRange(OpenRange<C> other) {
        return intersectionOfOpenRanges(this, other);
    }

    @Override
    Range<C> intersectionWithClosedRange(ClosedRange<C> other) {
        return intersectionOfOpenAndClosedRanges(this, other);
    }

    @Override
    Range<C> intersectionWithOpenClosedRange(OpenClosedRange<C> other) {
        return intersectionOfOpenAndOpenClosedRanges(this, other);
    }

    @Override
    Range<C> intersectionWithClosedOpenRange(ClosedOpenRange<C> other) {
        return intersectionOfOpenAndClosedOpenRanges(this, other);
    }

    @Override
    Range<C> intersectionWithGreaterThanRange(GreaterThanRange<C> other) {
        return intersectionOfOpenAndGreaterThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtLeastRange(AtLeastRange<C> other) {
        return intersectionOfOpenAndAtLeastRanges(this, other);
    }

    @Override
    Range<C> intersectionWithLessThanRange(LessThanRange<C> other) {
        return intersectionOfOpenAndLessThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtMostRange(AtMostRange<C> other) {
        return intersectionOfOpenAndAtMostRanges(this, other);
    }


    /* ──── Double-dispatch methods for spans ───────────────────────────────────────────────── */

    @Override
    Range<C> spanWithOpenRange(OpenRange<C> other) {
        return spanOfOpenRanges(this, other);
    }

    @Override
    Range<C> spanWithClosedRange(ClosedRange<C> other) {
        return spanOfOpenAndClosedRanges(this, other);
    }

    @Override
    Range<C> spanWithOpenClosedRange(OpenClosedRange<C> other) {
        return spanOfOpenAndOpenClosedRanges(this, other);
    }

    @Override
    Range<C> spanWithClosedOpenRange(ClosedOpenRange<C> other) {
        return spanOfOpenAndClosedOpenRanges(this, other);
    }

    @Override
    Range<C> spanWithGreaterThanRange(GreaterThanRange<C> other) {
        return spanOfOpenAndGreaterThanRanges(this, other);
    }

    @Override
    Range<C> spanWithAtLeastRange(AtLeastRange<C> other) {
        return spanOfOpenAndAtLeastRanges(this, other);
    }

    @Override
    Range<C> spanWithLessThanRange(LessThanRange<C> other) {
        return spanOfOpenAndLessThanRanges(this, other);
    }

    @Override
    Range<C> spanWithAtMostRange(AtMostRange<C> other) {
        return spanOfOpenAndAtMostRanges(this, other);
    }
}
