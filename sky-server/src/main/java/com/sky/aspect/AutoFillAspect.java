package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Component
@Aspect
public class AutoFillAspect {

    @Before("@annotation(com.sky.annotation.AutoFill)")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充...");

        // 1. 拿到 @AutoFill 注解，知道是 INSERT 还是 UPDATE
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        // 2. 拿到被拦截方法的第一个参数（实体对象）
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];

        // 3. 准备要填进去的值
        LocalDateTime now = LocalDateTime.now();
        Long currentUserId = BaseContext.getCurrentId();

        // 4. 根据操作类型，用反射调用 setter 给实体填值
        try {
            if (operationType == OperationType.INSERT) {
                invokeSetter(entity, AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class, now);
                invokeSetter(entity, AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class, now);
                invokeSetter(entity, AutoFillConstant.SET_CREATE_USER, Long.class, currentUserId);
                invokeSetter(entity, AutoFillConstant.SET_UPDATE_USER, Long.class, currentUserId);
            } else if (operationType == OperationType.UPDATE) {
                invokeSetter(entity, AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class, now);
                invokeSetter(entity, AutoFillConstant.SET_UPDATE_USER, Long.class, currentUserId);
            }
        } catch (Exception e) {
            log.error("公共字段自动填充失败：{}", e.getMessage(), e);
        }
    }

    /**
     * 反射调用实体类的 setter 方法
     * 相当于 entity.setXxx(value)
     */
    private void invokeSetter(Object entity, String methodName, Class<?> paramType, Object value) throws Exception {
        Method setter = entity.getClass().getDeclaredMethod(methodName, paramType);
        setter.invoke(entity, value);
    }
}