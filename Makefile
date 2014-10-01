compile:
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/client/testzip/*.java
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/registry/*.java
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/ror/*.java
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/server/*.java
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/util/*.java

registry:
	java -cp ./RMI/bin server.RegistryServer 8888

rmi_server:
	java -cp ./RMI/bin server.RMIServer client.testzip.ZipCodeServerImpl localhost 8888 zipcode

client:
	java -cp ./RMI/bin client.testzip.ZipCodeClient localhost 8888 zipcode data.txt   
