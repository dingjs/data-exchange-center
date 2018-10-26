/**
 * TmriJaxRpcOutAccessServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package data.exchange.center.service.zgslsj.webService;

public class TmriJaxRpcOutAccessServiceLocator extends org.apache.axis.client.Service implements data.exchange.center.service.zgslsj.webService.TmriJaxRpcOutAccessService {

    public TmriJaxRpcOutAccessServiceLocator() {
    }


    public TmriJaxRpcOutAccessServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TmriJaxRpcOutAccessServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TmriOutAccess
    private java.lang.String TmriOutAccess_address = "http://192.1.36.74:8181/drsp-ebsm-server/api/services/version/52ba0099e4064a13b5f75f6f7c87c6ac/soapWebService.TmriJaxRpcOutAccessService.";

    public java.lang.String getTmriOutAccessAddress() {
        return TmriOutAccess_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TmriOutAccessWSDDServiceName = "TmriOutAccess";

    public java.lang.String getTmriOutAccessWSDDServiceName() {
        return TmriOutAccessWSDDServiceName;
    }

    public void setTmriOutAccessWSDDServiceName(java.lang.String name) {
        TmriOutAccessWSDDServiceName = name;
    }

    public data.exchange.center.service.zgslsj.webService.TmriJaxRpcOutAccess getTmriOutAccess() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TmriOutAccess_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTmriOutAccess(endpoint);
    }

    public data.exchange.center.service.zgslsj.webService.TmriJaxRpcOutAccess getTmriOutAccess(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            data.exchange.center.service.zgslsj.webService.TmriOutAccessSoapBindingStub _stub = new data.exchange.center.service.zgslsj.webService.TmriOutAccessSoapBindingStub(portAddress, this);
            _stub.setPortName(getTmriOutAccessWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTmriOutAccessEndpointAddress(java.lang.String address) {
        TmriOutAccess_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (data.exchange.center.service.zgslsj.webService.TmriJaxRpcOutAccess.class.isAssignableFrom(serviceEndpointInterface)) {
                data.exchange.center.service.zgslsj.webService.TmriOutAccessSoapBindingStub _stub = new data.exchange.center.service.zgslsj.webService.TmriOutAccessSoapBindingStub(new java.net.URL(TmriOutAccess_address), this);
                _stub.setPortName(getTmriOutAccessWSDDServiceName());
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
        if ("TmriOutAccess".equals(inputPortName)) {
            return getTmriOutAccess();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://172.22.7.10:9086/transweb/services/TmriOutAccess", "TmriJaxRpcOutAccessService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://172.22.7.10:9086/transweb/services/TmriOutAccess", "TmriOutAccess"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TmriOutAccess".equals(portName)) {
            setTmriOutAccessEndpointAddress(address);
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
