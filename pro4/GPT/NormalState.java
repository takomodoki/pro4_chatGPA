package GPT;
public final class NormalState implements State {
    @Override
    public void applyEffect(Player player) {
        // 通常状態では効果なし
    }

    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public void onTurnStart() {
        // 通常状態では何もしない
    }
}
