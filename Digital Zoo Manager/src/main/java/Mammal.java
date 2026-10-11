/** A mammal adds a fur color to the common animal properties. */
public class Mammal extends Animal {
    private String furColor;

    public Mammal(String name, int age, String furColor) {
        super(name, age);
        setFurColor(furColor);
    }

    public String getFurColor() {
        return furColor;
    }

    public void setFurColor(String furColor) {
        this.furColor = requireText(furColor, "Fur color");
    }

    @Override
    public String makeSound() {
        return "The mammal makes a sound.";
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", Fur Color: " + furColor;
    }
}
