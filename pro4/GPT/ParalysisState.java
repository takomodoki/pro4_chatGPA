package GPT;
public final class ParalysisState implements State {
    private int turnCount = 0;

    @Override
    public void applyEffect(Player player) {
        // 麻痺状態の効果はBattleSceneで処理
    }

    @Override
    public boolean isExpired() {
        return turnCount >= 3;
    }

    @Override
    public void onTurnStart() {
        turnCount++;
    }
}
