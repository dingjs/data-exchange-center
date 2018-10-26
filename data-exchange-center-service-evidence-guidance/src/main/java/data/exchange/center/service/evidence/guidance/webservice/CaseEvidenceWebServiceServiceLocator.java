/**
 * CaseEvidenceWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package data.exchange.center.service.evidence.guidance.webservice;

public class CaseEvidenceWebServiceServiceLocator extends org.apache.axis.client.Service implements data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebServiceService {

    /**
	 * 2017年10月27日下午4:44:18
	 * yuguang
	 */
	private static final long serialVersionUID = 398451909369217527L;

	public CaseEvidenceWebServiceServiceLocator() {
    }


    public CaseEvidenceWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CaseEvidenceWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CaseEvidenceWebServicePort
    private java.lang.String CaseEvidenceWebServicePort_address = "http://150.0.0.101:8080/webservice/CaseEvidenceWebService";

    public java.lang.String getCaseEvidenceWebServicePortAddress() {
        return CaseEvidenceWebServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CaseEvidenceWebServicePortWSDDServiceName = "CaseEvidenceWebServicePort";

    public java.lang.String getCaseEvidenceWebServicePortWSDDServiceName() {
        return CaseEvidenceWebServicePortWSDDServiceName;
    }

    public void setCaseEvidenceWebServicePortWSDDServiceName(java.lang.String name) {
        CaseEvidenceWebServicePortWSDDServiceName = name;
    }

    public data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebService getCaseEvidenceWebServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CaseEvidenceWebServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCaseEvidenceWebServicePort(endpoint);
    }

    public data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebService getCaseEvidenceWebServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebServiceServiceSoapBindingStub _stub = new data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getCaseEvidenceWebServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCaseEvidenceWebServicePortEndpointAddress(java.lang.String address) {
        CaseEvidenceWebServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("rawtypes")
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebService.class.isAssignableFrom(serviceEndpointInterface)) {
                data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebServiceServiceSoapBindingStub _stub = new data.exchange.center.service.evidence.guidance.webservice.CaseEvidenceWebServiceServiceSoapBindingStub(new java.net.URL(CaseEvidenceWebServicePort_address), this);
                _stub.setPortName(getCaseEvidenceWebServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @SuppressWarnings("rawtypes")
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CaseEvidenceWebServicePort".equals(inputPortName)) {
            return getCaseEvidenceWebServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.proof.rpsoft.com/", "CaseEvidenceWebServiceService");
    }

    @SuppressWarnings("rawtypes")
	private java.util.HashSet ports = null;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.proof.rpsoft.com/", "CaseEvidenceWebServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CaseEvidenceWebServicePort".equals(portName)) {
            setCaseEvidenceWebServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
