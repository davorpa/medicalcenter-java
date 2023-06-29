package medicalcenter.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

public final class Collections
{
	private Collections() {
		throw new UnsupportedOperationException("Utility classes are not instantiable");
	}


	public static <T extends Object & Comparable<? super T>> T min(Collection<? extends T> coll) {
		Iterator<? extends T> iterator = coll.iterator();
		T candidate = iterator.next();

		while (iterator.hasNext()) {
			T next = iterator.next();
			if (next.compareTo(candidate) < 0)
				candidate = next;
		}
		return candidate;
	}


	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> T min(Collection<? extends T> coll, Comparator<? super T> comp) {
		if (comp == null) {
			return (T) min((Collection) coll);
		}

		Iterator<? extends T> iterator = coll.iterator();
		T candidate = iterator.next();

		while (iterator.hasNext()) {
			T next = iterator.next();
			if (comp.compare(next, candidate) < 0)
				candidate = next;
		}
		return candidate;
	}


	public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll) {
		Iterator<? extends T> iterator = coll.iterator();
		T candidate = iterator.next();

		while (iterator.hasNext()) {
			T next = iterator.next();
			if (next.compareTo(candidate) > 0)
				candidate = next;
		}
		return candidate;
	}


	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> T max(Collection<? extends T> coll, Comparator<? super T> comp) {
		if (comp == null) {
			return (T) min((Collection) coll);
		}

		Iterator<? extends T> iterator = coll.iterator();
		T candidate = iterator.next();

		while (iterator.hasNext()) {
			T next = iterator.next();
			if (comp.compare(next, candidate) > 0)
				candidate = next;
		}
		return candidate;
	}


	@SafeVarargs
	public static <T> boolean addAll(Collection<? super T> coll, T... elements) {
		return java.util.Collections.addAll(coll, elements);
	}


	public static <T> String toString(Collection<T> coll) {
		return toString(coll, String::valueOf);
	}


	public static <T> String toString(
			Collection<T> coll,
			Function<? super T, String> mapper)
	{
		return toString(coll, mapper, true);
	}


	public static <T> String toString(
			Collection<T> coll,
			Function<? super T, String> mapper,
			boolean wrapInBrackets)
	{
		Objects.requireNonNull(mapper, "'mapper' to use must not be null");

		if (coll == null)
			return "null";

		if (coll.isEmpty())
			return wrapInBrackets ? "[]" : "";

		StringBuilder buffer = new StringBuilder();
		if (wrapInBrackets) buffer.append('[');

		for (Iterator<T> iterator = coll.iterator(); ;) {
			T item = iterator.next();
			buffer.append(mapper.apply(item));
			if (!iterator.hasNext()) {
				if (wrapInBrackets) buffer.append(']');
				return buffer.toString();
			}
			buffer.append(", ");
		}
	}
}