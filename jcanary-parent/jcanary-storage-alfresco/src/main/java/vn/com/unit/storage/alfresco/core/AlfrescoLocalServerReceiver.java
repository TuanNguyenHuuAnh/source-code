/*******************************************************************************
 * Class        AlfrescoLocalServerReceiver
 * Created date ：2020/07/27
 * Lasted date  ：2020/07/27
 * Author       ：tantm
 * Change log   ：2020/07/27：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.storage.alfresco.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

/**
 * Runs a Jetty server on a free port, waiting for OAuth to redirect to it with the verification code.
 * <p>
 * Mostly copied from oacurl by phopkins@google.com.
 * </p>
 *
 * @author tantm
 */
public final class AlfrescoLocalServerReceiver implements AlfrescoVerificationCodeReceiver {

    /** The Constant CALLBACK_PATH. */
    private static final String CALLBACK_PATH = "/Callback";

    /** The Constant LOCALHOST. */
    private static final String LOCALHOST = "127.0.0.1";

    /** The Constant PORT. */
    private static final int PORT = 8080;

    /** Server or {@code null} before {@link #getRedirectUri()}. */
    private Server server;

    /** Verification code or {@code null} before received. */
    volatile String code;

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.storage.alfresco.core.AlfrescoVerificationCodeReceiver#getRedirectUri()
     */
    @Override
    public String getRedirectUri() throws Exception {
        server = new Server(PORT);
        for (Connector c : server.getConnectors()) {
            c.setHost(LOCALHOST);
        }
        server.addHandler(new CallbackHandler());
        server.start();
        return "http://" + LOCALHOST + ":" + PORT + CALLBACK_PATH;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.storage.alfresco.core.AlfrescoVerificationCodeReceiver#waitForCode()
     */
    @Override
    public synchronized String waitForCode() {
        try {
            this.wait();
        } catch (InterruptedException exception) {
            // should not happen
        }
        return code;
    }

    /*
     * (non-Javadoc)
     * 
     * @see vn.com.unit.storage.alfresco.core.AlfrescoVerificationCodeReceiver#stop()
     */
    @Override
    public void stop() throws Exception {
        if (server != null) {
            server.stop();
            server = null;
        }
    }

    /**
     * Jetty handler that takes the verifier token passed over from the OAuth provider and stashes it where {@link #waitForCode} will find
     * it.
     */
    class CallbackHandler extends AbstractHandler {

        /*
         * (non-Javadoc)
         * 
         * @see org.mortbay.jetty.Handler#handle(java.lang.String, javax.servlet.http.HttpServletRequest,
         * javax.servlet.http.HttpServletResponse, int)
         */
        @Override
        public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException {
            if (!CALLBACK_PATH.equals(target)) {
                return;
            }
            writeLandingHtml(response);
            response.flushBuffer();
            ((Request) request).setHandled(true);
            String error = request.getParameter("error");
            if (error != null) {
                System.out.println("Authorization failed. Error=" + error);
                System.out.println("Quitting.");
                System.exit(1);
            }
            code = request.getParameter("code");
            synchronized (AlfrescoLocalServerReceiver.this) {
                AlfrescoLocalServerReceiver.this.notify();
            }
        }

        /**
         * Write landing html.
         *
         * @param response
         *            the response
         * @throws IOException
         *             Signals that an I/O exception has occurred.
         * @author tantm
         */
        private void writeLandingHtml(HttpServletResponse response) throws IOException {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html");

            PrintWriter doc = response.getWriter();
            doc.println("<html>");
            doc.println("<head><title>OAuth 2.0 Authentication Token Recieved</title></head>");
            doc.println("<body>");
            doc.println("Received verification code. Closing...");
            doc.println("<script type='text/javascript'>");
            // We open "" in the same window to trigger JS ownership of it, which lets
            // us then close it via JS, at least in Chrome.
            doc.println("window.setTimeout(function() {");
            doc.println("    window.open('', '_self', ''); window.close(); }, 1000);");
            doc.println("if (window.opener) { window.opener.checkToken(); }");
            doc.println("</script>");
            doc.println("</body>");
            doc.println("</HTML>");
            doc.flush();
        }
    }
}
