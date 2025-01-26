package pl.cielebakbozecka.mushrooms;

public class NaszGracz {

    private final int numer;
    public int punkty= 0;

    public NaszGracz(int numer){
        this.numer = numer;
    }

    public int getPunkty(){
        return punkty;
    }

    public int getNumer(){
        return numer;
    }

}
