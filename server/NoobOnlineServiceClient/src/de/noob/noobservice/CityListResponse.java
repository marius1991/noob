
package de.noob.noobservice;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für cityListResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="cityListResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://noobservice.noob.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="cities" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cityListResponse", propOrder = {
    "cities"
})
public class CityListResponse
    extends ReturnCodeResponse
{

    @XmlElement(nillable = true)
    protected List<String> cities;

    /**
     * Gets the value of the cities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCities() {
        if (cities == null) {
            cities = new ArrayList<String>();
        }
        return this.cities;
    }

}
