package GPT;
public class Skill {
    private String name;
    private int damage;
    private String effect;

    public Skill(String name, int damage, String effect) {
        this.name = name;
        this.damage = damage;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public String getEffect() {
        return effect;
    }
}
