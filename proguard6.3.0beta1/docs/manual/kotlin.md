ProGuard can adapt the metadata stored with Kotlin classes, to make sure no
sensitive names remain in the code when preserving the @Metadata annotation.

## Limitations

This feature is currently in beta. Not all processing of ProGuard
is guaranteed to be correctly reflected in the Kotlin metadata.

In this beta version, the following features are supported:

* Shrinking
* Name obfuscation
* Integrity checking for Kotlin metadata
* Preserving kept API in library projects
* Obfuscate toString of data classes (see [**Data Classes**](#dataclasses))
* Obfuscate strings of Java interop checks (see [**Intrinsics**](#intrinsics))

Notably, the following features are not yet supported:

* Optimization (including Logging Removal)
* Delegated properties


## Configuration

Use the `-adaptkotlinmetadata` rule to toggle processing of Kotlin Metadata.
The adapted metadata will be written out to the @Metadata annotation if it
is kept.

### App Projects

Keeping Kotlin metadata might be necessary when using the kotlin-reflect library.
This is for example the case when using Jackson to (de)serialize Kotlin data
classes, which transitively depends on kotlin-reflect.
```
# Adapt Kotlin metadata.
-adaptkotlinmetadata

# Preserve Kotlin Metadata but allow obfuscation.
-keep class kotlin.Metadata { *; }

# Temporarily disable optimization on Kotlin classes.
-keep,includecode,allowobfuscation,allowshrinking @kotlin.Metadata class ** { *; }
```

!!! note ""
    By default, the `kotlin.Metadata` annotations will be removed in application
    projects unless explicitly kept via a `-keep class kotlin.Metadata { *; }` rule in your
    configuration.


### Library Projects
When developing an SDK that exposes Kotlin-specific features to its users, you need
to preserve its metadata. Examples are suspendable functions, default parameter values,
type aliases etc.

```
# Adapt Kotlin metadata.
-adaptkotlinmetadata

# Adapt information about Kotlin file facades.
-adaptresourcefilecontents **.kotlin_module

# Preserve Kotlin metadata.
-keep class kotlin.Metadata { *; }

# Temporarily disable optimization on Kotlin classes.
-keep,includecode,allowobfuscation,allowshrinking @kotlin.Metadata class ** { *; }
```

!!! note ""
    By default, the `kotlin.Metadata` annotations will be removed in library
    projects unless explicitly kept via a `-keep class kotlin.Metadata { *; }` rule.

## Obfuscation details

### Data Classes {: #dataclasses}

Data classes in Kotlin have an auto-generated `toString` method that lists all properties
of the class and their value. ProGuard automatically detects these classes and adapts
the names of properties to their obfuscated counterpart.

### Intrinsics {: #intrinsics}

To better support java interoperability, Kotlin injects numerous method calls to e.g.
check that a parameter is not null when it wasn't marked as Nullable. In pure Kotlin
codebases, these injected method calls are unnecessary and instead leak information
via their parameters (e.g. names of checked parameters).

ProGuard automatically detects calls to these methods and removes the Strings to ensure that the resulting code contains no references to original parameter names, member names etc.
