/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jacto.onet.map.wrapper.builders;

import br.com.jacto.onet.map.wrapper.pojo.WrapperFilter;

/**
 *
 * @author jairodealmeida
 */
public class MapRequestMapwrapper extends MapRequestBuilder{

    public MapRequestMapwrapper(String midware, String produces) {
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
