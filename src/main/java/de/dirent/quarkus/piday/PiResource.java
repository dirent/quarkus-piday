package de.dirent.quarkus.piday;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/pi")
public class PiResource {

    @ConfigProperty( name = "piday.pathtodigits" )
    String pathToDigits;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String pi() {
        return "Reading digits from " + pathToDigits;
    }
}