/* Initial goal */
!serve(keszkaja).

/* Initial beliefs and rules */
last_order_id(1).


//achive the goal "order" for agent Ag
+!order(Product,Qtd)[source(Ag)]:true
	<- ?last_order_id(N);
	OrderId = N+1;
	-+last_order_id(OrderId);
	deliver(Product, Qtd);
	.send(Ag, tell, deliverd(Product, Qtd, OrderId)).
	
+!serve(keszkaja): has(szakacs, keszkaja)
	<- 	!at(pincer, szakacs);
	   	felvesz(keszkaja);
		!at(pincer, asztal);
		felszolgal(keszkaja);
		!serve(keszkaja).

+!serve(keszkaja): not has(szakacs, keszkaja)
	<-	!at(pincer, szakacs).
	
+!at(pincer, P): at(pincer,P) <- true.

+!at(pincer,P):not at(pincer, P) <- move_towards(P);
								!at(pincer,P).
