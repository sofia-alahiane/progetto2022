# progetto2022
Il nostro progetto realizza un'applicazione web che raccoglie e mostra all'utente i dati di temperatura reale,percepita, minima e massima di una città attraverso delle chiamate alla API offerta dalla OpenWeather (https://openweathermap.org/), la API utilizzata in particolare si trova nella classe "ClimateApiCall" ed è la seguente "https://api.openweathermap.org/data/2.5/weather?q=%s&lang=it&units=metric&appid=%s".
Principalmente l'applicazione sfrutta degli oggetti "CityModel" che fungono da "carta d'Identità" delle città che analizzeremo e degli oggetti "ClimateData" che invece raccoglieranno tutti i dati indicati poc'anzi oltre che al nominativo della città stessa.
Questa applicazione prevede numerosi metodi, tuttavia i principali sono i seguenti:
		
		Metodi del "services"
			1)"getClimate(List<CityModel> cities)"
				Questo metodo associa i valori dei dati indicati per la città richiesta ad un oggetto "ClimateData", per 				fare ciò sfrutteremo la chiamata all'API;
				
			2)"createStatistics(List<CityModel> cities, Date 	startDate, Date endDate)"
				Questo metodo prepara la statistica (va completato);
		
		Metodi del "controllers"
			1)"getClimateFor(String city)"
				Questo metodo restituisce all'utente le condizioni climatiche (nonchè le varie temperaure) della città 				richiesta, esso sfrutta il metodo "getClimateFor(String city)" del package "Services", un metodo che 				richiama il metodo "getClimate(List<CityModel> cities)" descritto precedentemente tra i metodi del 				"service";
			2)"getStatisticsFor(String cities, String startDate, String endDate)"
				Come per l'altro metodo del controllers indicato questo restituisce all'utente le stastiche della città 				richiesta. Questo metodo utilizza il metodo "getStatisticFor(String city, String startDate, String 				endDate)" del package "Services", questo è un metodo che trova l'oggetto "CityModel" associato alla città 				richiesta così da porter chiamare correttamente il metodo "createStatistics(List<CityModel> cities, Date 				startDate, Date endDate)" descritto precedentemente tra i metodi del "service";
		
		Metodo per la chiamata dell'API
			1)"getClimateForCity(CityModel city)"
				Questo metodo utlizza il tipo di dato "CompletableFuture<Climate>" per affrontare la chiamata API. Questo 				infatti, attraverso il metodo "restTemplate.getForObject(url, Climate.class", gestisce l'assegnazione di 				tutte le coppie variabile/valore necessarie prese dall'url alle corrispondenti coppie nell'ogggetto 				"Climate";
				
Ognuna delle parti ha costantemente aiutato l'altra perciò trovo difficile riuscire ad indicare una vera separazione del compiti, non è una reale distizione dei compiti tuttavia posso affermare che Bruni Daniele ha realizzato il ReadMe e parte della classe "WeatherService", mentre Sofiaya Alahiane ha realizzato l'uml e la parte della classe "WeatherService" legata alla statistica.