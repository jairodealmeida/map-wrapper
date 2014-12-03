/**
 * Represents an map request builder midware commmunicator. Abstract class to
 * relation of the midwares concrete classes or The wrapper implementation,
 * independentry of mdware post request to webservices response.
 *
 * <p>
 * MapRequestBuilder
 * </p>
 *
 * <p>
 * The example of uses about map build instanciation
 * </p>
 *
 * <p>
 * <pre>
 * <code>
 *     String midware = "geoserver" || "mapserver" || "mapwrapper";
 *     MapRequestBuilder builder = MapRequestBuilder.getInstance(midware);
 *     String result = builder.buid(filter);
 * </code>
 * </pre>
 * </p>
 *
 * @author Jairo de Almeida (jairodealmeida@gmail.com)
 * @see	WrapperFilterRESTWebSErvice
 * @since 21/11/2014
 * @version 1.0
 */
package br.com.jacto.onet.map.wrapper.builders;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import br.com.jacto.onet.map.wrapper.pojo.WrapperFilter;

/**
 *
 * @author jairodealmeida
 */
public abstract class MapRequestBuilder {

    private String midware;
    private String produces;

    /**
     * Types of midwares suported
     */
    public enum MidwareTypes {

        geoserver,
        mapserver,
        mapwrapper
    }

    /**
     * Types of response produces
     */
    public enum MidwareProduces {
        geojson,
        gml,
        kml,
        png,
        gif
    }

    /**
     * Constructor
     *
     * @param midware webservices informed midware to make the request
     * @param produces webservices informed type of this webserver will be
     * produces
     */
    public MapRequestBuilder(String midware, String produces) {
        this.midware = midware;
        this.produces = produces;
    }

    /**
     * Singleton to get the type of instanciate about comuniccation.
     *
     * @param midware -
     * @param produces
     * @return The attribute <code>midware</code> define a type of intance
     * <code>"geoserver"</code> OR <code>"mapserver"</code> OR
     * <code>"mapwrapper"</code>;
     * @see MidwareTypes
     * @see MidwareProduces
     */
    public static MapRequestBuilder getInstance(String midware, String produces) {
        if (midware == null) {
            throw new UnsupportedOperationException(
                    "The parameter midware needs to be informed. example:: " + MidwareTypes.values());
        }
        if (produces == null) {
            throw new UnsupportedOperationException(
                    "The parameter produces needs to be informed. example:: " + MidwareProduces.values());
        }
        if (midware.equalsIgnoreCase(MidwareTypes.geoserver.name())) {
            return new MapRequestGeoserver(midware,produces);
        } else if (midware == MidwareTypes.mapserver.name()) {
            return new MapRequestMapserver(midware,produces);
        } else if (midware == MidwareTypes.mapwrapper.name()) {
            return new MapRequestMapwrapper(midware,produces);
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    /**
     * Abstract method to execute a request.
     *
     * @param url the representation of request from webservices to midware
     * @return request format produces. If <code>url</code> is not
     * <code>null</code> make wfs request about url parameter 
     * and returns a Markup language GeoJSON || GML || KML bout url requested
     * @throws URISyntaxException 
     * @throws MalformedURLException 
     * @throws IOException 
     */
    public abstract String executeWFS(String url) throws URISyntaxException, MalformedURLException, IOException;
    /**
     * Abstract method to build a request.
     *
     * @param filter the representation of json filter from webservices request
     * @return request format produces. If <code>filter</code> is not
     * <code>null</code> the request are maked about filter attributes
     */
    public abstract String buidWfsURL(WrapperFilter filter);

    /**
     * Abstract method to build a request.
     *
     * @param filter the representation of json filter from webservices request
     * @return request format produces. If <code>filter</code> is not
     * <code>null</code> the request are maked about filter attributes
     */
    public abstract String buidWMS(WrapperFilter filter);
    
    /**
     * @return the midware
     */
    public String getMidware() {
        return midware;
    }

    /**
     * @param midware the midware to set
     */
    public void setMidware(String midware) {
        this.midware = midware;
    }

    /**
     * @return the produces
     */
    public String getProduces() {
        return produces;
    }

    /**
     * @param produces the produces to set
     */
    public void setProduces(String produces) {
        this.produces = produces;
    }
    
}
