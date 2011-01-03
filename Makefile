
.PHONY: documentation pflichtenheft source

all: documentation pflichtenheft source

documentation:
	echo "\n\n\033[32mBuilding the documentation\033[00m"
	cd doc/documentation/ && make noview

pflichtenheft:
	echo "\n\n\033[32mBuilding the pflichtenheft\033[00m"
	cd doc/documentation/ && make noview

source:
	echo "\n\n\033[32mBuilding the actual source\033[00m"
	ant build
