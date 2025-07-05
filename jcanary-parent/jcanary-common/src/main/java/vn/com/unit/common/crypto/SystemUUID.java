/*******************************************************************************
 * Class        ：SystemUUID
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.crypto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * SystemUUID.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class SystemUUID {

    /**
     * Instantiates a new system UUID.
     */
    private SystemUUID() {
        // this is static class
    }

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public static void main(String[] args) throws IOException {
        System.out.println(getSystemUUID());
    }

    /**
     * Gets the system UUID.
     *
     * @return the system UUID
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public static String getSystemUUID() throws IOException {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            return getWindowsUUID();
        }

        return getLinuxUUID();
    }

    /**
     * Gets the windows UUID.
     *
     * @return the windows UUID
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    private static String getWindowsUUID() throws IOException {
        String output = ProgramExec.execute("wmic csproduct get UUID");
        return output.split("\\s+")[1];
    }

    /**
     * Gets the linux UUID.
     *
     * @return the linux UUID
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    private static String getLinuxUUID() throws IOException {
        String output = ProgramExec.execute("ls /dev/disk/by-uuid");
        List<String> uuids = Arrays.asList(output.split("\\s+"));
        uuids.sort((a, b) -> b.compareTo(a));
        return uuids.get(0);
    }
}
