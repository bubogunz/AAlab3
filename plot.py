import matplotlib.pyplot as plt
import numpy as np
import os

def plot(x, y, title):
    plt.figure(title)
    plt.plot(x, y, 'k', x, y, 'ro')
    plt.xlabel("Size of graph (nodes)")
    plt.ylabel("Time (s)")
    plt.yscale("log")
    plt.savefig(os.path.join(os.path.abspath(os.path.dirname(__file__)), f"relazioneAA/relazioneAA/imgs/{title}.png"))

def mean(x):
    i = 0
    summ = 0
    y = []
    for ind in x:
        summ += ind
        if(i == 3):
            y.append(summ / 4)
            summ = 0
            i = 0
        i = i + 1




my_path = os.path.abspath(os.path.dirname(__file__))
path = os.path.join(my_path, "JavaLab3/mincut.txt")
mincut = open(path, "r")

if mincut.mode == "r" :
    size_graph = []
    time = []
    full_contraction_time = []
    discovery_time = []

    for line in mincut :
        list_line = line.split("\t")
        size_graph.append(int (list_line[0]))
        time.append(float (list_line[2]))
        full_contraction_time.append(float (list_line[3]))
        discovery_time.append(float (list_line[4]))

    x = []
    y1 = []
    y2 = []
    y3 = []

    i = 0
    for ind in size_graph:
        if(i == 0 or i == 4):
            x.append(ind)
            i = 0
        i = i + 1

    y1 = mean(time)
    y2 = mean(full_contraction_time)
    y3 = mean(discovery_time)

    plot(x, y1, "time_Karger")
    plot(x, y2, "time_full_contraction")
    plot(x, y3, "time_Karger_discovery_time")

    if len(x) == 10:
        plot(x[:5], y1[:5], "time_Karger_firstHalf")
        plot(x[:5], y2[:5], "time_full_contraction_firstHalf")
        plot(x[:5], y3[:5], "time_Karger_discovery_time_firstHalf")

        plot(x[5:], y1[5:], "time_Karger_secondHalf")
        plot(x[5:], y2[5:], "time_full_contraction_secondHalf")
        plot(x[5:], y3[5:], "time_Karger_discovery_time_secondHalf")

mincut.close()