package br.com.jacto.onet.map.wrapper.wfs;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeoserverWFSConnectorTest {

	private String host="localhost", port="8084", workspace="otmisnet_workspace", layer="machine_route_aux ";
	
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
	public void testGeoserverWFSConnector() {
		try {
			GeoserverWFSConnector connector = new GeoserverWFSConnector(host, port, workspace, layer);
			assertNotNull(connector);
		} catch (Exception e) {
			fail("not pass here");
		}
		
	}

	@Test
	public void testWfsRequest() {
		try {
			GeoserverWFSConnector connector = new GeoserverWFSConnector(host, port, workspace, layer);
			assertNotNull(connector);
			String geojson = connector.wfsRequest();
			assertNotNull(geojson);
		} catch (Exception e) {
			e.printStackTrace();
			fail("not pass here");
		}
	}

}
