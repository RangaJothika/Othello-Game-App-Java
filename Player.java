public class Player {

    private String name;
    private char color;

    public Player(String n, char c) {
        name = n;
        color = Character.toUpperCase(c);
    }

    public char getColor() {
        return color;
    }

    public void setColor(char c) {
        this.color = Character.toUpperCase(c);
    }

    public String getName() {
        return name;
    }
}
