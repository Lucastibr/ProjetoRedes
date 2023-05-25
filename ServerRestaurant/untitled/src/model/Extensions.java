package model;

import java.util.ArrayList;
import java.util.List;

public class Extensions {
    public static List<String> Nomes(){
        var listNomes = new ArrayList<String>();

        listNomes.add("Geraldo");
        listNomes.add("Professor");
        listNomes.add("Professor Geraldo");

        return listNomes;
    }
}
