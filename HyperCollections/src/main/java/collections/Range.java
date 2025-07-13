package collections;

import java.util.Objects;

public abstract sealed class Range<C extends Comparable<C>>
        permits EmptyRange, OpenRange, ClosedRange, OpenClosedRange, ClosedOpenRange,
        GreaterThanRange, AtLeastRange, LessThanRange, AtMostRange, AllRange {

    // Required by Hyperskill tests:
    // Range cannot have a public constructor (even as an abstract class)
    Range() {
    }


    /* ──── API instance methods ────────────────────────────────────────────────────────────── */

    public abstract boolean contains(C value);

    public abstract boolean encloses(Range<C> other);

    public abstract Range<C> intersection(Range<C> other);

    public abstract Range<C> span(Range<C> other);

    public boolean isEmpty() {
        return false;
    }


    /* ──── API static factory methods ──────────────────────────────────────────────────────── */

    public static <C extends Comparable<C>> Range<C> open(C a, C b) {
        if (isAtLeast(a, b)) {
            throw new IllegalArgumentException("Lower endpoint must be less than upper one");
        }
        return new OpenRange<>(a, b);
    }

    public static <C extends Comparable<C>> Range<C> closed(C a, C b) {
        if (isGreaterThan(a, b)) {
            throw new IllegalArgumentException(
                    "Lower endpoint must be less than or equal to upper one");
        }
        return new ClosedRange<>(a, b);
    }

    public static <C extends Comparable<C>> Range<C> openClosed(C a, C b) {
        if (isGreaterThan(a, b)) {
            throw new IllegalArgumentException(
                    "Lower endpoint must be less than or equal to upper one");
        }
        return isEqualTo(a, b) ? EmptyRange.instance() : new OpenClosedRange<>(a, b);
    }

    public static <C extends Comparable<C>> Range<C> closedOpen(C a, C b) {
        if (isGreaterThan(a, b)) {
            throw new IllegalArgumentException(
                    "Lower endpoint must be less than or equal to upper one");
        }
        return isEqualTo(a, b) ? EmptyRange.instance() : new ClosedOpenRange<>(a, b);
    }

    public static <C extends Comparable<C>> Range<C> greaterThan(C a) {
        return new GreaterThanRange<>(Objects.requireNonNull(a));
    }

    public static <C extends Comparable<C>> Range<C> atLeast(C a) {
        return new AtLeastRange<>(Objects.requireNonNull(a));
    }

    public static <C extends Comparable<C>> Range<C> lessThan(C a) {
        return new LessThanRange<>(Objects.requireNonNull(a));
    }

    public static <C extends Comparable<C>> Range<C> atMost(C a) {
        return new AtMostRange<>(Objects.requireNonNull(a));
    }

    public static <C extends Comparable<C>> Range<C> all() {
        return AllRange.instance();
    }


    /* ──── Double-dispatch methods for enclosures ──────────────────────────────────────────── */

    abstract boolean isEnclosedByOpenRange(OpenRange<C> other);

    abstract boolean isEnclosedByClosedRange(ClosedRange<C> other);

    abstract boolean isEnclosedByOpenClosedRange(OpenClosedRange<C> other);

    abstract boolean isEnclosedByClosedOpenRange(ClosedOpenRange<C> other);

    abstract boolean isEnclosedByGreaterThanRange(GreaterThanRange<C> other);

    abstract boolean isEnclosedByAtLeastRange(AtLeastRange<C> other);

    abstract boolean isEnclosedByLessThanRange(LessThanRange<C> other);

    abstract boolean isEnclosedByAtMostRange(AtMostRange<C> other);


    /* ──── Double-dispatch methods for intersections ───────────────────────────────────────── */

    abstract Range<C> intersectionWithOpenRange(OpenRange<C> other);

    abstract Range<C> intersectionWithClosedRange(ClosedRange<C> other);

    abstract Range<C> intersectionWithOpenClosedRange(OpenClosedRange<C> other);

    abstract Range<C> intersectionWithClosedOpenRange(ClosedOpenRange<C> other);

    abstract Range<C> intersectionWithGreaterThanRange(GreaterThanRange<C> other);

    abstract Range<C> intersectionWithAtLeastRange(AtLeastRange<C> other);

    abstract Range<C> intersectionWithLessThanRange(LessThanRange<C> other);

    abstract Range<C> intersectionWithAtMostRange(AtMostRange<C> other);


    /* ──── Double-dispatch methods for spans ───────────────────────────────────────────────── */

    abstract Range<C> spanWithOpenRange(OpenRange<C> other);

    abstract Range<C> spanWithClosedRange(ClosedRange<C> other);

    abstract Range<C> spanWithOpenClosedRange(OpenClosedRange<C> other);

    abstract Range<C> spanWithClosedOpenRange(ClosedOpenRange<C> other);

    abstract Range<C> spanWithGreaterThanRange(GreaterThanRange<C> other);

    abstract Range<C> spanWithAtLeastRange(AtLeastRange<C> other);

    abstract Range<C> spanWithLessThanRange(LessThanRange<C> other);

    abstract Range<C> spanWithAtMostRange(AtMostRange<C> other);


    /* ──── Static helper methods for working with Comparable objects ───────────────────────── */

    // While it is strongly recommended that implementations of Comparable be consistent with
    // equals (i.e., x.compareTo(y) == 0 if and only if x.equals(y)), this is not strictly required:
    // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Comparable.html
    static <C extends Comparable<C>> boolean isEqualTo(C x, C y) {
        return x.compareTo(y) == 0;
    }

    static <C extends Comparable<C>> boolean isLessThan(C x, C y) {
        return x.compareTo(y) < 0;
    }

    static <C extends Comparable<C>> boolean isAtMost(C x, C y) {
        return x.compareTo(y) <= 0;
    }

    static <C extends Comparable<C>> boolean isGreaterThan(C x, C y) {
        return x.compareTo(y) > 0;
    }

    static <C extends Comparable<C>> boolean isAtLeast(C x, C y) {
        return x.compareTo(y) >= 0;
    }


    /* ──── Static helper methods for intersections ─────────────────────────────────────────── */

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenRanges(OpenRange<C> a, OpenRange<C> b) {
        // Relabel if necessary so that a.lower <= b.lower
        if (isGreaterThan(a.lower, b.lower)) {
            var temp = a;
            a = b;
            b = temp;
        }
        return isAtMost(a.upper, b.lower)
                ? EmptyRange.instance()
                : isLessThan(a.upper, b.upper)
                ? new OpenRange<>(b.lower, a.upper)
                : b;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenAndClosedRanges(OpenRange<C> o, ClosedRange<C> c) {
        if (isAtLeast(o.lower, c.lower)) {
            return isAtLeast(o.lower, c.upper)
                    ? EmptyRange.instance()
                    : isGreaterThan(o.upper, c.upper)
                    ? new OpenClosedRange<>(o.lower, c.upper)
                    : o;
        }
        return isAtLeast(c.lower, o.upper)
                ? EmptyRange.instance()
                : isAtLeast(c.upper, o.upper)
                ? new ClosedOpenRange<>(c.lower, o.upper)
                : c;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenAndOpenClosedRanges(OpenRange<C> o, OpenClosedRange<C> oc) {
        if (isAtLeast(o.lower, oc.lower)) {
            return isAtLeast(o.lower, oc.upper)
                    ? EmptyRange.instance()
                    : isGreaterThan(o.upper, oc.upper)
                    ? new OpenClosedRange<>(o.lower, oc.upper)
                    : o;
        }
        return isAtLeast(oc.lower, o.upper)
                ? EmptyRange.instance()
                : isAtLeast(oc.upper, o.upper)
                ? new OpenRange<>(oc.lower, o.upper)
                : oc;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenAndClosedOpenRanges(OpenRange<C> o, ClosedOpenRange<C> co) {
        if (isAtLeast(o.lower, co.lower)) {
            return isAtLeast(o.lower, co.upper)
                    ? EmptyRange.instance()
                    : isGreaterThan(o.upper, co.upper)
                    ? new OpenRange<>(o.lower, co.upper)
                    : o;
        }
        return isAtLeast(co.lower, o.upper)
                ? EmptyRange.instance()
                : isAtLeast(co.upper, o.upper)
                ? new ClosedOpenRange<>(co.lower, o.upper)
                : co;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenAndGreaterThanRanges(OpenRange<C> o, GreaterThanRange<C> gt) {
        return isAtMost(o.upper, gt.lower)
                ? EmptyRange.instance()
                : isLessThan(o.lower, gt.lower)
                ? new OpenRange<>(gt.lower, o.upper)
                : o;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenAndAtLeastRanges(OpenRange<C> o, AtLeastRange<C> al) {
        return isAtMost(o.upper, al.lower)
                ? EmptyRange.instance()
                : isLessThan(o.lower, al.lower)
                ? new ClosedOpenRange<>(al.lower, o.upper)
                : o;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenAndLessThanRanges(OpenRange<C> o, LessThanRange<C> lt) {
        return isAtLeast(o.lower, lt.upper)
                ? EmptyRange.instance()
                : isGreaterThan(o.upper, lt.upper)
                ? new OpenRange<>(o.lower, lt.upper)
                : o;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenAndAtMostRanges(OpenRange<C> o, AtMostRange<C> am) {
        return isAtLeast(o.lower, am.upper)
                ? EmptyRange.instance()
                : isGreaterThan(o.upper, am.upper)
                ? new OpenClosedRange<>(o.lower, am.upper)
                : o;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedRanges(ClosedRange<C> a, ClosedRange<C> b) {
        // Relabel if necessary so that a.lower <= b.lower
        if (isGreaterThan(a.lower, b.lower)) {
            var temp = a;
            a = b;
            b = temp;
        }
        return isLessThan(a.upper, b.lower)
                ? EmptyRange.instance()
                : isLessThan(a.upper, b.upper)
                ? new ClosedRange<>(b.lower, a.upper)
                : b;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedAndOpenClosedRanges(ClosedRange<C> c, OpenClosedRange<C> oc) {
        if (isGreaterThan(c.lower, oc.lower)) {
            return isGreaterThan(c.lower, oc.upper)
                    ? EmptyRange.instance()
                    : isGreaterThan(c.upper, oc.upper)
                    ? new ClosedRange<>(c.lower, oc.upper)
                    : c;
        }
        return isAtLeast(oc.lower, c.upper)
                ? EmptyRange.instance()
                : isGreaterThan(oc.upper, c.upper)
                ? new OpenClosedRange<>(oc.lower, c.upper)
                : oc;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedAndClosedOpenRanges(ClosedRange<C> c, ClosedOpenRange<C> co) {
        if (isGreaterThan(c.lower, co.lower)) {
            return isAtLeast(c.lower, co.upper)
                    ? EmptyRange.instance()
                    : isAtLeast(c.upper, co.upper)
                    ? new ClosedOpenRange<>(c.lower, co.upper)
                    : c;
        }
        return isGreaterThan(co.lower, c.upper)
                ? EmptyRange.instance()
                : isGreaterThan(co.upper, c.upper)
                ? new ClosedRange<>(co.lower, c.upper)
                : co;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedAndGreaterThanRanges(ClosedRange<C> c, GreaterThanRange<C> gt) {
        return isAtMost(c.upper, gt.lower)
                ? EmptyRange.instance()
                : isAtMost(c.lower, gt.lower)
                ? new OpenClosedRange<>(gt.lower, c.upper)
                : c;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedAndAtLeastRanges(ClosedRange<C> c, AtLeastRange<C> al) {
        return isLessThan(c.upper, al.lower)
                ? EmptyRange.instance()
                : isLessThan(c.lower, al.lower)
                ? new ClosedRange<>(al.lower, c.upper)
                : c;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedAndLessThanRanges(ClosedRange<C> c, LessThanRange<C> lt) {
        return isAtLeast(c.lower, lt.upper)
                ? EmptyRange.instance()
                : isAtLeast(c.upper, lt.upper)
                ? new ClosedOpenRange<>(c.lower, lt.upper)
                : c;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedAndAtMostRanges(ClosedRange<C> c, AtMostRange<C> am) {
        return isGreaterThan(c.lower, am.upper)
                ? EmptyRange.instance()
                : isGreaterThan(c.upper, am.upper)
                ? new ClosedRange<>(c.lower, am.upper)
                : c;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenClosedRanges(OpenClosedRange<C> a, OpenClosedRange<C> b) {
        // Relabel if necessary so that a.lower <= b.lower
        if (isGreaterThan(a.lower, b.lower)) {
            var temp = a;
            a = b;
            b = temp;
        }
        return isAtMost(a.upper, b.lower)
                ? EmptyRange.instance()
                : isLessThan(a.upper, b.upper)
                ? new OpenClosedRange<>(b.lower, a.upper)
                : b;
    }

    static <C extends Comparable<C>> Range<C> intersectionOfOpenClosedAndClosedOpenRanges(
            OpenClosedRange<C> oc, ClosedOpenRange<C> co) {
        if (isAtLeast(oc.lower, co.lower)) {
            return isAtLeast(oc.lower, co.upper)
                    ? EmptyRange.instance()
                    : isAtLeast(oc.upper, co.upper)
                    ? new OpenRange<>(oc.lower, co.upper)
                    : oc;
        }
        return isGreaterThan(co.lower, oc.upper)
                ? EmptyRange.instance()
                : isGreaterThan(co.upper, oc.upper)
                ? new ClosedRange<>(co.lower, oc.upper)
                : co;
    }

    static <C extends Comparable<C>> Range<C> intersectionOfOpenClosedAndGreaterThanRanges(
            OpenClosedRange<C> oc, GreaterThanRange<C> gt) {
        return isAtMost(oc.upper, gt.lower)
                ? EmptyRange.instance()
                : isLessThan(oc.lower, gt.lower)
                ? new OpenClosedRange<>(gt.lower, oc.upper)
                : oc;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenClosedAndAtLeastRanges(OpenClosedRange<C> oc, AtLeastRange<C> al) {
        return isLessThan(oc.upper, al.lower)
                ? EmptyRange.instance()
                : isLessThan(oc.lower, al.lower)
                ? new ClosedRange<>(al.lower, oc.upper)
                : oc;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenClosedAndLessThanRanges(OpenClosedRange<C> oc, LessThanRange<C> lt) {
        return isAtLeast(oc.lower, lt.upper)
                ? EmptyRange.instance()
                : isAtLeast(oc.upper, lt.upper)
                ? new OpenRange<>(oc.lower, lt.upper)
                : oc;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfOpenClosedAndAtMostRanges(OpenClosedRange<C> oc, AtMostRange<C> am) {
        return isAtLeast(oc.lower, am.upper)
                ? EmptyRange.instance()
                : isGreaterThan(oc.upper, am.upper)
                ? new OpenClosedRange<>(oc.lower, am.upper)
                : oc;

    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedOpenRanges(ClosedOpenRange<C> a, ClosedOpenRange<C> b) {
        // Relabel if necessary so that a.lower <= b.lower
        if (isGreaterThan(a.lower, b.lower)) {
            var temp = a;
            a = b;
            b = temp;
        }
        return isAtMost(a.upper, b.lower)
                ? EmptyRange.instance()
                : isLessThan(a.upper, b.upper)
                ? new ClosedOpenRange<>(b.lower, a.upper)
                : b;
    }

    static <C extends Comparable<C>> Range<C> intersectionOfClosedOpenAndGreaterThanRanges(
            ClosedOpenRange<C> co, GreaterThanRange<C> gt) {
        return isAtMost(co.upper, gt.lower)
                ? EmptyRange.instance()
                : isAtMost(co.lower, gt.lower)
                ? new OpenRange<>(gt.lower, co.upper)
                : co;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedOpenAndAtLeastRanges(ClosedOpenRange<C> co, AtLeastRange<C> al) {
        return isAtMost(co.upper, al.lower)
                ? EmptyRange.instance()
                : isLessThan(co.lower, al.lower)
                ? new ClosedOpenRange<>(al.lower, co.upper)
                : co;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedOpenAndLessThanRanges(ClosedOpenRange<C> co, LessThanRange<C> lt) {
        return isAtLeast(co.lower, lt.upper)
                ? EmptyRange.instance()
                : isGreaterThan(co.upper, lt.upper)
                ? new ClosedOpenRange<>(co.lower, lt.upper)
                : co;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfClosedOpenAndAtMostRanges(ClosedOpenRange<C> co, AtMostRange<C> am) {
        return isGreaterThan(co.lower, am.upper)
                ? EmptyRange.instance()
                : isGreaterThan(co.upper, am.upper)
                ? new ClosedRange<>(co.lower, am.upper)
                : co;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfGreaterThanRanges(GreaterThanRange<C> a, GreaterThanRange<C> b) {
        return isAtLeast(a.lower, b.lower) ? a : b;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfGreaterThanAndAtLeastRanges(GreaterThanRange<C> gt, AtLeastRange<C> al) {
        return isAtLeast(gt.lower, al.lower) ? gt : al;
    }

    static <C extends Comparable<C>> Range<C> intersectionOfGreaterThanAndLessThanRanges(
            GreaterThanRange<C> gt, LessThanRange<C> lt) {
        return isLessThan(gt.lower, lt.upper)
                ? new OpenRange<>(gt.lower, lt.upper)
                : EmptyRange.instance();
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfGreaterThanAndAtMostRanges(GreaterThanRange<C> gt, AtMostRange<C> am) {
        return isLessThan(gt.lower, am.upper)
                ? new OpenClosedRange<>(gt.lower, am.upper)
                : EmptyRange.instance();
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfAtLeastRanges(AtLeastRange<C> a, AtLeastRange<C> b) {
        return isAtLeast(a.lower, b.lower) ? a : b;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfAtLeastAndLessThanRanges(AtLeastRange<C> al, LessThanRange<C> lt) {
        return isLessThan(al.lower, lt.upper)
                ? new ClosedOpenRange<>(al.lower, lt.upper)
                : EmptyRange.instance();
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfAtLeastAndAtMostRanges(AtLeastRange<C> al, AtMostRange<C> am) {
        return isAtMost(al.lower, am.upper)
                ? new ClosedRange<>(al.lower, am.upper)
                : EmptyRange.instance();
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfLessThanRanges(LessThanRange<C> a, LessThanRange<C> b) {
        return isAtMost(a.upper, b.upper) ? a : b;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfLessThanAndAtMostRanges(LessThanRange<C> a, AtMostRange<C> b) {
        return isAtMost(a.upper, b.upper) ? a : b;
    }

    static <C extends Comparable<C>>
    Range<C> intersectionOfAtMostRanges(AtMostRange<C> a, AtMostRange<C> b) {
        return isAtMost(a.upper, b.upper) ? a : b;
    }


    /* ──── Static helper methods for spans ─────────────────────────────────────────────────── */

    static <C extends Comparable<C>>
    Range<C> spanOfOpenRanges(OpenRange<C> a, OpenRange<C> b) {
        // Relabel if necessary so that a.lower <= b.lower
        if (isGreaterThan(a.lower, b.lower)) {
            var temp = a;
            a = b;
            b = temp;
        }
        return isLessThan(a.upper, b.upper) ? new OpenRange<>(a.lower, b.upper) : a;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenAndClosedRanges(OpenRange<C> o, ClosedRange<C> c) {
        if (isLessThan(o.lower, c.lower)) {
            return isGreaterThan(o.upper, c.upper) ? o : new OpenClosedRange<>(o.lower, c.upper);
        }
        return isGreaterThan(o.upper, c.upper) ? new ClosedOpenRange<>(c.lower, o.upper) : c;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenAndOpenClosedRanges(OpenRange<C> o, OpenClosedRange<C> oc) {
        if (isLessThan(o.lower, oc.lower)) {
            return isGreaterThan(o.upper, oc.upper) ? o : new OpenClosedRange<>(o.lower, oc.upper);
        }
        return isGreaterThan(o.upper, oc.upper) ? new OpenRange<>(oc.lower, o.upper) : oc;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenAndClosedOpenRanges(OpenRange<C> o, ClosedOpenRange<C> co) {
        if (isLessThan(o.lower, co.lower)) {
            return isGreaterThan(o.upper, co.upper) ? o : new OpenRange<>(o.lower, co.upper);
        }
        return isGreaterThan(o.upper, co.upper) ? new ClosedOpenRange<>(co.lower, o.upper) : co;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenAndGreaterThanRanges(OpenRange<C> o, GreaterThanRange<C> gt) {
        return isAtLeast(o.lower, gt.lower) ? gt : new GreaterThanRange<>(o.lower);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenAndAtLeastRanges(OpenRange<C> o, AtLeastRange<C> al) {
        return isAtLeast(o.lower, al.lower) ? al : new GreaterThanRange<>(o.lower);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenAndLessThanRanges(OpenRange<C> o, LessThanRange<C> lt) {
        return isAtMost(o.upper, lt.upper) ? lt : new LessThanRange<>(o.upper);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenAndAtMostRanges(OpenRange<C> o, AtMostRange<C> am) {
        return isAtMost(o.upper, am.upper) ? am : new LessThanRange<>(o.upper);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedRanges(ClosedRange<C> a, ClosedRange<C> b) {
        // Relabel if necessary so that a.lower <= b.lower
        if (isGreaterThan(a.lower, b.lower)) {
            var temp = a;
            a = b;
            b = temp;
        }
        return isLessThan(a.upper, b.upper) ? new ClosedRange<>(a.lower, b.upper) : a;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedAndOpenClosedRanges(ClosedRange<C> c, OpenClosedRange<C> oc) {
        if (isAtMost(c.lower, oc.lower)) {
            return isAtLeast(c.upper, oc.upper) ? c : new ClosedRange<>(c.lower, oc.upper);
        }
        return isAtLeast(c.upper, oc.upper) ? new OpenClosedRange<>(oc.lower, c.upper) : oc;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedAndClosedOpenRanges(ClosedRange<C> c, ClosedOpenRange<C> co) {
        if (isAtMost(c.lower, co.lower)) {
            return isAtLeast(c.upper, co.upper) ? c : new ClosedOpenRange<>(c.lower, co.upper);
        }
        return isAtLeast(c.upper, co.upper) ? new ClosedRange<>(co.lower, c.upper) : co;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedAndGreaterThanRanges(ClosedRange<C> c, GreaterThanRange<C> gt) {
        return isGreaterThan(c.lower, gt.lower) ? gt : new AtLeastRange<>(c.lower);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedAndAtLeastRanges(ClosedRange<C> c, AtLeastRange<C> al) {
        return isAtLeast(c.lower, al.lower) ? al : new AtLeastRange<>(c.lower);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedAndLessThanRanges(ClosedRange<C> c, LessThanRange<C> lt) {
        return isLessThan(c.upper, lt.upper) ? lt : new AtMostRange<>(c.upper);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedAndAtMostRanges(ClosedRange<C> c, AtMostRange<C> am) {
        return isAtMost(c.upper, am.upper) ? am : new AtMostRange<>(c.upper);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenClosedRanges(OpenClosedRange<C> a, OpenClosedRange<C> b) {
        // Relabel if necessary so that a.lower <= b.lower
        if (isGreaterThan(a.lower, b.lower)) {
            var temp = a;
            a = b;
            b = temp;
        }
        return isLessThan(a.upper, b.upper) ? new OpenClosedRange<>(a.lower, b.upper) : a;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenClosedAndClosedOpenRanges(OpenClosedRange<C> oc, ClosedOpenRange<C> co) {
        if (isLessThan(oc.lower, co.lower)) {
            return isAtLeast(oc.upper, co.upper) ? oc : new OpenRange<>(oc.lower, co.upper);
        }
        return isAtLeast(oc.upper, co.upper) ? new ClosedRange<>(co.lower, oc.upper) : co;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenClosedAndGreaterThanRanges(OpenClosedRange<C> oc, GreaterThanRange<C> gt) {
        return isAtLeast(oc.lower, gt.lower) ? gt : new GreaterThanRange<>(oc.lower);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenClosedAndAtLeastRanges(OpenClosedRange<C> oc, AtLeastRange<C> al) {
        return isAtLeast(oc.lower, al.lower) ? al : new GreaterThanRange<>(oc.lower);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenClosedAndLessThanRanges(OpenClosedRange<C> oc, LessThanRange<C> lt) {
        return isLessThan(oc.upper, lt.upper) ? lt : new AtMostRange<>(oc.upper);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfOpenClosedAndAtMostRanges(OpenClosedRange<C> oc, AtMostRange<C> am) {
        return isAtMost(oc.upper, am.upper) ? am : new AtMostRange<>(oc.upper);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedOpenRanges(ClosedOpenRange<C> a, ClosedOpenRange<C> b) {
        // Relabel if necessary so that a.lower <= b.lower
        if (isGreaterThan(a.lower, b.lower)) {
            var temp = a;
            a = b;
            b = temp;
        }
        return isLessThan(a.upper, b.upper) ? new ClosedOpenRange<>(a.lower, b.upper) : a;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedOpenAndGreaterThanRanges(ClosedOpenRange<C> co, GreaterThanRange<C> gt) {
        return isGreaterThan(co.lower, gt.lower) ? gt : new AtLeastRange<>(co.lower);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedOpenAndAtLeastRanges(ClosedOpenRange<C> co, AtLeastRange<C> al) {
        return isAtLeast(co.lower, al.lower) ? al : new AtLeastRange<>(co.lower);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedOpenAndLessThanRanges(ClosedOpenRange<C> co, LessThanRange<C> lt) {
        return isAtMost(co.upper, lt.upper) ? lt : new LessThanRange<>(co.upper);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfClosedOpenAndAtMostRanges(ClosedOpenRange<C> co, AtMostRange<C> am) {
        return isAtMost(co.upper, am.upper) ? am : new LessThanRange<>(co.upper);
    }

    static <C extends Comparable<C>>
    Range<C> spanOfGreaterThanRanges(GreaterThanRange<C> a, GreaterThanRange<C> b) {
        return isAtMost(a.lower, b.lower) ? a : b;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfGreaterThanAndAtLeastRanges(GreaterThanRange<C> gt, AtLeastRange<C> al) {
        return isLessThan(gt.lower, al.lower) ? gt : al;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfAtLeastRanges(AtLeastRange<C> a, AtLeastRange<C> b) {
        return isAtMost(a.lower, b.lower) ? a : b;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfLessThanRanges(LessThanRange<C> a, LessThanRange<C> b) {
        return isAtLeast(a.upper, b.upper) ? a : b;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfLessThanAndAtMostRanges(LessThanRange<C> lt, AtMostRange<C> am) {
        return isGreaterThan(lt.upper, am.upper) ? lt : am;
    }

    static <C extends Comparable<C>>
    Range<C> spanOfAtMostRanges(AtMostRange<C> a, AtMostRange<C> b) {
        return isAtLeast(a.upper, b.upper) ? a : b;
    }
}
