
package br.com.jacto.onet.map.wrapper.pojo;

/**
 *
 * @author jairodealmeida
 */
public class Filters
{
    private String column;

    private Operators[] operators;

    private String table;

    public Filters() {
		super();
	}

	public Filters(String column, Operators[] operators, String table) {
		super();
		this.column = column;
		this.operators = operators;
		this.table = table;
	}

	public String getColumn ()
    {
        return column;
    }

    public void setColumn (String column)
    {
        this.column = column;
    }

    public Operators[] getOperators ()
    {
        return operators;
    }

    public void setOperators (Operators[] operators)
    {
        this.operators = operators;
    }

    public String getTable ()
    {
        return table;
    }

    public void setTable (String table)
    {
        this.table = table;
    }
}