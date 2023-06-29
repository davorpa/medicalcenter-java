package medicalcenter.util;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;

public final class Arrays
{
	private Arrays() {
		throw new UnsupportedOperationException("Utility classes are not instantiable");
	}


	@SuppressWarnings("unchecked")
	public static <T, R extends T> IntFunction<R[]> genericArray(
			IntFunction<T[]> arrayCreator)
	{
		Objects.requireNonNull(arrayCreator);
		return size -> (R[]) arrayCreator.apply(size);
	}


	public static <T> T[] newEmptyInstance(Class<T> classType) {
		return newInstance(classType, 0);
	}


	@SuppressWarnings("unchecked")
	public static <T> T[] newInstance(Class<T> classType, int length) {
		return (T[]) Array.newInstance(classType, length);
	}


	@SafeVarargs
	public static <T> T[] newInstance(Class<T> classType, T... items) {
		T[] array = newInstance(classType, items.length);
		// set items
		for (int i = 0; i < items.length; i++) {
			array[i] = items[i]; //NOSONAR
		}
		return array;
	}


	@SafeVarargs
	public static <T> T[] appendToCopyOf(T[] source, T... items) {
		Objects.requireNonNull(source);
		@SuppressWarnings("unchecked")
		T[] target = (T[]) Array.newInstance(
				source.getClass().getComponentType(),
				source.length + items.length);
		// set source items
		for (int i = 0; i < source.length; i++) {
			target[i] = source[i]; //NOSONAR
		}
		// append new items to target
		int offset = source.length;
		for (int i = 0; i < items.length; i++) {
			target[offset + i] = items[i];
		}
		return target;
	}


	@SafeVarargs
	public static <T> T[] prependToCopyOf(T[] source, T... items) {
		Objects.requireNonNull(source);
		@SuppressWarnings("unchecked")
		T[] target = (T[]) Array.newInstance(
				source.getClass().getComponentType(),
				source.length + items.length);
		// set prepended items
		for (int i = 0; i < items.length; i++) {
			target[i] = items[i]; //NOSONAR
		}
		// append source items to target
		int offset = items.length;
		for (int i = 0; i < source.length; i++) {
			target[offset + i] = source[i];
		}
		return target;
	}


	@SafeVarargs
	public static byte min(byte... arr) {
		final int length = requireNonEmptyArray(arr);
		byte candidate = arr[0];
		for (int i = 1; i < length; i++) {
			byte next = arr[i];
			if (next < candidate)
				candidate = next;
		}
		return candidate;
	}


	@SafeVarargs
	public static short min(short... arr) {
		final int length = requireNonEmptyArray(arr);
		short candidate = arr[0];
		for (int i = 1; i < length; i++) {
			short next = arr[i];
			if (next < candidate)
				candidate = next;
		}
		return candidate;
	}


	@SafeVarargs
	public static int min(int... arr) {
		final int length = requireNonEmptyArray(arr);
		int candidate = arr[0];
		for (int i = 1; i < length; i++) {
			candidate = Math.min(candidate, arr[i]);
		}
		return candidate;
	}


	@SafeVarargs
	public static long min(long... arr) {
		final int length = requireNonEmptyArray(arr);
		long candidate = arr[0];
		for (int i = 1; i < length; i++) {
			candidate = Math.min(candidate, arr[i]);
		}
		return candidate;
	}


	@SafeVarargs
	public static float min(float... arr) {
		final int length = requireNonEmptyArray(arr);
		float candidate = arr[0];
		for (int i = 1; i < length; i++) {
			candidate = Math.min(candidate, arr[i]);
		}
		return candidate;
	}


	@SafeVarargs
	public static double min(double... arr) {
		final int length = requireNonEmptyArray(arr);
		double candidate = arr[0];
		for (int i = 1; i < length; i++) {
			candidate = Math.min(candidate, arr[i]);
		}
		return candidate;
	}


	@SafeVarargs
	public static <T extends Object & Comparable<? super T>> T min(T... arr) {
		final int length = requireNonEmptyArray(arr);
		T candidate = arr[0];
		for (int i = 1; i < length; i++) {
			final T next = arr[i];
			if (next.compareTo(candidate) < 0)
				candidate = next;
		}
		return candidate;
	}


	@SafeVarargs
	public static byte max(byte... arr) {
		final int length = requireNonEmptyArray(arr);
		byte candidate = arr[0];
		for (int i = 1; i < length; i++) {
			byte next = arr[i];
			if (next > candidate)
				candidate = next;
		}
		return candidate;
	}


	@SafeVarargs
	public static short max(short... arr) {
		final int length = requireNonEmptyArray(arr);
		short candidate = arr[0];
		for (int i = 1; i < length; i++) {
			short next = arr[i];
			if (next > candidate)
				candidate = next;
		}
		return candidate;
	}


	@SafeVarargs
	public static int max(int... arr) {
		final int length = requireNonEmptyArray(arr);
		int candidate = arr[0];
		for (int i = 1; i < length; i++) {
			candidate = Math.max(candidate, arr[i]);
		}
		return candidate;
	}


	@SafeVarargs
	public static long max(long... arr) {
		final int length = requireNonEmptyArray(arr);
		long candidate = arr[0];
		for (int i = 1; i < length; i++) {
			candidate = Math.max(candidate, arr[i]);
		}
		return candidate;
	}


	@SafeVarargs
	public static float max(float... arr) {
		final int length = requireNonEmptyArray(arr);
		float candidate = arr[0];
		for (int i = 1; i < length; i++) {
			candidate = Math.max(candidate, arr[i]);
		}
		return candidate;
	}


	@SafeVarargs
	public static double max(double... arr) {
		final int length = requireNonEmptyArray(arr);
		double candidate = arr[0];
		for (int i = 1; i < length; i++) {
			candidate = Math.max(candidate, arr[i]);
		}
		return candidate;
	}


	@SafeVarargs
	public static <T extends Object & Comparable<? super T>> T max(T... arr) {
		final int length = requireNonEmptyArray(arr);
		T candidate = arr[0];
		for (int i = 1; i < length; i++) {
			final T next = arr[i];
			if (next.compareTo(candidate) > 0)
				candidate = next;
		}
		return candidate;
	}


	public static <T> T[] toDistinctArray(T[] arr) {
		Objects.requireNonNull(arr);
		if (arr.length < 2) {
			return arr;
		}
		// Set collection ignore duplicates. LinkedHashSet preserves the order
		Set<T> set = new LinkedHashSet<>(arr.length);
		Collections.addAll(set, arr);
		// transform back to array inferring types dynamically
		@SuppressWarnings("unchecked")
		T[] target = (T[]) Array.newInstance(
				arr.getClass().getComponentType(),
				set.size());
		return set.toArray(target);
	}


	public static int requireNonEmptyArray(Object arr) {
		int length = 0;
		if (arr == null || (length = Array.getLength(arr)) == 0) {
			throw new IllegalArgumentException("empty or nullish array");
		}
		return length;
	}


	public static <T> String toString(T[] arr) {
		return toString(arr, String::valueOf);
	}


	public static <T> String toString(
			T[] arr,
			Function<? super T, String> mapper)
	{
		return toString(arr, mapper, true);
	}


	public static <T> String toString(
			T[] arr,
			Function<? super T, String> mapper,
			boolean wrapInBrackets)
	{
		Objects.requireNonNull(mapper, "'mapper' to use must not be null");

		if (arr == null)
			return "null";

		int iMax = arr.length - 1;
		if (iMax == -1)
			return wrapInBrackets ? "[]" : "";

		StringBuilder buffer = new StringBuilder();
		if (wrapInBrackets) buffer.append('[');
		for (int i = 0; ; i++) {
			buffer.append(mapper.apply(arr[i]));
			if (i == iMax) {
				if (wrapInBrackets) buffer.append(']');
				return buffer.toString();
			}
			buffer.append(", ");
		}
	}
}