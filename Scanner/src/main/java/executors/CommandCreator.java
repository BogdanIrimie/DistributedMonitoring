package executors;

/**
 * Transform user commands in Nmap commands
 */
public class CommandCreator {

    public String createCommand(String userCommand) {
        String[] tokens =  userCommand.split("\\s+");

        switch(tokens[0]) {
            case "security":
                switch (tokens[1]) {
                    case "tls":
                        return "nmap --script ssl-enum-ciphers -p 443 " + tokens[2];
                    case "ecrypt2lvl":
                        return "nmap --script ssl-enum-ciphers -p 443 " + tokens[2];
                    case "open_ports":
                        return "nmap " + tokens[2];
                    default :
                        return null;
                }
            default :
                return null;
        }
    }
}
