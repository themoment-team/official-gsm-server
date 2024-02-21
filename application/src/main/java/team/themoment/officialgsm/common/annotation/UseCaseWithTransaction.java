package team.themoment.officialgsm.common.annotation;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Transactional(rollbackFor = Exception.class)
public @interface UseCaseWithTransaction {
}
