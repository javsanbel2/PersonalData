import re
file = open('regexfile.txt', 'r')
mydata = []
for line in file:
	mydata.append(line)

numberpat = re.compile(r'\d{3}.\d{3}.\d{3}')
#print(mydata)
newdata = []
for x in mydata:
	data = numberpat.findall(x)
	if data != "":
		newdata.append(data)
newdata = list(filter(None, newdata))
for y in newdata:
	y[0] = re.sub('[,*]','-',y[0])
	#print(y[0])
import csv
with open('resutls.csv', 'w') as csvfile:
	filewriter = csv.writer(csvfile, delimiter=',',quotechar='|', quoting=csv.QUOTE_MINIMAL)
	for num in newdata:
		filewriter.writerow([num[0]])
