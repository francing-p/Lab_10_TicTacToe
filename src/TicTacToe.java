import java.util.Scanner;

public class TicTacToe {
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static String board [][] = new String[ROWS][COLS]; // create board
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int playerRow = 0;
        int playerCol = 0;
        String cont=" ";

        do {
            boolean gameWinX = false;
            boolean gameWinO = false;
            boolean gameTie = false;
            int moveCount = 0; // initialize move counter
            clearBoard();
            do { // while there isn't a win , do this -

                // ------------- INITIAL MOVE -- X ---------------

                display(); // display the board

                // -- GET MOVES , SUBTRACT ONE FOR PROPER ARRAY INDEX
                int rowMove = SafeInput.getRangedInt(in, "X. Enter your row position [1-3]", 1, 3);
                playerRow = rowMove - 1;
                int colMove = SafeInput.getRangedInt(in, "X. Enter your column position [1-3]", 1, 3);
                playerCol = colMove - 1;

                // -- PLACE MOVE ON BOARD IF VALID -- PLAYER X
                boolean ValidMove = isValidMove(playerRow, playerCol);
                if (ValidMove) {
                    board[playerRow][playerCol] = "X";
                    moveCount += 1; // add to move counter
                }
                // -- IF MOVE IS INVALID - RUN THROUGH WHILE LOOP
                else {
                    while (!ValidMove) {
                        System.out.println("Move invalid. Try again.");
                        rowMove = SafeInput.getRangedInt(in, "X. Enter your row position [1-3]", 1, 3);
                        playerRow = rowMove - 1;
                        colMove = SafeInput.getRangedInt(in, "X. Enter your column position [1-3]", 1, 3);
                        playerCol = colMove - 1;
                        ValidMove = isValidMove(playerRow, playerCol);
                    }
                    board[playerRow][playerCol] = "X"; // add move once loop ends
                    moveCount += 1; // add to move counter
                }


                // -- CHECK FOR WIN WHEN FIVE OR MORE MOVES HAVE OCCURRED -- WIN FOR X
                // Use break in these loops because otherwise it will ask 'O' for another move
                if (moveCount == 9) {
                    display();
                    System.out.println("Board full. Tie!");
                    gameTie = true;
                    break;
                }
                else if (moveCount>=5){
                    gameTie = isTie();
                    gameWinX = isWin("X");
                    gameWinO = isWin("O");
                    if (gameWinX){
                        display();
                        System.out.println("Player X won!");
                        break;
                    }
                    if (gameWinO){
                        display();
                        System.out.println("Player O won!");
                        break;
                    }
                    if (gameTie){
                        display();
                        System.out.println("There was a tie.");
                        break;
                    }
                }

                // ---------- INITIAL MOVE -- O -----------
                display();

                rowMove = SafeInput.getRangedInt(in, "O. Enter your row position [1-3]", 1, 3);
                playerRow = rowMove - 1;
                colMove = SafeInput.getRangedInt(in, "O. Enter your column position [1-3]", 1, 3);
                playerCol = colMove - 1;

                // -- PLACE MOVE ON BOARD IF VALID -- PLAYER O
                ValidMove = isValidMove(playerRow, playerCol);
                if (ValidMove) {
                    board[playerRow][playerCol] = "O";
                    moveCount += 1; // add to move counter
                }
                // -- IF MOVE IS INVALID - RUN THROUGH WHILE LOOP
                else {
                    while (!ValidMove) {
                        System.out.println("Move invalid. Try again.");
                        rowMove = SafeInput.getRangedInt(in, "O. Enter your row position [1-3]", 1, 3);
                        playerRow = rowMove - 1;
                        colMove = SafeInput.getRangedInt(in, "O. Enter your column position [1-3]", 1, 3);
                        playerCol = colMove - 1;
                        ValidMove = isValidMove(playerRow, playerCol);
                    }
                    board[playerRow][playerCol] = "O"; // add move once loop ends
                    moveCount += 1; // add to move counter
                }


                // -- CHECK FOR WIN WHEN FIVE OR MORE MOVES HAVE OCCURRED -- WIN FOR O
                if (moveCount == 9) {
                    display();
                    System.out.println("Board full. Tie!");
                    gameTie = true;
                }
                else if (moveCount>=5){
                    gameTie = isTie();
                    gameWinO = isWin("O");
                    gameWinX = isWin("X");
                    if (gameWinO){
                        display();
                        System.out.println("Player O won!");
                    }
                    if (gameWinX){
                        display();
                        System.out.println("Player X won!");
                    }
                    if (gameTie){
                        display();
                        System.out.println("There was a tie.");
                    }
                }



            } while (!gameWinX && !gameWinO && !gameTie);

            in.nextLine(); // clear scanner

            // ASK TO CONTINUE
            System.out.print("Would you like to continue? [Y/N]: ");
            if (in.hasNextLine()){
                cont=in.nextLine();
            }
            if(cont.equalsIgnoreCase("Y")){
                System.out.println("Players, switch places. X, become O. O, become X. ");
            }
        } while(cont.equalsIgnoreCase("Y"));

    }


    // ----------------- METHODS --------------------
    private static void clearBoard(){ // empties board / wipes clean
        for(int row=0; row<ROWS; row++){
            for(int col=0; col<COLS; col++){
                board[row][col]=" ";
            }
        }
    }
    private static void display(){ // display the board
        System.out.println(board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.out.println("------------");
        System.out.println(board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.out.println("-----------");
        System.out.println(board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
    }

    private static boolean isValidMove(int row, int col){ // check if space is already filled - if not, move is valid
        boolean result=false;
        if (board[row][col].equals(" ")){
            result=true;
        }
        return result;
    }

    private static boolean isWin(String player){
        if(isColWin(player) || isRowWin(player) || isDiagonalWin(player)) {
            return true;
        }
        return false;
    }

    private static boolean isColWin(String player){
        for(int col = 0; col<COLS; col++){
            if(board[0][col].equals(player) && board[1][col].equals(player) && board[2][col].equals(player)){
                return true;
            }
        }
        return false;
    }

    private static boolean isRowWin(String player) {
        for(int row=0; row<ROWS; row++){
            if(board[row][0].equals(player) && board[row][1].equals(player) && board[row][2].equals(player)){
                return true;
            }
        }
        return false;
    }

    private static boolean isDiagonalWin(String player){
        if(board[0][0].equals(player) && board[1][1].equals(player) && board[2][2].equals(player)){
            return true;
        }
        else if(board[2][0].equals(player) && board[1][1].equals(player) && board[0][2].equals(player)){
            return true;
        }
        return false;
    }
    private static boolean isTie(){
        boolean result=false;
        int canVector = 0; // cancel win vector
        // DIAGONAL -- If there is one X in [0,0] , [1,1] , [2,2] and an O in one of the other locations, win vector cancelled
        // TWO WIN VECTORS
        if ((board[0][0].equals("X") || board[1][1].equals("X") || board[2][2].equals("X")) && (board[0][0].equals("O") || board[1][1].equals("O") || board[2][2].equals("O"))){
            canVector+=1;
        }
        if((board[2][0].equals("X") || board[1][1].equals("X") || board[0][2].equals("X")) && (board[2][0].equals("O") || board[1][1].equals("O") || board[0][2].equals("O"))){
            canVector+=1;
        }
        // ROW
        // THREE WIN VECTORS (Can loop through each row instead of hard coding)
        for(int row=0; row<ROWS; row++) {
            if ((board[row][0].equals("X") || board[row][1].equals("X") || board[row][2].equals("X")) && (board[row][0].equals("O") || board[row][1].equals("O") || board[row][2].equals("O"))){
                canVector += 1;
            }
        }
        // COLUMN
        // THREE WIN VECTORS (Can loop through each row instead of hard coding)
        for (int col = 0; col < COLS; col++) {
            if ( (board[col][0].equals("X") || board[col][1].equals("X") || board[col][2].equals("X")) && (board[col][0].equals("O") || board[col][1].equals("O") || board[col][2].equals("O"))) {
                canVector += 1;
            }
        }
        // IF ALL WIN VECTORS CANCELLED
        if(canVector == 8){
            result=true;
        }

        return result;
    }
}
