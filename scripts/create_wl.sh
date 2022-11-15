#!/bin/bash
customers=../app/src/main/kotlin/Customers.kt

positionToAddCustomer=12
lineToFind="val customers = mapOf("
count=0
while IFS="" read -r p || [ -n "$p" ]
do
  (( count++ ))
  var="${p#"${p%%[![:space:]]*}"}"
  #echo $var
  #echo "$var----$lineToFind"
  if [[ $var == "$lineToFind" ]]; then
    echo "Found it on line $count"
    positionToAddCustomer=$count
  fi
done < $customers

customerName="Max"
applicationId="se.caspeco.bstl.max"
singleTab="   "

customerToAdd="\/\/ $customerName\n$singleTabobject Caspeco$customerName : Customer {\n      override val applicationId = \"$applicationId\"\n   }\n"

sed -i "$positionToAddCustomer i $customerToAdd" $customers