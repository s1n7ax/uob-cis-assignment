#!/bin/bash

# clean gradle projects for eclipse
for i in $(ls -d */); do
    echo "cleaning eclipse files in project:: ${i%%/}";
    cd "$i"
    ./gradlew cleanEclipse eclipse
    cd ..
done
