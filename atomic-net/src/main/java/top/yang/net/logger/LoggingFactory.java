package top.yang.net.logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import top.yang.net.Level;

public class LoggingFactory {

    private LoggingFactory() {
        // disable construction
    }

    public static LoggingInterceptor getLog(Class<? extends LoggingInterceptor> clazz, Level level) {
        try {
            Constructor<? extends LoggingInterceptor> constructor = clazz.getDeclaredConstructor(Level.class);
            return constructor.newInstance(level);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new LoggingException("Error creating logger for logger " + clazz.getName() + ".  Cause: " + e, e);
        }
    }

//    public static Log getLog(String logger) {
//        try {
//            return logConstructor.newInstance(logger);
//        } catch (Throwable t) {
//            throw new LogException("Error creating logger for logger " + logger + ".  Cause: " + t, t);
//        }
//    }
}
