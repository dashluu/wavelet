import java.net.URI;
import java.util.ArrayList;
import java.io.IOException;

class SearchEngineHandler implements URLHandler {
    private ArrayList<String> strLists;

    public SearchEngineHandler() {
        strLists = new ArrayList<>();
    }

    public String handleRequest(URI url) {
        String path = url.getPath();
        String[] parameters;

        if (path.equals("/")) {
            if (strLists.isEmpty()) {
                return "Empty list!";
            }
            String responseBody = "";
            for (String str : strLists) {
                responseBody += str + ";";
            }
            return responseBody;
        } else if (path.indexOf("/add") == 0) {
            parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                strLists.add(parameters[1]);
                return String.format("%s added to list!", parameters[1]);
            }
        } else if (path.indexOf("/search") == 0) {
            parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String responseBody = "";
                for (String str : strLists) {
                    if (str.contains(parameters[1])) {
                        responseBody += str + ";";
                    }
                }
                return responseBody;
            }
        }
        
        return "404 not found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new SearchEngineHandler());
    }
}
