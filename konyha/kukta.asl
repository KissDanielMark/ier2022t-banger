/* Initial beliefs and rules */

// initially, I believe that there is some food in the fridge
available(food,fridge).

/* Plans */
+!has(szakacs,alapanyag) 
			: available(food, fridge)
		<-	!at(kukta, fridge);
			open(fridge);
			get(food);
			close(fridge);
			prepare(food);
			!at(kukta, szakacs);
			hand_in(food);
			?has(szakacs, food).

