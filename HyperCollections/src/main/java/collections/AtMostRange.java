package collections;

final class AtMostRange<C extends Comparable<C>> extends Range<C> {

    final C upper;

    AtMostRange(C upper) {
        this.upper = upper;
    }


    /* ──── API instance methods ────────────────────────────────────────────────────────────── */

    @Override
    public boolean contains(C value) {
        return isAtMost(value, upper);
    }

    @Override
    public boolean encloses(Range<C> other) {
        return other.isEnclosedByAtMostRange(this);
    }

    @Override
    public Range<C> intersection(Range<C> other) {
        return other.intersectionWithAtMostRange(this);
    }

    @Override
    public Range<C> span(Range<C> other) {
        return other.spanWithAtMostRange(this);
    }

    @Override
    public String toString() {
        return "(-INF, %s]".formatted(upper);
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
        return isLessThan(upper, other.upper);
    }

    @Override
    boolean isEnclosedByAtMostRange(AtMostRange<C> other) {
        return isAtMost(upper, other.upper);
    }


    /* ──── Double-dispatch methods for intersections ───────────────────────────────────────── */

    @Override
    Range<C> intersectionWithOpenRange(OpenRange<C> other) {
        return intersectionOfOpenAndAtMostRanges(other, this);
    }

    @Override
    Range<C> intersectionWithClosedRange(ClosedRange<C> other) {
        return intersectionOfClosedAndAtMostRanges(other, this);
    }

    @Override
    Range<C> intersectionWithOpenClosedRange(OpenClosedRange<C> other) {
        return intersectionOfOpenClosedAndAtMostRanges(other, this);
    }

    @Override
    Range<C> intersectionWithClosedOpenRange(ClosedOpenRange<C> other) {
        return intersectionOfClosedOpenAndAtMostRanges(other, this);
    }

    @Override
    Range<C> intersectionWithGreaterThanRange(GreaterThanRange<C> other) {
        return intersectionOfGreaterThanAndAtMostRanges(other, this);
    }

    @Override
    Range<C> intersectionWithAtLeastRange(AtLeastRange<C> other) {
        return intersectionOfAtLeastAndAtMostRanges(other, this);
    }

    @Override
    Range<C> intersectionWithLessThanRange(LessThanRange<C> other) {
        return intersectionOfLessThanAndAtMostRanges(other, this);
    }

    @Override
    Range<C> intersectionWithAtMostRange(AtMostRange<C> other) {
        return intersectionOfAtMostRanges(this, other);
    }


    /* ──── Double-dispatch methods for spans ───────────────────────────────────────────────── */

    @Override
    Range<C> spanWithOpenRange(OpenRange<C> other) {
        return spanOfOpenAndAtMostRanges(other, this);
    }

    @Override
    Range<C> spanWithClosedRange(ClosedRange<C> other) {
        return spanOfClosedAndAtMostRanges(other, this);
    }

    @Override
    Range<C> spanWithOpenClosedRange(OpenClosedRange<C> other) {
        return spanOfOpenClosedAndAtMostRanges(other, this);
    }

    @Override
    Range<C> spanWithClosedOpenRange(ClosedOpenRange<C> other) {
        return spanOfClosedOpenAndAtMostRanges(other, this);
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
        return spanOfLessThanAndAtMostRanges(other, this);
    }

    @Override
    Range<C> spanWithAtMostRange(AtMostRange<C> other) {
        return spanOfAtMostRanges(this, other);
    }
}
