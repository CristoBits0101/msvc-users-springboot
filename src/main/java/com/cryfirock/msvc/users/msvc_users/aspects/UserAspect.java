package com.cryfirock.msvc.users.msvc_users.aspects;

import org.aspectj.lang.annotation.Aspect;

@Aspect
public class UserAspect {
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.andres.curso.springboot.app.aop.springbootaop.services.GreetingService.*(..))")
    public void loggerBefore(JoinPoint joinPoint) {
        
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Antes: " + method + " con los argumentos " + args);
    }
    
    @After("execution(* com.andres.curso.springboot.app.aop.springbootaop.services.GreetingService.*(..))")
    public void loggerAfter(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Despues: " + method + " con los argumentos " + args);
    }
    
    @AfterReturning("execution(* com.andres.curso.springboot.app.aop.springbootaop.services.GreetingService.*(..))")
    public void loggerAfterReturningr(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Despues de retornar: " + method + " con los argumentos " + args);
    }
    
    @AfterThrowing("execution(* com.andres.curso.springboot.app.aop.springbootaop.services.GreetingService.*(..))")
    public void loggerAfterThrowing(JoinPoint joinPoint) {

        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Despues lanzar la excepcion: " + method + " con los argumentos " + args);
    }
    
    @Around("execution(* com.andres.curso.springboot.app.aop.springbootaop.services.*.*(..))")
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable{
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        Object result = null;
        try {
            logger.info("El metodo " + method + "() con los parametros " + args);
            result = joinPoint.proceed();
            logger.info("El metodo " + method + "() retorna el resultado: " + result);
            return result;
        } catch (Throwable e) {
            logger.error("Error en la llamada del metodo " + method + "()");
            throw e;
        }

    }

}
