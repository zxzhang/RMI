RegistryHost = localhost
RegistryPort = 8888

compile:
	mkdir ./RMI/bin
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/client/testzip/*.java
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/registry/*.java
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/ror/*.java
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/remote/*.java
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/server/*.java
	javac -d ./RMI/bin -cp ./RMI/src ./RMI/src/util/*.java

registry:
	java -cp ./RMI/bin server.RegistryServer $(RegistryPort)

rmi_server:
	java -cp ./RMI/bin server.RMIServer client.testzip.ZipCodeServerImpl $(RegistryHost) $(RegistryPort) zipcode

client:
	java -cp ./RMI/bin client.testzip.ZipCodeClient $(RegistryHost) $(RegistryPort) zipcode data.txt

clean:
	rm -rf ./RMI/bin

