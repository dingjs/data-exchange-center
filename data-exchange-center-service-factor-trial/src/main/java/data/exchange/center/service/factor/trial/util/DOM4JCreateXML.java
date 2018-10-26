package data.exchange.center.service.factor.trial.util;

import java.io.FileOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class DOM4JCreateXML {
    public static void main(String[] args) throws Exception    
    {    
        // 创建文档并设置文档的根元素节点   
        Element root = DocumentHelper.createElement("books");  
        Document doucment = DocumentHelper.createDocument(root);  
        //根节点  
        root.addAttribute("name","bookvalue");  
        //子节点  
        Element element1 = root.addElement("author1 ");  
        element1.addAttribute( "name", "James1" );  
        element1.addAttribute( "location1", "UK1" );  
        element1.addText( "James Strachan1" );  
          
        Element element = root.addElement("author2 ");  
        element.addAttribute( "name", "chen" );  
        element.addAttribute( "kenken", "ZK" );  
        element.addText( "chen kenken" );  
 
        //创建文件  
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
        FileOutputStream file = new FileOutputStream("C:/Users/Administrator/Desktop/xml/books.xml");  
        XMLWriter xmlwriter = new XMLWriter(file,format);  
        xmlwriter.write(doucment);
        xmlwriter.close();  
    } 
	
}
