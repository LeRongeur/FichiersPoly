#!/bin/bash

for file in data/*.le; do
    echo $file

    java -cp gen LEParser < $file >&2 && echo "REUSSI" || echo "ECHEC"

    echo
done
