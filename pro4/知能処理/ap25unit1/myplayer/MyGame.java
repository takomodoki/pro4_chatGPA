package myplayer;

import ap25.*;
import static ap25.Color.*;
import java.util.*;
import java.util.stream.*;

public class MyGame {
  public static void main(String args[]) {// ここでmain
    var player1 = new myplayer.TestPlayer(BLACK);// ここで黒を自分に
    var player2 = new myplayer.TestPlayer2(WHITE);// 白を相手に
    var board = new MyBoard();// ボードの初期値
    var game = new MyGame(board, player1, player2);// ゲーム開始
    // for (int i = 0; i < 100; i++) {
      game.play();
      
    // }
  }

  static final float TIME_LIMIT_SECONDS = 60;// タイムリミット

  Board board;
  Player black;
  Player white;
  Map<Color, Player> players;
  List<Move> moves = new ArrayList<>();
  Map<Color, Float> times = new HashMap<>(Map.of(BLACK, 0f, WHITE, 0f));

  public MyGame(Board board, Player black, Player white) {
    this.board = board.clone();
    this.black = black;
    this.white = white;
    this.players = Map.of(BLACK, black, WHITE, white);
  }

  public void play() {
    this.players.values().forEach(p -> p.setBoard(this.board.clone()));// 対戦用のボードに初期のボードをコピー

    while (this.board.isEnd() == false) {
      var turn = this.board.getTurn();// どちらのターンかどうか
      var player = this.players.get(turn);// 色から操作する側を取得

      Error error = null;
      long t0 = System.currentTimeMillis();// ターン開始時間
      Move move;

      // play
      try {
        move = player.think(board.clone()).colored(turn);// thinkを動かして、手を与えている
      } catch (Error e) {
        error = e;
        move = Move.ofError(turn);
      }

      // record time
      long t1 = System.currentTimeMillis();// 現在の時間
      final var t = (float) Math.max(t1 - t0, 1) / 1000.f;
      this.times.compute(turn, (k, v) -> v + t);// 現在のプレイヤーの 経過時間を更新している処理です。

      // check
      move = check(turn, move, error);// 反則かどうか判断する
      moves.add(move);// その手を記憶

      // update board
      if (move.isLegal()) {// ルール守ってるかどうか
        board = board.placed(move);// ボード更新
      } else {
        board.foul(turn);// 反則負け
        break;
      }
      //高速化のため
      // System.out.println(board);// 現在のboardを表示
    }

    printResult(board, moves);// 結果表示
  }

  Move check(Color turn, Move move, Error error) {// エラーがあるか判別
    if (move.isError()) {
      System.err.printf("error: %s %s", turn, error);
      System.err.println(board);
      return move;
    }

    if (this.times.get(turn) > TIME_LIMIT_SECONDS) {// 時間制限を越したらエラーを出す
      System.err.printf("timeout: %s %.2f", turn, this.times.get(turn));
      System.err.println(board);
      return Move.ofTimeout(turn);
    }

    var legals = board.findLegalMoves(turn);
    if (move == null || legals.contains(move) == false) {// 反則だったらエラー表示
      System.err.printf("illegal move: %s %s", turn, move);
      System.err.println(board);
      return Move.ofIllegal(turn);
    }

    return move;
  }

  public Player getWinner(Board board) {// 勝った方のPlayerを返す
    return this.players.get(board.winner());
  }


  //(変更)勝ち数記憶
  public int winCount=0;

  public void printResult(Board board, List<Move> moves) {// 結果発表
    var result = String.format("%5s%-9s", "", "draw");
    var score = Math.abs(board.score());
    if (score > 0)
      result = String.format("%-4s won by %-2d", getWinner(board), score);

    var s = toString() + " -> " + result + "\t| " + toString(moves);
    System.out.println(s);
  }

  public String toString() {// 戦ってる人を表示する
    return String.format("%4s vs %4s", this.black, this.white);
  }

  public static String toString(List<Move> moves) {// すべての動きをまとめて表示
    return moves.stream().map(x -> x.toString()).collect(Collectors.joining());
  }
}
