/* Initial goals */

//celja az agensnek penzt szerezni
!get(penz).		

//ha penzt akarok szerezni szolni kell a kuktanak, hogy szerezzen alapanyag-ot
+!get(penz) : true <- .send(kukta, achieve, has(szakacs, alapanyag)).

//ha van keszkaja a szakacsnal kuldje el a pincert felszolgalni
+has(szakacs, keszkaja): true <- .send(pincer, achieve, serve(keszkaja)).
-has(szakacs, keszkaja): true <- true.

//ha van szakacsnak alapanyag-a akkor cook etel
+has(szakacs, alapanyag) : true <- !cook(etel).

//ha nincs nala alapanyag kiindulasi allpotba lepes
-has(szakacs, alapanyag) : true <- !get(penz).
			
//fozni ha van a szakacsnal alapanyag
+!cook(etel) : has(szakacs, alapanyag)
				<- kavar(etel);
				!cook(etel).
				
+!cook(etel) : not has(szakacs, alapanyag) <- elkeszult(keszkaja).

-!cook(etel): true <- true.
