/**
 * Class to represents the filter about associeted applications, application who consumes this webservices
 * <ul>
 * <li>
 The extruture about this filtes see
 *  layer - layer name about midware
 *  join - query joins about cross tables 
 *  table - table about query 
 *  attributes - attributes listed in query
 *  filters - filters applying for the other resources
 * </li>
 * </ul>
 * <p>
 * The example of uses about filter extruture
 * </p>
 *
 * <p>
 * <
 * pre>
 * <code>
 *   {
 *	"layer": "layer_speed",
*	"attributes": 
*	[
*		{
*			"name": "speed_m_s"
*		},
*
*		{
*			"name": "geom_sl"
**		}
*	],
*
*	"table": 
*	{
*		"name": "onet_data.services"
*	},
*
*	"join": 
*	[
*		{
*			"name": "onet_data.sentence_line",
*			"column": "id_sequence"
*		}
*	],
*
*	"filters": 
*	[
*		{
*			"table": "otmisnet.machine",
*			"column": "id_machine",
*			"operators": 
*			[
*				{
*					"operator": "IN",
*					"values": 
*					[
*						"1","11"
*					]
*				}
*			]
*		}
*	]
*}
 * </code>
 * </pre>
 * </p>
 * 
 * @author jairodealmeida
 * @since 24/11/2014
 * @version 1.0
 * @see Join
 * @see Table
 * @see Attributes
 * @see Filters
 */
package br.com.jacto.onet.map.wrapper.pojo;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class WrapperFilter
{
    private String layer;

    private Join[] join;

    private Table table;
    
    private Attributes[] attributes;

    private Filters[] filters;

    public String getLayer ()
    {
        return layer;
    }

    public void setLayer (String layer)
    {
        this.layer = layer;
    }

    public Join[] getJoin ()
    {
        return join;
    }

    public void setJoin (Join[] join)
    {
        this.join = join;
    }

    public Table getTable ()
    {
        return table;
    }

    public void setTable (Table table)
    {
        this.table = table;
    }

    public Attributes[] getAttributes ()
    {
        return attributes;
    }

    public void setAttributes (Attributes[] attributes)
    {
        this.attributes = attributes;
    }

    public Filters[] getFilters ()
    {
        return filters;
    }

    public void setFilters (Filters[] filters)
    {
        this.filters = filters;
    }
}
		