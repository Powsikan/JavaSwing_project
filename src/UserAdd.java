import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

public class UserAdd extends JFrame implements KeyListener {

    JLabel l1, l2, l3;
    JTextField t1;
    JPasswordField p1, p2;

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    String username, pword, conpword;

    public UserAdd() {
        super("User Addition");
        setSize(332, 300);
        setResizable(false);
        setLayout(null);
        getContentPane().setBackground(Color.cyan);

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize = getSize();

        this.setBounds((screensize.width - windowsize.width) / 2, (screensize.height - windowsize.height) / 2, windowsize.width, windowsize.height);

        l1 = new JLabel("User Name");
        add(l1);
        l1.setBounds(60, 80, 100, 25);

        l2 = new JLabel("Password");
        add(l2);
        l2.setBounds(60, 140, 100, 25);

        l3 = new JLabel("Re-Type password");
        add(l3);
        l3.setBounds(60, 200, 100, 25);

        t1 = new JTextField();
        add(t1);
        t1.setBounds(180, 80, 100, 25);
        t1.addKeyListener(this);
        p1 = new JPasswordField();
        add(p1);
        p1.setEditable(false);
        p1.setBounds(180, 140, 100, 25);
        p1.addKeyListener(this);

        p2 = new JPasswordField();
        add(p2);
        p2.setEditable(false);
        p2.setBounds(180, 200, 100, 25);
        p2.addKeyListener(this);


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
        } else if ((code == 10) && (e.getSource() == p2)) {
            onConPassVal();
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
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null, "User name already exist");
                        t1.setText("");
                    } else {
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
        pword = p1.getText();
        if (pword.equals(""))
            JOptionPane.showMessageDialog(null, "Password is not empty");
        else {
            pword = pword.trim();
            if ((pword.length() < 10) || (pword.length() > 20)) {
                JOptionPane.showMessageDialog(null, "Password should be in between 10 and 20");
                p1.setText("");
            } else {
                p2.setEditable(true);
                p2.requestFocus();
                p1.setEditable(false);
            }
        }
    }

    private void onConPassVal() {
        conpword = p2.getText();
        if (conpword.equals(""))
            JOptionPane.showMessageDialog(null, "Please confirm the password");
        else {
            conpword = conpword.trim();
            if ((conpword.length() < 10) || (conpword.length() > 20)) {
                JOptionPane.showMessageDialog(null, "password should be in 10 to 20");
                p2.setText("");
            } else {
                if (!(pword.equals(conpword))) {
                    JOptionPane.showMessageDialog(null, "Password not match");
                    p2.setText("");
                } else {
                    onRecordAdd();
                }
            }
        }
    }

    private void onRecordAdd() {
        int confirm = JOptionPane.showConfirmDialog(null, "Do you want to add this record", "Addition message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_NO_OPTION) {
            try {
                ps = con.prepareStatement("insert into userlist values(?,?)");
                ps.setString(1, username);
                ps.setString(2, pword);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "record added");

                onUserConfirm();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            onUserConfirm();

        }
    }

    private void onUserConfirm() {
        int confirm = JOptionPane.showConfirmDialog(null, "Do you want to continue", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == JOptionPane.YES_NO_OPTION) {
            onClearAndStart();
        } else {
            System.exit(0);
        }
    }

    private void onClearAndStart() {
        t1.setEditable(true);
        t1.setText("");
        p1.setText("");
        p2.setText("");
        t1.requestFocus();
        p1.setEditable(false);
        p2.setEditable(false);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        UserAdd uadd = new UserAdd();
        uadd.setVisible(true);

    }
}
