package eu.redstom.botapi;

import java.lang.annotation.*;

/**
 * Defines the main class of the plugin
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BotPlugin {
    /**
     * @return The author of the plugin
     */
    String author();

    /**
     * @return The id of the plugin. Might be unique in the plugin folder
     */
    String id();

    /**
     * @return The name of the plugin
     */
    String name();

    /**
     * @return The version of the plugin
     */
    String version() default "1.0";

    /**
     * @return The description of the plugin
     */
    String description() default "";

    /**
     * @return If the plugin uses the inject methods
     */
    boolean needsInject() default true;

    /**
     * @return The list of plugin that must be loaded before this plugin is
     */
    String[] loadAfter() default {};
}
