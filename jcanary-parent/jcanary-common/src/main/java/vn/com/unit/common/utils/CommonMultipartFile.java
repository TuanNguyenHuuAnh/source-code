/*******************************************************************************
 * Class        ：CommonMultipartFile
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：tantm
 * Change log   ：2021/01/20：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * CommonMultipartFile
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class CommonMultipartFile implements MultipartFile {

	/** The name. */
	private final String name;

	/** The original filename. */
	private String originalFilename;

	/** The content type. */
	private String contentType;

	/** The content. */
	private final byte[] content;

	/**
     * Create a new CustomMultipartFile with the given content.
     *
     * @param name
     *            the name of the file
     * @param contentStream
     *            the content of the file as stream
     * @throws IOException
     *             if reading from the stream failed
     * @author tantm
     */
	public CommonMultipartFile(String name, InputStream contentStream) throws IOException {
		this(name, "", null, FileCopyUtils.copyToByteArray(contentStream));
	}
	
	/**
     * Create a new CustomMultipartFile with the given content.
     *
     * @param name
     *            the name of the file
     * @param originalFilename
     *            the original filename (as on the client's machine)
     * @param contentType
     *            the content type (if known)
     * @param content
     *            the content of the file
     * @author tantm
     */
	public CommonMultipartFile(
			String name, String originalFilename, String contentType, byte[] content) {
		this.name = name;
		this.originalFilename = (originalFilename != null ? originalFilename : "");
		this.contentType = contentType;
		this.content = (content != null ? content : new byte[0]);
	}

	/**
     * Create a new CustomMultipartFile with the given content.
     *
     * @param name
     *            the name of the file
     * @param originalFilename
     *            the original filename (as on the client's machine)
     * @param contentType
     *            the content type (if known)
     * @param contentStream
     *            the content of the file as stream
     * @throws IOException
     *             if reading from the stream failed
     * @author tantm
     */
	public CommonMultipartFile(
			String name, String originalFilename, String contentType, InputStream contentStream)
			throws IOException {

		this(name, originalFilename, contentType, FileCopyUtils.copyToByteArray(contentStream));
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getOriginalFilename()
	 */
	@Override
	public String getOriginalFilename() {
		return this.originalFilename;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getContentType()
	 */
	@Override
	public String getContentType() {
		return this.contentType;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return (this.content.length == 0);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getSize()
	 */
	@Override
	public long getSize() {
		return this.content.length;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getBytes()
	 */
	@Override
	public byte[] getBytes() throws IOException {
		return this.content;
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(this.content);
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.multipart.MultipartFile#transferTo(java.io.File)
	 */
	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		FileCopyUtils.copy(this.content, dest);
	}

}