import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

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
    second character = type of piece (k=king, q=queen, r=rook, b=bishop, kn = knight, p=pawn)
    "ee" = empty
    */
    String[][] board = new String[8][8];

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
        var frame = new JFrame("oChess");
        frame.setSize(1025, 1025);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        board[1][0] = "bkn";
        g.drawImage(blackknight, 700, 100, this);
        board[6][0] = "bkn";
        g.drawImage(whiteknight, 200, 800, this);
        board[1][7] = "wkn";
        g.drawImage(whiteknight, 700, 800, this);
        board[6][7] = "wkn";

        //kings
        g.drawImage(blackking, 500+kingoffset, 100, this);
        board[4][0] = "bk";
        g.drawImage(whiteking, 500+kingoffset, 800, this);
        board[4][7] = "wk";


        for (int i=0; i<8; i++) {
            for (int j=0; j<8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
}

