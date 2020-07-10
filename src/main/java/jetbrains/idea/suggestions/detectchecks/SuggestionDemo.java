package jetbrains.idea.suggestions.detectchecks;

import java.util.Objects;

import static jetbrains.idea.suggestions.detectchecks.Utils.*;

public class SuggestionDemo {

    public static void methodOne(SomeObject someObject) {
        check(someObject != null, () -> new IllegalArgumentException("someObject must be defined"));
        doWithObject(someObject.aMethod());
        // someObject.aMethod() <---
        // someObject.aMethod() here is highlighted gray with warning of NPE although someObject here is never null
    }

    public static void methodTwo(SomeObject someObject) {
        Objects.requireNonNull(someObject);
        doWithObject(someObject.aMethod());
        // someObject.aMethod() <---
        // someObject.aMethod() here is not highlighted IDE "understands" it.
    }


    public enum AEnum { A, B, C }

    public static void methodWithMaps() {
        immutableEnumMap(AEnum.class, Enum::name).put("A", AEnum.C); // <--- ide gives a warning  Immutable object is modified
        mutableEnumMap(AEnum.class, Enum::name).put("A", AEnum.C); // <--- ide gives no warnings
    }

    private static void doWithObject(Object o) {
        // on purpose
    }

}
