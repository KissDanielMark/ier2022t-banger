/* Initial beliefs and rules */

// initially, I believe that there is some alapanyag in the fridge
available(alapanyag,fridge).

/* Plans */
//Ha azt a parancsot kapja hogy legyen a szakacsnak 
//alapanyag-a & van alapanyag fridge-ben, menjen el es vigye a szakacshoz
+!has(szakacs,alapanyag) : available(alapanyag, fridge)
		<-	!at(kukta, fridge);
			open(fridge);
			get(alapanyag);
			close(fridge);
			!at(kukta, szakacs);
			hand_in(alapanyag);
			?has(szakacs, alapanyag).
			
//HA azt a parancsot kaptam szakacstol hogy legyen alapanyaga
//viszont nincs nyersanyag akkor szolni a supermarket-nek, hogy hozzon 5 adagot
+!has(szakacs, alapanyag) : not available(alapanyag, fridge)
				<- .send(supermarket, achieve, order(alapanyag, 5));
				   !at(kukta,fridge). 

-!has(szakacs,alapanyag): true <- !has(szakacs,alapanyag).


+!at(kukta, P): at(kukta,P) <- true.

//ha nincsen a celjanal menjen az iranyaba
+!at(kukta,P):not at(kukta, P) <- move_towards(P);
								!at(kukta,P).
				
+delivered(alapanyag,_Qtd)[source(supermarket)]
  :  true
  <- +available(alapanyag,fridge);
     !has(szakacs,alapanyag).

+stock(alapanyag,N)
   :  N < 3 & available(alapanyag,fridge)
   <- -available(alapanyag,fridge).
   
   
+stock(alapanyag,N)
   :  N > 3 & not available(alapanyag,fridge)
   <- -+available(alapanyag,fridge).
