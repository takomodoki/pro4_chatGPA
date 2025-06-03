package GPT;
public final class PoisonState implements State {
    private int turnCount = 0;

    @Override
    public void applyEffect(Player player) {
        player.reduceHp(2);
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
