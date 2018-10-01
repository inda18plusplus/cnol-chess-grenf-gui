import socket, struct, json, telnetlib

host = "127.0.0.1"
port = 1234

s = socket.create_connection((host, port))

length = s.recv(2)
length = struct.unpack(">H", length)[0]
print length

text = s.recv(length)
print text

a = {"choice":1, "tjoho":"tjenare"}
a = json.dumps(a)
alen = struct.pack(">H", len(a))
s.send(alen)
s.send(a)


t = telnetlib.Telnet()
t.sock = s
t.interact()
