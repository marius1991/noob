
package de.noob.noobservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für userLoginResponse complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="userLoginResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://noobservice.noob.de/}returnCodeResponse">
 *       &lt;sequence>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userLoginResponse", propOrder = {
    "sessionId"
})
public class UserLoginResponse
    extends ReturnCodeResponse
{

    protected int sessionId;

    /**
     * Ruft den Wert der sessionId-Eigenschaft ab.
     * 
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * Legt den Wert der sessionId-Eigenschaft fest.
     * 
     */
    public void setSessionId(int value) {
        this.sessionId = value;
    }

}
