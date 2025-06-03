package GPT;
public class Main {
    public static void main(String[] args) {
        Player player = new Player();
        int winCount = 0;

        while (true) {
            System.out.println("プロローグ: 新たな敵が現れた！");
            Enemy enemy = EnemyFactory.createEnemy();
            BattleScene battle = new BattleScene(player, enemy);
            boolean victory = battle.startBattle();

            if (victory) {
                System.out.println("勝利！HPが25回復した。");
                player.heal(25);
                winCount++;
            } else {
                System.out.println("敗北...連勝数: " + winCount);
                break;
            }
        }
    }
}
