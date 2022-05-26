//achive the goal "order" for agent Ag
+!order(Product,Qtd)[source(Ag)]:true
	<- deliver(Product, Qtd);
	.send(Ag, tell, delivered(Product, Qtd)).
