package service.sjsb.xmlvalidate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * 
 * Implement LSResourceResolver to customize resource resolution when parsing
 * schemas.
 * <p>
 * SchemaFactory uses a LSResourceResolver when it needs to locate external
 * resources while parsing schemas, although exactly what constitutes "locating
 * external resources" is up to each schema language.
 * </p>
 * <p>
 * For example, for W3C XML Schema, this includes files &lt;include&gt;d or
 * &lt;import&gt;ed, and DTD referenced from schema files, etc.
 * </p>
 * 
 */
class SchemaResourceResolver implements LSResourceResolver {

	private static final Logger log = Logger.getLogger(SchemaResourceResolver.class);

	/**
	 * 
	 * Allow the application to resolve external resources.
	 * 
	 * <p>
	 * The LSParser will call this method before opening any external resource,
	 * including the external DTD subset, external entities referenced within the
	 * DTD, and external entities referenced within the document element (however,
	 * the top-level document entity is not passed to this method). The application
	 * may then request that the LSParser resolve the external resource itself, that
	 * it use an alternative URI, or that it use an entirely different input source.
	 * </p>
	 * 
	 * <p>
	 * Application writers can use this method to redirect external system
	 * identifiers to secure and/or local URI, to look up public identifiers in a
	 * catalogue, or to read an entity from a database or other input source
	 * (including, for example, a dialog box).
	 * </p>
	 */
	public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
		log.info("/n>> Resolving " + "/n" + "TYPE: " + type + "/n" + "NAMESPACE_URI: " + namespaceURI + "/n"
				+ "PUBLIC_ID: " + publicId + "/n" + "SYSTEM_ID: " + systemId + "/n" + "BASE_URI: " + baseURI + "/n");

		String schemaLocation = baseURI.substring(0, baseURI.lastIndexOf("/") + 1);

		if (systemId.indexOf("http://") < 0) {
			systemId = schemaLocation + systemId;
		}

		LSInput lsInput = new LSInputImpl();

		URI uri = null;
		try {
			uri = new URI(systemId);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File file = new File(uri);
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		lsInput.setSystemId(systemId);
		lsInput.setByteStream(is);

		return lsInput;
	}

	/**
	 * 
	 * Represents an input source for data
	 * 
	 */
	class LSInputImpl implements LSInput {

		private String publicId;
		private String systemId;
		private String baseURI;
		private InputStream byteStream;
		private Reader charStream;
		private String stringData;
		private String encoding;
		private boolean certifiedText;

		public LSInputImpl() {
		}

		public LSInputImpl(String publicId, String systemId, InputStream byteStream) {
			this.publicId = publicId;
			this.systemId = systemId;
			this.byteStream = byteStream;
		}

		public String getBaseURI() {
			return baseURI;
		}

		public InputStream getByteStream() {
			return byteStream;
		}

		public boolean getCertifiedText() {
			return certifiedText;
		}

		public Reader getCharacterStream() {
			return charStream;
		}

		public String getEncoding() {
			return encoding;
		}

		public String getPublicId() {
			return publicId;
		}

		public String getStringData() {
			return stringData;
		}

		public String getSystemId() {
			return systemId;
		}

		public void setBaseURI(String baseURI) {
			this.baseURI = baseURI;
		}

		public void setByteStream(InputStream byteStream) {
			this.byteStream = byteStream;
		}

		public void setCertifiedText(boolean certifiedText) {
			this.certifiedText = certifiedText;
		}

		public void setCharacterStream(Reader characterStream) {
			this.charStream = characterStream;
		}

		public void setEncoding(String encoding) {
			this.encoding = encoding;
		}

		public void setPublicId(String publicId) {
			this.publicId = publicId;
		}

		public void setStringData(String stringData) {
			this.stringData = stringData;
		}

		public void setSystemId(String systemId) {
			this.systemId = systemId;
		}

	}

}