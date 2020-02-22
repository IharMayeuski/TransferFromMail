package by.transfer.maevo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constant {
    public final static String TITLE_NAME = "CV";
    public final static String BUTTON_NAME = "\t\tStart\t\t\t\t";
    public final static String SCENE_NAME = "Your mail:";
    public final static Map<String, String> myMails = new HashMap<String, String>() {
        {

        }
    };
    public final static List <String> protocols = new ArrayList<String>() {
        {
            add("pop3");
            add("imap");
        }
    };
}