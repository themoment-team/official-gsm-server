package team.themoment.officialgsm.common.annotation;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Transactional(readOnly = true, rollbackFor = Exception.class)
public @interface UseCaseWithReadOnlyTransaction {
}
