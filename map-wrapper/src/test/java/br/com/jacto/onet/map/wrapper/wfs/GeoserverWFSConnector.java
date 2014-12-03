package br.com.jacto.onet.map.wrapper.wfs;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.wfs.WFSDataStore;
import org.geotools.data.wfs.WFSDataStoreFactory;
import org.geotools.data.wfs.v1_0_0.WFS_1_0_0_DataStore;
import org.geotools.geojson.feature.FeatureJSON;
import org.opengis.feature.simple.SimpleFeature;

public class GeoserverWFSConnector {
	/*public void wfsGetResponse(){
		String getCapabilities = "http://localhost:8084/geoserver/wfs?REQUEST=GetCapabilities";

		Map connectionParameters = new HashMap();
		connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities );

		// Step 2 - connection
		DataStore data = DataStoreFinder.getDataStore( connectionParameters );

		// Step 3 - discouvery
		String typeNames[] = data.getTypeNames();
		String typeName = typeNames[0];
		SimpleFeatureType schema = data.getSchema( typeName );

		// Step 4 - target
		FeatureSource<SimpleFeatureType, SimpleFeature> source = data.getFeatureSource( typeName );
		System.out.println( "Metadata Bounds:"+ source.getBounds() );

		// Step 5 - query
		String geomName = schema.getDefaultGeometry().getLocalName();
		Envelope bbox = new Envelope( -100.0, -70, 25, 40 );

		FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2( GeoTools.getDefaultHints() );
		Object polygon = JTS.toGeometry( bbox );
		Intersects filter = ff.intersects( ff.property( geomName ), ff.literal( polygon ) );

		Query query = new DefaultQuery( typeName, filter, new String[]{ geomName } );
		FeatureCollection<SimpleFeatureType, SimpleFeature> features = source.getFeatures( query );

		ReferencedEnvelope bounds = new ReferencedEnvelope();
		Iterator<SimpleFeature> iterator = features.iterator();
		try {
		    while( iterator.hasNext() ){
		        Feature feature = (Feature) iterator.next();
		    bounds.include( feature.getBounds() );
		}
		    System.out.println( "Calculated Bounds:"+ bounds );
		}
		finally {
		    features.close( iterator );
		}
	}*/
	private String host;
	private  String port;
	private  String workspace;
	private  String layer;
	
	
	public GeoserverWFSConnector(String host, String port, String workspace, String layer) {
		super();
		this.host = host;
		this.port = port;
		this.workspace = workspace;
		this.layer = layer;
	}


	public String wfsRequest(){
		//String getCapabilities = "http://"+host+":"+port+"/geoserver/"+workspace+"/wfs?REQUEST=GetCapabilities&version=1.0.0";
		String getCapabilities = "http://"+host+":"+port+"/geoserver/"+workspace+
				"/ows?REQUEST=GetCapabilities&version=1.1.1&service=WFS";
		Map connectionParameters = new HashMap();
		connectionParameters.put("WFSDataStoreFactory:GET_CAPABILITIES_URL", getCapabilities);
		connectionParameters.put("WFSDataStoreFactory:USERNAME", "admin");
		connectionParameters.put("WFSDataStoreFactory:PASSWORD", "geoserver");
		connectionParameters.put("WFSDataStoreFactory:WFS_STRATEGY", "geoserver");
		connectionParameters.put("WFSDataStoreFactory:MAXFEATURES",1000);
		WFSDataStoreFactory  dsf = new WFSDataStoreFactory();
		try {
			//WFS_1_0_0_DataStore wfs = dsf.createDataStore(connectionParameters);
		    WFSDataStore dataStore = dsf.createDataStore(connectionParameters);
		    
		    String[] typeNames = dataStore.getTypeNames();
	        System.out.println(ArrayUtils.toString(typeNames));
		    
		    SimpleFeatureSource source = dataStore.getFeatureSource(workspace+":"+layer);
		    SimpleFeatureCollection fc = source.getFeatures();
		    StringWriter writer = new StringWriter();
		    while(fc.features().hasNext()){
		        SimpleFeature sf = fc.features().next();
		        System.out.println(sf.getAttribute("myname"));
		        FeatureJSON fjson = new FeatureJSON();
		        fjson.writeFeature(sf, writer);
		    }
		    String geojson = writer.toString();
		    return geojson;
		} catch (IOException ex) {
		    ex.printStackTrace();
		}	
		return null;
	}
	
}
