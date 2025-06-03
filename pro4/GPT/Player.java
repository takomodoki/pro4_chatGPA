package GPT;
public class Player {
    private int hp;
    private State state;

    public Player() {
        this.hp = 100; // 初期HP
        this.state = new NormalState(); // 初期状態
    }

    public void attack(Enemy enemy) {
        int damage = 10; // 固定ダメージ
        System.out.println("プレイヤーの攻撃！" + damage + "のダメージを与えた。");
        enemy.reduceHp(damage);
    }

    public void reduceHp(int damage) {
        hp -= damage;
        System.out.println("プレイヤーは" + damage + "のダメージを受けた。");
    }

    public void heal(int amount) {
        hp += amount;
        System.out.println("プレイヤーのHPが" + amount + "回復した。");
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int getHp() {
        return hp;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
