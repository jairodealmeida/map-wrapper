package br.com.jacto.mapwrapper;

import static org.junit.Assert.*;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.jacto.onet.map.wrapper.mock.MockWrapperFilter;

public class WrapperResourceTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetFilterWfsURL() {
		
	Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target(MockWrapperFilter.getBaseURI());
		try {
			String requestJson = MockWrapperFilter.getTesteJson();
			
			String requestDecode = URLDecoder.decode(requestJson, "UTF-8");
			assertNotNull(requestDecode);
			
			String requestEncode = URLEncoder.encode(requestJson, "UTF-8");
			assertNotNull(requestEncode);

			WebTarget resource = target.path("/getFilterWfsURL/"+requestEncode);
	        Invocation.Builder invocationBuilder =
	        		resource.request(MediaType.APPLICATION_JSON);
	        Response response = invocationBuilder.post(Entity.json(requestJson));
	        String task = response.readEntity(String.class);
	        assertNotNull(task);
	        
	        /*
	         * Example:
	       		 http://192.168.56.101:8084/geoserver/poc_otmis_net_ws&LAYERS=layer_speed
	       		 &viewparams=attributes:speed_m_s,geom_sl;
	       		 table:onet_data.services;
	       		 join_onet_data.sentence_line:join onet_data.sentence_line on (id_sequence);
	       		 where_id_machine:where id_machine IN(1,11)
	        */
	        
			assertEquals(MockWrapperFilter.MOCK_TEST_URL3,task);
		} catch (Exception e) {
			fail("could not pass here");
		}
	}

	@Test
	public void testGetFilterInGEOJSON() {
		
		Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target(MockWrapperFilter.getBaseURI());
		try {
			String requestJson = MockWrapperFilter.getTesteJson();
			
			String requestDecode = URLDecoder.decode(requestJson, "UTF-8");
			assertNotNull(requestDecode);
			
			String requestEncode = URLEncoder.encode(requestJson, "UTF-8");
			assertNotNull(requestEncode);

			WebTarget resource = target.path("/getFilterInGEOJSON/"+requestEncode);
	        Invocation.Builder invocationBuilder =
	        		resource.request(MediaType.APPLICATION_JSON);
	        Response response = invocationBuilder.post(Entity.json(requestJson));
	        String task = response.readEntity(String.class);
	        assertNotNull(task);
	        
	        /*
	         * Example:
	       		...
	        */
	        
			assertEquals(MockWrapperFilter.MOCK_TEST_URL3,task);
			
		} catch (Exception e) {
			fail("could not pass here");	
		}
	}

	

}
