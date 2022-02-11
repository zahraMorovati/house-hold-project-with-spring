package ir.maktab.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomerAspect {

    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String RESET = "\033[0m";  // Text Reset
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Pointcut(value = "execution( * ir.maktab.service.CustomerServiceImpl.findByEmailAndPassword())")
    public void customerService(){}

    @Before(value = "customerService()")
    public void before(JoinPoint ip){
        System.out.println(CYAN_BRIGHT+"customer service -> "+ip.getSignature()+" method is loading ..."+RESET);
    }

    @After(value = "customerService()")
    public void after(JoinPoint ip){
        System.out.println(CYAN_BRIGHT+"customer service -> "+ip.getSignature()+" is worked successfully!"+RESET);
    }

    @Around(value = "customerService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.debug(pjp.getSignature().toShortString() + " Finish: " + (end - start) + "ms");
        return retVal;
    }
}
