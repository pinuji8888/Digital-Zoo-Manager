/** Superclass for every animal in the zoo. */
public class Animal {
    private String name;
    private int age;

    public Animal(String name, int age) {
        setName(name);
        setAge(age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = requireText(name, "Name");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be zero or greater.");
        }
        this.age = age;
    }

    public String makeSound() {
        return "The animal makes a sound.";
    }

    public String getInfo() {
        return "Name: " + name + ", Age: " + age;
    }

    protected static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " is required.");
        }
        return value.trim();
    }
}
