package collections;

final class GreaterThanRange<C extends Comparable<C>> extends Range<C> {

    final C lower;

    GreaterThanRange(C lower) {
        this.lower = lower;
    }


    /* ──── API instance methods ────────────────────────────────────────────────────────────── */

    @Override
    public boolean contains(C value) {
        return isGreaterThan(value, lower);
    }

    @Override
    public boolean encloses(Range<C> other) {
        return other.isEnclosedByGreaterThanRange(this);
    }

    @Override
    public Range<C> intersection(Range<C> other) {
        return other.intersectionWithGreaterThanRange(this);
    }

    @Override
    public Range<C> span(Range<C> other) {
        return other.spanWithGreaterThanRange(this);
    }

    @Override
    public String toString() {
        return "(%s, INF)".formatted(lower);
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
        return isAtLeast(lower, other.lower);
    }

    @Override
    boolean isEnclosedByAtLeastRange(AtLeastRange<C> other) {
        return isAtLeast(lower, other.lower);
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
        return intersectionOfOpenAndGreaterThanRanges(other, this);
    }

    @Override
    Range<C> intersectionWithClosedRange(ClosedRange<C> other) {
        return intersectionOfClosedAndGreaterThanRanges(other, this);
    }

    @Override
    Range<C> intersectionWithOpenClosedRange(OpenClosedRange<C> other) {
        return intersectionOfOpenClosedAndGreaterThanRanges(other, this);
    }

    @Override
    Range<C> intersectionWithClosedOpenRange(ClosedOpenRange<C> other) {
        return intersectionOfClosedOpenAndGreaterThanRanges(other, this);
    }

    @Override
    Range<C> intersectionWithGreaterThanRange(GreaterThanRange<C> other) {
        return intersectionOfGreaterThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtLeastRange(AtLeastRange<C> other) {
        return intersectionOfGreaterThanAndAtLeastRanges(this, other);
    }

    @Override
    Range<C> intersectionWithLessThanRange(LessThanRange<C> other) {
        return intersectionOfGreaterThanAndLessThanRanges(this, other);
    }

    @Override
    Range<C> intersectionWithAtMostRange(AtMostRange<C> other) {
        return intersectionOfGreaterThanAndAtMostRanges(this, other);
    }


    /* ──── Double-dispatch methods for spans ───────────────────────────────────────────────── */

    @Override
    Range<C> spanWithOpenRange(OpenRange<C> other) {
        return spanOfOpenAndGreaterThanRanges(other, this);
    }

    @Override
    Range<C> spanWithClosedRange(ClosedRange<C> other) {
        return spanOfClosedAndGreaterThanRanges(other, this);
    }

    @Override
    Range<C> spanWithOpenClosedRange(OpenClosedRange<C> other) {
        return spanOfOpenClosedAndGreaterThanRanges(other, this);
    }

    @Override
    Range<C> spanWithClosedOpenRange(ClosedOpenRange<C> other) {
        return spanOfClosedOpenAndGreaterThanRanges(other, this);
    }

    @Override
    Range<C> spanWithGreaterThanRange(GreaterThanRange<C> other) {
        return spanOfGreaterThanRanges(this, other);
    }

    @Override
    Range<C> spanWithAtLeastRange(AtLeastRange<C> other) {
        return spanOfGreaterThanAndAtLeastRanges(this, other);
    }

    @Override
    Range<C> spanWithLessThanRange(LessThanRange<C> other) {
        return AllRange.instance();
    }

    @Override
    Range<C> spanWithAtMostRange(AtMostRange<C> other) {
        return AllRange.instance();
    }
}
