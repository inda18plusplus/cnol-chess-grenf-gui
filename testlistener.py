import socket, struct, json, telnetlib
from hashlib import sha256
import random, time, string

host = "127.0.0.1"
port = 1234

listener = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
listener.bind((host,port))
listener.listen(1)
s, _ = listener.accept()


def sendJSON(a):
	a = json.dumps(a)
	alen = struct.pack(">H", len(a))
	#print len(a)
	s.send(alen)
	print "sending",a
	s.send(a)

def recvJSON():
	alen = s.recv(2)
	alen = struct.unpack(">H", alen)[0]
	a = s.recv(alen)
	a = json.loads(a)
	return a

random.seed(time.time())
mac = random.choice("01")
mac += ''.join([random.choice(string.uppercase) for _ in range(10)])
print mac

hashmac = sha256(mac).hexdigest()
print hashmac
sendJSON({"type":"init","hash":hashmac.upper()})
print "received", recvJSON()
sendJSON({"type":"init","seed":mac,"choice":int(mac[0])})


sendJSON({"type":"repsonse","repsonse":"ok"})
sendJSON({"type":"move","from":"G7", "to":"G5", "promotion":"Q"})



t = telnetlib.Telnet()
t.sock = s
t.interact()
