package com.base.util.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.base.util.annotation.OperationLog;

@Aspect
@Component
public class OperationLogAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OperationLogAspect.class);
	
	@Pointcut("@annotation(com.base.util.annotation.OperationLog)")
	public void pointCut() {};
	
	@AfterReturning("pointCut()")
	public void successOperationLog(JoinPoint joinPoint) {
        Method method = null;
        try {
        	 MethodSignature signature = (MethodSignature) joinPoint.getSignature();
             method = signature.getMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
        OperationLog ma = method.getAnnotation(OperationLog.class);
		LOGGER.info(ma.value()+"---"+joinPoint.getSignature().getDeclaringType().getSimpleName()
				+"/"+joinPoint.getSignature().getName()+"---"
				);
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
        	LOGGER.info("第" + (i+1) + "个参数为:" + args[i]);
        }
	}
	
	@AfterThrowing(value="pointCut()")
	public void errorOperationLog(JoinPoint joinPoint) {
		LOGGER.info("操作失败");
		
	}

}
