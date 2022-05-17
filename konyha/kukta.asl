/* Initial beliefs and rules */

// initially, I believe that there is some etel in the fridge
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
			?has(szakacs, etel).
//HA azt a parancsot kaptam szakacstol hog ker elokeszitett kajat
//viszont nincs nyersanyag akkor
+!has(szakacs, etel): not available(etel, fridge)
		<- .send(futar, achieve, get(etel, 5));
			!at(kukta, fridge).

+!at(kukta, P): at(kukta,P) <- true.

+!at(kukta,P):not at(kukta, P) <- move_towards(P);
								!at(kukta,P).
								

// when the fridge is opened, the beer stock is perceived
// and thus the available belief is updated
+stock(etel,0)
   :  available(etel,fridge)
   <- -available(etel,fridge).
+stock(etel,N)
   :  N > 0 & not available(etel,fridge)
   <- -+available(etel,fridge).
