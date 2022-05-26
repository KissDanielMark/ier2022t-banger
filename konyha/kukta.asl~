/* Initial beliefs and rules */

// initially, I believe that there is some alapanyag in the fridge
available(alapanyag,fridge).

/* Plans */
//Ha azt a parancsot kapja hogy legyen a szakacsnak 
//elokeszitett kajaja & van nyersanyag hutoben
+!has(szakacs,alapanyag) : available(alapanyag, fridge)
		<-	!at(kukta, fridge);
			open(fridge);
			get(alapanyag);
			close(fridge);
			!at(kukta, szakacs);
			hand_in(alapanyag);
			?has(szakacs, alapanyag).
//HA azt a parancsot kaptam szakacstol hog ker elokeszitett kajat
//viszont nincs nyersanyag akkor
+!has(szakacs, alapanyag) : not available(alapanyag, fridge)
				<- .send(supermarket, achieve, order(alapanyag, 5));
				   !at(kukta,fridge). 

-!has(szakacs,alapanyag)
   :  true
   <- !has(szakacs,alapanyag).


+!at(kukta, P): at(kukta,P) <- true.

+!at(kukta,P):not at(kukta, P) <- move_towards(P);
								!at(kukta,P).
				
+delivered(alapanyag,_Qtd,_OrderId)[source(supermarket)]
  :  true
  <- +available(alapanyag,fridge);
     !has(szakacs,alapanyag).

// when the fridge is opened, the beer stock is perceived
// and thus the available belief is updated
+stock(alapanyag,N)
   :  N < 3 & available(alapanyag,fridge)
   <- -available(alapanyag,fridge).
+stock(alapanyag,N)
   :  N > 3 & not available(alapanyag,fridge)
   <- -+available(alapanyag,fridge).
