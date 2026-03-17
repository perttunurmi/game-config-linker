default:
	make test
	make clean
	make package
	make run

compile:
	mvn compile

test:
	mvn test

package:
	mvn package

clean:
	mvn clean

run:
	java -cp target/SteamGameConfigManager-0.50.jar configManager.App
