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
 * 验证字段表名<br/>
 * 正确的表名前面必须为oa_dict_且后面全为类型的英文表达
 *
 */
@ConstraintComposition(CompositionType.OR)
@Pattern(regexp = "oa_dict_[a-z]+")
//@Null
//@Length(min = 0, max = 11)
@Documented
@Constraint(validatedBy = {})
@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface dictTableName {

    String message() default "表名格式不规范";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
