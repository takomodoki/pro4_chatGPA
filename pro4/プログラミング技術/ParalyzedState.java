public final class ParalyzedState implements BaseState {
    private int turnCount = 0;
    private final int DURATION = 3;

    @Override
    public void applyEffect(Character target){}

    @Override
    public boolean isPaused(){
        return turnCount % 2 == 0;
    }

    @Override
    public boolean isExpired(){
        return turnCount >= DURATION;
    }

    @Override
    public String currentState(){
        return "\u001b[00;33m麻痺\u001b[00m";
    }

    @Override
    public void startTurn(){
        turnCount++;
    }
}
