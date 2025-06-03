package GPT;
public sealed interface State permits NormalState, PoisonState, ParalysisState {
    void applyEffect(Player player);
    boolean isExpired();
    void onTurnStart();
}
