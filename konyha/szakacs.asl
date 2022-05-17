/* Initial goals */

!get(etel).		//celja az agensnek penzt szerezni
//!check_tired. // initial goal: verify whether I am getting tired

//ha penzt akarok szerezni szólni kell a kuktanak, hogy erje el,
//hogy legyen elokeszitett_kaja
+!get(etel) : true 
	<- .send(kukta, achieve, has(szakacs, etel)).
	
	
//ha van szakacsnak elokeszitett_kaja akkor fozzon kajat
+has(szakacs, etel) : true <- !cook(etel).



//ha nincs nala elokeszitett kaja szolni a kuktanak
-has(szakacs, etel): true <- !get(etel).
