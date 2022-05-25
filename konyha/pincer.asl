/* Initial goal */
!serve(keszkaja).

/* Initial beliefs and rules */
rendeles(ures).

+!serve(keszkaja): has(szakacs, keszkaja)
	<- 	!at(pincer, szakacs);
	   	felvesz(keszkaja);
		?rendeles(A);
		!at(pincer, A);
		felszolgal(keszkaja);
		!at(pincer, szakacs).
		
+!serve(keszkaja): not has(szakacs, keszkaja)
	<-	!at(pincer, szakacs).
-!serve(keszkaja): true <- !serve(keszkaja).
+!at(pincer, P): at(pincer,P) <- true.

+!at(pincer,P):not at(pincer, P) <- move_towards(P);
								!at(pincer,P).
-!at(pincer, P): true
	<- true.
