package hello;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

@ControllerAdvice()
public class HelloControllerAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    protected void beforeBodyWriteInternal(
            MappingJacksonValue bodyContainer,
            MediaType contentType,
            MethodParameter returnType,
            ServerHttpRequest request,
            ServerHttpResponse response) {
        if (Restricted.class.isAssignableFrom(bodyContainer.getValue().getClass())) {
            Restricted value = (Restricted) bodyContainer.getValue();
            if (value.isRestricted()) {
                System.out.println("setting view to readonly");
                bodyContainer.setSerializationView(View.ReadOnly.class);
                return;
            }
        }
        System.out.println("leaving view alone");
    }
}
