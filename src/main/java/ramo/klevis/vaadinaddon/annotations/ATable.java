package ramo.klevis.vaadinaddon.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ATable {

	String caption() default "";

	int pageLength() default 5;

	String width() default "";

	String height() default "";

	boolean imetdiate() default true;

	boolean selected() default true;

	boolean multiSelect() default false;

}
