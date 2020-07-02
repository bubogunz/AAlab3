import numpy as np
import os

def write_data_cost(file_n, file_f):
    data_n = []
    data_f = []
    cost = []
    for line_n, line_f in zip(file_n, file_f):
        list_line_n = line_n.split(" ")
        list_line_f = line_f.split(" ")
        if list_line_n[0] == "Time":
            data_n.append(float(list_line_n[2]))
        if list_line_f[0] == "Time":
            data_f.append(float(list_line_f[2]))
        if list_line_n[0] == "MST":
            cost.append(int(list_line_n[2]))
    return np.array(data_n), np.array(data_f), np.array(cost) 

def write_table(size, cost, time, contraction, discovery, error):
    w_results = open("table_results.txt", "w")
    w_results.write("\\begin{center}"
	"\\scriptsize"
	"\\begin{longtable}{|c|c|c|c|c|c|}\n"
    "\\caption{Risultati dell'algoritmo di Karger} \\\\\n"
    "\\hline\n"
    "\\textbf{N.} & \\textbf{Graph Size (nodes)} & \\textbf{Time (s)} & \\textbf{Full Contraction Time (s)} & \\textbf{Discovery Time} & \\textbf{Error (%)} \\\\\n")

    count = 0

    for i in range(len (size)):
        count = count + 1
        w_results.write("\\hline\n"
        f"{count} & {size[i]} & {cost[i]} & {time[i]} & {contraction[i]} & {discovery[i]} & {error[i]} \\\\\n")

    w_results.write("\\hline"
    "\\caption{Risultati dell'algoritmo di karger}"
    "\\label{results}"
	"\\end{longtable}"
    "\\end{center}")

    w_results.close()

my_path = os.path.abspath(os.path.dirname(__file__))
path = os.path.join(my_path, "JavaLab3/mincut.txt")
mincut = open(path, "r")

if mincut.mode == "r" :
    size_graph = []
    cost = []
    time = []
    full_contraction_time = []
    discovery_time = []
    error = []

    for line in mincut :
        list_line = line.split("\t")
        size_graph.append(int (list_line[0]))
        cost.append(int (list_line[1]))
        time.append(float (list_line[2]))
        full_contraction_time.append(float (list_line[3]))
        discovery_time.append(float (list_line[4]))
        error.append(float (list_line[5]))

    write_table(size_graph, cost, time, full_contraction_time, discovery_time, error)

mincut.close()
    