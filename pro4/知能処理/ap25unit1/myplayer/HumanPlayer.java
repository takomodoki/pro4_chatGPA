// package myplayer;

// import static ap25.Board.*;
// import static ap25.Color.*;

// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
// import java.util.stream.IntStream;

// import ap25.*;

// public class HumanPlayer extends Player {
//     private Color color;
//     private Board board;
//     public HumanPlayer(String name, Color color) {

//         this.color = color;
//       }

//     public HumanPlayer(Color color) {
//         this.color = color;
//     }

//     @Override
//     public void setBoard(Board board) {
//         this.board = board.clone();
//     }

//     @Override
//     public Move think(Board board) {
//         Scanner scanner = new Scanner(System.in);
//         List<Move> legalMoves = board.findLegalMoves(color);

//         System.out.println("あなたの番です: " + color);
//         System.out.println("合法手: " + legalMoves);
//         System.out.print("手を入力してください（例: d3 または pass）: ");

//         while (true) {
//             String input = scanner.nextLine().trim().toLowerCase();

//             Move move;
//             if (input.equals("pass")) {
//                 move = Move.ofPass(color);
//             } else {
//                 try {
//                     int index = Move.parseIndex(input);
//                     move = new Move(index, color);
//                 } catch (Exception e) {
//                     System.out.println("入力形式が正しくありません。再入力してください。");
//                     continue;
//                 }
//             }

//             if (legalMoves.contains(move)) {
//                 return move;
//             } else {
//                 System.out.println("その手は合法ではありません。再入力してください。");
//             }
//         }
//     }

//     @Override
//     public Color getColor() {
//         return color;
//     }

//     @Override
//     public String toString() {
//         return "Human(" + color + ")";
//     }
// }


