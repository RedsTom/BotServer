package org.redstom.botapi;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BotPlugin {
    String author();

    String id();

    String name();

    String version() default "1.0";

    String description() default "";
}
