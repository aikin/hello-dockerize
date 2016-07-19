package com.ulaijn.HelloWorld.resources;


import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Component
@Path("/helloworld")
public class HelloWorldResource {
    @GET
    public String helloWorld() {
        return "Hello World!";
    }
}
