/* Initial goal */
!serve(keszkaja).

/* Initial beliefs and rules */
rendeles(asztal).

+!serve(keszkaja): has(szakacs, keszkaja)
	<- 	!at(pincer, szakacs);
	   	felvesz(keszkaja);
		?rendeles(A);
		!at(pincer, A);
		felszolgal(keszkaja);
		!at(pincer, szakacs).
		
+!serve(keszkaja): not has(szakacs, keszkaja)
	<-	!at(pincer, szakacs).
	
+!at(pincer, P): at(pincer,P) <- true.

+!at(pincer,P):not at(pincer, P) <- move_towards(P);
								!at(pincer,P).
-!at(pincer, P): true
	<- true.
