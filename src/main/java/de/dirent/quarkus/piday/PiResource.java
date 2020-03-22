package de.dirent.quarkus.piday;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

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
        byte[] head = new byte[0];
        File piDigits = new File( pathToDigits );
        try ( InputStream in = new FileInputStream( piDigits ) ) {
            head = in.readNBytes(100);
            return new String( head, "utf-8" );
        } catch( Exception e) {
            return "Could not read from digits file: " + e.getMessage();
        }
    }
}