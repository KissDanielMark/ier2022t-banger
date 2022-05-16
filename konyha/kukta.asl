/* Initial beliefs and rules */

// initially, I believe that there is some nyersanyag in the fridge
available(nyersanyag,fridge).

/* Plans */
//Ha azt a parancsot kapja hogy legyen a szakacsnak 
//elokeszitett kajaja & van nyersanyag hutoben
+!has(szakacs,elokeszitett_kaja) : available(nyersanyag, fridge)
		<-	!at(kukta, fridge);
			open(fridge);
			get(food);
			close(fridge);
			prepare(nyersanyag);
			!at(kukta, szakacs);
			hand_in(elokeszitett_kaja);//na itt ezt nem tudom, lehet elokeszitett_kaja kell neki
			?has(szakacs, nyersanyag);
			print("KUKTA: elvegeztem a feladatomat - elokeszitettem, mert volt a hutoben").
//HA azt a parancsot kaptam szakacstol hog ker elokeszitett kajat
//viszont nincs nyersanyag akkor
+!has(szakacs, elokeszitett_kaja): not available(nyersanyag, fridge)
		<- .send(futar, achieve, get(nyersanyag, 5));
			!at(kukta, fridge);
			.print("\n\n\nSzakas szol futarnak hozzon nyersanyagot").

