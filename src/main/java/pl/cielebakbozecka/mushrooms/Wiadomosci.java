package pl.cielebakbozecka.mushrooms;

import java.io.Serializable;

public class Wiadomosci implements Serializable {

    private final TypWiadomosci typ;
    private final Object dane;

    public Wiadomosci(TypWiadomosci typ, Object dane){
        this.typ = typ;
        this.dane = dane;
    }

    public TypWiadomosci getTyp(){
        return typ;
    }

    public Object getDane(){
        return dane;
    }
}

