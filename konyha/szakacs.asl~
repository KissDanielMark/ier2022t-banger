/* Initial goals */

!get(etel).		//celja az agensnek penzt szerezni
//!check_tired. // initial goal: verify whether I am getting tired

//ha penzt akarok szerezni szólni kell a kuktanak, hogy erje el,
//hogy legyen elokeszitett_kaja
+!get(etel) : true 
	<- .send(kukta, achieve, has(szakacs, etel)).
	
+has(szakacs, keszkaja): true
				<- .send(pincer, achieve, serve(keszkaja)).
				
//ha van szakacsnak elokeszitett_kaja akkor fozzon kajat
+has(szakacs, etel) : true 
				<- !cook(etel).

//ha nincs nala elokeszitett kaja szolni a kuktanak
-has(szakacs, etel) : true 
				<- !get(etel).
			
//amig van nalam kaja suss vagy valami
+!cook(etel) : has(szakacs, etel)
				<-sip(etel);
				!cook(etel).
				
+!cook(etel) : not has(szakacs, etel)
				<- elkeszult(keszetel).
				
