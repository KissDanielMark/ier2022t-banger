/*!start.

+!start : true 
	<- 	.print("Megint kestel ",futar,"!");	
		.send(futar,tell,lebaszas).

+kilepek[source(A)]
  <- .print(":( Csődbe mentem miattad ",A).
//!cook(food).   // initial goal: cook the food
//pontosabban itt azt kene ha rendelest kapok fozzek....
//+!cook(food) : true <- .send(kukta, achieve, prepare(owner,beer)).


*/
/* Initial goals */
!get(money).		//initial goal: get the money
!check_tired. // initial goal: verify whether I am getting tired

//ha penzt akarok szerezni szólni kell a kuktanak, hogy erje el,
//hogy legyen alapanyag
+!get(money) : true <- .send(kukta, achieve, has(szakacs, alapanyag)).
//ha van szakacsnak alapanyaga akkor fozzon kajat
+has(szakacs, alapanyag) : true <- !cook(food).
//??? ha nincs alapanyag szolj hogy miiii?????
-has(szakacs, alapanyag): true <- !get(money).
