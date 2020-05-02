package pl.north93.serializer.platform.template;

public final class TemplatePriority
{
    // things critical for serializer to work
    public static final int HIGHEST = 1_000_000;

    // overrides, special cases for optimization
    public static final int HIGH = 1_000;

    // Normal priority for all other things
    public static final int NORMAL = 0;
}