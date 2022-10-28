package TestButt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TestButton extends Component implements ActionListener {

    private JButton[][] jButtons;// Массив кнопок
    private JFrame jFrame;// Фрейм
    // Массив значений
    private String[] numbers = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", ""
    };
    // Массив измененных значений
    private String[] changedNumbers;
    // Координаты пустой кнопки
    private int save1, save2;

    // Проверка решаемости
    private boolean isSolvable() {

        int countInversions = 0;
        for (int i = 0; i < changedNumbers.length - 1; i++) {
            for (int j = 0; j < i; j++) {
                if (Integer.valueOf(changedNumbers[j]) > Integer.valueOf(changedNumbers[i])) countInversions++;
            }
        }
        System.out.println(countInversions);
        return countInversions % 2 == 0;
    }

    // Конструктор
    TestButton() {

        FrameCreator();
        Menu();
        Button();
        ButtonSetter();
        jFrame.setResizable( false );
        jFrame.setVisible( true );
    }

    // Метод для занесения Listener'ов для кнопок
    private void ButtonSetter(){
        for ( int i = 0; i < jButtons.length; i++ ) {
            for ( int j = 0; j < jButtons[ i ].length; j++ ) {
                jFrame.add(jButtons[ i ][ j ]);
                int finalI = i;
                int finalJ = j;
                jButtons[ i ][ j ].addActionListener( new ActionListener() {
                    @Override
                    public void actionPerformed( ActionEvent actionEvent ) {
                        if ( ( ( finalI == save1 + 1 || finalI == save1 - 1 ) && finalJ == save2 ) || ( ( finalJ == save2 + 1 || finalJ == save2 - 1 ) && finalI == save1 ) ) {
                            pressKey( finalI, finalJ );
                        }
                    }
                });

                jButtons[ i ][ j ].addKeyListener( new KeyAdapter() {
                    @Override
                    public void keyPressed( KeyEvent e ) {
                        if( e.getKeyCode() == KeyEvent.VK_LEFT ) pressKey( save1, save2 + 1 );
                        else if( e.getKeyCode() == KeyEvent.VK_RIGHT ) pressKey( save1, save2 - 1 );
                        else if( e.getKeyCode() == KeyEvent.VK_DOWN ) pressKey( save1 - 1, save2 );
                        else if( e.getKeyCode() == KeyEvent.VK_UP ) pressKey( save1 + 1, save2 );
                    }
                });
                jFrame.add( jButtons[ i ][ j ] );
            }
        }
    }

    // Создание фрейма
    private void FrameCreator(){

        jFrame = new JFrame();
        jFrame.setSize( new Dimension(500, 500) );
        jFrame.setLayout( new GridLayout(4, 4) );
        jFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        jFrame.setTitle( "Пятнашки" );

    }

    // Метод для изменения значений для кнопок
    private void pressKey( int enter1, int enter2 ){

        try{
            jButtons[ save1 ][ save2 ].setVisible( true );
            jButtons[ save1 ][ save2 ].setText( jButtons[ enter1 ][ enter2 ].getText() );
            jButtons[ enter1 ][ enter2 ].setText( "" );
            jButtons[ save1 ][ save2 ].setBackground( new Color(162, 103, 248) );
            save1 = enter1;
            save2 = enter2;
            //jButtons[ save1 ][ save2 ].setBackground( new Color( 239, 239, 239 ) );
            jButtons[ save1 ][ save2 ].setVisible( false );
            if ( check() ) {
                int result = JOptionPane.showConfirmDialog(
                        jFrame,
                        "<html>You're winner<br>New game?</html>",
                        "Winner",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION){
                    jFrame.setVisible(false);
                    new TestButton();
                }
                else if (result == JOptionPane.NO_OPTION)
                    System.exit( 0 );
            }
        }
        catch( ArrayIndexOutOfBoundsException e ){}
    }

    // Создание меню
    public void Menu() {

        JMenuBar jMenuBar = new JMenuBar();
        JMenu File = new JMenu("File");
        File.setMnemonic( KeyEvent.VK_F );
        JMenu About = new JMenu("About");
        About.setMnemonic( KeyEvent.VK_A );

        JMenuItem Author = new JMenuItem("Author", KeyEvent.VK_A );
        JMenuItem NewGame = new JMenuItem("New Game", KeyEvent.VK_N );
        JMenuItem Exit = new JMenuItem("Exit", KeyEvent.VK_E );
        NewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        Exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
        Author.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        File.add(NewGame);
        File.addSeparator();
        File.add(Exit);
        About.add(Author);

        jMenuBar.add(File);
        jMenuBar.add(About);

        jFrame.setJMenuBar(jMenuBar);
        NewGame.addActionListener(this);
        Exit.addActionListener(this);
        Author.addActionListener( this );
    }

    // Создание кнопок
    public void Button() {

        int k = 0;
        String[] string = new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", ""
        };
        mix(string);
        changedNumbers = string;
        while( !isSolvable() ){
            mix(string);
            changedNumbers = string;
        }
        jButtons = new JButton[ 4 ][ 4 ];
        Font font = new Font( "Arial", Font.ITALIC, 50 );
        for ( int i = 0; i < jButtons.length; i++ ) {
            for ( int j = 0; j < jButtons.length; j++ ) {
                jButtons[ i ][ j ] = new JButton();
                jButtons[ i ][ j ].setForeground( new Color(255, 255, 255) );
                jButtons[ i ][ j ].setText( string[ k ] );
                jButtons[ i ][ j ].setFont( font );
                jButtons[ i ][ j ].setBackground( new Color(162, 103, 248) );
                k++;
            }
        }
        save1 = 3;
        save2 = 3;
        jButtons[ save1 ][ save2 ].setVisible( false );
    }

    // Метод для перемешивания значений
    void mix(String[] a) {

        int n = 15;
        while (n > 1) {
            int rnd = new Random().nextInt(n--);
            String temp = a[rnd];
            a[rnd] = a[n];
            a[n] = temp;
        }
    }

    // Метод для проверки решенности головоломки
    public boolean check() {

        int k = 0, h = 0;
        for ( int i = 0; i < 4; i++ ) {
            for ( int j = 0; j < 4; j++ ) {
                if (jButtons[ i ][ j ].getText() == numbers[ h ]) k++;
                h++;
            }
        }
        if ( k == 16 ) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        new TestButton();
    }

    // Действия для нажатия кнопок из меню
    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        String str = actionEvent.getActionCommand();
        if ( str.equals( "New Game" ) ) {
            int k = 0;
            for( int i = 0; i < 4; i++ ){
                for( int j = 0; j < 4; j++ ){
                    mix( changedNumbers );
                    while( !isSolvable() ) mix( changedNumbers );
                    jButtons[ i ][ j ].setText( changedNumbers[ k ] );
                    k++;
                }
            }
        } else if ( str.equals( "Exit" ) ) System.exit(0);
        else if ( str.equals( "Author" ) ) JOptionPane.showMessageDialog( jFrame,
        "<html>Лебедев Иван Р3170<br>2020</html>",
                "About author", JOptionPane.INFORMATION_MESSAGE );
    }
}