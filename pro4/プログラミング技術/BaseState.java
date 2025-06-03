public sealed interface BaseState permits NormalState, PoisonedState, ParalyzedState{
    void applyEffect(Character target); //行動後の状態異常によるイベント
    boolean isPaused();                 //行動可能かどうかを返す
    boolean isExpired();                //状態異常が効果切れかどうかを返す
    String currentState();              //現在の状態の名前を返す
    void startTurn();                   //状態異常時の経過ターン数をカウント
}
