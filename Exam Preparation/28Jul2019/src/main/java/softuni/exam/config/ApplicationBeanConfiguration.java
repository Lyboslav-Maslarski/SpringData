package softuni.exam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.validation.Validation;
import javax.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import softuni.exam.util.FileUtil;
import softuni.exam.util.FileUtilImpl;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.ValidatorUtilImpl;


@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    @Primary
    public ValidatorUtil validationUtil() {
        return new ValidatorUtilImpl(validator());
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
