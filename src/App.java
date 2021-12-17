import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class App extends JPanel {

    public BufferedImage whiteking;
    public BufferedImage whitequeen;
    public BufferedImage whiterook;
    public BufferedImage whitebishop;
    public BufferedImage whiteknight;
    public BufferedImage whitepawn;
    public BufferedImage blackking;
    public BufferedImage blackqueen;
    public BufferedImage blackrook;
    public BufferedImage blackbishop;
    public BufferedImage blackknight;
    public BufferedImage blackpawn;

    //offsets cuz i couldnt find a better way to do this lol
    int pawnoffset[] = {18,5};
    int rookoffset[] = {5,5};
    int kingoffset = 7;

    //keeps track of all the pieces on the board
    /*
    syntax:
    first character = piece color (white or black)
    second character = type of piece (k=king, q=queen, r=rook, b=bishop, n = knight, p=pawn)
    "ee" = empty
    */
    static String[][] board = new String[8][8];
    static boolean boardComplete = false;
    static String selectedPiece = "";
    static boolean pieceSelected = false;
    static int y = 0;
    static int x = 0;
    static int selectedPieceX = 0;
    static int selectedPieceY = 0;

    public App() {
        try {                
            whiteking = ImageIO.read(new File("whiteking.jpeg"));
            whitequeen = ImageIO.read(new File("whitequeen.jpeg"));
            whiterook = ImageIO.read(new File("whiterook.jpeg"));
            whitebishop = ImageIO.read(new File("whitebishop.jpeg"));
            whiteknight = ImageIO.read(new File("whiteknight.jpeg"));
            whitepawn = ImageIO.read(new File("whitepawn.jpeg"));
            blackking = ImageIO.read(new File("blackking.png"));
            blackqueen = ImageIO.read(new File("blackqueen.png"));
            blackrook = ImageIO.read(new File("blackrook.png"));
            blackbishop = ImageIO.read(new File("blackbishop.png"));
            blackknight = ImageIO.read(new File("blackknight.png"));
            blackpawn = ImageIO.read(new File("blackpawn.png"));
        } catch (IOException ex) {
                // handle exception...
        }
    }

    public static void main(String[] args) throws Exception {
        
        var panel = new App();
        panel.setBackground(Color.BLACK.darker());
        var frame = new JFrame("Chess");clickDetection cd = new clickDetection(){
            public void mouseClicked(MouseEvent e){}
            public void mouseReleased(MouseEvent e){}
            public void mousePressed(MouseEvent e){
                y = e.getX();
                //for some reason it's offset by 30 idk why
                x = e.getY()-30;
                if (board[y/100-1][x/100-1] != "ee" && !pieceSelected) {
                    selectedPiece = board[y/100-1][x/100-1];
                    selectedPieceX = x;
                    selectedPieceY = y;
                }
                y = y/100-1;
                x = x/100-1;
                boardComplete = true;
                frame.repaint();
            }
            public void mouseExited(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
        };
        frame.setSize(1025, 1025);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.addMouseListener(cd);
        frame.setVisible(true);

    }

    private static class clickDetection implements MouseListener {
        
        @Override
        public void mouseClicked(MouseEvent e) {
         }
    
         @Override
         public void mouseEntered(MouseEvent e) { }
    
         @Override
         public void mouseExited(MouseEvent e) { }
    
         @Override
         public void mousePressed(MouseEvent e) { }
    
         @Override
         public void mouseReleased(MouseEvent e) { }
    
    }

    //setup the board
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!boardComplete) {
            //draw the board
            Color colorOne = Color.WHITE;
            Color colorTwo = Color.decode("#4B77BE");
            Boolean colorBool;
            for (int i=0; i<8; i++) {
                if (i%2 == 0) {
                    colorBool = true;
                } else {
                    colorBool = false;
                }
                for (int j=0; j<8; j++) {
                    board[i][j] = "ee";
                    if (colorBool) {
                        g.setColor(colorOne);
                        colorBool = false;
                    } else {
                        g.setColor(colorTwo);
                        colorBool = true;
                    }
                    g.fillRect(100+i*100, 100+j*100, 100, 100);
                    
                }
            }

            //pawns cuz they are easiest
            //black pawns
            for (int i=0; i<8; i++) {
                board[i][1] = "bp";
                g.drawImage(blackpawn, 100+i*100+pawnoffset[0], 200+pawnoffset[1], this);

            }

            //white pawns
            for (int i=0; i<8; i++) {
                board[i][6] = "wp";
                g.drawImage(whitepawn, 100+i*100+pawnoffset[0], 700+pawnoffset[1], this);
            }

            //rooks
            g.drawImage(blackrook, 100+rookoffset[0], 100+rookoffset[1], this);
            board[0][0] = "br";
            g.drawImage(blackrook, 800+rookoffset[0], 100+rookoffset[1], this);
            board[7][0] = "br";
            g.drawImage(whiterook, 100+rookoffset[0], 800+rookoffset[1], this);
            board[0][7] = "wr";
            g.drawImage(whiterook, 800+rookoffset[0], 800+rookoffset[1], this);
            board[7][7] = "wr";

            //queens
            g.drawImage(whitequeen, 400, 800, this);
            board[3][7] = "wq";
            g.drawImage(blackqueen, 400, 100, this);
            board[3][0] = "bq";

            //bishops
            g.drawImage(blackbishop, 300, 100, this);
            board[2][0] = "bb";
            g.drawImage(blackbishop, 600, 100, this);
            board[5][0] = "bb";
            g.drawImage(whitebishop, 300, 800, this);
            board[2][7] = "wb";
            g.drawImage(whitebishop, 600, 800, this);
            board[5][7] = "wb";

            //knights
            g.drawImage(blackknight, 200, 100, this);
            board[1][0] = "bn";
            g.drawImage(blackknight, 700, 100, this);
            board[6][0] = "bn";
            g.drawImage(whiteknight, 200, 800, this);
            board[1][7] = "wn";
            g.drawImage(whiteknight, 700, 800, this);
            board[6][7] = "wn";

            //kings
            g.drawImage(blackking, 500+kingoffset, 100, this);
            board[4][0] = "bk";
            g.drawImage(whiteking, 500+kingoffset, 800, this);
            board[4][7] = "wk";
        }

        if (boardComplete) {
            //pieceselected and making a move
            if (pieceSelected) {
                //draw the board
                Color colorOne = Color.WHITE;
                Color colorTwo = Color.decode("#4B77BE");
                Boolean colorBool;
                for (int i=0; i<8; i++) {
                    if (i%2 == 0) {
                        colorBool = true;
                    } else {
                        colorBool = false;
                    }
                    for (int j=0; j<8; j++) {
                        //board drawing
                        if (colorBool) {
                            g.setColor(colorOne);
                            colorBool = false;
                        } else {
                            g.setColor(colorTwo);
                            colorBool = true;
                        }
                        g.fillRect(100+i*100, 100+j*100, 100, 100);
                        //draw the move in (doesn't check if the move is legal yet)
                        if (j == x && i == y) {
                            //change the array so in the next step you draw the move in
                            board[selectedPieceY/100-1][selectedPieceX/100-1] = "ee";
                            if (selectedPiece != "ee") {
                                board[y][x] = selectedPiece;
                                selectedPiece = "";
                            }
                            
                        }
                    }
                }
                //pieces
                for (int i=0; i<8; i++) {
                    for (int j=0; j<8; j++) {
                        //piece drawing
                        String pieceOnSquare = board[j][i];
                        String pieceColor = pieceOnSquare.substring(0,1);
                        String pieceType = pieceOnSquare.substring(1,2);
                        if (pieceColor.equals("w")) {
                            if (pieceType.equals("k")) {
                                g.drawImage(whiteking, 100+100*j+kingoffset, 100+100*i, this);
                            }
                            if (pieceType.equals("q")) {
                                g.drawImage(whitequeen, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("b")) {
                                g.drawImage(whitebishop, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("n")) {
                                g.drawImage(whiteknight, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("r")) {
                                g.drawImage(whiterook, 100+100*j+rookoffset[0], 100+100*i+rookoffset[1], this);
                            }
                            if (pieceType.equals("p")) {
                                g.drawImage(whitepawn, 100+100*j+pawnoffset[0], 100+100*i+pawnoffset[1], this);
                            }
                        }
                        if (pieceColor.equals("b")) {
                            if (pieceType.equals("k")) {
                                g.drawImage(blackking, 100+100*j+kingoffset, 100+100*i, this);
                            }
                            if (pieceType.equals("q")) {
                                g.drawImage(blackqueen, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("b")) {
                                g.drawImage(blackbishop, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("n")) {
                                g.drawImage(blackknight, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("r")) {
                                g.drawImage(blackrook, 100+100*j+rookoffset[0], 100+100*i+rookoffset[1], this);
                            }
                            if (pieceType.equals("p")) {
                                g.drawImage(blackpawn, 100+100*j+pawnoffset[0], 100+100*i+pawnoffset[1], this);
                            }
                        }
                    }
                }
            }

            //no piece selected
            if (!pieceSelected) {
                //draw the board but with the selected square highlighted
                Color colorOne = Color.WHITE;
                Color colorTwo = Color.decode("#4B77BE");
                Boolean colorBool;
                for (int i=0; i<8; i++) {
                    if (i%2 == 0) {
                        colorBool = true;
                    } else {
                        colorBool = false;
                    }
                    for (int j=0; j<8; j++) {
                        //board drawing
                        if (colorBool) {
                            g.setColor(colorOne);
                            colorBool = false;
                        } else {
                            g.setColor(colorTwo);
                            colorBool = true;
                        }
                        //yellow
                        if (y==i && x==j) {
                            if (board[y][x] != "ee") {
                                g.setColor(Color.YELLOW);
                            } else if (board[y][x] == "ee") {
                                selectedPiece = "ee";
                            }
                        }
                        g.fillRect(100+i*100, 100+j*100, 100, 100);
                        
                        
                    }
                }
                //pieces
                for (int i=0; i<8; i++) {
                    for (int j=0; j<8; j++) {
                        //piece drawing
                        String pieceOnSquare = board[j][i];
                        String pieceColor = pieceOnSquare.substring(0,1);
                        String pieceType = pieceOnSquare.substring(1,2);
                        if (pieceColor.equals("w")) {
                            if (pieceType.equals("k")) {
                                g.drawImage(whiteking, 100+100*j+kingoffset, 100+100*i, this);
                            }
                            if (pieceType.equals("q")) {
                                g.drawImage(whitequeen, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("b")) {
                                g.drawImage(whitebishop, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("n")) {
                                g.drawImage(whiteknight, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("r")) {
                                g.drawImage(whiterook, 100+100*j+rookoffset[0], 100+100*i+rookoffset[1], this);
                            }
                            if (pieceType.equals("p")) {
                                g.drawImage(whitepawn, 100+100*j+pawnoffset[0], 100+100*i+pawnoffset[1], this);
                            }
                        }
                        if (pieceColor.equals("b")) {
                            if (pieceType.equals("k")) {
                                g.drawImage(blackking, 100+100*j+kingoffset, 100+100*i, this);
                            }
                            if (pieceType.equals("q")) {
                                g.drawImage(blackqueen, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("b")) {
                                g.drawImage(blackbishop, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("n")) {
                                g.drawImage(blackknight, 100+100*j, 100+100*i, this);
                            }
                            if (pieceType.equals("r")) {
                                g.drawImage(blackrook, 100+100*j+rookoffset[0], 100+100*i+rookoffset[1], this);
                            }
                            if (pieceType.equals("p")) {
                                g.drawImage(blackpawn, 100+100*j+pawnoffset[0], 100+100*i+pawnoffset[1], this);
                            }
                        }
                    }
                }
            }

            if (pieceSelected) {
                pieceSelected = false;
            } else {
                pieceSelected = true;
            }
        }
    }
}

