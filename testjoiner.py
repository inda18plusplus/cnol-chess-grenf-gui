import socket, struct, json, telnetlib

host = "127.0.0.1"
port = 1234

s = socket.create_connection((host, port))

length = s.recv(2)
length = struct.unpack(">H", length)[0]
print length

text = s.recv(length)
print text

def sendJSON(a):
	a = json.dumps(a)
	alen = struct.pack(">H", len(a))
	print len(a)
	s.send(alen)
	print a
	s.send(a)

sendJSON({"type":"init","choice":1})
sendJSON({"type":"response","response":"ok"})
sendJSON({"type":"move","to":"B5","from":"B7","promotion":"Q"})

t = telnetlib.Telnet()
t.sock = s
t.interact()
