import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.codehaus.jackson.map.ObjectMapper;



public class Ppi extends JPanel{
	static final int MAX_TRKS = 1000;
	static final int MAX_HT = 100000;
	static final long MAX_RANGE = 500000;
	static final long MIN_RANGE = 100;
	static final long MAX_VEL = 10000;
	static final long MIN_VEL = 0;
	static final long LONG_RANGE = MAX_RANGE;
	static final long MEDIUM_RANGE = MAX_RANGE/2;
	static final long SHORT_RANGE = MAX_RANGE/4;
	static final int HISTORY = 6;
	static Tracks[] tracks = new Tracks[HISTORY];
	static int newest = -1;
	static long scale = LONG_RANGE;
	static MyThread t;
	private Ppi() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));

	}

	JLabel minxl= new JLabel("Render interval (ms)");
	JTextField minx= new JTextField("1000",10);

	JButton longRange = new JButton("Long");
	JButton mediumRange = new JButton("Medium");
	JButton shortRange = new JButton("Short");
	JFrame jfrm = new JFrame("Plan Position Indicator");
	static Ppi pe;

	class Point{

		double x, y;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = getHeight();
		int width = getWidth();
		int current = newest;
		int shade = 0;
		g.setColor(Color.BLACK);
		g.drawArc(0, 0, width, height, 0, -360);
		g.drawString(""+(scale/1000)+"Km", width/2 -40, 0 +20);

		do {
			int shadeOfGray = (int)(((int)(255.0 * shade /HISTORY) << 16)+
					((int)(255.0 * shade /HISTORY) << 8)+
					(255.0 * shade /HISTORY));
			g.setColor(Color.decode(""+shadeOfGray));

			if (tracks[current] != null)
			for (Track track : tracks[current].getTracks()) {
				int x1, y1;
				int x2, y2;
				x1 = (int)(width/2.0+width*track.getEmetres()*1.0/(scale*2.0))-5;
				x2 = x1+10;
				y1 = (int)(height/2.0-height*track.getNmetres()*1.0/(scale*2.0));
				y2 = y1;
				g.drawLine(x1,y1,x2,y2);
				x1 = (int)(width/2.0+width*track.getEmetres()*1.0/(scale*2.0));
				x2 = x1;
				y1 = (int)(height/2.0-height*track.getNmetres()*1.0/(scale*2.0))-5;
				y2 = y1+10;
				g.drawLine(x1,y1,x2,y2);
			}			
			current--;
			if (current < 0) {
				current = HISTORY-1;
			}
			shade++; 
		} while (current != newest && tracks[current] != null);
		
	}



	// http://localhost:8080/RESTfulExample/json/product/get
	private Tracks getTracks() {
		Tracks tracks = null;

		try {

			URL url = new URL("http://localhost:8080/RADAR/Tracks");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			ObjectMapper mapper = new ObjectMapper();
			StringBuffer jsonInString = new StringBuffer();

			while ((output = br.readLine()) != null) {
				jsonInString.append(output+"\r\r\n");
			}
			//JSON from String to Object
			tracks = mapper.readValue(jsonInString.toString(), Tracks.class);

			conn.disconnect();
			return tracks;

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return tracks;

	}

	class MyThread extends Thread{
		@Override
		public void run() {
			do {
				newest++;
				if (newest >= HISTORY) {
					newest = 0;
				}
				tracks[newest]=pe.getTracks();
				
				pe.repaint();
				try {
					t.sleep(Long.parseLong(minx.getText()));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (true);
		}
	}
	class PaintDemo{

		PaintDemo(){
			jfrm.setSize(1000, 1000);
			jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


			jfrm.setLayout(new BorderLayout());

			JPanel temp = new JPanel();
			temp.add(minxl);		
			temp.add(minx);
			temp.add(longRange);
			longRange.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					scale = LONG_RANGE;
				}
				
			});
			temp.add(mediumRange);
			mediumRange.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					scale = MEDIUM_RANGE;
				}
				
			});
			temp.add(shortRange);
			shortRange.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					scale = SHORT_RANGE;
				}
				
			});
			jfrm.add(temp, BorderLayout.NORTH);
			jfrm.add(pe, BorderLayout.CENTER);


			jfrm.setVisible(true);
		}

	}
	public static void main(String args[]) throws IOException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (args.length != 0) {
					System.out.println("usage: java Ppi");
					System.exit(1);
				}
				pe = new Ppi();
				pe.new PaintDemo();
				t = pe.new MyThread();
				t.start();
			}
		});
	}

}

