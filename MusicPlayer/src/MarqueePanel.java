import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MarqueePanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int Rate = 12;
	final Timer t = new Timer(1000/Rate,(ActionListener)this);
	JLabel l = new JLabel();
	String s;
	int n;
	int index;
	
		public MarqueePanel(String s , int n) {
		
		if(s == null || n < 1) {
			s=" ";
		}
		
		StringBuilder sb = new StringBuilder(n);
		for(int i =0 ; i<n ; i++) {
			sb.append(' '); 
		}
		this.s = sb+s+sb;
		this.n = n;
		l.setFont(loadFont("CHILLER.ttf", 30 ,Font.TYPE1_FONT));
		l.setForeground(new Color(126, 247, 140));
		this.add(l);
	}
	
	public void start() {
		t.start();
	}
	public void stop() {
		t.stop();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		index++;
		if(index> s.length() -n ) {
			index = 0 ;
		}
		l.setText(s.substring(index,index+n));
	}
	
	private static Font loadFont(String fontName, float size, int style) {

        InputStream openStream = MarqueePanel.class
                .getResourceAsStream("/font/"
                        + fontName);
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, openStream);
            Font finalFont = font.deriveFont((float) size).deriveFont(style);
            System.out.println("Loading font " + fontName + " " + finalFont);
            return finalFont;
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (openStream != null) {
                try {
                    openStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
		
}
