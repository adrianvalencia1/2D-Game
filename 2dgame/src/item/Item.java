package item;

public class Item {
    
    public int stackSize;
    public int invX, invY;
    public boolean moveable;

    public Item(int stackSize, boolean moveable) {
        this.stackSize = stackSize;
        this.moveable = moveable;
    }

}
