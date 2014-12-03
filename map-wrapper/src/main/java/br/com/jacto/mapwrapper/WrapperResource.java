package br.com.jacto.mapwrapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.omg.CORBA.portable.ResponseHandler;

import br.com.jacto.onet.map.wrapper.builders.MapRequestBuilder;
import br.com.jacto.onet.map.wrapper.pojo.Filters;
import br.com.jacto.onet.map.wrapper.pojo.Join;
import br.com.jacto.onet.map.wrapper.pojo.Operators;
import br.com.jacto.onet.map.wrapper.pojo.Table;
import br.com.jacto.onet.map.wrapper.pojo.WrapperFilter;
import br.com.nova.core.ChronometerUtil;

/**
 * Root resource (exposed at "resource" path)
 */
@Path("resource")
public class WrapperResource {
	Logger log = Logger.getLogger(WrapperResource.class.getSimpleName()); 
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/getIt")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @GET
    @Path("/getSchema")
    @Produces(MediaType.APPLICATION_JSON)
    public WrapperFilter getSchema() {
    	log.info("init getSchema");
    	ChronometerUtil c = new ChronometerUtil();
    	c.start();
    	try {
        	WrapperFilter f = new WrapperFilter();
        	Operators[] operators = new Operators[]{
        		new Operators(new String[]{"valor1","valor2"},"IN")	
        	};
        	Filters[] filters = new Filters[]{
        			new Filters("column", operators, "table")
        	};
        	f.setFilters(filters);
        	Join[] joins = new Join[]{
        			new Join("name","column")
        	};
        	f.setJoin(joins);
        	f.setLayer("layer");
        	f.setTable(new Table("table"));
        	return f;	
		} catch (Exception e) {
			log.throwing(WrapperResource.class.getSimpleName(), "getFilterWfsURL", e);
		}finally{
			log.info(c.stop("finish getSchema"));	
		}
        return null;
    }
    
    @POST
    @Path("/getFilterWfsURL/{filter}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getFilterWfsURL(WrapperFilter filter) {
       	log.info("init getFilterWfsURL");
    	ChronometerUtil c = new ChronometerUtil();
    	c.start();
    	try {
    		String result = executeFilterWfsURL(filter, 
                    MapRequestBuilder.MidwareTypes.geoserver.name(), 
                    MapRequestBuilder.MidwareProduces.geojson.name());
        	 return result;
		} catch (Exception e) {
			log.throwing(WrapperResource.class.getSimpleName(), "getFilterWfsURL", e);
		}finally{
			log.info(c.stop("finish getFilterWfsURL"));	
		}
        return null;
    }
    
    @POST
    @Path("/getFilterInGEOJSON/{filter}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilterInGEOJSON(WrapperFilter filter) {
       	log.info("init getFilterInGEOJSON");
    	ChronometerUtil c = new ChronometerUtil();
    	c.start();
    	try {
    		String result = executeFilterWFS(filter, 
                    MapRequestBuilder.MidwareTypes.geoserver.name(), 
                    MapRequestBuilder.MidwareProduces.geojson.name());
    		
        	return result;
        	
        	
		} catch (Exception e) {
			
			log.throwing(WrapperResource.class.getSimpleName(), "getFilterInGEOJSON", e);
		}finally{
			log.info(c.stop("finish getFilterInGEOJSON"));	
		}
        return null;
    }
    
    /**
     * Execute to execute generic method from web services.
     * Parameter produces types from cliente response
     * @param filter - Filter from client request
     * @param midware - Midware to request sends
     * @param produces - Type of markup produced, @see ResponseType
     * @return Url to midware request
     * @see ResponseType
     */
    private String executeFilterWfsURL(WrapperFilter filter, String midware, String produces){
        try {
            MapRequestBuilder builder = MapRequestBuilder.getInstance(midware,produces);
            String result = builder.buidWfsURL(filter);
            log.info("request for : " +  midware);
    		log.info("using url : " + result);
            return result;
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
        
    }
    /**
     * Execute to execute generic method from web services.
     * Parameter produces types from cliente response
     * @param filter - Filter from client request
     * @param midware - Midware to request sends
     * @param produces - Type of markup produced, @see ResponseType
     * @return Markup response
     * @see ResponseType
     */
    private String executeFilterWFS(WrapperFilter filter, String midware, String produces){
        try {
            MapRequestBuilder builder = MapRequestBuilder.getInstance(midware,produces);
            String url = builder.buidWfsURL(filter);
            //log.info("request for : " +  midware);
    		//log.info("using url : " + url);
            //return Response.seeOther(new URI(url)).build();
            String markupLanguage = builder.executeWFS(url);
            log.info("response markup wfs produces : "+produces);
            log.info(markupLanguage);
            return markupLanguage;
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
        
    }
   /* private String communicator(){
    	HttpGet httpGet = new HttpGet(targetUrl);
        OutputStream os = new FileOutputStream("C:\\Java\\jdk1.7.0_45\\bin\\imageNotFound.png");

        CredentialsProvider provider = new BasicCredentialsProvider(); 
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("username", "password"); 
        provider.setCredentials(AuthScope.ANY, credentials); 
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
        // Invoking the restful service to fetch the png
            result = httpClient.execute(httpGet, responseHandler);
        // Conversion to bytes
            os.write(result.getBytes());

        } catch (IOException ioe) {
            LOGGER.error("Caught IOException while processing request <" + httpGet.toString() + "> :=> <" + ioe.getMessage() + ">");
        } finally {
            httpGet.releaseConnection();
        }

        return javax.ws.rs.core.Response.ok(os).build();
    }*/
    /**
     * Execute to execute generic method from web services.
     * Parameter produces types from cliente response
     * @param filter - Filter from client request
     * @param midware - Midware to request sends
     * @param imageType - Type of markup produced, @see ResponseType
     * @return URL to redirect wms request
     * @see WMS URL
     */
    private String executeFilterWMS(WrapperFilter filter, String midware, String imageType){
    	
        try {
            MapRequestBuilder builder = MapRequestBuilder.getInstance(midware,imageType);
            String result = builder.buidWMS(filter);
            log.info("request for : " +  midware);
            log.info("image type : " +  imageType);
    		log.info("result url : " + result);
            return result;
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
        
    }
}
