package lenzInsanityCleanCrafting.Craft;

public record IndexSelection(int x, int y) {

    public boolean leftUpMore(IndexSelection that){
        return  this.x() <= that.x() && this.y() <= that.x();
    }

    public IndexSelection decreasedX() {
        return new IndexSelection(x - 1, y);
    }

    public IndexSelection decreasedY() {
        return new IndexSelection(x, y - 1);
    }

    public IndexSelection increasedX() {
        return new IndexSelection(x + 1, y);
    }

    public IndexSelection increasedY() {
        return new IndexSelection(x, y + 1);
    }

    public IndexSelection decreasedBoth(){
        return new IndexSelection(x - 1, y - 1);
    }

    public IndexSelection increasedBoth(){
        return new IndexSelection(x + 1, y + 1);
    }

    public IndexSelection decreasedXIncreasedY(){
        return new IndexSelection(x - 1, y + 1);
    }

    public IndexSelection increasedXDecreasedY(){
        return new IndexSelection(x + 1, y - 1);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof IndexSelection that))
            return false;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode(){
        return (x * 31 + y * 31) ;
    }

}
