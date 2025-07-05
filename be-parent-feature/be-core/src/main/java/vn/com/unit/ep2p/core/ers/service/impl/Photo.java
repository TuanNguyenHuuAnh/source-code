package vn.com.unit.ep2p.core.ers.service.impl;

//import fr.opensagres.xdocreport.document.images.IImageProvider;
import fr.opensagres.xdocreport.template.annotations.FieldMetadata;
import fr.opensagres.xdocreport.template.annotations.ImageMetadata;

// org.apache.velocity.exception.MethodInvocationException: Invocation of method 'registerImage' in  class fr.opensagres.xdocreport.document.docx.images.DocxImageRegistry threw exception fr.opensagres.xdocreport.core.XDocReportException: Image provider for field [photo] cannot be null! at fr.opensagres.xdocreport.document.docx.DocxReport@4ca5668a!word/document.xml[line 1, column 288017]
//registerImage

//import fr.opensagres.xdocreport.document.docx.images.DocxImageRegistry;

// XDocReportImage
public class Photo {
//	private final IImageProvider photo;
//	
//	public Photo(IImageProvider photo) {
//		this.photo = photo;
////		DocxImageRegistry a = new DocxImageRegistry(null, null, null, null);
////		a.registerImage(a, null, null);
//	}
//
//	@FieldMetadata(images = { @ImageMetadata(name = "photo") }, description = "Photo of list attachment")
//	public IImageProvider getPhoto() {
//		return photo;
//	}
//
////	public void setPhoto(IImageProvider photo) {
////		this.photo = photo;
////	}

	private byte[] bytes;

	public Photo(byte[] bytes) {
		super();
		this.bytes = bytes;
	}

	@FieldMetadata(images = { @ImageMetadata(name = "photo") }, description = "Photo")
	public byte[] getBytes() {
		return bytes;
	}
}
