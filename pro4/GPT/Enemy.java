package GPT;
public class Enemy {
    private String name;
    private int hp;
    private Skill skill1;
    private Skill skill2;

    public Enemy(String name, int hp, Skill skill1, Skill skill2) {
        this.name = name;
        this.hp = hp;
        this.skill1 = skill1;
        this.skill2 = skill2;
    }

    public void attack(Player player) {
        Skill skill = Math.random() < 0.5 ? skill1 : skill2;
        System.out.println(name + "の攻撃！スキル「" + skill.getName() + "」を使用。");
        player.reduceHp(skill.getDamage());

        switch (skill.getEffect()) {
            case "poison":
                player.setState(new PoisonState());
                System.out.println("プレイヤーは毒状態になった！");
                break;
            case "paralysis":
                player.setState(new ParalysisState());
                System.out.println("プレイヤーは麻痺状態になった！");
                break;
            default:
                // 通常攻撃
                break;
        }
    }

    public void reduceHp(int damage) {
        hp -= damage;
        System.out.println(name + "は" + damage + "のダメージを受けた。");
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }
}
