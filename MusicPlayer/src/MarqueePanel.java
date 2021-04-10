import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		l.setFont(new Font("Chiller" , Font.TYPE1_FONT, 30));
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
		
}
