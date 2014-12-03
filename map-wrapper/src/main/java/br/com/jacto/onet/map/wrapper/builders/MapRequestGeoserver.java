/**
 * Represents an map request builder to geoserver communication. Builder url
 * redirect from geoservers patterns this will be generate requst and redirect
 * post request to webservices response.
 *
 * <p>
 * MapRequestGeoserver
 * </p>
 *
 * <ul>
 * <li>
 * Example of request format produces 
 * </li>
 * <code>
 * http://host:port/geoserver ...
 * &viewparams=param1:value1;param2:value2 ... &env=color1:HEX1;color2:HEX2 ...
 * value1:0;value2:2 ...
 * 
 * </code>
 * 
 * <li>
 * Other real example of request format will be produces
 * </li>
 * <code>
 * http://192.168.56.101:8084/geoserver/poc_otmis_net_ws/ows?service=WMS&
 * LAYERS=machine_route&TRANSPARENT=TRUE&
 * VIEWPARAMS=where_users:WHERE  id_client in (5\,3\,4\,1\,2);
 * date1:1396828800000;
 * date2:1397523540999&
 * ENV=
 * value1:0.6656624069213868;
 * value2:12.376525366613993;
 * value3:14.763094820402266;
 * value4:17.14966427419054;
 * value5:19.53623372797881;
 * value6:21.922803181767083;
 * value7:24.309372635555356;value8:42.34332139587402;
 * color1:FF0000;
 * color2:FF8400;
 * color3:FFFF00;
 * color4:BDFF42;
 * color5:42FFBD;
 * color6:0084FF;
 * color7:0000BD&
 * VERSION=1.1.1&REQUEST=GetMap&STYLES=&FORMAT=image/png&
 * SRS=EPSG:3857&BBOX=-14994607.94695,-1327284.8617645,16313998.8343,29981321.919486&
 * WIDTH=800&HEIGHT=800
 * </code>
 * 
 
 * </ul>
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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import br.com.jacto.onet.map.wrapper.pojo.Attributes;
import br.com.jacto.onet.map.wrapper.pojo.Filters;
import br.com.jacto.onet.map.wrapper.pojo.Join;
import br.com.jacto.onet.map.wrapper.pojo.Operators;
import br.com.jacto.onet.map.wrapper.pojo.Table;
import br.com.jacto.onet.map.wrapper.pojo.WrapperFilter;
import br.com.nova.core.StringUtils;

public class MapRequestGeoserver extends MapRequestBuilder {

    private String host = "localhost";
    private String port = "8084";
    private String workspace = "otmisnet_workspace";
    
    Logger log = Logger.getLogger(MapRequestGeoserver.class.getSimpleName()); 

    public MapRequestGeoserver(String midware, String produces) {
        super(midware, produces);
    }
    public void buildHeader(StringBuilder request){
    	request.append("http://");
        request.append(host);
        request.append(":");
        request.append(port);
        request.append("/geoserver/");
        request.append(workspace);
        request.append("/ows");
        
        if(MapRequestBuilder.MidwareProduces.geojson.name().equalsIgnoreCase(super.getProduces()) ){
        	request.append("?service=WFS");
        	request.append("&version=1.0.0");
        	request.append("&request=GetFeature");
        	request.append("&maxFeatures=50");
        	request.append("&outputFormat=application/json");
        }else if(MapRequestBuilder.MidwareProduces.gml.name().equalsIgnoreCase(super.getProduces()) ){
        	request.append("?service=WFS");
        	request.append("&version=1.0.0");
        	request.append("&request=GetFeature");
        	request.append("&maxFeatures=50");
        	request.append("&outputFormat=application/xml");
        }else if(MapRequestBuilder.MidwareProduces.kml.name().equalsIgnoreCase(super.getProduces()) ) {
        	request.append("?service=WMS");
        	request.append("&version=1.1.0");
        	request.append("&request=GetMap");
        	request.append("&width=512&height=476");
        	request.append("&srs=EPSG:4326");
        	request.append("&styles=");
        	request.append("&bbox=-54.7580833333333,-15.3844283333333,-54.63779,-15.27259");
        	request.append("&outputFormat=application/kml");
        }else if(MapRequestBuilder.MidwareProduces.png.name().equalsIgnoreCase(super.getProduces()) ){
        	request.append("?service=WMS");
        	request.append("&version=1.1.0");
        	request.append("&request=GetMap");
        	request.append("&width=512&height=476");
        	request.append("&srs=EPSG:4326");
        	request.append("&styles=");
        	request.append("&bbox=-54.7580833333333,-15.3844283333333,-54.63779,-15.27259");
        	request.append("&format=image/png");
        }else if(MapRequestBuilder.MidwareProduces.gif.name().equalsIgnoreCase(super.getProduces()) ){
        	request.append("?service=WMS");
        	request.append("&version=1.1.0");
        	request.append("&request=GetMap");
        	request.append("&width=512&height=476");
        	request.append("&srs=EPSG:4326");
        	request.append("&styles=");
        	request.append("&bbox=-54.7580833333333,-15.3844283333333,-54.63779,-15.27259");
        	request.append("&format=image/gif");
        }
        
    }
    /*
             		 UriBuilder.fromUri(RESTURL).
        		       path("geoserver").
        		       queryParam("name", "{value}").
        		       build("segment", "value");
        		 
        			
        		   String s = webResource.queryParams(queryParams).get(String.class);

     */
    /**
     * method is responsable to build geoserver url request
     *
     * @param filter the representation of json filter from webservices request
     * @return request format produces. If <code>filter</code> is not
     * <code>null</code> the request are maked 
     * http://host:port/geoserver ...
     * &viewparams=param1:value1;param2:value2 ... 
     * &env=color1:HEX1;color2:HEX2
     * ... value1:0;value2:2 ...
     * @throws URISyntaxException 
     * @throws IOException 
     *  
     */
    @Override
    public String executeWFS(String url) throws URISyntaxException, IOException {
        if(url!=null){
        	URL urlconn = new URL(url);
        	URLConnection conn = urlconn.openConnection();
        	conn.setDoOutput(true);
        	conn.setDoInput(true);
        	return StringUtils.parse(conn.getInputStream());     	
        	
        }else{
        	throw new NullPointerException("URL is NULL");
        }
        
    }
    
    /**
     * method is responsable to build geoserver url request
     *
     * @param filter the representation of json filter from webservices request
     * @return request format produces. If <code>filter</code> is not
     * <code>null</code> the request are maked 
     * http://host:port/geoserver ...
     * &viewparams=param1:value1;param2:value2 ... 
     * &env=color1:HEX1;color2:HEX2
     * ... value1:0;value2:2 ...
     */
	@Override
	public String buidWfsURL(WrapperFilter filter) {
		StringBuilder request  = new StringBuilder();
        //Append header
		 //Append header
        buildHeader(request);  
        //Layer from geoserver
        buildLayerRequest(filter,request);
        //Parameters of wms request from geoserver
        List<String> params = new ArrayList<String>();
        request.append("&viewparams=");
        buildAttributesRequest(filter,params);
        buildTableRequest(filter,params);
        buildJoinRequest(filter,params);
        buildFiltersRequest(filter,params);
        
        request.append(joinParams(params));
        return request.toString();
	}
	public String joinParams(List<String> params){
		if(params!=null && params.size()>0){
			StringBuilder request  = new StringBuilder();
	        for (int i = 0; i < params.size(); i++) {
	        	String param = params.get(i);
	        	request.append(param);
	        	if(i<params.size()-1){
	    			request.append(";");
	    		}
			}
	        return request.toString();			
		}else{
			return "";
		}

	}
    /**
     * method is responsable to build geoserver url request
     *
     * @param filter the representation of json filter from webservices request
     * @return request format produces. If <code>filter</code> is not
     * <code>null</code> the request are maked 
     * http://host:port/geoserver ...
     * &viewparams=param1:value1;param2:value2 ... 
     * &env=color1:HEX1;color2:HEX2
     * ... value1:0;value2:2 ...
     */
    @Override
    public String buidWMS(WrapperFilter filter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /*
        java.net.URI location = new java.net.URI("../index.jsp?msg=A_User_Added");
        return Response.temporaryRedirect(location).build()
        */
    }
    /**
     * build the layer from json filter request
     * @param filter the representation of json filter from webservices request
     */
    private void buildLayerRequest(WrapperFilter filter,StringBuilder request ) {
        if (filter != null && filter.getLayer() != null) {
            request.append("&LAYERS=");
            request.append(filter.getLayer());
        }
    }
    /**
     * build the attributes from json filter request
     * @param filter the representation of json filter from webservices request
     */
    private void buildAttributesRequest(WrapperFilter filter, List<String> params ) {
        if (filter != null) {
        	
            Attributes[] attributes = filter.getAttributes();
            if (attributes != null) {
            	StringBuilder request = new StringBuilder();
            	request.append("attributes:");
            	for (int i = 0; i < attributes.length; i++) {
            		Attributes attribute = attributes[i];
                    request.append(attribute.getName());
            		if(i<attributes.length-1){
            			request.append(",");
            		}
                }
            	params.add(request.toString());
            }
           
        }
    }
  
    /**
     * build the table from json filter request
     * @param filter the representation of json filter from webservices request
     */
    private void buildTableRequest(WrapperFilter filter, List<String> params  ) {
        if (filter != null) {
            Table table = filter.getTable();
            if (table != null) {
                params.add("table:"+table.getName());
            }
            
        }
    }
    /**
     * build the joins from json filter request
     * @param filter the representation of json filter from webservices request
     */
    private void buildJoinRequest(WrapperFilter filter, List<String> params  ) {
        
        if (filter != null) {
            Join[] joins = filter.getJoin();
            if (joins != null) {
            	for (int i = 0; i < joins.length; i++) {
            		StringBuilder request = new StringBuilder();
                    Join join = joins[i];
                    request.append("join_"+join.getName()+":");
                    request.append("join ");
                    request.append(join.getName());
                    request.append(" on ");
                    request.append("("+join.getColumn()+")");
                    params.add(request.toString());
                }
            	
            }
        }

    }
    /**
     * build the filters from json filter request
     * LAYERS=machine_route&TRANSPARENT=TRUE&
    * VIEWPARAMS=where_users:WHERE  id_client in (5\,3\,4\,1\,2);
    * date1:1396828800000;
    * date2:1397523540999
     * @param filter the representation of json filter from webservices request
     */
    private void buildFiltersRequest(WrapperFilter filter, List<String> params  ) {
        
        
        if (filter != null) {
        	
            Filters[] filters = filter.getFilters();
            if (filters != null) {
                for (int i = 0; i < filters.length; i++) {
                    Filters f = filters[i];
                    //Example of produces 
                    //where_id_client:WHERE  id_client in (5\,3\,4\,1\,2)
                    StringBuilder request = new StringBuilder();
                    request.append("where_"+f.getColumn() + ":where " +f.getColumn() );
                    Operators[] operators = f.getOperators();
                    if (operators != null) {

                        for (Operators operator : operators) {
                        	if(operator.getOperator().equalsIgnoreCase("IN")){
                        		request.append(" " + operator.getOperator());
                                String[] values = operator.getValues();
                                if (values != null) {
                                	
                                    request.append("(");
                                	
                                	for (int j = 0; j < values.length; j++) {
                                		String value = values[j];
                                		
                                		request.append(value);
                                		if(j<values.length-1){
                                			request.append(",");
                                		}else{
                                			request.append(")");
                                		}
    								} //end of for values
                                }	
                        	}else{
                        		throw new NullPointerException("Operator not supported");
                        	}
                            
                        } //end of for operators
                    }
                    params.add(request.toString());
                    
                } //end of for filters
            }
        }
    }//end of buildFiltersRequest




} //end of class MapRequestGeoserver
