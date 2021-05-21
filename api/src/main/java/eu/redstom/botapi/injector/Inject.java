package eu.redstom.botapi.injector;

import java.lang.annotation.*;

/**
 * Annotation to add when you want to auto inject a variable into a method
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Inject {
    /**
     * @return The name of the value to inject
     */
    String value();
}
