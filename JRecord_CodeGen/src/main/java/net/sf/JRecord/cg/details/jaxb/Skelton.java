//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.08.01 at 01:12:16 PM EST 
//


package net.sf.JRecord.cg.details.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import net.sf.JRecord.common.Conversion;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}if" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="output" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="template" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "_if"
})
@XmlRootElement(name = "skelton")
public class Skelton {

    @XmlElement(name = "if")
    protected List<If> _if;
    @XmlAttribute(name = "description")
    @XmlSchemaType(name = "anySimpleType")
    protected String description;
    @XmlAttribute(name = "output", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String output;
    @XmlAttribute(name = "template", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String template;

    
    
    @XmlTransient
    private String packageExtension, codeGenTemplate;
    @XmlTransient
    public boolean generate = true; 
    @XmlTransient
    private Properties templateProperties;

    public Skelton() {
    	
    }
    
	public Skelton(Properties templateProperties,
			String codeGenTemplate, String template, String description, String outputFileName, boolean generate) {
		super();
//		this.fileName = fileName;
		this.templateProperties = templateProperties;
		this.codeGenTemplate = codeGenTemplate;
		this.template = template;
		this.description = description;
		this.output = outputFileName;
		this.generate = generate;
	}
	
	public void update(Properties templateProperties, String codeGenTemplate, boolean generate) {
		this.templateProperties = templateProperties;
		this.codeGenTemplate = codeGenTemplate;
		this.generate = generate;		
	}

	
	public String getPackageExtension() {
		if (this.packageExtension == null) {
			int st = output.indexOf('/');
			int en = output.lastIndexOf('/');
			String extension = "";
			if (st >= 0 && en > st) {
				StringBuilder b = new StringBuilder()
										.append('.')
										.append(output.substring(st+1, en));
			    extension = Conversion.replace(b, "/", ".").toString();
			}

			this.packageExtension = extension;
		}
		
		return this.packageExtension;
	}
	
	public String getTemplateFileName() {
		return Conversion.replace(
				template,
				"&template.", 
				codeGenTemplate
		).toString();
	}


    /**
     * Gets the value of the if property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the if property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link If }
     * 
     * 
     */
    public List<If> getIf() {
        if (_if == null) {
            _if = new ArrayList<If>();
        }
        return this._if;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutput() {
        return output;
    }

    /**
     * Sets the value of the output property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutput(String value) {
        this.output = value;
    }

//    /**
//     * Gets the value of the template property.
//     * 
//     * @return
//     *     possible object is
//     *     {@link String }
//     *     
//     */
//    public String getTemplate() {
//        return template;
//    }
//
    /**
     * Sets the value of the template property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplate(String value) {
        this.template = value;
    }

}
