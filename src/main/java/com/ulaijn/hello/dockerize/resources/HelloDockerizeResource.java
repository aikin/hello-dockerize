package com.ulaijn.hello.dockerize.resources;


import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("/hello-dockerize")
public class HelloDockerizeResource {
    @GET
    public String helloDockerize() {
        return "Hello Dockerize!";
    }
}
