import java.util.ArrayList;

/** Dependency-free behavioral checks; run using test.sh. */
public final class ZooTests {
    private static int checks;

    public static void main(String[] args) {
        Animal animal = new Animal("  Alex  ", 0);
        check("Alex".equals(animal.getName()), "Name is trimmed");
        check(animal.getAge() == 0, "Zero is a valid age");
        check("The animal makes a sound.".equals(animal.makeSound()), "Base sound");
        animal.setName("Sam");
        animal.setAge(4);
        check("Name: Sam, Age: 4".equals(animal.getInfo()), "Base setters and info");

        Mammal mammal = new Mammal("Leo", 5, " Golden ");
        check("Golden".equals(mammal.getFurColor()), "Fur color is trimmed");
        check("Name: Leo, Age: 5, Fur Color: Golden".equals(mammal.getInfo()), "Mammal info");
        mammal.setFurColor("Brown");
        check(mammal.getInfo().contains("Brown"), "Fur color setter");
        Bird bird = new Bird("Rio", 2, true);
        check(bird.getCanFly() && bird.getInfo().contains("Can Fly: Yes"), "Flying bird");
        bird.setCanFly(false);
        check(!bird.getCanFly() && bird.getInfo().contains("Can Fly: No"), "Flight setter");

        ArrayList<Animal> zoo = new ArrayList<>();
        zoo.add(mammal);
        zoo.add(bird);
        check("The mammal makes a sound.".equals(zoo.get(0).makeSound()), "Mammal polymorphism");
        check("The bird chirps.".equals(zoo.get(1).makeSound()), "Bird polymorphism");
        expectInvalid(() -> new Animal(null, 0));
        expectInvalid(() -> new Animal("   ", 0));
        expectInvalid(() -> new Animal("Alex", -1));
        expectInvalid(() -> new Mammal("Leo", 2, " "));
        expectInvalid(() -> mammal.setFurColor(null));
        expectInvalid(() -> animal.setAge(-1));
        expectInvalid(() -> animal.setName(""));
        check(animal.getAge() == 4 && "Sam".equals(animal.getName()), "Rejected edits preserve valid state");
        System.out.println("PASS: " + checks + " model checks.");
    }

    private static void expectInvalid(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            checks++;
            return;
        }
        throw new AssertionError("Invalid input should be rejected");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
        checks++;
    }
}
