/**
 * TmriJaxRpcOutAccess.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package data.exchange.center.service.zgslsj.webService;

public interface TmriJaxRpcOutAccess extends java.rmi.Remote {
    public java.lang.String queryObjectOut(java.lang.String xtlb, java.lang.String jkxlh, java.lang.String jkid, java.lang.String GBKXmlDoc) throws java.rmi.RemoteException;
    public java.lang.String writeObjectOut(java.lang.String xtlb, java.lang.String jkxlh, java.lang.String jkid, java.lang.String GBKXmlDoc) throws java.rmi.RemoteException;
    public java.lang.String writeListOut(java.lang.String xtlb, java.lang.String jkxlh, java.lang.String jkid, java.lang.String GBKXmlDoc) throws java.rmi.RemoteException;
    public java.lang.String queryObjectOutACDR1(java.lang.String jkxlh, java.lang.String jkid, java.lang.String xmlDoc) throws java.rmi.RemoteException;
    public java.lang.String queryObjectOutACDR2(java.lang.String jkxlh, java.lang.String jkid, java.lang.String xmlDoc) throws java.rmi.RemoteException;
    public java.lang.String writeObjectOutACDW1(java.lang.String jkxlh, java.lang.String jkid, java.lang.String xmlDoc) throws java.rmi.RemoteException;
    public java.lang.String writeObjectOutACDW2(java.lang.String jkxlh, java.lang.String jkid, java.lang.String xmlDoc) throws java.rmi.RemoteException;
}
