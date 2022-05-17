/* Initial beliefs and rules */

// initially, I believe that there is some nyersanyag in the fridge
available(etel,fridge).

/* Plans */
//Ha azt a parancsot kapja hogy legyen a szakacsnak 
//elokeszitett kajaja & van nyersanyag hutoben
+!has(szakacs,etel) : available(etel, fridge)
		<-	!at(kukta, fridge);
			open(fridge);
			get(etel);
			close(fridge);
			prepare(etel);
			!at(kukta, szakacs);
			hand_in(etel);//na itt ezt nem tudom, lehet elokeszitett_kaja kell neki
			?has(szakacs, etel);
			print("KUKTA: elvegeztem a feladatomat - elokeszitettem, mert volt a hutoben").
//HA azt a parancsot kaptam szakacstol hog ker elokeszitett kajat
//viszont nincs nyersanyag akkor
+!has(szakacs, etel): not available(etel, fridge)
		<- .send(futar, achieve, get(etel, 5));
			!at(kukta, fridge);
			.print("\n\n\nSzakas szol futarnak hozzon nyersanyagot").

+!at(kukta, P): at(kukta,P) <- true.
+!at(robot,P):not at(kukta, P) <- move_towards(P);
								print("Mozog a P fele");
								!at(kukta,P).
