package br.com.jacto.onet.map.wrapper.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.AssertFalse;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.jacto.onet.map.wrapper.mock.MockWrapperFilter;
import br.com.jacto.onet.map.wrapper.wfs.GeoserverWFSConnector;

public class MapRequestBuilderTest {

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
	public void testGetInstance() {
		MapRequestBuilder mrb = null;
		mrb = MapRequestBuilder.getInstance(
				MapRequestBuilder.MidwareTypes.geoserver.name(), 
				MapRequestBuilder.MidwareProduces.geojson.name());
		assertTrue(mrb instanceof MapRequestGeoserver);
		mrb = MapRequestBuilder.getInstance(
				MapRequestBuilder.MidwareTypes.mapserver.name(), 
				MapRequestBuilder.MidwareProduces.geojson.name());
		assertTrue(mrb instanceof MapRequestMapserver);
		mrb = MapRequestBuilder.getInstance(
				MapRequestBuilder.MidwareTypes.mapwrapper.name(), 
				MapRequestBuilder.MidwareProduces.geojson.name());
		assertTrue(mrb instanceof MapRequestMapwrapper);
		
		try {
			mrb = MapRequestBuilder.getInstance(
					"", 
					MapRequestBuilder.MidwareProduces.geojson.name());
			fail("not pass here");			
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedOperationException);
		}
		try {
			mrb = MapRequestBuilder.getInstance(
					MapRequestBuilder.MidwareTypes.mapwrapper.name(), 
					"");
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedOperationException);
		}
		try {
			mrb = MapRequestBuilder.getInstance(
					null, 
					null);
			assertNull(mrb);
		} catch (Exception e) {
			assertTrue(e instanceof UnsupportedOperationException);
		}
	}
	
	@Test
	public void testJoinParams() {
		MapRequestGeoserver mrb = new MapRequestGeoserver("geoserver","geojson");
		List<String> params = new ArrayList<String>();
		String result = null;
		
		params.add("param1");
		params.add("param2");
		params.add("param3");
		params.add("param4");
		params.add("param5");
		result = mrb.joinParams(params);
		assertNotNull(result);
		assertEquals("param1;param2;param3;param4;param5", result);
		
		params.removeAll(params);
		params.add("param1");
		result = mrb.joinParams(params);
		assertNotNull(result);
		assertEquals("param1", result);
		
		params.removeAll(params);
		result = mrb.joinParams(params);
		assertNotNull(result);
		assertEquals("", result);
		
		result = mrb.joinParams(null);
		assertNotNull(result);
		assertEquals("", result);
		
		
		
	}

	@Test
	public void testBuidWFS() {
		MapRequestBuilder mrb1 = MapRequestBuilder.getInstance(
				MapRequestBuilder.MidwareTypes.geoserver.name(), 
				MapRequestBuilder.MidwareProduces.geojson.name());
		String wfsUrl = mrb1.buidWfsURL(MockWrapperFilter.getWrapperFilter1());
		assertNotNull(wfsUrl);
		//http://192.168.56.101:8084/geoserver/poc_otmis_net_ws
		//&LAYERS=layer
		//&viewparams=
		//table:table;
		//join_name:join name on (column);
		//where_column:where column IN(valor1,valor2)
		assertEquals(MockWrapperFilter.MOCK_TEST_URL1 ,wfsUrl);
	}
	
	
	@Test
	public void testExecuteWFS() {
		MapRequestBuilder mrb = MapRequestBuilder.getInstance(
				MapRequestBuilder.MidwareTypes.geoserver.name(), 
				MapRequestBuilder.MidwareProduces.geojson.name());
		String url = "http://localhost:8084/geoserver/otmisnet_workspace/ows"
				+ "?service=WFS"
				+ "&version=1.0.0"
				+ "&request=GetFeature"
				+ "&typeName=otmisnet_workspace:machine_route_aux"
				+ "&maxFeatures=50&outputFormat=application/json";
		//http://localhost:8084/geoserver/otmisnet_workspace/ows?
		//	service=WFS&
		//	version=1.0.0&
		//	request=GetFeature&
		//	typeName=otmisnet_workspace:machine_route_aux&
		//	maxFeatures=50&outputFormat=application/json
		try {
			String result = mrb.executeWFS(url);
			assertNotNull(result);
			assertTrue(!result.isEmpty());
		} catch (URISyntaxException e) {
			fail("couldn't pass here!");
		} catch (MalformedURLException e) {
			fail("couldn't pass here!");
		} catch (IOException e) {
			fail("couldn't pass here!");
		}
	}

	@Test
	public void testGeoserverWFSConnector(){
		
		String resturl = "localhost:8084/geoserver/";
		String restuser = "admin";
		String restpw = "geoserver";
		String host = "localhost";
		String port = "8084";
		String workspace = "otmisnet_workspace";
		String layer = "machine_route_aux";
		try {
			//GeoServerRESTReader reader = new GeoServerRESTReader(resturl, restuser, restpw);
	    	//if(reader.existGeoserver()){
	    		GeoserverWFSConnector gwc = new GeoserverWFSConnector(host, port,workspace,layer);
	    		String geojson = gwc.wfsRequest();
	    		assertNotNull(geojson);
	    	//}else{
	    	//	throw new NullPointerException("Geoserver not exist " + resturl );
	    	//}	
		} catch (Exception e) {
			fail("can't pass here");
		}
		
	}
	
	

}
