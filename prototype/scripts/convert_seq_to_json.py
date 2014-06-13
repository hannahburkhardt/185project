import sys

f = open(sys.argv[1], "r")
line = f.readline().strip()

continuous_seq = ""
while (line):
	continuous_seq += line
	line = f.readline().strip()
f.close()

print '{rna:['
for letter in continuous_seq:
	if letter is "A" or letter is "a":
		print '{nucleotide:"a",available:1},'
	elif letter is "U" or letter is "u":
		print '{nucleotide:"u",available:1},'
	elif letter is "G" or letter is "g":
		print '{nucleotide:"g",available:1},'
	elif letter is "C" or letter is "c":
		print '{nucleotide:"c",available:1},'

print '],connections:['

for c in range(len(continuous_seq)-1):
	print '{source:'+str(c)+',target:'+str(c+1)+',type:0},'

print ']}'
