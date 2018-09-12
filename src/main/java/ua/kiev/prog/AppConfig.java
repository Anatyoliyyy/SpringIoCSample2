package ua.kiev.prog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppConfig {
    // comment out this bean to test the result
    @Bean
    public Preprocessor datePreprocessor() {
        return new DatePreprocessor();
    }

    @Bean
    public String logFilePath() {
        return "output.log";
    }
}
