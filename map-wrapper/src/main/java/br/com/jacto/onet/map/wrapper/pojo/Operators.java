
package br.com.jacto.onet.map.wrapper.pojo;

/**
 *
 * @author jairodealmeida
 */
public class Operators
{
    private String[] values;

    private String operator;

    
    
    public Operators() {
		super();
	}

	public Operators(String[] values, String operator) {
		super();
		this.values = values;
		this.operator = operator;
	}

	public String[] getValues ()
    {
        return values;
    }

    public void setValues (String[] values)
    {
        this.values = values;
    }

    public String getOperator ()
    {
        return operator;
    }

    public void setOperator (String operator)
    {
        this.operator = operator;
    }
}
		
