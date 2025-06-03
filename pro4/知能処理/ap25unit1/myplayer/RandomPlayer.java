package myplayer;

import ap25.*;
import java.util.Random;

public class RandomPlayer extends Player {
  Random rand = new Random();

  public RandomPlayer(Color color) {//Playerクラスの継承クラス
    super("R", color);//名前'R'と色を格納
  }

  public Move think(Board board) {//盤面 Board を受け取り、次の手 Move を返すメソッド
    var moves = board.findLegalMoves(getColor());//実行可能な手のリスト
    var i = this.rand.nextInt(moves.size());//moveリストのサイズ内でランダムな値をだす。
    return moves.get(i);//実行可能な手の中からランダムに選んだ手を実行。
  }
}
