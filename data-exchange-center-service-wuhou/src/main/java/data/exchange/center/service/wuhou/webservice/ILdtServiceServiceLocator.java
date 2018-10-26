/**
 * ILdtServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package data.exchange.center.service.wuhou.webservice;

public class ILdtServiceServiceLocator extends org.apache.axis.client.Service implements data.exchange.center.service.wuhou.webservice.ILdtServiceService {

    public ILdtServiceServiceLocator() {
    }


    public ILdtServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ILdtServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ILdtServicePort
    private java.lang.String ILdtServicePort_address = "http://150.100.1.93:8888/court/service/LdtService";

    public java.lang.String getILdtServicePortAddress() {
        return ILdtServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ILdtServicePortWSDDServiceName = "ILdtServicePort";

    public java.lang.String getILdtServicePortWSDDServiceName() {
        return ILdtServicePortWSDDServiceName;
    }

    public void setILdtServicePortWSDDServiceName(java.lang.String name) {
        ILdtServicePortWSDDServiceName = name;
    }

    public data.exchange.center.service.wuhou.webservice.ILdtService getILdtServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ILdtServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getILdtServicePort(endpoint);
    }

    public data.exchange.center.service.wuhou.webservice.ILdtService getILdtServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            data.exchange.center.service.wuhou.webservice.ILdtServiceServiceSoapBindingStub _stub = new data.exchange.center.service.wuhou.webservice.ILdtServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getILdtServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setILdtServicePortEndpointAddress(java.lang.String address) {
        ILdtServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (data.exchange.center.service.wuhou.webservice.ILdtService.class.isAssignableFrom(serviceEndpointInterface)) {
                data.exchange.center.service.wuhou.webservice.ILdtServiceServiceSoapBindingStub _stub = new data.exchange.center.service.wuhou.webservice.ILdtServiceServiceSoapBindingStub(new java.net.URL(ILdtServicePort_address), this);
                _stub.setPortName(getILdtServicePortWSDDServiceName());
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
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ILdtServicePort".equals(inputPortName)) {
            return getILdtServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.ldt.court.tdh/", "ILdtServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.ldt.court.tdh/", "ILdtServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ILdtServicePort".equals(portName)) {
            setILdtServicePortEndpointAddress(address);
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
