package zad1;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.*;

public class GUI 
	extends JFrame {
	
	private final TravelData travelData;
	private TranslatedView translatedView;
	private final String[] languages;
	private GridBagLayout gridBagLayout;
	
	public GUI(TravelData travelData, String[] languages) {
		
		this.languages = languages;
		this.travelData = travelData;
		
		SwingUtilities.invokeLater(()->{
			
			this.gridBagLayout = new GridBagLayout();
			
			this.setLayout(gridBagLayout);
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(750, 1000);
			this.translatedView = new TranslatedView(this);
			setContentPane(translatedView);
			
			
			setVisible(true);
			
		});
	}
	
	private void setLanguageView(String command) {
		List<String> travels = travelData.getOffersDescriptionsList(command, "yyyy-MM-dd");

		String[] columnNames = {"Country", "Start date", "End date", "Destination", "Price", "Currency"};	
		
		ArrayList<Travel> dataTravel = travelData.getTravels();
		String[][] data = new String[travels.size()][6];
		
		Locale locale = new Locale(command);
		
		
		for(int a = 0; a < dataTravel.size(); a++) {
			
			NumberFormat nf = NumberFormat.getInstance(locale);
			String price = dataTravel.get(a).getPrice() + "";
			
			data[a][0] = travelData.translateCountry(dataTravel.get(a).getCountry(), dataTravel.get(a).getLocale(), locale);
			data[a][1] = dataTravel.get(a).getStartDate();
			data[a][2] = dataTravel.get(a).getEndDate();
			data[a][3] = travelData.translate(dataTravel.get(a).getDestination(), dataTravel.get(a).getLocale(), locale);
			try {
				data[a][4] = nf.parse(price).toString();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			data[a][5] = dataTravel.get(a).getCurrency();
		}
		
		JTable jt = new JTable(data, columnNames);
		JScrollPane jsp = new JScrollPane(jt);
		GridBagConstraints gbc = new GridBagConstraints();
		
		this.gridBagLayout.setConstraints(jsp, gbc);
		gbc.fill = GridBagConstraints.NONE;
		jt.setEnabled(false);

		add(jsp, gbc);
		
		revalidate();
		repaint();
	}

	
	private static class TranslatedView
		extends JPanel 
		implements ActionListener {
		
		private final GUI gui;
		List<JButton> buttons;
		
		private TranslatedView(GUI gui) {
			this.gui = gui;
			this.buttons = new ArrayList<>();
			
			for(int a = 0; a < gui.languages.length; a++) {
				JButton button = new JButton(gui.languages[a]);
				buttons.add(button);
				button.setActionCommand(gui.languages[a]);
				button.addActionListener(this);
				add(buttons.get(a));
			}
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			gui.setLanguageView(e.getActionCommand());
			
			for(JButton button : buttons) {
				button.setEnabled(false);
				remove(button);
			}
		}
	}
}
