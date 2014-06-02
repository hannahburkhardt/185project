import sys

f = open(sys.argv[1], "r")
line = f.readline().strip()

continuous_seq = ""
while (line):
	continuous_seq += line
	line = f.readline().strip()
f.close()

print '{"nodes":['
for letter in continuous_seq:
	if letter is "A" or letter is "a":
		print '{base:"a",available:1},'
	elif letter is "U" or letter is "u":
		print '{base:"u",available:1},'
	elif letter is "G" or letter is "g":
		print '{base:"g",available:1},'
	elif letter is "C" or letter is "c":
		print '{base:"c",available:1},'

print "]}"
