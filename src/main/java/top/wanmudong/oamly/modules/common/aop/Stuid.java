package top.wanmudong.oamly.modules.common.aop;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * 验证学号<br/>
 * 正确的学号前四位是年份，后6位是编号
 *
 */
@ConstraintComposition(CompositionType.OR)
@Pattern(regexp = "(19|20)\\d{2}\\d{6}")
//@Null
//@Length(min = 0, max = 11)
@Documented
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface Stuid {
    String message() default "非本校学生";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
