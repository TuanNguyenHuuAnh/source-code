package vn.com.unit.ep2p.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
//import org.springframework.util.StreamUtils;

public class RequestWrapper extends HttpServletRequestWrapper {

	private byte[] body;

	private ByteArrayOutputStream cacheByte;

	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);

//		InputStream requestInputStream = request.getInputStream();
//
//		body = StreamUtils.copyToByteArray(requestInputStream);
	}

	public ServletInputStream getCacheInputStream() throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

		ServletInputStream servletInputStream = new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

			@Override
			public boolean isFinished() {
				return byteArrayInputStream.available() == 0;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener listener) {
				throw new UnsupportedOperationException();
			}
		};

		return servletInputStream;
	}

	public BufferedReader getCacheReader() throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);

		return new BufferedReader(new InputStreamReader(byteArrayInputStream));
	}

	private void cacheInputStream() throws IOException {
		/*
		 * Cache the inputStream in order to read it multiple times. For convenience, I
		 * use apache.commons IOUtils
		 */
		cacheByte = new ByteArrayOutputStream();
		IOUtils.copy(super.getInputStream(), cacheByte);
	}

	/* An inputstream which reads the cached request body */
	public class CachedServletInputStream extends ServletInputStream {
		private ByteArrayInputStream input;

		public CachedServletInputStream() {
			/* create a new input stream from the cached request body */
			input = new ByteArrayInputStream(cacheByte.toByteArray());
		}

		@Override
		public int read() throws IOException {
			return input.read();
		}

		@Override
		public boolean isFinished() {
			return input.available() == 0;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(ReadListener listener) {
			throw new RuntimeException("Not implemented");
		}
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
//		return getCacheInputStream();

		if (cacheByte == null)
			cacheInputStream();

		return new CachedServletInputStream();
	}

	@Override
	public BufferedReader getReader() throws IOException {
//		return getCacheReader();

		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

}
