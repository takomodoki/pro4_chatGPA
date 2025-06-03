package myplayer;

import static ap25.Board.*;
import static ap25.Color.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import ap25.*;

class MyEval {
  static float[][] M = { // 盤面に点数（重要度）を割り当て
      { 10, 10, 10, 10, 10, 10 },
      { 10, -5, 1, 1, -5, 10 },
      { 10, 1, 1, 1, 1, 10 },
      { 10, 1, 1, 1, 1, 10 },
      { 10, -5, 1, 1, -5, 10 },
      { 10, 10, 10, 10, 10, 10 },
  };

  public float value(Board board) {
    if (board.isEnd())
      return 1000000 * board.score();// スコアに大きな定数をかけて評価する。score()は自分と相手の石の差を返す

    return (float) IntStream.range(0, LENGTH)// まだ試合が終わってない時
        .mapToDouble(k -> score(board, k))// mapToDoubleは整数ストリームをDouble型のストリームに変換する
        .reduce(Double::sum).orElse(0);// reduceの中はDoubleの値を合計する。orElse(0)は値が存在すればその値をなければ０を返す。
  }

  float score(Board board, int k) {//
    return M[k / SIZE][k % SIZE] * board.get(k).getValue();// Mの点数と手の値の積をscoreとしてだす
  }
}

public class TestPlayer extends ap25.Player {
  static final String MY_NAME = "Test";
  MyEval eval;// 評価
  int depthLimit;// 深さ制限
  Move move;
  MyBoard board;

  public TestPlayer(Color color) {
    this(MY_NAME, color, new MyEval(), 2);// MyPlayerを作成
  }

  public TestPlayer(String name, Color color, MyEval eval, int depthLimit) {// Myplayerコンストラクタの多重定義
    super(name, color);
    this.eval = eval;
    this.depthLimit = depthLimit;
    this.board = new MyBoard();
  }

  public TestPlayer(String name, Color color, int depthLimit) {// Myplayerコンストラクタの多重定義
    this(name, color, new MyEval(), depthLimit);
  }

  public void setBoard(Board board) {
    for (var i = 0; i < LENGTH; i++) {
      this.board.set(i, board.get(i));// board の i 番目のマスの状態を this.board にコピーする
    }
  }

  boolean isBlack() {// 黒かどうかの判定
    return getColor() == BLACK;// 黒ならtrue
  }

  public Move think(Board board) {
    this.board = this.board.placed(board.getMove());// moveを入れてboard盤面を変更

    if (this.board.findNoPassLegalIndexes(getColor()).size() == 0) {// 打てる手があるかどうか
      this.move = Move.ofPass(getColor());// パスする
    } else {// 打てる手があれば
      var newBoard = isBlack() ? this.board.clone() : this.board.flipped();// 黒だったらコピー、白だったら反転
      this.move = null;

      maxSearch(newBoard, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, 0);// 評価値がいい選択肢だけ残す。αーβ法

      this.move = this.move.colored(getColor());// 評価値のいい手に変える
    }

    this.board = this.board.placed(this.move);// 盤面変更
    return this.move;// 最善（評価値最大）となる手を返す
  }

  float maxSearch(Board board, float alpha, float beta, int depth) {// 自分の評価値を最大にする動き
    if (isTerminal(board, depth))
      return this.eval.value(board);

    var moves = board.findLegalMoves(BLACK);
    moves = order(moves);

    if (depth == 0)
      this.move = moves.get(0);

    for (var move : moves) {
      var newBoard = board.placed(move);
      float v = minSearch(newBoard, alpha, beta, depth + 1);// 深さ（ターン数）をプラス１して送る

      if (v > alpha) {
        alpha = v;
        if (depth == 0)
          this.move = move;
      }

      if (alpha >= beta)
        break;
    }

    return alpha;
  }

  float minSearch(Board board, float alpha, float beta, int depth) {// 相手の評価値を最大値を最大にする動き
    if (isTerminal(board, depth))
      return this.eval.value(board);

    var moves = board.findLegalMoves(WHITE);
    moves = order(moves);

    for (var move : moves) {
      var newBoard = board.placed(move);
      float v = maxSearch(newBoard, alpha, beta, depth + 1);
      beta = Math.min(beta, v);
      if (alpha >= beta)
        break;
    }

    return beta;
  }

  boolean isTerminal(Board board, int depth) {// 思考の終わりを判断する
    return board.isEnd() || depth > this.depthLimit;// 試合が終わったか、考える最大までいったか
  }

  List<Move> order(List<Move> moves) {// 動きが複数パターンある時にランダムに動きを決める
    var shuffled = new ArrayList<Move>(moves);
    Collections.shuffle(shuffled);
    return shuffled;
  }
}
