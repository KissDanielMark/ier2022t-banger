/* Initial beliefs and rules */
last_order_id(1).

//achive the goal "order" for agent Ag
+!order(Product,Qtd)[source(Ag)]:true
	<- ?last_order_id(N);
	OrderId = N+1;
	-+last_order_id(OrderId);
	deliver(Product, Qtd);
	.send(Ag, tell, deliverd(Product, Qtd, OrderId)).
