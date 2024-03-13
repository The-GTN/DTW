EXEC = dtw
TEST = java -jar lib/test-1.7.jar
MOD_PATH = C:\Users\Utilisateur\Downloads\openjfx-19_windows-x64_bin-sdk\javafx-sdk-19\bin

all: $(EXEC)

test:
	cd /mnt/c/Documents\ and\ Settings/

doc:
	cd src;javadoc dtw -d ../docs;

comp: 
	cd src; javac */*.java -d ../classes

compTest: comp
	javac -classpath lib/test-1.7.jar test/DTW/*.java test/DTW/*/*.java;


dtw:
	java --module-path $(MOD_PATH) --add-modules javafx.controls,javafx.fxml -jar dist/dtw.jar

exe: comp
	cd classes;jar cvfm ../dist/dtw.jar ../lib/manifest/manifest-DTW.MF dtw com javafx

extract:
	cd dist;jar xvf dtw.jar

clean:
	rm -rf classes docs test/*.class test/*/*.class test/*/*/*.class dist/META-INF dist/dtw

.PHONY: clean extract exe test compTest comp classes doc all dtw
