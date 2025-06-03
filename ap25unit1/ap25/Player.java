package ap25;

public abstract class Player {
  String name;
  Color color;
  Board board;

    //Playerのコンストラクタ
  public Player(String name, Color color) {
    this.name = name;
    this.color = color;
  }

  //盤上更新
  public void setBoard(Board board) { this.board = board; }
  //Player色と名前のゲッタ
  public Color getColor() { return this.color; }
  public String toString() { return this.name; }
//盤上を読み込んで思考（次の手）を返す
  public Move think(Board board) { return null; }
}
