/* Initial beliefs and rules */

// initially, I believe that there is some nyersanyag in the fridge
available(nyersanyag,fridge).

/* Plans */
+!has(szakacs,elokeszitett_kaja) 
			: available(nyersanyag, fridge)
		<-	!at(kukta, fridge);
			open(fridge);
			get(food);
			close(fridge);
			prepare(nyersanyag);
			!at(kukta, szakacs);
			hand_in(nyersanyag);//na itt ezt nem tudom, lehet elokeszitett_kaja kell neki
			?has(szakacs, nyersanyag).
+!has(szakacs, elokeszitett_kaja)
			: not available(nyersanyag, fridge)
		<- .send(futar, achieve, get(nyersanyag, 5));
			!at(kukta, fridge).

