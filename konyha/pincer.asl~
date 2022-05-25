/* Initial goal */
!serve(keszkaja).

/* Initial beliefs and rules */
	
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
