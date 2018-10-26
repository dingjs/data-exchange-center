/**
 * ILdtService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package data.exchange.center.service.wuhou.webservice;

public interface ILdtService extends java.rmi.Remote {
    public java.lang.String getCaseList(java.lang.String userid, java.lang.String pwd, java.lang.String latestSynchTime, java.lang.String ajzt, java.lang.String pageNum, java.lang.String fjm) throws java.rmi.RemoteException;
    public java.lang.String getCaseInfo(java.lang.String userid, java.lang.String pwd, java.lang.String caseID, java.lang.String latestSynchTime) throws java.rmi.RemoteException;
    public java.lang.String getChangeFileList(java.lang.String userid, java.lang.String pwd, java.lang.String latestSynchTime) throws java.rmi.RemoteException;
    public java.lang.String receiveDocument(java.lang.String userid, java.lang.String pwd, java.lang.String requestXML) throws java.rmi.RemoteException;
    public java.lang.String getFile(java.lang.String userid, java.lang.String pwd, java.lang.String caseID, java.lang.String serial) throws java.rmi.RemoteException;
    public java.lang.String getJzInfoList(java.lang.String userid, java.lang.String pwd, java.lang.String caseID) throws java.rmi.RemoteException;
    public java.lang.String getDelInfoList(java.lang.String userid, java.lang.String pwd, java.lang.String latestSynchTime) throws java.rmi.RemoteException;
    public java.lang.String caseEvolumeSave(java.lang.String uid, java.lang.String pass, java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getZzjg(java.lang.String uid, java.lang.String pwd, java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getRy(java.lang.String uid, java.lang.String pwd, java.lang.String xml) throws java.rmi.RemoteException;
    public java.lang.String getJzInfoListByYlz(java.lang.String userid, java.lang.String pwd, java.lang.String caseID) throws java.rmi.RemoteException;
    public java.lang.String dsrTjclSave(java.lang.String uid, java.lang.String pass, java.lang.String xml) throws java.rmi.RemoteException;
}
