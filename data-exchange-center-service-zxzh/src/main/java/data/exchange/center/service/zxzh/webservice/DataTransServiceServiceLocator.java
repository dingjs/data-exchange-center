/**
 * DataTransServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package data.exchange.center.service.zxzh.webservice;

public class DataTransServiceServiceLocator extends org.apache.axis.client.Service implements data.exchange.center.service.zxzh.webservice.DataTransServiceService {

    public DataTransServiceServiceLocator() {
    }


    public DataTransServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DataTransServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DataTransServicePort
    private java.lang.String DataTransServicePort_address = "http://150.0.2.142:8095/zxfw/service/dataTransWebService.ws";

    public java.lang.String getDataTransServicePortAddress() {
        return DataTransServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataTransServicePortWSDDServiceName = "DataTransServicePort";

    public java.lang.String getDataTransServicePortWSDDServiceName() {
        return DataTransServicePortWSDDServiceName;
    }

    public void setDataTransServicePortWSDDServiceName(java.lang.String name) {
        DataTransServicePortWSDDServiceName = name;
    }

    public data.exchange.center.service.zxzh.webservice.DataTransService getDataTransServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataTransServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataTransServicePort(endpoint);
    }

    public data.exchange.center.service.zxzh.webservice.DataTransService getDataTransServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            data.exchange.center.service.zxzh.webservice.DataTransServiceServiceSoapBindingStub _stub = new data.exchange.center.service.zxzh.webservice.DataTransServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getDataTransServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataTransServicePortEndpointAddress(java.lang.String address) {
        DataTransServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (data.exchange.center.service.zxzh.webservice.DataTransService.class.isAssignableFrom(serviceEndpointInterface)) {
                data.exchange.center.service.zxzh.webservice.DataTransServiceServiceSoapBindingStub _stub = new data.exchange.center.service.zxzh.webservice.DataTransServiceServiceSoapBindingStub(new java.net.URL(DataTransServicePort_address), this);
                _stub.setPortName(getDataTransServicePortWSDDServiceName());
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
        if ("DataTransServicePort".equals(inputPortName)) {
            return getDataTransServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://sjzh.swap.tdh/", "DataTransServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://sjzh.swap.tdh/", "DataTransServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DataTransServicePort".equals(portName)) {
            setDataTransServicePortEndpointAddress(address);
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
