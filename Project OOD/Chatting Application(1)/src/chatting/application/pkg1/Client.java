package chatting.application.pkg1;

import java.util.*;
import javax.swing.border.*;//EmptyBorder
import javax.swing.*;//libarey funtion ekhane JFrame ase.
import java.awt.*;//background color jonne .
import java.awt.event.*;//ActionListener
import java.text.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener {
//Frame bananor jonne Jframe class use hobe

    JTextField text;//global var
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();//VerticalBox() eta ekta niche r ekta line built kore
    static JFrame f = new JFrame();
    static DataOutputStream dout;

    /*jokhon class call hobe
-->main() method jabe
-->main() method er object(new Client()) call hobe
-->then constraction(Client())sob kaj hobe*/
    Client() { //object bananor pore conustration call hoi

        f.setLayout(null);
        JPanel p1 = new JPanel();
        // p1.setBackground(new Color(195, 69, 240));
        p1.setBounds(0, 0, 450, 60);//frame bitorar panel setup
        p1.setLayout(null);
        f.add(p1);
///----------------------------icons-----------------------------------------
        ImageIcon img1 = new ImageIcon(ClassLoader.getSystemResource("icons/bclose.png"));//png use hoise
        Image img11 = img1.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon img111 = new ImageIcon(img11);
        JLabel close = new JLabel(img111);
        close.setBounds(390, 20, 25, 25);//<-- position
        p1.add(close);
        //event--------------------------------------------      
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                //or setVisible(false); but program still running.....
                System.exit(0);
            }
        });

        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons/faysal.png"));
        Image i22 = i2.getImage().getScaledInstance(90, 50, Image.SCALE_DEFAULT);
        ImageIcon i222 = new ImageIcon(i22);
        JLabel profile = new JLabel(i222);
        profile.setBounds(45, 10, 50, 50);
        p1.add(profile);

        //----------------------------end icons----------------------------------
        JLabel name = new JLabel("Faysal Mahmud");
        name.setBounds(110, 25, 200, 18);
        name.setForeground(Color.black);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);
        //---------------------------end profile----------------------------------
//a1 bitorar panel..........
        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5, 655, 310, 40);
        a1.setBackground(Color.white);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        f.add(text);
//---------------------------send button------------------------------------------
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(Color.black);
        send.setForeground(Color.white);
        send.addActionListener(this);//action this active hobe
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 13));
        f.add(send);
//---------------------------end send button-------------------------------------
//---------------------------frame ----------------------------------------
        f.setSize(450, 700);//default invisibal thakbe. seter jonne setVisible() use korbo.

        f.setLocation(700, 50);//frame kothay thake oita thik kore dibe
        f.setUndecorated(true);
        f.getContentPane().setBackground(new Color(220, 220, 220));

        f.setVisible(true);//setVisible last rakha valo karon sob kaj process pore eta run hobe.
    }
//-----------------------------end frame------------------------------------------
    public void actionPerformed(ActionEvent ae) {

        try {
            //actionPerformed(ActionEvent ae) abestraction method  
            String out = text.getText();//je value hobe ta String
            System.out.println(out);//see output my consule

//        JLabel output = new JLabel(out);//output ke object baniya string value nise
            JPanel p2 = formatLabel(out);//p2 name object 

            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));//line niche space . doi maje space

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);//send server

            text.setText("");//send korar pore cole jabe

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


public static JPanel formatLabel(String out) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 120px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(250, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
        
        panel.add(output);

        Calendar cal = Calendar.getInstance();//import java.util.*;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {//main method
        new Client();//class er object
        try {
            Socket s = new Socket("127.0.0.1", 6002);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true) {
                a1.setLayout(new BorderLayout());//dekhate hobe
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);

                f.validate();
            }
        } catch (Exception ae) {
            ae.printStackTrace();
        }
    }

}
