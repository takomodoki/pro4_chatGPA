public final class PoisonedState implements BaseState {
    private int turnCount = 0;
    private final int DURATION = 3;

    @Override
    public void applyEffect(Character target){
        System.out.println("\u001b[00;35m" + target.getName() + "を毒が襲う！");
        target.reduceHp(5);
    }

    @Override
    public boolean isPaused(){
        return false;
    }

    @Override
    public boolean isExpired(){
        return turnCount >= DURATION;
    }

    @Override
    public String currentState(){
        return "\u001b[00;35m毒\u001b[00m";
    }

    @Override
    public void startTurn(){
        turnCount++;
    }
}
