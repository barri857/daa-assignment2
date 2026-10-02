import pandas as pd
import matplotlib.pyplot as plt
import os


os.makedirs("results/plots", exist_ok=True)
df = pd.read_csv("results/results.csv")

#Time vs n (Random Access)
plt.figure(figsize=(8, 5))
for struct in df[df['workload'] == 'W1']['structure'].unique():
    subset = df[(df['workload'] == 'W1') & (df['structure'] == struct)]
    plt.plot(subset['n'], subset['time_ms'], marker='o', label=struct)
plt.xlabel('n')
plt.ylabel('Time (ms)')
plt.title('Workload 1: Random Access - Time vs n')
plt.legend()
plt.grid(True)
plt.savefig('results/plots/w1_time_vs_n.png')
plt.close()

#Time vs n (Search)
plt.figure(figsize=(8, 5))
for struct in df[df['workload'] == 'W2']['structure'].unique():
    subset = df[(df['workload'] == 'W2') & (df['structure'] == struct)]
    plt.plot(subset['n'], subset['time_ms'], marker='s', label=struct)
plt.xlabel('n')
plt.ylabel('Time (ms)')
plt.title('Workload 2: Search - Time vs n')
plt.legend()
plt.grid(True)
plt.savefig('results/plots/w2_time_vs_n.png')
plt.close()

#Steps/Moves/Comparisons vs n
plt.figure(figsize=(8, 5))
w3_mid = df[(df['workload'] == 'W3') & (df['variant'] == 'middle')]
for struct in w3_mid['structure'].unique():
    sub = w3_mid[w3_mid['structure'] == struct]
    plt.plot(sub['n'], sub['steps'], marker='o', label=f'{struct} Steps')
    plt.plot(sub['n'], sub['moves'], marker='x', label=f'{struct} Moves')
plt.xlabel('n')
plt.ylabel('Operation Count')
plt.title('Workload 3 (Middle): Insert & Remove Metrics vs n')
plt.legend()
plt.grid(True)
plt.savefig('results/plots/w3_middle_metrics.png')
plt.close()

print("Plots generated successfully in results/plots/")