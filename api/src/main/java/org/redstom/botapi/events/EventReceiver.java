package org.redstom.botapi.events;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventReceiver {

    Class<? extends IEvent> value();

}
