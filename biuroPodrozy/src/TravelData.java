package zad1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class TravelData {

	private final ArrayList<Travel> travels;
	private Properties dictionary;
	private String dateFormat;

	public TravelData(File data) {
		this.travels = new ArrayList<>();
		File[] files = data.listFiles();

		assert files != null;
		for(File file : files){
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				
				String travel = br.readLine();
		
				while(travel != null) {
					Locale locale = Locale.forLanguageTag(travel.split("\t")[0].replace("_", "-"));
					String country = travel.split("\t")[1];
					String startDate = travel.split("\t")[2];
					String endDate = travel.split("\t")[3];
					String destination = travel.split("\t")[4];
					NumberFormat numberFormat = NumberFormat.getInstance(locale);
					double price = numberFormat.parse(travel.split("\t")[5]).doubleValue();
					String currency = travel.split("\t")[6];
					
					travels.add(new Travel(locale, country, startDate, endDate, destination, price, currency));
					travel = br.readLine();
				}
				
				br.close();
				
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		
		try {
			InputStream is = new FileInputStream("dictionary.properties");
			dictionary = new Properties();
			dictionary.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	List<String> getOffersDescriptionsList(String locale, String dateFormat) {
		List<String> offers = new ArrayList<>();
		
		Locale toTranslate = Locale.forLanguageTag(locale.replace("_", "-"));
		NumberFormat numberFormat = NumberFormat.getInstance(toTranslate);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		
		for (Travel t : travels) {
			String travel;
			
			try {
				
				travel = translateCountry(t.getCountry(), t.getLocale(), toTranslate) + " " +
				simpleDateFormat.format(simpleDateFormat.parse(t.getStartDate())) + " " + simpleDateFormat.format(simpleDateFormat.parse(t.getEndDate())) + " " + 
				translate(t.getDestination(), t.getLocale(), toTranslate) + " " + numberFormat.format(numberFormat.parse(t.getPrice()+"")) + " " + t.getCurrency();
				
				offers.add(travel);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return offers;		
	}
	
	public String translate(String word, Locale from, Locale to) {
		return dictionary.getProperty(from.getLanguage() + "-" + to.getLanguage() + "." + word, word);
	}
	
	public String translateCountry(String word, Locale from, Locale to) {
		
		for(Locale locale : Locale.getAvailableLocales())
			if(locale.getDisplayCountry(from).equals(word))
				return locale.getDisplayCountry(to);
				
		return "";
		
	}
	
	public ArrayList<Travel> getTravels(){
		return travels;
	}

}