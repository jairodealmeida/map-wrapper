/**
 * Class to transfer object.
 * Responsable to data tranfer object response markup object from midware to client.
 * 
 * @author Jairo de Almeida (jairodealmeida@gmail.com)
 * @version 1.0
 * @since 25/11/2014
 * @see WrapperFilterRESTWebService
 */
package br.com.jacto.onet.map.wrapper.pojo;


import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class WrapperMarkupResponse {
    
    private String markup;

    public WrapperMarkupResponse(String markup) {
        this.markup = markup;
    }

    public String getMarkup() {
        return markup;
    }

    public void setMarkup(String markup) {
        this.markup = markup;
    }
    
}
