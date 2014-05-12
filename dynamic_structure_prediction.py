match_scores = int(1)
no_match_score = int(-1)

import sys


def match(a,b):
	if a is 'G':
		if b is 'C':
			return 1
		else:
			return 0
	if a is 'C':
		if b is 'G':
			return 1
		else:
			return 0
	if a is 'A':
		if b is 'U':
			return 1
		else:
			return 0
	if a is 'U':
		if b is 'A':
			return 1
		else:
			return 0
	else: return 0

def fill_field(m,i,j):
	#i,j base pair
	a = m[i+1][j-1] + match(rna[i],rna[j])
	
	#no pair
	b = m[i+1][j]
	c = m[i][j-1]

	#bifurcations
	d = -100
	for k in range(i,j):
		d = max(d, m[i][k] + m[k+1][j])

	m[i][j] = max(a,b,c,d)

def print_array(m):
	for i in m:
		print i

# read input sequence
rna = ""
#print "RNA: " + rna

f = open(sys.argv[1], "r")
line = f.readline()

while (line):
	rna +=(line.strip())
	line = f.readline()
f.close()
print "RNA:", rna, "| length:", len(rna)

# initialize
l = len(rna)
m = [[0 for i in range(l)] for j in range(l)]
for i in range(l):
	m[i][i] = 0
	m[i][i-1] = 0
#print_array(m)

# fill 
i = l-2
j = l-1
while i >= 0:
	#print "i and j: " + str(i) + " " + str(j)
	fill_field(m,i,j)
	if j == l-1:
		i = i-1
		j = i+1
	else:
		j = j+1
#print_array(m)
print "\nscore:", m[0][len(m)-1]

