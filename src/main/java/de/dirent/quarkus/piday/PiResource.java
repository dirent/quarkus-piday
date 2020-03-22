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
        byte[] buf = new byte[32000];
        File piDigits = new File( pathToDigits );
        try ( InputStream in = new FileInputStream( piDigits ) ) {
            StringBuffer response = new StringBuffer(1000000);
            while( in.read(buf) > 0 ) {
                response.append( new String(buf, "utf-8" ) );
            }
            return response.toString();
        } catch( Exception e) {
            return "Could not read from digits file: " + e.getMessage();
        }
    }
}