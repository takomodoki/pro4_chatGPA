public final class NormalState implements BaseState {
    @Override
    public void applyEffect(Character target){}

    @Override
    public boolean isPaused(){
        return false;
    }

    @Override
    public boolean isExpired(){
        return false;
    }

    @Override
    public String currentState(){
        return "通常";
    }

    @Override
    public void startTurn(){}
}
