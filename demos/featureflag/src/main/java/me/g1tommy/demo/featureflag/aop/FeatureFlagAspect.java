package me.g1tommy.demo.featureflag.aop;

import dev.openfeature.sdk.Client;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@RequiredArgsConstructor
public class FeatureFlagAspect {

	private static final boolean RUN_METHOD_WHEN_EVALUATION_FAILED = false;

	private final Client openfeatureClient;

	@Around("@annotation(booleanFlag)")
	public Object booleanAspect(ProceedingJoinPoint joinPoint, BooleanFlag booleanFlag) throws Throwable {
		final String flagName = booleanFlag.value();

		if ("".equals(flagName)) {
			throw new IllegalArgumentException("Flag cannot be empty");
		}

		if (openfeatureClient.getBooleanValue(flagName, RUN_METHOD_WHEN_EVALUATION_FAILED)) {
			return joinPoint.proceed();
		}

		return null;
	}

}
