/* Initial goals */
!get(penz).		//celja az agensnek penzt szerezni

//ha penzt akarok szerezni szólni kell a kuktanak, hogy erje el,
//hogy legyen elokeszitett_kaja
+!get(penz) : true 
	<- .send(kukta, achieve, has(szakacs, alapanyag)).
	
+has(szakacs, keszkaja): true
				<- .send(pincer, achieve, serve(keszkaja)).
-has(szakacs, keszkaja): true
				<- true.
//ha van szakacsnak elokeszitett_kaja akkor fozzon kajat
+has(szakacs, alapanyag) : true 
				<- !cook(etel).

//ha nincs nala elokeszitett kaja szolni a kuktanak
-has(szakacs, alapanyag) : true 
				<- !get(penz).
			
//amig van nalam kaja suss vagy valami
+!cook(etel) : has(szakacs, alapanyag)
				<- kavar(etel);
				!cook(etel).
				
+!cook(etel) : not has(szakacs, alapanyag)
				<- elkeszult(keszkaja).
-!cook(etel): true
	<- true.
