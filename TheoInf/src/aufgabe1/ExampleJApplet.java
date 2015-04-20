package aufgabe1;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class ExampleJApplet extends JApplet implements ActionListener {

  JButton btnHello;
  JLabel  lblMessage = new JLabel();
  
  public ExampleJApplet() {
    setLayout( new FlowLayout() );
    
    btnHello = new JButton("Hello");
    lblMessage = new JLabel("Nichts angeklickt");
  }
  
  public void init() {
    // Aktion zu Hello-Button hinzufuegen
    btnHello.addActionListener( this );
    
    // Elemente auf JApplet einfuegen
    add( lblMessage );
    add( btnHello );
  }
  
  public void start() {
  }
  
  public void stop() {
    // stoppe Ausf�hrung
  }
  public void destroy() {
    // initialisierte Ressourcen freigeben
  }

  public void actionPerformed(ActionEvent arg0) {
    lblMessage.setText( "JButton wurde gedrueckt!" );
  }
  
  public static void main(String[] args) {
	ExampleJApplet app = new ExampleJApplet();
	app.start();
}
}