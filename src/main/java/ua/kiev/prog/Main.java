package ua.kiev.prog;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

enum LoggerType {Console, File};

public class Main {
    static LoggerType loggerType = LoggerType.File;
    static boolean usePreprocessors = true;

    public static void main(String[] args) {
        // case #1
        System.out.println(">>> Sample #1:");

        LoggerAPI api = null;
        if (loggerType == LoggerType.Console)
            api = new ConsoleLoggerAPI();
        else if (loggerType == LoggerType.File)
            api = new FileLoggerAPI("log.txt");

        try {
            api.open();
            try {
                // optional functionality
                if (usePreprocessors) {
                    Preprocessor preprocessor = new DatePreprocessor();
                    api.setPreprocessor(preprocessor);
                }
                Notifier notifier = new Notifier(api);
                notifier.sendSms();
            } finally {
                api.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // case #2
        System.out.println(">>> Sample #2:");

        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        try {
            Notifier notifier = ctx.getBean(Notifier.class);
            notifier.sendSms();
        } finally {
            ctx.close();
        }
    }
}

//>>> Sample #1:
//Open file
//Writing to file: [Wed Sep 12 23:28:12 EEST 2018] Sending sms...
//Writing to file: [Wed Sep 12 23:28:16 EEST 2018] Done!
//Close file
//>>> Sample #2:
//вер. 12, 2018 11:28:17 PM org.springframework.context.annotation.AnnotationConfigApplicationContext prepareRefresh
//INFO: Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@254989ff: startup date [Wed Sep 12 23:28:17 EEST 2018]; root of context hierarchy
//вер. 12, 2018 11:28:17 PM org.springframework.beans.factory.support.DefaultListableBeanFactory registerBeanDefinition
//INFO: Overriding bean definition for bean 'datePreprocessor' with a different definition: replacing [Generic bean: class [ua.kiev.prog.DatePreprocessor]; scope=singleton; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null; defined in file [D:\Projects\SpringIoCSample2\target\classes\ua\kiev\prog\DatePreprocessor.class]] with [Root bean: class [null]; scope=; abstract=false; lazyInit=false; autowireMode=3; dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=appConfig; factoryMethodName=datePreprocessor; initMethodName=null; destroyMethodName=(inferred); defined in ua.kiev.prog.AppConfig]
//Open file
//Writing to file: [Wed Sep 12 23:28:18 EEST 2018] Sending sms...
//Writing to file: [Wed Sep 12 23:28:21 EEST 2018] Done!
//Close file
//вер. 12, 2018 11:28:21 PM org.springframework.context.annotation.AnnotationConfigApplicationContext doClose
//INFO: Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@254989ff: startup date [Wed Sep 12 23:28:17 EEST 2018]; root of context hierarchy
//
//Process finished with exit code 0
