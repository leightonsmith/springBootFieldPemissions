package hello;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
//        Set<String> excluded = new HashSet<>();
//        excluded.add("hidden");

        SimpleBeanPropertyFilter hideHiddenFilter = new SimpleBeanPropertyFilter() {
            @Override
            public void serializeAsField(
                    Object pojo,
                    JsonGenerator jgen,
                    SerializerProvider provider,
                    PropertyWriter writer) throws Exception {
                System.out.println("serializeAsField ...");
                if (((Restricted) pojo).isRestricted()) {
                    JsonView view = writer.getAnnotation(JsonView.class);
                    boolean isReadonly = false;
                    if (view != null) {
                        for (int i = 0; i < view.value().length; i++) {
                            System.out.println("checking class: " + view.value()[i]);
                            if (view.value()[i].equals(View.ReadOnly.class)) {
                                isReadonly = true;
                                break;
                            }
                        }
                    }
                    if (isReadonly) {
                        super.serializeAsField(pojo, jgen, provider, writer);
                    }
                    else {
                        writer.serializeAsOmittedField(pojo, jgen, provider);
                    }
                } else {
                    super.serializeAsField(pojo, jgen, provider, writer);
                }
            }
        };

        // builder.mixIn(Object.class, RestrictedMixin.class);
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("restrict filter", hideHiddenFilter);
        builder.filters(filterProvider);
        return builder;
    }


}
