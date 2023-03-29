package zad1;

import java.util.Locale;

public class Travel {
		private final Locale locale;
		private final String country;
		private final String startDate;
		private final String endDate;
		private final String destination;
		private final double price;
		private final String currency;
		
		public Travel(Locale locale, String country, String startDate, String endDate, String destination, double price, String currency) {
			this.locale = locale;
			this.country = country;
			this.startDate = startDate;
			this.endDate = endDate;
			this.destination = destination;
			this.price = price;
			this.currency = currency;
		}
		
		public Locale getLocale() {
			return locale;
		}
		
		public String getCountry() {
			return country;
		}
		
		public String getStartDate() {
			return startDate;
		}
		
		public String getEndDate() {
			return endDate;
		}
		
		public String getDestination() {
			return destination;
		}
		
		public double getPrice() {
			return price;
		}
		
		public String getCurrency() {
			return currency;
		}
	}