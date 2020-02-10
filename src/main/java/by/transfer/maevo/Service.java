package by.transfer.maevo;

import java.util.ArrayList;
import java.util.List;

import static by.transfer.maevo.Constant.myMails;

public class Service {
    public List<String> getEmailList() {
        List<String> elements = new ArrayList<>();
        myMails.forEach((key, value) -> elements.add(key));
        return elements;
    }
}
