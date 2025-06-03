package myplayer;

import static ap25.Board.*;
import static ap25.Color.*;

import java.util.List;
import java.util.Map;

import ap25.*;

public class MyBoardFormatter {
  public static String format(MyBoard board) {
    var turn = board.getTurn();//ターンの取得
    var move = board.getMove();//moveの取得
    var blacks = board.findNoPassLegalIndexes(BLACK);//黒側の打てる手のリスト
    var whites = board.findNoPassLegalIndexes(WHITE);//白側の打てる手のリスト
    var legals = Map.of(BLACK, blacks, WHITE, whites);//手番ごとの合法手をMAPに格納

    var buf = new StringBuilder("  ");
    for (int k = 0; k < SIZE; k++) buf.append(Move.toColString(k));
    buf.append("\n");

    for (int k = 0; k < SIZE * SIZE; k++) {//全てのマスをループ処理して1マスずつ出力
      int col = k % SIZE;//col（列）とrow（行）でマスの位置を獲得
      int row = k / SIZE;

      if (col == 0) buf.append((row + 1) + "|");//行列番号の表示
      //buf.append()は文字列をどんどん追加していくメソッド

      if (board.get(k) == NONE) {//マスが空白なら
        boolean legal = false;
        var b = blacks.contains(k);
        var w = whites.contains(k);
        if (turn == BLACK && b) legal = true;
        if (turn == WHITE && w) legal = true;
        buf.append(legal ? '.' : ' ');
      } else {
        var s = board.get(k).toString();//文字の色を取得
        if (move != null && k == move.getIndex()) s = s.toUpperCase();//最後に打たれた場所ならtoUpperCaseで目立たせる
        buf.append(s);
      }

      if (col == SIZE - 1) {//直前の手または合法手一覧を表示
        buf.append("| ");
        if (row == 0 && move != null) {
          buf.append(move);
        } else if (row == 1) {
          buf.append(turn + ": " + toString(legals.get(turn)));
        }
        buf.append("\n");
      }
    }

    buf.setLength(buf.length() - 1);
    return buf.toString();
  }

  static List<String> toString(List<Integer> moves) {//合法手一覧を表示
    return moves.stream().map(k -> Move.toIndexString(k)).toList();//表示
  }
}
