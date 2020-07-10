package jetbrains.idea.suggestions.detectchecks;


import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public final class Utils {

    private Utils() {
    }

    public static <E extends Throwable> void check(boolean assumption, Supplier<E> exceptionSupplier) throws E {
        requireNonNull(exceptionSupplier);
        if (!assumption) {
            throw exceptionSupplier.get();
        }
    }

    public static <K, E extends Enum<E>> Map<K, E> immutableEnumMap(Class<E> clazz, Function<E, K> keyFunction) {
        check(clazz != null, () -> new IllegalArgumentException("enum class is not defined"));
        check(keyFunction != null, () -> new IllegalArgumentException("key function is not defined"));
        requireNonNull(clazz);
        requireNonNull(keyFunction);
        return unmodifiableMap(stream(clazz.getEnumConstants()).collect(toMap(keyFunction, identity(), (e, e2) -> {
            throw new IllegalStateException(
                    "Key must be unique " + e + " " + keyFunction
                            .apply(e));
        })));
    }

    public static <K, E extends Enum<E>> Map<K, E> mutableEnumMap(Class<E> clazz, Function<E, K> keyFunction) {
        check(clazz != null, () -> new IllegalArgumentException("enum class is not defined"));
        check(keyFunction != null, () -> new IllegalArgumentException("key function is not defined"));
        requireNonNull(clazz);
        requireNonNull(keyFunction);
        return stream(clazz.getEnumConstants()).collect(toMap(keyFunction, identity(), (e, e2) -> {
            throw new IllegalStateException(
                    "Key must be unique " + e + " " + keyFunction
                            .apply(e));
        }));
    }


}
