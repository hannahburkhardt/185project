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
	if letter is "A":
		print '{"group":1},'
	elif letter is "U":
		print '{"group":2},'
	elif letter is "G":
		print '{"group":3},'
	elif letter is "C":
		print '{"group":4},'

if continuous_seq[-1] is "A":
	print '{"group":1}'
elif continuous_seq[-1] is "U":
	print '{"group":2}'
elif continuous_seq[-1] is "G":
	print '{"group":3}'
elif continuous_seq[-1] is "C":
	print '{"group":4}'

print "]}"
