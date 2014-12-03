package br.com.jacto.onet.map.wrapper.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.ws.rs.core.UriBuilder;

import org.json.JSONObject;

import br.com.jacto.onet.map.wrapper.pojo.Filters;
import br.com.jacto.onet.map.wrapper.pojo.Join;
import br.com.jacto.onet.map.wrapper.pojo.Operators;
import br.com.jacto.onet.map.wrapper.pojo.Table;
import br.com.jacto.onet.map.wrapper.pojo.WrapperFilter;

public class MockWrapperFilter {
	
	public static final String MOCK_TEST_URL1 = "http://192.168.56.101:8084/geoserver/poc_otmis_net_ws"
			+ "&LAYERS=layer"
			+ "&viewparams="
				+ "table:table;"
				+ "join_name:join name on (column);"
				+ "where_column:where column IN(valor1,valor2)";   
	
	public static final String MOCK_TEST_URL2 = "http://192.168.56.101:8084/geoserver/poc_otmis_net_ws"
			+ "&LAYERS=layer"
			+ "&viewparams="
				+ "table:table;"
				+ "join_name:join name on (column);"
				+ "where_column:where column IN(valor1,valor2)";
	
	public static final String MOCK_TEST_URL3 = "http://192.168.56.101:8084/geoserver/poc_otmis_net_ws"
			+ "&LAYERS=layer_speed"
			+ "&viewparams="
				+ "attributes:speed_m_s,geom_sl;"
				+ "table:onet_data.services;"
				+ "join_onet_data.sentence_line:join onet_data.sentence_line on (id_sequence);"
				+ "where_id_machine:where id_machine IN(1,11)";
	
	public static WrapperFilter getWrapperFilter1(){
		WrapperFilter filter = new WrapperFilter();
    	Operators[] operators = new Operators[]{
    		new Operators(new String[]{"valor1","valor2"},"IN")	
    	};
    	Filters[] filters = new Filters[]{
    			new Filters("column", operators, "table")
    	};
    	filter.setFilters(filters);
    	Join[] joins = new Join[]{
    			new Join("name","column")
    	};
    	filter.setJoin(joins);
    	filter.setLayer("layer");
    	filter.setTable(new Table("table"));
    	return filter;
	}
	
	public static String getTesteJson() throws IOException{
        URL path = new URL("file://" + System.getProperty("user.home")+
                    "/Jacto/workspace/"
                    + "map-wrapper/src/main/resources/" + 
                    "filter_machine_route.json");
        String string = "";
        InputStream is = path.openStream(); 
        InputStreamReader crunchifyReader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(crunchifyReader);
        String line;
        while ((line = br.readLine()) != null) {
            string += line + "\n";
        }
        JSONObject jsonObject = new JSONObject(string);
        return jsonObject.toString();
	}

	public static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8084/map-wrapper/webapi/resource").build();
	}
}
