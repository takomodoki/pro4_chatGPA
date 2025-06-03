package GPT;
public class BattleScene {
    private Player player;
    private Enemy enemy;

    public BattleScene(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public boolean startBattle() {
        while (player.isAlive() && enemy.isAlive()) {
            // プレイヤーのターン
            player.getState().onTurnStart();
            player.getState().applyEffect(player);
            if (!player.isAlive()) break;

            player.attack(enemy);
            if (!enemy.isAlive()) break;

            // 麻痺状態の処理
            if (player.getState() instanceof ParalysisState) {
                enemy.attack(player);
                if (!player.isAlive()) break;
            }

            // 敵のターン
            enemy.attack(player);
            if (!player.isAlive()) break;
        }
        return player.isAlive();
    }
}
