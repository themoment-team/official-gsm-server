package team.themoment.officialgsm.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Transactional;

@Target(ElementType.TYPE)
@Transactional(rollbackFor = Exception.class)
public @interface UseCaseWithTransaction {
}
