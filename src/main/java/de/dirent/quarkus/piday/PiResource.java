package de.dirent.quarkus.piday;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@Path("/pi")
@ApplicationScoped
public class PiResource {

    String pi = "";

    @ConfigProperty( name = "piday.pathtodigits" )
    String pathToDigits;

    void onStart(@Observes StartupEvent ev) {   
        System.out.println( "Loading pi into memory..." );            
        byte[] buf = new byte[32000];
        File piDigits = new File( pathToDigits );
        try ( InputStream in = new FileInputStream( piDigits ) ) {
            StringBuffer response = new StringBuffer(1000000);
            while( in.read(buf) > 0 ) {
                response.append( new String(buf, "utf-8" ) );
            }
            pi = response.toString();
            System.out.println( "Loaded pi into memory." );
        } catch( Exception e) {
            System.err.println("Could not read from digits file: " + e.getMessage() );
        }
    }

    void onStop(@Observes ShutdownEvent ev) {               
        System.out.println("The application is stopping...");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String pi() {
        return pi;
    }
}