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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.json.simple.JSONObject;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@Path("/pi")
@ApplicationScoped
public class PiResource {

    protected static final Log logger = LogFactory.getLog( PiResource.class );

    String pi = "";

    @ConfigProperty( name = "piday.pathtodigits" )
    String pathToDigits;

    void onStart(@Observes StartupEvent ev) {   
        logger.debug( "Loading pi into memory..." );            
        byte[] buf = new byte[32000];
        File piDigits = new File( pathToDigits );
        try ( InputStream in = new FileInputStream( piDigits ) ) {
            StringBuffer response = new StringBuffer(1000000000);
            while( in.read(buf) > 0 ) {
                response.append( new String(buf, "utf-8" ) );
            }
            pi = response.toString();
            logger.info( "Loaded pi into memory." );
        } catch( Exception e) {
            logger.error("Could not read from digits file: " + e.getMessage() );
        }
    }

    void onStop(@Observes ShutdownEvent ev) {               
        logger.debug("The application is stopping...");
    }

    public int indexOf( String search, int start ) {
        if( search == null ) {
            return -1;
        }
        if( pi == null  ||  pi.length() < start ) {
            return -1;
        }
        if( search.length() == 0 ) {
            return 0;
        }
        for( int i=start; i<pi.length(); i++ ) {
            for( int j=0; j<search.length(); j++ ) {
                if( search.charAt(j) != pi.charAt(i+j) ) {
                    break;
                }
                if( j == search.length()-1 ) {
                    return i-1;
                }
            }
        }
        return -1;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String pi( @QueryParam("search") String search ) {
        if( search == null ) {
            return pi.substring(0,Math.min(pi.length()-1, 1000002));
        }
        return ""+indexOf(search,2);
    }

    @GET
    @Path( "/search/{digits}")
    @Produces(MediaType.APPLICATION_JSON)
    public String search( @PathParam String digits ) {
        JSONObject result = new JSONObject();
        result.put( "index", indexOf(digits,2) );
        result.put( "search", digits );
        return result.toJSONString();
    }
}