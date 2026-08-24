package com.pioneers.picturepublishingservice;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initializer for the Picture Publishing Service application.
 *
 * @author esraa
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PicturePublishingServiceApplication.class);
    }

}
