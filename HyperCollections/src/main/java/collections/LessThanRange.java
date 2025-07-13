package collections;

final class LessThanRange<C extends Comparable<C>> extends Range<C> {

    final C upper;

    LessThanRange(C upper) {
        this.upper = upper;
    }


    /* ──── API instance methods ────────────────────────────────────────────────────────────── */

    @Override
    public boolean contains(C value) {
        return isLessThan(value, upper);
    }

    @Override
    public boolean encloses(Range<C> other) {
        return other.isEnclosedByLessThanRange(this);
    }

    @Override
    public Range<C> intersection(Range<C> other) {
        return other.intersectionWithLessThanRange(this);
    }

    @Override
    public Range<C> span(Range<C> other) {
        return other.spanWithLessThanRange(this);
    }

    @Override
    public String toString() {
        return "(-INF, %s)".formatted(upper);
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
        return isAtMost(upper, other.upper);
    }

    @Override
    boolean isEnclosedByAtMostRange(AtMostRange<C> other) {
        return isAtMost(upper, other.upper);
    }


    /* ──── Double-dispatch methods for intersections ───────────────────────────────────────── */

    @Override
    Range<C> intersectionWithOpenRange(OpenRange<C> other) {
        return intersectionOfOpenAndLessThanRanges(other, this);
    }

    @Override
    Range<C> intersectionWithClosedRange(ClosedRange<C> other) {
        return intersectionOfClosedAndLessThanRanges(other, this);
    }

    @Override
    Range<C> intersectionWithOpenClosedRange(OpenClosedRange<C> other) {
        return intersectionOfOpenClosedAndLessThanRanges(other, this);
    }

    @Override
    Range<C> intersectionWithClosedOpenRange(ClosedOpenRange<C> other) {
        return intersectionOfClosedOpenAndLessThanRanges(other, this);
    }

    @Override
    Range<C> intersectionWithGreaterThanRange(GreaterThanRange<C> other) {
        return intersectionOfGreaterThanAndLessThanRanges(other, this);
    }

    @Override
    Range<C> intersectionWithAtLeastRange(AtLeastRange<C> other) {
        return intersectionOfAtLeastAndLessThanRanges(other, this);
    }

    @Override
    Range<C> intersectionWithLessThanRange(LessThanRange<C> other) {
        return intersectionOfLessThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtMostRange(AtMostRange<C> other) {
        return intersectionOfLessThanAndAtMostRanges(this, other);
    }


    /* ──── Double-dispatch methods for spans ───────────────────────────────────────────────── */

    @Override
    Range<C> spanWithOpenRange(OpenRange<C> other) {
        return spanOfOpenAndLessThanRanges(other, this);
    }

    @Override
    Range<C> spanWithClosedRange(ClosedRange<C> other) {
        return spanOfClosedAndLessThanRanges(other, this);
    }

    @Override
    Range<C> spanWithOpenClosedRange(OpenClosedRange<C> other) {
        return spanOfOpenClosedAndLessThanRanges(other, this);
    }

    @Override
    Range<C> spanWithClosedOpenRange(ClosedOpenRange<C> other) {
        return spanOfClosedOpenAndLessThanRanges(other, this);
    }

    @Override
    Range<C> spanWithGreaterThanRange(GreaterThanRange<C> other) {
        return AllRange.instance();
    }

    @Override
    Range<C> spanWithAtLeastRange(AtLeastRange<C> other) {
        return AllRange.instance();
    }

    @Override
    Range<C> spanWithLessThanRange(LessThanRange<C> other) {
        return spanOfLessThanRanges(this, other);
    }

    @Override
    Range<C> spanWithAtMostRange(AtMostRange<C> other) {
        return spanOfLessThanAndAtMostRanges(this, other);
    }
}
