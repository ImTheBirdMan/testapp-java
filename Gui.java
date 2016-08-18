
import java.io.*;
import java.net.*;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.*;
import java.net.SocketException;
import javax.swing.JFrame;
import javax.swing.JPanel; 
import javax.swing.JComboBox; 
import javax.swing.JButton; 
import javax.swing.JLabel; 
import javax.swing.JList; 
import java.awt.BorderLayout; 
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;



public class Gui{
    
   public Gui(){
      JFrame guiFrame = new JFrame();
      guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      guiFrame.setTitle("Placeholder");
      guiFrame.setSize(1920,1080);
      final JTextArea textArea = new JTextArea(100, 400);
      guiFrame.getContentPane().add(BorderLayout.CENTER, textArea);
      final JButton button = new JButton("Click it");
      //JLabel comboLbl = new JLabel("Fruits:");
       //JComboBox fruits = new JComboBox(fruitOptions);
      button.addActionListener(
         new ActionListener() {
         
            @Override
            public void actionPerformed(ActionEvent e) {
               textArea.append("Button was clicked\n");
            
            }
         });
   
      guiFrame.setVisible(true);
   
   }




}