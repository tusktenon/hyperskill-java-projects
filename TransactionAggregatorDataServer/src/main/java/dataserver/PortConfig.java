package dataserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class PortConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Value("${dataservice.id}")
    private int id;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(id == 1 ? 8889 : 8888);
    }
}
