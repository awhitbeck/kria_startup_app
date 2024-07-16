import time
import test_kv260

r = test_kv260.Root()
r.start()
n =  r.getNode('Root.Application.WriteRegisterA')

f = open("demofile.txt", "a")


for i in range(1000):

    n.set(i)
    f.write("Wrote {0}, read {1} \n".format(hex(i),hex(n.get())))
    time.sleep(0.1)
    
f.close()
