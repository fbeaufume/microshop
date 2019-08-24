package com.adeliosys.microshop.common.tracing;

import brave.ScopedSpan;
import brave.Tracer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@Aspect
@ConditionalOnProperty("microshop.methodTracing.enabled")
public class ZipkinTracingAspect {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private Tracer tracer;

    /**
     * Wrap the target method with a span.
     */
    @Around("execution(public * com.adeliosys..*(..))")
    public Object traceAllMethodCalls(ProceedingJoinPoint joinPoint) throws Throwable {
        final String methodSignature = joinPoint.getSignature().toShortString();
        final ScopedSpan span = tracer.startScopedSpan(methodSignature);

        if (span != null) {
            span.annotate("Enter method");
            try {
                return joinPoint.proceed();
            } catch (Throwable error) {
                span.error(error);
                throw error;
            } finally {
                span.annotate("Exit method");
                span.finish();
            }
        } else {
            return joinPoint.proceed();
        }
    }
}
