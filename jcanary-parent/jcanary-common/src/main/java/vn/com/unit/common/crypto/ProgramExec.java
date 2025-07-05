/*******************************************************************************
 * Class        ：ProgramExec
 * Created date ：2020/12/22
 * Lasted date  ：2020/12/22
 * Author       ：tantm
 * Change log   ：2020/12/22：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.common.crypto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ProgramExec.
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public class ProgramExec {

    /**
     * Instantiates a new program exec.
     */
    private ProgramExec() {
        // This is static class
    }

    /**
     * Execute.
     *
     * @param command
     *            the command type String
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public static String execute(String command) throws IOException {
        return execute(command.split("\\s+"));
    }

    /**
     * Execute.
     *
     * @param commands
     *            the commands type String[]
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @author tantm
     */
    public static String execute(String... commands) throws IOException {
        ProcessBuilder pb = new ProcessBuilder().command(commands).redirectErrorStream(true);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(pb.start().getInputStream()));

        // read the output from the command
        String output = "";
        String s;
        while ((s = stdInput.readLine()) != null) {
            output += s + "\n";
        }

        return output;
    }
}
