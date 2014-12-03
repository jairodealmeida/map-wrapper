
package br.com.jacto.onet.map.wrapper.pojo;

/**
 *
 * @author jairodealmeida
 */
public class Join
{
    private String name;

    private String column;

    
    public Join() {
		super();
	}

	public Join(String name, String column) {
		super();
		this.name = name;
		this.column = column;
	}

	public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getColumn ()
    {
        return column;
    }

    public void setColumn (String column)
    {
        this.column = column;
    }
}