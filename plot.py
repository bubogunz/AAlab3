import matplotlib.pyplot as plt
from matplotlib import colors
from matplotlib.ticker import PercentFormatter
import random
import numpy as np
import re

output = open("./JavaLab3/mincut.txt", "r")
lines = output.readlines()

dataset = []
size = []
time = []
full_contraction = []
discovery_time = []

for line in lines:
    ln = re.split(r'\t+', line)
    if len(ln) > 1:
        dataset.append(ln[0])
        if ln[1] != "Size":
            size.append(int(ln[1]))
            time.append(float(ln[3]))
            full_contraction.append(float(ln[4]))
            discovery_time.append(float(ln[5]))

dataset = dataset[1:]
"""
size = size[1:]
time = time[1:]
full_contraction = full_contraction[1:]
discovery_time = discovery_time[1:]
"""
"""
print("dataset:")
print(dataset)
print()
print("size:")
print(size)
print()
print("time:")
print(time)
print()
print("full_contraction:")
print(full_contraction)
print()
print("discovery_time:")
print(discovery_time)
"""

avg_size = []
avg_time = []
avg_full_contraction = []
avg_discovery_time = []

si = 0
ti = 0.0
fc = 0.0
dt = 0.0

for i in range(0, len(dataset)):
    si = si + int(size[i])
    ti = ti + float(time[i])
    fc = fc + float(full_contraction[i])
    dt = dt + float(discovery_time[i])
    if (i+1)%4 == 0:
        avg_size.append(si/4)
        avg_time.append(ti/4)
        avg_full_contraction.append(fc/4)
        avg_discovery_time.append(dt/4)
        si = 0
        ti = 0.0
        fc = 0.0
        dt = 0.0
"""
print(size)
print(time)
print(full_contraction)
print(discovery_time)
"""
print(avg_size)
plt.title("Average time by graph size 125 ≤ |G| ≤ 200")
plt.plot(avg_size[6:], avg_time[6:],"b")
#plt.yscale("symlog")
plt.grid(True)
plt.yticks(avg_time[6:])
plt.xticks(avg_size[6:])
#plt.legend()
plt.ylabel('time elapsed (s)')
plt.xlabel('graph size')
plt.show()
output.close()
"""
print(avg_size)
offset = 680
plt.bar(dataset[36:40], height=[time[38]-offset, time[37]-offset, time[38]-offset, time[39]-offset], bottom=offset)
plt.yticks(time[37:40])
plt.grid(True)
plt.ylabel('time elapsed (s)')
plt.title("Karger-Stein algorithm time with |G| = 200")
#plt.legend()
#plt.savefig("./imgs/" + dataset[i] + ".png")
plt.show()
"""