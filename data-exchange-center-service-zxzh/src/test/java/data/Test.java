//package data;
//
//import java.io.File;
//import java.net.URLDecoder;
//
//import org.apache.commons.io.FileUtils;
//
//import data.exchange.center.service.zxzh.util.Singleton;
//
//
//public class Test {
////    public static void main(String[] args) {
////        try {
////                           
////            String jkxlh= "lkU984LfjkvnuY09114dfs09ddS2gdsjfr3fsdg";
////            String jkid = "ACDR1";
////            File contentFile = new File("D:\\dJ1.xml");
////            String fileContent = FileUtils.readFileToString(contentFile, "UTF-8");
////           String xml =  Singleton.getInstance().queryObjectOutACDR1(jkxlh, jkid, fileContent);
////           xml = URLDecoder.decode(xml, "utf-8");   
////           System.out.println(xml);
////        } catch (Exception e) {
////           e.printStackTrace();
////        }
//  
//    }
////    public static void main(String[] args) {
////        try {
////                           
////            String jkxlh= "lkU984LfjkvnuY09114dfs09ddS2gdsjfr3fsdg";
////            String jkid = "ACDR2";
////            File contentFile = new File("D:\\dJ2.xml");
////            String fileContent = FileUtils.readFileToString(contentFile, "UTF-8");
////           String xml =  Singleton.getInstance().queryObjectOutACDR2(jkxlh, jkid, fileContent);
////           xml = URLDecoder.decode(xml, "utf-8");   
////           System.out.println(xml);
////        } catch (Exception e) {
////           e.printStackTrace();
////        }
////  
////    }
////    public static void main(String[] args) {
////        try {
//////            JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
//////            Client client = factory.createClient(
//////                "http://172.16.192.244:8380/drsp-ebsm-server/api/services/0a6919e648df434ea91ebe4ca8b89f31?drsp_ticket=d1701bdaa05440e7a003fcd051f18b36 ");
//////
//////            Object[] obj = client.invoke("getTsRyInfoByCorpId", "2400");
//////            System.out.println(obj[0]);
////                            
////
////
////            // 名字空间 
////            String targetNamespace = "http://impl.service.dwjk.fy.np.thunisoft.com";
////            //服务名
////            String serName = "NpfyOrganServiceService";
////            //端口名
////            String pName = "NpfyOrganServiceServiceSoapBinding";
////            //服务地址
////            String endpointAddress = "http://192.1.36.74:8181/drsp-ebsm-server/api/services/ca8179820820415093be28d51c051880?drsp_ticket=1ba8f246ac1b42ad8c6273cd3215426d";
////            //方法名
////            String OPER_NAME = "getDeptAndUserByUpdateTime";
////            //参数名
////            QName serviceName = new QName(targetNamespace, serName);
////            QName portName = new QName(targetNamespace, pName);
////
////            javax.xml.ws.Service service = Service.create(serviceName);
////            service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
////
////            Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);
////
////            BindingProvider bp = (BindingProvider) dispatch;
////            Map<String, Object> rc = bp.getRequestContext();
////            rc.put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
////            //rc.put(BindingProvider.SOAPACTION_URI_PROPERTY, OPER_NAME);
////            rc.put(BindingProvider.SOAPACTION_URI_PROPERTY, "");
////            MessageFactory factory = ((SOAPBinding) bp.getBinding()).getMessageFactory();
////
////            SOAPMessage request = factory.createMessage();
////            SOAPBody body = request.getSOAPBody();
////            QName payloadName = new QName(targetNamespace, OPER_NAME, "");
////            SOAPBodyElement payload = body.addBodyElement(payloadName);
////
////            SOAPElement ag0 = payload.addChildElement("condition");
////            Document doc = DocumentHelper.createDocument();
////            Element root = doc.addElement("condition");
////            root.addElement("corpId").setText("2400");
////            root.addElement("updateBeginTime").setText("2014-01-01 18:00:00");
////            root.addElement("updateEndTime").setText("2016-01-01 00:00:00");
////            String asXML = doc.asXML();
////            ag0.addTextNode(asXML);
////
////            SOAPMessage reply = null;
////            try {
////                request.writeTo(System.out);
////                reply = dispatch.invoke(request);
////                reply.writeTo(System.out);
////            } catch (Exception wse) {
////                wse.printStackTrace();
////                return;
////            }
////            SOAPBody soapBody = reply.getSOAPBody();
////            SOAPBodyElement nextSoapBodyElement = (SOAPBodyElement) soapBody.getChildElements().next();
////            SOAPElement soapElement = (SOAPElement) nextSoapBodyElement.getChildElements().next();
////
////            System.out.println("获取回应信息为：" + soapElement.getValue());} catch (Exception e) {
////           e.printStackTrace();
////        }
////
////    }
//}
