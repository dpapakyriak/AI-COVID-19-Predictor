import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 
 */

/**
 * @author dimitrispapakyriakopoylos
 *
 */
public class AiPanel extends JPanel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	   public void paintComponent(Graphics g) {
	      super.paintComponent(g);
	    
	      g.setColor(Color.WHITE);
	      g.fillRect(0, 0, 1000, 1000);
	      g.setColor(Color.BLACK);
	      g.fillRect(100, 100, 2, 600);
	      g.fillRect(100, 700, 800, 2);
	      int i;
	      int y1, y2;
	      for (i = 0; i < Results.aiPredictions.length - 1; i++) {
	    	  // For prediction
	    	  g.setColor(new Color(23,17,95));
	    	  y1 = (int) ( 700 - 500 * Results.getAiPredictions(i)/ Results.max);
	    	  y2 = (int) ( 700 - 500 * Results.getAiPredictions(i + 1)/ Results.max);
	    	  g.drawLine(100 + 2 *i, y1, 100 + 2*i +2, y2);
	    	  // For target
	    	  g.setColor(new Color(229,108,53));
	    	  y1 = (int) ( 700 - 500 * Results.getTargetData(i,0)/ Results.max);
	    	  y2 = (int) ( 700 - 500 * Results.getTargetData(i + 1,0)/ Results.max);
	    	  g.drawLine(100 + 2 *i, y1, 100 + 2*i +2, y2);
	    	  
	      }
	      
	      
	      
	   }
}

