/** A bird records whether it can fly. */
public class Bird extends Animal {
    private boolean canFly;

    public Bird(String name, int age, boolean canFly) {
        super(name, age);
        setCanFly(canFly);
    }

    public boolean getCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    @Override
    public String makeSound() {
        return "The bird chirps.";
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", Can Fly: " + (canFly ? "Yes" : "No");
    }
}
