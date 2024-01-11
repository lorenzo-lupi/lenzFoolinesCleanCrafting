package lenzInsanityCleanCrafting.Craft;

public record IndexSelection(int x, int y){

    public IndexSelection{
        if(x < 0 || y < 0)
            throw new IllegalArgumentException("x and y must be positive");
    }

    public int compareTo(IndexSelection that){
        if(this.x() < that.x() && this.y() < that.y()) return 1;
        if(this.x() > that.x() && this.y() > that.y()) return -1;
        return 0;
    }

    public boolean greaterThan(IndexSelection that){
        return this.x() < that.x() && this.y() < that.y();
    }

    public boolean greaterThanOrEqual(IndexSelection that){
        return this.x() <= that.x() && this.y() <= that.y();
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
