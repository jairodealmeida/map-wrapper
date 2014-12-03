/**
 * 
 */
package br.com.jacto.onet.map.wrapper.builders;

import br.com.jacto.onet.map.wrapper.pojo.WrapperFilter;


public class MapRequestMapserver extends MapRequestBuilder{

    public MapRequestMapserver(String midware, String produces) {
        super(midware, produces);
    }

   

    @Override
    public String executeWFS(String url) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String buidWMS(WrapperFilter filter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



	@Override
	public String buidWfsURL(WrapperFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
