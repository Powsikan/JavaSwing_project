import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

public class Login extends JFrame implements KeyListener {

    JLabel l1, l2, l3;
    JTextField t1;
    JPasswordField p1, p2;

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    String username, pword, conpword;

    public Login() {
        super("User Login");
        setSize(332, 300);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.cyan);

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize = getSize();

        this.setBounds((screensize.width - windowsize.width) / 2, (screensize.height - windowsize.height) / 2, windowsize.width, windowsize.height);

        l1 = new JLabel("User Name");
        add(l1);
        l1.setBounds(60, 100, 100, 30);

        l2 = new JLabel("Password");
        add(l2);
        l2.setBounds(60, 175, 100, 30);


        t1 = new JTextField();
        add(t1);
        t1.setBounds(180, 100, 100, 30);
        t1.addKeyListener(this);

        p1 = new JPasswordField();
        add(p1);
        p1.setEditable(false);
        p1.setBounds(180, 175, 100, 30);
        p1.addKeyListener(this);


        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }


    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyChar();
        if ((code == 10) && (e.getSource() == t1)) {
            onUserNameVal();
        } else if ((code == 10) && (e.getSource() == p1)) {
            onPasswordVal();
        }
    }

    private void onUserNameVal() {
        username = t1.getText();
        if (username.equals(""))
            JOptionPane.showMessageDialog(null, "User Name not entered");
        else {
            username = username.trim();
            if ((username.length() < 10) || (username.length() > 20)) {
                JOptionPane.showMessageDialog(null, "User name shoul be in between 10 and 20");
                t1.setText("");
            } else {
                try {
                    st = con.createStatement();
                    rs = st.executeQuery("select * from userlist where username='" + username + "'");
                    if (!(rs.next())) {
                        JOptionPane.showMessageDialog(null, "User name not found");
                        t1.setText("");
                    } else {
                        pword = rs.getString(2);
                        p1.setEditable(true);
                        p1.requestFocus();
                        t1.setEditable(false);
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onPasswordVal() {
        String ppword;
        ppword = p1.getText();
        if (ppword.equals(""))
            JOptionPane.showMessageDialog(null, "Password is not empty");
        else {
            ppword = ppword.trim();
            if ((ppword.length() < 10) || (ppword.length() > 20)) {
                JOptionPane.showMessageDialog(null, "Password should be in between 10 and 20");
                p1.setText("");
            } else {
                if (!(ppword.equals(pword))) {
                    JOptionPane.showMessageDialog(null, "Invalid Password");
                    p1.setText("");
                } else {
                    //JOptionPane.showMessageDialog(null,"Success","Login",JOptionPane.INFORMATION_MESSAGE);
                    Mainwindow mw = new Mainwindow();
                    mw.setVisible(true);
                    this.setVisible(false);
                }

            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        Login uadd = new Login();
        uadd.setVisible(true);

    }
}
