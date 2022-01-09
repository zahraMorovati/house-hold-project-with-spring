package ir.maktab.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {DatabaseConfig.class})
@ComponentScan(basePackages = "ir.maktab")
public class SpringConfig {
}
