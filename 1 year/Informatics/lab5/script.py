import matplotlib.pyplot as plt
import mplcyberpunk

import seaborn as sb
import pandas as pd
import csv

column = ['START', 'MAX', 'MIN', 'CLOSE']
date = ['05.09.2018', '05.10.2018', '07.11.2018', '05.12.2018']

with open('SPFB.csv', 'r') as file:
    data = csv.reader(file, delimiter=';')
    sp = [[], [], [], []]

    next(data, None)

    for row in data:
        if row[2] == date[0]:
            sp[0].append(list(map(int, row[4:8])))
        elif row[2] == date[1]:
            sp[1].append(list(map(int, row[4:8])))
        elif row[2] == date[2]:
            sp[2].append(list(map(int, row[4:8])))
        elif row[2] == date[3]:
            sp[3].append(list(map(int, row[4:8])))

    with plt.style.context('cyberpunk'):
        plt.figure(figsize=(15, 8))
        for i in range(4):
            df = pd.DataFrame(sp[i], columns=column)
            plt.subplot(2, 2, i + 1)
            df.boxplot()
            # sb.boxplot(date=df, palette='husl')

            plt.title(f"{date[i]}")
            plt.legend(column, loc='best')

        plt.subplots_adjust(wspace=0.3, hspace=0.3)
        plt.savefig("BoxPlot")
        plt.show()

